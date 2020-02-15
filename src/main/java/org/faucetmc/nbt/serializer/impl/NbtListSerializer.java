package org.faucetmc.nbt.serializer.impl;

import org.faucetmc.nbt.NbtParser;
import org.faucetmc.nbt.serializer.NbtSerializer;
import org.faucetmc.nbt.tag.NbtTag;
import org.faucetmc.nbt.tag.impl.NbtTagList;
import org.faucetmc.nbt.type.NbtTagType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class NbtListSerializer extends NbtSerializer<NbtTagList> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void serialize(OutputStream out, NbtTagList value) throws IOException {
        int type = value.getPayload().get(0).getType().getID();
        out.write((type >>> 24) & 0xFF);
        out.write((type >>> 16) & 0xFF);
        out.write((type >>> 8) & 0xFF);
        out.write((type) & 0xFF);

        NbtSerializer serializer = NbtParser.getSerializer(type);
        for (NbtTag<?> tag : value.getPayload()) {
            serializer.serialize(out, tag);
        }
    }

    @Override
    public NbtTagList deserialize(InputStream in, boolean hasName) throws IOException {
        int type = in.read();
        int length = ((in.read() << 24) + (in.read() << 16) + (in.read() << 8) + (in.read()));
        if(length <= 0) return new NbtTagList(new LinkedList<>());
        NbtSerializer<?> serializer = NbtParser.getSerializer(type);
        List<NbtTag<?>> tags = new LinkedList<>();

        for (int i = 0; i < length; i++) {
            tags.add(serializer.deserialize(in, false));
        }

        return new NbtTagList(tags);
    }

    @Override
    public NbtTagType getType() {
        return NbtTagType.TAG_LIST;
    }
}
