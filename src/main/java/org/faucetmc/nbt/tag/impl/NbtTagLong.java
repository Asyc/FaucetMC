package org.faucetmc.nbt.tag.impl;

import org.faucetmc.nbt.tag.NbtTag;
import org.faucetmc.nbt.type.NbtTagType;

public class NbtTagLong extends NbtTag<Long> {

	public NbtTagLong(long value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_LONG;
	}
}
