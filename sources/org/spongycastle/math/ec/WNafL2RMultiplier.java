package org.spongycastle.math.ec;

import java.math.BigInteger;

public class WNafL2RMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint p, BigInteger k) {
        ECPoint R;
        int width = Math.max(2, Math.min(16, c(k.bitLength())));
        WNafPreCompInfo wnafPreCompInfo = WNafUtil.k(p, width, true);
        ECPoint[] preComp = wnafPreCompInfo.a();
        ECPoint[] preCompNeg = wnafPreCompInfo.b();
        int[] wnaf = WNafUtil.b(width, k);
        ECPoint R2 = p.i().u();
        int i = wnaf.length;
        if (i > 1) {
            i--;
            int wi = wnaf[i];
            int digit = wi >> 16;
            int zeroes = wi & 65535;
            int n = Math.abs(digit);
            ECPoint[] table = digit < 0 ? preCompNeg : preComp;
            if ((n << 2) < (1 << width)) {
                byte highest = LongArray.y[n];
                int scale = width - highest;
                int i2 = width;
                R = table[((1 << (width - 1)) - 1) >>> 1].a(table[(((n ^ (1 << (highest - 1))) << scale) + 1) >>> 1]);
                zeroes -= scale;
            } else {
                R = table[n >>> 1];
            }
            R2 = R.G(zeroes);
        }
        while (i > 0) {
            i--;
            int wi2 = wnaf[i];
            int digit2 = wi2 >> 16;
            R2 = R2.I((digit2 < 0 ? preCompNeg : preComp)[Math.abs(digit2) >>> 1]).G(wi2 & 65535);
        }
        return R2;
    }

    /* access modifiers changed from: protected */
    public int c(int bits) {
        return WNafUtil.h(bits);
    }
}
