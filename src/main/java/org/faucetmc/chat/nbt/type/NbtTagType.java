package org.faucetmc.chat.nbt.type;

import org.faucetmc.chat.nbt.tag.NbtTag;
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

public enum NbtTagType {
	TAG_END(null),
	TAG_BYTE(NbtTagByte.class),
	TAG_SHORT(NbtTagShort.class),
	TAG_INT(NbtTagInt.class),
	TAG_LONG(NbtTagLong.class),
	TAG_FLOAT(NbtTagFloat.class),
	TAG_DOUBLE(NbtTagDouble.class),
	TAG_BYTE_ARRAY(NbtTagByteArray.class),
	TAG_STRING(NbtTagString.class),
	TAG_LIST(NbtTagList.class),
	TAG_COMPOUND(NbtTagCompound.class),
	TAG_INT_ARRAY(NbtTagIntArray.class),
	TAG_LONG_ARRAY(NbtTagLongArray.class);

	Class<? extends NbtTag<?>> classType;

	NbtTagType(Class<? extends NbtTag<?>> classType) {
		this.classType = classType;
	}

	public int getID() {
		return this.ordinal();
	}

	public Class<? extends NbtTag<?>> getClassType() {
		return classType;
	}
}
