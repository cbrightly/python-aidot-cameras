package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;

public class SSL3Mac implements Mac {
    static final byte[] a = f((byte) 54, 48);
    static final byte[] b = f((byte) 92, 48);
    private Digest c;
    private int d;
    private byte[] e;

    public SSL3Mac(Digest digest) {
        this.c = digest;
        if (digest.e() == 20) {
            this.d = 40;
        } else {
            this.d = 48;
        }
    }

    public String b() {
        return this.c.b() + "/SSL3MAC";
    }

    public void a(CipherParameters params) {
        this.e = Arrays.h(((KeyParameter) params).a());
        reset();
    }

    public int e() {
        return this.c.e();
    }

    public void d(byte in) {
        this.c.d(in);
    }

    public void update(byte[] in, int inOff, int len) {
        this.c.update(in, inOff, len);
    }

    public int c(byte[] out, int outOff) {
        byte[] tmp = new byte[this.c.e()];
        this.c.c(tmp, 0);
        Digest digest = this.c;
        byte[] bArr = this.e;
        digest.update(bArr, 0, bArr.length);
        this.c.update(b, 0, this.d);
        this.c.update(tmp, 0, tmp.length);
        int len = this.c.c(out, outOff);
        reset();
        return len;
    }

    public void reset() {
        this.c.reset();
        Digest digest = this.c;
        byte[] bArr = this.e;
        digest.update(bArr, 0, bArr.length);
        this.c.update(a, 0, this.d);
    }

    private static byte[] f(byte b2, int count) {
        byte[] padding = new byte[count];
        Arrays.F(padding, b2);
        return padding;
    }
}
