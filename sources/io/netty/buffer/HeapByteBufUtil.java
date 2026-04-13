package io.netty.buffer;

import org.glassfish.grizzly.http.server.util.MappingData;

public final class HeapByteBufUtil {
    static byte getByte(byte[] memory, int index) {
        return memory[index];
    }

    static short getShort(byte[] memory, int index) {
        return (short) ((memory[index] << 8) | (memory[index + 1] & 255));
    }

    static int getUnsignedMedium(byte[] memory, int index) {
        return ((memory[index] & 255) << MappingData.PATH) | ((memory[index + 1] & 255) << 8) | (memory[index + 2] & 255);
    }

    static int getInt(byte[] memory, int index) {
        return ((memory[index] & 255) << 24) | ((memory[index + 1] & 255) << MappingData.PATH) | ((memory[index + 2] & 255) << 8) | (memory[index + 3] & 255);
    }

    static long getLong(byte[] memory, int index) {
        return ((((long) memory[index]) & 255) << 56) | ((((long) memory[index + 1]) & 255) << 48) | ((((long) memory[index + 2]) & 255) << 40) | ((((long) memory[index + 3]) & 255) << 32) | ((((long) memory[index + 4]) & 255) << 24) | ((((long) memory[index + 5]) & 255) << 16) | ((((long) memory[index + 6]) & 255) << 8) | (255 & ((long) memory[index + 7]));
    }

    static void setByte(byte[] memory, int index, int value) {
        memory[index] = (byte) value;
    }

    static void setShort(byte[] memory, int index, int value) {
        memory[index] = (byte) (value >>> 8);
        memory[index + 1] = (byte) value;
    }

    static void setMedium(byte[] memory, int index, int value) {
        memory[index] = (byte) (value >>> 16);
        memory[index + 1] = (byte) (value >>> 8);
        memory[index + 2] = (byte) value;
    }

    static void setInt(byte[] memory, int index, int value) {
        memory[index] = (byte) (value >>> 24);
        memory[index + 1] = (byte) (value >>> 16);
        memory[index + 2] = (byte) (value >>> 8);
        memory[index + 3] = (byte) value;
    }

    static void setLong(byte[] memory, int index, long value) {
        memory[index] = (byte) ((int) (value >>> 56));
        memory[index + 1] = (byte) ((int) (value >>> 48));
        memory[index + 2] = (byte) ((int) (value >>> 40));
        memory[index + 3] = (byte) ((int) (value >>> 32));
        memory[index + 4] = (byte) ((int) (value >>> 24));
        memory[index + 5] = (byte) ((int) (value >>> 16));
        memory[index + 6] = (byte) ((int) (value >>> 8));
        memory[index + 7] = (byte) ((int) value);
    }

    private HeapByteBufUtil() {
    }
}
