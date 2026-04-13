package org.spongycastle.pqc.crypto.xmss;

import java.io.IOException;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.util.Arrays;

public final class XMSSMTPrivateKeyParameters extends AsymmetricKeyParameter implements XMSSStoreableObjectInterface {
    private final XMSSMTParameters d;
    private final long f;
    private final BDSStateMap p0;
    private final byte[] q;
    private final byte[] x;
    private final byte[] y;
    private final byte[] z;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private XMSSMTPrivateKeyParameters(Builder builder) {
        super(true);
        XMSSMTParameters a = builder.a;
        this.d = a;
        if (a != null) {
            int n = a.b();
            byte[] privateKey = builder.h;
            if (privateKey == null) {
                this.f = builder.b;
                byte[] tmpSecretKeySeed = builder.c;
                if (tmpSecretKeySeed == null) {
                    this.q = new byte[n];
                } else if (tmpSecretKeySeed.length == n) {
                    this.q = tmpSecretKeySeed;
                } else {
                    throw new IllegalArgumentException("size of secretKeySeed needs to be equal size of digest");
                }
                byte[] tmpSecretKeyPRF = builder.d;
                if (tmpSecretKeyPRF == null) {
                    this.x = new byte[n];
                } else if (tmpSecretKeyPRF.length == n) {
                    this.x = tmpSecretKeyPRF;
                } else {
                    throw new IllegalArgumentException("size of secretKeyPRF needs to be equal size of digest");
                }
                byte[] tmpPublicSeed = builder.e;
                if (tmpPublicSeed == null) {
                    this.y = new byte[n];
                } else if (tmpPublicSeed.length == n) {
                    this.y = tmpPublicSeed;
                } else {
                    throw new IllegalArgumentException("size of publicSeed needs to be equal size of digest");
                }
                byte[] tmpRoot = builder.f;
                if (tmpRoot == null) {
                    this.z = new byte[n];
                } else if (tmpRoot.length == n) {
                    this.z = tmpRoot;
                } else {
                    throw new IllegalArgumentException("size of root needs to be equal size of digest");
                }
                BDSStateMap tmpBDSState = builder.g;
                if (tmpBDSState != null) {
                    this.p0 = tmpBDSState;
                    int i = n;
                    return;
                }
                long globalIndex = builder.b;
                int totalHeight = a.c();
                if (!XMSSUtil.l(totalHeight, globalIndex) || tmpPublicSeed == null || tmpSecretKeySeed == null) {
                    int i2 = n;
                    this.p0 = new BDSStateMap();
                    return;
                }
                int i3 = n;
                BDSStateMap bDSStateMap = r2;
                int i4 = totalHeight;
                BDSStateMap bDSStateMap2 = new BDSStateMap(a, builder.b, tmpPublicSeed, tmpSecretKeySeed);
                this.p0 = bDSStateMap;
            } else if (builder.i != null) {
                int totalHeight2 = a.c();
                int indexSize = (totalHeight2 + 7) / 8;
                int secretKeySize = n;
                int secretKeyPRFSize = n;
                int publicSeedSize = n;
                int rootSize = n;
                long a2 = XMSSUtil.a(privateKey, 0, indexSize);
                this.f = a2;
                if (XMSSUtil.l(totalHeight2, a2)) {
                    int position = 0 + indexSize;
                    this.q = XMSSUtil.g(privateKey, position, secretKeySize);
                    int position2 = position + secretKeySize;
                    this.x = XMSSUtil.g(privateKey, position2, secretKeyPRFSize);
                    int position3 = position2 + secretKeyPRFSize;
                    this.y = XMSSUtil.g(privateKey, position3, publicSeedSize);
                    int position4 = position3 + publicSeedSize;
                    this.z = XMSSUtil.g(privateKey, position4, rootSize);
                    int position5 = position4 + rootSize;
                    BDSStateMap bdsImport = null;
                    try {
                        bdsImport = (BDSStateMap) XMSSUtil.f(XMSSUtil.g(privateKey, position5, privateKey.length - position5));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e2) {
                        e2.printStackTrace();
                    }
                    bdsImport.setXMSS(builder.i);
                    this.p0 = bdsImport;
                    int i5 = n;
                    return;
                }
                throw new IllegalArgumentException("index out of bounds");
            } else {
                throw new NullPointerException("xmss == null");
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
        public byte[] d = null;
        /* access modifiers changed from: private */
        public byte[] e = null;
        /* access modifiers changed from: private */
        public byte[] f = null;
        /* access modifiers changed from: private */
        public BDSStateMap g = null;
        /* access modifiers changed from: private */
        public byte[] h = null;
        /* access modifiers changed from: private */
        public XMSSParameters i = null;

        public Builder(XMSSMTParameters params) {
            this.a = params;
        }

        public Builder l(long val) {
            this.b = val;
            return this;
        }

        public Builder p(byte[] val) {
            this.c = XMSSUtil.c(val);
            return this;
        }

        public Builder o(byte[] val) {
            this.d = XMSSUtil.c(val);
            return this;
        }

        public Builder m(byte[] val) {
            this.e = XMSSUtil.c(val);
            return this;
        }

        public Builder n(byte[] val) {
            this.f = XMSSUtil.c(val);
            return this;
        }

        public Builder k(BDSStateMap val) {
            this.g = val;
            return this;
        }

        public XMSSMTPrivateKeyParameters j() {
            return new XMSSMTPrivateKeyParameters(this);
        }
    }

    public byte[] j() {
        int n = this.d.b();
        int indexSize = (this.d.c() + 7) / 8;
        int secretKeySize = n;
        int secretKeyPRFSize = n;
        int publicSeedSize = n;
        byte[] out = new byte[(indexSize + secretKeySize + secretKeyPRFSize + publicSeedSize + n)];
        XMSSUtil.e(out, XMSSUtil.q(this.f, indexSize), 0);
        int position = 0 + indexSize;
        XMSSUtil.e(out, this.q, position);
        int position2 = position + secretKeySize;
        XMSSUtil.e(out, this.x, position2);
        int position3 = position2 + secretKeyPRFSize;
        XMSSUtil.e(out, this.y, position3);
        XMSSUtil.e(out, this.z, position3 + publicSeedSize);
        try {
            return Arrays.r(out, XMSSUtil.p(this.p0));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error serializing bds state");
        }
    }

    public long c() {
        return this.f;
    }

    public byte[] i() {
        return XMSSUtil.c(this.q);
    }

    public byte[] h() {
        return XMSSUtil.c(this.x);
    }

    public byte[] f() {
        return XMSSUtil.c(this.y);
    }

    public byte[] g() {
        return XMSSUtil.c(this.z);
    }

    /* access modifiers changed from: package-private */
    public BDSStateMap b() {
        return this.p0;
    }

    public XMSSMTParameters e() {
        return this.d;
    }

    public XMSSMTPrivateKeyParameters d() {
        return new Builder(this.d).l(this.f + 1).p(this.q).o(this.x).m(this.y).n(this.z).k(new BDSStateMap(this.p0, this.d, c(), this.y, this.q)).j();
    }
}
