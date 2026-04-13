package org.spongycastle.pqc.crypto.xmss;

public final class WOTSPlusSignature {
    private byte[][] a;

    protected WOTSPlusSignature(WOTSPlusParameters params, byte[][] signature) {
        if (params == null) {
            throw new NullPointerException("params == null");
        } else if (signature == null) {
            throw new NullPointerException("signature == null");
        } else if (XMSSUtil.k(signature)) {
            throw new NullPointerException("signature byte array == null");
        } else if (signature.length == params.c()) {
            int i = 0;
            while (i < signature.length) {
                if (signature[i].length == params.b()) {
                    i++;
                } else {
                    throw new IllegalArgumentException("wrong signature format");
                }
            }
            this.a = XMSSUtil.d(signature);
        } else {
            throw new IllegalArgumentException("wrong signature size");
        }
    }

    public byte[][] a() {
        return XMSSUtil.d(this.a);
    }
}
