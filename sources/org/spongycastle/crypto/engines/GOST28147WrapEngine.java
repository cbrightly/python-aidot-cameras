package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.macs.GOST28147Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.crypto.params.ParametersWithUKM;
import org.spongycastle.util.Arrays;

public class GOST28147WrapEngine implements Wrapper {
    private GOST28147Engine a = new GOST28147Engine();
    private GOST28147Mac b = new GOST28147Mac();

    public void a(boolean forWrapping, CipherParameters param) {
        KeyParameter kParam;
        if (param instanceof ParametersWithRandom) {
            param = ((ParametersWithRandom) param).a();
        }
        ParametersWithUKM pU = (ParametersWithUKM) param;
        this.a.a(forWrapping, pU.a());
        if (pU.a() instanceof ParametersWithSBox) {
            kParam = (KeyParameter) ((ParametersWithSBox) pU.a()).a();
        } else {
            kParam = (KeyParameter) pU.a();
        }
        this.b.a(new ParametersWithIV(kParam, pU.b()));
    }

    public String b() {
        return "GOST28147Wrap";
    }

    public byte[] wrap(byte[] input, int inOff, int inLen) {
        this.b.update(input, inOff, inLen);
        byte[] wrappedKey = new byte[(this.b.e() + inLen)];
        this.a.f(input, inOff, wrappedKey, 0);
        this.a.f(input, inOff + 8, wrappedKey, 8);
        this.a.f(input, inOff + 16, wrappedKey, 16);
        this.a.f(input, inOff + 24, wrappedKey, 24);
        this.b.c(wrappedKey, inLen);
        return wrappedKey;
    }

    public byte[] c(byte[] input, int inOff, int inLen) {
        byte[] decKey = new byte[(inLen - this.b.e())];
        this.a.f(input, inOff, decKey, 0);
        this.a.f(input, inOff + 8, decKey, 8);
        this.a.f(input, inOff + 16, decKey, 16);
        this.a.f(input, inOff + 24, decKey, 24);
        byte[] macResult = new byte[this.b.e()];
        this.b.update(decKey, 0, decKey.length);
        this.b.c(macResult, 0);
        byte[] macExpected = new byte[this.b.e()];
        System.arraycopy(input, (inOff + inLen) - 4, macExpected, 0, this.b.e());
        if (Arrays.u(macResult, macExpected)) {
            return decKey;
        }
        throw new IllegalStateException("mac mismatch");
    }
}
