package org.faucetmc.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.faucetmc.network.packet.abstraction.InboundPacket;
import org.faucetmc.network.player.PlayerConnection;
import org.faucetmc.network.type.VarInteger;
import org.faucetmc.network.utils.CryptUtils;
import org.faucetmc.network.utils.Packets;

@ChannelHandler.Sharable
public class PacketDeserializer extends ChannelInboundHandlerAdapter {

    private static final AttributeKey<PlayerConnection> ATTRIBUTE_KEY_PLAYER_CONNECTION = AttributeKey.valueOf("playerConnection");
    private static final AttributeKey<Integer> ATTRIBUTE_KEY_PACKET_SIZE = AttributeKey.valueOf("packetSize");
    private static final AttributeKey<ByteBuf> ATTRIBUTE_KEY_PACKET_UNREAD = AttributeKey.valueOf("packetBuffer");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        Attribute<ByteBuf> unreadAttribute = ctx.channel().attr(ATTRIBUTE_KEY_PACKET_UNREAD);
        ByteBuf unread = unreadAttribute.getAndSet(null);

        if (unread != null) {
            int length = in.readableBytes() + unread.readableBytes();
            ByteBuf newIn = ctx.alloc().buffer(length, length);
            newIn.writeBytes(unread);
            newIn.writeBytes(in);
            in = newIn;
        }

        while (in.readableBytes() > 0) {
            if(!decodePacket(ctx, in)) {
                unreadAttribute.set(in);
                break;
            }
        }

        if(unreadAttribute.get() == null) in.release(in.refCnt());
    }

    private boolean decodePacket(ChannelHandlerContext ctx, ByteBuf in) {
        PlayerConnection connection = ctx.channel().attr(ATTRIBUTE_KEY_PLAYER_CONNECTION).get();
        Attribute<Integer> packetSizeAttribute = ctx.channel().attr(ATTRIBUTE_KEY_PACKET_SIZE);

        if (packetSizeAttribute.get() == null) {
            int length = readLength(connection, in);
            if (length == -1) return false;
            packetSizeAttribute.set(length);
        }

        int packetLength = packetSizeAttribute.get();

        if (in.readableBytes() < packetLength) return false;

        ByteBuf slice;
        if (connection.isEncryptionEnabled()) {
            int packetLengthByteSize = VarInteger.getSize(packetLength);
            byte[] bytes = new byte[packetLength + packetLengthByteSize];
            in.readBytes(bytes);
            bytes = CryptUtils.decryptAES(connection.getSecretKey(), connection.getIvParameterSpec(), bytes);
            slice = ctx.alloc().buffer(bytes.length, bytes.length).skipBytes(packetLengthByteSize);
        } else {
            slice = in.readBytes(packetLength);
        }

        int packetID = VarInteger.read(slice);
        InboundPacket packet = null;
        switch (connection.getConnectionState()) {
            case HANDSHAKE:
                if (packetID != 0) throw new RuntimeException("PacketID must be 0");
                packet = Packets.Handshake.HANDSHAKE.getInstance();
                break;
            case LOGIN:
                packet = Packets.Login.values()[packetID].getInstance();
                break;
            case QUERY:
                packet = Packets.Query.values()[packetID].getInstance();
                break;
            case PLAY:
                packet = Packets.Play.values()[packetID].getInstance();
                break;
        }
        connection.getInBuffer().setIn(slice);
        packet.onPacketReceived(connection, connection.getInBuffer());
        slice.release(slice.refCnt());
        packetSizeAttribute.set(null);
        return true;
    }

    private int readLength(PlayerConnection connection, ByteBuf in) {
        int readableBytes = Math.min(in.readableBytes(), 5);
        try {
            int packetLength;
            if (connection.isEncryptionEnabled()) {
                byte[] bytes = new byte[readableBytes];
                in.getBytes(in.readerIndex(), bytes);
                bytes = CryptUtils.decryptAES(connection.getSecretKey(), connection.getIvParameterSpec(), bytes);
                packetLength = VarInteger.read(bytes);
            } else {
                byte[] bytes = new byte[readableBytes];
                in.getBytes(in.readerIndex(), bytes);
                packetLength = VarInteger.read(bytes);
                in.skipBytes(VarInteger.getSize(packetLength));
            }
            return packetLength;
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }
}
