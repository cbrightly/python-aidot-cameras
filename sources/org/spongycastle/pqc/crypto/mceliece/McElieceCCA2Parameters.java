package org.spongycastle.pqc.crypto.mceliece;

public class McElieceCCA2Parameters extends McElieceParameters {
    private final String y;

    public McElieceCCA2Parameters() {
        this(11, 50, "SHA-256");
    }

    public McElieceCCA2Parameters(int m, int t, String digest) {
        super(m, t);
        this.y = digest;
    }

    public String e() {
        return this.y;
    }
}
