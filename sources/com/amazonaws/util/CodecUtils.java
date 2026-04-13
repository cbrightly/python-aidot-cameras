package com.amazonaws.util;

public enum CodecUtils {
    ;

    static int sanitize(String singleOctets, byte[] dest) {
        int capacity = dest.length;
        char[] src = singleOctets.toCharArray();
        int limit = 0;
        for (int i = 0; i < capacity; i++) {
            char c = src[i];
            if (!(c == 13 || c == 10 || c == ' ')) {
                if (c <= 127) {
                    dest[limit] = (byte) c;
                    limit++;
                } else {
                    throw new IllegalArgumentException("Invalid character found at position " + i + " for " + singleOctets);
                }
            }
        }
        return limit;
    }

    public static byte[] toBytesDirect(String singleOctets) {
        char[] src = singleOctets.toCharArray();
        byte[] dest = new byte[src.length];
        int i = 0;
        while (i < dest.length) {
            char c = src[i];
            if (c <= 127) {
                dest[i] = (byte) c;
                i++;
            } else {
                throw new IllegalArgumentException("Invalid character found at position " + i + " for " + singleOctets);
            }
        }
        return dest;
    }

    public static String toStringDirect(byte[] bytes) {
        char[] dest = new char[bytes.length];
        int i = 0;
        int length = bytes.length;
        int i2 = 0;
        while (i2 < length) {
            dest[i] = (char) bytes[i2];
            i2++;
            i++;
        }
        return new String(dest);
    }

    static void sanityCheckLastPos(int pos, int mask) {
        if ((pos & mask) != 0) {
            throw new IllegalArgumentException("Invalid last non-pad character detected");
        }
    }
}
