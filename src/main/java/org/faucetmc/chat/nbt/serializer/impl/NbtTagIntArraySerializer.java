package org.faucetmc.chat.nbt.serializer.impl;

import org.faucetmc.chat.nbt.serializer.NbtSerializer;
import org.faucetmc.chat.nbt.tag.impl.NbtTagIntArray;
import org.faucetmc.chat.nbt.type.NbtTagType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NbtTagIntArraySerializer extends NbtSerializer<NbtTagIntArray> {

	@Override
	public void serialize(OutputStream out, NbtTagIntArray value) throws IOException {
		int length = value.getPayload().length;
		out.write((length >>> 24) & 0xFF);
		out.write((length >>> 16) & 0xFF);
		out.write((length >>> 8) & 0xFF);
		out.write((length) & 0xFF);

		for (int element : value.getPayload()) {
			out.write((element >>> 24) & 0xFF);
			out.write((element >>> 16) & 0xFF);
			out.write((element >>> 8) & 0xFF);
			out.write((element) & 0xFF);
		}
	}

	@Override
	public NbtTagIntArray deserialize(InputStream in, boolean hasName) throws IOException {
		int length = ((in.read() << 24) + (in.read() << 16) + (in.read() << 8) + (in.read()));
		int[] array = new int[length];

		for (int i = 0; i < length; i++) {
			array[i] = ((in.read() << 24) + (in.read() << 16) + (in.read() << 8) + (in.read()));
		}

		return new NbtTagIntArray(array);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_INT_ARRAY;
	}

}
