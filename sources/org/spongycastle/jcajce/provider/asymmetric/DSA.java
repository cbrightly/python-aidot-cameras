package org.spongycastle.jcajce.provider.asymmetric;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.jcajce.provider.asymmetric.dsa.DSAUtil;
import org.spongycastle.jcajce.provider.asymmetric.dsa.KeyFactorySpi;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public class DSA {

    public static class Mappings extends AsymmetricAlgorithmProvider {
        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("AlgorithmParameters.DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.AlgorithmParametersSpi");
            provider.addAlgorithm("AlgorithmParameterGenerator.DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.AlgorithmParameterGeneratorSpi");
            provider.addAlgorithm("KeyPairGenerator.DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.KeyPairGeneratorSpi");
            provider.addAlgorithm("KeyFactory.DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.KeyFactorySpi");
            provider.addAlgorithm("Signature.DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$stdDSA");
            provider.addAlgorithm("Signature.NONEWITHDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$noneDSA");
            provider.addAlgorithm("Alg.Alias.Signature.RAWDSA", "NONEWITHDSA");
            provider.addAlgorithm("Signature.DETDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA");
            provider.addAlgorithm("Signature.SHA1WITHDETDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA");
            provider.addAlgorithm("Signature.SHA224WITHDETDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA224");
            provider.addAlgorithm("Signature.SHA256WITHDETDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA256");
            provider.addAlgorithm("Signature.SHA384WITHDETDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA384");
            provider.addAlgorithm("Signature.SHA512WITHDETDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA512");
            provider.addAlgorithm("Signature.DDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA");
            provider.addAlgorithm("Signature.SHA1WITHDDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA");
            provider.addAlgorithm("Signature.SHA224WITHDDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA224");
            provider.addAlgorithm("Signature.SHA256WITHDDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA256");
            provider.addAlgorithm("Signature.SHA384WITHDDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA384");
            provider.addAlgorithm("Signature.SHA512WITHDDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSA512");
            provider.addAlgorithm("Signature.SHA3-224WITHDDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSASha3_224");
            provider.addAlgorithm("Signature.SHA3-256WITHDDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSASha3_256");
            provider.addAlgorithm("Signature.SHA3-384WITHDDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSASha3_384");
            provider.addAlgorithm("Signature.SHA3-512WITHDDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$detDSASha3_512");
            b(provider, "SHA224", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa224", NISTObjectIdentifiers.T);
            b(provider, "SHA256", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa256", NISTObjectIdentifiers.U);
            ConfigurableProvider configurableProvider = provider;
            b(configurableProvider, "SHA384", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa384", NISTObjectIdentifiers.V);
            ConfigurableProvider configurableProvider2 = provider;
            b(configurableProvider2, "SHA512", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa512", NISTObjectIdentifiers.W);
            b(configurableProvider, "SHA3-224", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsaSha3_224", NISTObjectIdentifiers.X);
            b(configurableProvider2, "SHA3-256", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsaSha3_256", NISTObjectIdentifiers.Y);
            b(configurableProvider, "SHA3-384", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsaSha3_384", NISTObjectIdentifiers.Z);
            b(configurableProvider2, "SHA3-512", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsaSha3_512", NISTObjectIdentifiers.a0);
            provider.addAlgorithm("Alg.Alias.Signature.SHA/DSA", "DSA");
            provider.addAlgorithm("Alg.Alias.Signature.SHA1withDSA", "DSA");
            provider.addAlgorithm("Alg.Alias.Signature.SHA1WITHDSA", "DSA");
            provider.addAlgorithm("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10040.4.1", "DSA");
            provider.addAlgorithm("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10040.4.3", "DSA");
            provider.addAlgorithm("Alg.Alias.Signature.DSAwithSHA1", "DSA");
            provider.addAlgorithm("Alg.Alias.Signature.DSAWITHSHA1", "DSA");
            provider.addAlgorithm("Alg.Alias.Signature.SHA1WithDSA", "DSA");
            provider.addAlgorithm("Alg.Alias.Signature.DSAWithSHA1", "DSA");
            provider.addAlgorithm("Alg.Alias.Signature.1.2.840.10040.4.3", "DSA");
            AsymmetricKeyInfoConverter keyFact = new KeyFactorySpi();
            int i = 0;
            while (true) {
                ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = DSAUtil.a;
                if (i != aSN1ObjectIdentifierArr.length) {
                    provider.addAlgorithm("Alg.Alias.Signature." + aSN1ObjectIdentifierArr[i], "DSA");
                    c(provider, aSN1ObjectIdentifierArr[i], "DSA", keyFact);
                    d(provider, aSN1ObjectIdentifierArr[i], "DSA");
                    i++;
                } else {
                    return;
                }
            }
        }
    }
}
