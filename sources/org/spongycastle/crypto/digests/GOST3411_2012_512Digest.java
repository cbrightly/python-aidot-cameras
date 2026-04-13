package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;

public class GOST3411_2012_512Digest extends GOST3411_2012Digest {
    private static final byte[] m = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public GOST3411_2012_512Digest() {
        super(m);
    }

    public GOST3411_2012_512Digest(GOST3411_2012_512Digest other) {
        super(m);
        m(other);
    }

    public String b() {
        return "GOST3411-2012-512";
    }

    public int e() {
        return 64;
    }

    public Memoable copy() {
        return new GOST3411_2012_512Digest(this);
    }
}
