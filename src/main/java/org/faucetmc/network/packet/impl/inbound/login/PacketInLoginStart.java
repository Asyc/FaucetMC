package org.faucetmc.network.packet.impl.inbound.login;

import io.netty.util.AttributeKey;
import org.faucetmc.network.NetworkManager;
import org.faucetmc.network.packet.abstraction.InboundPacket;
import org.faucetmc.network.packet.impl.outbound.login.PacketOutEncryptionRequest;
import org.faucetmc.network.player.PlayerConnection;
import org.faucetmc.network.utils.PacketInStream;

import java.util.Random;

public class PacketInLoginStart implements InboundPacket {

    private static final AttributeKey<String> ATTRIBUTE_KEY_NAME = AttributeKey.valueOf("name");
    private static final Random RANDOM = new Random();

    @Override
    public void onPacketReceived(PlayerConnection connection, PacketInStream in) {
        String name = in.readString();
        connection.getChannel().attr(ATTRIBUTE_KEY_NAME).set(name);

        byte[] verifyToken = new byte[32];
        RANDOM.nextBytes(verifyToken);

        connection.writePacketImmediately(new PacketOutEncryptionRequest("", NetworkManager.RSA_KEYPAIR.getPublic(), verifyToken));
    }

    @Override
    public int getPacketID() {
        return 0x00;
    }
}
