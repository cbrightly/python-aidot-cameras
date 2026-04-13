package org.spongycastle.pqc.jcajce.provider;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.jcajce.provider.sphincs.Sphincs256KeyFactorySpi;

public class SPHINCS {

    public static class Mappings extends AsymmetricAlgorithmProvider {
        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("KeyFactory.SPHINCS256", "org.spongycastle.pqc.jcajce.provider.sphincs.Sphincs256KeyFactorySpi");
            provider.addAlgorithm("KeyPairGenerator.SPHINCS256", "org.spongycastle.pqc.jcajce.provider.sphincs.Sphincs256KeyPairGeneratorSpi");
            b(provider, "SHA512", "SPHINCS256", "org.spongycastle.pqc.jcajce.provider.sphincs.SignatureSpi$withSha512", PQCObjectIdentifiers.t);
            b(provider, "SHA3-512", "SPHINCS256", "org.spongycastle.pqc.jcajce.provider.sphincs.SignatureSpi$withSha3_512", PQCObjectIdentifiers.u);
            AsymmetricKeyInfoConverter keyFact = new Sphincs256KeyFactorySpi();
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PQCObjectIdentifiers.r;
            c(provider, aSN1ObjectIdentifier, "SPHINCS256", keyFact);
            e(provider, aSN1ObjectIdentifier, "SPHINCS256");
        }
    }
}
