package org.faucetmc.server;

import org.faucetmc.world.World;

import java.util.LinkedList;
import java.util.List;

public class MinecraftServer {

    private long tickTime = 0;

    private boolean shouldStop = false;

    private long delay = (long) (1.0D / 20.0D * 1000);
    private long lastTick = 0;

    private List<World> worlds = new LinkedList<>();
    private List<?> players = new LinkedList<>();

    public MinecraftServer() {

    }

    public void startGameLoop() {
        while(!shouldStop) {
            if(System.currentTimeMillis() - lastTick < delay) continue;
            doTick();
            tickTime++;
        }
    }

    private void doTick() {

    }

    public void stop() {
        shouldStop = true;
    }

    public long getTickTime() {
        return tickTime;
    }

    public List<?> getPlayers() {
        return players;
    }

}
