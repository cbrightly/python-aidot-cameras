package org.spongycastle.jcajce.provider.asymmetric.util;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.jce.spec.IESParameterSpec;

public class IESUtil {
    public static IESParameterSpec a(BufferedBlockCipher iesBlockCipher, byte[] nonce) {
        if (iesBlockCipher == null) {
            return new IESParameterSpec((byte[]) null, (byte[]) null, 128);
        }
        BlockCipher underlyingCipher = iesBlockCipher.d();
        if (underlyingCipher.b().equals("DES") || underlyingCipher.b().equals("RC2") || underlyingCipher.b().equals("RC5-32") || underlyingCipher.b().equals("RC5-64")) {
            return new IESParameterSpec((byte[]) null, (byte[]) null, 64, 64, nonce);
        }
        if (underlyingCipher.b().equals("SKIPJACK")) {
            return new IESParameterSpec((byte[]) null, (byte[]) null, 80, 80, nonce);
        }
        if (underlyingCipher.b().equals("GOST28147")) {
            return new IESParameterSpec((byte[]) null, (byte[]) null, 256, 256, nonce);
        }
        return new IESParameterSpec((byte[]) null, (byte[]) null, 128, 128, nonce);
    }
}
