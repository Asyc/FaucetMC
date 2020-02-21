package org.faucetmc.network.packet.impl.outbound.play;

import org.faucetmc.network.packet.abstraction.InboundPacket;
import org.faucetmc.network.packet.abstraction.OutboundPacket;
import org.faucetmc.network.player.PlayerConnection;
import org.faucetmc.network.utils.PacketInStream;
import org.faucetmc.network.utils.PacketOutStream;

public class PacketOutKeepAlive implements OutboundPacket {

    private static final int KEEP_ALIVE_CONSTANT = 42069;

    private int keepAliveID;

    public PacketOutKeepAlive() {
        this(KEEP_ALIVE_CONSTANT);
    }

    public PacketOutKeepAlive(int keepAliveID) {
        this.keepAliveID = keepAliveID;
    }

    @Override
    public void write(PacketOutStream out) {
        out.writeInt(42069);
    }

    @Override
    public int getPacketID() {
        return 0x00;
    }
}
