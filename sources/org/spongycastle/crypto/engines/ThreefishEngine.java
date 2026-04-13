package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.TweakableBlockCipherParameters;

public class ThreefishEngine implements BlockCipher {
    /* access modifiers changed from: private */
    public static int[] a;
    /* access modifiers changed from: private */
    public static int[] b;
    /* access modifiers changed from: private */
    public static int[] c;
    /* access modifiers changed from: private */
    public static int[] d;
    private int e;
    private int f;
    private long[] g;
    private long[] h;
    private long[] i;
    private ThreefishCipher j;
    private boolean k;

    static {
        int[] iArr = new int[80];
        a = iArr;
        b = new int[iArr.length];
        c = new int[iArr.length];
        d = new int[iArr.length];
        int i2 = 0;
        while (true) {
            int[] iArr2 = a;
            if (i2 < iArr2.length) {
                b[i2] = i2 % 17;
                iArr2[i2] = i2 % 9;
                c[i2] = i2 % 5;
                d[i2] = i2 % 3;
                i2++;
            } else {
                return;
            }
        }
    }

    public ThreefishEngine(int blocksizeBits) {
        long[] jArr = new long[5];
        this.h = jArr;
        int i2 = blocksizeBits / 8;
        this.e = i2;
        int i3 = i2 / 8;
        this.f = i3;
        this.g = new long[i3];
        long[] jArr2 = new long[((i3 * 2) + 1)];
        this.i = jArr2;
        switch (blocksizeBits) {
            case 256:
                this.j = new Threefish256Cipher(jArr2, jArr);
                return;
            case 512:
                this.j = new Threefish512Cipher(jArr2, jArr);
                return;
            case 1024:
                this.j = new Threefish1024Cipher(jArr2, jArr);
                return;
            default:
                throw new IllegalArgumentException("Invalid blocksize - Threefish is defined with block size of 256, 512, or 1024 bits");
        }
    }

    public void a(boolean forEncryption, CipherParameters params) {
        byte[] keyBytes;
        byte[] tweakBytes;
        if (params instanceof TweakableBlockCipherParameters) {
            TweakableBlockCipherParameters tParams = (TweakableBlockCipherParameters) params;
            keyBytes = tParams.a().a();
            tweakBytes = tParams.b();
        } else if (params instanceof KeyParameter) {
            keyBytes = ((KeyParameter) params).a();
            tweakBytes = null;
        } else {
            throw new IllegalArgumentException("Invalid parameter passed to Threefish init - " + params.getClass().getName());
        }
        long[] keyWords = null;
        long[] tweakWords = null;
        if (keyBytes != null) {
            if (keyBytes.length == this.e) {
                keyWords = new long[this.f];
                for (int i2 = 0; i2 < keyWords.length; i2++) {
                    keyWords[i2] = i(keyBytes, i2 * 8);
                }
            } else {
                throw new IllegalArgumentException("Threefish key must be same size as block (" + this.e + " bytes)");
            }
        }
        if (tweakBytes != null) {
            if (tweakBytes.length == 16) {
                tweakWords = new long[]{i(tweakBytes, 0), i(tweakBytes, 8)};
            } else {
                throw new IllegalArgumentException("Threefish tweak must be 16 bytes");
            }
        }
        j(forEncryption, keyWords, tweakWords);
    }

    public void j(boolean forEncryption, long[] key, long[] tweak) {
        this.k = forEncryption;
        if (key != null) {
            m(key);
        }
        if (tweak != null) {
            n(tweak);
        }
    }

    private void m(long[] key) {
        if (key.length == this.f) {
            long knw = 2004413935125273122L;
            int i2 = 0;
            while (true) {
                int i3 = this.f;
                if (i2 < i3) {
                    long[] jArr = this.i;
                    jArr[i2] = key[i2];
                    knw ^= jArr[i2];
                    i2++;
                } else {
                    long[] jArr2 = this.i;
                    jArr2[i3] = knw;
                    System.arraycopy(jArr2, 0, jArr2, i3 + 1, i3);
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("Threefish key must be same size as block (" + this.f + " words)");
        }
    }

    private void n(long[] tweak) {
        if (tweak.length == 2) {
            long[] jArr = this.h;
            jArr[0] = tweak[0];
            jArr[1] = tweak[1];
            jArr[2] = jArr[0] ^ jArr[1];
            jArr[3] = jArr[0];
            jArr[4] = jArr[1];
            return;
        }
        throw new IllegalArgumentException("Tweak must be 2 words.");
    }

    public String b() {
        return "Threefish-" + (this.e * 8);
    }

    public int c() {
        return this.e;
    }

    public void reset() {
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        int i2 = this.e;
        if (inOff + i2 > in.length) {
            throw new DataLengthException("Input buffer too short");
        } else if (i2 + outOff <= out.length) {
            for (int i3 = 0; i3 < this.e; i3 += 8) {
                this.g[i3 >> 3] = i(in, inOff + i3);
            }
            long[] jArr = this.g;
            k(jArr, jArr);
            int i4 = 0;
            while (true) {
                int i5 = this.e;
                if (i4 >= i5) {
                    return i5;
                }
                o(this.g[i4 >> 3], out, outOff + i4);
                i4 += 8;
            }
        } else {
            throw new OutputLengthException("Output buffer too short");
        }
    }

    public int k(long[] in, long[] out) {
        long[] jArr = this.i;
        int i2 = this.f;
        if (jArr[i2] == 0) {
            throw new IllegalStateException("Threefish engine not initialised");
        } else if (in.length != i2) {
            throw new DataLengthException("Input buffer too short");
        } else if (out.length == i2) {
            if (this.k) {
                this.j.b(in, out);
            } else {
                this.j.a(in, out);
            }
            return this.f;
        } else {
            throw new OutputLengthException("Output buffer too short");
        }
    }

    public static long i(byte[] bytes, int off) {
        if (off + 8 <= bytes.length) {
            int index = off;
            int index2 = index + 1;
            int index3 = index2 + 1;
            long word = (((long) bytes[index]) & 255) | ((((long) bytes[index2]) & 255) << 8);
            int index4 = index3 + 1;
            int index5 = index4 + 1;
            long word2 = word | ((((long) bytes[index3]) & 255) << 16) | ((((long) bytes[index4]) & 255) << 24);
            int index6 = index5 + 1;
            int index7 = index6 + 1;
            long word3 = word2 | ((((long) bytes[index5]) & 255) << 32) | ((((long) bytes[index6]) & 255) << 40);
            int index8 = index7 + 1;
            int i2 = index8 + 1;
            return word3 | ((((long) bytes[index7]) & 255) << 48) | ((((long) bytes[index8]) & 255) << 56);
        }
        throw new IllegalArgumentException();
    }

    public static void o(long word, byte[] bytes, int off) {
        if (off + 8 <= bytes.length) {
            int index = off;
            int index2 = index + 1;
            bytes[index] = (byte) ((int) word);
            int index3 = index2 + 1;
            bytes[index2] = (byte) ((int) (word >> 8));
            int index4 = index3 + 1;
            bytes[index3] = (byte) ((int) (word >> 16));
            int index5 = index4 + 1;
            bytes[index4] = (byte) ((int) (word >> 24));
            int index6 = index5 + 1;
            bytes[index5] = (byte) ((int) (word >> 32));
            int index7 = index6 + 1;
            bytes[index6] = (byte) ((int) (word >> 40));
            int index8 = index7 + 1;
            bytes[index7] = (byte) ((int) (word >> 48));
            int i2 = index8 + 1;
            bytes[index8] = (byte) ((int) (word >> 56));
            return;
        }
        throw new IllegalArgumentException();
    }

    static long l(long x, int n, long xor) {
        return ((x << n) | (x >>> (-n))) ^ xor;
    }

    static long p(long x, int n, long xor) {
        long xored = x ^ xor;
        return (xored >>> n) | (xored << (-n));
    }

    public static abstract class ThreefishCipher {
        protected final long[] a;
        protected final long[] b;

        /* access modifiers changed from: package-private */
        public abstract void a(long[] jArr, long[] jArr2);

        /* access modifiers changed from: package-private */
        public abstract void b(long[] jArr, long[] jArr2);

        protected ThreefishCipher(long[] kw, long[] t) {
            this.b = kw;
            this.a = t;
        }
    }

    public static final class Threefish256Cipher extends ThreefishCipher {
        public Threefish256Cipher(long[] kw, long[] t) {
            super(kw, t);
        }

        /* access modifiers changed from: package-private */
        public void b(long[] block, long[] out) {
            long[] kw = this.b;
            long[] t = this.a;
            int[] mod5 = ThreefishEngine.c;
            int[] mod3 = ThreefishEngine.d;
            if (kw.length != 9) {
                throw new IllegalArgumentException();
            } else if (t.length == 5) {
                long b0 = block[0];
                long b1 = block[1];
                long b2 = block[2];
                long b3 = block[3];
                long b02 = b0 + kw[0];
                long b12 = b1 + kw[1] + t[0];
                long b22 = b2 + kw[2] + t[1];
                long b32 = b3 + kw[3];
                int d = 1;
                while (d < 18) {
                    int dm5 = mod5[d];
                    int dm3 = mod3[d];
                    int[] mod52 = mod5;
                    long j = b02 + b12;
                    long b03 = j;
                    long b13 = ThreefishEngine.l(b12, 14, j);
                    long j2 = b22 + b32;
                    long b23 = j2;
                    long b33 = ThreefishEngine.l(b32, 16, j2);
                    long j3 = b03 + b33;
                    long b04 = j3;
                    long b34 = ThreefishEngine.l(b33, 52, j3);
                    long j4 = b23 + b13;
                    long b24 = j4;
                    long b14 = ThreefishEngine.l(b13, 57, j4);
                    long j5 = b04 + b14;
                    long b05 = j5;
                    long b15 = ThreefishEngine.l(b14, 23, j5);
                    long b25 = b24 + b34;
                    long b35 = ThreefishEngine.l(b34, 40, b25);
                    long b06 = b05 + b35;
                    long b36 = ThreefishEngine.l(b35, 5, b06);
                    int dm52 = dm5;
                    long b26 = b25 + b15;
                    long b16 = ThreefishEngine.l(b15, 37, b26);
                    long b07 = b06 + kw[dm52];
                    long b17 = b16 + kw[dm52 + 1] + t[dm3];
                    long b27 = b26 + kw[dm52 + 2] + t[dm3 + 1];
                    long b37 = b36 + kw[dm52 + 3] + ((long) d);
                    long j6 = b07 + b17;
                    long b08 = j6;
                    long b18 = ThreefishEngine.l(b17, 25, j6);
                    long j7 = b27 + b37;
                    long b28 = j7;
                    long b38 = ThreefishEngine.l(b37, 33, j7);
                    long j8 = b08 + b38;
                    long b09 = j8;
                    long b39 = ThreefishEngine.l(b38, 46, j8);
                    long j9 = b28 + b18;
                    long b29 = j9;
                    long b19 = ThreefishEngine.l(b18, 12, j9);
                    long j10 = b09 + b19;
                    long b010 = j10;
                    long b110 = ThreefishEngine.l(b19, 58, j10);
                    long b210 = b29 + b39;
                    long b310 = ThreefishEngine.l(b39, 22, b210);
                    long j11 = b010 + b310;
                    long b011 = j11;
                    long b311 = ThreefishEngine.l(b310, 32, j11);
                    long b211 = b210 + b110;
                    long b111 = ThreefishEngine.l(b110, 32, b211);
                    b02 = b011 + kw[dm52 + 1];
                    b12 = b111 + kw[dm52 + 2] + t[dm3 + 1];
                    b22 = b211 + kw[dm52 + 3] + t[dm3 + 2];
                    b32 = b311 + kw[dm52 + 4] + ((long) d) + 1;
                    d += 2;
                    kw = kw;
                    mod5 = mod52;
                    mod3 = mod3;
                }
                out[0] = b02;
                out[1] = b12;
                out[2] = b22;
                out[3] = b32;
            } else {
                throw new IllegalArgumentException();
            }
        }

        /* access modifiers changed from: package-private */
        public void a(long[] block, long[] state) {
            long[] kw = this.b;
            long[] t = this.a;
            int[] mod5 = ThreefishEngine.c;
            int[] mod3 = ThreefishEngine.d;
            if (kw.length != 9) {
                throw new IllegalArgumentException();
            } else if (t.length == 5) {
                long b0 = block[0];
                long b1 = block[1];
                long b2 = block[2];
                long b3 = block[3];
                int d = 17;
                for (int i = 1; d >= i; i = 1) {
                    int dm5 = mod5[d];
                    int dm3 = mod3[d];
                    long b02 = b0 - kw[dm5 + 1];
                    long b12 = b1 - (kw[dm5 + 2] + t[dm3 + 1]);
                    long b22 = b2 - (kw[dm5 + 3] + t[dm3 + 2]);
                    long b32 = ThreefishEngine.p(b3 - ((kw[dm5 + 4] + ((long) d)) + 1), 32, b02);
                    long b03 = b02 - b32;
                    long b13 = ThreefishEngine.p(b12, 32, b22);
                    long b23 = b22 - b13;
                    long b14 = ThreefishEngine.p(b13, 58, b03);
                    long b04 = b03 - b14;
                    long b33 = ThreefishEngine.p(b32, 22, b23);
                    long b24 = b23 - b33;
                    long b34 = ThreefishEngine.p(b33, 46, b04);
                    long b05 = b04 - b34;
                    long b15 = ThreefishEngine.p(b14, 12, b24);
                    long b25 = b24 - b15;
                    long b16 = ThreefishEngine.p(b15, 25, b05);
                    long b35 = ThreefishEngine.p(b34, 33, b25);
                    long b06 = (b05 - b16) - kw[dm5];
                    long b17 = b16 - (kw[dm5 + 1] + t[dm3]);
                    long b26 = (b25 - b35) - (kw[dm5 + 2] + t[dm3 + 1]);
                    long b36 = ThreefishEngine.p(b35 - (kw[dm5 + 3] + ((long) d)), 5, b06);
                    long b07 = b06 - b36;
                    int[] mod52 = mod5;
                    long b18 = ThreefishEngine.p(b17, 37, b26);
                    long b27 = b26 - b18;
                    long b19 = ThreefishEngine.p(b18, 23, b07);
                    long b08 = b07 - b19;
                    long b37 = ThreefishEngine.p(b36, 40, b27);
                    long b28 = b27 - b37;
                    long b38 = ThreefishEngine.p(b37, 52, b08);
                    long b09 = b08 - b38;
                    long b110 = ThreefishEngine.p(b19, 57, b28);
                    long b29 = b28 - b110;
                    b1 = ThreefishEngine.p(b110, 14, b09);
                    b0 = b09 - b1;
                    b3 = ThreefishEngine.p(b38, 16, b29);
                    b2 = b29 - b3;
                    d -= 2;
                    mod5 = mod52;
                    mod3 = mod3;
                }
                int[] iArr = mod3;
                long b111 = b1 - (kw[1] + t[0]);
                long b210 = b2 - (kw[2] + t[1]);
                state[0] = b0 - kw[0];
                state[1] = b111;
                state[2] = b210;
                state[3] = b3 - kw[3];
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public static final class Threefish512Cipher extends ThreefishCipher {
        protected Threefish512Cipher(long[] kw, long[] t) {
            super(kw, t);
        }

        public void b(long[] block, long[] out) {
            long[] kw = this.b;
            long[] t = this.a;
            int[] mod9 = ThreefishEngine.a;
            int[] mod3 = ThreefishEngine.d;
            if (kw.length != 17) {
                throw new IllegalArgumentException();
            } else if (t.length == 5) {
                long b0 = block[0];
                long b1 = block[1];
                long b2 = block[2];
                long b3 = block[3];
                long b4 = block[4];
                long b5 = block[5];
                long b6 = block[6];
                long b7 = block[7];
                long b02 = b0 + kw[0];
                long b42 = b1 + kw[1];
                long b22 = b2 + kw[2];
                long b32 = b3 + kw[3];
                long b43 = b4 + kw[4];
                long b52 = b5 + kw[5] + t[0];
                long b62 = b6 + kw[6] + t[1];
                long b33 = b32;
                long b53 = b52;
                long b72 = b7 + kw[7];
                int d = 1;
                while (d < 18) {
                    int dm9 = mod9[d];
                    int dm3 = mod3[d];
                    int[] mod92 = mod9;
                    long j = b02 + b42;
                    long b03 = j;
                    long b12 = ThreefishEngine.l(b42, 46, j);
                    long b23 = b22 + b33;
                    long b34 = ThreefishEngine.l(b33, 36, b23);
                    long[] kw2 = kw;
                    int dm92 = dm9;
                    long b54 = b53;
                    long j2 = b43 + b54;
                    long b44 = j2;
                    long b55 = ThreefishEngine.l(b54, 19, j2);
                    long b35 = b34;
                    long b73 = b72;
                    long b36 = b62 + b73;
                    long b63 = b36;
                    long b74 = ThreefishEngine.l(b73, 37, b36);
                    long j3 = b23 + b12;
                    long b24 = j3;
                    long b13 = ThreefishEngine.l(b12, 33, j3);
                    long j4 = b44 + b74;
                    long b45 = j4;
                    long b75 = ThreefishEngine.l(b74, 27, j4);
                    long j5 = b63 + b55;
                    long b64 = j5;
                    long b56 = ThreefishEngine.l(b55, 14, j5);
                    long j6 = b03 + b35;
                    long b04 = j6;
                    long b76 = b75;
                    long b37 = ThreefishEngine.l(b35, 42, j6);
                    long j7 = b45 + b13;
                    long b46 = j7;
                    long b14 = ThreefishEngine.l(b13, 17, j7);
                    long j8 = b64 + b37;
                    long b65 = j8;
                    long b38 = ThreefishEngine.l(b37, 49, j8);
                    long b05 = b04 + b56;
                    long b57 = ThreefishEngine.l(b56, 36, b05);
                    long b25 = b24 + b76;
                    long b39 = b38;
                    long b77 = ThreefishEngine.l(b76, 39, b25);
                    long j9 = b65 + b14;
                    long b66 = j9;
                    long b15 = ThreefishEngine.l(b14, 44, j9);
                    long j10 = b05 + b77;
                    long b06 = j10;
                    long b78 = ThreefishEngine.l(b77, 9, j10);
                    long j11 = b25 + b57;
                    long b26 = j11;
                    long b58 = ThreefishEngine.l(b57, 54, j11);
                    long b47 = b46 + b39;
                    long b310 = ThreefishEngine.l(b39, 56, b47);
                    long b07 = b06 + kw2[dm92];
                    long b16 = b15 + kw2[dm92 + 1];
                    long b27 = b26 + kw2[dm92 + 2];
                    long b311 = b310 + kw2[dm92 + 3];
                    long b48 = b47 + kw2[dm92 + 4];
                    long b59 = b58 + kw2[dm92 + 5] + t[dm3];
                    long b67 = b66 + kw2[dm92 + 6] + t[dm3 + 1];
                    long b79 = b78 + kw2[dm92 + 7] + ((long) d);
                    long b08 = b07 + b16;
                    long b17 = ThreefishEngine.l(b16, 39, b08);
                    long b312 = b311;
                    int d2 = d;
                    long j12 = b27 + b312;
                    long b28 = j12;
                    long b313 = ThreefishEngine.l(b312, 30, j12);
                    long j13 = b48 + b59;
                    long b49 = j13;
                    long b510 = ThreefishEngine.l(b59, 34, j13);
                    long j14 = b67 + b79;
                    long b68 = j14;
                    long b710 = ThreefishEngine.l(b79, 24, j14);
                    long j15 = b28 + b17;
                    long b29 = j15;
                    long b18 = ThreefishEngine.l(b17, 13, j15);
                    long j16 = b49 + b710;
                    long b410 = j16;
                    long b711 = ThreefishEngine.l(b710, 50, j16);
                    long b69 = b68 + b510;
                    long b511 = ThreefishEngine.l(b510, 10, b69);
                    long b09 = b08 + b313;
                    long b314 = ThreefishEngine.l(b313, 17, b09);
                    long b411 = b410 + b18;
                    long b19 = ThreefishEngine.l(b18, 25, b411);
                    long b610 = b69 + b314;
                    long b315 = ThreefishEngine.l(b314, 29, b610);
                    long b010 = b09 + b511;
                    long b512 = ThreefishEngine.l(b511, 39, b010);
                    long b316 = b315;
                    long b317 = b29 + b711;
                    long b210 = b317;
                    long b712 = ThreefishEngine.l(b711, 43, b317);
                    long b611 = b610 + b19;
                    long b110 = ThreefishEngine.l(b19, 8, b611);
                    long b612 = b611;
                    long j17 = b010 + b712;
                    long b011 = j17;
                    long b713 = ThreefishEngine.l(b712, 35, j17);
                    long j18 = b210 + b512;
                    long b211 = j18;
                    long b513 = ThreefishEngine.l(b512, 56, j18);
                    long b412 = b411 + b316;
                    long b318 = ThreefishEngine.l(b316, 22, b412);
                    long b012 = b011 + kw2[dm92 + 1];
                    long b111 = b110 + kw2[dm92 + 2];
                    b22 = b211 + kw2[dm92 + 3];
                    long b319 = b318 + kw2[dm92 + 4];
                    long b413 = b412 + kw2[dm92 + 5];
                    long b514 = b513 + kw2[dm92 + 6] + t[dm3 + 1];
                    b62 = b612 + kw2[dm92 + 7] + t[dm3 + 2];
                    int d3 = d2;
                    b53 = b514;
                    b72 = b713 + kw2[dm92 + 8] + ((long) d3) + 1;
                    b33 = b319;
                    b02 = b012;
                    b43 = b413;
                    kw = kw2;
                    d = d3 + 2;
                    b42 = b111;
                    mod9 = mod92;
                    mod3 = mod3;
                }
                out[0] = b02;
                out[1] = b42;
                out[2] = b22;
                out[3] = b33;
                out[4] = b43;
                out[5] = b53;
                out[6] = b62;
                out[7] = b72;
            } else {
                throw new IllegalArgumentException();
            }
        }

        public void a(long[] block, long[] state) {
            long[] kw = this.b;
            long[] t = this.a;
            int[] mod9 = ThreefishEngine.a;
            int[] mod3 = ThreefishEngine.d;
            if (kw.length != 17) {
                throw new IllegalArgumentException();
            } else if (t.length == 5) {
                long b0 = block[0];
                int i = 1;
                long b1 = block[1];
                long b2 = block[2];
                long b3 = block[3];
                long b4 = block[4];
                long b5 = block[5];
                long b6 = block[6];
                long b7 = block[7];
                int d = 17;
                while (d >= i) {
                    int dm9 = mod9[d];
                    int dm3 = mod3[d];
                    long b02 = b0 - kw[dm9 + 1];
                    long b12 = b1 - kw[dm9 + 2];
                    long b22 = b2 - kw[dm9 + 3];
                    long b32 = b3 - kw[dm9 + 4];
                    long b42 = b4 - kw[dm9 + 5];
                    long b52 = b5 - (kw[dm9 + 6] + t[dm3 + 1]);
                    int[] mod92 = mod9;
                    int[] mod32 = mod3;
                    long b62 = b6 - (kw[dm9 + 7] + t[dm3 + 2]);
                    long[] kw2 = kw;
                    long b72 = b7 - ((kw[dm9 + 8] + ((long) d)) + 1);
                    long b33 = b32;
                    long b13 = ThreefishEngine.p(b12, 8, b62);
                    long b63 = b62 - b13;
                    long b73 = ThreefishEngine.p(b72, 35, b02);
                    long b03 = b02 - b73;
                    long b74 = b73;
                    long b23 = b22;
                    long b53 = ThreefishEngine.p(b52, 56, b23);
                    long b24 = b23 - b53;
                    long b34 = ThreefishEngine.p(b33, 22, b42);
                    long b43 = b42 - b34;
                    long b14 = ThreefishEngine.p(b13, 25, b43);
                    long b44 = b43 - b14;
                    long b35 = ThreefishEngine.p(b34, 29, b63);
                    long b64 = b63 - b35;
                    long b54 = ThreefishEngine.p(b53, 39, b03);
                    long b36 = b35;
                    int d2 = d;
                    long b25 = b24;
                    long b75 = ThreefishEngine.p(b74, 43, b25);
                    long b26 = b25 - b75;
                    long b15 = ThreefishEngine.p(b14, 13, b26);
                    long b27 = b26 - b15;
                    long b76 = ThreefishEngine.p(b75, 50, b44);
                    long b45 = b44 - b76;
                    long b55 = ThreefishEngine.p(b54, 10, b64);
                    long b65 = b64 - b55;
                    long b77 = b76;
                    long b78 = b03 - b54;
                    long b37 = ThreefishEngine.p(b36, 17, b78);
                    long b04 = b78 - b37;
                    long b16 = ThreefishEngine.p(b15, 39, b04);
                    long b38 = ThreefishEngine.p(b37, 30, b27);
                    long b56 = ThreefishEngine.p(b55, 34, b45);
                    long b46 = b45 - b56;
                    long b57 = b56;
                    long b79 = ThreefishEngine.p(b77, 24, b65);
                    long b05 = (b04 - b16) - kw2[dm9];
                    long b17 = b16 - kw2[dm9 + 1];
                    long b28 = (b27 - b38) - kw2[dm9 + 2];
                    long b39 = b38 - kw2[dm9 + 3];
                    long b47 = b46 - kw2[dm9 + 4];
                    long b310 = b39;
                    long b311 = b57 - (kw2[dm9 + 5] + t[dm3]);
                    long b66 = (b65 - b79) - (kw2[dm9 + 6] + t[dm3 + 1]);
                    long j = kw2[dm9 + 7];
                    int d3 = d2;
                    long b18 = ThreefishEngine.p(b17, 44, b66);
                    long b67 = b66 - b18;
                    long b710 = ThreefishEngine.p(b79 - (j + ((long) d3)), 9, b05);
                    long b06 = b05 - b710;
                    long b58 = ThreefishEngine.p(b311, 54, b28);
                    long[] t2 = t;
                    long b29 = b28 - b58;
                    long b210 = b47;
                    long b312 = ThreefishEngine.p(b310, 56, b210);
                    long b48 = b210 - b312;
                    long b19 = ThreefishEngine.p(b18, 17, b48);
                    long b49 = b48 - b19;
                    long b313 = ThreefishEngine.p(b312, 49, b67);
                    long b59 = ThreefishEngine.p(b58, 36, b06);
                    long b07 = b06 - b59;
                    long b211 = b29;
                    long b711 = ThreefishEngine.p(b710, 39, b211);
                    long b212 = b211 - b711;
                    long b110 = ThreefishEngine.p(b19, 33, b212);
                    long b213 = b212 - b110;
                    long b712 = ThreefishEngine.p(b711, 27, b49);
                    long b410 = b49 - b712;
                    long b713 = b712;
                    long b68 = b67 - b313;
                    long b510 = ThreefishEngine.p(b59, 14, b68);
                    long b69 = b68 - b510;
                    long b314 = ThreefishEngine.p(b313, 42, b07);
                    long b08 = b07 - b314;
                    long b111 = ThreefishEngine.p(b110, 46, b08);
                    long b09 = b08 - b111;
                    b3 = ThreefishEngine.p(b314, 36, b213);
                    b5 = ThreefishEngine.p(b510, 19, b410);
                    b7 = ThreefishEngine.p(b713, 37, b69);
                    b6 = b69 - b7;
                    b2 = b213 - b3;
                    b1 = b111;
                    b4 = b410 - b5;
                    d = d3 - 2;
                    t = t2;
                    mod9 = mod92;
                    mod3 = mod32;
                    i = 1;
                    b0 = b09;
                    kw = kw2;
                }
                long[] kw3 = kw;
                long[] t3 = t;
                int[] iArr = mod9;
                long b010 = b0 - kw3[0];
                long b112 = b1 - kw3[1];
                long b214 = b2 - kw3[2];
                long b315 = b3 - kw3[3];
                long b411 = b4 - kw3[4];
                long b610 = b6 - (kw3[6] + t3[1]);
                state[0] = b010;
                state[1] = b112;
                state[2] = b214;
                state[3] = b315;
                state[4] = b411;
                state[5] = b5 - (kw3[5] + t3[0]);
                state[6] = b610;
                state[7] = b7 - kw3[7];
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public static final class Threefish1024Cipher extends ThreefishCipher {
        public Threefish1024Cipher(long[] kw, long[] t) {
            super(kw, t);
        }

        /* access modifiers changed from: package-private */
        public void b(long[] block, long[] out) {
            long[] kw = this.b;
            long[] t = this.a;
            int[] mod17 = ThreefishEngine.b;
            int[] mod3 = ThreefishEngine.d;
            if (kw.length != 33) {
                throw new IllegalArgumentException();
            } else if (t.length == 5) {
                long b0 = block[0];
                long b1 = block[1];
                long b2 = block[2];
                long b3 = block[3];
                long b4 = block[4];
                long b5 = block[5];
                long b6 = block[6];
                long b7 = block[7];
                long b8 = block[8];
                long b9 = block[9];
                long b10 = block[10];
                long b11 = block[11];
                long b12 = block[12];
                long b13 = block[13];
                long b14 = block[14];
                long b15 = block[15];
                long b02 = b0 + kw[0];
                long b03 = b1 + kw[1];
                long b22 = b2 + kw[2];
                long b32 = b3 + kw[3];
                long b42 = b4 + kw[4];
                long b52 = b5 + kw[5];
                long b33 = b6 + kw[6];
                long b72 = b7 + kw[7];
                long b82 = b8 + kw[8];
                long b92 = b9 + kw[9];
                long b102 = b10 + kw[10];
                long b112 = b11 + kw[11];
                long b122 = b12 + kw[12];
                long b132 = b13 + kw[13] + t[0];
                long b23 = b14 + kw[14] + t[1];
                long b34 = b32;
                long b53 = b52;
                long b73 = b72;
                long b93 = b92;
                long b113 = b112;
                long b133 = b132;
                long b152 = b15 + kw[15];
                int d = 1;
                while (d < 20) {
                    int dm17 = mod17[d];
                    int dm3 = mod3[d];
                    int[] mod172 = mod17;
                    int[] mod32 = mod3;
                    long j = b02 + b03;
                    long b04 = j;
                    long b16 = ThreefishEngine.l(b03, 24, j);
                    long j2 = b22 + b34;
                    long b24 = j2;
                    long b35 = ThreefishEngine.l(b34, 13, j2);
                    int d2 = d;
                    long b54 = b53;
                    long j3 = b42 + b54;
                    long b43 = j3;
                    long b55 = ThreefishEngine.l(b54, 8, j3);
                    long[] kw2 = kw;
                    long b74 = b73;
                    long j4 = b33 + b74;
                    long b62 = j4;
                    long b75 = ThreefishEngine.l(b74, 47, j4);
                    long[] t2 = t;
                    long b17 = b16;
                    long b94 = b93;
                    long j5 = b82 + b94;
                    long b83 = j5;
                    long b95 = ThreefishEngine.l(b94, 8, j5);
                    long b56 = b55;
                    long b114 = b113;
                    long b57 = b102 + b114;
                    long b103 = b57;
                    long b115 = ThreefishEngine.l(b114, 17, b57);
                    long b36 = b35;
                    long b134 = b133;
                    long b37 = b122 + b134;
                    long b135 = ThreefishEngine.l(b134, 22, b37);
                    long b76 = b75;
                    long b153 = b152;
                    long b77 = b23 + b153;
                    long b142 = b77;
                    long b154 = ThreefishEngine.l(b153, 37, b77);
                    long b155 = b04 + b95;
                    long b05 = b155;
                    long b96 = ThreefishEngine.l(b95, 38, b155);
                    long j6 = b24 + b135;
                    long b25 = j6;
                    long b136 = ThreefishEngine.l(b135, 19, j6);
                    long b137 = b62 + b115;
                    long b63 = b137;
                    long b116 = ThreefishEngine.l(b115, 10, b137);
                    long j7 = b43 + b154;
                    long b44 = j7;
                    long b97 = b96;
                    long b156 = ThreefishEngine.l(b154, 55, j7);
                    long j8 = b103 + b76;
                    long b104 = j8;
                    long b117 = b116;
                    long b78 = ThreefishEngine.l(b76, 49, j8);
                    long j9 = b37 + b36;
                    long b123 = j9;
                    long b138 = b136;
                    long b38 = ThreefishEngine.l(b36, 18, j9);
                    long j10 = b142 + b56;
                    long b143 = j10;
                    long b157 = b156;
                    long b58 = ThreefishEngine.l(b56, 23, j10);
                    long j11 = b83 + b17;
                    long b84 = j11;
                    long b64 = b63;
                    long b18 = ThreefishEngine.l(b17, 52, j11);
                    long j12 = b05 + b78;
                    long b06 = j12;
                    long b79 = ThreefishEngine.l(b78, 33, j12);
                    long j13 = b25 + b58;
                    long b26 = j13;
                    long b59 = ThreefishEngine.l(b58, 4, j13);
                    long j14 = b44 + b38;
                    long b45 = j14;
                    long b39 = ThreefishEngine.l(b38, 51, j14);
                    long j15 = b64 + b18;
                    long b65 = j15;
                    long b19 = ThreefishEngine.l(b18, 13, j15);
                    long j16 = b123 + b157;
                    long b124 = j16;
                    long b710 = b79;
                    long b158 = ThreefishEngine.l(b157, 34, j16);
                    long j17 = b143 + b138;
                    long b144 = j17;
                    long b310 = b39;
                    long b139 = ThreefishEngine.l(b138, 41, j17);
                    long j18 = b84 + b117;
                    long b85 = j18;
                    long b110 = b19;
                    long b118 = ThreefishEngine.l(b117, 59, j18);
                    long b105 = b104 + b97;
                    long b98 = ThreefishEngine.l(b97, 17, b105);
                    long j19 = b06 + b158;
                    long b07 = j19;
                    long b159 = ThreefishEngine.l(b158, 5, j19);
                    long j20 = b26 + b118;
                    long b27 = j20;
                    long b119 = ThreefishEngine.l(b118, 20, j20);
                    long b1510 = b159;
                    long b1511 = b65 + b139;
                    long b66 = b1511;
                    long b1310 = ThreefishEngine.l(b139, 48, b1511);
                    long j21 = b45 + b98;
                    long b46 = j21;
                    long b99 = ThreefishEngine.l(b98, 41, j21);
                    long b910 = b144 + b110;
                    long b145 = b910;
                    long b146 = b1310;
                    long b111 = ThreefishEngine.l(b110, 47, b910);
                    long j22 = b85 + b59;
                    long b86 = j22;
                    long b510 = ThreefishEngine.l(b59, 28, j22);
                    long b106 = b105 + b310;
                    long b47 = b46;
                    long j23 = b124 + b710;
                    long b125 = j23;
                    long b711 = ThreefishEngine.l(b710, 25, j23);
                    long b08 = b07 + kw2[dm17];
                    long b120 = b111 + kw2[dm17 + 1];
                    long b28 = b27 + kw2[dm17 + 2];
                    long b311 = ThreefishEngine.l(b310, 16, b106) + kw2[dm17 + 3];
                    long b48 = b47 + kw2[dm17 + 4];
                    long b511 = b510 + kw2[dm17 + 5];
                    long b67 = b66 + kw2[dm17 + 6];
                    long b712 = b711 + kw2[dm17 + 7];
                    long b87 = b86 + kw2[dm17 + 8];
                    long b911 = b99 + kw2[dm17 + 9];
                    long b107 = b106 + kw2[dm17 + 10];
                    long b1110 = b119 + kw2[dm17 + 11];
                    long b126 = b125 + kw2[dm17 + 12];
                    long b1311 = b146 + kw2[dm17 + 13] + t2[dm3];
                    long b147 = b145 + kw2[dm17 + 14] + t2[dm3 + 1];
                    long b713 = b712;
                    long b1312 = b1311;
                    long j24 = b08 + b120;
                    long b09 = j24;
                    long b1512 = b1510 + kw2[dm17 + 15] + ((long) d2);
                    long b121 = ThreefishEngine.l(b120, 41, j24);
                    long b714 = b713;
                    long j25 = b28 + b311;
                    long b29 = j25;
                    long b127 = b121;
                    long b312 = ThreefishEngine.l(b311, 9, j25);
                    long j26 = b48 + b511;
                    long b49 = j26;
                    long b313 = b312;
                    long b512 = ThreefishEngine.l(b511, 37, j26);
                    long j27 = b67 + b714;
                    long b68 = j27;
                    long b69 = b512;
                    long b715 = ThreefishEngine.l(b714, 31, j27);
                    long b716 = b87 + b911;
                    long b88 = b716;
                    long b912 = ThreefishEngine.l(b911, 12, b716);
                    long j28 = b107 + b1110;
                    long b108 = j28;
                    long b1111 = ThreefishEngine.l(b1110, 47, j28);
                    long j29 = b126 + b1312;
                    long b128 = j29;
                    long b717 = b715;
                    long b1313 = ThreefishEngine.l(b1312, 44, j29);
                    long j30 = b147 + b1512;
                    long b148 = j30;
                    long b1513 = ThreefishEngine.l(b1512, 30, j30);
                    long j31 = b09 + b912;
                    long b010 = j31;
                    long b913 = ThreefishEngine.l(b912, 16, j31);
                    long j32 = b29 + b1313;
                    long b210 = j32;
                    long b1314 = ThreefishEngine.l(b1313, 34, j32);
                    long j33 = b68 + b1111;
                    long b610 = j33;
                    long b1112 = ThreefishEngine.l(b1111, 56, j33);
                    long j34 = b49 + b1513;
                    long b410 = j34;
                    long b1514 = ThreefishEngine.l(b1513, 51, j34);
                    long j35 = b108 + b717;
                    long b109 = j35;
                    long b211 = b210;
                    long b718 = ThreefishEngine.l(b717, 4, j35);
                    long j36 = b128 + b313;
                    long b129 = j36;
                    long b719 = b718;
                    long b314 = ThreefishEngine.l(b313, 53, j36);
                    long b149 = b148 + b69;
                    long b914 = b913;
                    long b513 = ThreefishEngine.l(b69, 42, b149);
                    long j37 = b88 + b127;
                    long b89 = j37;
                    long b1113 = b1112;
                    long b1114 = ThreefishEngine.l(b127, 41, j37);
                    long j38 = b010 + b719;
                    long b011 = j38;
                    long b1315 = b1314;
                    long b720 = ThreefishEngine.l(b719, 31, j38);
                    long j39 = b211 + b513;
                    long b212 = j39;
                    long b514 = ThreefishEngine.l(b513, 44, j39);
                    long b515 = b410 + b314;
                    long b411 = b515;
                    long b721 = b720;
                    long b315 = ThreefishEngine.l(b314, 47, b515);
                    long j40 = b610 + b1114;
                    long b611 = j40;
                    long b130 = ThreefishEngine.l(b1114, 46, j40);
                    long j41 = b129 + b1514;
                    long b1210 = j41;
                    long b1515 = ThreefishEngine.l(b1514, 19, j41);
                    long j42 = b149 + b1315;
                    long b1410 = j42;
                    long b316 = b315;
                    long b1316 = ThreefishEngine.l(b1315, 42, j42);
                    long j43 = b89 + b1113;
                    long b810 = j43;
                    long b516 = b514;
                    long b1115 = ThreefishEngine.l(b1113, 44, j43);
                    long j44 = b109 + b914;
                    long b1010 = j44;
                    long b131 = b130;
                    long b915 = ThreefishEngine.l(b914, 25, j44);
                    long b012 = b011 + b1515;
                    long b1516 = ThreefishEngine.l(b1515, 9, b012);
                    long j45 = b212 + b1115;
                    long b213 = j45;
                    long b1116 = ThreefishEngine.l(b1115, 48, j45);
                    long j46 = b611 + b1316;
                    long b612 = j46;
                    long b1317 = ThreefishEngine.l(b1316, 35, j46);
                    long j47 = b411 + b915;
                    long b412 = j47;
                    long b916 = ThreefishEngine.l(b915, 52, j47);
                    long b1411 = b1410 + b131;
                    long b1517 = b1516;
                    long b140 = ThreefishEngine.l(b131, 23, b1411);
                    long j48 = b810 + b516;
                    long b811 = j48;
                    long b1318 = b1317;
                    long b517 = ThreefishEngine.l(b516, 31, j48);
                    long j49 = b1010 + b316;
                    long b1011 = j49;
                    long b1117 = b1116;
                    long b317 = ThreefishEngine.l(b316, 37, j49);
                    long j50 = b1210 + b721;
                    long b1211 = j50;
                    long b1212 = b916;
                    long b722 = ThreefishEngine.l(b721, 20, j50);
                    long b013 = b012 + kw2[dm17 + 1];
                    long b141 = b140 + kw2[dm17 + 2];
                    long b214 = b213 + kw2[dm17 + 3];
                    long b318 = b317 + kw2[dm17 + 4];
                    long b518 = b517 + kw2[dm17 + 6];
                    long b613 = b612 + kw2[dm17 + 7];
                    b73 = b722 + kw2[dm17 + 8];
                    b82 = b811 + kw2[dm17 + 9];
                    long b917 = b1212 + kw2[dm17 + 10];
                    b102 = b1011 + kw2[dm17 + 11];
                    b113 = b1117 + kw2[dm17 + 12];
                    b122 = b1211 + kw2[dm17 + 13];
                    b133 = b1318 + kw2[dm17 + 14] + t2[dm3 + 1];
                    long b1412 = b1411 + kw2[dm17 + 15] + t2[dm3 + 2];
                    long b319 = b318;
                    long b614 = b613;
                    int d3 = d2;
                    b152 = b1517 + kw2[dm17 + 16] + ((long) d3) + 1;
                    b53 = b518;
                    b93 = b917;
                    b42 = b412 + kw2[dm17 + 5];
                    b34 = b319;
                    mod17 = mod172;
                    mod3 = mod32;
                    kw = kw2;
                    t = t2;
                    b33 = b614;
                    d = d3 + 2;
                    b02 = b013;
                    b03 = b141;
                    b22 = b214;
                    b23 = b1412;
                }
                int[] iArr = mod17;
                out[0] = b02;
                out[1] = b03;
                out[2] = b22;
                out[3] = b34;
                out[4] = b42;
                out[5] = b53;
                out[6] = b33;
                out[7] = b73;
                out[8] = b82;
                out[9] = b93;
                out[10] = b102;
                out[11] = b113;
                out[12] = b122;
                out[13] = b133;
                out[14] = b23;
                out[15] = b152;
            } else {
                throw new IllegalArgumentException();
            }
        }

        /* access modifiers changed from: package-private */
        public void a(long[] block, long[] state) {
            long[] kw = this.b;
            long[] t = this.a;
            int[] mod17 = ThreefishEngine.b;
            int[] mod3 = ThreefishEngine.d;
            if (kw.length != 33) {
                throw new IllegalArgumentException();
            } else if (t.length == 5) {
                long b0 = block[0];
                int i = 1;
                long b3 = block[1];
                long b2 = block[2];
                long b32 = block[3];
                long b6 = block[4];
                long b7 = block[5];
                long b13 = block[6];
                long b02 = block[7];
                long b12 = block[8];
                long b72 = block[9];
                long b122 = block[10];
                long b11 = block[11];
                long b8 = block[12];
                long b112 = block[13];
                long b14 = block[14];
                long b15 = block[15];
                int d = 19;
                while (d >= i) {
                    int dm17 = mod17[d];
                    int dm3 = mod3[d];
                    long b03 = b0 - kw[dm17 + 1];
                    long b1 = b3 - kw[dm17 + 2];
                    long b22 = b2 - kw[dm17 + 3];
                    long b33 = b32 - kw[dm17 + 4];
                    long b4 = b6 - kw[dm17 + 5];
                    long b5 = b7 - kw[dm17 + 6];
                    int[] mod172 = mod17;
                    int[] mod32 = mod3;
                    long b62 = b13 - kw[dm17 + 7];
                    long b73 = b02 - kw[dm17 + 8];
                    long b74 = b12 - kw[dm17 + 9];
                    long b34 = b33;
                    long b35 = b72 - kw[dm17 + 10];
                    long b52 = b5;
                    long b10 = b122 - kw[dm17 + 11];
                    long b113 = b11 - kw[dm17 + 12];
                    long b82 = b74;
                    long b123 = b8 - kw[dm17 + 13];
                    long b124 = b112 - (kw[dm17 + 14] + t[dm3 + 1]);
                    long b9 = b35;
                    long b142 = b14 - (kw[dm17 + 15] + t[dm3 + 2]);
                    int d2 = d;
                    long[] kw2 = kw;
                    int d3 = d2;
                    long b152 = ThreefishEngine.p(b15 - ((kw[dm17 + 16] + ((long) d2)) + 1), 9, b03);
                    long b04 = b03 - b152;
                    long b153 = b152;
                    long b23 = b22;
                    long b114 = ThreefishEngine.p(b113, 48, b23);
                    long b24 = b23 - b114;
                    long b132 = ThreefishEngine.p(b124, 35, b62);
                    long b63 = b62 - b132;
                    long b115 = b114;
                    long b92 = ThreefishEngine.p(b9, 52, b4);
                    long b42 = b4 - b92;
                    long b93 = b92;
                    long b16 = ThreefishEngine.p(b1, 23, b142);
                    long b143 = b142 - b16;
                    long b144 = b52;
                    long b133 = b132;
                    long b83 = b82;
                    long b53 = ThreefishEngine.p(b144, 31, b83);
                    long[] t2 = t;
                    long b84 = b83 - b53;
                    long b102 = b10;
                    long b36 = ThreefishEngine.p(b34, 37, b102);
                    long b103 = b102 - b36;
                    long b125 = b123;
                    long b75 = ThreefishEngine.p(b73, 20, b125);
                    long b126 = b125 - b75;
                    long b76 = ThreefishEngine.p(b75, 31, b04);
                    long b54 = ThreefishEngine.p(b53, 44, b24);
                    long b25 = b24 - b54;
                    long b43 = b42;
                    long b37 = ThreefishEngine.p(b36, 47, b43);
                    long b44 = b43 - b37;
                    long b38 = b37;
                    long b64 = b63;
                    long b65 = b54;
                    long b17 = ThreefishEngine.p(b16, 46, b64);
                    long b66 = b64 - b17;
                    long b154 = ThreefishEngine.p(b153, 19, b126);
                    long b127 = b126 - b154;
                    long b155 = b154;
                    long b156 = b133;
                    long b18 = b17;
                    long b145 = b143;
                    long b134 = ThreefishEngine.p(b156, 42, b145);
                    long b146 = b145 - b134;
                    long b85 = b84;
                    long b135 = b134;
                    long b45 = b44;
                    long b116 = ThreefishEngine.p(b115, 44, b85);
                    long b67 = b66;
                    long j = b127;
                    long b128 = b155;
                    long b129 = j;
                    long b86 = b85 - b116;
                    long b94 = ThreefishEngine.p(b93, 25, b103);
                    long b104 = b103 - b94;
                    long b05 = b04 - b76;
                    long b95 = ThreefishEngine.p(b94, 16, b05);
                    long b06 = b05 - b95;
                    long b136 = ThreefishEngine.p(b135, 34, b25);
                    long b26 = b25 - b136;
                    long b117 = ThreefishEngine.p(b116, 56, b67);
                    long b68 = b67 - b117;
                    long b137 = b136;
                    long b46 = b45;
                    long b157 = ThreefishEngine.p(b128, 51, b46);
                    long b47 = b46 - b157;
                    long b158 = b157;
                    long j2 = b117;
                    long b118 = b104;
                    long b119 = j2;
                    long b77 = ThreefishEngine.p(b76, 4, b118);
                    long b78 = b77;
                    long j3 = b118 - b77;
                    long b1210 = b129;
                    long b1211 = j3;
                    long b39 = ThreefishEngine.p(b38, 53, b1210);
                    long b1212 = b1210 - b39;
                    long b1213 = b65;
                    long b96 = b95;
                    long b147 = b146;
                    long b55 = ThreefishEngine.p(b1213, 42, b147);
                    long b148 = b147 - b55;
                    long b69 = b68;
                    long b610 = b86;
                    long b19 = ThreefishEngine.p(b18, 41, b610);
                    long b87 = b610 - b19;
                    long b110 = ThreefishEngine.p(b19, 41, b06);
                    long b310 = ThreefishEngine.p(b39, 9, b26);
                    long b56 = ThreefishEngine.p(b55, 37, b47);
                    long b159 = b158;
                    long j4 = b26 - b310;
                    long b27 = b78;
                    long b28 = j4;
                    long b48 = b47 - b56;
                    long b57 = b56;
                    long b611 = b69;
                    long b79 = ThreefishEngine.p(b27, 31, b611);
                    long b612 = b611 - b79;
                    long b710 = b79;
                    long b97 = ThreefishEngine.p(b96, 12, b87);
                    long b88 = b87 - b97;
                    long b98 = b97;
                    long b1110 = b119;
                    long b1111 = b88;
                    long b105 = b1211;
                    long b1112 = ThreefishEngine.p(b1110, 47, b105);
                    long b1113 = b1112;
                    long b1214 = b105 - b1112;
                    long b1215 = b1212;
                    long b138 = ThreefishEngine.p(b137, 44, b1215);
                    long b1216 = b1215 - b138;
                    long b139 = b138;
                    long b149 = b148;
                    long b1510 = ThreefishEngine.p(b159, 30, b149);
                    long b07 = (b06 - b110) - kw2[dm17];
                    long b111 = b110 - kw2[dm17 + 1];
                    long b29 = b28 - kw2[dm17 + 2];
                    long b311 = b310 - kw2[dm17 + 3];
                    long b49 = b48 - kw2[dm17 + 4];
                    long b312 = b311;
                    long b313 = b57 - kw2[dm17 + 5];
                    long b613 = b612 - kw2[dm17 + 6];
                    long b58 = b313;
                    long b711 = b710 - kw2[dm17 + 7];
                    long b89 = b1111 - kw2[dm17 + 8];
                    long b810 = b98 - kw2[dm17 + 9];
                    long b410 = b49;
                    long b106 = b1214 - kw2[dm17 + 10];
                    long b1114 = b1113 - kw2[dm17 + 11];
                    long b1217 = b1216 - kw2[dm17 + 12];
                    long b1218 = b139 - (kw2[dm17 + 13] + t2[dm3]);
                    long b1410 = (b149 - b1510) - (kw2[dm17 + 14] + t2[dm3 + 1]);
                    long b99 = b810;
                    long b1511 = ThreefishEngine.p(b1510 - (kw2[dm17 + 15] + ((long) d3)), 5, b07);
                    long b08 = b07 - b1511;
                    long b1115 = ThreefishEngine.p(b1114, 20, b29);
                    long b210 = b29 - b1115;
                    long b1310 = ThreefishEngine.p(b1218, 48, b613);
                    long b614 = b613 - b1310;
                    long b1116 = b1115;
                    long b411 = b410;
                    long b910 = ThreefishEngine.p(b99, 41, b411);
                    long b412 = b411 - b910;
                    long b911 = b910;
                    long b1411 = b1410;
                    long b1412 = b1310;
                    long b120 = ThreefishEngine.p(b111, 47, b1411);
                    long b1413 = b1411 - b120;
                    long j5 = b1511;
                    long b1512 = b89;
                    long b1513 = j5;
                    long b59 = ThreefishEngine.p(b58, 28, b1512);
                    long b811 = b1512 - b59;
                    long b615 = b614;
                    long b314 = b312;
                    long b315 = b811;
                    long b107 = b106;
                    long b616 = ThreefishEngine.p(b314, 16, b107);
                    long b108 = b107 - b616;
                    long b712 = b711;
                    long b713 = b120;
                    long b1219 = b1217;
                    long b714 = ThreefishEngine.p(b712, 25, b1219);
                    long b1220 = b1219 - b714;
                    long b715 = ThreefishEngine.p(b714, 33, b08);
                    long b09 = b08 - b715;
                    long b510 = ThreefishEngine.p(b59, 4, b210);
                    long b211 = b210 - b510;
                    long b316 = ThreefishEngine.p(b616, 51, b412);
                    long b413 = b412 - b316;
                    long b317 = b316;
                    long b617 = b615;
                    long j6 = b510;
                    long b121 = b713;
                    long b511 = j6;
                    long b130 = ThreefishEngine.p(b121, 13, b617);
                    long b618 = b617 - b130;
                    long b131 = b130;
                    long b1514 = ThreefishEngine.p(b1513, 34, b1220);
                    long b1221 = b1220 - b1514;
                    long b1311 = b1412;
                    long b1222 = b1413;
                    long b716 = b715;
                    long b717 = ThreefishEngine.p(b1311, 41, b1222);
                    long b1414 = b1222 - b717;
                    long b1515 = b1514;
                    long b1516 = b315;
                    long b1117 = ThreefishEngine.p(b1116, 59, b1516);
                    long b812 = b1516 - b1117;
                    long b813 = b911;
                    long b414 = b413;
                    long b109 = b108;
                    long b912 = ThreefishEngine.p(b813, 17, b109);
                    long b1010 = b109 - b912;
                    long b913 = ThreefishEngine.p(b912, 38, b09);
                    long b010 = b09 - b913;
                    long b1312 = ThreefishEngine.p(b717, 19, b211);
                    long b212 = b211 - b1312;
                    long b1118 = ThreefishEngine.p(b1117, 10, b618);
                    long b619 = b618 - b1118;
                    long b1313 = b1312;
                    long b415 = b414;
                    long j7 = b1118;
                    long b1517 = b1515;
                    long b1119 = j7;
                    long b1518 = ThreefishEngine.p(b1517, 55, b415);
                    long b416 = b415 - b1518;
                    long b1519 = b1518;
                    long b718 = ThreefishEngine.p(b716, 49, b1010);
                    long b1011 = b1010 - b718;
                    long j8 = b913;
                    long b1223 = b1221;
                    long b1224 = j8;
                    long b318 = ThreefishEngine.p(b317, 18, b1223);
                    long b1225 = b1223 - b318;
                    long b1226 = b511;
                    long b620 = b619;
                    long b621 = b1414;
                    long b512 = ThreefishEngine.p(b1226, 23, b621);
                    long b1415 = b621 - b512;
                    long j9 = b718;
                    long b719 = b812;
                    long b720 = j9;
                    long b140 = ThreefishEngine.p(b131, 52, b719);
                    long b814 = b719 - b140;
                    long b141 = ThreefishEngine.p(b140, 24, b010);
                    long b319 = ThreefishEngine.p(b318, 13, b212);
                    long b213 = b212 - b319;
                    long b513 = ThreefishEngine.p(b512, 8, b416);
                    long b417 = b416 - b513;
                    long b622 = b620;
                    long b623 = b010 - b141;
                    long j10 = b513;
                    long b721 = b720;
                    b7 = j10;
                    long b722 = ThreefishEngine.p(b721, 47, b622);
                    long b624 = b622 - b722;
                    long b625 = b1224;
                    long b723 = b722;
                    long b914 = ThreefishEngine.p(b625, 8, b814);
                    long b815 = b814 - b914;
                    long b1120 = b1119;
                    long b626 = b624;
                    long b627 = b1011;
                    long b1121 = ThreefishEngine.p(b1120, 17, b627);
                    long b1122 = b1121;
                    long j11 = b627 - b1121;
                    long b1227 = b1225;
                    b122 = j11;
                    long b1314 = ThreefishEngine.p(b1313, 22, b1227);
                    long b1228 = b1227 - b1314;
                    long b1520 = b1519;
                    long b1521 = b1314;
                    long b1416 = b1415;
                    b15 = ThreefishEngine.p(b1520, 37, b1416);
                    b14 = b1416 - b15;
                    int i2 = d3 - 2;
                    b32 = b319;
                    b11 = b1122;
                    t = t2;
                    kw = kw2;
                    mod17 = mod172;
                    b3 = b141;
                    b112 = b1521;
                    mod3 = mod32;
                    b13 = b626;
                    b6 = b417;
                    d = i2;
                    i = 1;
                    b0 = b623;
                    b02 = b723;
                    b72 = b914;
                    b2 = b213;
                    long j12 = b1228;
                    b12 = b815;
                    b8 = j12;
                }
                long[] kw3 = kw;
                long[] t3 = t;
                int[] iArr = mod17;
                long b011 = b0 - kw3[0];
                long b150 = b3 - kw3[1];
                long b214 = b2 - kw3[2];
                long b320 = b32 - kw3[3];
                long b418 = b6 - kw3[4];
                long b514 = b7 - kw3[5];
                long b628 = b13 - kw3[6];
                long b724 = b02 - kw3[7];
                long b816 = b12 - kw3[8];
                long b915 = b72 - kw3[9];
                long b1012 = b122 - kw3[10];
                long b1123 = b11 - kw3[11];
                long b1229 = b8 - kw3[12];
                long b1417 = b14 - (kw3[14] + t3[1]);
                state[0] = b011;
                state[1] = b150;
                state[2] = b214;
                state[3] = b320;
                state[4] = b418;
                state[5] = b514;
                state[6] = b628;
                state[7] = b724;
                state[8] = b816;
                state[9] = b915;
                state[10] = b1012;
                state[11] = b1123;
                state[12] = b1229;
                state[13] = b112 - (kw3[13] + t3[0]);
                state[14] = b1417;
                state[15] = b15 - kw3[15];
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
}
