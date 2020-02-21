package org.faucetmc.world;

import org.faucetmc.nbt.NbtParser;
import org.faucetmc.nbt.type.tag.NbtCompound;
import org.faucetmc.util.Vec2i;
import org.faucetmc.world.chunk.Chunk;
import org.faucetmc.world.parse.RegionFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private Map<Vec2i, Chunk> loadedChunks = new ConcurrentHashMap<>();

    private Map<Vec2i, RegionFile> loadedRegions = new ConcurrentHashMap<>();

    private String name;
    private String generatorName;
    private long seed;

    public World(String name) {
        this.name = name;

        NbtCompound level = NbtParser.readFromFile(new File(name + "/level.dat"));
        this.generatorName = level.getString("generatorName");
        this.seed = level.getLong("RandomSeed");
    }

    public Chunk loadChunkAt(int x, int z) throws IOException {
        Chunk chunk = loadedChunks.get(new Vec2i(x, z));
        if (chunk != null) return chunk;
        int regionX = x / 32;
        int regionZ = z / 32;
        int chunkX = x - (regionX * 32);
        int chunkZ = z - (regionZ * 32);
        RegionFile regionFile = loadedRegions.get(new Vec2i(x, z));
        if (regionFile == null) {
            //todo : region gen
        } else {
            chunk = new Chunk(regionFile.readChunkData(chunkX, chunkZ));
            loadedChunks.put(new Vec2i(x, z), chunk);
        }

        return chunk;
    }
}
