package org.spongycastle.jcajce.provider.asymmetric;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.jcajce.provider.asymmetric.gost.KeyFactorySpi;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class GOST {

    public static class Mappings extends AsymmetricAlgorithmProvider {
        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("KeyPairGenerator.GOST3410", "org.spongycastle.jcajce.provider.asymmetric.gost.KeyPairGeneratorSpi");
            provider.addAlgorithm("Alg.Alias.KeyPairGenerator.GOST-3410", "GOST3410");
            provider.addAlgorithm("Alg.Alias.KeyPairGenerator.GOST-3410-94", "GOST3410");
            provider.addAlgorithm("KeyFactory.GOST3410", "org.spongycastle.jcajce.provider.asymmetric.gost.KeyFactorySpi");
            provider.addAlgorithm("Alg.Alias.KeyFactory.GOST-3410", "GOST3410");
            provider.addAlgorithm("Alg.Alias.KeyFactory.GOST-3410-94", "GOST3410");
            provider.addAlgorithm("AlgorithmParameters.GOST3410", "org.spongycastle.jcajce.provider.asymmetric.gost.AlgorithmParametersSpi");
            provider.addAlgorithm("AlgorithmParameterGenerator.GOST3410", "org.spongycastle.jcajce.provider.asymmetric.gost.AlgorithmParameterGeneratorSpi");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = CryptoProObjectIdentifiers.l;
            c(provider, aSN1ObjectIdentifier, "GOST3410", new KeyFactorySpi());
            d(provider, aSN1ObjectIdentifier, "GOST3410");
            provider.addAlgorithm("Signature.GOST3410", "org.spongycastle.jcajce.provider.asymmetric.gost.SignatureSpi");
            provider.addAlgorithm("Alg.Alias.Signature.GOST-3410", "GOST3410");
            provider.addAlgorithm("Alg.Alias.Signature.GOST-3410-94", "GOST3410");
            provider.addAlgorithm("Alg.Alias.Signature.GOST3411withGOST3410", "GOST3410");
            provider.addAlgorithm("Alg.Alias.Signature.GOST3411WITHGOST3410", "GOST3410");
            provider.addAlgorithm("Alg.Alias.Signature.GOST3411WithGOST3410", "GOST3410");
            provider.addAlgorithm("Alg.Alias.Signature." + CryptoProObjectIdentifiers.n, "GOST3410");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.GOST-3410", "GOST3410");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.GOST-3410", "GOST3410");
        }
    }
}
