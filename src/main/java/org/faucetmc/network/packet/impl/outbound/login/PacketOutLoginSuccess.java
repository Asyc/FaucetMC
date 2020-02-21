package org.faucetmc.network.packet.impl.outbound.login;

import org.faucetmc.network.packet.abstraction.OutboundPacket;
import org.faucetmc.network.utils.PacketOutStream;

import java.util.UUID;

public class PacketOutLoginSuccess implements OutboundPacket {

    private UUID uuid;
    private String username;

    public PacketOutLoginSuccess(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public void write(PacketOutStream out) {
        out.writeString(uuid.toString());
        out.writeString(username);
    }

    @Override
    public int getPacketID() {
        return 0x02;
    }
}
