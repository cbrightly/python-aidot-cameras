package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.spongycastle.jcajce.provider.asymmetric.util.ExtendedInvalidKeySpecException;

public class KeyFactorySpi extends BaseKeyFactorySpi {
    /* access modifiers changed from: protected */
    public KeySpec engineGetKeySpec(Key key, Class spec) {
        if (spec.isAssignableFrom(RSAPublicKeySpec.class) && (key instanceof RSAPublicKey)) {
            RSAPublicKey k = (RSAPublicKey) key;
            return new RSAPublicKeySpec(k.getModulus(), k.getPublicExponent());
        } else if (spec.isAssignableFrom(RSAPrivateKeySpec.class) && (key instanceof RSAPrivateKey)) {
            RSAPrivateKey k2 = (RSAPrivateKey) key;
            return new RSAPrivateKeySpec(k2.getModulus(), k2.getPrivateExponent());
        } else if (!spec.isAssignableFrom(RSAPrivateCrtKeySpec.class) || !(key instanceof RSAPrivateCrtKey)) {
            return super.engineGetKeySpec(key, spec);
        } else {
            RSAPrivateCrtKey k3 = (RSAPrivateCrtKey) key;
            return new RSAPrivateCrtKeySpec(k3.getModulus(), k3.getPublicExponent(), k3.getPrivateExponent(), k3.getPrimeP(), k3.getPrimeQ(), k3.getPrimeExponentP(), k3.getPrimeExponentQ(), k3.getCrtCoefficient());
        }
    }

    /* access modifiers changed from: protected */
    public Key engineTranslateKey(Key key) {
        if (key instanceof RSAPublicKey) {
            return new BCRSAPublicKey((RSAPublicKey) key);
        }
        if (key instanceof RSAPrivateCrtKey) {
            return new BCRSAPrivateCrtKey((RSAPrivateCrtKey) key);
        }
        if (key instanceof RSAPrivateKey) {
            return new BCRSAPrivateKey((RSAPrivateKey) key);
        }
        throw new InvalidKeyException("key type unknown");
    }

    /* access modifiers changed from: protected */
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) {
        if (keySpec instanceof PKCS8EncodedKeySpec) {
            try {
                return a(PrivateKeyInfo.f(((PKCS8EncodedKeySpec) keySpec).getEncoded()));
            } catch (Exception e) {
                try {
                    return new BCRSAPrivateCrtKey(org.spongycastle.asn1.pkcs.RSAPrivateKey.h(((PKCS8EncodedKeySpec) keySpec).getEncoded()));
                } catch (Exception e2) {
                    throw new ExtendedInvalidKeySpecException("unable to process key spec: " + e.toString(), e);
                }
            }
        } else if (keySpec instanceof RSAPrivateCrtKeySpec) {
            return new BCRSAPrivateCrtKey((RSAPrivateCrtKeySpec) keySpec);
        } else {
            if (keySpec instanceof RSAPrivateKeySpec) {
                return new BCRSAPrivateKey((RSAPrivateKeySpec) keySpec);
            }
            throw new InvalidKeySpecException("Unknown KeySpec type: " + keySpec.getClass().getName());
        }
    }

    /* access modifiers changed from: protected */
    public PublicKey engineGeneratePublic(KeySpec keySpec) {
        if (keySpec instanceof RSAPublicKeySpec) {
            return new BCRSAPublicKey((RSAPublicKeySpec) keySpec);
        }
        return super.engineGeneratePublic(keySpec);
    }

    public PrivateKey a(PrivateKeyInfo keyInfo) {
        ASN1ObjectIdentifier algOid = keyInfo.g().e();
        if (RSAUtil.d(algOid)) {
            org.spongycastle.asn1.pkcs.RSAPrivateKey rsaPrivKey = org.spongycastle.asn1.pkcs.RSAPrivateKey.h(keyInfo.h());
            if (rsaPrivKey.e().intValue() == 0) {
                return new BCRSAPrivateKey(rsaPrivKey);
            }
            return new BCRSAPrivateCrtKey(keyInfo);
        }
        throw new IOException("algorithm identifier " + algOid + " in key not recognised");
    }

    public PublicKey b(SubjectPublicKeyInfo keyInfo) {
        ASN1ObjectIdentifier algOid = keyInfo.e().e();
        if (RSAUtil.d(algOid)) {
            return new BCRSAPublicKey(keyInfo);
        }
        throw new IOException("algorithm identifier " + algOid + " in key not recognised");
    }
}
