package org.faucetmc.chat.nbt.tag;

import org.faucetmc.chat.nbt.tag.impl.NbtTagByte;
import org.faucetmc.chat.nbt.tag.impl.NbtTagByteArray;
import org.faucetmc.chat.nbt.tag.impl.NbtTagCompound;
import org.faucetmc.chat.nbt.tag.impl.NbtTagDouble;
import org.faucetmc.chat.nbt.tag.impl.NbtTagFloat;
import org.faucetmc.chat.nbt.tag.impl.NbtTagInt;
import org.faucetmc.chat.nbt.tag.impl.NbtTagIntArray;
import org.faucetmc.chat.nbt.tag.impl.NbtTagList;
import org.faucetmc.chat.nbt.tag.impl.NbtTagLong;
import org.faucetmc.chat.nbt.tag.impl.NbtTagLongArray;
import org.faucetmc.chat.nbt.tag.impl.NbtTagShort;
import org.faucetmc.chat.nbt.tag.impl.NbtTagString;
import org.faucetmc.chat.nbt.type.NbtTagType;

public abstract class NbtTag<T> {

	protected T payload;

	public NbtTag(T value) {
		this.payload = value;
	}

	public abstract NbtTagType getType();

	public T getPayload() {
		return payload;
	}

	public NbtTagByte asByteTag() {
		return (NbtTagByte) this;
	}

	public NbtTagShort asShortTag() {
		return (NbtTagShort) this;
	}

	public NbtTagInt asIntTag() {
		return (NbtTagInt) this;
	}

	public NbtTagLong asLongTag() {
		return (NbtTagLong) this;
	}

	public NbtTagFloat asFloatTag() {
		return (NbtTagFloat) this;
	}

	public NbtTagDouble asDoubleTag() {
		return (NbtTagDouble) this;
	}

	public NbtTagByteArray asByteArrayTag() {
		return (NbtTagByteArray) this;
	}

	public NbtTagString asStringTag() {
		return (NbtTagString) this;
	}

	public NbtTagList asListTag() {
		return (NbtTagList) this;
	}

	public NbtTagCompound asCompoundTag() {
		return (NbtTagCompound) this;
	}

	public NbtTagIntArray asIntArrayTag() {
		return (NbtTagIntArray) this;
	}

	public NbtTagLongArray asLongArrayTag() {
		return (NbtTagLongArray) this;
	}

}
