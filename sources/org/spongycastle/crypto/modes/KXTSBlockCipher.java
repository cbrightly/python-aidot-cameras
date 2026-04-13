package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Pack;

public class KXTSBlockCipher extends BufferedBlockCipher {
    private final int g;
    private final long h;
    private final long[] i;
    private final long[] j;
    private int k;

    public int c(int length) {
        return length;
    }

    public int e(int len) {
        return len;
    }

    public void f(boolean forEncryption, CipherParameters parameters) {
        if (parameters instanceof ParametersWithIV) {
            ParametersWithIV ivParam = (ParametersWithIV) parameters;
            CipherParameters parameters2 = ivParam.b();
            byte[] iv = ivParam.a();
            int length = iv.length;
            int i2 = this.g;
            if (length == i2) {
                byte[] tweak = new byte[i2];
                System.arraycopy(iv, 0, tweak, 0, i2);
                this.d.a(true, parameters2);
                this.d.f(tweak, 0, tweak, 0);
                this.d.a(forEncryption, parameters2);
                Pack.n(tweak, 0, this.i);
                long[] jArr = this.i;
                System.arraycopy(jArr, 0, this.j, 0, jArr.length);
                this.k = 0;
                return;
            }
            throw new IllegalArgumentException("Currently only support IVs of exactly one block");
        }
        throw new IllegalArgumentException("Invalid parameters passed");
    }

    public int g(byte[] input, int inOff, int len, byte[] output, int outOff) {
        if (input.length - inOff < len) {
            throw new DataLengthException("Input buffer too short");
        } else if (output.length - inOff < len) {
            throw new OutputLengthException("Output buffer too short");
        } else if (len % this.g == 0) {
            int pos = 0;
            while (pos < len) {
                j(input, inOff + pos, output, outOff + pos);
                pos += this.g;
            }
            return len;
        } else {
            throw new IllegalArgumentException("Partial blocks not supported");
        }
    }

    private void j(byte[] input, int inOff, byte[] output, int outOff) {
        int i2 = this.k;
        if (i2 != -1) {
            this.k = i2 + 1;
            i(this.h, this.j);
            byte[] tweak = new byte[this.g];
            Pack.t(this.j, tweak, 0);
            int i3 = this.g;
            byte[] buffer = new byte[i3];
            System.arraycopy(tweak, 0, buffer, 0, i3);
            for (int i4 = 0; i4 < this.g; i4++) {
                buffer[i4] = (byte) (buffer[i4] ^ input[inOff + i4]);
            }
            this.d.f(buffer, 0, buffer, 0);
            for (int i5 = 0; i5 < this.g; i5++) {
                output[outOff + i5] = (byte) (buffer[i5] ^ tweak[i5]);
            }
            return;
        }
        throw new IllegalStateException("Attempt to process too many blocks");
    }

    public int a(byte[] output, int outOff) {
        h();
        return 0;
    }

    public void h() {
        this.d.reset();
        long[] jArr = this.i;
        System.arraycopy(jArr, 0, this.j, 0, jArr.length);
        this.k = 0;
    }

    private static void i(long redPoly, long[] z) {
        long c = 0;
        for (int i2 = 0; i2 < z.length; i2++) {
            long zVal = z[i2];
            z[i2] = (zVal << 1) ^ c;
            c = zVal >>> 63;
        }
        z[0] = z[0] ^ ((-c) & redPoly);
    }
}
