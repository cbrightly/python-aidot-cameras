package org.spongycastle.pqc.jcajce.provider.rainbow;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.spongycastle.pqc.asn1.RainbowPrivateKey;
import org.spongycastle.pqc.asn1.RainbowPublicKey;
import org.spongycastle.pqc.jcajce.spec.RainbowPrivateKeySpec;
import org.spongycastle.pqc.jcajce.spec.RainbowPublicKeySpec;

public class RainbowKeyFactorySpi extends KeyFactorySpi implements AsymmetricKeyInfoConverter {
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) {
        if (keySpec instanceof RainbowPrivateKeySpec) {
            return new BCRainbowPrivateKey((RainbowPrivateKeySpec) keySpec);
        }
        if (keySpec instanceof PKCS8EncodedKeySpec) {
            try {
                return a(PrivateKeyInfo.f(ASN1Primitive.h(((PKCS8EncodedKeySpec) keySpec).getEncoded())));
            } catch (Exception e) {
                throw new InvalidKeySpecException(e.toString());
            }
        } else {
            throw new InvalidKeySpecException("Unsupported key specification: " + keySpec.getClass() + ".");
        }
    }

    public PublicKey engineGeneratePublic(KeySpec keySpec) {
        if (keySpec instanceof RainbowPublicKeySpec) {
            return new BCRainbowPublicKey((RainbowPublicKeySpec) keySpec);
        }
        if (keySpec instanceof X509EncodedKeySpec) {
            try {
                return b(SubjectPublicKeyInfo.g(((X509EncodedKeySpec) keySpec).getEncoded()));
            } catch (Exception e) {
                throw new InvalidKeySpecException(e.toString());
            }
        } else {
            throw new InvalidKeySpecException("Unknown key specification: " + keySpec + ".");
        }
    }

    public final KeySpec engineGetKeySpec(Key key, Class keySpec) {
        if (key instanceof BCRainbowPrivateKey) {
            if (PKCS8EncodedKeySpec.class.isAssignableFrom(keySpec)) {
                return new PKCS8EncodedKeySpec(key.getEncoded());
            }
            if (RainbowPrivateKeySpec.class.isAssignableFrom(keySpec)) {
                BCRainbowPrivateKey privKey = (BCRainbowPrivateKey) key;
                return new RainbowPrivateKeySpec(privKey.getInvA1(), privKey.getB1(), privKey.getInvA2(), privKey.getB2(), privKey.getVi(), privKey.getLayers());
            }
        } else if (!(key instanceof BCRainbowPublicKey)) {
            throw new InvalidKeySpecException("Unsupported key type: " + key.getClass() + ".");
        } else if (X509EncodedKeySpec.class.isAssignableFrom(keySpec)) {
            return new X509EncodedKeySpec(key.getEncoded());
        } else {
            if (RainbowPublicKeySpec.class.isAssignableFrom(keySpec)) {
                BCRainbowPublicKey pubKey = (BCRainbowPublicKey) key;
                return new RainbowPublicKeySpec(pubKey.getDocLength(), pubKey.getCoeffQuadratic(), pubKey.getCoeffSingular(), pubKey.getCoeffScalar());
            }
        }
        throw new InvalidKeySpecException("Unknown key specification: " + keySpec + ".");
    }

    public final Key engineTranslateKey(Key key) {
        if ((key instanceof BCRainbowPrivateKey) || (key instanceof BCRainbowPublicKey)) {
            return key;
        }
        throw new InvalidKeyException("Unsupported key type");
    }

    public PrivateKey a(PrivateKeyInfo keyInfo) {
        RainbowPrivateKey pKey = RainbowPrivateKey.g(keyInfo.h());
        return new BCRainbowPrivateKey(pKey.h(), pKey.e(), pKey.i(), pKey.f(), pKey.n(), pKey.k());
    }

    public PublicKey b(SubjectPublicKeyInfo keyInfo) {
        RainbowPublicKey pKey = RainbowPublicKey.i(keyInfo.i());
        return new BCRainbowPublicKey(pKey.h(), pKey.e(), pKey.g(), pKey.f());
    }
}
