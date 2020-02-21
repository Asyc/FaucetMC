package org.faucetmc.player;

public enum GameMode {
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    HARDCORE(8);

    private int id;

    GameMode(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
