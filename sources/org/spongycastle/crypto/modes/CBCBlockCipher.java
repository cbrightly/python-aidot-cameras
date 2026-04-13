package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class CBCBlockCipher implements BlockCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;
    private BlockCipher e = null;
    private boolean f;

    public CBCBlockCipher(BlockCipher cipher) {
        this.e = cipher;
        int c2 = cipher.c();
        this.d = c2;
        this.a = new byte[c2];
        this.b = new byte[c2];
        this.c = new byte[c2];
    }

    public BlockCipher g() {
        return this.e;
    }

    public void a(boolean encrypting, CipherParameters params) {
        boolean oldEncrypting = this.f;
        this.f = encrypting;
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParam = (ParametersWithIV) params;
            byte[] iv = ivParam.a();
            if (iv.length == this.d) {
                System.arraycopy(iv, 0, this.a, 0, iv.length);
                reset();
                if (ivParam.b() != null) {
                    this.e.a(encrypting, ivParam.b());
                } else if (oldEncrypting != encrypting) {
                    throw new IllegalArgumentException("cannot change encrypting state without providing key.");
                }
            } else {
                throw new IllegalArgumentException("initialisation vector must be the same length as block size");
            }
        } else {
            reset();
            if (params != null) {
                this.e.a(encrypting, params);
            } else if (oldEncrypting != encrypting) {
                throw new IllegalArgumentException("cannot change encrypting state without providing key.");
            }
        }
    }

    public String b() {
        return this.e.b() + "/CBC";
    }

    public int c() {
        return this.e.c();
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        return this.f ? e(in, inOff, out, outOff) : d(in, inOff, out, outOff);
    }

    public void reset() {
        byte[] bArr = this.a;
        System.arraycopy(bArr, 0, this.b, 0, bArr.length);
        Arrays.F(this.c, (byte) 0);
        this.e.reset();
    }

    private int e(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.d + inOff <= in.length) {
            for (int i = 0; i < this.d; i++) {
                byte[] bArr = this.b;
                bArr[i] = (byte) (bArr[i] ^ in[inOff + i]);
            }
            int length = this.e.f(this.b, 0, out, outOff);
            byte[] bArr2 = this.b;
            System.arraycopy(out, outOff, bArr2, 0, bArr2.length);
            return length;
        }
        throw new DataLengthException("input buffer too short");
    }

    private int d(byte[] in, int inOff, byte[] out, int outOff) {
        int i = this.d;
        if (inOff + i <= in.length) {
            System.arraycopy(in, inOff, this.c, 0, i);
            int length = this.e.f(in, inOff, out, outOff);
            for (int i2 = 0; i2 < this.d; i2++) {
                int i3 = outOff + i2;
                out[i3] = (byte) (out[i3] ^ this.b[i2]);
            }
            byte[] tmp = this.b;
            this.b = this.c;
            this.c = tmp;
            return length;
        }
        throw new DataLengthException("input buffer too short");
    }
}
