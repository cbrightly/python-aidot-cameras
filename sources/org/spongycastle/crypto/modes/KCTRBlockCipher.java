package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class KCTRBlockCipher extends StreamBlockCipher {
    private byte[] b;
    private byte[] c;
    private byte[] d;
    private int e;
    private boolean f;
    private BlockCipher g;

    public KCTRBlockCipher(BlockCipher engine) {
        super(engine);
        this.g = engine;
        this.b = new byte[engine.c()];
        this.c = new byte[engine.c()];
        this.d = new byte[engine.c()];
    }

    public void a(boolean forEncryption, CipherParameters params) {
        this.f = true;
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParam = (ParametersWithIV) params;
            byte[] iv = ivParam.a();
            byte[] bArr = this.b;
            Arrays.F(bArr, (byte) 0);
            System.arraycopy(iv, 0, this.b, bArr.length - iv.length, iv.length);
            CipherParameters params2 = ivParam.b();
            if (params2 != null) {
                this.g.a(true, params2);
            }
            reset();
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed");
    }

    public String b() {
        return this.g.b() + "/KCTR";
    }

    public int c() {
        return this.g.c();
    }

    /* access modifiers changed from: protected */
    public byte g(byte b2) {
        int i = this.e;
        if (i == 0) {
            j(0);
            i();
            this.g.f(this.c, 0, this.d, 0);
            byte[] bArr = this.d;
            int i2 = this.e;
            this.e = i2 + 1;
            return (byte) (bArr[i2] ^ b2);
        }
        byte[] bArr2 = this.d;
        int i3 = i + 1;
        this.e = i3;
        byte rv = (byte) (bArr2[i] ^ b2);
        if (i3 == this.c.length) {
            this.e = 0;
        }
        return rv;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (in.length - inOff < c()) {
            throw new DataLengthException("input buffer too short");
        } else if (out.length - outOff >= c()) {
            d(in, inOff, c(), out, outOff);
            return c();
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
        if (this.f) {
            this.g.f(this.b, 0, this.c, 0);
        }
        this.g.reset();
        this.e = 0;
    }

    private void j(int pos) {
        int i = pos;
        while (true) {
            byte[] bArr = this.c;
            if (i < bArr.length) {
                int i2 = i + 1;
                byte b2 = (byte) (bArr[i] + 1);
                bArr[i] = b2;
                if (b2 != 0) {
                    int i3 = i2;
                    return;
                }
                i = i2;
            } else {
                return;
            }
        }
    }

    private void i() {
    }
}
