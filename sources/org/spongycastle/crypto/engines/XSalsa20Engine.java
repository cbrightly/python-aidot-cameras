package org.spongycastle.crypto.engines;

import org.spongycastle.util.Pack;

public class XSalsa20Engine extends Salsa20Engine {
    public String b() {
        return "XSalsa20";
    }

    /* access modifiers changed from: protected */
    public int i() {
        return 24;
    }

    /* access modifiers changed from: protected */
    public void s(byte[] keyBytes, byte[] ivBytes) {
        if (keyBytes == null) {
            throw new IllegalArgumentException(b() + " doesn't support re-init with null key");
        } else if (keyBytes.length == 32) {
            super.s(keyBytes, ivBytes);
            Pack.k(ivBytes, 8, this.f, 8, 2);
            int[] iArr = this.f;
            int[] hsalsa20Out = new int[iArr.length];
            Salsa20Engine.r(20, iArr, hsalsa20Out);
            int[] iArr2 = this.f;
            iArr2[1] = hsalsa20Out[0] - iArr2[0];
            iArr2[2] = hsalsa20Out[5] - iArr2[5];
            iArr2[3] = hsalsa20Out[10] - iArr2[10];
            iArr2[4] = hsalsa20Out[15] - iArr2[15];
            iArr2[11] = hsalsa20Out[6] - iArr2[6];
            iArr2[12] = hsalsa20Out[7] - iArr2[7];
            iArr2[13] = hsalsa20Out[8] - iArr2[8];
            iArr2[14] = hsalsa20Out[9] - iArr2[9];
            Pack.k(ivBytes, 16, iArr2, 6, 2);
        } else {
            throw new IllegalArgumentException(b() + " requires a 256 bit key");
        }
    }
}
