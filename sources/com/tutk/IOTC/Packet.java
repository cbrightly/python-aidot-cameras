package com.tutk.IOTC;

import org.glassfish.grizzly.http.server.util.MappingData;

public class Packet {
    public static final short byteArrayToShort_Little(byte[] byt, int nBeginPos) {
        return (short) ((byt[nBeginPos] & 255) | ((byt[nBeginPos + 1] & 255) << 8));
    }

    public static final int byteArrayToInt_Little(byte[] byt, int nBeginPos) {
        return (byt[nBeginPos] & 255) | ((byt[nBeginPos + 1] & 255) << 8) | ((byt[nBeginPos + 2] & 255) << MappingData.PATH) | ((byt[nBeginPos + 3] & 255) << 24);
    }

    public static final long byteArrayToLong_Little2(byte[] byt, int nBeginPos) {
        long l = 0;
        for (int i = 0; i < 4; i++) {
            l |= (255 & ((long) byt[nBeginPos + i])) << (i * 8);
        }
        return l;
    }

    public static final int byteArrayToInt_Little(byte[] byt) {
        if (byt.length == 1) {
            return byt[0] & 255;
        }
        if (byt.length == 2) {
            return (byt[0] & 255) | ((byt[1] & 255) << 8);
        }
        if (byt.length == 4) {
            return (byt[0] & 255) | ((byt[1] & 255) << 8) | ((byt[2] & 255) << MappingData.PATH) | ((byt[3] & 255) << 24);
        }
        return 0;
    }

    public static final long byteArrayToLong_Little(byte[] byt, int nBeginPos) {
        return (long) ((byt[nBeginPos] & 255) | ((byt[nBeginPos + 1] & 255) << 8) | ((byt[nBeginPos + 2] & 255) << MappingData.PATH) | ((byt[nBeginPos + 3] & 255) << 24) | ((byt[nBeginPos + 4] & 255) << 32) | ((byt[nBeginPos + 5] & 255) << 40) | ((byt[nBeginPos + 6] & 255) << 48) | ((byt[nBeginPos + 7] & 255) << 56));
    }

    public static final int byteArrayToInt_Big(byte[] byt) {
        if (byt.length == 1) {
            return byt[0] & 255;
        }
        if (byt.length == 2) {
            return ((byt[0] & 255) << 8) | (byt[1] & 255);
        }
        if (byt.length == 4) {
            return ((byt[0] & 255) << 24) | ((byt[1] & 255) << MappingData.PATH) | ((byt[2] & 255) << 8) | (byt[3] & 255);
        }
        return 0;
    }

    public static final byte[] longToByteArray_Little(long value) {
        return new byte[]{(byte) ((int) value), (byte) ((int) (value >>> 8)), (byte) ((int) (value >>> 16)), (byte) ((int) (value >>> 24)), (byte) ((int) (value >>> 32)), (byte) ((int) (value >>> 40)), (byte) ((int) (value >>> 48)), (byte) ((int) (value >>> 56))};
    }

    public static final byte[] intToByteArray_Little(int value) {
        return new byte[]{(byte) value, (byte) (value >>> 8), (byte) (value >>> 16), (byte) (value >>> 24)};
    }

    public static final byte[] intToByteArray_Big(int value) {
        return new byte[]{(byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value};
    }

    public static final byte[] shortToByteArray_Little(short value) {
        return new byte[]{(byte) value, (byte) (value >>> 8)};
    }

    public static final byte[] shortToByteArray_Big(short value) {
        return new byte[]{(byte) (value >>> 8), (byte) value};
    }

    public static final short[] byteArray2shortArray_Little(byte[] b, int length) {
        short[] buf = new short[(length / 2)];
        for (int i = 0; i < length / 2; i++) {
            buf[i] = byteArrayToShort_Little(b, i * 2);
        }
        return buf;
    }

    public static final byte[] shortArray2byteArray_Little(short[] s, int length) {
        byte[] buf = new byte[(length * 2)];
        for (int i = 0; i < length; i++) {
            byte[] b = shortToByteArray_Little(s[i]);
            buf[(i * 2) + 0] = b[0];
            buf[(i * 2) + 1] = b[1];
        }
        return buf;
    }

    public static final long bytes2Long(byte[] data, int length) {
        byte[] bData = reverse(data, length);
        long n = 0;
        for (int i = 0; i < length; i++) {
            n = (n << 8) | ((long) (bData[i] & 255));
        }
        return n;
    }

    public static byte[] reverse(byte[] data, int length) {
        int nSize = length;
        for (int i = 0; i < nSize / 2; i++) {
            byte temp = data[i];
            data[i] = data[(nSize - 1) - i];
            data[(nSize - 1) - i] = temp;
        }
        return data;
    }
}
