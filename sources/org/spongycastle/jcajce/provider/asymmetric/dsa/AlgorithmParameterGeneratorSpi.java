package org.spongycastle.jcajce.provider.asymmetric.dsa;

import com.alibaba.fastjson.asm.Opcodes;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.generators.DSAParametersGenerator;
import org.spongycastle.crypto.params.DSAParameterGenerationParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi;
import org.spongycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;

public class AlgorithmParameterGeneratorSpi extends BaseAlgorithmParameterGeneratorSpi {
    protected SecureRandom b;
    protected int c = 2048;
    protected DSAParameterGenerationParameters d;

    /* access modifiers changed from: protected */
    public void engineInit(int strength, SecureRandom random) {
        if (strength < 512 || strength > 3072) {
            throw new InvalidParameterException("strength must be from 512 - 3072");
        } else if (strength <= 1024 && strength % 64 != 0) {
            throw new InvalidParameterException("strength must be a multiple of 64 below 1024 bits.");
        } else if (strength <= 1024 || strength % 1024 == 0) {
            this.c = strength;
            this.b = random;
        } else {
            throw new InvalidParameterException("strength must be a multiple of 1024 above 1024 bits.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
        throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DSA parameter generation.");
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGenerateParameters() {
        DSAParametersGenerator pGen;
        if (this.c <= 1024) {
            pGen = new DSAParametersGenerator();
        } else {
            pGen = new DSAParametersGenerator(new SHA256Digest());
        }
        if (this.b == null) {
            this.b = new SecureRandom();
        }
        int certainty = PrimeCertaintyCalculator.a(this.c);
        int i = this.c;
        if (i == 1024) {
            DSAParameterGenerationParameters dSAParameterGenerationParameters = new DSAParameterGenerationParameters(1024, Opcodes.IF_ICMPNE, certainty, this.b);
            this.d = dSAParameterGenerationParameters;
            pGen.l(dSAParameterGenerationParameters);
        } else if (i > 1024) {
            DSAParameterGenerationParameters dSAParameterGenerationParameters2 = new DSAParameterGenerationParameters(i, 256, certainty, this.b);
            this.d = dSAParameterGenerationParameters2;
            pGen.l(dSAParameterGenerationParameters2);
        } else {
            pGen.k(i, certainty, this.b);
        }
        DSAParameters p = pGen.d();
        try {
            AlgorithmParameters params = a("DSA");
            params.init(new DSAParameterSpec(p.b(), p.c(), p.a()));
            return params;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
