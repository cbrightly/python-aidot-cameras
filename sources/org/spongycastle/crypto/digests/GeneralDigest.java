package org.spongycastle.crypto.digests;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Memoable;

public abstract class GeneralDigest implements ExtendedDigest, Memoable {
    private final byte[] a;
    private int b;
    private long c;

    /* access modifiers changed from: protected */
    public abstract void p();

    /* access modifiers changed from: protected */
    public abstract void q(long j);

    /* access modifiers changed from: protected */
    public abstract void r(byte[] bArr, int i);

    protected GeneralDigest() {
        this.a = new byte[4];
        this.b = 0;
    }

    protected GeneralDigest(GeneralDigest t) {
        this.a = new byte[4];
        n(t);
    }

    /* access modifiers changed from: protected */
    public void n(GeneralDigest t) {
        byte[] bArr = t.a;
        System.arraycopy(bArr, 0, this.a, 0, bArr.length);
        this.b = t.b;
        this.c = t.c;
    }

    public void d(byte in) {
        byte[] bArr = this.a;
        int i = this.b;
        int i2 = i + 1;
        this.b = i2;
        bArr[i] = in;
        if (i2 == bArr.length) {
            r(bArr, 0);
            this.b = 0;
        }
        this.c++;
    }

    public void update(byte[] in, int inOff, int len) {
        int len2 = Math.max(0, len);
        int i = 0;
        if (this.b != 0) {
            while (true) {
                if (i >= len2) {
                    break;
                }
                byte[] bArr = this.a;
                int i2 = this.b;
                int i3 = i2 + 1;
                this.b = i3;
                int i4 = i + 1;
                bArr[i2] = in[i + inOff];
                if (i3 == 4) {
                    r(bArr, 0);
                    this.b = 0;
                    i = i4;
                    break;
                }
                i = i4;
            }
        }
        int limit = ((len2 - i) & -4) + i;
        while (i < limit) {
            r(in, inOff + i);
            i += 4;
        }
        while (i < len2) {
            byte[] bArr2 = this.a;
            int i5 = this.b;
            this.b = i5 + 1;
            bArr2[i5] = in[i + inOff];
            i++;
        }
        this.c += (long) len2;
    }

    public void o() {
        long bitLength = this.c << 3;
        d(OTACommand.RESP_ID_VERSION);
        while (this.b != 0) {
            d((byte) 0);
        }
        q(bitLength);
        p();
    }

    public void reset() {
        this.c = 0;
        this.b = 0;
        int i = 0;
        while (true) {
            byte[] bArr = this.a;
            if (i < bArr.length) {
                bArr[i] = 0;
                i++;
            } else {
                return;
            }
        }
    }

    public int k() {
        return 64;
    }
}
