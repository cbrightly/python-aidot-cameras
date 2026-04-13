package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.Digest;

public class CombinedHash implements TlsHandshakeHash {
    protected TlsContext a;
    protected Digest b;
    protected Digest c;

    CombinedHash() {
        this.b = TlsUtils.o(1);
        this.c = TlsUtils.o(2);
    }

    CombinedHash(CombinedHash t) {
        this.a = t.a;
        this.b = TlsUtils.l(1, t.b);
        this.c = TlsUtils.l(2, t.c);
    }

    public void m(TlsContext context) {
        this.a = context;
    }

    public TlsHandshakeHash g() {
        return this;
    }

    public void h(short hashAlgorithm) {
        throw new IllegalStateException("CombinedHash only supports calculating the legacy PRF for handshake hash");
    }

    public void l() {
    }

    public TlsHandshakeHash a() {
        return new CombinedHash(this);
    }

    public Digest f() {
        return new CombinedHash(this);
    }

    public byte[] i(short hashAlgorithm) {
        throw new IllegalStateException("CombinedHash doesn't support multiple hashes");
    }

    public String b() {
        return this.b.b() + " and " + this.c.b();
    }

    public int e() {
        return this.b.e() + this.c.e();
    }

    public void d(byte input) {
        this.b.d(input);
        this.c.d(input);
    }

    public void update(byte[] input, int inOff, int len) {
        this.b.update(input, inOff, len);
        this.c.update(input, inOff, len);
    }

    public int c(byte[] output, int outOff) {
        TlsContext tlsContext = this.a;
        if (tlsContext != null && TlsUtils.P(tlsContext)) {
            Digest digest = this.b;
            byte[] bArr = SSL3Mac.a;
            byte[] bArr2 = SSL3Mac.b;
            n(digest, bArr, bArr2, 48);
            n(this.c, bArr, bArr2, 40);
        }
        int i1 = this.b.c(output, outOff);
        return i1 + this.c.c(output, outOff + i1);
    }

    public void reset() {
        this.b.reset();
        this.c.reset();
    }

    /* access modifiers changed from: protected */
    public void n(Digest d, byte[] ipad, byte[] opad, int padLength) {
        byte[] master_secret = this.a.f().f;
        d.update(master_secret, 0, master_secret.length);
        d.update(ipad, 0, padLength);
        byte[] tmp = new byte[d.e()];
        d.c(tmp, 0);
        d.update(master_secret, 0, master_secret.length);
        d.update(opad, 0, padLength);
        d.update(tmp, 0, tmp.length);
    }
}
