package org.spongycastle.pqc.crypto.xmss;

import java.util.ArrayList;
import java.util.List;
import org.spongycastle.pqc.crypto.xmss.XMSSReducedSignature;

public final class XMSSMTSignature implements XMSSStoreableObjectInterface {
    private final XMSSMTParameters c;
    private final long d;
    private final byte[] f;
    private final List<XMSSReducedSignature> q;

    private XMSSMTSignature(Builder builder) {
        XMSSMTParameters a = builder.a;
        this.c = a;
        if (a != null) {
            int n = a.b();
            byte[] signature = builder.e;
            if (signature != null) {
                int len = a.f().e().c();
                int indexSize = (int) Math.ceil(((double) a.c()) / 8.0d);
                int randomSize = n;
                int reducedSignatureSizeSingle = ((a.c() / a.d()) + len) * n;
                if (signature.length == indexSize + randomSize + (a.d() * reducedSignatureSizeSingle)) {
                    long a2 = XMSSUtil.a(signature, 0, indexSize);
                    this.d = a2;
                    if (XMSSUtil.l(a.c(), a2)) {
                        int position = 0 + indexSize;
                        this.f = XMSSUtil.g(signature, position, randomSize);
                        this.q = new ArrayList();
                        for (int position2 = position + randomSize; position2 < signature.length; position2 += reducedSignatureSizeSingle) {
                            this.q.add(new XMSSReducedSignature.Builder(this.c.h()).g(XMSSUtil.g(signature, position2, reducedSignatureSizeSingle)).e());
                        }
                        return;
                    }
                    throw new IllegalArgumentException("index out of bounds");
                }
                throw new IllegalArgumentException("signature has wrong size");
            }
            this.d = builder.b;
            byte[] tmpRandom = builder.c;
            if (tmpRandom == null) {
                this.f = new byte[n];
            } else if (tmpRandom.length == n) {
                this.f = tmpRandom;
            } else {
                throw new IllegalArgumentException("size of random needs to be equal to size of digest");
            }
            List<XMSSReducedSignature> tmpReducedSignatures = builder.d;
            if (tmpReducedSignatures != null) {
                this.q = tmpReducedSignatures;
            } else {
                this.q = new ArrayList();
            }
        } else {
            throw new NullPointerException("params == null");
        }
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public final XMSSMTParameters a;
        /* access modifiers changed from: private */
        public long b = 0;
        /* access modifiers changed from: private */
        public byte[] c = null;
        /* access modifiers changed from: private */
        public List<XMSSReducedSignature> d = null;
        /* access modifiers changed from: private */
        public byte[] e = null;

        public Builder(XMSSMTParameters params) {
            this.a = params;
        }

        public Builder g(long val) {
            this.b = val;
            return this;
        }

        public Builder h(byte[] val) {
            this.c = XMSSUtil.c(val);
            return this;
        }

        public Builder i(byte[] val) {
            this.e = val;
            return this;
        }

        public XMSSMTSignature f() {
            return new XMSSMTSignature(this);
        }
    }

    public byte[] d() {
        int n = this.c.b();
        int len = this.c.f().e().c();
        int indexSize = (int) Math.ceil(((double) this.c.c()) / 8.0d);
        int randomSize = n;
        int reducedSignatureSizeSingle = ((this.c.c() / this.c.d()) + len) * n;
        byte[] out = new byte[(indexSize + randomSize + (this.c.d() * reducedSignatureSizeSingle))];
        XMSSUtil.e(out, XMSSUtil.q(this.d, indexSize), 0);
        int position = 0 + indexSize;
        XMSSUtil.e(out, this.f, position);
        int position2 = position + randomSize;
        for (XMSSReducedSignature reducedSignature : this.q) {
            XMSSUtil.e(out, reducedSignature.d(), position2);
            position2 += reducedSignatureSizeSingle;
        }
        return out;
    }

    public long a() {
        return this.d;
    }

    public byte[] b() {
        return XMSSUtil.c(this.f);
    }

    public List<XMSSReducedSignature> c() {
        return this.q;
    }
}
