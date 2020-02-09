package org.faucetmc.chat.nbt.tag.impl;

import org.faucetmc.chat.nbt.tag.NbtTag;
import org.faucetmc.chat.nbt.type.NbtTagType;

public class NbtTagFloat extends NbtTag<Float> {

	public NbtTagFloat(float value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_FLOAT;
	}
}
