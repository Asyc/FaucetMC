package org.faucetmc.network.packet.abstraction;

import org.faucetmc.network.utils.PacketOutStream;

public interface OutboundPacket extends Packet {
    void write(PacketOutStream out);
}
