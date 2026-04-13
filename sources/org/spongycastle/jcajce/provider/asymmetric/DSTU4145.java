package org.spongycastle.jcajce.provider.asymmetric;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ua.UAObjectIdentifiers;
import org.spongycastle.jcajce.provider.asymmetric.dstu.KeyFactorySpi;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class DSTU4145 {

    public static class Mappings extends AsymmetricAlgorithmProvider {
        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("KeyFactory.DSTU4145", "org.spongycastle.jcajce.provider.asymmetric.dstu.KeyFactorySpi");
            provider.addAlgorithm("Alg.Alias.KeyFactory.DSTU-4145-2002", "DSTU4145");
            provider.addAlgorithm("Alg.Alias.KeyFactory.DSTU4145-3410", "DSTU4145");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = UAObjectIdentifiers.b;
            c(provider, aSN1ObjectIdentifier, "DSTU4145", new KeyFactorySpi());
            e(provider, aSN1ObjectIdentifier, "DSTU4145");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = UAObjectIdentifiers.c;
            c(provider, aSN1ObjectIdentifier2, "DSTU4145", new KeyFactorySpi());
            e(provider, aSN1ObjectIdentifier2, "DSTU4145");
            provider.addAlgorithm("KeyPairGenerator.DSTU4145", "org.spongycastle.jcajce.provider.asymmetric.dstu.KeyPairGeneratorSpi");
            provider.addAlgorithm("Alg.Alias.KeyPairGenerator.DSTU-4145", "DSTU4145");
            provider.addAlgorithm("Alg.Alias.KeyPairGenerator.DSTU-4145-2002", "DSTU4145");
            provider.addAlgorithm("Signature.DSTU4145", "org.spongycastle.jcajce.provider.asymmetric.dstu.SignatureSpi");
            provider.addAlgorithm("Alg.Alias.Signature.DSTU-4145", "DSTU4145");
            provider.addAlgorithm("Alg.Alias.Signature.DSTU-4145-2002", "DSTU4145");
            b(provider, "GOST3411", "DSTU4145LE", "org.spongycastle.jcajce.provider.asymmetric.dstu.SignatureSpiLe", aSN1ObjectIdentifier);
            b(provider, "GOST3411", "DSTU4145", "org.spongycastle.jcajce.provider.asymmetric.dstu.SignatureSpi", aSN1ObjectIdentifier2);
        }
    }
}
