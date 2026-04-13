package org.spongycastle.crypto.agreement;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SM3Digest;

public class SM2KeyExchange {
    private final Digest a;

    public SM2KeyExchange() {
        this(new SM3Digest());
    }

    public SM2KeyExchange(Digest digest) {
        this.a = digest;
    }
}
