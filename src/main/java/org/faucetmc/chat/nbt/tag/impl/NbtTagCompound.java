package org.faucetmc.chat.nbt.tag.impl;

import org.faucetmc.chat.nbt.tag.NbtTag;
import org.faucetmc.chat.nbt.type.NbtTagType;

import java.util.HashMap;

public class NbtTagCompound extends NbtTag<HashMap<String, NbtTag<?>>> {

	public NbtTagCompound() {
		this(new HashMap<>());
	}

	public NbtTagCompound(HashMap<String, NbtTag<?>> value) {
		super(value);
	}

	public void addTag(String key, NbtTag<?> value) {
		this.payload.put(key, value);
	}

	public NbtTag<?> getTag(String key) {
		return this.payload.get(key);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_COMPOUND;
	}
}
