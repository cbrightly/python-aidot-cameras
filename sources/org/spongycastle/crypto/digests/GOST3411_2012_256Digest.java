package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;

public final class GOST3411_2012_256Digest extends GOST3411_2012Digest {
    private static final byte[] m = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    public GOST3411_2012_256Digest() {
        super(m);
    }

    public GOST3411_2012_256Digest(GOST3411_2012_256Digest other) {
        super(m);
        m(other);
    }

    public String b() {
        return "GOST3411-2012-256";
    }

    public int e() {
        return 32;
    }

    public int c(byte[] out, int outOff) {
        byte[] result = new byte[64];
        super.c(result, 0);
        System.arraycopy(result, 32, out, outOff, 32);
        return 32;
    }

    public Memoable copy() {
        return new GOST3411_2012_256Digest(this);
    }
}
