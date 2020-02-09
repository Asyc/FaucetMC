package org.faucetmc.nbt.serializer;

import org.faucetmc.nbt.tag.NbtTag;
import org.faucetmc.nbt.type.NbtTagType;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class NbtSerializer<T extends NbtTag<?>> {

    public abstract void serialize(OutputStream out, T value) throws IOException;

    public abstract T deserialize(InputStream in, boolean hasName) throws IOException;

    public abstract NbtTagType getType();

    public final String readTagName(InputStream in) throws IOException {
        int length = ((in.read() << 8) + (in.read()));
        if (length == 0) return "";
        byte[] bytes = new byte[length];
        if (in.read(bytes) == -1) throw new EOFException();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public void writeTagName(OutputStream out, String key) throws IOException {
        int strLength = key.length();
        out.write((strLength >>> 8) & 0xFF);
        out.write((strLength) & 0xFF);
        out.write(key.getBytes(StandardCharsets.UTF_8));
    }

    public void writeTagType(OutputStream out) throws IOException {
        out.write(getType().getID());
    }

}
