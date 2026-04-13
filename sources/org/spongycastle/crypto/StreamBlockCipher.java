package org.spongycastle.crypto;

public abstract class StreamBlockCipher implements BlockCipher, StreamCipher {
    private final BlockCipher a;

    /* access modifiers changed from: protected */
    public abstract byte g(byte b);

    protected StreamBlockCipher(BlockCipher cipher) {
        this.a = cipher;
    }

    public BlockCipher h() {
        return this.a;
    }

    public final byte e(byte in) {
        return g(in);
    }

    public int d(byte[] in, int inOff, int len, byte[] out, int outOff) {
        if (inOff + len > in.length) {
            throw new DataLengthException("input buffer too small");
        } else if (outOff + len <= out.length) {
            int inEnd = inOff + len;
            int outStart = outOff;
            for (int inStart = inOff; inStart < inEnd; inStart++) {
                out[outStart] = g(in[inStart]);
                outStart++;
            }
            return len;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }
}
