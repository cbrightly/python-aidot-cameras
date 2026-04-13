package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public class PKCS7Padding implements BlockCipherPadding {
    public void b(SecureRandom random) {
    }

    public int c(byte[] in, int inOff) {
        byte code = (byte) (in.length - inOff);
        while (inOff < in.length) {
            in[inOff] = code;
            inOff++;
        }
        return code;
    }

    public int a(byte[] in) {
        int count = in[in.length - 1] & 255;
        byte countAsbyte = (byte) count;
        boolean failed = (count > in.length) | (count == 0);
        for (int i = 0; i < in.length; i++) {
            failed |= (in.length - i <= count) & (in[i] != countAsbyte);
        }
        if (!failed) {
            return count;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}
