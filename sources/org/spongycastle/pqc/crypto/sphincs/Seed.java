package org.spongycastle.pqc.crypto.sphincs;

import org.spongycastle.crypto.engines.ChaChaEngine;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.pqc.crypto.sphincs.Tree;
import org.spongycastle.util.Pack;

public class Seed {
    Seed() {
    }

    static void a(HashFunctions hs, byte[] seed, int seedOff, byte[] sk, Tree.leafaddr a) {
        byte[] buffer = new byte[40];
        for (int i = 0; i < 32; i++) {
            buffer[i] = sk[i];
        }
        Pack.r(((long) a.a) | (a.b << 4) | (a.c << 59), buffer, 32);
        hs.f(seed, seedOff, buffer, buffer.length);
    }

    static void b(byte[] r, int rOff, long rlen, byte[] key, int keyOff) {
        ChaChaEngine chaChaEngine = new ChaChaEngine(12);
        chaChaEngine.a(true, new ParametersWithIV(new KeyParameter(key, keyOff, 32), new byte[8]));
        chaChaEngine.d(r, rOff, (int) rlen, r, rOff);
    }
}
