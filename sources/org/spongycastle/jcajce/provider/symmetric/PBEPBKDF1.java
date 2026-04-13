package org.spongycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.asn1.pkcs.PBEParameter;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public class PBEPBKDF1 {
    private PBEPBKDF1() {
    }

    public static class AlgParams extends BaseAlgorithmParameters {
        PBEParameter params;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            try {
                return this.params.getEncoded("DER");
            } catch (IOException e) {
                throw new RuntimeException("Oooops! " + e.toString());
            }
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String format) {
            if (isASN1FormatString(format)) {
                return engineGetEncoded();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec) {
            if (paramSpec == PBEParameterSpec.class) {
                return new PBEParameterSpec(this.params.getSalt(), this.params.getIterationCount().intValue());
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PBKDF1 PBE parameters object.");
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (paramSpec instanceof PBEParameterSpec) {
                PBEParameterSpec pbeSpec = (PBEParameterSpec) paramSpec;
                this.params = new PBEParameter(pbeSpec.getSalt(), pbeSpec.getIterationCount());
                return;
            }
            throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PBKDF1 PBE parameters algorithm parameters object");
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params2) {
            this.params = PBEParameter.e(params2);
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params2, String format) {
            if (isASN1FormatString(format)) {
                engineInit(params2);
                return;
            }
            throw new IOException("Unknown parameters format in PBKDF2 parameters object");
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "PBKDF1 Parameters";
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = PBEPBKDF1.class.getName();

        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("AlgorithmParameters.PBKDF1", a + "$AlgParams");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.d0, "PBKDF1");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.f0, "PBKDF1");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.g0, "PBKDF1");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.h0, "PBKDF1");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.i0, "PBKDF1");
        }
    }
}
