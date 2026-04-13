package org.spongycastle.crypto.macs;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.digests.DSTU7564Digest;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Pack;

public class DSTU7564Mac implements Mac {
    private DSTU7564Digest a;
    private int b;
    private byte[] c = null;
    private byte[] d = null;
    private long e;

    public DSTU7564Mac(int macBitSize) {
        this.a = new DSTU7564Digest(macBitSize);
        this.b = macBitSize / 8;
    }

    public void a(CipherParameters params) {
        if (params instanceof KeyParameter) {
            byte[] key = ((KeyParameter) params).a();
            this.d = new byte[key.length];
            this.c = g(key);
            int byteIndex = 0;
            while (true) {
                byte[] bArr = this.d;
                if (byteIndex < bArr.length) {
                    bArr[byteIndex] = (byte) (~key[byteIndex]);
                    byteIndex++;
                } else {
                    DSTU7564Digest dSTU7564Digest = this.a;
                    byte[] bArr2 = this.c;
                    dSTU7564Digest.update(bArr2, 0, bArr2.length);
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("Bad parameter passed");
        }
    }

    public String b() {
        return "DSTU7564Mac";
    }

    public int e() {
        return this.b;
    }

    public void d(byte in) {
        this.a.d(in);
        this.e++;
    }

    public void update(byte[] in, int inOff, int len) {
        if (in.length - inOff < len) {
            throw new DataLengthException("Input buffer too short");
        } else if (this.c != null) {
            this.a.update(in, inOff, len);
            this.e += (long) len;
        } else {
            throw new IllegalStateException(b() + " not initialised");
        }
    }

    public int c(byte[] out, int outOff) {
        if (this.c == null) {
            throw new IllegalStateException(b() + " not initialised");
        } else if (out.length - outOff >= this.b) {
            f();
            DSTU7564Digest dSTU7564Digest = this.a;
            byte[] bArr = this.d;
            dSTU7564Digest.update(bArr, 0, bArr.length);
            this.e = 0;
            return this.a.c(out, outOff);
        } else {
            throw new OutputLengthException("Output buffer too short");
        }
    }

    public void reset() {
        this.e = 0;
        this.a.reset();
        byte[] bArr = this.c;
        if (bArr != null) {
            this.a.update(bArr, 0, bArr.length);
        }
    }

    private void f() {
        int extra = this.a.k() - ((int) (this.e % ((long) this.a.k())));
        if (extra < 13) {
            extra += this.a.k();
        }
        byte[] padded = new byte[extra];
        padded[0] = OTACommand.RESP_ID_VERSION;
        Pack.r(this.e * 8, padded, padded.length - 12);
        this.a.update(padded, 0, padded.length);
    }

    private byte[] g(byte[] in) {
        int paddedLen = (((in.length + this.a.k()) - 1) / this.a.k()) * this.a.k();
        if (this.a.k() - (in.length % this.a.k()) < 13) {
            paddedLen += this.a.k();
        }
        byte[] padded = new byte[paddedLen];
        System.arraycopy(in, 0, padded, 0, in.length);
        padded[in.length] = OTACommand.RESP_ID_VERSION;
        Pack.h(in.length * 8, padded, padded.length - 12);
        return padded;
    }
}
