package org.spongycastle.jcajce.provider.symmetric;

import com.alibaba.fastjson.asm.Opcodes;
import org.spongycastle.asn1.gnu.GNUObjectIdentifiers;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.SerpentEngine;
import org.spongycastle.crypto.engines.TnepresEngine;
import org.spongycastle.crypto.engines.TwofishEngine;
import org.spongycastle.crypto.generators.Poly1305KeyGenerator;
import org.spongycastle.crypto.macs.GMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.modes.CFBBlockCipher;
import org.spongycastle.crypto.modes.GCMBlockCipher;
import org.spongycastle.crypto.modes.OFBBlockCipher;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;

public final class Serpent {
    private Serpent() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new SerpentEngine();
                }
            });
        }
    }

    public static class TECB extends BaseBlockCipher {
        public TECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new TnepresEngine();
                }
            });
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new SerpentEngine()), 128);
        }
    }

    public static class CFB extends BaseBlockCipher {
        public CFB() {
            super(new BufferedBlockCipher(new CFBBlockCipher(new SerpentEngine(), 128)), 128);
        }
    }

    public static class OFB extends BaseBlockCipher {
        public OFB() {
            super(new BufferedBlockCipher(new OFBBlockCipher(new SerpentEngine(), 128)), 128);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("Serpent", Opcodes.CHECKCAST, new CipherKeyGenerator());
        }
    }

    public static class TKeyGen extends BaseKeyGenerator {
        public TKeyGen() {
            super("Tnepres", Opcodes.CHECKCAST, new CipherKeyGenerator());
        }
    }

    public static class SerpentGMAC extends BaseMac {
        public SerpentGMAC() {
            super(new GMac(new GCMBlockCipher(new SerpentEngine())));
        }
    }

    public static class TSerpentGMAC extends BaseMac {
        public TSerpentGMAC() {
            super(new GMac(new GCMBlockCipher(new TnepresEngine())));
        }
    }

    public static class Poly1305 extends BaseMac {
        public Poly1305() {
            super(new org.spongycastle.crypto.macs.Poly1305(new TwofishEngine()));
        }
    }

    public static class Poly1305KeyGen extends BaseKeyGenerator {
        public Poly1305KeyGen() {
            super("Poly1305-Serpent", 256, new Poly1305KeyGenerator());
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Serpent IV";
        }
    }

    public static class TAlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Tnepres IV";
        }
    }

    public static class Mappings extends SymmetricAlgorithmProvider {
        private static final String a = Serpent.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$ECB");
            provider.addAlgorithm("Cipher.Serpent", sb.toString());
            provider.addAlgorithm("KeyGenerator.Serpent", str + "$KeyGen");
            provider.addAlgorithm("AlgorithmParameters.Serpent", str + "$AlgParams");
            provider.addAlgorithm("Cipher.Tnepres", str + "$TECB");
            provider.addAlgorithm("KeyGenerator.Tnepres", str + "$TKeyGen");
            provider.addAlgorithm("AlgorithmParameters.Tnepres", str + "$TAlgParams");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.j, str + "$ECB");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.n, str + "$ECB");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.r, str + "$ECB");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.k, str + "$CBC");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.o, str + "$CBC");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.s, str + "$CBC");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.m, str + "$CFB");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.q, str + "$CFB");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.u, str + "$CFB");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.l, str + "$OFB");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.p, str + "$OFB");
            provider.addAlgorithm("Cipher", GNUObjectIdentifiers.t, str + "$OFB");
            c(provider, "SERPENT", str + "$SerpentGMAC", str + "$KeyGen");
            c(provider, "TNEPRES", str + "$TSerpentGMAC", str + "$TKeyGen");
            d(provider, "SERPENT", str + "$Poly1305", str + "$Poly1305KeyGen");
        }
    }
}
