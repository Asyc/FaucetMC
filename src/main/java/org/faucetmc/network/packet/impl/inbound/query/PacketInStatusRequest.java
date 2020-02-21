package org.faucetmc.network.packet.impl.inbound.query;

import org.faucetmc.chat.component.impl.ChatComponentText;
import org.faucetmc.network.NetworkManager;
import org.faucetmc.network.packet.abstraction.InboundPacket;
import org.faucetmc.network.packet.impl.outbound.query.PacketOutStatusResponse;
import org.faucetmc.network.player.PlayerConnection;
import org.faucetmc.network.utils.PacketInStream;
import org.faucetmc.server.Faucet;

import java.io.IOException;

public class PacketInStatusRequest implements InboundPacket {

    @Override
    public void onPacketReceived(PlayerConnection connection, PacketInStream in) {
        connection.writePacketImmediately(new PacketOutStatusResponse("FaucetMC", NetworkManager.PROTOCOL, Faucet.getInstance().getProperties()
                .getMaxPlayers(), Faucet.getInstance().getPlayers().size(), new ChatComponentText(Faucet.getInstance().getProperties().getMotd())));
    }

    @Override
    public int getPacketID() {
        return 0x00;
    }
}
