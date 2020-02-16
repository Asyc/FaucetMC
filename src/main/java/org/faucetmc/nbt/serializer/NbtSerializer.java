package org.faucetmc.nbt.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface NbtSerializer<T> {
    T deserialize(InputStream in) throws IOException;
    void serialize(OutputStream out, T value) throws IOException;

}
