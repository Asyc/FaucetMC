package org.faucetmc.nbt.tag.impl;

import org.faucetmc.nbt.tag.NbtTag;
import org.faucetmc.nbt.type.NbtTagType;

public class NbtTagByte extends NbtTag<Byte> {

	public NbtTagByte(byte value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_BYTE;
	}
}
