package org.faucetmc.world.parse;

import org.faucetmc.nbt.NbtParser;
import org.faucetmc.nbt.NbtWriter;
import org.faucetmc.nbt.tag.impl.NbtTagCompound;
import org.faucetmc.util.FlippableByteArrayOutStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class RegionFile {

	private static final short TABLE_SIZE = 4096;

	private RandomAccessFile file;
	private MappedByteBuffer mappedBuffer;

	private LocationTableEntry[][] locationTable = new LocationTableEntry[32][32];
	private int[][] timestampTable = new int[32][32];

	private int regionX, regionZ;

	public RegionFile(int regionX, int regionZ, File regionFile) throws IOException {
		this.file = new RandomAccessFile(regionFile, "rw");
		this.mappedBuffer = file.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, file.length());

		if(!regionFile.exists()) {
			for(int x = 0; x < 32; x++) {
				for(int z = 0; z < 32; z++) {
					timestampTable[x][z] = 0;
					locationTable[x][z] = new LocationTableEntry(0, (short) 0);
				}
			}

			this.writeChunkLocationTable();;
			this.writeChunkTimestampTable();
		} else {
			this.reloadChunkLocations();
			this.reloadChunkTimestamps();
		}

	}

	public void reloadChunkLocations() {
		byte[] table = new byte[TABLE_SIZE];
		mappedBuffer.position(0);
		mappedBuffer.get(table);
		for(int x = 0; x < 32; x++) {
			for(int z = 0; z < 32; z++) {
				int location = ((x % 32) + (z % 32) * 32) * 4;
				int offset = (table[location + 2] & 0xFF) | ((table[location + 1] & 0xFF) << 8) | ((table[location] & 0x0F) << 16);
				short size = (short) (table[location + 3] & 0xFF);
				locationTable[x][z] = new LocationTableEntry(offset, size);
			}
		}
	}

	public void reloadChunkTimestamps() {
		byte[] table = new byte[TABLE_SIZE];
		mappedBuffer.position(TABLE_SIZE);
		mappedBuffer.get(table);

		for(int x = 0; x < 32; x++) {
			for(int z = 0; z < 32; z++) {
				int location = ((x % 32) + (z % 32) * 32) * 4;
				timestampTable[x][z] = ((table[location] << 24) + (table[location + 1] << 16) + (table[location + 2] << 8) + (table[location + 3]));
			}
		}
	}

	public NbtTagCompound readChunkData(int x, int z) throws IOException {
		LocationTableEntry entry = locationTable[x][z];
		if(entry == null) return null;
		mappedBuffer.position(entry.getOffset() * TABLE_SIZE);
		int length = mappedBuffer.getInt();
		byte compression = mappedBuffer.get();
		byte[] compressed = new byte[length - 1];
		mappedBuffer.get(compressed);

		InflaterInputStream inflaterIn = compression == 1 ? new GZIPInputStream(new ByteArrayInputStream(compressed)) : new InflaterInputStream(new ByteArrayInputStream(compressed));
		FlippableByteArrayOutStream decompressedOut = new FlippableByteArrayOutStream();

		while (inflaterIn.available() > 0) {
			decompressedOut.write(inflaterIn.read());
		}

		return NbtParser.readInputStream(decompressedOut.toInputStream());
	}

	public void writeChunkData(int x, int z, NbtTagCompound data) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DeflaterOutputStream deflaterOut = new DeflaterOutputStream(out);
		deflaterOut.write(NbtWriter.writeToByteArray(data));
		deflaterOut.close();
		byte[] compressed = out.toByteArray();
		byte compression = 2;
		int length = compressed.length + 1;

		LocationTableEntry entry = locationTable[x][z];
		mappedBuffer.position(entry.getOffset());
		mappedBuffer.putInt(length);
		mappedBuffer.put(compression);
		mappedBuffer.put(compressed);
	}

	public void writeChunkLocationTable() {
		for(int x = 0; x < 32; x++) {
			for(int z = 0; z < 32; z++) {
				LocationTableEntry entry = locationTable[x][z];
				int location = ((x % 32) + (z % 32) * 32) * 4;
				int offset = entry.getOffset();

				mappedBuffer.position(location);
				mappedBuffer.put((byte) ((offset >>> 16) & 0xFF));
				mappedBuffer.put((byte) ((offset >>> 8) & 0xFF));
				mappedBuffer.put((byte) ((offset) & 0xFF));
				mappedBuffer.put((byte) ((entry.getSize()) & 0xFF));
			}
		}
	}

	public void writeChunkTimestampTable() {
		for(int x = 0; x < 32; x++) {
			for(int z = 0; z < 32; z++) {
				int location = ((x % 32) + (z % 32) * 32) * 4;
				mappedBuffer.position(TABLE_SIZE + location);
				mappedBuffer.putInt(timestampTable[x][z]);
			}
		}
	}

	public int getChunkTimestamp(int x, int z) {
		return timestampTable[x][z];
	}

	public void setChunkTimestamp(int x, int z, int timestamp) {
		int location = ((x % 32) + (z % 32) * 32) * 4;
		mappedBuffer.putInt(location, timestamp);
		timestampTable[x][z] = timestamp;
	}
}
