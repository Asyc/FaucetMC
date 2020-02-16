package org.faucetmc.nbt;

import org.faucetmc.nbt.serializer.NbtSerializer;
import org.faucetmc.nbt.type.NbtTagType;
import org.faucetmc.nbt.type.tag.NbtCompound;

import java.io.*;
import java.util.zip.GZIPInputStream;

@SuppressWarnings("unchecked")
public class NbtParser {

    static final NbtSerializer<NbtCompound> COMPOUND_SERIALIZER = (NbtSerializer<NbtCompound>) NbtTagType.TAG_COMPOUND.getSerializer();
    static final NbtSerializer<String> STRING_SERIALIZER = (NbtSerializer<String>) NbtTagType.TAG_STRING.getSerializer();

    public static NbtCompound readFromBytes(byte[] bytes) {
        try {
            boolean gzip = (bytes[0] & 0xff | ((bytes[1] << 8) & 0xff00)) == GZIPInputStream.GZIP_MAGIC;
            InputStream in = gzip ? new GZIPInputStream(new ByteArrayInputStream(bytes)) : new ByteArrayInputStream(bytes);
            return readFromInputStream(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static NbtCompound readFromFile(File file) throws IOException {
        BufferedInputStream bufferedIn = new BufferedInputStream(new FileInputStream(file));
        bufferedIn.mark(2);
        boolean gzip = (bufferedIn.read() & 0xff | ((bufferedIn.read() << 8) & 0xff00)) == GZIPInputStream.GZIP_MAGIC;
        bufferedIn.reset();

        try(InputStream in = gzip ? new GZIPInputStream(bufferedIn) : bufferedIn) {
            return readFromInputStream(in);
        }
    }

    public static NbtCompound readFromInputStream(InputStream in) throws IOException {
        int type = in.read();
        in.skip((in.read() << 8) + (in.read())); //Key
        if(type != NbtTagType.TAG_COMPOUND.getTagID()) throw new IOException("No root tag");
        return COMPOUND_SERIALIZER.deserialize(in);
    }

}
