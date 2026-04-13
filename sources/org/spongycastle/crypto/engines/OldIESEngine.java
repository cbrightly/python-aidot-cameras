package org.spongycastle.crypto.engines;

import org.spongycastle.util.Pack;

public class OldIESEngine extends IESEngine {
    /* access modifiers changed from: protected */
    public byte[] e(byte[] p2) {
        byte[] L2 = new byte[4];
        if (p2 != null) {
            Pack.d(p2.length * 8, L2, 0);
        }
        return L2;
    }
}
