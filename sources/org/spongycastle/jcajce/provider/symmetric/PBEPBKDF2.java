package org.spongycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PBKDF2Params;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.PasswordConverter;
import org.spongycastle.jcajce.PBKDF2Key;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.spongycastle.jcajce.provider.symmetric.util.PBE;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.jcajce.spec.PBKDF2KeySpec;
import org.spongycastle.util.Integers;

public class PBEPBKDF2 {
    /* access modifiers changed from: private */
    public static final Map a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put(CryptoProObjectIdentifiers.c, Integers.b(6));
        hashMap.put(PKCSObjectIdentifiers.u0, Integers.b(1));
        hashMap.put(PKCSObjectIdentifiers.w0, Integers.b(4));
        hashMap.put(PKCSObjectIdentifiers.v0, Integers.b(7));
        hashMap.put(PKCSObjectIdentifiers.x0, Integers.b(8));
        hashMap.put(PKCSObjectIdentifiers.y0, Integers.b(9));
        hashMap.put(NISTObjectIdentifiers.p, Integers.b(11));
        hashMap.put(NISTObjectIdentifiers.o, Integers.b(10));
        hashMap.put(NISTObjectIdentifiers.q, Integers.b(12));
        hashMap.put(NISTObjectIdentifiers.r, Integers.b(13));
    }

    private PBEPBKDF2() {
    }

    public static class AlgParams extends BaseAlgorithmParameters {
        PBKDF2Params params;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            try {
                return this.params.getEncoded("DER");
            } catch (IOException e) {
                throw new RuntimeException("Oooops! " + e.toString());
            }
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String format) {
            if (isASN1FormatString(format)) {
                return engineGetEncoded();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec) {
            if (paramSpec == PBEParameterSpec.class) {
                return new PBEParameterSpec(this.params.getSalt(), this.params.getIterationCount().intValue());
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PBKDF2 PBE parameters object.");
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (paramSpec instanceof PBEParameterSpec) {
                PBEParameterSpec pbeSpec = (PBEParameterSpec) paramSpec;
                this.params = new PBKDF2Params(pbeSpec.getSalt(), pbeSpec.getIterationCount());
                return;
            }
            throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PBKDF2 PBE parameters algorithm parameters object");
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params2) {
            this.params = PBKDF2Params.getInstance(ASN1Primitive.h(params2));
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params2, String format) {
            if (isASN1FormatString(format)) {
                engineInit(params2);
                return;
            }
            throw new IOException("Unknown parameters format in PBKDF2 parameters object");
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "PBKDF2 Parameters";
        }
    }

    public static class BasePBKDF2 extends BaseSecretKeyFactory {
        private int f;
        private int q;

        public BasePBKDF2(String name, int scheme) {
            this(name, scheme, 1);
        }

        public BasePBKDF2(String name, int scheme, int defaultDigest) {
            super(name, PKCSObjectIdentifiers.k0);
            this.f = scheme;
            this.q = defaultDigest;
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateSecret(KeySpec keySpec) {
            KeySpec keySpec2 = keySpec;
            if (keySpec2 instanceof PBEKeySpec) {
                PBEKeySpec pbeSpec = (PBEKeySpec) keySpec2;
                if (pbeSpec.getSalt() == null) {
                    return new PBKDF2Key(((PBEKeySpec) keySpec2).getPassword(), this.f == 1 ? PasswordConverter.ASCII : PasswordConverter.UTF8);
                } else if (pbeSpec.getIterationCount() <= 0) {
                    throw new InvalidKeySpecException("positive iteration count required: " + pbeSpec.getIterationCount());
                } else if (pbeSpec.getKeyLength() <= 0) {
                    throw new InvalidKeySpecException("positive key length required: " + pbeSpec.getKeyLength());
                } else if (pbeSpec.getPassword().length == 0) {
                    throw new IllegalArgumentException("password empty");
                } else if (pbeSpec instanceof PBKDF2KeySpec) {
                    int digest = a(((PBKDF2KeySpec) pbeSpec).a().e());
                    int keySize = pbeSpec.getKeyLength();
                    return new BCPBEKey(this.c, this.d, this.f, digest, keySize, -1, pbeSpec, PBE.Util.d(pbeSpec, this.f, digest, keySize));
                } else {
                    int digest2 = this.q;
                    int keySize2 = pbeSpec.getKeyLength();
                    return new BCPBEKey(this.c, this.d, this.f, digest2, keySize2, -1, pbeSpec, PBE.Util.d(pbeSpec, this.f, digest2, keySize2));
                }
            } else {
                throw new InvalidKeySpecException("Invalid KeySpec");
            }
        }

        private int a(ASN1ObjectIdentifier algorithm) {
            Integer code = (Integer) PBEPBKDF2.a.get(algorithm);
            if (code != null) {
                return code.intValue();
            }
            throw new InvalidKeySpecException("Invalid KeySpec: unknown PRF algorithm " + algorithm);
        }
    }

    public static class PBKDF2withUTF8 extends BasePBKDF2 {
        public PBKDF2withUTF8() {
            super("PBKDF2", 5);
        }
    }

    public static class PBKDF2withSHA224 extends BasePBKDF2 {
        public PBKDF2withSHA224() {
            super("PBKDF2", 5, 7);
        }
    }

    public static class PBKDF2withSHA256 extends BasePBKDF2 {
        public PBKDF2withSHA256() {
            super("PBKDF2", 5, 4);
        }
    }

    public static class PBKDF2withSHA384 extends BasePBKDF2 {
        public PBKDF2withSHA384() {
            super("PBKDF2", 5, 8);
        }
    }

    public static class PBKDF2withSHA512 extends BasePBKDF2 {
        public PBKDF2withSHA512() {
            super("PBKDF2", 5, 9);
        }
    }

    public static class PBKDF2withGOST3411 extends BasePBKDF2 {
        public PBKDF2withGOST3411() {
            super("PBKDF2", 5, 6);
        }
    }

    public static class PBKDF2withSHA3_224 extends BasePBKDF2 {
        public PBKDF2withSHA3_224() {
            super("PBKDF2", 5, 10);
        }
    }

    public static class PBKDF2withSHA3_256 extends BasePBKDF2 {
        public PBKDF2withSHA3_256() {
            super("PBKDF2", 5, 11);
        }
    }

    public static class PBKDF2withSHA3_384 extends BasePBKDF2 {
        public PBKDF2withSHA3_384() {
            super("PBKDF2", 5, 12);
        }
    }

    public static class PBKDF2withSHA3_512 extends BasePBKDF2 {
        public PBKDF2withSHA3_512() {
            super("PBKDF2", 5, 13);
        }
    }

    public static class PBKDF2with8BIT extends BasePBKDF2 {
        public PBKDF2with8BIT() {
            super("PBKDF2", 1);
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = PBEPBKDF2.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$AlgParams");
            provider.addAlgorithm("AlgorithmParameters.PBKDF2", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.k0;
            sb2.append(aSN1ObjectIdentifier);
            provider.addAlgorithm(sb2.toString(), "PBKDF2");
            provider.addAlgorithm("SecretKeyFactory.PBKDF2", str + "$PBKDF2withUTF8");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITHHMACSHA1", "PBKDF2");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITHHMACSHA1ANDUTF8", "PBKDF2");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory." + aSN1ObjectIdentifier, "PBKDF2");
            provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHASCII", str + "$PBKDF2with8BIT");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITH8BIT", "PBKDF2WITHASCII");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITHHMACSHA1AND8BIT", "PBKDF2WITHASCII");
            provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA224", str + "$PBKDF2withSHA224");
            provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA256", str + "$PBKDF2withSHA256");
            provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA384", str + "$PBKDF2withSHA384");
            provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA512", str + "$PBKDF2withSHA512");
            provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA3-224", str + "$PBKDF2withSHA3_224");
            provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA3-256", str + "$PBKDF2withSHA3_256");
            provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA3-384", str + "$PBKDF2withSHA3_384");
            provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA3-512", str + "$PBKDF2withSHA3_512");
            provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACGOST3411", str + "$PBKDF2withGOST3411");
        }
    }
}
