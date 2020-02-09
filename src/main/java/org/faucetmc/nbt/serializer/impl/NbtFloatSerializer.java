package org.faucetmc.nbt.serializer.impl;

import org.faucetmc.nbt.serializer.NbtSerializer;
import org.faucetmc.nbt.tag.impl.NbtTagFloat;
import org.faucetmc.nbt.type.NbtTagType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NbtFloatSerializer extends NbtSerializer<NbtTagFloat> {

	@Override
	public void serialize(OutputStream out, NbtTagFloat value) throws IOException {
		int v = Float.floatToIntBits(value.getPayload());
		out.write((v >>> 24) & 0xFF);
		out.write((v >>> 16) & 0xFF);
		out.write((v >>> 8) & 0xFF);
		out.write((v) & 0xFF);
	}

	@Override
	public NbtTagFloat deserialize(InputStream in, boolean hasName) throws IOException {
		int read = ((in.read() << 24) + (in.read() << 16) + (in.read() << 8) + (in.read()));
		return new NbtTagFloat(Float.intBitsToFloat(read));
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_FLOAT;
	}
}
