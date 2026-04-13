package org.spongycastle.pqc.crypto.xmss;

import java.io.IOException;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.crypto.xmss.OTSHashAddress;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;

public final class XMSSPrivateKeyParameters extends AsymmetricKeyParameter implements XMSSStoreableObjectInterface {
    private final XMSSParameters d;
    private final byte[] f;
    private final byte[] q;
    private final byte[] x;
    private final byte[] y;
    private final BDS z;

    private XMSSPrivateKeyParameters(Builder builder) {
        super(true);
        XMSSParameters a = builder.a;
        this.d = a;
        if (a != null) {
            int n = a.c();
            byte[] privateKey = builder.h;
            if (privateKey == null) {
                byte[] tmpSecretKeySeed = builder.c;
                if (tmpSecretKeySeed == null) {
                    this.f = new byte[n];
                } else if (tmpSecretKeySeed.length == n) {
                    this.f = tmpSecretKeySeed;
                } else {
                    throw new IllegalArgumentException("size of secretKeySeed needs to be equal size of digest");
                }
                byte[] tmpSecretKeyPRF = builder.d;
                if (tmpSecretKeyPRF == null) {
                    this.q = new byte[n];
                } else if (tmpSecretKeyPRF.length == n) {
                    this.q = tmpSecretKeyPRF;
                } else {
                    throw new IllegalArgumentException("size of secretKeyPRF needs to be equal size of digest");
                }
                byte[] tmpPublicSeed = builder.e;
                if (tmpPublicSeed == null) {
                    this.x = new byte[n];
                } else if (tmpPublicSeed.length == n) {
                    this.x = tmpPublicSeed;
                } else {
                    throw new IllegalArgumentException("size of publicSeed needs to be equal size of digest");
                }
                byte[] tmpRoot = builder.f;
                if (tmpRoot == null) {
                    this.y = new byte[n];
                } else if (tmpRoot.length == n) {
                    this.y = tmpRoot;
                } else {
                    throw new IllegalArgumentException("size of root needs to be equal size of digest");
                }
                BDS tmpBDSState = builder.g;
                if (tmpBDSState != null) {
                    this.z = tmpBDSState;
                } else if (builder.b >= (1 << a.d()) - 2 || tmpPublicSeed == null || tmpSecretKeySeed == null) {
                    this.z = new BDS(a, builder.b);
                } else {
                    this.z = new BDS(a, tmpPublicSeed, tmpSecretKeySeed, (OTSHashAddress) new OTSHashAddress.Builder().l(), builder.b);
                }
            } else if (builder.i != null) {
                int height = a.d();
                int secretKeySize = n;
                int secretKeyPRFSize = n;
                int publicSeedSize = n;
                int rootSize = n;
                int index = Pack.a(privateKey, 0);
                if (XMSSUtil.l(height, (long) index)) {
                    int position = 0 + 4;
                    this.f = XMSSUtil.g(privateKey, position, secretKeySize);
                    int position2 = position + secretKeySize;
                    this.q = XMSSUtil.g(privateKey, position2, secretKeyPRFSize);
                    int position3 = position2 + secretKeyPRFSize;
                    this.x = XMSSUtil.g(privateKey, position3, publicSeedSize);
                    int position4 = position3 + publicSeedSize;
                    this.y = XMSSUtil.g(privateKey, position4, rootSize);
                    int position5 = position4 + rootSize;
                    BDS bdsImport = null;
                    try {
                        bdsImport = (BDS) XMSSUtil.f(XMSSUtil.g(privateKey, position5, privateKey.length - position5));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e2) {
                        e2.printStackTrace();
                    }
                    bdsImport.setXMSS(builder.i);
                    bdsImport.validate();
                    if (bdsImport.getIndex() == index) {
                        this.z = bdsImport;
                        return;
                    }
                    throw new IllegalStateException("serialized BDS has wrong index");
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
        public final XMSSParameters a;
        /* access modifiers changed from: private */
        public int b = 0;
        /* access modifiers changed from: private */
        public byte[] c = null;
        /* access modifiers changed from: private */
        public byte[] d = null;
        /* access modifiers changed from: private */
        public byte[] e = null;
        /* access modifiers changed from: private */
        public byte[] f = null;
        /* access modifiers changed from: private */
        public BDS g = null;
        /* access modifiers changed from: private */
        public byte[] h = null;
        /* access modifiers changed from: private */
        public XMSSParameters i = null;

        public Builder(XMSSParameters params) {
            this.a = params;
        }

        public Builder l(int val) {
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

        public Builder k(BDS valBDS) {
            this.g = valBDS;
            return this;
        }

        public XMSSPrivateKeyParameters j() {
            return new XMSSPrivateKeyParameters(this);
        }
    }

    public byte[] j() {
        int n = this.d.c();
        int secretKeySize = n;
        int secretKeyPRFSize = n;
        int publicSeedSize = n;
        byte[] out = new byte[(4 + secretKeySize + secretKeyPRFSize + publicSeedSize + n)];
        Pack.d(this.z.getIndex(), out, 0);
        int position = 0 + 4;
        XMSSUtil.e(out, this.f, position);
        int position2 = position + secretKeySize;
        XMSSUtil.e(out, this.q, position2);
        int position3 = position2 + secretKeyPRFSize;
        XMSSUtil.e(out, this.x, position3);
        XMSSUtil.e(out, this.y, position3 + publicSeedSize);
        try {
            return Arrays.r(out, XMSSUtil.p(this.z));
        } catch (IOException e) {
            throw new RuntimeException("error serializing bds state: " + e.getMessage());
        }
    }

    public int c() {
        return this.z.getIndex();
    }

    public byte[] i() {
        return XMSSUtil.c(this.f);
    }

    public byte[] h() {
        return XMSSUtil.c(this.q);
    }

    public byte[] f() {
        return XMSSUtil.c(this.x);
    }

    public byte[] g() {
        return XMSSUtil.c(this.y);
    }

    /* access modifiers changed from: package-private */
    public BDS b() {
        return this.z;
    }

    public XMSSParameters e() {
        return this.d;
    }

    public XMSSPrivateKeyParameters d() {
        if (c() < (1 << this.d.d()) - 1) {
            return new Builder(this.d).p(this.f).o(this.q).m(this.x).n(this.y).k(this.z.getNextState(this.x, this.f, (OTSHashAddress) new OTSHashAddress.Builder().l())).j();
        }
        return new Builder(this.d).p(this.f).o(this.q).m(this.x).n(this.y).k(new BDS(this.d, c() + 1)).j();
    }
}
