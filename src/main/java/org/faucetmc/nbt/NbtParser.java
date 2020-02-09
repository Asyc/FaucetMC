package org.faucetmc.nbt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.faucetmc.nbt.serializer.NbtSerializer;
import org.faucetmc.nbt.serializer.impl.NbtByteArraySerializer;
import org.faucetmc.nbt.serializer.impl.NbtByteSerializer;
import org.faucetmc.nbt.serializer.impl.NbtCompoundSerializer;
import org.faucetmc.nbt.serializer.impl.NbtDoubleSerializer;
import org.faucetmc.nbt.serializer.impl.NbtFloatSerializer;
import org.faucetmc.nbt.serializer.impl.NbtIntSerializer;
import org.faucetmc.nbt.serializer.impl.NbtListSerializer;
import org.faucetmc.nbt.serializer.impl.NbtLongSerializer;
import org.faucetmc.nbt.serializer.impl.NbtShortSerializer;
import org.faucetmc.nbt.serializer.impl.NbtStringSerializer;
import org.faucetmc.nbt.serializer.impl.NbtTagIntArraySerializer;
import org.faucetmc.nbt.serializer.impl.NbtTagLongArraySerializer;
import org.faucetmc.nbt.tag.impl.NbtTagCompound;
import org.faucetmc.nbt.type.NbtTagType;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class NbtParser {

    static final NbtSerializer<?>[] SERIALIZERS = new NbtSerializer<?>[]{
            null,   //TAG_END padding
            new NbtByteSerializer(),
            new NbtShortSerializer(),
            new NbtIntSerializer(),
            new NbtLongSerializer(),
            new NbtFloatSerializer(),
            new NbtDoubleSerializer(),
            new NbtByteArraySerializer(),
            new NbtStringSerializer(),
            new NbtListSerializer(),
            new NbtCompoundSerializer(),
            new NbtTagIntArraySerializer(),
            new NbtTagLongArraySerializer(),
    };

    private static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder();
        /*for(NbtSerializer<?> serializer : SERIALIZERS) {
            if(serializer == null) continue;
            builder.registerTypeAdapter(serializer.getType().getClassType(), serializer);
        }*/
        GSON = builder.create();
    }

    public static NbtTagCompound parseFile(File file) throws IOException {
        BufferedInputStream bufferedIn = new BufferedInputStream(new FileInputStream(file));
        bufferedIn.mark(2);
        boolean gzip = (bufferedIn.read() & 0xff | ((bufferedIn.read() << 8) & 0xff00)) == GZIPInputStream.GZIP_MAGIC;
        bufferedIn.reset();
        try (InputStream in = gzip ? new GZIPInputStream(bufferedIn) : bufferedIn) {
            return NbtParser.readInputStream(in);
        }
    }

    private static NbtTagCompound readInputStream(InputStream in) throws IOException {
        int read = in.read();
        int length = ((in.read() << 8) + (in.read()));
        if (read == NbtTagType.TAG_END.getID() || read == -1 || length == -1) throw new EOFException();
        if (read != NbtTagType.TAG_COMPOUND.getID() || length != 0) throw new RuntimeException("No root tag exists");
        return (NbtTagCompound) SERIALIZERS[read].deserialize(in, true);
    }

    @SuppressWarnings("rawtypes")
    public static NbtSerializer<?> getSerializer(int type) {
        return SERIALIZERS[type];
    }
}
