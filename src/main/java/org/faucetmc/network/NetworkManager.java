package org.faucetmc.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.Logger;
import org.faucetmc.server.Faucet;

import java.io.IOException;
import java.net.InetAddress;

public class NetworkManager {

    private static final Logger logger = Faucet.getLogger();

    private static EventLoopGroup bossGroup = new NioEventLoopGroup(2);
    private static EventLoopGroup workerGroup = new NioEventLoopGroup(Faucet.getInstance().getProperties().getNioThreads());

    public void start(InetAddress ip, int port) throws IOException {
        logger.info("Starting Server [{}:{}]", ip.getHostAddress(), port);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);

    }

}
