package org.faucetmc.chat.nbt.serializer.impl;

import org.faucetmc.chat.nbt.serializer.NbtSerializer;
import org.faucetmc.chat.nbt.tag.impl.NbtTagInt;
import org.faucetmc.chat.nbt.type.NbtTagType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NbtIntSerializer extends NbtSerializer<NbtTagInt> {

	@Override
	public void serialize(OutputStream out, NbtTagInt value) throws IOException {
		int v = value.getPayload();
		out.write((v >>> 24) & 0xFF);
		out.write((v >>> 16) & 0xFF);
		out.write((v >>> 8) & 0xFF);
		out.write((v) & 0xFF);
	}

	@Override
	public NbtTagInt deserialize(InputStream in, boolean hasName) throws IOException {
		return new NbtTagInt(((in.read() << 24) + (in.read() << 16) + (in.read() << 8) + (in.read())));
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_INT;
	}
}
