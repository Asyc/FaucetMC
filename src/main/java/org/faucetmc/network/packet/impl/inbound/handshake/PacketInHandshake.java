package org.faucetmc.network.packet.impl.inbound.handshake;

import org.faucetmc.network.packet.InboundPacket;
import org.faucetmc.network.utils.PacketInStream;
import org.faucetmc.network.utils.PacketOutStream;

import java.io.IOException;

public class PacketInHandshake implements InboundPacket {

    private int protocol;
    private String ip;
    private int port;
    private int state;

    @Override
    public void read(PacketInStream in) throws IOException {
        this.protocol = in.readVarInt();
        this.ip = in.readString();
        this.port = Short.toUnsignedInt(in.readShort());
        this.state = in.readVarInt();
    }

    @Override
    public void handle() {
        switch (state) {
            case 1:

                break;
            case 2:
                break;
        }
    }

    @Override
    public int getPacketID() {
        return 0x00;
    }
}
