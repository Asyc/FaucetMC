package org.faucetmc.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.faucetmc.network.player.PlayerConnection;
import org.faucetmc.network.type.VarInteger;
import org.faucetmc.network.utils.CryptUtils;

import java.util.List;

public class PacketDeFramer extends ByteToMessageDecoder {

    private static final AttributeKey<PlayerConnection> playerConnectionAttributeKey = AttributeKey.valueOf("playerConnection");
    private static final AttributeKey<Integer> packetSizeAttributeKey = AttributeKey.valueOf("packetSize");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        PlayerConnection connection = ctx.channel().attr(playerConnectionAttributeKey).get();
        Attribute<Integer> packetSizeAttribute = ctx.channel().attr(packetSizeAttributeKey);
        Integer packetSize = packetSizeAttribute.get();

        if(connection.isEncryptionEnabled()) {
            if(packetSize == null) {
                byte[] bytes = new byte[Math.min(in.readableBytes(), 5)];
                in.getBytes(in.readerIndex(), bytes);
                packetSize = VarInteger.read(CryptUtils.decryptAES(connection.getSecretKey(), connection.getIvParameterSpec(), bytes));
                packetSizeAttribute.set(packetSize);
            }

            if (in.readableBytes() >= packetSize) {
                byte[] encrypted = new byte[packetSize];
                in.readBytes(encrypted);
                byte[] decrypted = CryptUtils.decryptAES(connection.getSecretKey(), connection.getIvParameterSpec(), encrypted);
                ByteBuf slice = ctx.alloc().buffer(decrypted.length).writeBytes(decrypted);
                slice.skipBytes(VarInteger.getSize(packetSize));
                out.add(slice);
            }
        } else {
            if (packetSize == null) {
                packetSize = VarInteger.read(in);
                packetSizeAttribute.set(packetSize);
            }

            if (in.readableBytes() >= packetSize) {
                ByteBuf slice = in.readBytes(packetSize);
                packetSizeAttribute.set(null);
                out.add(slice);
            }
        }
    }

}
