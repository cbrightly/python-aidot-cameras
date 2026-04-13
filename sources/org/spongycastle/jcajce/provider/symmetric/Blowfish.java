package org.spongycastle.jcajce.provider.symmetric;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.misc.MiscObjectIdentifiers;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.BlowfishEngine;
import org.spongycastle.crypto.macs.CMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class Blowfish {
    private Blowfish() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipher) new BlowfishEngine());
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new BlowfishEngine()), 64);
        }
    }

    public static class CMAC extends BaseMac {
        public CMAC() {
            super(new CMac(new BlowfishEngine()));
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("Blowfish", 128, new CipherKeyGenerator());
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Blowfish IV";
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = Blowfish.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$CMAC");
            provider.addAlgorithm("Mac.BLOWFISHCMAC", sb.toString());
            provider.addAlgorithm("Cipher.BLOWFISH", str + "$ECB");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = MiscObjectIdentifiers.z;
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier, str + "$CBC");
            provider.addAlgorithm("KeyGenerator.BLOWFISH", str + "$KeyGen");
            provider.addAlgorithm("Alg.Alias.KeyGenerator", aSN1ObjectIdentifier, "BLOWFISH");
            provider.addAlgorithm("AlgorithmParameters.BLOWFISH", str + "$AlgParams");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier, "BLOWFISH");
        }
    }
}
