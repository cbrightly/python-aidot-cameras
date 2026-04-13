package org.spongycastle.crypto.prng;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public class FixedSecureRandom extends SecureRandom {
    private byte[] _data;
    private int _index;
    private int _intPad;

    public FixedSecureRandom(byte[] value) {
        this(false, new byte[][]{value});
    }

    public FixedSecureRandom(byte[][] values) {
        this(false, values);
    }

    public FixedSecureRandom(boolean intPad, byte[] value) {
        this(intPad, new byte[][]{value});
    }

    public FixedSecureRandom(boolean intPad, byte[][] values) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        int i = 0;
        while (i != values.length) {
            try {
                bOut.write(values[i]);
                i++;
            } catch (IOException e) {
                throw new IllegalArgumentException("can't save value array.");
            }
        }
        byte[] byteArray = bOut.toByteArray();
        this._data = byteArray;
        if (intPad) {
            this._intPad = byteArray.length % 4;
        }
    }

    public void nextBytes(byte[] bytes) {
        System.arraycopy(this._data, this._index, bytes, 0, bytes.length);
        this._index += bytes.length;
    }

    public byte[] generateSeed(int numBytes) {
        byte[] bytes = new byte[numBytes];
        nextBytes(bytes);
        return bytes;
    }

    public int nextInt() {
        int val = 0 | (a() << 24) | (a() << 16);
        int i = this._intPad;
        if (i == 2) {
            this._intPad = i - 1;
        } else {
            val |= a() << 8;
        }
        int i2 = this._intPad;
        if (i2 != 1) {
            return val | a();
        }
        this._intPad = i2 - 1;
        return val;
    }

    public long nextLong() {
        return 0 | (((long) a()) << 56) | (((long) a()) << 48) | (((long) a()) << 40) | (((long) a()) << 32) | (((long) a()) << 24) | (((long) a()) << 16) | (((long) a()) << 8) | ((long) a());
    }

    public boolean isExhausted() {
        return this._index == this._data.length;
    }

    private int a() {
        byte[] bArr = this._data;
        int i = this._index;
        this._index = i + 1;
        return bArr[i] & 255;
    }
}
