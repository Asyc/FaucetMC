package org.faucetmc.network.packet.impl.inbound.handshake;

import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import org.apache.logging.log4j.Logger;
import org.faucetmc.chat.component.impl.ChatComponentText;
import org.faucetmc.network.NetworkManager;
import org.faucetmc.network.packet.abstraction.InboundPacket;
import org.faucetmc.network.packet.impl.outbound.login.PacketOutLoginDisconnect;
import org.faucetmc.network.player.PlayerConnection;
import org.faucetmc.network.utils.PacketInStream;
import org.faucetmc.server.Faucet;

public class PacketInHandshake implements InboundPacket {

    private static final Logger logger = Faucet.getLogger();

    private static final AttributeKey<String> IP_ATTRIBUTE_KEY = AttributeKey.valueOf("loginIP");
    private static final AttributeKey<Integer> PORT_ATTRIBUTE_KEY = AttributeKey.valueOf("loginPort");

    private static final ChatComponentText OUTDATED_SERVER = new ChatComponentText("Outdated server! I\'m still on 1.7.10");
    private static final ChatComponentText OUTDATED_CLIENT = new ChatComponentText("Outdated client! Please use 1.7.10");

    @Override
    public void onPacketReceived(PlayerConnection connection, PacketInStream in) {
        int protocol = in.readVarInt();
        String ip = in.readString();
        int port = Short.toUnsignedInt(in.readShort());
        int state = in.readVarInt();

        switch (state) {
            case 1:
                connection.setConnectionState(PlayerConnection.ConnectionState.QUERY);
                break;
            case 2:
                SocketChannel channel = connection.getChannel();
                logger.info("Login Started [User IP: {}]", channel.remoteAddress().getAddress().getHostAddress());
                if (protocol > NetworkManager.PROTOCOL) {
                    connection.writePacketImmediately(new PacketOutLoginDisconnect(OUTDATED_SERVER));
                    logger.info("Login Disconnect [User IP: {}]: Client version ahead of server version", channel.remoteAddress().getAddress().getHostAddress());
                    channel.close();
                } else if (protocol < NetworkManager.PROTOCOL) {
                    connection.writePacketImmediately(new PacketOutLoginDisconnect(OUTDATED_CLIENT));
                    logger.info("Login Disconnect [User IP: {}]: Client version behind of server version", channel.remoteAddress().getAddress().getHostAddress());
                    channel.close();
                } else {
                    channel.attr(IP_ATTRIBUTE_KEY).set(ip);
                    channel.attr(PORT_ATTRIBUTE_KEY).set(port);
                    connection.setConnectionState(PlayerConnection.ConnectionState.LOGIN);
                }
                break;
        }
    }

    @Override
    public int getPacketID() {
        return 0x00;
    }


}
