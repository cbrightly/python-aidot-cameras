package org.spongycastle.pqc.crypto.newhope;

import org.spongycastle.crypto.engines.ChaChaEngine;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class ChaCha20 {
    ChaCha20() {
    }

    static void a(byte[] key, byte[] nonce, byte[] buf, int off, int len) {
        ChaChaEngine e = new ChaChaEngine(20);
        e.a(true, new ParametersWithIV(new KeyParameter(key), nonce));
        e.d(buf, off, len, buf, off);
    }
}
