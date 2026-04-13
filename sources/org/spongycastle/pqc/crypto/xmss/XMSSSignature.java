package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.pqc.crypto.xmss.XMSSReducedSignature;
import org.spongycastle.util.Pack;

public final class XMSSSignature extends XMSSReducedSignature implements XMSSStoreableObjectInterface {
    private final int q;
    private final byte[] x;

    private XMSSSignature(Builder builder) {
        super(builder);
        this.q = builder.f;
        int n = b().c();
        byte[] tmpRandom = builder.g;
        if (tmpRandom == null) {
            this.x = new byte[n];
        } else if (tmpRandom.length == n) {
            this.x = tmpRandom;
        } else {
            throw new IllegalArgumentException("size of random needs to be equal to size of digest");
        }
    }

    public static class Builder extends XMSSReducedSignature.Builder {
        private final XMSSParameters e;
        /* access modifiers changed from: private */
        public int f = 0;
        /* access modifiers changed from: private */
        public byte[] g = null;

        public Builder(XMSSParameters params) {
            super(params);
            this.e = params;
        }

        public Builder l(int val) {
            this.f = val;
            return this;
        }

        public Builder m(byte[] val) {
            this.g = XMSSUtil.c(val);
            return this;
        }

        public Builder n(byte[] val) {
            if (val != null) {
                int n = this.e.c();
                int len = this.e.f().e().c();
                int randomSize = n;
                this.f = Pack.a(val, 0);
                int position = 0 + 4;
                this.g = XMSSUtil.g(val, position, randomSize);
                g(XMSSUtil.g(val, position + randomSize, (len * n) + (this.e.d() * n)));
                return this;
            }
            throw new NullPointerException("signature == null");
        }

        /* renamed from: k */
        public XMSSSignature e() {
            return new XMSSSignature(this);
        }
    }

    public byte[] d() {
        int n = b().c();
        int randomSize = n;
        byte[] out = new byte[(4 + randomSize + (b().f().e().c() * n) + (b().d() * n))];
        Pack.d(this.q, out, 0);
        int position = 0 + 4;
        XMSSUtil.e(out, this.x, position);
        int position2 = position + randomSize;
        byte[][] signature = c().a();
        for (byte[] e : signature) {
            XMSSUtil.e(out, e, position2);
            position2 += n;
        }
        for (int i = 0; i < a().size(); i++) {
            XMSSUtil.e(out, a().get(i).getValue(), position2);
            position2 += n;
        }
        return out;
    }

    public int e() {
        return this.q;
    }

    public byte[] f() {
        return XMSSUtil.c(this.x);
    }
}
