package org.faucetmc.chat.nbt.serializer.impl;

import org.faucetmc.chat.nbt.serializer.NbtSerializer;
import org.faucetmc.chat.nbt.tag.impl.NbtTagString;
import org.faucetmc.chat.nbt.type.NbtTagType;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class NbtStringSerializer extends NbtSerializer<NbtTagString> {

	@Override
	public void serialize(OutputStream out, NbtTagString value) throws IOException {
		int strLength = value.getPayload().length();
		out.write((strLength >>> 8) & 0xFF);
		out.write((strLength) & 0xFF);
		out.write(value.getPayload().getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public NbtTagString deserialize(InputStream in, boolean hasName) throws IOException {
		int length = ((in.read() << 8) + (in.read()));
		byte[] bytes = new byte[length];
		if (in.read(bytes) == -1) throw new EOFException();
		return new NbtTagString(new String(bytes, StandardCharsets.UTF_8));
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_STRING;
	}
}
