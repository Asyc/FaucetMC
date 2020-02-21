package org.faucetmc.world;

public enum Difficulty {
    PEACEFUL, EASY, NORMAL, HARD;

    public int getID() {
        return ordinal();
    }
}
