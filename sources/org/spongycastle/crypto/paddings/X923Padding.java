package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public class X923Padding implements BlockCipherPadding {
    SecureRandom a = null;

    public void b(SecureRandom random) {
        this.a = random;
    }

    public int c(byte[] in, int inOff) {
        byte code = (byte) (in.length - inOff);
        while (inOff < in.length - 1) {
            SecureRandom secureRandom = this.a;
            if (secureRandom == null) {
                in[inOff] = 0;
            } else {
                in[inOff] = (byte) secureRandom.nextInt();
            }
            inOff++;
        }
        in[inOff] = code;
        return code;
    }

    public int a(byte[] in) {
        int count = in[in.length - 1] & 255;
        if (count <= in.length) {
            return count;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}
