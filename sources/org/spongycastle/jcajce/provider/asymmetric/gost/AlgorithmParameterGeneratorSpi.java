package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.generators.GOST3410ParametersGenerator;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi;
import org.spongycastle.jce.spec.GOST3410ParameterSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public abstract class AlgorithmParameterGeneratorSpi extends BaseAlgorithmParameterGeneratorSpi {
    protected SecureRandom b;
    protected int c = 1024;

    /* access modifiers changed from: protected */
    public void engineInit(int strength, SecureRandom random) {
        this.c = strength;
        this.b = random;
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
        throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for GOST3410 parameter generation.");
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGenerateParameters() {
        GOST3410ParametersGenerator pGen = new GOST3410ParametersGenerator();
        SecureRandom secureRandom = this.b;
        if (secureRandom != null) {
            pGen.b(this.c, 2, secureRandom);
        } else {
            pGen.b(this.c, 2, new SecureRandom());
        }
        GOST3410Parameters p = pGen.a();
        try {
            AlgorithmParameters params = a("GOST3410");
            params.init(new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(p.b(), p.c(), p.a())));
            return params;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
