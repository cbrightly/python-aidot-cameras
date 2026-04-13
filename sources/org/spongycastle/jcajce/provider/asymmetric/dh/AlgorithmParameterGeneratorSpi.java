package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.generators.DHParametersGenerator;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi;
import org.spongycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;

public class AlgorithmParameterGeneratorSpi extends BaseAlgorithmParameterGeneratorSpi {
    protected SecureRandom b;
    protected int c = 2048;
    private int d = 0;

    /* access modifiers changed from: protected */
    public void engineInit(int strength, SecureRandom random) {
        this.c = strength;
        this.b = random;
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
        if (genParamSpec instanceof DHGenParameterSpec) {
            DHGenParameterSpec spec = (DHGenParameterSpec) genParamSpec;
            this.c = spec.getPrimeSize();
            this.d = spec.getExponentSize();
            this.b = random;
            return;
        }
        throw new InvalidAlgorithmParameterException("DH parameter generator requires a DHGenParameterSpec for initialisation");
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGenerateParameters() {
        DHParametersGenerator pGen = new DHParametersGenerator();
        int certainty = PrimeCertaintyCalculator.a(this.c);
        SecureRandom secureRandom = this.b;
        if (secureRandom != null) {
            pGen.b(this.c, certainty, secureRandom);
        } else {
            pGen.b(this.c, certainty, new SecureRandom());
        }
        DHParameters p = pGen.a();
        try {
            AlgorithmParameters params = a("DH");
            params.init(new DHParameterSpec(p.e(), p.b(), this.d));
            return params;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
