package org.spongycastle.crypto.digests;

public class SHA3Digest extends KeccakDigest {
    private static int E(int bitLength) {
        switch (bitLength) {
            case 224:
            case 256:
            case 384:
            case 512:
                return bitLength;
            default:
                throw new IllegalArgumentException("'bitLength' " + bitLength + " not supported for SHA-3");
        }
    }

    public SHA3Digest() {
        this(256);
    }

    public SHA3Digest(int bitLength) {
        super(E(bitLength));
    }

    public SHA3Digest(SHA3Digest source) {
        super((KeccakDigest) source);
    }

    public String b() {
        return "SHA3-" + this.g;
    }

    public int c(byte[] out, int outOff) {
        r(2, 2);
        return super.c(out, outOff);
    }
}
