package org.faucetmc.nbt.tag.impl;

import org.faucetmc.nbt.tag.NbtTag;
import org.faucetmc.nbt.type.NbtTagType;

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
