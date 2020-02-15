package org.faucetmc.nbt;

import org.faucetmc.nbt.serializer.NbtSerializer;
import org.faucetmc.nbt.tag.impl.NbtTagCompound;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class NbtWriter {

    public static void writeToFile(File file, NbtTagCompound compound) throws IOException {
        try (OutputStream out = new GZIPOutputStream(new FileOutputStream(file))) {
            writeOut(out, compound);
        }
    }

    public static byte[] writeToByteArray(NbtTagCompound compound) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            writeOut(buffer, compound);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return buffer.toByteArray();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void writeOut(OutputStream out, NbtTagCompound compound) throws IOException {
        NbtSerializer serializer = NbtParser.SERIALIZERS[compound.getType().getID()];
        serializer.writeTagType(out);
        serializer.writeTagName(out, "");
        serializer.serialize(out, compound);
    }

}
