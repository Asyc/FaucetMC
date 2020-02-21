package org.faucetmc.network.packet.impl.inbound.login;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.netty.util.AttributeKey;
import org.faucetmc.chat.component.impl.ChatComponentText;
import org.faucetmc.network.NetworkManager;
import org.faucetmc.network.packet.abstraction.InboundPacket;
import org.faucetmc.network.packet.impl.outbound.login.PacketOutLoginDisconnect;
import org.faucetmc.network.packet.impl.outbound.login.PacketOutLoginSuccess;
import org.faucetmc.network.packet.impl.outbound.play.PacketOutJoinGame;
import org.faucetmc.network.player.PlayerConnection;
import org.faucetmc.network.utils.CryptUtils;
import org.faucetmc.network.utils.HttpUtils;
import org.faucetmc.network.utils.MojangLoginResponseJson;
import org.faucetmc.network.utils.PacketInStream;
import org.faucetmc.server.Faucet;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class PacketInEncryptionResponse implements InboundPacket {

    private static final AttributeKey<byte[]> ATTRIBUTE_KEY_VERIFY_TOKEN = AttributeKey.valueOf("verifyToken");
    private static final AttributeKey<String> ATTRIBUTE_KEY_NAME = AttributeKey.valueOf("name");
    private static final AttributeKey<String> ATTRIBUTE_KEY_SERVER_ID = AttributeKey.valueOf("serverID");
    private static final AttributeKey<byte[]> ATTRIBUTE_KEY_PUBLIC_KEY = AttributeKey.valueOf("rsaPublicKey");

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onPacketReceived(PlayerConnection connection, PacketInStream in) {
        byte[] serverToken = connection.getChannel().attr(ATTRIBUTE_KEY_VERIFY_TOKEN).getAndSet(null);

        short secretLength = in.readShort();
        byte[] secretKey = new byte[128];
        in.read(secretKey);
        secretKey = CryptUtils.decryptRSA(NetworkManager.RSA_KEYPAIR.getPrivate(), secretKey);

        short verifyLength = in.readShort();
        byte[] verifyToken = new byte[128];
        in.read(verifyToken);
        verifyToken = CryptUtils.decryptRSA(NetworkManager.RSA_KEYPAIR.getPrivate(), verifyToken);

        if(Arrays.equals(serverToken, verifyToken)) {
            connection.setEncryptionEnabled(new SecretKeySpec(secretKey, "AES"), new IvParameterSpec(secretKey));

            String serverID = connection.getChannel().attr(ATTRIBUTE_KEY_SERVER_ID).get();
            String name = connection.getChannel().attr(ATTRIBUTE_KEY_NAME).get();
            byte[] rsaPubKey = connection.getChannel().attr(ATTRIBUTE_KEY_PUBLIC_KEY).get();

            BigInteger hash = CryptUtils.toBigIntegerSHA1(serverID.getBytes(StandardCharsets.US_ASCII), secretKey, rsaPubKey);

            String url = "https://sessionserver.mojang.com/session/minecraft/hasJoined?username=" + name + "&serverId=" + hash.toString(16);
            MojangLoginResponseJson response = Faucet.GSON.fromJson(HttpUtils.httpGET(url), MojangLoginResponseJson.class);
            connection.writePacketImmediately(new PacketOutLoginSuccess(stringToUUID(response.getID()), response.getName()));
            connection.writePacketImmediately(new PacketOutJoinGame());
        } else {
            connection.writePacketImmediately(new PacketOutLoginDisconnect(new ChatComponentText("Encryption Error")));
            connection.getChannel().close();
        }
    }

    @Override
    public int getPacketID() {
        return 0x01;
    }

    private static UUID stringToUUID(String uuid) {
        StringBuilder sb = new StringBuilder(uuid.length() + 4).append(uuid)
        .insert(8, "-")
        .insert(13, "-")
        .insert(18, "-")
        .insert(23, "-");
        return UUID.fromString(sb.toString());
    }
}
