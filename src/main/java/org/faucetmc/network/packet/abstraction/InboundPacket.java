package org.faucetmc.network.packet.abstraction;

import org.faucetmc.network.player.PlayerConnection;
import org.faucetmc.network.utils.PacketInStream;

public interface InboundPacket extends Packet {
    void onPacketReceived(PlayerConnection connection, PacketInStream in);
}
