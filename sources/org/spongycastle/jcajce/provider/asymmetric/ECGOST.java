package org.spongycastle.jcajce.provider.asymmetric;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.spongycastle.jcajce.provider.asymmetric.ecgost.KeyFactorySpi;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class ECGOST {

    public static class Mappings extends AsymmetricAlgorithmProvider {
        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("KeyFactory.ECGOST3410", "org.spongycastle.jcajce.provider.asymmetric.ecgost.KeyFactorySpi");
            provider.addAlgorithm("Alg.Alias.KeyFactory.GOST-3410-2001", "ECGOST3410");
            provider.addAlgorithm("Alg.Alias.KeyFactory.ECGOST-3410", "ECGOST3410");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = CryptoProObjectIdentifiers.m;
            c(provider, aSN1ObjectIdentifier, "ECGOST3410", new KeyFactorySpi());
            c(provider, CryptoProObjectIdentifiers.F, "ECGOST3410", new KeyFactorySpi());
            e(provider, aSN1ObjectIdentifier, "ECGOST3410");
            provider.addAlgorithm("KeyPairGenerator.ECGOST3410", "org.spongycastle.jcajce.provider.asymmetric.ecgost.KeyPairGeneratorSpi");
            provider.addAlgorithm("Alg.Alias.KeyPairGenerator.ECGOST-3410", "ECGOST3410");
            provider.addAlgorithm("Alg.Alias.KeyPairGenerator.GOST-3410-2001", "ECGOST3410");
            provider.addAlgorithm("Signature.ECGOST3410", "org.spongycastle.jcajce.provider.asymmetric.ecgost.SignatureSpi");
            provider.addAlgorithm("Alg.Alias.Signature.ECGOST-3410", "ECGOST3410");
            provider.addAlgorithm("Alg.Alias.Signature.GOST-3410-2001", "ECGOST3410");
            provider.addAlgorithm("KeyAgreement.ECGOST3410", "org.spongycastle.jcajce.provider.asymmetric.ecgost.KeyAgreementSpi$ECVKO");
            provider.addAlgorithm("Alg.Alias.KeyAgreement." + aSN1ObjectIdentifier, "ECGOST3410");
            provider.addAlgorithm("Alg.Alias.KeyAgreement.GOST-3410-2001", "ECGOST3410");
            provider.addAlgorithm("Alg.Alias.KeyAgreement." + CryptoProObjectIdentifiers.E, "ECGOST3410");
            provider.addAlgorithm("AlgorithmParameters.ECGOST3410", "org.spongycastle.jcajce.provider.asymmetric.ecgost.AlgorithmParametersSpi");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.GOST-3410-2001", "ECGOST3410");
            b(provider, "GOST3411", "ECGOST3410", "org.spongycastle.jcajce.provider.asymmetric.ecgost.SignatureSpi", CryptoProObjectIdentifiers.o);
            provider.addAlgorithm("KeyFactory.ECGOST3410-2012", "org.spongycastle.jcajce.provider.asymmetric.ecgost12.KeyFactorySpi");
            provider.addAlgorithm("Alg.Alias.KeyFactory.GOST-3410-2012", "ECGOST3410-2012");
            provider.addAlgorithm("Alg.Alias.KeyFactory.ECGOST-3410-2012", "ECGOST3410-2012");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = RosstandartObjectIdentifiers.g;
            c(provider, aSN1ObjectIdentifier2, "ECGOST3410-2012", new org.spongycastle.jcajce.provider.asymmetric.ecgost12.KeyFactorySpi());
            ASN1ObjectIdentifier aSN1ObjectIdentifier3 = RosstandartObjectIdentifiers.l;
            c(provider, aSN1ObjectIdentifier3, "ECGOST3410-2012", new org.spongycastle.jcajce.provider.asymmetric.ecgost12.KeyFactorySpi());
            e(provider, aSN1ObjectIdentifier2, "ECGOST3410-2012");
            ASN1ObjectIdentifier aSN1ObjectIdentifier4 = RosstandartObjectIdentifiers.h;
            c(provider, aSN1ObjectIdentifier4, "ECGOST3410-2012", new org.spongycastle.jcajce.provider.asymmetric.ecgost12.KeyFactorySpi());
            ASN1ObjectIdentifier aSN1ObjectIdentifier5 = RosstandartObjectIdentifiers.m;
            c(provider, aSN1ObjectIdentifier5, "ECGOST3410-2012", new org.spongycastle.jcajce.provider.asymmetric.ecgost12.KeyFactorySpi());
            e(provider, aSN1ObjectIdentifier4, "ECGOST3410-2012");
            provider.addAlgorithm("KeyPairGenerator.ECGOST3410-2012", "org.spongycastle.jcajce.provider.asymmetric.ecgost12.KeyPairGeneratorSpi");
            provider.addAlgorithm("Alg.Alias.KeyPairGenerator.ECGOST3410-2012", "ECGOST3410-2012");
            provider.addAlgorithm("Alg.Alias.KeyPairGenerator.GOST-3410-2012", "ECGOST3410-2012");
            provider.addAlgorithm("Signature.ECGOST3410-2012-256", "org.spongycastle.jcajce.provider.asymmetric.ecgost12.ECGOST2012SignatureSpi256");
            provider.addAlgorithm("Alg.Alias.Signature.ECGOST3410-2012-256", "ECGOST3410-2012-256");
            provider.addAlgorithm("Alg.Alias.Signature.GOST-3410-2012-256", "ECGOST3410-2012-256");
            b(provider, "GOST3411-2012-256", "ECGOST3410-2012-256", "org.spongycastle.jcajce.provider.asymmetric.ecgost12.ECGOST2012SignatureSpi256", RosstandartObjectIdentifiers.i);
            provider.addAlgorithm("Signature.ECGOST3410-2012-512", "org.spongycastle.jcajce.provider.asymmetric.ecgost12.ECGOST2012SignatureSpi512");
            provider.addAlgorithm("Alg.Alias.Signature.ECGOST3410-2012-512", "ECGOST3410-2012-512");
            provider.addAlgorithm("Alg.Alias.Signature.GOST-3410-2012-512", "ECGOST3410-2012-512");
            b(provider, "GOST3411-2012-512", "ECGOST3410-2012-512", "org.spongycastle.jcajce.provider.asymmetric.ecgost12.ECGOST2012SignatureSpi512", RosstandartObjectIdentifiers.j);
            provider.addAlgorithm("KeyAgreement.ECGOST3410-2012-256", "org.spongycastle.jcajce.provider.asymmetric.ecgost12.KeyAgreementSpi$ECVKO256");
            provider.addAlgorithm("KeyAgreement.ECGOST3410-2012-512", "org.spongycastle.jcajce.provider.asymmetric.ecgost12.KeyAgreementSpi$ECVKO512");
            provider.addAlgorithm("Alg.Alias.KeyAgreement." + aSN1ObjectIdentifier3, "ECGOST3410-2012-256");
            provider.addAlgorithm("Alg.Alias.KeyAgreement." + aSN1ObjectIdentifier5, "ECGOST3410-2012-512");
            provider.addAlgorithm("Alg.Alias.KeyAgreement." + aSN1ObjectIdentifier2, "ECGOST3410-2012-256");
            provider.addAlgorithm("Alg.Alias.KeyAgreement." + aSN1ObjectIdentifier4, "ECGOST3410-2012-512");
        }
    }
}
