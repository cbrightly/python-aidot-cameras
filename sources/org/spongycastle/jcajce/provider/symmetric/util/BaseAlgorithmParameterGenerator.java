package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.AlgorithmParameterGeneratorSpi;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;

public abstract class BaseAlgorithmParameterGenerator extends AlgorithmParameterGeneratorSpi {
    private final JcaJceHelper a = new BCJcaJceHelper();
    protected SecureRandom b;
    protected int c = 1024;

    /* access modifiers changed from: protected */
    public final AlgorithmParameters a(String algorithm) {
        return this.a.g(algorithm);
    }

    /* access modifiers changed from: protected */
    public void engineInit(int strength, SecureRandom random) {
        this.c = strength;
        this.b = random;
    }
}
