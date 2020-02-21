package org.faucetmc.network.packet.impl.outbound.login;

import io.netty.util.AttributeKey;
import org.faucetmc.network.packet.abstraction.OutboundPacket;
import org.faucetmc.network.utils.PacketOutStream;

import java.security.Key;
import java.security.PublicKey;

public class PacketOutEncryptionRequest implements OutboundPacket {

    private static final AttributeKey<byte[]> ATTRIBUTE_KEY_VERIFY_TOKEN = AttributeKey.valueOf("verifyToken");
    private static final AttributeKey<String> ATTRIBUTE_KEY_SERVER_ID = AttributeKey.valueOf("serverID");
    private static final AttributeKey<byte[]> ATTRIBUTE_KEY_PUBLIC_KEY = AttributeKey.valueOf("rsaPublicKey");

    private String serverID;
    private Key publicKey;
    private byte[] verifyToken;

    public PacketOutEncryptionRequest(String serverID, PublicKey publicKey, byte[] verifyToken) {
        this.serverID = serverID;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }

    @Override
    public void write(PacketOutStream out) {
        byte[] publicKey = this.publicKey.getEncoded();
        out.getChannel().attr(ATTRIBUTE_KEY_VERIFY_TOKEN).set(verifyToken);
        out.getChannel().attr(ATTRIBUTE_KEY_SERVER_ID).set(serverID);
        out.getChannel().attr(ATTRIBUTE_KEY_PUBLIC_KEY).set(publicKey);
        out.writeString(serverID);
        out.writeShort((short)publicKey.length);
        out.write(publicKey);
        out.writeShort((short)verifyToken.length);
        out.write(verifyToken);

    }

    @Override
    public int getPacketID() {
        return 0x01;
    }
}
