package org.faucetmc.chat.nbt.tag.impl;

import org.faucetmc.chat.nbt.tag.NbtTag;
import org.faucetmc.chat.nbt.type.NbtTagType;

public class NbtTagString extends NbtTag<String> {

	public NbtTagString(String value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_STRING;
	}
}
