package org.faucetmc.network.packet.impl.outbound.query;

import org.faucetmc.network.packet.abstraction.OutboundPacket;
import org.faucetmc.network.utils.PacketOutStream;

public class PacketOutPing implements OutboundPacket {

    private long time;

    public PacketOutPing(long time) {
        this.time = time;
    }

    @Override
    public void write(PacketOutStream out) {
        out.writeLong(time);
    }

    @Override
    public int getPacketID() {
        return 0x01;
    }
}
