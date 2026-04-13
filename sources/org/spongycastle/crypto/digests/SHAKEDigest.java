package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.Xof;

public class SHAKEDigest extends KeccakDigest implements Xof {
    private static int E(int bitLength) {
        switch (bitLength) {
            case 128:
            case 256:
                return bitLength;
            default:
                throw new IllegalArgumentException("'bitLength' " + bitLength + " not supported for SHAKE");
        }
    }

    public SHAKEDigest() {
        this(128);
    }

    public SHAKEDigest(int bitLength) {
        super(E(bitLength));
    }

    public String b() {
        return "SHAKE" + this.g;
    }

    public int c(byte[] out, int outOff) {
        return j(out, outOff, e());
    }

    public int j(byte[] out, int outOff, int outLen) {
        int length = F(out, outOff, outLen);
        reset();
        return length;
    }

    public int F(byte[] out, int outOff, int outLen) {
        if (!this.h) {
            r(15, 4);
        }
        C(out, outOff, ((long) outLen) * 8);
        return outLen;
    }
}
