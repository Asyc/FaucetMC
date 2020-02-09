package org.faucetmc.chat.nbt.tag.impl;

import org.faucetmc.chat.nbt.tag.NbtTag;
import org.faucetmc.chat.nbt.type.NbtTagType;

public class NbtTagDouble extends NbtTag<Double> {

	public NbtTagDouble(double value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_DOUBLE;
	}
}
