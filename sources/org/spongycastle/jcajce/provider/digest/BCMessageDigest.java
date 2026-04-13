package org.spongycastle.jcajce.provider.digest;

import java.security.MessageDigest;
import org.spongycastle.crypto.Digest;

public class BCMessageDigest extends MessageDigest {
    protected Digest c;

    protected BCMessageDigest(Digest digest) {
        super(digest.b());
        this.c = digest;
    }

    public void engineReset() {
        this.c.reset();
    }

    public void engineUpdate(byte input) {
        this.c.d(input);
    }

    public void engineUpdate(byte[] input, int offset, int len) {
        this.c.update(input, offset, len);
    }

    public byte[] engineDigest() {
        byte[] digestBytes = new byte[this.c.e()];
        this.c.c(digestBytes, 0);
        return digestBytes;
    }
}
