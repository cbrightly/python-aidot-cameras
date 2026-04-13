package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.spongycastle.jce.interfaces.GOST3410PrivateKey;
import org.spongycastle.jce.interfaces.GOST3410PublicKey;
import org.spongycastle.jce.spec.GOST3410PrivateKeySpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeySpec;

public class KeyFactorySpi extends BaseKeyFactorySpi {
    /* access modifiers changed from: protected */
    public KeySpec engineGetKeySpec(Key key, Class spec) {
        if (spec.isAssignableFrom(GOST3410PublicKeySpec.class) && (key instanceof GOST3410PublicKey)) {
            GOST3410PublicKey k = (GOST3410PublicKey) key;
            GOST3410PublicKeyParameterSetSpec parameters = k.getParameters().getPublicKeyParameters();
            return new GOST3410PublicKeySpec(k.getY(), parameters.b(), parameters.c(), parameters.a());
        } else if (!spec.isAssignableFrom(GOST3410PrivateKeySpec.class) || !(key instanceof GOST3410PrivateKey)) {
            return super.engineGetKeySpec(key, spec);
        } else {
            GOST3410PrivateKey k2 = (GOST3410PrivateKey) key;
            GOST3410PublicKeyParameterSetSpec parameters2 = k2.getParameters().getPublicKeyParameters();
            return new GOST3410PrivateKeySpec(k2.getX(), parameters2.b(), parameters2.c(), parameters2.a());
        }
    }

    /* access modifiers changed from: protected */
    public Key engineTranslateKey(Key key) {
        if (key instanceof GOST3410PublicKey) {
            return new BCGOST3410PublicKey((GOST3410PublicKey) key);
        }
        if (key instanceof GOST3410PrivateKey) {
            return new BCGOST3410PrivateKey((GOST3410PrivateKey) key);
        }
        throw new InvalidKeyException("key type unknown");
    }

    /* access modifiers changed from: protected */
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) {
        if (keySpec instanceof GOST3410PrivateKeySpec) {
            return new BCGOST3410PrivateKey((GOST3410PrivateKeySpec) keySpec);
        }
        return super.engineGeneratePrivate(keySpec);
    }

    /* access modifiers changed from: protected */
    public PublicKey engineGeneratePublic(KeySpec keySpec) {
        if (keySpec instanceof GOST3410PublicKeySpec) {
            return new BCGOST3410PublicKey((GOST3410PublicKeySpec) keySpec);
        }
        return super.engineGeneratePublic(keySpec);
    }

    public PrivateKey a(PrivateKeyInfo keyInfo) {
        ASN1ObjectIdentifier algOid = keyInfo.g().e();
        if (algOid.equals(CryptoProObjectIdentifiers.l)) {
            return new BCGOST3410PrivateKey(keyInfo);
        }
        throw new IOException("algorithm identifier " + algOid + " in key not recognised");
    }

    public PublicKey b(SubjectPublicKeyInfo keyInfo) {
        ASN1ObjectIdentifier algOid = keyInfo.e().e();
        if (algOid.equals(CryptoProObjectIdentifiers.l)) {
            return new BCGOST3410PublicKey(keyInfo);
        }
        throw new IOException("algorithm identifier " + algOid + " in key not recognised");
    }
}
