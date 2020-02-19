package org.faucetmc.network.packet;

import org.faucetmc.network.utils.PacketInStream;
import org.faucetmc.network.utils.PacketOutStream;

import java.io.IOException;

public interface InboundPacket extends Packet {
    void read(PacketInStream in) throws IOException;
    void handle();
}
