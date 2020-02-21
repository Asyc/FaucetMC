package org.faucetmc.network.packet.impl.outbound.login;

import org.faucetmc.chat.component.impl.ChatComponentText;
import org.faucetmc.network.packet.abstraction.OutboundPacket;
import org.faucetmc.network.utils.PacketOutStream;
import org.faucetmc.server.Faucet;

public class PacketOutLoginDisconnect implements OutboundPacket {

    private ChatComponentText component;

    public PacketOutLoginDisconnect(ChatComponentText component) {
        this.component = component;
    }

    @Override
    public void write(PacketOutStream out) {
        out.writeString(Faucet.GSON.toJson(component));
    }

    @Override
    public int getPacketID() {
        return 0x00;
    }
}
