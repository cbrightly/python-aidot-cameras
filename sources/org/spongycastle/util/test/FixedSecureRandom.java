package org.spongycastle.util.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import org.spongycastle.util.Pack;
import org.spongycastle.util.encoders.Hex;

public class FixedSecureRandom extends SecureRandom {
    private static java.math.BigInteger c = new java.math.BigInteger("01020304ffffffff0506070811111111", 16);
    private static java.math.BigInteger d = new java.math.BigInteger("1111111105060708ffffffff01020304", 16);
    private static java.math.BigInteger f = new java.math.BigInteger("3020104ffffffff05060708111111", 16);
    private static final boolean q;
    private static final boolean x;
    private static final boolean y;
    private byte[] _data;
    private int _index;

    static {
        java.math.BigInteger check1 = new java.math.BigInteger(128, new RandomChecker());
        java.math.BigInteger check2 = new java.math.BigInteger(120, new RandomChecker());
        q = check1.equals(d);
        y = check1.equals(c);
        x = check2.equals(f);
    }

    public static class Source {
        byte[] a;

        Source(byte[] data) {
            this.a = data;
        }
    }

    public static class Data extends Source {
        public Data(byte[] data) {
            super(data);
        }
    }

    public static class BigInteger extends Source {
        public BigInteger(byte[] data) {
            super(data);
        }

        public BigInteger(int bitLength, byte[] data) {
            super(FixedSecureRandom.b(bitLength, data));
        }
    }

    public FixedSecureRandom(byte[] value) {
        this(new Source[]{new Data(value)});
    }

    public FixedSecureRandom(byte[][] values) {
        this((Source[]) a(values));
    }

    private static Data[] a(byte[][] values) {
        Data[] res = new Data[values.length];
        for (int i = 0; i != values.length; i++) {
            res[i] = new Data(values[i]);
        }
        return res;
    }

    public FixedSecureRandom(Source[] sources) {
        super((SecureRandomSpi) null, new DummyProvider());
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        if (y) {
            if (x) {
                int i = 0;
                while (i != sources.length) {
                    try {
                        if (sources[i] instanceof BigInteger) {
                            byte[] data = sources[i].a;
                            int len = data.length - (data.length % 4);
                            for (int w = (data.length - len) - 1; w >= 0; w--) {
                                bOut.write(data[w]);
                            }
                            for (int w2 = data.length - len; w2 < data.length; w2 += 4) {
                                bOut.write(data, w2, 4);
                            }
                        } else {
                            bOut.write(sources[i].a);
                        }
                        i++;
                    } catch (IOException e) {
                        throw new IllegalArgumentException("can't save value source.");
                    }
                }
            } else {
                int i2 = 0;
                while (i2 != sources.length) {
                    try {
                        bOut.write(sources[i2].a);
                        i2++;
                    } catch (IOException e2) {
                        throw new IllegalArgumentException("can't save value source.");
                    }
                }
            }
        } else if (q) {
            int i3 = 0;
            while (i3 != sources.length) {
                try {
                    if (sources[i3] instanceof BigInteger) {
                        byte[] data2 = sources[i3].a;
                        int len2 = data2.length - (data2.length % 4);
                        for (int w3 = 0; w3 < len2; w3 += 4) {
                            bOut.write(data2, data2.length - (w3 + 4), 4);
                        }
                        if (data2.length - len2 != 0) {
                            for (int w4 = 0; w4 != 4 - (data2.length - len2); w4++) {
                                bOut.write(0);
                            }
                        }
                        for (int w5 = 0; w5 != data2.length - len2; w5++) {
                            bOut.write(data2[len2 + w5]);
                        }
                    } else {
                        bOut.write(sources[i3].a);
                    }
                    i3++;
                } catch (IOException e3) {
                    throw new IllegalArgumentException("can't save value source.");
                }
            }
        } else {
            throw new IllegalStateException("Unrecognized BigInteger implementation");
        }
        this._data = bOut.toByteArray();
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
        return 0 | (c() << 24) | (c() << 16) | (c() << 8) | c();
    }

    public long nextLong() {
        return 0 | (((long) c()) << 56) | (((long) c()) << 48) | (((long) c()) << 40) | (((long) c()) << 32) | (((long) c()) << 24) | (((long) c()) << 16) | (((long) c()) << 8) | ((long) c());
    }

    public boolean isExhausted() {
        return this._index == this._data.length;
    }

    private int c() {
        byte[] bArr = this._data;
        int i = this._index;
        this._index = i + 1;
        return bArr[i] & 255;
    }

    public static class RandomChecker extends SecureRandom {
        byte[] data = Hex.a("01020304ffffffff0506070811111111");
        int index = 0;

        RandomChecker() {
            super((SecureRandomSpi) null, new DummyProvider());
        }

        public void nextBytes(byte[] bytes) {
            System.arraycopy(this.data, this.index, bytes, 0, bytes.length);
            this.index += bytes.length;
        }
    }

    /* access modifiers changed from: private */
    public static byte[] b(int bitLength, byte[] v) {
        if ((bitLength + 7) / 8 > v.length) {
            byte[] tmp = new byte[((bitLength + 7) / 8)];
            System.arraycopy(v, 0, tmp, tmp.length - v.length, v.length);
            if (q && bitLength % 8 != 0) {
                Pack.d(Pack.a(tmp, 0) << (8 - (bitLength % 8)), tmp, 0);
            }
            return tmp;
        }
        if (q && bitLength < v.length * 8 && bitLength % 8 != 0) {
            Pack.d(Pack.a(v, 0) << (8 - (bitLength % 8)), v, 0);
        }
        return v;
    }

    public static class DummyProvider extends Provider {
        DummyProvider() {
            super("BCFIPS_FIXED_RNG", 1.0d, "BCFIPS Fixed Secure Random Provider");
        }
    }
}
