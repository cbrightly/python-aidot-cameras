package org.spongycastle.pqc.crypto.xmss;

public final class WOTSPlusPublicKeyParameters {
    private final byte[][] a;

    protected WOTSPlusPublicKeyParameters(WOTSPlusParameters params, byte[][] publicKey) {
        if (params == null) {
            throw new NullPointerException("params == null");
        } else if (publicKey == null) {
            throw new NullPointerException("publicKey == null");
        } else if (XMSSUtil.k(publicKey)) {
            throw new NullPointerException("publicKey byte array == null");
        } else if (publicKey.length == params.c()) {
            int i = 0;
            while (i < publicKey.length) {
                if (publicKey[i].length == params.b()) {
                    i++;
                } else {
                    throw new IllegalArgumentException("wrong publicKey format");
                }
            }
            this.a = XMSSUtil.d(publicKey);
        } else {
            throw new IllegalArgumentException("wrong publicKey size");
        }
    }

    /* access modifiers changed from: protected */
    public byte[][] a() {
        return XMSSUtil.d(this.a);
    }
}
