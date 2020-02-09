package org.faucetmc.chat.nbt.tag.impl;

import org.faucetmc.chat.nbt.tag.NbtTag;
import org.faucetmc.chat.nbt.type.NbtTagType;

public class NbtTagLongArray extends NbtTag<long[]> {

	public NbtTagLongArray(long[] value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_LONG_ARRAY;
	}
}
