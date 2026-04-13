package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;

public class NullEngine implements BlockCipher {
    private boolean a;
    private final int b;

    public NullEngine() {
        this(1);
    }

    public NullEngine(int blockSize) {
        this.b = blockSize;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        this.a = true;
    }

    public String b() {
        return "Null";
    }

    public int c() {
        return this.b;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.a) {
            int i = this.b;
            if (inOff + i > in.length) {
                throw new DataLengthException("input buffer too short");
            } else if (i + outOff <= out.length) {
                int i2 = 0;
                while (true) {
                    int i3 = this.b;
                    if (i2 >= i3) {
                        return i3;
                    }
                    out[outOff + i2] = in[inOff + i2];
                    i2++;
                }
            } else {
                throw new OutputLengthException("output buffer too short");
            }
        } else {
            throw new IllegalStateException("Null engine not initialised");
        }
    }

    public void reset() {
    }
}
