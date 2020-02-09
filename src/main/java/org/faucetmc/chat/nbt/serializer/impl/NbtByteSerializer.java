package org.faucetmc.chat.nbt.serializer.impl;

import org.faucetmc.chat.nbt.serializer.NbtSerializer;
import org.faucetmc.chat.nbt.tag.impl.NbtTagByte;
import org.faucetmc.chat.nbt.type.NbtTagType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NbtByteSerializer extends NbtSerializer<NbtTagByte> {

	@Override
	public void serialize(OutputStream out, NbtTagByte value) throws IOException {
		out.write(value.getPayload());
	}

	@Override
	public NbtTagByte deserialize(InputStream in, boolean hasName) throws IOException {
		return new NbtTagByte((byte) in.read());
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_BYTE;
	}
}
