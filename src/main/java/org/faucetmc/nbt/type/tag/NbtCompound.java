package org.faucetmc.nbt.type.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NbtCompound {

    private Map<String, Object> entries = new HashMap<>();

    public void putByte(String key, byte value) {
        entries.put(key, value);
    }

    public Byte getByte(String key) {
        return (byte) entries.get(key);
    }

    public void putShort(String key, short value) {
        entries.put(key, value);
    }

    public Short getShort(String key) {
        return (Short) entries.get(key);
    }

    public void putInt(String key, int value) {
        entries.put(key, value);
    }

    public Integer getInt(String key) {
        return (Integer) entries.get(key);
    }

    public void putLong(String key, long value) {
        entries.put(key, value);
    }

    public Long getLong(String key) {
        return (Long) entries.get(key);
    }

    public void putFloat(String key, float value) {
        entries.put(key, value);
    }

    public Float getFloat(String key) {
        return (Float) entries.get(key);
    }

    public void putDouble(String key, double value) {
        entries.put(key, value);
    }

    public Double getDouble(String key) {
        return (Double) entries.get(key);
    }

    public void putByteArray(String key, byte[] value) {
        entries.put(key, value);
    }

    public byte[] getByteArray(String key) {
        return (byte[]) entries.get(key);
    }

    public void putString(String key, String value) {
        entries.put(key, value);
    }

    public String getString(String key) {
        return (String) entries.get(key);
    }

    public void putList(String key, List<?> value) {
        entries.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String key) {
        return (List<T>) entries.get(key);
    }

    public void putCompound(String key, NbtCompound value) {
        entries.put(key, value);
    }

    public NbtCompound getCompound(String key) {
        return (NbtCompound) entries.get(key);
    }

    public void putIntArray(String key, int[] value) {
        entries.put(key, value);
    }

    public int[] getIntArray(String key) {
        return (int[]) entries.get(key);
    }

    public void putLongArray(String key, long[] value) {
        entries.put(key, value);
    }

    public long[] getLongArray(String key) {
        return (long[]) entries.get(key);
    }

    @Deprecated
    public void put(String key, Object value) {
        entries.put(key, value);
    }

    @Deprecated
    public Object get(String key) {
        return entries.get(key);
    }

    public boolean has(String key) {
        return entries.containsKey(key);
    }

    public int size() {
        return entries.size();
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        return entries.entrySet();
    }
}
