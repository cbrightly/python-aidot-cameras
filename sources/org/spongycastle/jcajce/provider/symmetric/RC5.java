package org.spongycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.RC532Engine;
import org.spongycastle.crypto.engines.RC564Engine;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.macs.CFBBlockCipherMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class RC5 {
    private RC5() {
    }

    public static class ECB32 extends BaseBlockCipher {
        public ECB32() {
            super((BlockCipher) new RC532Engine());
        }
    }

    public static class ECB64 extends BaseBlockCipher {
        public ECB64() {
            super((BlockCipher) new RC564Engine());
        }
    }

    public static class CBC32 extends BaseBlockCipher {
        public CBC32() {
            super((BlockCipher) new CBCBlockCipher(new RC532Engine()), 64);
        }
    }

    public static class KeyGen32 extends BaseKeyGenerator {
        public KeyGen32() {
            super("RC5", 128, new CipherKeyGenerator());
        }
    }

    public static class KeyGen64 extends BaseKeyGenerator {
        public KeyGen64() {
            super("RC5-64", 256, new CipherKeyGenerator());
        }
    }

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for RC5 parameter generation.");
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] iv = new byte[8];
            if (this.b == null) {
                this.b = new SecureRandom();
            }
            this.b.nextBytes(iv);
            try {
                AlgorithmParameters params = a("RC5");
                params.init(new IvParameterSpec(iv));
                return params;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static class Mac32 extends BaseMac {
        public Mac32() {
            super(new CBCBlockCipherMac(new RC532Engine()));
        }
    }

    public static class CFB8Mac32 extends BaseMac {
        public CFB8Mac32() {
            super(new CFBBlockCipherMac(new RC532Engine()));
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "RC5 IV";
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = RC5.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$ECB32");
            provider.addAlgorithm("Cipher.RC5", sb.toString());
            provider.addAlgorithm("Alg.Alias.Cipher.RC5-32", "RC5");
            provider.addAlgorithm("Cipher.RC5-64", str + "$ECB64");
            provider.addAlgorithm("KeyGenerator.RC5", str + "$KeyGen32");
            provider.addAlgorithm("Alg.Alias.KeyGenerator.RC5-32", "RC5");
            provider.addAlgorithm("KeyGenerator.RC5-64", str + "$KeyGen64");
            provider.addAlgorithm("AlgorithmParameters.RC5", str + "$AlgParams");
            provider.addAlgorithm("AlgorithmParameters.RC5-64", str + "$AlgParams");
            provider.addAlgorithm("Mac.RC5MAC", str + "$Mac32");
            provider.addAlgorithm("Alg.Alias.Mac.RC5", "RC5MAC");
            provider.addAlgorithm("Mac.RC5MAC/CFB8", str + "$CFB8Mac32");
            provider.addAlgorithm("Alg.Alias.Mac.RC5/CFB8", "RC5MAC/CFB8");
        }
    }
}
