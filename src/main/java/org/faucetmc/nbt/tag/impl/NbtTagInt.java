package org.faucetmc.nbt.tag.impl;

import org.faucetmc.nbt.tag.NbtTag;
import org.faucetmc.nbt.type.NbtTagType;

public class NbtTagInt extends NbtTag<Integer> {

    public NbtTagInt(Integer value) {
        super(value);
    }

    @Override
    public NbtTagType getType() {
        return NbtTagType.TAG_INT;
    }
}
