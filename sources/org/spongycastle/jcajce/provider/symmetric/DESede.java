package org.spongycastle.jcajce.provider.symmetric;

import com.alibaba.fastjson.asm.Opcodes;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.engines.DESedeWrapEngine;
import org.spongycastle.crypto.engines.RFC3211WrapEngine;
import org.spongycastle.crypto.generators.DESedeKeyGenerator;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.macs.CFBBlockCipherMac;
import org.spongycastle.crypto.macs.CMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.ISO7816d4Padding;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.DES;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class DESede {
    private DESede() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipher) new DESedeEngine());
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new DESedeEngine()), 64);
        }
    }

    public static class DESedeCFB8 extends BaseMac {
        public DESedeCFB8() {
            super(new CFBBlockCipherMac(new DESedeEngine()));
        }
    }

    public static class DESede64 extends BaseMac {
        public DESede64() {
            super(new CBCBlockCipherMac(new DESedeEngine(), 64));
        }
    }

    public static class DESede64with7816d4 extends BaseMac {
        public DESede64with7816d4() {
            super(new CBCBlockCipherMac(new DESedeEngine(), 64, new ISO7816d4Padding()));
        }
    }

    public static class CBCMAC extends BaseMac {
        public CBCMAC() {
            super(new CBCBlockCipherMac(new DESedeEngine()));
        }
    }

    public static class CMAC extends BaseMac {
        public CMAC() {
            super(new CMac(new DESedeEngine()));
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new DESedeWrapEngine());
        }
    }

    public static class RFC3211 extends BaseWrapCipher {
        public RFC3211() {
            super(new RFC3211WrapEngine(new DESedeEngine()), 8);
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        private boolean f = false;

        public KeyGenerator() {
            super("DESede", Opcodes.CHECKCAST, new DESedeKeyGenerator());
        }

        /* access modifiers changed from: protected */
        public void engineInit(int keySize, SecureRandom random) {
            super.engineInit(keySize, random);
            this.f = true;
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateKey() {
            if (this.e) {
                this.d.b(new KeyGenerationParameters(new SecureRandom(), this.c));
                this.e = false;
            }
            if (this.f) {
                return new SecretKeySpec(this.d.a(), this.a);
            }
            byte[] k = this.d.a();
            System.arraycopy(k, 0, k, 16, 8);
            return new SecretKeySpec(k, this.a);
        }
    }

    public static class KeyGenerator3 extends BaseKeyGenerator {
        public KeyGenerator3() {
            super("DESede3", Opcodes.CHECKCAST, new DESedeKeyGenerator());
        }
    }

    public static class PBEWithSHAAndDES3Key extends BaseBlockCipher {
        public PBEWithSHAAndDES3Key() {
            super(new CBCBlockCipher(new DESedeEngine()), 2, 1, Opcodes.CHECKCAST, 8);
        }
    }

    public static class PBEWithSHAAndDES2Key extends BaseBlockCipher {
        public PBEWithSHAAndDES2Key() {
            super(new CBCBlockCipher(new DESedeEngine()), 2, 1, 128, 8);
        }
    }

    public static class PBEWithSHAAndDES3KeyFactory extends DES.DESPBEKeyFactory {
        public PBEWithSHAAndDES3KeyFactory() {
            super("PBEwithSHAandDES3Key-CBC", PKCSObjectIdentifiers.t2, true, 2, 1, Opcodes.CHECKCAST, 64);
        }
    }

    public static class PBEWithSHAAndDES2KeyFactory extends DES.DESPBEKeyFactory {
        public PBEWithSHAAndDES2KeyFactory() {
            super("PBEwithSHAandDES2Key-CBC", PKCSObjectIdentifiers.u2, true, 2, 1, 128, 64);
        }
    }

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DES parameter generation.");
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] iv = new byte[8];
            if (this.b == null) {
                this.b = new SecureRandom();
            }
            this.b.nextBytes(iv);
            try {
                AlgorithmParameters params = a("DES");
                params.init(new IvParameterSpec(iv));
                return params;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static class KeyFactory extends BaseSecretKeyFactory {
        public KeyFactory() {
            super("DESede", (ASN1ObjectIdentifier) null);
        }

        /* access modifiers changed from: protected */
        public KeySpec engineGetKeySpec(SecretKey key, Class keySpec) {
            if (keySpec == null) {
                throw new InvalidKeySpecException("keySpec parameter is null");
            } else if (key == null) {
                throw new InvalidKeySpecException("key parameter is null");
            } else if (SecretKeySpec.class.isAssignableFrom(keySpec)) {
                return new SecretKeySpec(key.getEncoded(), this.c);
            } else {
                if (DESedeKeySpec.class.isAssignableFrom(keySpec)) {
                    byte[] bytes = key.getEncoded();
                    try {
                        if (bytes.length != 16) {
                            return new DESedeKeySpec(bytes);
                        }
                        byte[] longKey = new byte[24];
                        System.arraycopy(bytes, 0, longKey, 0, 16);
                        System.arraycopy(bytes, 0, longKey, 16, 8);
                        return new DESedeKeySpec(longKey);
                    } catch (Exception e) {
                        throw new InvalidKeySpecException(e.toString());
                    }
                } else {
                    throw new InvalidKeySpecException("Invalid KeySpec");
                }
            }
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateSecret(KeySpec keySpec) {
            if (keySpec instanceof DESedeKeySpec) {
                return new SecretKeySpec(((DESedeKeySpec) keySpec).getKey(), "DESede");
            }
            return super.engineGenerateSecret(keySpec);
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = DESede.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$ECB");
            provider.addAlgorithm("Cipher.DESEDE", sb.toString());
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.m0;
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier, str + "$CBC");
            provider.addAlgorithm("Cipher.DESEDEWRAP", str + "$Wrap");
            provider.addAlgorithm("Cipher", PKCSObjectIdentifiers.y2, str + "$Wrap");
            provider.addAlgorithm("Cipher.DESEDERFC3211WRAP", str + "$RFC3211");
            provider.addAlgorithm("Alg.Alias.Cipher.DESEDERFC3217WRAP", "DESEDEWRAP");
            provider.addAlgorithm("Alg.Alias.Cipher.TDEA", "DESEDE");
            provider.addAlgorithm("Alg.Alias.Cipher.TDEAWRAP", "DESEDEWRAP");
            provider.addAlgorithm("Alg.Alias.KeyGenerator.TDEA", "DESEDE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.TDEA", "DESEDE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.TDEA", "DESEDE");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.TDEA", "DESEDE");
            if (provider.hasAlgorithm("MessageDigest", "SHA-1")) {
                provider.addAlgorithm("Cipher.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", str + "$PBEWithSHAAndDES3Key");
                provider.addAlgorithm("Cipher.BROKENPBEWITHSHAAND3-KEYTRIPLEDES-CBC", str + "$BrokePBEWithSHAAndDES3Key");
                provider.addAlgorithm("Cipher.OLDPBEWITHSHAAND3-KEYTRIPLEDES-CBC", str + "$OldPBEWithSHAAndDES3Key");
                provider.addAlgorithm("Cipher.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", str + "$PBEWithSHAAndDES2Key");
                provider.addAlgorithm("Cipher.BROKENPBEWITHSHAAND2-KEYTRIPLEDES-CBC", str + "$BrokePBEWithSHAAndDES2Key");
                provider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.t2, "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                provider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.u2, "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
                provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDESEDE", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND3-KEYTRIPLEDES-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND2-KEYTRIPLEDES-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
                provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND3-KEYDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND2-KEYDESEDE-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
                provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND3-KEYDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND2-KEYDESEDE-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
                provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            }
            provider.addAlgorithm("KeyGenerator.DESEDE", str + "$KeyGenerator");
            provider.addAlgorithm("KeyGenerator." + aSN1ObjectIdentifier, str + "$KeyGenerator3");
            provider.addAlgorithm("KeyGenerator.DESEDEWRAP", str + "$KeyGenerator");
            provider.addAlgorithm("SecretKeyFactory.DESEDE", str + "$KeyFactory");
            provider.addAlgorithm("SecretKeyFactory", OIWObjectIdentifiers.h, str + "$KeyFactory");
            provider.addAlgorithm("Mac.DESEDECMAC", str + "$CMAC");
            provider.addAlgorithm("Mac.DESEDEMAC", str + "$CBCMAC");
            provider.addAlgorithm("Alg.Alias.Mac.DESEDE", "DESEDEMAC");
            provider.addAlgorithm("Mac.DESEDEMAC/CFB8", str + "$DESedeCFB8");
            provider.addAlgorithm("Alg.Alias.Mac.DESEDE/CFB8", "DESEDEMAC/CFB8");
            provider.addAlgorithm("Mac.DESEDEMAC64", str + "$DESede64");
            provider.addAlgorithm("Alg.Alias.Mac.DESEDE64", "DESEDEMAC64");
            provider.addAlgorithm("Mac.DESEDEMAC64WITHISO7816-4PADDING", str + "$DESede64with7816d4");
            provider.addAlgorithm("Alg.Alias.Mac.DESEDE64WITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
            provider.addAlgorithm("Alg.Alias.Mac.DESEDEISO9797ALG1MACWITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
            provider.addAlgorithm("Alg.Alias.Mac.DESEDEISO9797ALG1WITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
            provider.addAlgorithm("AlgorithmParameters.DESEDE", "org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + aSN1ObjectIdentifier, "DESEDE");
            provider.addAlgorithm("AlgorithmParameterGenerator.DESEDE", str + "$AlgParamGen");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier, "DESEDE");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", str + "$PBEWithSHAAndDES3KeyFactory");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", str + "$PBEWithSHAAndDES2KeyFactory");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES3KEY-CBC", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES2KEY-CBC", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.3", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.4", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.3", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.4", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.Cipher.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        }
    }
}
