package org.faucetmc.chat.nbt.tag.impl;

import org.faucetmc.chat.nbt.tag.NbtTag;
import org.faucetmc.chat.nbt.type.NbtTagType;

public class NbtTagByteArray extends NbtTag<byte[]> {

	public NbtTagByteArray(byte[] value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_BYTE_ARRAY;
	}
}
