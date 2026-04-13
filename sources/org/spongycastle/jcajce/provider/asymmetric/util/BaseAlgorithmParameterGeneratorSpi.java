package org.spongycastle.jcajce.provider.asymmetric.util;

import java.security.AlgorithmParameterGeneratorSpi;
import java.security.AlgorithmParameters;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;

public abstract class BaseAlgorithmParameterGeneratorSpi extends AlgorithmParameterGeneratorSpi {
    private final JcaJceHelper a = new BCJcaJceHelper();

    /* access modifiers changed from: protected */
    public final AlgorithmParameters a(String algorithm) {
        return this.a.g(algorithm);
    }
}
