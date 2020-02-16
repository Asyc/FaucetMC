package org.faucetmc.nbt.type;

import org.faucetmc.nbt.serializer.NbtSerializer;
import org.faucetmc.nbt.type.tag.NbtCompound;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes", "deprecated"})
public enum NbtTagType {

    TAG_END(null),
    TAG_BYTE(new NbtSerializer<Byte>() {
        @Override
        public Byte deserialize(InputStream in) throws IOException {
            return (byte) in.read();
        }

        @Override
        public void serialize(OutputStream out, Byte value) throws IOException {
            out.write(value);
        }
    }),
    TAG_SHORT(new NbtSerializer<Short>() {
        @Override
        public Short deserialize(InputStream in) throws IOException {
            return (short) ((in.read() << 8) + (in.read()));
        }

        @Override
        public void serialize(OutputStream out, Short value) throws IOException {
            out.write((value >>> 8) & 0xFF);
            out.write((value) & 0xFF);
        }
    }),
    TAG_INT(new NbtSerializer<Integer>() {
        @Override
        public Integer deserialize(InputStream in) throws IOException {
            return ((in.read() << 24) + (in.read() << 16) + (in.read() << 8) + (in.read()));
        }

        @Override
        public void serialize(OutputStream out, Integer value) throws IOException {
            out.write((value >>> 24) & 0xFF);
            out.write((value >>> 16) & 0xFF);
            out.write((value >>> 8) & 0xFF);
            out.write((value) & 0xFF);
        }
    }),
    TAG_LONG(new NbtSerializer<Long>() {
        NbtSerializer<Integer> intSerializer = (NbtSerializer<Integer>) NbtTagType.TAG_INT.getSerializer();

        @Override
        public Long deserialize(InputStream in) throws IOException {
            return ((long) (intSerializer.deserialize(in)) << 32) + (intSerializer.deserialize(in) & 0xFFFFFFFFL);
        }

        @Override
        public void serialize(OutputStream out, Long value) throws IOException {
            long v = value;
            out.write((int) (v >>> 56) & 0xFF);
            out.write((int) (v >>> 48) & 0xFF);
            out.write((int) (v >>> 40) & 0xFF);
            out.write((int) (v >>> 32) & 0xFF);
            out.write((int) (v >>> 24) & 0xFF);
            out.write((int) (v >>> 16) & 0xFF);
            out.write((int) (v >>> 8) & 0xFF);
            out.write((int) (v) & 0xFF);
        }
    }),
    TAG_FLOAT(new NbtSerializer<Float>() {
        NbtSerializer<Integer> intSerializer = (NbtSerializer<Integer>) NbtTagType.TAG_INT.getSerializer();
        @Override
        public Float deserialize(InputStream in) throws IOException {
            return Float.intBitsToFloat(intSerializer.deserialize(in));
        }

        @Override
        public void serialize(OutputStream out, Float value) throws IOException {
            intSerializer.serialize(out, Float.floatToIntBits(value));
        }
    }),
    TAG_DOUBLE(new NbtSerializer<Double>() {
        NbtSerializer<Long> longSerializer = (NbtSerializer<Long>) NbtTagType.TAG_LONG.getSerializer();
        @Override
        public Double deserialize(InputStream in) throws IOException {
            return Double.longBitsToDouble(longSerializer.deserialize(in));
        }

        @Override
        public void serialize(OutputStream out, Double value) throws IOException {
            longSerializer.serialize(out, Double.doubleToLongBits(value));
        }
    }),
    TAG_BYTE_ARRAY(new NbtSerializer<byte[]>() {
        NbtSerializer<Integer> intSerializer = (NbtSerializer<Integer>) NbtTagType.TAG_INT.getSerializer();
        @Override
        public byte[] deserialize(InputStream in) throws IOException {
            int length = intSerializer.deserialize(in);
            byte[] bytes = new byte[length];
            if(in.read(bytes) == -1) throw new EOFException();
            return bytes;
        }

        @Override
        public void serialize(OutputStream out, byte[] value) throws IOException {
            intSerializer.serialize(out, value.length);
            out.write(value);
        }
    }),
    TAG_STRING(new NbtSerializer<String>() {
        NbtSerializer<Short> shortSerializer = (NbtSerializer<Short>) NbtTagType.TAG_SHORT.getSerializer();

        @Override
        public String deserialize(InputStream in) throws IOException {
            int length = Short.toUnsignedInt(shortSerializer.deserialize(in));
            byte[] bytes = new byte[length];
            if(in.read(bytes) == -1) throw new EOFException();
            return new String(bytes, StandardCharsets.UTF_8);
        }

        @Override
        public void serialize(OutputStream out, String value) throws IOException {
            short length = (short) value.length();
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            shortSerializer.serialize(out, length);
            out.write(bytes);
        }
    }),
    TAG_LIST(new NbtSerializer<List<?>>() {
        NbtSerializer<Integer> intSerializer = (NbtSerializer<Integer>) NbtTagType.TAG_INT.getSerializer();
        @Override
        public List<?> deserialize(InputStream in) throws IOException {
            NbtSerializer typeSerializer = NbtTagType.values()[in.read()].getSerializer();
            int length = intSerializer.deserialize(in);
            List<Object> list = new LinkedList<>();
            for(int i = 0; i < length; i++) {
                list.add(typeSerializer.deserialize(in));
            }
            return list;
        }

        @Override
        public void serialize(OutputStream out, List<?> value) throws IOException {
            NbtTagType type = value.size() == 0 ? TAG_END : TYPE_MAPPING.get(value.get(0).getClass());
            intSerializer.serialize(out, value.size());
            out.write(type.getTagID());
            if(type == TAG_END) return;

            NbtSerializer typeSerializer = type.getSerializer();
            for(Object o : value) {
                typeSerializer.serialize(out, o);
            }
        }
    }),
    TAG_COMPOUND(new NbtSerializer<NbtCompound>() {
        NbtSerializer<String> stringSerializer = (NbtSerializer<String>) NbtTagType.TAG_STRING.getSerializer();

        @Override
        public NbtCompound deserialize(InputStream in) throws IOException {
            NbtCompound compound = new NbtCompound();

            for(;;) {
                NbtSerializer typeSerializer = getTagTypeFromID(in.read()).getSerializer();
                if(typeSerializer == null /* TAG_END */) break;
                String key = stringSerializer.deserialize(in);
                compound.put(key, typeSerializer.deserialize(in));
            }

            return compound;
        }

        @Override
        public void serialize(OutputStream out, NbtCompound value) throws IOException {
            for(Map.Entry<String, Object> entry : value.entrySet()) {
                NbtSerializer typeSerializer;
                if(entry.getValue() instanceof List) typeSerializer = TAG_LIST.getSerializer();
                else typeSerializer = TYPE_MAPPING.get(entry.getValue().getClass()).getSerializer();
                if(typeSerializer == null) continue;
                stringSerializer.serialize(out, entry.getKey());
                typeSerializer.serialize(out, entry.getKey());
            }

            out.write(TAG_END.getTagID());
        }
    }),
    TAG_INT_ARRAY(new NbtSerializer<int[]>() {
        NbtSerializer<Integer> intSerializer = (NbtSerializer<Integer>) NbtTagType.TAG_INT.getSerializer();

        @Override
        public int[] deserialize(InputStream in) throws IOException {
            int length = intSerializer.deserialize(in);
            int[] array = new int[length];
            for(int i = 0; i < array.length; i++) {
                array[i] = intSerializer.deserialize(in);
            }

            return array;
        }

        @Override
        public void serialize(OutputStream out, int[] value) throws IOException {
            intSerializer.serialize(out, value.length);
            for(int i : value) {
                intSerializer.serialize(out, i);
            }
        }
    }),
    TAG_LONG_ARRAY(new NbtSerializer<long[]>() {
        NbtSerializer<Integer> intSerializer = (NbtSerializer<Integer>) NbtTagType.TAG_INT.getSerializer();
        NbtSerializer<Long> longSerializer = (NbtSerializer<Long>) NbtTagType.TAG_LONG.getSerializer();

        @Override
        public long[] deserialize(InputStream in) throws IOException {
            int length = intSerializer.deserialize(in);
            long[] array = new long[length];
            for(int i = 0; i < array.length; i++) {
                array[i] = longSerializer.deserialize(in);
            }

            return array;
        }

        @Override
        public void serialize(OutputStream out, long[] value) throws IOException {
            intSerializer.serialize(out, value.length);
            for(long l : value) {
                longSerializer.serialize(out, l);
            }
        }
    });

    private static HashMap<Class<?>, NbtTagType> TYPE_MAPPING = new HashMap<>(NbtTagType.values().length);

    static {
        TYPE_MAPPING.put(Byte.class, TAG_BYTE);
        TYPE_MAPPING.put(byte.class, TAG_BYTE);
        TYPE_MAPPING.put(Short.class, TAG_SHORT);
        TYPE_MAPPING.put(short.class, TAG_SHORT);
        TYPE_MAPPING.put(Integer.class, TAG_INT);
        TYPE_MAPPING.put(int.class, TAG_INT);
        TYPE_MAPPING.put(Long.class, TAG_LONG);
        TYPE_MAPPING.put(long.class, TAG_LONG);
        TYPE_MAPPING.put(Float.class, TAG_FLOAT);
        TYPE_MAPPING.put(float.class, TAG_FLOAT);
        TYPE_MAPPING.put(Double.class, TAG_INT);
        TYPE_MAPPING.put(double.class, TAG_INT);
        TYPE_MAPPING.put(Byte[].class, TAG_BYTE_ARRAY);
        TYPE_MAPPING.put(byte[].class, TAG_BYTE_ARRAY);
        TYPE_MAPPING.put(String.class, TAG_STRING);
        TYPE_MAPPING.put(List.class, TAG_LIST);
        TYPE_MAPPING.put(int[].class, TAG_INT_ARRAY);
        TYPE_MAPPING.put(long[].class, TAG_LONG_ARRAY);
    }

    NbtSerializer<?> serializer;

    NbtTagType(NbtSerializer<?> serializer) {
        this.serializer = serializer;
    }

    public NbtSerializer<?> getSerializer() {
        return serializer;
    }

    public int getTagID() {
        return ordinal();
    }

    public static NbtTagType getTagTypeFromID(int id) {
        return values()[id];
    }
}
