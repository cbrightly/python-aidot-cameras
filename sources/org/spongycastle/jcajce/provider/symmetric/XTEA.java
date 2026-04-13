package org.spongycastle.jcajce.provider.symmetric;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.XTEAEngine;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class XTEA {
    private XTEA() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipher) new XTEAEngine());
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("XTEA", 128, new CipherKeyGenerator());
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "XTEA IV";
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = XTEA.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$ECB");
            provider.addAlgorithm("Cipher.XTEA", sb.toString());
            provider.addAlgorithm("KeyGenerator.XTEA", str + "$KeyGen");
            provider.addAlgorithm("AlgorithmParameters.XTEA", str + "$AlgParams");
        }
    }
}
