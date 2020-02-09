package org.faucetmc.chat.nbt.tag.impl;

import org.faucetmc.chat.nbt.tag.NbtTag;
import org.faucetmc.chat.nbt.type.NbtTagType;

import java.util.List;

public class NbtTagList extends NbtTag<List<NbtTag<?>>> {

	public NbtTagList(List<NbtTag<?>> value) {
		super(value);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_LIST;
	}
}
