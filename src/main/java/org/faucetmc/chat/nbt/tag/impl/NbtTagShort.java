package org.faucetmc.chat.nbt.tag.impl;

import org.faucetmc.chat.nbt.tag.NbtTag;
import org.faucetmc.chat.nbt.type.NbtTagType;

public class NbtTagShort extends NbtTag<Short> {

	public NbtTagShort(short value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_SHORT;
	}
}
