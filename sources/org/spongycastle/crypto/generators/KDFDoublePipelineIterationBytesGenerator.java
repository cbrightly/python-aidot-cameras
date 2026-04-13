package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.MacDerivationFunction;
import org.spongycastle.crypto.params.KDFDoublePipelineIterationParameters;
import org.spongycastle.crypto.params.KeyParameter;

public class KDFDoublePipelineIterationBytesGenerator implements MacDerivationFunction {
    private static final BigInteger a = BigInteger.valueOf(2147483647L);
    private static final BigInteger b = BigInteger.valueOf(2);
    private final Mac c;
    private final int d;
    private byte[] e;
    private int f;
    private byte[] g;
    private boolean h;
    private int i;
    private byte[] j;
    private byte[] k;

    public void b(DerivationParameters params) {
        if (params instanceof KDFDoublePipelineIterationParameters) {
            KDFDoublePipelineIterationParameters dpiParams = (KDFDoublePipelineIterationParameters) params;
            this.c.a(new KeyParameter(dpiParams.b()));
            this.e = dpiParams.a();
            int r = dpiParams.c();
            this.g = new byte[(r / 8)];
            int i2 = Integer.MAX_VALUE;
            if (dpiParams.d()) {
                BigInteger maxSize = b.pow(r).multiply(BigInteger.valueOf((long) this.d));
                if (maxSize.compareTo(a) != 1) {
                    i2 = maxSize.intValue();
                }
                this.f = i2;
            } else {
                this.f = Integer.MAX_VALUE;
            }
            this.h = dpiParams.d();
            this.i = 0;
            return;
        }
        throw new IllegalArgumentException("Wrong type of arguments given");
    }

    public int a(byte[] out, int outOff, int len) {
        int i2 = this.i;
        int generatedBytesAfter = i2 + len;
        if (generatedBytesAfter < 0 || generatedBytesAfter >= this.f) {
            throw new DataLengthException("Current KDFCTR may only be used for " + this.f + " bytes");
        }
        if (i2 % this.d == 0) {
            c();
        }
        int toGenerate = len;
        int i3 = this.i;
        int i4 = this.d;
        int posInK = i3 % i4;
        int toCopy = Math.min(i4 - (i3 % i4), toGenerate);
        System.arraycopy(this.k, posInK, out, outOff, toCopy);
        this.i += toCopy;
        int toGenerate2 = toGenerate - toCopy;
        int outOff2 = outOff + toCopy;
        while (toGenerate2 > 0) {
            c();
            int toCopy2 = Math.min(this.d, toGenerate2);
            System.arraycopy(this.k, 0, out, outOff2, toCopy2);
            this.i += toCopy2;
            toGenerate2 -= toCopy2;
            outOff2 += toCopy2;
        }
        return len;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x004a, code lost:
        r2[r2.length - 3] = (byte) (r0 >>> 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0052, code lost:
        r2[r2.length - 2] = (byte) (r0 >>> 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x005a, code lost:
        r2[r2.length - 1] = (byte) r0;
        r5.c.update(r2, 0, r2.length);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c() {
        /*
            r5 = this;
            int r0 = r5.i
            r1 = 0
            if (r0 != 0) goto L_0x0015
            org.spongycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.e
            int r3 = r2.length
            r0.update(r2, r1, r3)
            org.spongycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.j
            r0.c(r2, r1)
            goto L_0x0024
        L_0x0015:
            org.spongycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.j
            int r3 = r2.length
            r0.update(r2, r1, r3)
            org.spongycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.j
            r0.c(r2, r1)
        L_0x0024:
            org.spongycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.j
            int r3 = r2.length
            r0.update(r2, r1, r3)
            boolean r0 = r5.h
            if (r0 == 0) goto L_0x0067
            int r0 = r5.i
            int r2 = r5.d
            int r0 = r0 / r2
            int r0 = r0 + 1
            byte[] r2 = r5.g
            int r3 = r2.length
            switch(r3) {
                case 1: goto L_0x005a;
                case 2: goto L_0x0052;
                case 3: goto L_0x004a;
                case 4: goto L_0x0045;
                default: goto L_0x003d;
            }
        L_0x003d:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "Unsupported size of counter i"
            r1.<init>(r2)
            throw r1
        L_0x0045:
            int r3 = r0 >>> 24
            byte r3 = (byte) r3
            r2[r1] = r3
        L_0x004a:
            int r3 = r2.length
            int r3 = r3 + -3
            int r4 = r0 >>> 16
            byte r4 = (byte) r4
            r2[r3] = r4
        L_0x0052:
            int r3 = r2.length
            int r3 = r3 + -2
            int r4 = r0 >>> 8
            byte r4 = (byte) r4
            r2[r3] = r4
        L_0x005a:
            int r3 = r2.length
            int r3 = r3 + -1
            byte r4 = (byte) r0
            r2[r3] = r4
            org.spongycastle.crypto.Mac r3 = r5.c
            int r4 = r2.length
            r3.update(r2, r1, r4)
        L_0x0067:
            org.spongycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.e
            int r3 = r2.length
            r0.update(r2, r1, r3)
            org.spongycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.k
            r0.c(r2, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.crypto.generators.KDFDoublePipelineIterationBytesGenerator.c():void");
    }
}
