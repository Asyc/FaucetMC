package org.faucetmc.nbt.tag.impl;

import org.faucetmc.nbt.tag.NbtTag;
import org.faucetmc.nbt.type.NbtTagType;

public class NbtTagIntArray extends NbtTag<int[]> {

	public NbtTagIntArray(int[] value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_INT_ARRAY;
	}
}
