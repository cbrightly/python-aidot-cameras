package org.spongycastle.jcajce;

import javax.crypto.interfaces.PBEKey;
import org.spongycastle.util.Arrays;

public class PKCS12KeyWithParameters extends PKCS12Key implements PBEKey {
    private final int iterationCount;
    private final byte[] salt;

    public PKCS12KeyWithParameters(char[] password, byte[] salt2, int iterationCount2) {
        super(password);
        this.salt = Arrays.h(salt2);
        this.iterationCount = iterationCount2;
    }

    public PKCS12KeyWithParameters(char[] password, boolean useWrongZeroLengthConversion, byte[] salt2, int iterationCount2) {
        super(password, useWrongZeroLengthConversion);
        this.salt = Arrays.h(salt2);
        this.iterationCount = iterationCount2;
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public int getIterationCount() {
        return this.iterationCount;
    }
}
