package org.spongycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.PasswordConverter;
import org.spongycastle.crypto.engines.DESEngine;
import org.spongycastle.crypto.engines.RFC3211WrapEngine;
import org.spongycastle.crypto.generators.DESKeyGenerator;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.macs.CFBBlockCipherMac;
import org.spongycastle.crypto.macs.CMac;
import org.spongycastle.crypto.macs.ISO9797Alg3Mac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.ISO7816d4Padding;
import org.spongycastle.crypto.params.DESParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.jcajce.PBKDF1Key;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.spongycastle.jcajce.provider.symmetric.util.PBE;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class DES {
    private DES() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipher) new DESEngine());
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new DESEngine()), 64);
        }
    }

    public static class DESCFB8 extends BaseMac {
        public DESCFB8() {
            super(new CFBBlockCipherMac(new DESEngine()));
        }
    }

    public static class DES64 extends BaseMac {
        public DES64() {
            super(new CBCBlockCipherMac(new DESEngine(), 64));
        }
    }

    public static class DES64with7816d4 extends BaseMac {
        public DES64with7816d4() {
            super(new CBCBlockCipherMac(new DESEngine(), 64, new ISO7816d4Padding()));
        }
    }

    public static class CBCMAC extends BaseMac {
        public CBCMAC() {
            super(new CBCBlockCipherMac(new DESEngine()));
        }
    }

    public static class CMAC extends BaseMac {
        public CMAC() {
            super(new CMac(new DESEngine()));
        }
    }

    public static class DES9797Alg3with7816d4 extends BaseMac {
        public DES9797Alg3with7816d4() {
            super(new ISO9797Alg3Mac(new DESEngine(), new ISO7816d4Padding()));
        }
    }

    public static class DES9797Alg3 extends BaseMac {
        public DES9797Alg3() {
            super(new ISO9797Alg3Mac(new DESEngine()));
        }
    }

    public static class RFC3211 extends BaseWrapCipher {
        public RFC3211() {
            super(new RFC3211WrapEngine(new DESEngine()), 8);
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

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("DES", 64, new DESKeyGenerator());
        }

        /* access modifiers changed from: protected */
        public void engineInit(int keySize, SecureRandom random) {
            super.engineInit(keySize, random);
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateKey() {
            if (this.e) {
                this.d.b(new KeyGenerationParameters(new SecureRandom(), this.c));
                this.e = false;
            }
            return new SecretKeySpec(this.d.a(), this.a);
        }
    }

    public static class KeyFactory extends BaseSecretKeyFactory {
        public KeyFactory() {
            super("DES", (ASN1ObjectIdentifier) null);
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
                if (DESKeySpec.class.isAssignableFrom(keySpec)) {
                    try {
                        return new DESKeySpec(key.getEncoded());
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
            if (keySpec instanceof DESKeySpec) {
                return new SecretKeySpec(((DESKeySpec) keySpec).getKey(), "DES");
            }
            return super.engineGenerateSecret(keySpec);
        }
    }

    public static class DESPBEKeyFactory extends BaseSecretKeyFactory {
        private boolean f;
        private int q;
        private int x;
        private int y;
        private int z;

        public DESPBEKeyFactory(String algorithm, ASN1ObjectIdentifier oid, boolean forCipher, int scheme, int digest, int keySize, int ivSize) {
            super(algorithm, oid);
            this.f = forCipher;
            this.q = scheme;
            this.x = digest;
            this.y = keySize;
            this.z = ivSize;
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateSecret(KeySpec keySpec) {
            CipherParameters param;
            KeyParameter kParam;
            if (keySpec instanceof PBEKeySpec) {
                PBEKeySpec pbeSpec = (PBEKeySpec) keySpec;
                if (pbeSpec.getSalt() == null) {
                    int i = this.q;
                    if (i != 0 && i != 4) {
                        return new BCPBEKey(this.c, this.d, i, this.x, this.y, this.z, pbeSpec, (CipherParameters) null);
                    }
                    return new PBKDF1Key(pbeSpec.getPassword(), this.q == 0 ? PasswordConverter.ASCII : PasswordConverter.UTF8);
                }
                if (this.f) {
                    param = PBE.Util.f(pbeSpec, this.q, this.x, this.y, this.z);
                } else {
                    param = PBE.Util.d(pbeSpec, this.q, this.x, this.y);
                }
                if (param instanceof ParametersWithIV) {
                    kParam = (KeyParameter) ((ParametersWithIV) param).b();
                } else {
                    kParam = (KeyParameter) param;
                }
                DESParameters.c(kParam.a());
                return new BCPBEKey(this.c, this.d, this.q, this.x, this.y, this.z, pbeSpec, param);
            }
            throw new InvalidKeySpecException("Invalid KeySpec");
        }
    }

    public static class PBEWithMD2KeyFactory extends DESPBEKeyFactory {
        public PBEWithMD2KeyFactory() {
            super("PBEwithMD2andDES", PKCSObjectIdentifiers.d0, true, 0, 5, 64, 64);
        }
    }

    public static class PBEWithMD5KeyFactory extends DESPBEKeyFactory {
        public PBEWithMD5KeyFactory() {
            super("PBEwithMD5andDES", PKCSObjectIdentifiers.f0, true, 0, 0, 64, 64);
        }
    }

    public static class PBEWithSHA1KeyFactory extends DESPBEKeyFactory {
        public PBEWithSHA1KeyFactory() {
            super("PBEwithSHA1andDES", PKCSObjectIdentifiers.h0, true, 0, 1, 64, 64);
        }
    }

    public static class PBEWithMD2 extends BaseBlockCipher {
        public PBEWithMD2() {
            super(new CBCBlockCipher(new DESEngine()), 0, 5, 64, 8);
        }
    }

    public static class PBEWithMD5 extends BaseBlockCipher {
        public PBEWithMD5() {
            super(new CBCBlockCipher(new DESEngine()), 0, 0, 64, 8);
        }
    }

    public static class PBEWithSHA1 extends BaseBlockCipher {
        public PBEWithSHA1() {
            super(new CBCBlockCipher(new DESEngine()), 0, 1, 64, 8);
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = DES.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$ECB");
            provider.addAlgorithm("Cipher.DES", sb.toString());
            ASN1ObjectIdentifier aSN1ObjectIdentifier = OIWObjectIdentifiers.e;
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier, str + "$CBC");
            b(provider, aSN1ObjectIdentifier, "DES");
            provider.addAlgorithm("Cipher.DESRFC3211WRAP", str + "$RFC3211");
            provider.addAlgorithm("KeyGenerator.DES", str + "$KeyGenerator");
            provider.addAlgorithm("SecretKeyFactory.DES", str + "$KeyFactory");
            provider.addAlgorithm("Mac.DESCMAC", str + "$CMAC");
            provider.addAlgorithm("Mac.DESMAC", str + "$CBCMAC");
            provider.addAlgorithm("Alg.Alias.Mac.DES", "DESMAC");
            provider.addAlgorithm("Mac.DESMAC/CFB8", str + "$DESCFB8");
            provider.addAlgorithm("Alg.Alias.Mac.DES/CFB8", "DESMAC/CFB8");
            provider.addAlgorithm("Mac.DESMAC64", str + "$DES64");
            provider.addAlgorithm("Alg.Alias.Mac.DES64", "DESMAC64");
            provider.addAlgorithm("Mac.DESMAC64WITHISO7816-4PADDING", str + "$DES64with7816d4");
            provider.addAlgorithm("Alg.Alias.Mac.DES64WITHISO7816-4PADDING", "DESMAC64WITHISO7816-4PADDING");
            provider.addAlgorithm("Alg.Alias.Mac.DESISO9797ALG1MACWITHISO7816-4PADDING", "DESMAC64WITHISO7816-4PADDING");
            provider.addAlgorithm("Alg.Alias.Mac.DESISO9797ALG1WITHISO7816-4PADDING", "DESMAC64WITHISO7816-4PADDING");
            provider.addAlgorithm("Mac.DESWITHISO9797", str + "$DES9797Alg3");
            provider.addAlgorithm("Alg.Alias.Mac.DESISO9797MAC", "DESWITHISO9797");
            provider.addAlgorithm("Mac.ISO9797ALG3MAC", str + "$DES9797Alg3");
            provider.addAlgorithm("Alg.Alias.Mac.ISO9797ALG3", "ISO9797ALG3MAC");
            provider.addAlgorithm("Mac.ISO9797ALG3WITHISO7816-4PADDING", str + "$DES9797Alg3with7816d4");
            provider.addAlgorithm("Alg.Alias.Mac.ISO9797ALG3MACWITHISO7816-4PADDING", "ISO9797ALG3WITHISO7816-4PADDING");
            provider.addAlgorithm("AlgorithmParameters.DES", "org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier, "DES");
            provider.addAlgorithm("AlgorithmParameterGenerator.DES", str + "$AlgParamGen");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier, "DES");
            provider.addAlgorithm("Cipher.PBEWITHMD2ANDDES", str + "$PBEWithMD2");
            provider.addAlgorithm("Cipher.PBEWITHMD5ANDDES", str + "$PBEWithMD5");
            provider.addAlgorithm("Cipher.PBEWITHSHA1ANDDES", str + "$PBEWithSHA1");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = PKCSObjectIdentifiers.d0;
            provider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier2, "PBEWITHMD2ANDDES");
            ASN1ObjectIdentifier aSN1ObjectIdentifier3 = PKCSObjectIdentifiers.f0;
            provider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier3, "PBEWITHMD5ANDDES");
            ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.h0;
            provider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier4, "PBEWITHSHA1ANDDES");
            provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHMD2ANDDES-CBC", "PBEWITHMD2ANDDES");
            provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHMD5ANDDES-CBC", "PBEWITHMD5ANDDES");
            provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDES-CBC", "PBEWITHSHA1ANDDES");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHMD2ANDDES", str + "$PBEWithMD2KeyFactory");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHMD5ANDDES", str + "$PBEWithMD5KeyFactory");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHSHA1ANDDES", str + "$PBEWithSHA1KeyFactory");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHMD2ANDDES-CBC", "PBEWITHMD2ANDDES");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHMD5ANDDES-CBC", "PBEWITHMD5ANDDES");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1ANDDES-CBC", "PBEWITHSHA1ANDDES");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory." + aSN1ObjectIdentifier2, "PBEWITHMD2ANDDES");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory." + aSN1ObjectIdentifier3, "PBEWITHMD5ANDDES");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory." + aSN1ObjectIdentifier4, "PBEWITHSHA1ANDDES");
        }

        private void b(ConfigurableProvider provider, ASN1ObjectIdentifier oid, String name) {
            provider.addAlgorithm("Alg.Alias.KeyGenerator." + oid.s(), name);
            provider.addAlgorithm("Alg.Alias.KeyFactory." + oid.s(), name);
        }
    }
}
