package org.faucetmc.chat.nbt.tag.impl;

import org.faucetmc.chat.nbt.tag.NbtTag;
import org.faucetmc.chat.nbt.type.NbtTagType;

public class NbtTagIntArray extends NbtTag<int[]> {

	public NbtTagIntArray(int[] value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_INT_ARRAY;
	}
}
