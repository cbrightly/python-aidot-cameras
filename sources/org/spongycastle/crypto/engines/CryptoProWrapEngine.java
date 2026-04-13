package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.modes.GCFBBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.crypto.params.ParametersWithUKM;
import org.spongycastle.util.Pack;

public class CryptoProWrapEngine extends GOST28147WrapEngine {
    public void a(boolean forWrapping, CipherParameters param) {
        KeyParameter kParam;
        if (param instanceof ParametersWithRandom) {
            param = ((ParametersWithRandom) param).a();
        }
        ParametersWithUKM pU = (ParametersWithUKM) param;
        byte[] sBox = null;
        if (pU.a() instanceof ParametersWithSBox) {
            kParam = (KeyParameter) ((ParametersWithSBox) pU.a()).a();
            sBox = ((ParametersWithSBox) pU.a()).b();
        } else {
            kParam = (KeyParameter) pU.a();
        }
        KeyParameter kParam2 = new KeyParameter(e(kParam.a(), pU.b(), sBox));
        if (sBox != null) {
            super.a(forWrapping, new ParametersWithUKM(new ParametersWithSBox(kParam2, sBox), pU.b()));
        } else {
            super.a(forWrapping, new ParametersWithUKM(kParam2, pU.b()));
        }
    }

    private static byte[] e(byte[] K, byte[] ukm, byte[] sBox) {
        for (int i = 0; i != 8; i++) {
            int sOn = 0;
            int sOff = 0;
            for (int j = 0; j != 8; j++) {
                int kj = Pack.j(K, j * 4);
                if (d(ukm[i], j)) {
                    sOn += kj;
                } else {
                    sOff += kj;
                }
            }
            byte[] s = new byte[8];
            Pack.h(sOn, s, 0);
            Pack.h(sOff, s, 4);
            GCFBBlockCipher c = new GCFBBlockCipher(new GOST28147Engine());
            c.a(true, new ParametersWithIV(new ParametersWithSBox(new KeyParameter(K), sBox), s));
            c.f(K, 0, K, 0);
            c.f(K, 8, K, 8);
            c.f(K, 16, K, 16);
            c.f(K, 24, K, 24);
        }
        return K;
    }

    private static boolean d(byte v, int bitNo) {
        return ((1 << bitNo) & v) != 0;
    }
}
