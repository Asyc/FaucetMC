package org.faucetmc.network.packet;

import org.faucetmc.network.utils.PacketInStream;
import org.faucetmc.network.utils.PacketOutStream;

import java.io.IOException;

public interface OutboundPacket extends Packet {
    void write(PacketOutStream out);
}
