package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.modes.GCMBlockCipher;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class GMac implements Mac {
    private final GCMBlockCipher a;
    private final int b = 128;

    public GMac(GCMBlockCipher cipher) {
        this.a = cipher;
    }

    public void a(CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            ParametersWithIV param = (ParametersWithIV) params;
            this.a.a(true, new AEADParameters((KeyParameter) param.b(), this.b, param.a()));
            return;
        }
        throw new IllegalArgumentException("GMAC requires ParametersWithIV");
    }

    public String b() {
        return this.a.g().b() + "-GMAC";
    }

    public int e() {
        return this.b / 8;
    }

    public void d(byte in) {
        this.a.r(in);
    }

    public void update(byte[] in, int inOff, int len) {
        this.a.i(in, inOff, len);
    }

    public int c(byte[] out, int outOff) {
        try {
            return this.a.c(out, outOff);
        } catch (InvalidCipherTextException e) {
            throw new IllegalStateException(e.toString());
        }
    }

    public void reset() {
        this.a.s();
    }
}
