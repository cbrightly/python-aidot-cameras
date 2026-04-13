package org.spongycastle.jcajce.provider.symmetric;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.util.Strings;

public final class OpenSSLPBKDF {
    private OpenSSLPBKDF() {
    }

    public static class PBKDF extends BaseSecretKeyFactory {
        public PBKDF() {
            super("PBKDF-OpenSSL", (ASN1ObjectIdentifier) null);
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateSecret(KeySpec keySpec) {
            if (keySpec instanceof PBEKeySpec) {
                PBEKeySpec pbeSpec = (PBEKeySpec) keySpec;
                if (pbeSpec.getSalt() == null) {
                    throw new InvalidKeySpecException("missing required salt");
                } else if (pbeSpec.getIterationCount() <= 0) {
                    throw new InvalidKeySpecException("positive iteration count required: " + pbeSpec.getIterationCount());
                } else if (pbeSpec.getKeyLength() <= 0) {
                    throw new InvalidKeySpecException("positive key length required: " + pbeSpec.getKeyLength());
                } else if (pbeSpec.getPassword().length != 0) {
                    OpenSSLPBEParametersGenerator pGen = new OpenSSLPBEParametersGenerator();
                    pGen.i(Strings.g(pbeSpec.getPassword()), pbeSpec.getSalt());
                    return new SecretKeySpec(((KeyParameter) pGen.e(pbeSpec.getKeyLength())).a(), "OpenSSLPBKDF");
                } else {
                    throw new IllegalArgumentException("password empty");
                }
            } else {
                throw new InvalidKeySpecException("Invalid KeySpec");
            }
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = OpenSSLPBKDF.class.getName();

        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("SecretKeyFactory.PBKDF-OPENSSL", a + "$PBKDF");
        }
    }
}
