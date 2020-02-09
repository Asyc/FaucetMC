package org.faucetmc.nbt.tag.impl;

import org.faucetmc.nbt.tag.NbtTag;
import org.faucetmc.nbt.type.NbtTagType;

public class NbtTagDouble extends NbtTag<Double> {

	public NbtTagDouble(double value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_DOUBLE;
	}
}
