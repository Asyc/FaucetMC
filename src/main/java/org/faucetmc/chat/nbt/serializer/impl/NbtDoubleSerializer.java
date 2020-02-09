package org.faucetmc.chat.nbt.serializer.impl;

import org.faucetmc.chat.nbt.serializer.NbtSerializer;
import org.faucetmc.chat.nbt.tag.impl.NbtTagDouble;
import org.faucetmc.chat.nbt.type.NbtTagType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NbtDoubleSerializer extends NbtSerializer<NbtTagDouble> {

	@Override
	public void serialize(OutputStream out, NbtTagDouble value) throws IOException {
		long v = Double.doubleToLongBits(value.getPayload());
		out.write((byte) (v >>> 56));
		out.write((byte) (v >>> 48));
		out.write((byte) (v >>> 40));
		out.write((byte) (v >>> 32));
		out.write((byte) (v >>> 24));
		out.write((byte) (v >>> 16));
		out.write((byte) (v >>> 8));
		out.write((byte) (v));
	}

	@Override
	public NbtTagDouble deserialize(InputStream in, boolean hasName) throws IOException {
		long read = (((long) in.read() << 56) +
				((long) (in.read() & 255) << 48) +
				((long) (in.read() & 255) << 40) +
				((long) (in.read() & 255) << 32) +
				((long) (in.read() & 255) << 24) +
				((in.read() & 255) << 16) +
				((in.read() & 255) << 8) +
				((in.read() & 255)));
		return new NbtTagDouble(Double.longBitsToDouble(read));
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_DOUBLE;
	}
}
