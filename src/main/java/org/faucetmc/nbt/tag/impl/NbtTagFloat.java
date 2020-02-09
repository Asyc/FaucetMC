package org.faucetmc.nbt.tag.impl;

import org.faucetmc.nbt.tag.NbtTag;
import org.faucetmc.nbt.type.NbtTagType;

public class NbtTagFloat extends NbtTag<Float> {

	public NbtTagFloat(float value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_FLOAT;
	}
}
