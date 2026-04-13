package org.spongycastle.crypto.params;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;

public class ParametersWithRandom implements CipherParameters {
    private SecureRandom c;
    private CipherParameters d;

    public ParametersWithRandom(CipherParameters parameters, SecureRandom random) {
        this.c = random;
        this.d = parameters;
    }

    public SecureRandom b() {
        return this.c;
    }

    public CipherParameters a() {
        return this.d;
    }
}
