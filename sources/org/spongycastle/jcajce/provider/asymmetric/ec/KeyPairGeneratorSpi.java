package org.spongycastle.jcajce.provider.asymmetric.ec;

import com.alibaba.fastjson.asm.Opcodes;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.jce.spec.ECParameterSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.Integers;

public abstract class KeyPairGeneratorSpi extends KeyPairGenerator {
    public KeyPairGeneratorSpi(String algorithmName) {
        super(algorithmName);
    }

    public static class EC extends KeyPairGeneratorSpi {
        private static Hashtable a;
        ECKeyGenerationParameters b;
        ECKeyPairGenerator c;
        Object d;
        int e;
        int f;
        SecureRandom g;
        boolean h;
        String i;
        ProviderConfiguration j;

        static {
            Hashtable hashtable = new Hashtable();
            a = hashtable;
            hashtable.put(Integers.b(Opcodes.CHECKCAST), new ECGenParameterSpec("prime192v1"));
            a.put(Integers.b(239), new ECGenParameterSpec("prime239v1"));
            a.put(Integers.b(256), new ECGenParameterSpec("prime256v1"));
            a.put(Integers.b(224), new ECGenParameterSpec("P-224"));
            a.put(Integers.b(384), new ECGenParameterSpec("P-384"));
            a.put(Integers.b(521), new ECGenParameterSpec("P-521"));
        }

        public EC() {
            super("EC");
            this.c = new ECKeyPairGenerator();
            this.d = null;
            this.e = 239;
            this.f = 50;
            this.g = new SecureRandom();
            this.h = false;
            this.i = "EC";
            this.j = BouncyCastleProvider.CONFIGURATION;
        }

        public EC(String algorithm, ProviderConfiguration configuration) {
            super(algorithm);
            this.c = new ECKeyPairGenerator();
            this.d = null;
            this.e = 239;
            this.f = 50;
            this.g = new SecureRandom();
            this.h = false;
            this.i = algorithm;
            this.j = configuration;
        }

        public void initialize(int strength, SecureRandom random) {
            this.e = strength;
            this.g = random;
            ECGenParameterSpec ecParams = (ECGenParameterSpec) a.get(Integers.b(strength));
            if (ecParams != null) {
                try {
                    initialize((AlgorithmParameterSpec) ecParams, random);
                } catch (InvalidAlgorithmParameterException e2) {
                    throw new InvalidParameterException("key size not configurable.");
                }
            } else {
                throw new InvalidParameterException("unknown key size.");
            }
        }

        public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
            if (params == null) {
                ECParameterSpec implicitCA = this.j.b();
                if (implicitCA != null) {
                    this.d = null;
                    this.b = a(implicitCA, random);
                } else {
                    throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
                }
            } else if (params instanceof ECParameterSpec) {
                this.d = params;
                this.b = a((ECParameterSpec) params, random);
            } else if (params instanceof java.security.spec.ECParameterSpec) {
                this.d = params;
                this.b = b((java.security.spec.ECParameterSpec) params, random);
            } else if (params instanceof ECGenParameterSpec) {
                d(((ECGenParameterSpec) params).getName(), random);
            } else if (params instanceof ECNamedCurveGenParameterSpec) {
                d(((ECNamedCurveGenParameterSpec) params).a(), random);
            } else {
                throw new InvalidAlgorithmParameterException("parameter object not a ECParameterSpec");
            }
            this.c.c(this.b);
            this.h = true;
        }

        public KeyPair generateKeyPair() {
            if (!this.h) {
                initialize(this.e, new SecureRandom());
            }
            AsymmetricCipherKeyPair pair = this.c.a();
            ECPublicKeyParameters pub2 = (ECPublicKeyParameters) pair.b();
            ECPrivateKeyParameters priv = (ECPrivateKeyParameters) pair.a();
            Object obj = this.d;
            if (obj instanceof ECParameterSpec) {
                ECParameterSpec p = (ECParameterSpec) obj;
                BCECPublicKey pubKey = new BCECPublicKey(this.i, pub2, p, this.j);
                return new KeyPair(pubKey, new BCECPrivateKey(this.i, priv, pubKey, p, this.j));
            } else if (obj == null) {
                return new KeyPair(new BCECPublicKey(this.i, pub2, this.j), new BCECPrivateKey(this.i, priv, this.j));
            } else {
                java.security.spec.ECParameterSpec p2 = (java.security.spec.ECParameterSpec) obj;
                BCECPublicKey pubKey2 = new BCECPublicKey(this.i, pub2, p2, this.j);
                return new KeyPair(pubKey2, new BCECPrivateKey(this.i, priv, pubKey2, p2, this.j));
            }
        }

        /* access modifiers changed from: protected */
        public ECKeyGenerationParameters a(ECParameterSpec p, SecureRandom r) {
            return new ECKeyGenerationParameters(new ECDomainParameters(p.a(), p.b(), p.d(), p.c()), r);
        }

        /* access modifiers changed from: protected */
        public ECKeyGenerationParameters b(java.security.spec.ECParameterSpec p, SecureRandom r) {
            ECCurve curve = EC5Util.b(p.getCurve());
            return new ECKeyGenerationParameters(new ECDomainParameters(curve, EC5Util.e(curve, p.getGenerator(), false), p.getOrder(), BigInteger.valueOf((long) p.getCofactor())), r);
        }

        /* access modifiers changed from: protected */
        public ECNamedCurveSpec c(String curveName) {
            X9ECParameters p = ECUtils.d(curveName);
            if (p == null) {
                try {
                    p = ECNamedCurveTable.c(new ASN1ObjectIdentifier(curveName));
                    if (p == null) {
                        p = (X9ECParameters) this.j.a().get(new ASN1ObjectIdentifier(curveName));
                        if (p == null) {
                            throw new InvalidAlgorithmParameterException("unknown curve OID: " + curveName);
                        }
                    }
                } catch (IllegalArgumentException e2) {
                    throw new InvalidAlgorithmParameterException("unknown curve name: " + curveName);
                }
            }
            return new ECNamedCurveSpec(curveName, p.e(), p.f(), p.i(), p.g(), (byte[]) null);
        }

        /* access modifiers changed from: protected */
        public void d(String curveName, SecureRandom random) {
            ECNamedCurveSpec namedCurve = c(curveName);
            this.d = namedCurve;
            this.b = b(namedCurve, random);
        }
    }

    public static class ECDSA extends EC {
        public ECDSA() {
            super("ECDSA", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDH extends EC {
        public ECDH() {
            super("ECDH", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDHC extends EC {
        public ECDHC() {
            super("ECDHC", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECMQV extends EC {
        public ECMQV() {
            super("ECMQV", BouncyCastleProvider.CONFIGURATION);
        }
    }
}
