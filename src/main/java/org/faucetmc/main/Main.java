package org.faucetmc.main;

import org.faucetmc.network.type.VarInteger;
import org.faucetmc.server.Faucet;
import org.faucetmc.world.chunk.Chunk;
import org.faucetmc.world.parse.RegionFile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        //RegionFile file = new RegionFile(new File("world/region/r.0.0.mca"));
        //Chunk c = new Chunk(file.readChunkData(3, 15));
        new Faucet();
    }

}