package org.faucetmc.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.faucetmc.network.NetworkManager;
import org.faucetmc.network.packet.abstraction.OutboundPacket;
import org.faucetmc.network.player.PlayerConnection;
import org.faucetmc.network.type.VarInteger;
import org.faucetmc.network.utils.CryptUtils;

@ChannelHandler.Sharable
public class PacketSerializer extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        OutboundPacket packet = (OutboundPacket) msg;
        PlayerConnection connection = ctx.channel().attr(NetworkManager.playerConnectionAttributeKey).get();

        ByteBuf tmp = ctx.alloc().buffer();
        tmp.writerIndex(5);     //Length PrePadding

        connection.getOutBuffer().setOut(tmp);
        connection.getOutBuffer().setChannel(ctx.channel());

        VarInteger.write(tmp, packet.getPacketID());
        packet.write(connection.getOutBuffer());

        int length = tmp.writerIndex() - 5;
        int lengthByteSize = VarInteger.getSize(length);

        tmp.markWriterIndex();
        tmp.writerIndex(5 - lengthByteSize);
        VarInteger.write(tmp, length);
        tmp.resetWriterIndex();

        tmp.readerIndex(5 - lengthByteSize);

        if(connection.isEncryptionEnabled()) {
            byte[] bytes = new byte[tmp.readableBytes()];
            tmp.readBytes(bytes);
            bytes = CryptUtils.encryptAES(connection.getSecretKey(), connection.getIvParameterSpec(), bytes);
            tmp.readerIndex(0);
            tmp.writerIndex(0);
            tmp.writeBytes(bytes);
        }

        super.write(ctx, tmp, promise);
    }
}
