package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.MacDerivationFunction;
import org.spongycastle.crypto.params.KDFFeedbackParameters;
import org.spongycastle.crypto.params.KeyParameter;

public class KDFFeedbackBytesGenerator implements MacDerivationFunction {
    private static final BigInteger a = BigInteger.valueOf(2147483647L);
    private static final BigInteger b = BigInteger.valueOf(2);
    private final Mac c;
    private final int d;
    private byte[] e;
    private int f;
    private byte[] g;
    private byte[] h;
    private boolean i;
    private int j;
    private byte[] k;

    public void b(DerivationParameters params) {
        if (params instanceof KDFFeedbackParameters) {
            KDFFeedbackParameters feedbackParams = (KDFFeedbackParameters) params;
            this.c.a(new KeyParameter(feedbackParams.c()));
            this.e = feedbackParams.a();
            int r = feedbackParams.d();
            this.g = new byte[(r / 8)];
            int i2 = Integer.MAX_VALUE;
            if (feedbackParams.e()) {
                BigInteger maxSize = b.pow(r).multiply(BigInteger.valueOf((long) this.d));
                if (maxSize.compareTo(a) != 1) {
                    i2 = maxSize.intValue();
                }
                this.f = i2;
            } else {
                this.f = Integer.MAX_VALUE;
            }
            this.h = feedbackParams.b();
            this.i = feedbackParams.e();
            this.j = 0;
            return;
        }
        throw new IllegalArgumentException("Wrong type of arguments given");
    }

    public int a(byte[] out, int outOff, int len) {
        int i2 = this.j;
        int generatedBytesAfter = i2 + len;
        if (generatedBytesAfter < 0 || generatedBytesAfter >= this.f) {
            throw new DataLengthException("Current KDFCTR may only be used for " + this.f + " bytes");
        }
        if (i2 % this.d == 0) {
            c();
        }
        int toGenerate = len;
        int i3 = this.j;
        int i4 = this.d;
        int posInK = i3 % i4;
        int toCopy = Math.min(i4 - (i3 % i4), toGenerate);
        System.arraycopy(this.k, posInK, out, outOff, toCopy);
        this.j += toCopy;
        int toGenerate2 = toGenerate - toCopy;
        int outOff2 = outOff + toCopy;
        while (toGenerate2 > 0) {
            c();
            int toCopy2 = Math.min(this.d, toGenerate2);
            System.arraycopy(this.k, 0, out, outOff2, toCopy2);
            this.j += toCopy2;
            toGenerate2 -= toCopy2;
            outOff2 += toCopy2;
        }
        return len;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0034, code lost:
        r2[r2.length - 3] = (byte) (r0 >>> 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003c, code lost:
        r2[r2.length - 2] = (byte) (r0 >>> 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0044, code lost:
        r2[r2.length - 1] = (byte) r0;
        r5.c.update(r2, 0, r2.length);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c() {
        /*
            r5 = this;
            int r0 = r5.j
            r1 = 0
            if (r0 != 0) goto L_0x000e
            org.spongycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.h
            int r3 = r2.length
            r0.update(r2, r1, r3)
            goto L_0x0016
        L_0x000e:
            org.spongycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.k
            int r3 = r2.length
            r0.update(r2, r1, r3)
        L_0x0016:
            boolean r0 = r5.i
            if (r0 == 0) goto L_0x0051
            int r0 = r5.j
            int r2 = r5.d
            int r0 = r0 / r2
            int r0 = r0 + 1
            byte[] r2 = r5.g
            int r3 = r2.length
            switch(r3) {
                case 1: goto L_0x0044;
                case 2: goto L_0x003c;
                case 3: goto L_0x0034;
                case 4: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "Unsupported size of counter i"
            r1.<init>(r2)
            throw r1
        L_0x002f:
            int r3 = r0 >>> 24
            byte r3 = (byte) r3
            r2[r1] = r3
        L_0x0034:
            int r3 = r2.length
            int r3 = r3 + -3
            int r4 = r0 >>> 16
            byte r4 = (byte) r4
            r2[r3] = r4
        L_0x003c:
            int r3 = r2.length
            int r3 = r3 + -2
            int r4 = r0 >>> 8
            byte r4 = (byte) r4
            r2[r3] = r4
        L_0x0044:
            int r3 = r2.length
            int r3 = r3 + -1
            byte r4 = (byte) r0
            r2[r3] = r4
            org.spongycastle.crypto.Mac r3 = r5.c
            int r4 = r2.length
            r3.update(r2, r1, r4)
        L_0x0051:
            org.spongycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.e
            int r3 = r2.length
            r0.update(r2, r1, r3)
            org.spongycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.k
            r0.c(r2, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.crypto.generators.KDFFeedbackBytesGenerator.c():void");
    }
}
