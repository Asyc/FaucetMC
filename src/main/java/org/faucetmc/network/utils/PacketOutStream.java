package org.faucetmc.network.utils;

import io.netty.buffer.ByteBuf;
import org.faucetmc.network.type.VarInteger;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PacketOutStream extends OutputStream {

    private ByteBuf out;

    @Override
    public void write(int b) {
        out.writeByte(b);
    }

    public void writeByte(byte b) {
        out.writeByte(b);
    }

    public void writeShort(short s) {
        out.writeShort(s);
    }

    public void writeShort(int s) {
        out.writeShort(s);
    }

    public void writeInt(int i) {
        out.writeInt(i);
    }

    public void writeVarInt(int i) {
        VarInteger.write(out, i);
    }

    public void writeLong(long l) {
        out.writeLong(l);
    }

    public void writeString(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        writeVarInt(bytes.length);
        out.writeBytes(bytes);
    }

    public ByteBuf getOut() {
        return out;
    }

    public void setOut(ByteBuf out) {
        this.out = out;
    }
}
