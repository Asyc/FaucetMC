package org.faucetmc.nbt.serializer.impl;

import org.faucetmc.nbt.serializer.NbtSerializer;
import org.faucetmc.nbt.tag.impl.NbtTagShort;
import org.faucetmc.nbt.type.NbtTagType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NbtShortSerializer extends NbtSerializer<NbtTagShort> {

    @Override
    public void serialize(OutputStream out, NbtTagShort value) throws IOException {
        out.write((value.getPayload() >>> 8) & 0xFF);
        out.write((value.getPayload()) & 0xFF);
    }

    @Override
    public NbtTagShort deserialize(InputStream in, boolean hasName) throws IOException {
        return new NbtTagShort((short) ((in.read() << 8) + (in.read())));
    }

    @Override
    public NbtTagType getType() {
        return NbtTagType.TAG_SHORT;
    }
}
