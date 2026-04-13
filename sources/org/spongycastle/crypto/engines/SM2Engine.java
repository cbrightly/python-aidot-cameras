package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SM3Digest;

public class SM2Engine {
    private final Digest a;

    public SM2Engine() {
        this(new SM3Digest());
    }

    public SM2Engine(Digest digest) {
        this.a = digest;
    }
}
