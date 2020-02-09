package org.faucetmc.nbt.tag.impl;

import org.faucetmc.nbt.tag.NbtTag;
import org.faucetmc.nbt.type.NbtTagType;

public class NbtTagByteArray extends NbtTag<byte[]> {

	public NbtTagByteArray(byte[] value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_BYTE_ARRAY;
	}
}
