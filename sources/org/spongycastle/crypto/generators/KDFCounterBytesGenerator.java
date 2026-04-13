package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.MacDerivationFunction;
import org.spongycastle.crypto.params.KDFCounterParameters;
import org.spongycastle.crypto.params.KeyParameter;

public class KDFCounterBytesGenerator implements MacDerivationFunction {
    private static final BigInteger a = BigInteger.valueOf(2147483647L);
    private static final BigInteger b = BigInteger.valueOf(2);
    private final Mac c;
    private final int d;
    private byte[] e;
    private byte[] f;
    private int g;
    private byte[] h;
    private int i;
    private byte[] j;

    public void b(DerivationParameters param) {
        int i2;
        if (param instanceof KDFCounterParameters) {
            KDFCounterParameters kdfParams = (KDFCounterParameters) param;
            this.c.a(new KeyParameter(kdfParams.c()));
            this.e = kdfParams.a();
            this.f = kdfParams.b();
            int r = kdfParams.d();
            this.h = new byte[(r / 8)];
            BigInteger maxSize = b.pow(r).multiply(BigInteger.valueOf((long) this.d));
            if (maxSize.compareTo(a) == 1) {
                i2 = Integer.MAX_VALUE;
            } else {
                i2 = maxSize.intValue();
            }
            this.g = i2;
            this.i = 0;
            return;
        }
        throw new IllegalArgumentException("Wrong type of arguments given");
    }

    public int a(byte[] out, int outOff, int len) {
        int i2 = this.i;
        int generatedBytesAfter = i2 + len;
        if (generatedBytesAfter < 0 || generatedBytesAfter >= this.g) {
            throw new DataLengthException("Current KDFCTR may only be used for " + this.g + " bytes");
        }
        if (i2 % this.d == 0) {
            c();
        }
        int toGenerate = len;
        int i3 = this.i;
        int i4 = this.d;
        int posInK = i3 % i4;
        int toCopy = Math.min(i4 - (i3 % i4), toGenerate);
        System.arraycopy(this.j, posInK, out, outOff, toCopy);
        this.i += toCopy;
        int toGenerate2 = toGenerate - toCopy;
        int outOff2 = outOff + toCopy;
        while (toGenerate2 > 0) {
            c();
            int toCopy2 = Math.min(this.d, toGenerate2);
            System.arraycopy(this.j, 0, out, outOff2, toCopy2);
            this.i += toCopy2;
            toGenerate2 -= toCopy2;
            outOff2 += toCopy2;
        }
        return len;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001b, code lost:
        r1[r1.length - 3] = (byte) (r0 >>> 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0023, code lost:
        r1[r1.length - 2] = (byte) (r0 >>> 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002b, code lost:
        r1[r1.length - 1] = (byte) r0;
        r1 = r5.c;
        r2 = r5.e;
        r1.update(r2, 0, r2.length);
        r1 = r5.c;
        r2 = r5.h;
        r1.update(r2, 0, r2.length);
        r1 = r5.c;
        r2 = r5.f;
        r1.update(r2, 0, r2.length);
        r5.c.c(r5.j, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0051, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c() {
        /*
            r5 = this;
            int r0 = r5.i
            int r1 = r5.d
            int r0 = r0 / r1
            int r0 = r0 + 1
            byte[] r1 = r5.h
            int r2 = r1.length
            r3 = 0
            switch(r2) {
                case 1: goto L_0x002b;
                case 2: goto L_0x0023;
                case 3: goto L_0x001b;
                case 4: goto L_0x0016;
                default: goto L_0x000e;
            }
        L_0x000e:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "Unsupported size of counter i"
            r1.<init>(r2)
            throw r1
        L_0x0016:
            int r2 = r0 >>> 24
            byte r2 = (byte) r2
            r1[r3] = r2
        L_0x001b:
            int r2 = r1.length
            int r2 = r2 + -3
            int r4 = r0 >>> 16
            byte r4 = (byte) r4
            r1[r2] = r4
        L_0x0023:
            int r2 = r1.length
            int r2 = r2 + -2
            int r4 = r0 >>> 8
            byte r4 = (byte) r4
            r1[r2] = r4
        L_0x002b:
            int r2 = r1.length
            int r2 = r2 + -1
            byte r4 = (byte) r0
            r1[r2] = r4
            org.spongycastle.crypto.Mac r1 = r5.c
            byte[] r2 = r5.e
            int r4 = r2.length
            r1.update(r2, r3, r4)
            org.spongycastle.crypto.Mac r1 = r5.c
            byte[] r2 = r5.h
            int r4 = r2.length
            r1.update(r2, r3, r4)
            org.spongycastle.crypto.Mac r1 = r5.c
            byte[] r2 = r5.f
            int r4 = r2.length
            r1.update(r2, r3, r4)
            org.spongycastle.crypto.Mac r1 = r5.c
            byte[] r2 = r5.j
            r1.c(r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.crypto.generators.KDFCounterBytesGenerator.c():void");
    }
}
