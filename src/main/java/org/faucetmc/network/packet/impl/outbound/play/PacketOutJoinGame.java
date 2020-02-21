package org.faucetmc.network.packet.impl.outbound.play;

import org.faucetmc.network.packet.abstraction.OutboundPacket;
import org.faucetmc.network.utils.PacketOutStream;
import org.faucetmc.player.GameMode;
import org.faucetmc.world.Difficulty;

public class PacketOutJoinGame implements OutboundPacket {

    private int entityID;
    private GameMode gamemode;
    private byte dimension;
    private Difficulty difficulty;
    private byte maxPlayers;
    private String levelType;

    //MaxPlayers should be unsigned
    public PacketOutJoinGame(int entityID, GameMode gamemode, byte dimension, Difficulty difficulty, byte maxPlayers, String levelType) {
        this.entityID = entityID;
        this.gamemode = gamemode;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.levelType = levelType;
    }

    @Override
    public void write(PacketOutStream out) {
        out.writeInt(entityID);
        out.writeByte((byte) gamemode.getID());
        out.writeByte(dimension);
        out.writeByte((byte) difficulty.getID());
        out.writeByte(maxPlayers);
        out.writeString(levelType);
    }

    @Override
    public int getPacketID() {
        return 0x01;
    }
}
