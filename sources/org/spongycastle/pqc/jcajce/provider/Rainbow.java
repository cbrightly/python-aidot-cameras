package org.spongycastle.pqc.jcajce.provider;

import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.jcajce.provider.rainbow.RainbowKeyFactorySpi;

public class Rainbow {

    public static class Mappings extends AsymmetricAlgorithmProvider {
        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("KeyFactory.Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.RainbowKeyFactorySpi");
            provider.addAlgorithm("KeyPairGenerator.Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.RainbowKeyPairGeneratorSpi");
            b(provider, "SHA224", "Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.SignatureSpi$withSha224", PQCObjectIdentifiers.c);
            b(provider, "SHA256", "Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.SignatureSpi$withSha256", PQCObjectIdentifiers.d);
            b(provider, "SHA384", "Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.SignatureSpi$withSha384", PQCObjectIdentifiers.e);
            b(provider, "SHA512", "Rainbow", "org.spongycastle.pqc.jcajce.provider.rainbow.SignatureSpi$withSha512", PQCObjectIdentifiers.f);
            c(provider, PQCObjectIdentifiers.a, "Rainbow", new RainbowKeyFactorySpi());
        }
    }
}
