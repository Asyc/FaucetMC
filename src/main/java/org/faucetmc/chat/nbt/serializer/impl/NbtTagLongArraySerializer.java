package org.faucetmc.chat.nbt.serializer.impl;

import org.faucetmc.chat.nbt.serializer.NbtSerializer;
import org.faucetmc.chat.nbt.tag.impl.NbtTagLongArray;
import org.faucetmc.chat.nbt.type.NbtTagType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NbtTagLongArraySerializer extends NbtSerializer<NbtTagLongArray> {

	@Override
	public void serialize(OutputStream out, NbtTagLongArray value) throws IOException {
		int length = value.getPayload().length;
		out.write((length >>> 24) & 0xFF);
		out.write((length >>> 16) & 0xFF);
		out.write((length >>> 8) & 0xFF);
		out.write((length) & 0xFF);

		for (long element : value.getPayload()) {
			out.write((byte) (element >>> 56));
			out.write((byte) (element >>> 48));
			out.write((byte) (element >>> 40));
			out.write((byte) (element >>> 32));
			out.write((byte) (element >>> 24));
			out.write((byte) (element >>> 16));
			out.write((byte) (element >>> 8));
			out.write((byte) (element));
		}

	}

	@Override
	public NbtTagLongArray deserialize(InputStream in, boolean hasName) throws IOException {
		int length = ((in.read() << 24) + (in.read() << 16) + (in.read() << 8) + (in.read()));
		long[] array = new long[length];

		for (int i = 0; i < length; i++) {
			array[i] = (((long) in.read() << 56) +
					((long) (in.read() & 255) << 48) +
					((long) (in.read() & 255) << 40) +
					((long) (in.read() & 255) << 32) +
					((long) (in.read() & 255) << 24) +
					((in.read() & 255) << 16) +
					((in.read() & 255) << 8) +
					((in.read() & 255)));
		}

		return new NbtTagLongArray(array);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_LONG_ARRAY;
	}

}
