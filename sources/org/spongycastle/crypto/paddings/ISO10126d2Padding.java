package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public class ISO10126d2Padding implements BlockCipherPadding {
    SecureRandom a;

    public void b(SecureRandom random) {
        if (random != null) {
            this.a = random;
        } else {
            this.a = new SecureRandom();
        }
    }

    public int c(byte[] in, int inOff) {
        byte code = (byte) (in.length - inOff);
        while (inOff < in.length - 1) {
            in[inOff] = (byte) this.a.nextInt();
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
