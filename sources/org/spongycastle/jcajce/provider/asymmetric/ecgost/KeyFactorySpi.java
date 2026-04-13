package org.spongycastle.jcajce.provider.asymmetric.ecgost;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.KeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECParameterSpec;

public class KeyFactorySpi extends BaseKeyFactorySpi {
    /* access modifiers changed from: protected */
    public KeySpec engineGetKeySpec(Key key, Class spec) {
        if (spec.isAssignableFrom(ECPublicKeySpec.class) && (key instanceof ECPublicKey)) {
            ECPublicKey k = (ECPublicKey) key;
            if (k.getParams() != null) {
                return new ECPublicKeySpec(k.getW(), k.getParams());
            }
            ECParameterSpec implicitSpec = BouncyCastleProvider.CONFIGURATION.b();
            return new ECPublicKeySpec(k.getW(), EC5Util.f(EC5Util.a(implicitSpec.a(), implicitSpec.e()), implicitSpec));
        } else if (spec.isAssignableFrom(ECPrivateKeySpec.class) && (key instanceof ECPrivateKey)) {
            ECPrivateKey k2 = (ECPrivateKey) key;
            if (k2.getParams() != null) {
                return new ECPrivateKeySpec(k2.getS(), k2.getParams());
            }
            ECParameterSpec implicitSpec2 = BouncyCastleProvider.CONFIGURATION.b();
            return new ECPrivateKeySpec(k2.getS(), EC5Util.f(EC5Util.a(implicitSpec2.a(), implicitSpec2.e()), implicitSpec2));
        } else if (spec.isAssignableFrom(org.spongycastle.jce.spec.ECPublicKeySpec.class) && (key instanceof ECPublicKey)) {
            ECPublicKey k3 = (ECPublicKey) key;
            if (k3.getParams() != null) {
                return new org.spongycastle.jce.spec.ECPublicKeySpec(EC5Util.d(k3.getParams(), k3.getW(), false), EC5Util.g(k3.getParams(), false));
            }
            return new org.spongycastle.jce.spec.ECPublicKeySpec(EC5Util.d(k3.getParams(), k3.getW(), false), BouncyCastleProvider.CONFIGURATION.b());
        } else if (!spec.isAssignableFrom(org.spongycastle.jce.spec.ECPrivateKeySpec.class) || !(key instanceof ECPrivateKey)) {
            return super.engineGetKeySpec(key, spec);
        } else {
            ECPrivateKey k4 = (ECPrivateKey) key;
            if (k4.getParams() != null) {
                return new org.spongycastle.jce.spec.ECPrivateKeySpec(k4.getS(), EC5Util.g(k4.getParams(), false));
            }
            return new org.spongycastle.jce.spec.ECPrivateKeySpec(k4.getS(), BouncyCastleProvider.CONFIGURATION.b());
        }
    }

    /* access modifiers changed from: protected */
    public Key engineTranslateKey(Key key) {
        throw new InvalidKeyException("key type unknown");
    }

    /* access modifiers changed from: protected */
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) {
        if (keySpec instanceof org.spongycastle.jce.spec.ECPrivateKeySpec) {
            return new BCECGOST3410PrivateKey((org.spongycastle.jce.spec.ECPrivateKeySpec) keySpec);
        }
        if (keySpec instanceof ECPrivateKeySpec) {
            return new BCECGOST3410PrivateKey((ECPrivateKeySpec) keySpec);
        }
        return super.engineGeneratePrivate(keySpec);
    }

    /* access modifiers changed from: protected */
    public PublicKey engineGeneratePublic(KeySpec keySpec) {
        if (keySpec instanceof org.spongycastle.jce.spec.ECPublicKeySpec) {
            return new BCECGOST3410PublicKey((org.spongycastle.jce.spec.ECPublicKeySpec) keySpec, BouncyCastleProvider.CONFIGURATION);
        }
        if (keySpec instanceof ECPublicKeySpec) {
            return new BCECGOST3410PublicKey((ECPublicKeySpec) keySpec);
        }
        return super.engineGeneratePublic(keySpec);
    }

    public PrivateKey a(PrivateKeyInfo keyInfo) {
        ASN1ObjectIdentifier algOid = keyInfo.g().e();
        if (algOid.equals(CryptoProObjectIdentifiers.m)) {
            return new BCECGOST3410PrivateKey(keyInfo);
        }
        if (algOid.equals(CryptoProObjectIdentifiers.F)) {
            return new BCECGOST3410PrivateKey(keyInfo);
        }
        if (algOid.equals(CryptoProObjectIdentifiers.E)) {
            return new BCECGOST3410PrivateKey(keyInfo);
        }
        throw new IOException("algorithm identifier " + algOid + " in key not recognised");
    }

    public PublicKey b(SubjectPublicKeyInfo keyInfo) {
        ASN1ObjectIdentifier algOid = keyInfo.e().e();
        if (algOid.equals(CryptoProObjectIdentifiers.m)) {
            return new BCECGOST3410PublicKey(keyInfo);
        }
        if (algOid.equals(CryptoProObjectIdentifiers.F)) {
            return new BCECGOST3410PublicKey(keyInfo);
        }
        if (algOid.equals(CryptoProObjectIdentifiers.E)) {
            return new BCECGOST3410PublicKey(keyInfo);
        }
        throw new IOException("algorithm identifier " + algOid + " in key not recognised");
    }
}
