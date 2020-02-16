package org.faucetmc.main;

import org.faucetmc.nbt.type.tag.NbtCompound;
import org.faucetmc.world.parse.RegionFile;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        RegionFile file = new RegionFile(new File(Main.class.getResource("/world/region/r.0.0.mca").toURI()));
        NbtCompound compound = file.readChunkData(1, 15);
        System.out.println(1);
    }

}