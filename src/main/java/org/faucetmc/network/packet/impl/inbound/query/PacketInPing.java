package org.faucetmc.network.packet.impl.inbound.query;

import org.faucetmc.network.packet.abstraction.InboundPacket;
import org.faucetmc.network.packet.impl.outbound.query.PacketOutPing;
import org.faucetmc.network.player.PlayerConnection;
import org.faucetmc.network.utils.PacketInStream;

import java.io.IOException;

public class PacketInPing implements InboundPacket {

    @Override
    public void onPacketReceived(PlayerConnection connection, PacketInStream in) {
        connection.writePacketImmediately(new PacketOutPing(in.readLong()));
    }

    @Override
    public int getPacketID() {
        return 0x01;
    }
}
