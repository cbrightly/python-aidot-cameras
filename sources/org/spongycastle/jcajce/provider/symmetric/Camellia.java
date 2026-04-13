package org.spongycastle.jcajce.provider.symmetric;

import com.alibaba.fastjson.asm.Opcodes;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ntt.NTTObjectIdentifiers;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.CamelliaEngine;
import org.spongycastle.crypto.engines.CamelliaWrapEngine;
import org.spongycastle.crypto.engines.RFC3211WrapEngine;
import org.spongycastle.crypto.generators.Poly1305KeyGenerator;
import org.spongycastle.crypto.macs.GMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.modes.GCMBlockCipher;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;

public final class Camellia {
    private Camellia() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new CamelliaEngine();
                }
            });
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new CamelliaEngine()), 128);
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new CamelliaWrapEngine());
        }
    }

    public static class RFC3211Wrap extends BaseWrapCipher {
        public RFC3211Wrap() {
            super(new RFC3211WrapEngine(new CamelliaEngine()), 16);
        }
    }

    public static class GMAC extends BaseMac {
        public GMAC() {
            super(new GMac(new GCMBlockCipher(new CamelliaEngine())));
        }
    }

    public static class Poly1305 extends BaseMac {
        public Poly1305() {
            super(new org.spongycastle.crypto.macs.Poly1305(new CamelliaEngine()));
        }
    }

    public static class Poly1305KeyGen extends BaseKeyGenerator {
        public Poly1305KeyGen() {
            super("Poly1305-Camellia", 256, new Poly1305KeyGenerator());
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            this(256);
        }

        public KeyGen(int keySize) {
            super("Camellia", keySize, new CipherKeyGenerator());
        }
    }

    public static class KeyGen128 extends KeyGen {
        public KeyGen128() {
            super(128);
        }
    }

    public static class KeyGen192 extends KeyGen {
        public KeyGen192() {
            super(Opcodes.CHECKCAST);
        }
    }

    public static class KeyGen256 extends KeyGen {
        public KeyGen256() {
            super(256);
        }
    }

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for Camellia parameter generation.");
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] iv = new byte[16];
            if (this.b == null) {
                this.b = new SecureRandom();
            }
            this.b.nextBytes(iv);
            try {
                AlgorithmParameters params = a("Camellia");
                params.init(new IvParameterSpec(iv));
                return params;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Camellia IV";
        }
    }

    public static class Mappings extends SymmetricAlgorithmProvider {
        private static final String a = Camellia.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$AlgParams");
            provider.addAlgorithm("AlgorithmParameters.CAMELLIA", sb.toString());
            ASN1ObjectIdentifier aSN1ObjectIdentifier = NTTObjectIdentifiers.a;
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier, "CAMELLIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NTTObjectIdentifiers.b;
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier2, "CAMELLIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NTTObjectIdentifiers.c;
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier3, "CAMELLIA");
            provider.addAlgorithm("AlgorithmParameterGenerator.CAMELLIA", str + "$AlgParamGen");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier, "CAMELLIA");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier2, "CAMELLIA");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier3, "CAMELLIA");
            provider.addAlgorithm("Cipher.CAMELLIA", str + "$ECB");
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier, str + "$CBC");
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier2, str + "$CBC");
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier3, str + "$CBC");
            provider.addAlgorithm("Cipher.CAMELLIARFC3211WRAP", str + "$RFC3211Wrap");
            provider.addAlgorithm("Cipher.CAMELLIAWRAP", str + "$Wrap");
            ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NTTObjectIdentifiers.d;
            provider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier4, "CAMELLIAWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier5 = NTTObjectIdentifiers.e;
            provider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier5, "CAMELLIAWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier6 = NTTObjectIdentifiers.f;
            provider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier6, "CAMELLIAWRAP");
            provider.addAlgorithm("KeyGenerator.CAMELLIA", str + "$KeyGen");
            provider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier4, str + "$KeyGen128");
            provider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier5, str + "$KeyGen192");
            provider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier6, str + "$KeyGen256");
            provider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier, str + "$KeyGen128");
            provider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier2, str + "$KeyGen192");
            provider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier3, str + "$KeyGen256");
            c(provider, "CAMELLIA", str + "$GMAC", str + "$KeyGen");
            d(provider, "CAMELLIA", str + "$Poly1305", str + "$Poly1305KeyGen");
        }
    }
}
