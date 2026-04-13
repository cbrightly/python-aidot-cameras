package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public final class XMSSPublicKeyParameters extends AsymmetricKeyParameter implements XMSSStoreableObjectInterface {
    private final XMSSParameters d;
    private final byte[] f;
    private final byte[] q;

    private XMSSPublicKeyParameters(Builder builder) {
        super(false);
        XMSSParameters a = builder.a;
        this.d = a;
        if (a != null) {
            int n = a.c();
            byte[] publicKey = builder.d;
            if (publicKey != null) {
                int rootSize = n;
                int publicSeedSize = n;
                if (publicKey.length == rootSize + publicSeedSize) {
                    this.f = XMSSUtil.g(publicKey, 0, rootSize);
                    this.q = XMSSUtil.g(publicKey, 0 + rootSize, publicSeedSize);
                    return;
                }
                throw new IllegalArgumentException("public key has wrong size");
            }
            byte[] tmpRoot = builder.b;
            if (tmpRoot == null) {
                this.f = new byte[n];
            } else if (tmpRoot.length == n) {
                this.f = tmpRoot;
            } else {
                throw new IllegalArgumentException("length of root must be equal to length of digest");
            }
            byte[] tmpPublicSeed = builder.c;
            if (tmpPublicSeed == null) {
                this.q = new byte[n];
            } else if (tmpPublicSeed.length == n) {
                this.q = tmpPublicSeed;
            } else {
                throw new IllegalArgumentException("length of publicSeed must be equal to length of digest");
            }
        } else {
            throw new NullPointerException("params == null");
        }
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public final XMSSParameters a;
        /* access modifiers changed from: private */
        public byte[] b = null;
        /* access modifiers changed from: private */
        public byte[] c = null;
        /* access modifiers changed from: private */
        public byte[] d = null;

        public Builder(XMSSParameters params) {
            this.a = params;
        }

        public Builder g(byte[] val) {
            this.b = XMSSUtil.c(val);
            return this;
        }

        public Builder f(byte[] val) {
            this.c = XMSSUtil.c(val);
            return this;
        }

        public XMSSPublicKeyParameters e() {
            return new XMSSPublicKeyParameters(this);
        }
    }

    public byte[] e() {
        int n = this.d.c();
        int rootSize = n;
        byte[] out = new byte[(rootSize + n)];
        XMSSUtil.e(out, this.f, 0);
        XMSSUtil.e(out, this.q, 0 + rootSize);
        return out;
    }

    public byte[] d() {
        return XMSSUtil.c(this.f);
    }

    public byte[] c() {
        return XMSSUtil.c(this.q);
    }

    public XMSSParameters b() {
        return this.d;
    }
}
