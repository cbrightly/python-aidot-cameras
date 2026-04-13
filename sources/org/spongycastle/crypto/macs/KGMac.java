package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.modes.KGCMBlockCipher;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class KGMac implements Mac {
    private final KGCMBlockCipher a;
    private final int b;

    public KGMac(KGCMBlockCipher cipher, int macSizeBits) {
        this.a = cipher;
        this.b = macSizeBits;
    }

    public void a(CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            ParametersWithIV param = (ParametersWithIV) params;
            this.a.a(true, new AEADParameters((KeyParameter) param.b(), this.b, param.a()));
            return;
        }
        throw new IllegalArgumentException("KGMAC requires ParametersWithIV");
    }

    public String b() {
        return this.a.g().b() + "-KGMAC";
    }

    public int e() {
        return this.b / 8;
    }

    public void d(byte in) {
        this.a.m(in);
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
        this.a.n();
    }
}
