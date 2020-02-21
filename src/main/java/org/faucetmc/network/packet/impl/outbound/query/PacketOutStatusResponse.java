package org.faucetmc.network.packet.impl.outbound.query;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import org.faucetmc.chat.component.ChatComponent;
import org.faucetmc.network.NetworkManager;
import org.faucetmc.network.packet.abstraction.OutboundPacket;
import org.faucetmc.network.utils.PacketOutStream;
import org.faucetmc.server.Faucet;
import org.faucetmc.server.config.ServerIcon;

import java.io.IOException;
import java.io.StringWriter;

public class PacketOutStatusResponse implements OutboundPacket {

    private static final JsonArray EMPTY_JSON_ARRAY = new JsonArray();

    private String versionName;
    private int protocol;
    private int maxPlayers, onlinePlayers;
    private ChatComponent motd;
    private ServerIcon icon;

    public PacketOutStatusResponse(String versionName, int protocol, int maxPlayers, int onlinePlayers, ChatComponent motd) {
        this(versionName, protocol, maxPlayers, onlinePlayers, motd, null);
    }

    public PacketOutStatusResponse(String versionName, int protocol, int maxPlayers, int onlinePlayers, ChatComponent motd, ServerIcon icon) {
        this.versionName = versionName;
        this.protocol = protocol;
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
        this.motd = motd;
    }

    @Override
    public void write(PacketOutStream out) {
        JsonObject object = new JsonObject();
        {
            JsonObject version = new JsonObject();
            version.addProperty("name", versionName);
            version.addProperty("protocol", protocol);
            object.add("version", version);
        }
        {
            JsonObject players = new JsonObject();
            players.addProperty("max", maxPlayers);
            players.addProperty("online", onlinePlayers);
            players.add("sample", EMPTY_JSON_ARRAY);
            object.add("players", players);
        }
        object.add("description", Faucet.GSON.toJsonTree(motd));
        if(icon != null) {
            object.addProperty("favicon", "data:image/png;base64," + icon.getBase64());
        }
        out.writeString(object.toString());
    }

    @Override
    public int getPacketID() {
        return 0x00;
    }
}
