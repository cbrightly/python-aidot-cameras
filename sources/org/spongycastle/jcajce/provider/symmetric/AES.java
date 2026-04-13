package org.spongycastle.jcajce.provider.symmetric;

import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.gms.stats.CodePackage;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.IvParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.bc.BCObjectIdentifiers;
import org.spongycastle.asn1.cms.CCMParameters;
import org.spongycastle.asn1.cms.GCMParameters;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.engines.AESEngine;
import org.spongycastle.crypto.engines.AESWrapEngine;
import org.spongycastle.crypto.engines.AESWrapPadEngine;
import org.spongycastle.crypto.engines.RFC3211WrapEngine;
import org.spongycastle.crypto.engines.RFC5649WrapEngine;
import org.spongycastle.crypto.generators.Poly1305KeyGenerator;
import org.spongycastle.crypto.macs.CMac;
import org.spongycastle.crypto.macs.GMac;
import org.spongycastle.crypto.modes.AEADBlockCipher;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.modes.CCMBlockCipher;
import org.spongycastle.crypto.modes.CFBBlockCipher;
import org.spongycastle.crypto.modes.GCMBlockCipher;
import org.spongycastle.crypto.modes.OFBBlockCipher;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.spongycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import org.spongycastle.jcajce.spec.AEADParameterSpec;

public final class AES {
    /* access modifiers changed from: private */
    public static final Map<String, String> a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put("SupportedKeyClasses", "javax.crypto.SecretKey");
        hashMap.put("SupportedKeyFormats", "RAW");
    }

    private AES() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new AESEngine();
                }
            });
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new AESEngine()), 128);
        }
    }

    public static class CFB extends BaseBlockCipher {
        public CFB() {
            super(new BufferedBlockCipher(new CFBBlockCipher(new AESEngine(), 128)), 128);
        }
    }

    public static class OFB extends BaseBlockCipher {
        public OFB() {
            super(new BufferedBlockCipher(new OFBBlockCipher(new AESEngine(), 128)), 128);
        }
    }

    public static class GCM extends BaseBlockCipher {
        public GCM() {
            super((AEADBlockCipher) new GCMBlockCipher(new AESEngine()));
        }
    }

    public static class CCM extends BaseBlockCipher {
        public CCM() {
            super(new CCMBlockCipher(new AESEngine()), false, 16);
        }
    }

    public static class AESCMAC extends BaseMac {
        public AESCMAC() {
            super(new CMac(new AESEngine()));
        }
    }

    public static class AESGMAC extends BaseMac {
        public AESGMAC() {
            super(new GMac(new GCMBlockCipher(new AESEngine())));
        }
    }

    public static class AESCCMMAC extends BaseMac {
        public AESCCMMAC() {
            super(new CCMMac());
        }

        public static class CCMMac implements Mac {
            private final CCMBlockCipher a;
            private int b;

            private CCMMac() {
                this.a = new CCMBlockCipher(new AESEngine());
                this.b = 8;
            }

            public void a(CipherParameters params) {
                this.a.a(true, params);
                this.b = this.a.h().length;
            }

            public String b() {
                return this.a.j() + "Mac";
            }

            public int e() {
                return this.b;
            }

            public void d(byte in) {
                this.a.m(in);
            }

            public void update(byte[] in, int inOff, int len) {
                this.a.i(in, inOff, len);
            }

            public int c(byte[] out, int outOff) {
                try {
                    return this.a.c(out, 0);
                } catch (InvalidCipherTextException e) {
                    throw new IllegalStateException("exception on doFinal(): " + e.toString());
                }
            }

            public void reset() {
                this.a.o();
            }
        }
    }

    public static class KeyFactory extends BaseSecretKeyFactory {
        public KeyFactory() {
            super("AES", (ASN1ObjectIdentifier) null);
        }
    }

    public static class Poly1305 extends BaseMac {
        public Poly1305() {
            super(new org.spongycastle.crypto.macs.Poly1305(new AESEngine()));
        }
    }

    public static class Poly1305KeyGen extends BaseKeyGenerator {
        public Poly1305KeyGen() {
            super("Poly1305-AES", 256, new Poly1305KeyGenerator());
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new AESWrapEngine());
        }
    }

    public static class WrapPad extends BaseWrapCipher {
        public WrapPad() {
            super(new AESWrapPadEngine());
        }
    }

    public static class RFC3211Wrap extends BaseWrapCipher {
        public RFC3211Wrap() {
            super(new RFC3211WrapEngine(new AESEngine()), 16);
        }
    }

    public static class RFC5649Wrap extends BaseWrapCipher {
        public RFC5649Wrap() {
            super(new RFC5649WrapEngine(new AESEngine()));
        }
    }

    public static class PBEWithAESCBC extends BaseBlockCipher {
        public PBEWithAESCBC() {
            super((BlockCipher) new CBCBlockCipher(new AESEngine()));
        }
    }

    public static class PBEWithSHA1AESCBC128 extends BaseBlockCipher {
        public PBEWithSHA1AESCBC128() {
            super(new CBCBlockCipher(new AESEngine()), 2, 1, 128, 16);
        }
    }

    public static class PBEWithSHA1AESCBC192 extends BaseBlockCipher {
        public PBEWithSHA1AESCBC192() {
            super(new CBCBlockCipher(new AESEngine()), 2, 1, Opcodes.CHECKCAST, 16);
        }
    }

    public static class PBEWithSHA1AESCBC256 extends BaseBlockCipher {
        public PBEWithSHA1AESCBC256() {
            super(new CBCBlockCipher(new AESEngine()), 2, 1, 256, 16);
        }
    }

    public static class PBEWithSHA256AESCBC128 extends BaseBlockCipher {
        public PBEWithSHA256AESCBC128() {
            super(new CBCBlockCipher(new AESEngine()), 2, 4, 128, 16);
        }
    }

    public static class PBEWithSHA256AESCBC192 extends BaseBlockCipher {
        public PBEWithSHA256AESCBC192() {
            super(new CBCBlockCipher(new AESEngine()), 2, 4, Opcodes.CHECKCAST, 16);
        }
    }

    public static class PBEWithSHA256AESCBC256 extends BaseBlockCipher {
        public PBEWithSHA256AESCBC256() {
            super(new CBCBlockCipher(new AESEngine()), 2, 4, 256, 16);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            this(Opcodes.CHECKCAST);
        }

        public KeyGen(int keySize) {
            super("AES", keySize, new CipherKeyGenerator());
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

    public static class PBEWithSHAAnd128BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHAAnd128BitAESBC() {
            super("PBEWithSHA1And128BitAES-CBC-BC", (ASN1ObjectIdentifier) null, true, 2, 1, 128, 128);
        }
    }

    public static class PBEWithSHAAnd192BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHAAnd192BitAESBC() {
            super("PBEWithSHA1And192BitAES-CBC-BC", (ASN1ObjectIdentifier) null, true, 2, 1, Opcodes.CHECKCAST, 128);
        }
    }

    public static class PBEWithSHAAnd256BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHAAnd256BitAESBC() {
            super("PBEWithSHA1And256BitAES-CBC-BC", (ASN1ObjectIdentifier) null, true, 2, 1, 256, 128);
        }
    }

    public static class PBEWithSHA256And128BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHA256And128BitAESBC() {
            super("PBEWithSHA256And128BitAES-CBC-BC", (ASN1ObjectIdentifier) null, true, 2, 4, 128, 128);
        }
    }

    public static class PBEWithSHA256And192BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHA256And192BitAESBC() {
            super("PBEWithSHA256And192BitAES-CBC-BC", (ASN1ObjectIdentifier) null, true, 2, 4, Opcodes.CHECKCAST, 128);
        }
    }

    public static class PBEWithSHA256And256BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHA256And256BitAESBC() {
            super("PBEWithSHA256And256BitAES-CBC-BC", (ASN1ObjectIdentifier) null, true, 2, 4, 256, 128);
        }
    }

    public static class PBEWithMD5And128BitAESCBCOpenSSL extends PBESecretKeyFactory {
        public PBEWithMD5And128BitAESCBCOpenSSL() {
            super("PBEWithMD5And128BitAES-CBC-OpenSSL", (ASN1ObjectIdentifier) null, true, 3, 0, 128, 128);
        }
    }

    public static class PBEWithMD5And192BitAESCBCOpenSSL extends PBESecretKeyFactory {
        public PBEWithMD5And192BitAESCBCOpenSSL() {
            super("PBEWithMD5And192BitAES-CBC-OpenSSL", (ASN1ObjectIdentifier) null, true, 3, 0, Opcodes.CHECKCAST, 128);
        }
    }

    public static class PBEWithMD5And256BitAESCBCOpenSSL extends PBESecretKeyFactory {
        public PBEWithMD5And256BitAESCBCOpenSSL() {
            super("PBEWithMD5And256BitAES-CBC-OpenSSL", (ASN1ObjectIdentifier) null, true, 3, 0, 256, 128);
        }
    }

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for AES parameter generation.");
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] iv = new byte[16];
            if (this.b == null) {
                this.b = new SecureRandom();
            }
            this.b.nextBytes(iv);
            try {
                AlgorithmParameters params = a("AES");
                params.init(new IvParameterSpec(iv));
                return params;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static class AlgParamGenCCM extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for AES parameter generation.");
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] iv = new byte[12];
            if (this.b == null) {
                this.b = new SecureRandom();
            }
            this.b.nextBytes(iv);
            try {
                AlgorithmParameters params = a("CCM");
                params.init(new CCMParameters(iv, 12).getEncoded());
                return params;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static class AlgParamGenGCM extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for AES parameter generation.");
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] nonce = new byte[12];
            if (this.b == null) {
                this.b = new SecureRandom();
            }
            this.b.nextBytes(nonce);
            try {
                AlgorithmParameters params = a(CodePackage.GCM);
                params.init(new GCMParameters(nonce, 16).getEncoded());
                return params;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "AES IV";
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
        private static final String a = AES.class.getName();

        public void a(ConfigurableProvider provider) {
            ConfigurableProvider configurableProvider = provider;
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.AES", sb.toString());
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.2", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.22", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.42", "AES");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = NISTObjectIdentifiers.u;
            sb2.append(aSN1ObjectIdentifier);
            configurableProvider.addAlgorithm(sb2.toString(), "AES");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NISTObjectIdentifiers.C;
            sb3.append(aSN1ObjectIdentifier2);
            configurableProvider.addAlgorithm(sb3.toString(), "AES");
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NISTObjectIdentifiers.K;
            sb4.append(aSN1ObjectIdentifier3);
            configurableProvider.addAlgorithm(sb4.toString(), "AES");
            configurableProvider.addAlgorithm("AlgorithmParameters.GCM", str + "$AlgParamsGCM");
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NISTObjectIdentifiers.y;
            sb5.append(aSN1ObjectIdentifier4);
            configurableProvider.addAlgorithm(sb5.toString(), CodePackage.GCM);
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier5 = NISTObjectIdentifiers.G;
            sb6.append(aSN1ObjectIdentifier5);
            configurableProvider.addAlgorithm(sb6.toString(), CodePackage.GCM);
            StringBuilder sb7 = new StringBuilder();
            sb7.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier6 = NISTObjectIdentifiers.O;
            sb7.append(aSN1ObjectIdentifier6);
            configurableProvider.addAlgorithm(sb7.toString(), CodePackage.GCM);
            configurableProvider.addAlgorithm("AlgorithmParameters.CCM", str + "$AlgParamsCCM");
            StringBuilder sb8 = new StringBuilder();
            sb8.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier7 = NISTObjectIdentifiers.z;
            sb8.append(aSN1ObjectIdentifier7);
            configurableProvider.addAlgorithm(sb8.toString(), "CCM");
            StringBuilder sb9 = new StringBuilder();
            sb9.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier8 = NISTObjectIdentifiers.H;
            sb9.append(aSN1ObjectIdentifier8);
            configurableProvider.addAlgorithm(sb9.toString(), "CCM");
            StringBuilder sb10 = new StringBuilder();
            sb10.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier9 = NISTObjectIdentifiers.P;
            sb10.append(aSN1ObjectIdentifier9);
            configurableProvider.addAlgorithm(sb10.toString(), "CCM");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.AES", str + "$AlgParamGen");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.2", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.22", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.42", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier, "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier2, "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier3, "AES");
            ASN1ObjectIdentifier aSN1ObjectIdentifier10 = aSN1ObjectIdentifier6;
            configurableProvider.addAttributes("Cipher.AES", AES.a);
            ASN1ObjectIdentifier aSN1ObjectIdentifier11 = aSN1ObjectIdentifier5;
            configurableProvider.addAlgorithm("Cipher.AES", str + "$ECB");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.2", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.22", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.42", "AES");
            ASN1ObjectIdentifier aSN1ObjectIdentifier12 = NISTObjectIdentifiers.t;
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier12, str + "$ECB");
            ASN1ObjectIdentifier aSN1ObjectIdentifier13 = NISTObjectIdentifiers.B;
            ASN1ObjectIdentifier aSN1ObjectIdentifier14 = aSN1ObjectIdentifier12;
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier13, str + "$ECB");
            ASN1ObjectIdentifier aSN1ObjectIdentifier15 = NISTObjectIdentifiers.J;
            ASN1ObjectIdentifier aSN1ObjectIdentifier16 = aSN1ObjectIdentifier13;
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier15, str + "$ECB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier, str + "$CBC");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier2, str + "$CBC");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier3, str + "$CBC");
            ASN1ObjectIdentifier aSN1ObjectIdentifier17 = NISTObjectIdentifiers.v;
            StringBuilder sb11 = new StringBuilder();
            sb11.append(str);
            ASN1ObjectIdentifier aSN1ObjectIdentifier18 = aSN1ObjectIdentifier3;
            sb11.append("$OFB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier17, sb11.toString());
            ASN1ObjectIdentifier aSN1ObjectIdentifier19 = NISTObjectIdentifiers.D;
            StringBuilder sb12 = new StringBuilder();
            sb12.append(str);
            ASN1ObjectIdentifier aSN1ObjectIdentifier20 = aSN1ObjectIdentifier15;
            sb12.append("$OFB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier19, sb12.toString());
            ASN1ObjectIdentifier aSN1ObjectIdentifier21 = NISTObjectIdentifiers.L;
            StringBuilder sb13 = new StringBuilder();
            sb13.append(str);
            ASN1ObjectIdentifier aSN1ObjectIdentifier22 = aSN1ObjectIdentifier19;
            sb13.append("$OFB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier21, sb13.toString());
            ASN1ObjectIdentifier aSN1ObjectIdentifier23 = NISTObjectIdentifiers.w;
            StringBuilder sb14 = new StringBuilder();
            sb14.append(str);
            ASN1ObjectIdentifier aSN1ObjectIdentifier24 = aSN1ObjectIdentifier21;
            sb14.append("$CFB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier23, sb14.toString());
            ASN1ObjectIdentifier aSN1ObjectIdentifier25 = NISTObjectIdentifiers.E;
            StringBuilder sb15 = new StringBuilder();
            sb15.append(str);
            ASN1ObjectIdentifier aSN1ObjectIdentifier26 = aSN1ObjectIdentifier2;
            sb15.append("$CFB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier25, sb15.toString());
            ASN1ObjectIdentifier aSN1ObjectIdentifier27 = NISTObjectIdentifiers.M;
            StringBuilder sb16 = new StringBuilder();
            sb16.append(str);
            ASN1ObjectIdentifier aSN1ObjectIdentifier28 = aSN1ObjectIdentifier25;
            sb16.append("$CFB");
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier27, sb16.toString());
            configurableProvider.addAttributes("Cipher.AESWRAP", AES.a);
            configurableProvider.addAlgorithm("Cipher.AESWRAP", str + "$Wrap");
            ASN1ObjectIdentifier aSN1ObjectIdentifier29 = NISTObjectIdentifiers.x;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier29, "AESWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier30 = aSN1ObjectIdentifier29;
            ASN1ObjectIdentifier aSN1ObjectIdentifier31 = NISTObjectIdentifiers.F;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier31, "AESWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier32 = aSN1ObjectIdentifier31;
            ASN1ObjectIdentifier aSN1ObjectIdentifier33 = NISTObjectIdentifiers.N;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier33, "AESWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier34 = aSN1ObjectIdentifier33;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.AESKW", "AESWRAP");
            configurableProvider.addAttributes("Cipher.AESWRAPPAD", AES.a);
            configurableProvider.addAlgorithm("Cipher.AESWRAPPAD", str + "$WrapPad");
            ASN1ObjectIdentifier aSN1ObjectIdentifier35 = NISTObjectIdentifiers.A;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier35, "AESWRAPPAD");
            ASN1ObjectIdentifier aSN1ObjectIdentifier36 = aSN1ObjectIdentifier35;
            ASN1ObjectIdentifier aSN1ObjectIdentifier37 = NISTObjectIdentifiers.I;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier37, "AESWRAPPAD");
            ASN1ObjectIdentifier aSN1ObjectIdentifier38 = aSN1ObjectIdentifier37;
            ASN1ObjectIdentifier aSN1ObjectIdentifier39 = NISTObjectIdentifiers.Q;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier39, "AESWRAPPAD");
            ASN1ObjectIdentifier aSN1ObjectIdentifier40 = aSN1ObjectIdentifier39;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.AESKWP", "AESWRAPPAD");
            configurableProvider.addAlgorithm("Cipher.AESRFC3211WRAP", str + "$RFC3211Wrap");
            configurableProvider.addAlgorithm("Cipher.AESRFC5649WRAP", str + "$RFC5649Wrap");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.CCM", str + "$AlgParamGenCCM");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier7, "CCM");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier8, "CCM");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier9, "CCM");
            configurableProvider.addAttributes("Cipher.CCM", AES.a);
            configurableProvider.addAlgorithm("Cipher.CCM", str + "$CCM");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier7, "CCM");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier8, "CCM");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier9, "CCM");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.GCM", str + "$AlgParamGenGCM");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier4, CodePackage.GCM);
            StringBuilder sb17 = new StringBuilder();
            sb17.append("Alg.Alias.AlgorithmParameterGenerator.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier41 = aSN1ObjectIdentifier11;
            sb17.append(aSN1ObjectIdentifier41);
            configurableProvider.addAlgorithm(sb17.toString(), CodePackage.GCM);
            StringBuilder sb18 = new StringBuilder();
            sb18.append("Alg.Alias.AlgorithmParameterGenerator.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier42 = aSN1ObjectIdentifier10;
            sb18.append(aSN1ObjectIdentifier42);
            configurableProvider.addAlgorithm(sb18.toString(), CodePackage.GCM);
            configurableProvider.addAttributes("Cipher.GCM", AES.a);
            configurableProvider.addAlgorithm("Cipher.GCM", str + "$GCM");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier4, CodePackage.GCM);
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier41, CodePackage.GCM);
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier42, CodePackage.GCM);
            configurableProvider.addAlgorithm("KeyGenerator.AES", str + "$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.2.16.840.1.101.3.4.2", str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator.2.16.840.1.101.3.4.22", str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator.2.16.840.1.101.3.4.42", str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier14, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier17, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier23, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier16, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier26, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier22, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier28, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier20, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier18, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier24, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier27, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator.AESWRAP", str + "$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier30, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier32, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier34, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier4, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier41, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier42, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier7, str + "$KeyGen128");
            ASN1ObjectIdentifier aSN1ObjectIdentifier43 = aSN1ObjectIdentifier8;
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier43, str + "$KeyGen192");
            ASN1ObjectIdentifier aSN1ObjectIdentifier44 = aSN1ObjectIdentifier9;
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier44, str + "$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator.AESWRAPPAD", str + "$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier36, str + "$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier38, str + "$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier40, str + "$KeyGen256");
            configurableProvider.addAlgorithm("Mac.AESCMAC", str + "$AESCMAC");
            configurableProvider.addAlgorithm("Mac.AESCCMMAC", str + "$AESCCMMAC");
            configurableProvider.addAlgorithm("Alg.Alias.Mac." + aSN1ObjectIdentifier7.s(), "AESCCMMAC");
            configurableProvider.addAlgorithm("Alg.Alias.Mac." + aSN1ObjectIdentifier43.s(), "AESCCMMAC");
            configurableProvider.addAlgorithm("Alg.Alias.Mac." + aSN1ObjectIdentifier44.s(), "AESCCMMAC");
            ASN1ObjectIdentifier aSN1ObjectIdentifier45 = BCObjectIdentifiers.l;
            String str2 = "Alg.Alias.Cipher";
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier45, "PBEWITHSHAAND128BITAES-CBC-BC");
            ASN1ObjectIdentifier aSN1ObjectIdentifier46 = BCObjectIdentifiers.m;
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier46, "PBEWITHSHAAND192BITAES-CBC-BC");
            ASN1ObjectIdentifier aSN1ObjectIdentifier47 = BCObjectIdentifiers.n;
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier47, "PBEWITHSHAAND256BITAES-CBC-BC");
            ASN1ObjectIdentifier aSN1ObjectIdentifier48 = BCObjectIdentifiers.o;
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier48, "PBEWITHSHA256AND128BITAES-CBC-BC");
            ASN1ObjectIdentifier aSN1ObjectIdentifier49 = BCObjectIdentifiers.p;
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier49, "PBEWITHSHA256AND192BITAES-CBC-BC");
            ASN1ObjectIdentifier aSN1ObjectIdentifier50 = BCObjectIdentifiers.q;
            configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier50, "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND128BITAES-CBC-BC", str + "$PBEWithSHA1AESCBC128");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND192BITAES-CBC-BC", str + "$PBEWithSHA1AESCBC192");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND256BITAES-CBC-BC", str + "$PBEWithSHA1AESCBC256");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHA256AND128BITAES-CBC-BC", str + "$PBEWithSHA256AESCBC128");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHA256AND192BITAES-CBC-BC", str + "$PBEWithSHA256AESCBC192");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHA256AND256BITAES-CBC-BC", str + "$PBEWithSHA256AESCBC256");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND128BITAES-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND192BITAES-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND256BITAES-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITAES-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND192BITAES-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND256BITAES-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND128BITAES-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND192BITAES-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND256BITAES-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND128BITAES-CBC-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND192BITAES-CBC-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND256BITAES-CBC-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA256AND128BITAES-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA256AND192BITAES-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA256AND256BITAES-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND128BITAES-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND192BITAES-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND256BITAES-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHMD5AND128BITAES-CBC-OPENSSL", str + "$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHMD5AND192BITAES-CBC-OPENSSL", str + "$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHMD5AND256BITAES-CBC-OPENSSL", str + "$PBEWithAESCBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.AES", str + "$KeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory", NISTObjectIdentifiers.s, str + "$KeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND128BITAES-CBC-OPENSSL", str + "$PBEWithMD5And128BitAESCBCOpenSSL");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND192BITAES-CBC-OPENSSL", str + "$PBEWithMD5And192BitAESCBCOpenSSL");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND256BITAES-CBC-OPENSSL", str + "$PBEWithMD5And256BitAESCBCOpenSSL");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND128BITAES-CBC-BC", str + "$PBEWithSHAAnd128BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND192BITAES-CBC-BC", str + "$PBEWithSHAAnd192BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND256BITAES-CBC-BC", str + "$PBEWithSHAAnd256BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND128BITAES-CBC-BC", str + "$PBEWithSHA256And128BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND192BITAES-CBC-BC", str + "$PBEWithSHA256And192BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND256BITAES-CBC-BC", str + "$PBEWithSHA256And256BitAESBC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND128BITAES-CBC-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND192BITAES-CBC-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND256BITAES-CBC-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND128BITAES-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND192BITAES-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND256BITAES-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier45, "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier46, "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier47, "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier48, "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier49, "PBEWITHSHA256AND192BITAES-CBC-BC");
            ASN1ObjectIdentifier aSN1ObjectIdentifier51 = aSN1ObjectIdentifier50;
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier51, "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND256BITAES-CBC-BC", "PKCS12PBE");
            StringBuilder sb19 = new StringBuilder();
            String str3 = "Alg.Alias.AlgorithmParameters.";
            sb19.append(str3);
            sb19.append(aSN1ObjectIdentifier45.s());
            configurableProvider.addAlgorithm(sb19.toString(), "PKCS12PBE");
            configurableProvider.addAlgorithm(str3 + aSN1ObjectIdentifier46.s(), "PKCS12PBE");
            configurableProvider.addAlgorithm(str3 + aSN1ObjectIdentifier47.s(), "PKCS12PBE");
            configurableProvider.addAlgorithm(str3 + aSN1ObjectIdentifier48.s(), "PKCS12PBE");
            configurableProvider.addAlgorithm(str3 + aSN1ObjectIdentifier49.s(), "PKCS12PBE");
            configurableProvider.addAlgorithm(str3 + aSN1ObjectIdentifier51.s(), "PKCS12PBE");
            String str4 = "AES";
            c(configurableProvider, str4, str + "$AESGMAC", str + "$KeyGen128");
            d(configurableProvider, str4, str + "$Poly1305", str + "$Poly1305KeyGen");
        }
    }
}
