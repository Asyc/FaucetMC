package org.faucetmc.network.utils;

import io.netty.buffer.ByteBuf;
import org.faucetmc.network.type.VarInteger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PacketInStream extends InputStream {

    private ByteBuf in;

    @Override
    public int read() throws IOException {
        return Byte.toUnsignedInt(in.readByte());
    }

    public byte readByte() {
        return in.readByte();
    }

    public short readShort() {
        return in.readShort();
    }

    public int readInt() {
        return in.readInt();
    }

    public int readVarInt() {
        return VarInteger.read(in);
    }

    public float readLong() {
        return in.readLong();
    }

    public float readFloat() {
        return in.readFloat();
    }

    public double readDouble() {
        return in.readDouble();
    }

    public String readString() {
        int length = readVarInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public ByteBuf getIn() {
        return in;
    }

    public void setIn(ByteBuf in) {
        this.in = in;
    }
}
