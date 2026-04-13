package org.spongycastle.crypto.engines;

import java.math.BigInteger;

public class CramerShoupCoreEngine {
    private static final BigInteger a = BigInteger.valueOf(1);
    private String b = null;

    public static class CramerShoupCiphertextException extends Exception {
        private static final long serialVersionUID = -6360977166495345076L;

        public CramerShoupCiphertextException(String msg) {
            super(msg);
        }
    }
}
