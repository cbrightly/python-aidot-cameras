package org.spongycastle.jcajce.provider.asymmetric.dsa;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;

public class KeyFactorySpi extends BaseKeyFactorySpi {
    /* access modifiers changed from: protected */
    public KeySpec engineGetKeySpec(Key key, Class spec) {
        if (spec.isAssignableFrom(DSAPublicKeySpec.class) && (key instanceof DSAPublicKey)) {
            DSAPublicKey k = (DSAPublicKey) key;
            return new DSAPublicKeySpec(k.getY(), k.getParams().getP(), k.getParams().getQ(), k.getParams().getG());
        } else if (!spec.isAssignableFrom(DSAPrivateKeySpec.class) || !(key instanceof DSAPrivateKey)) {
            return super.engineGetKeySpec(key, spec);
        } else {
            DSAPrivateKey k2 = (DSAPrivateKey) key;
            return new DSAPrivateKeySpec(k2.getX(), k2.getParams().getP(), k2.getParams().getQ(), k2.getParams().getG());
        }
    }

    /* access modifiers changed from: protected */
    public Key engineTranslateKey(Key key) {
        if (key instanceof DSAPublicKey) {
            return new BCDSAPublicKey((DSAPublicKey) key);
        }
        if (key instanceof DSAPrivateKey) {
            return new BCDSAPrivateKey((DSAPrivateKey) key);
        }
        throw new InvalidKeyException("key type unknown");
    }

    public PrivateKey a(PrivateKeyInfo keyInfo) {
        ASN1ObjectIdentifier algOid = keyInfo.g().e();
        if (DSAUtil.d(algOid)) {
            return new BCDSAPrivateKey(keyInfo);
        }
        throw new IOException("algorithm identifier " + algOid + " in key not recognised");
    }

    public PublicKey b(SubjectPublicKeyInfo keyInfo) {
        ASN1ObjectIdentifier algOid = keyInfo.e().e();
        if (DSAUtil.d(algOid)) {
            return new BCDSAPublicKey(keyInfo);
        }
        throw new IOException("algorithm identifier " + algOid + " in key not recognised");
    }

    /* access modifiers changed from: protected */
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) {
        if (keySpec instanceof DSAPrivateKeySpec) {
            return new BCDSAPrivateKey((DSAPrivateKeySpec) keySpec);
        }
        return super.engineGeneratePrivate(keySpec);
    }

    /* access modifiers changed from: protected */
    public PublicKey engineGeneratePublic(KeySpec keySpec) {
        if (!(keySpec instanceof DSAPublicKeySpec)) {
            return super.engineGeneratePublic(keySpec);
        }
        try {
            return new BCDSAPublicKey((DSAPublicKeySpec) keySpec);
        } catch (Exception e) {
            throw new InvalidKeySpecException("invalid KeySpec: " + e.getMessage()) {
                public Throwable getCause() {
                    return e;
                }
            };
        }
    }
}
