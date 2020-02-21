package org.faucetmc.server.config;

import org.faucetmc.server.Faucet;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ServerProperties {

    private static final Yaml YAML = new Yaml();
    private static final Object SAVE_LOCK = new Object();

    private int nioThreads = (int) Math.ceil(Runtime.getRuntime().availableProcessors() / 3.0F);

    private int serverPort = 25565;
    private String serverIP = "0.0.0.0";
    private boolean onlineMode =  true;
    private int gamemode = 0;
    private int maxPlayers = 20;
    private String motd = "A Minecraft Server";

    private String levelName = "world";

    public void save(File file) {
        String dump = YAML.dumpAsMap(this);
        synchronized (SAVE_LOCK) {
            Faucet.EXECUTOR_SERVICE.submit(() -> {
                try {
                    if(!file.exists() && !file.createNewFile()) throw new IOException("Could not create file.");
                    try (FileOutputStream out = new FileOutputStream(file)) { out.write(dump.getBytes(StandardCharsets.UTF_8)); }
                } catch (IOException e) {
                    Faucet.getLogger().fatal("Unable to save [server.properties] file. Error: {}", e.getMessage());
                }
            });
        }
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public boolean isOnlineMode() {
        return onlineMode;
    }

    public void setOnlineMode(boolean onlineMode) {
        this.onlineMode = onlineMode;
    }

    public int getGamemode() {
        return gamemode;
    }

    public void setGamemode(int gamemode) {
        this.gamemode = gamemode;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getNioThreads() {
        return nioThreads;
    }

    public void setNioThreads(int nioThreads) {
        this.nioThreads = nioThreads;
    }
}
