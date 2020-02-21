package org.faucetmc.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import org.apache.logging.log4j.Logger;
import org.faucetmc.network.pipeline.ClientChannelInitializer;
import org.faucetmc.network.player.PlayerConnection;
import org.faucetmc.profiler.Profiler;
import org.faucetmc.server.Faucet;

import java.io.IOException;
import java.net.InetAddress;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class NetworkManager {

    private static final Logger logger = Faucet.getLogger();

    private static EventLoopGroup bossGroup = new NioEventLoopGroup(2);
    private static EventLoopGroup workerGroup = new NioEventLoopGroup(Faucet.getInstance().getProperties().getNioThreads());

    public static final AttributeKey<PlayerConnection> playerConnectionAttributeKey = AttributeKey.valueOf("playerConnection");
    public static final AttributeKey<Integer> packetSizeAttributeKey = AttributeKey.valueOf("packetSize");
    public static final int PROTOCOL = 5;

    public static final KeyPair RSA_KEYPAIR;

    static {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            RSA_KEYPAIR = generator.generateKeyPair();
        } catch (GeneralSecurityException e) {
            logger.fatal("Unable to generate encryption keypair: {}", e.getMessage());
            System.exit(-1);
            throw new RuntimeException(e);
        }
    }

    public void start(InetAddress ip, int port) throws IOException {
        Profiler profiler = new Profiler();
        profiler.beginSection("Network Server Start");
        logger.info("Starting Server [{}:{}]", ip.getHostAddress(), port);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ClientChannelInitializer());
        bootstrap.bind(ip, port).syncUninterruptibly();
        profiler.endSection();
    }

}
