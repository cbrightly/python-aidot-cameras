package org.spongycastle.pqc.jcajce.provider;

import org.spongycastle.asn1.bc.BCObjectIdentifiers;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.jcajce.provider.xmss.XMSSKeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.xmss.XMSSMTKeyFactorySpi;

public class XMSS {

    public static class Mappings extends AsymmetricAlgorithmProvider {
        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("KeyFactory.XMSS", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSKeyFactorySpi");
            provider.addAlgorithm("KeyPairGenerator.XMSS", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSKeyPairGeneratorSpi");
            b(provider, "SHA256", "XMSS", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSSignatureSpi$withSha256", BCObjectIdentifiers.x);
            b(provider, "SHAKE128", "XMSS", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSSignatureSpi$withShake128", BCObjectIdentifiers.z);
            b(provider, "SHA512", "XMSS", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSSignatureSpi$withSha512", BCObjectIdentifiers.y);
            b(provider, "SHAKE256", "XMSS", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSSignatureSpi$withShake256", BCObjectIdentifiers.A);
            provider.addAlgorithm("KeyFactory.XMSSMT", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSMTKeyFactorySpi");
            provider.addAlgorithm("KeyPairGenerator.XMSSMT", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSMTKeyPairGeneratorSpi");
            b(provider, "SHA256", "XMSSMT", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSMTSignatureSpi$withSha256", BCObjectIdentifiers.C);
            b(provider, "SHAKE128", "XMSSMT", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSMTSignatureSpi$withShake128", BCObjectIdentifiers.E);
            b(provider, "SHA512", "XMSSMT", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSMTSignatureSpi$withSha512", BCObjectIdentifiers.D);
            b(provider, "SHAKE256", "XMSSMT", "org.spongycastle.pqc.jcajce.provider.xmss.XMSSMTSignatureSpi$withShake256", BCObjectIdentifiers.F);
            c(provider, PQCObjectIdentifiers.w, "XMSS", new XMSSKeyFactorySpi());
            c(provider, PQCObjectIdentifiers.B, "XMSSMT", new XMSSMTKeyFactorySpi());
        }
    }
}
