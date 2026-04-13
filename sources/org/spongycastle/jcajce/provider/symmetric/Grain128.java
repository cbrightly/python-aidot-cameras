package org.spongycastle.jcajce.provider.symmetric;

import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.Grain128Engine;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class Grain128 {
    private Grain128() {
    }

    public static class Base extends BaseStreamCipher {
        public Base() {
            super(new Grain128Engine(), 12);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("Grain128", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = Grain128.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Base");
            provider.addAlgorithm("Cipher.Grain128", sb.toString());
            provider.addAlgorithm("KeyGenerator.Grain128", str + "$KeyGen");
        }
    }
}
