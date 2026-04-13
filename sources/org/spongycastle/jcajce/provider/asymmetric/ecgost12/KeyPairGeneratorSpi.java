package org.spongycastle.jcajce.provider.asymmetric.ecgost12;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
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

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    Object a = null;
    ECKeyPairGenerator b = new ECKeyPairGenerator();
    String c = "ECGOST3410-2012";
    ECKeyGenerationParameters d;
    int e = 239;
    SecureRandom f = null;
    boolean g = false;

    public KeyPairGeneratorSpi() {
        super("ECGOST3410-2012");
    }

    public void initialize(int strength, SecureRandom random) {
        this.e = strength;
        this.f = random;
        Object obj = this.a;
        if (obj != null) {
            try {
                initialize((AlgorithmParameterSpec) (ECGenParameterSpec) obj, random);
            } catch (InvalidAlgorithmParameterException e2) {
                throw new InvalidParameterException("key size not configurable.");
            }
        } else {
            throw new InvalidParameterException("unknown key size.");
        }
    }

    public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
        String curveName;
        if (params instanceof ECParameterSpec) {
            ECParameterSpec p = (ECParameterSpec) params;
            this.a = params;
            ECKeyGenerationParameters eCKeyGenerationParameters = new ECKeyGenerationParameters(new ECDomainParameters(p.a(), p.b(), p.d(), p.c()), random);
            this.d = eCKeyGenerationParameters;
            this.b.c(eCKeyGenerationParameters);
            this.g = true;
        } else if (params instanceof java.security.spec.ECParameterSpec) {
            java.security.spec.ECParameterSpec p2 = (java.security.spec.ECParameterSpec) params;
            this.a = params;
            ECCurve curve = EC5Util.b(p2.getCurve());
            ECKeyGenerationParameters eCKeyGenerationParameters2 = new ECKeyGenerationParameters(new ECDomainParameters(curve, EC5Util.e(curve, p2.getGenerator(), false), p2.getOrder(), BigInteger.valueOf((long) p2.getCofactor())), random);
            this.d = eCKeyGenerationParameters2;
            this.b.c(eCKeyGenerationParameters2);
            this.g = true;
        } else if ((params instanceof ECGenParameterSpec) || (params instanceof ECNamedCurveGenParameterSpec)) {
            if (params instanceof ECGenParameterSpec) {
                curveName = ((ECGenParameterSpec) params).getName();
            } else {
                curveName = ((ECNamedCurveGenParameterSpec) params).a();
            }
            ECDomainParameters ecP = ECGOST3410NamedCurves.a(curveName);
            if (ecP != null) {
                ECNamedCurveSpec eCNamedCurveSpec = new ECNamedCurveSpec(curveName, ecP.a(), ecP.b(), ecP.d(), ecP.c(), ecP.e());
                this.a = eCNamedCurveSpec;
                java.security.spec.ECParameterSpec p3 = eCNamedCurveSpec;
                ECCurve curve2 = EC5Util.b(p3.getCurve());
                ECKeyGenerationParameters eCKeyGenerationParameters3 = new ECKeyGenerationParameters(new ECDomainParameters(curve2, EC5Util.e(curve2, p3.getGenerator(), false), p3.getOrder(), BigInteger.valueOf((long) p3.getCofactor())), random);
                this.d = eCKeyGenerationParameters3;
                this.b.c(eCKeyGenerationParameters3);
                this.g = true;
                return;
            }
            throw new InvalidAlgorithmParameterException("unknown curve name: " + curveName);
        } else {
            if (params == null) {
                ProviderConfiguration providerConfiguration = BouncyCastleProvider.CONFIGURATION;
                if (providerConfiguration.b() != null) {
                    ECParameterSpec p4 = providerConfiguration.b();
                    this.a = params;
                    ECKeyGenerationParameters eCKeyGenerationParameters4 = new ECKeyGenerationParameters(new ECDomainParameters(p4.a(), p4.b(), p4.d(), p4.c()), random);
                    this.d = eCKeyGenerationParameters4;
                    this.b.c(eCKeyGenerationParameters4);
                    this.g = true;
                    return;
                }
            }
            if (params == null && BouncyCastleProvider.CONFIGURATION.b() == null) {
                throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
            }
            throw new InvalidAlgorithmParameterException("parameter object not a ECParameterSpec: " + params.getClass().getName());
        }
    }

    public KeyPair generateKeyPair() {
        if (this.g) {
            AsymmetricCipherKeyPair pair = this.b.a();
            ECPublicKeyParameters pub2 = (ECPublicKeyParameters) pair.b();
            ECPrivateKeyParameters priv = (ECPrivateKeyParameters) pair.a();
            Object obj = this.a;
            if (obj instanceof ECParameterSpec) {
                ECParameterSpec p = (ECParameterSpec) obj;
                BCECGOST3410_2012PublicKey pubKey = new BCECGOST3410_2012PublicKey(this.c, pub2, p);
                return new KeyPair(pubKey, new BCECGOST3410_2012PrivateKey(this.c, priv, pubKey, p));
            } else if (obj == null) {
                return new KeyPair(new BCECGOST3410_2012PublicKey(this.c, pub2), new BCECGOST3410_2012PrivateKey(this.c, priv));
            } else {
                java.security.spec.ECParameterSpec p2 = (java.security.spec.ECParameterSpec) obj;
                BCECGOST3410_2012PublicKey pubKey2 = new BCECGOST3410_2012PublicKey(this.c, pub2, p2);
                return new KeyPair(pubKey2, new BCECGOST3410_2012PrivateKey(this.c, priv, pubKey2, p2));
            }
        } else {
            throw new IllegalStateException("EC Key Pair Generator not initialised");
        }
    }
}
