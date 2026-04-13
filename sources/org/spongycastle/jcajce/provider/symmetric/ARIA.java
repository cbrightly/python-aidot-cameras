package org.spongycastle.jcajce.provider.symmetric;

import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.gms.stats.CodePackage;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cms.CCMParameters;
import org.spongycastle.asn1.cms.GCMParameters;
import org.spongycastle.asn1.nsri.NSRIObjectIdentifiers;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.ARIAEngine;
import org.spongycastle.crypto.engines.ARIAWrapEngine;
import org.spongycastle.crypto.engines.ARIAWrapPadEngine;
import org.spongycastle.crypto.engines.RFC3211WrapEngine;
import org.spongycastle.crypto.generators.Poly1305KeyGenerator;
import org.spongycastle.crypto.macs.GMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.modes.CFBBlockCipher;
import org.spongycastle.crypto.modes.GCMBlockCipher;
import org.spongycastle.crypto.modes.OFBBlockCipher;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.spongycastle.jcajce.spec.AEADParameterSpec;

public final class ARIA {
    private ARIA() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new ARIAEngine();
                }
            });
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new ARIAEngine()), 128);
        }
    }

    public static class CFB extends BaseBlockCipher {
        public CFB() {
            super(new BufferedBlockCipher(new CFBBlockCipher(new ARIAEngine(), 128)), 128);
        }
    }

    public static class OFB extends BaseBlockCipher {
        public OFB() {
            super(new BufferedBlockCipher(new OFBBlockCipher(new ARIAEngine(), 128)), 128);
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new ARIAWrapEngine());
        }
    }

    public static class WrapPad extends BaseWrapCipher {
        public WrapPad() {
            super(new ARIAWrapPadEngine());
        }
    }

    public static class RFC3211Wrap extends BaseWrapCipher {
        public RFC3211Wrap() {
            super(new RFC3211WrapEngine(new ARIAEngine()), 16);
        }
    }

    public static class GMAC extends BaseMac {
        public GMAC() {
            super(new GMac(new GCMBlockCipher(new ARIAEngine())));
        }
    }

    public static class Poly1305 extends BaseMac {
        public Poly1305() {
            super(new org.spongycastle.crypto.macs.Poly1305(new ARIAEngine()));
        }
    }

    public static class Poly1305KeyGen extends BaseKeyGenerator {
        public Poly1305KeyGen() {
            super("Poly1305-ARIA", 256, new Poly1305KeyGenerator());
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            this(256);
        }

        public KeyGen(int keySize) {
            super("ARIA", keySize, new CipherKeyGenerator());
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
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for ARIA parameter generation.");
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] iv = new byte[16];
            if (this.b == null) {
                this.b = new SecureRandom();
            }
            this.b.nextBytes(iv);
            try {
                AlgorithmParameters params = a("ARIA");
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
            return "ARIA IV";
        }
    }

    public static class AlgParamsGCM extends BaseAlgorithmParameters {
        private GCMParameters a;

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (GcmSpecUtil.e(paramSpec)) {
                this.a = GcmSpecUtil.a(paramSpec);
            } else if (paramSpec instanceof AEADParameterSpec) {
                this.a = new GCMParameters(((AEADParameterSpec) paramSpec).c(), ((AEADParameterSpec) paramSpec).b() / 8);
            } else {
                throw new InvalidParameterSpecException("AlgorithmParameterSpec class not recognized: " + paramSpec.getClass().getName());
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params) {
            this.a = GCMParameters.f(params);
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params, String format) {
            if (isASN1FormatString(format)) {
                this.a = GCMParameters.f(params);
                return;
            }
            throw new IOException("unknown format specified");
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            return this.a.getEncoded();
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String format) {
            if (isASN1FormatString(format)) {
                return this.a.getEncoded();
            }
            throw new IOException("unknown format specified");
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return CodePackage.GCM;
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec) {
            if (paramSpec == AlgorithmParameterSpec.class || GcmSpecUtil.d(paramSpec)) {
                if (GcmSpecUtil.c()) {
                    return GcmSpecUtil.b(this.a.toASN1Primitive());
                }
                return new AEADParameterSpec(this.a.g(), this.a.e() * 8);
            } else if (paramSpec == AEADParameterSpec.class) {
                return new AEADParameterSpec(this.a.g(), this.a.e() * 8);
            } else {
                if (paramSpec == IvParameterSpec.class) {
                    return new IvParameterSpec(this.a.g());
                }
                throw new InvalidParameterSpecException("AlgorithmParameterSpec not recognized: " + paramSpec.getName());
            }
        }
    }

    public static class AlgParamsCCM extends BaseAlgorithmParameters {
        private CCMParameters a;

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (GcmSpecUtil.e(paramSpec)) {
                this.a = CCMParameters.f(GcmSpecUtil.a(paramSpec));
            } else if (paramSpec instanceof AEADParameterSpec) {
                this.a = new CCMParameters(((AEADParameterSpec) paramSpec).c(), ((AEADParameterSpec) paramSpec).b() / 8);
            } else {
                throw new InvalidParameterSpecException("AlgorithmParameterSpec class not recognized: " + paramSpec.getClass().getName());
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params) {
            this.a = CCMParameters.f(params);
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params, String format) {
            if (isASN1FormatString(format)) {
                this.a = CCMParameters.f(params);
                return;
            }
            throw new IOException("unknown format specified");
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            return this.a.getEncoded();
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String format) {
            if (isASN1FormatString(format)) {
                return this.a.getEncoded();
            }
            throw new IOException("unknown format specified");
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "CCM";
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec) {
            if (paramSpec == AlgorithmParameterSpec.class || GcmSpecUtil.d(paramSpec)) {
                if (GcmSpecUtil.c()) {
                    return GcmSpecUtil.b(this.a.toASN1Primitive());
                }
                return new AEADParameterSpec(this.a.g(), this.a.e() * 8);
            } else if (paramSpec == AEADParameterSpec.class) {
                return new AEADParameterSpec(this.a.g(), this.a.e() * 8);
            } else {
                if (paramSpec == IvParameterSpec.class) {
                    return new IvParameterSpec(this.a.g());
                }
                throw new InvalidParameterSpecException("AlgorithmParameterSpec not recognized: " + paramSpec.getName());
            }
        }
    }

    public static class Mappings extends SymmetricAlgorithmProvider {
        private static final String a = ARIA.class.getName();

        public void a(ConfigurableProvider provider) {
            ConfigurableProvider configurableProvider = provider;
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.ARIA", sb.toString());
            ASN1ObjectIdentifier aSN1ObjectIdentifier = NSRIObjectIdentifiers.h;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NSRIObjectIdentifiers.m;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier2, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NSRIObjectIdentifiers.r;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier3, "ARIA");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.ARIA", str + "$AlgParamGen");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier, "ARIA");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier2, "ARIA");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier3, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NSRIObjectIdentifiers.j;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier4, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier5 = NSRIObjectIdentifiers.o;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier5, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier6 = NSRIObjectIdentifiers.t;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier6, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier7 = NSRIObjectIdentifiers.i;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier7, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier8 = NSRIObjectIdentifiers.n;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier8, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier9 = NSRIObjectIdentifiers.s;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier9, "ARIA");
            configurableProvider.addAlgorithm("Cipher.ARIA", str + "$ECB");
            ASN1ObjectIdentifier aSN1ObjectIdentifier10 = NSRIObjectIdentifiers.g;
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier10, str + "$ECB");
            ASN1ObjectIdentifier aSN1ObjectIdentifier11 = NSRIObjectIdentifiers.l;
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier11, str + "$ECB");
            ASN1ObjectIdentifier aSN1ObjectIdentifier12 = NSRIObjectIdentifiers.q;
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier12, str + "$ECB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier, str + "$CBC");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier2, str + "$CBC");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier3, str + "$CBC");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier7, str + "$CFB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier8, str + "$CFB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier9, str + "$CFB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier4, str + "$OFB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier5, str + "$OFB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier6, str + "$OFB");
            configurableProvider.addAlgorithm("Cipher.ARIARFC3211WRAP", str + "$RFC3211Wrap");
            configurableProvider.addAlgorithm("Cipher.ARIAWRAP", str + "$Wrap");
            ASN1ObjectIdentifier aSN1ObjectIdentifier13 = NSRIObjectIdentifiers.H;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier13, "ARIAWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier14 = aSN1ObjectIdentifier6;
            ASN1ObjectIdentifier aSN1ObjectIdentifier15 = NSRIObjectIdentifiers.I;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier15, "ARIAWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier16 = aSN1ObjectIdentifier5;
            ASN1ObjectIdentifier aSN1ObjectIdentifier17 = NSRIObjectIdentifiers.J;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier17, "ARIAWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier18 = aSN1ObjectIdentifier4;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.ARIAKW", "ARIAWRAP");
            configurableProvider.addAlgorithm("Cipher.ARIAWRAPPAD", str + "$WrapPad");
            ASN1ObjectIdentifier aSN1ObjectIdentifier19 = NSRIObjectIdentifiers.K;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier19, "ARIAWRAPPAD");
            ASN1ObjectIdentifier aSN1ObjectIdentifier20 = aSN1ObjectIdentifier9;
            ASN1ObjectIdentifier aSN1ObjectIdentifier21 = NSRIObjectIdentifiers.L;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier21, "ARIAWRAPPAD");
            ASN1ObjectIdentifier aSN1ObjectIdentifier22 = aSN1ObjectIdentifier8;
            ASN1ObjectIdentifier aSN1ObjectIdentifier23 = NSRIObjectIdentifiers.M;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier23, "ARIAWRAPPAD");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.ARIAKWP", "ARIAWRAPPAD");
            configurableProvider.addAlgorithm("KeyGenerator.ARIA", str + "$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier13, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier15, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier17, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier19, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier21, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier23, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier10, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier11, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier12, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier2, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier3, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier7, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier22, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier20, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier18, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier16, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier14, str + "$KeyGen256");
            ASN1ObjectIdentifier aSN1ObjectIdentifier24 = NSRIObjectIdentifiers.E;
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier24, str + "$KeyGen128");
            ASN1ObjectIdentifier aSN1ObjectIdentifier25 = NSRIObjectIdentifiers.F;
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier25, str + "$KeyGen192");
            ASN1ObjectIdentifier aSN1ObjectIdentifier26 = NSRIObjectIdentifiers.G;
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier26, str + "$KeyGen256");
            ASN1ObjectIdentifier aSN1ObjectIdentifier27 = NSRIObjectIdentifiers.B;
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier27, str + "$KeyGen128");
            ASN1ObjectIdentifier aSN1ObjectIdentifier28 = NSRIObjectIdentifiers.C;
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier28, str + "$KeyGen192");
            ASN1ObjectIdentifier aSN1ObjectIdentifier29 = NSRIObjectIdentifiers.D;
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier29, str + "$KeyGen256");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.ARIACCM", str + "$AlgParamGenCCM");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier24, "CCM");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier25, "CCM");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier26, "CCM");
            String str2 = "Alg.Alias.Cipher";
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier24, "CCM");
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier25, "CCM");
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier26, "CCM");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.ARIAGCM", str + "$AlgParamGenGCM");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier27, CodePackage.GCM);
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier28, CodePackage.GCM);
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier29, CodePackage.GCM);
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier27, CodePackage.GCM);
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier28, CodePackage.GCM);
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier29, CodePackage.GCM);
            String str3 = "ARIA";
            c(configurableProvider, str3, str + "$GMAC", str + "$KeyGen");
            d(configurableProvider, str3, str + "$Poly1305", str + "$Poly1305KeyGen");
        }
    }
}
