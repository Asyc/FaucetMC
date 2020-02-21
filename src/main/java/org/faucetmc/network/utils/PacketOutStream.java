package org.faucetmc.network.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.faucetmc.network.type.VarInteger;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PacketOutStream extends OutputStream {

    private ByteBuf out;
    private Channel channel;

    @Override
    public void write(int b) {
        out.writeByte(b);
    }

    @Override
    public void write(byte[] b, int off, int len) {
        out.writeBytes(b, off, len);
    }

    @Override
    public void write(byte[] b) {
        write(b, 0, b.length);
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

    public void writeBoolean(boolean b) {
        out.writeBoolean(b);
    }

    public void writeString(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        writeVarInt(bytes.length);
        out.writeBytes(bytes);
    }

    public void writeUUID(UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    public ByteBuf getOut() {
        return out;
    }

    public void setOut(ByteBuf out) {
        this.out = out;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
