package org.spongycastle.jcajce.provider.symmetric;

import com.alibaba.fastjson.asm.Opcodes;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.RijndaelEngine;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class Rijndael {
    private Rijndael() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new RijndaelEngine();
                }
            });
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("Rijndael", Opcodes.CHECKCAST, new CipherKeyGenerator());
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Rijndael IV";
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = Rijndael.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$ECB");
            provider.addAlgorithm("Cipher.RIJNDAEL", sb.toString());
            provider.addAlgorithm("KeyGenerator.RIJNDAEL", str + "$KeyGen");
            provider.addAlgorithm("AlgorithmParameters.RIJNDAEL", str + "$AlgParams");
        }
    }
}
