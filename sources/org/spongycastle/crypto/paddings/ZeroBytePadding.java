package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;

public class ZeroBytePadding implements BlockCipherPadding {
    public void b(SecureRandom random) {
    }

    public int c(byte[] in, int inOff) {
        int added = in.length - inOff;
        while (inOff < in.length) {
            in[inOff] = 0;
            inOff++;
        }
        return added;
    }

    public int a(byte[] in) {
        int count = in.length;
        while (count > 0 && in[count - 1] == 0) {
            count--;
        }
        return in.length - count;
    }
}
