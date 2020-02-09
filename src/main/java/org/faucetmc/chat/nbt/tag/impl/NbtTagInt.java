package org.faucetmc.chat.nbt.tag.impl;

import org.faucetmc.chat.nbt.tag.NbtTag;
import org.faucetmc.chat.nbt.type.NbtTagType;

public class NbtTagInt extends NbtTag<Integer> {

	public NbtTagInt(Integer value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_INT;
	}
}
