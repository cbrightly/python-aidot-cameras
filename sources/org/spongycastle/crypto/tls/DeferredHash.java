package org.spongycastle.crypto.tls;

import java.util.Enumeration;
import java.util.Hashtable;
import org.spongycastle.crypto.Digest;
import org.spongycastle.util.Shorts;

public class DeferredHash implements TlsHandshakeHash {
    protected TlsContext a;
    private DigestInputBuffer b;
    private Hashtable c;
    private Short d;

    DeferredHash() {
        this.b = new DigestInputBuffer();
        this.c = new Hashtable();
        this.d = null;
    }

    private DeferredHash(Short prfHashAlgorithm, Digest prfHash) {
        this.b = null;
        Hashtable hashtable = new Hashtable();
        this.c = hashtable;
        this.d = prfHashAlgorithm;
        hashtable.put(prfHashAlgorithm, prfHash);
    }

    public void o(TlsContext context) {
        this.a = context;
    }

    public TlsHandshakeHash g() {
        int prfAlgorithm = this.a.f().g();
        if (prfAlgorithm == 0) {
            CombinedHash legacyHash = new CombinedHash();
            legacyHash.m(this.a);
            this.b.a(legacyHash);
            return legacyHash.g();
        }
        Short a2 = Shorts.a(TlsUtils.D(prfAlgorithm));
        this.d = a2;
        n(a2);
        return this;
    }

    public void h(short hashAlgorithm) {
        if (this.b != null) {
            n(Shorts.a(hashAlgorithm));
            return;
        }
        throw new IllegalStateException("Too late to track more hash algorithms");
    }

    public void l() {
        m();
    }

    public TlsHandshakeHash a() {
        Digest prfHash = TlsUtils.l(this.d.shortValue(), (Digest) this.c.get(this.d));
        DigestInputBuffer digestInputBuffer = this.b;
        if (digestInputBuffer != null) {
            digestInputBuffer.a(prfHash);
        }
        DeferredHash result = new DeferredHash(this.d, prfHash);
        result.o(this.a);
        return result;
    }

    public Digest f() {
        m();
        if (this.b == null) {
            return TlsUtils.l(this.d.shortValue(), (Digest) this.c.get(this.d));
        }
        Digest prfHash = TlsUtils.o(this.d.shortValue());
        this.b.a(prfHash);
        return prfHash;
    }

    public byte[] i(short hashAlgorithm) {
        Digest d2 = (Digest) this.c.get(Shorts.a(hashAlgorithm));
        if (d2 != null) {
            Digest d3 = TlsUtils.l(hashAlgorithm, d2);
            DigestInputBuffer digestInputBuffer = this.b;
            if (digestInputBuffer != null) {
                digestInputBuffer.a(d3);
            }
            byte[] bs = new byte[d3.e()];
            d3.c(bs, 0);
            return bs;
        }
        throw new IllegalStateException("HashAlgorithm." + HashAlgorithm.b(hashAlgorithm) + " is not being tracked");
    }

    public String b() {
        throw new IllegalStateException("Use fork() to get a definite Digest");
    }

    public int e() {
        throw new IllegalStateException("Use fork() to get a definite Digest");
    }

    public void d(byte input) {
        DigestInputBuffer digestInputBuffer = this.b;
        if (digestInputBuffer != null) {
            digestInputBuffer.write(input);
            return;
        }
        Enumeration e = this.c.elements();
        while (e.hasMoreElements()) {
            ((Digest) e.nextElement()).d(input);
        }
    }

    public void update(byte[] input, int inOff, int len) {
        DigestInputBuffer digestInputBuffer = this.b;
        if (digestInputBuffer != null) {
            digestInputBuffer.write(input, inOff, len);
            return;
        }
        Enumeration e = this.c.elements();
        while (e.hasMoreElements()) {
            ((Digest) e.nextElement()).update(input, inOff, len);
        }
    }

    public int c(byte[] output, int outOff) {
        throw new IllegalStateException("Use fork() to get a definite Digest");
    }

    public void reset() {
        DigestInputBuffer digestInputBuffer = this.b;
        if (digestInputBuffer != null) {
            digestInputBuffer.reset();
            return;
        }
        Enumeration e = this.c.elements();
        while (e.hasMoreElements()) {
            ((Digest) e.nextElement()).reset();
        }
    }

    /* access modifiers changed from: protected */
    public void m() {
        if (this.b != null && this.c.size() <= 4) {
            Enumeration e = this.c.elements();
            while (e.hasMoreElements()) {
                this.b.a((Digest) e.nextElement());
            }
            this.b = null;
        }
    }

    /* access modifiers changed from: protected */
    public void n(Short hashAlgorithm) {
        if (!this.c.containsKey(hashAlgorithm)) {
            this.c.put(hashAlgorithm, TlsUtils.o(hashAlgorithm.shortValue()));
        }
    }
}
