package org.spongycastle.crypto.tls;

public class CipherSuite {
    public static boolean a(int cipherSuite) {
        switch (cipherSuite) {
            case 255:
            case 22016:
                return true;
            default:
                return false;
        }
    }
}
