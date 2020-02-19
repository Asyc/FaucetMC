package org.faucetmc.world.chunk;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.faucetmc.nbt.type.tag.NbtCompound;
import org.faucetmc.profiler.Profiler;

import java.util.List;

public class Chunk {

    private static Logger logger = LogManager.getLogger("Chunk");
    private static byte[] EMPTY_BYTE_ARRAY = new byte[4096];


    private int xPos, zPos;
    private long lastTickUpdate, inhabitedTime;
    private boolean lightPopulated, terrainPopulated;

    private Profiler profiler = new Profiler();

    public Chunk(NbtCompound compound) {
        NbtCompound chunk = compound.getCompound("Level");
        this.xPos = chunk.getInt("xPos");
        this.zPos = chunk.getInt("zPos");
        this.lastTickUpdate = chunk.getLong("LastUpdate");
        this.inhabitedTime = chunk.getLong("InhabitedTime");
        this.parseChunkSections(chunk.getList("Sections"));
    }

    private void parseChunkSections(List<NbtCompound> compounds) {
        profiler.beginSection("Load Chunk Blocks");
        for (NbtCompound compound : compounds) {
            byte yIndex = compound.getByte("Y");
            byte[] blocks = compound.getByteArray("Blocks");
            byte[] add = compound.getByteArray("Add");
            byte[] data = compound.getByteArray("Data");
            byte[] blockLights = compound.getByteArray("BlockLight");
            byte[] skylights = compound.getByteArray("SkyLight");
            if(data == null) data = EMPTY_BYTE_ARRAY;
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {
                        int location = y * 16 * 16 + z * 16 + x;
                        short blockID = blocks[location];
                        if(add != null) blockID += (nibble(add, location) << 8);
                        byte blockData = nibble(data, location);
                        byte blockLight = nibble(blockLights, location);
                        byte skyLight = nibble(skylights, location);
                        logger.info("Block({}) at X:{}, Y:{}, Z:{}", blockID, x, y + (yIndex * 16), z);
                    }
                }
            }
        }
        profiler.endSection();
    }

    private byte nibble(byte[] array, int index) {
        return (byte) (index % 2 == 0 ? array[index / 2] & 0x0F : (array[index / 2] >> 4) & 0x0F);
    }
}
