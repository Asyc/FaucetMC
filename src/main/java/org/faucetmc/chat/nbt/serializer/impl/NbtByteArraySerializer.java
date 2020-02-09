package org.faucetmc.chat.nbt.serializer.impl;

import org.faucetmc.chat.nbt.serializer.NbtSerializer;
import org.faucetmc.chat.nbt.tag.impl.NbtTagByteArray;
import org.faucetmc.chat.nbt.type.NbtTagType;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NbtByteArraySerializer extends NbtSerializer<NbtTagByteArray> {

	@Override
	public void serialize(OutputStream out, NbtTagByteArray value) throws IOException {
		byte[] bytes = value.getPayload();
		out.write((bytes.length >>> 24) & 0xFF);
		out.write((bytes.length >>> 16) & 0xFF);
		out.write((bytes.length >>> 8) & 0xFF);
		out.write((bytes.length) & 0xFF);
		out.write(bytes);
	}

	@Override
	public NbtTagByteArray deserialize(InputStream in, boolean hasName) throws IOException {
		int length = ((in.read() << 24) + (in.read() << 16) + (in.read() << 8) + (in.read()));
		byte[] bytes = new byte[length];
		if (in.read(bytes) == -1) throw new EOFException();
		return new NbtTagByteArray(bytes);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_BYTE_ARRAY;
	}
}
