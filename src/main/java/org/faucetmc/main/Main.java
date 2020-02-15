package org.faucetmc.main;

import org.faucetmc.nbt.tag.NbtTag;
import org.faucetmc.nbt.tag.impl.NbtTagByteArray;
import org.faucetmc.nbt.tag.impl.NbtTagCompound;
import org.faucetmc.nbt.tag.impl.NbtTagList;
import org.faucetmc.world.parse.RegionFile;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.putInt(4);
        buffer.putInt(9);
        buffer.putInt(0, 6);
        System.out.println(Arrays.toString(buffer.array()));
    }

}