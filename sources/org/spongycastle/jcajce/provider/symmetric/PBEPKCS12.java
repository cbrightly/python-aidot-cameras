package org.spongycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public class PBEPKCS12 {
    private PBEPKCS12() {
    }

    public static class AlgParams extends BaseAlgorithmParameters {
        PKCS12PBEParams params;

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
                return new PBEParameterSpec(this.params.getIV(), this.params.getIterations().intValue());
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PKCS12 PBE parameters object.");
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (paramSpec instanceof PBEParameterSpec) {
                PBEParameterSpec pbeSpec = (PBEParameterSpec) paramSpec;
                this.params = new PKCS12PBEParams(pbeSpec.getSalt(), pbeSpec.getIterationCount());
                return;
            }
            throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PKCS12 PBE parameters algorithm parameters object");
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params2) {
            this.params = PKCS12PBEParams.getInstance(ASN1Primitive.h(params2));
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params2, String format) {
            if (isASN1FormatString(format)) {
                engineInit(params2);
                return;
            }
            throw new IOException("Unknown parameters format in PKCS12 PBE parameters object");
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "PKCS12 PBE Parameters";
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = PBEPKCS12.class.getName();

        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("AlgorithmParameters.PKCS12PBE", a + "$AlgParams");
        }
    }
}
