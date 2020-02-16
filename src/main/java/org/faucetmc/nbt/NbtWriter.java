package org.faucetmc.nbt;

import org.faucetmc.nbt.type.NbtTagType;
import org.faucetmc.nbt.type.tag.NbtCompound;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class NbtWriter {

    public static void writeToFile(File file, NbtCompound compound) throws IOException {
        writeToOutputStream(new GZIPOutputStream(new FileOutputStream(file)), compound);
    }

    public static byte[] writeToByteArray(NbtCompound compound) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            writeToOutputStream(buffer, compound);
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToOutputStream(OutputStream out, NbtCompound compound) throws IOException {
        out.write(NbtTagType.TAG_END.getTagID());
        NbtParser.STRING_SERIALIZER.serialize(out, "");
        NbtParser.COMPOUND_SERIALIZER.serialize(out, compound);
    }
}
