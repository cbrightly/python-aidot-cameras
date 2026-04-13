package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;

public class TBCPadding implements BlockCipherPadding {
    public void b(SecureRandom random) {
    }

    public int c(byte[] in, int inOff) {
        byte code;
        int count = in.length - inOff;
        int i = 255;
        if (inOff > 0) {
            if ((in[inOff - 1] & 1) != 0) {
                i = 0;
            }
            code = (byte) i;
        } else {
            if ((in[in.length - 1] & 1) != 0) {
                i = 0;
            }
            code = (byte) i;
        }
        while (inOff < in.length) {
            in[inOff] = code;
            inOff++;
        }
        return count;
    }

    public int a(byte[] in) {
        byte code = in[in.length - 1];
        int index = in.length - 1;
        while (index > 0 && in[index - 1] == code) {
            index--;
        }
        return in.length - index;
    }
}
