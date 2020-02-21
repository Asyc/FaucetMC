package org.faucetmc.network.pipeline;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.faucetmc.network.NetworkManager;
import org.faucetmc.network.player.PlayerConnection;

@ChannelHandler.Sharable
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final PacketDeserializer deserializer = new PacketDeserializer();
    private static final PacketSerializer serializer = new PacketSerializer();

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.attr(NetworkManager.playerConnectionAttributeKey).set(new PlayerConnection(ch));
        ch.pipeline()
                .addLast("Timeout", new ReadTimeoutHandler(20))
                .addLast("Serializer", serializer)
                .addLast("Deserializer", deserializer);
    }

}
