package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;

public class HMacDSAKCalculator implements DSAKCalculator {
    private static final BigInteger a = BigInteger.valueOf(0);
    private final HMac b;
    private final byte[] c;
    private final byte[] d;
    private BigInteger e;

    public HMacDSAKCalculator(Digest digest) {
        HMac hMac = new HMac(digest);
        this.b = hMac;
        this.d = new byte[hMac.e()];
        this.c = new byte[hMac.e()];
    }

    public boolean c() {
        return true;
    }

    public void a(BigInteger n, SecureRandom random) {
        throw new IllegalStateException("Operation not supported");
    }

    public void d(BigInteger n, BigInteger d2, byte[] message) {
        this.e = n;
        Arrays.F(this.d, (byte) 1);
        Arrays.F(this.c, (byte) 0);
        byte[] x = new byte[((n.bitLength() + 7) / 8)];
        byte[] dVal = BigIntegers.b(d2);
        System.arraycopy(dVal, 0, x, x.length - dVal.length, dVal.length);
        byte[] m = new byte[((n.bitLength() + 7) / 8)];
        BigInteger mInt = e(message);
        if (mInt.compareTo(n) >= 0) {
            mInt = mInt.subtract(n);
        }
        byte[] mVal = BigIntegers.b(mInt);
        System.arraycopy(mVal, 0, m, m.length - mVal.length, mVal.length);
        this.b.a(new KeyParameter(this.c));
        HMac hMac = this.b;
        byte[] bArr = this.d;
        hMac.update(bArr, 0, bArr.length);
        this.b.d((byte) 0);
        this.b.update(x, 0, x.length);
        this.b.update(m, 0, m.length);
        this.b.c(this.c, 0);
        this.b.a(new KeyParameter(this.c));
        HMac hMac2 = this.b;
        byte[] bArr2 = this.d;
        hMac2.update(bArr2, 0, bArr2.length);
        this.b.c(this.d, 0);
        HMac hMac3 = this.b;
        byte[] bArr3 = this.d;
        hMac3.update(bArr3, 0, bArr3.length);
        this.b.d((byte) 1);
        this.b.update(x, 0, x.length);
        this.b.update(m, 0, m.length);
        this.b.c(this.c, 0);
        this.b.a(new KeyParameter(this.c));
        HMac hMac4 = this.b;
        byte[] bArr4 = this.d;
        hMac4.update(bArr4, 0, bArr4.length);
        this.b.c(this.d, 0);
    }

    public BigInteger b() {
        byte[] t = new byte[((this.e.bitLength() + 7) / 8)];
        while (true) {
            int tOff = 0;
            while (tOff < t.length) {
                HMac hMac = this.b;
                byte[] bArr = this.d;
                hMac.update(bArr, 0, bArr.length);
                this.b.c(this.d, 0);
                int len = Math.min(t.length - tOff, this.d.length);
                System.arraycopy(this.d, 0, t, tOff, len);
                tOff += len;
            }
            BigInteger k = e(t);
            if (k.compareTo(a) > 0 && k.compareTo(this.e) < 0) {
                return k;
            }
            HMac hMac2 = this.b;
            byte[] bArr2 = this.d;
            hMac2.update(bArr2, 0, bArr2.length);
            this.b.d((byte) 0);
            this.b.c(this.c, 0);
            this.b.a(new KeyParameter(this.c));
            HMac hMac3 = this.b;
            byte[] bArr3 = this.d;
            hMac3.update(bArr3, 0, bArr3.length);
            this.b.c(this.d, 0);
        }
    }

    private BigInteger e(byte[] t) {
        BigInteger v = new BigInteger(1, t);
        if (t.length * 8 > this.e.bitLength()) {
            return v.shiftRight((t.length * 8) - this.e.bitLength());
        }
        return v;
    }
}
