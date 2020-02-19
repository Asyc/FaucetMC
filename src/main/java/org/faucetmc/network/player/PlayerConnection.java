package org.faucetmc.network.player;

import io.netty.channel.socket.SocketChannel;
import org.faucetmc.network.packet.OutboundPacket;
import org.faucetmc.network.utils.PacketInStream;
import org.faucetmc.network.utils.PacketOutStream;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class PlayerConnection  {

    private SocketChannel channel;

    private boolean encryptionEnabled;
    private SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;

    private PacketOutStream outBuffer;
    private PacketInStream inBuffer;

    private final Object SEND_LOCK = new Object();

    public PlayerConnection(SocketChannel channel) {
        this.channel = channel;
        this.outBuffer = new PacketOutStream();
        this.inBuffer = new PacketInStream();
    }

    public void writeAndFlushPacket(OutboundPacket packet) {
        synchronized (SEND_LOCK) {
            channel.writeAndFlush(packet);
        }
    }

    public boolean isEncryptionEnabled() {
        return encryptionEnabled;
    }

    public void setEncryptionEnabled(SecretKey secretKey, IvParameterSpec parameterSpec) {
        this.encryptionEnabled = true;
        this.secretKey = secretKey;
        this.ivParameterSpec = parameterSpec;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public IvParameterSpec getIvParameterSpec() {
        return ivParameterSpec;
    }

    public PacketOutStream getOutBuffer() {
        return outBuffer;
    }

    public PacketInStream getInBuffer() {
        return inBuffer;
    }
}
