package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
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
import org.spongycastle.pqc.asn1.McElieceCCA2PrivateKey;
import org.spongycastle.pqc.asn1.McElieceCCA2PublicKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2PublicKeyParameters;

public class McElieceCCA2KeyFactorySpi extends KeyFactorySpi implements AsymmetricKeyInfoConverter {
    /* access modifiers changed from: protected */
    public PublicKey engineGeneratePublic(KeySpec keySpec) {
        if (keySpec instanceof X509EncodedKeySpec) {
            try {
                SubjectPublicKeyInfo pki = SubjectPublicKeyInfo.g(ASN1Primitive.h(((X509EncodedKeySpec) keySpec).getEncoded()));
                try {
                    if (PQCObjectIdentifiers.n.equals(pki.e().e())) {
                        McElieceCCA2PublicKey key = McElieceCCA2PublicKey.g(pki.i());
                        return new BCMcElieceCCA2PublicKey(new McElieceCCA2PublicKeyParameters(key.h(), key.i(), key.f(), Utils.b(key.e()).b()));
                    }
                    throw new InvalidKeySpecException("Unable to recognise OID in McEliece private key");
                } catch (IOException cce) {
                    throw new InvalidKeySpecException("Unable to decode X509EncodedKeySpec: " + cce.getMessage());
                }
            } catch (IOException e) {
                throw new InvalidKeySpecException(e.toString());
            }
        } else {
            throw new InvalidKeySpecException("Unsupported key specification: " + keySpec.getClass() + ".");
        }
    }

    /* access modifiers changed from: protected */
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) {
        if (keySpec instanceof PKCS8EncodedKeySpec) {
            try {
                PrivateKeyInfo pki = PrivateKeyInfo.f(ASN1Primitive.h(((PKCS8EncodedKeySpec) keySpec).getEncoded()));
                try {
                    if (PQCObjectIdentifiers.n.equals(pki.g().e())) {
                        McElieceCCA2PrivateKey key = McElieceCCA2PrivateKey.h(pki.h());
                        return new BCMcElieceCCA2PrivateKey(new McElieceCCA2PrivateKeyParameters(key.k(), key.i(), key.f(), key.g(), key.n(), Utils.b(key.e()).b()));
                    }
                    throw new InvalidKeySpecException("Unable to recognise OID in McEliece public key");
                } catch (IOException e) {
                    throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec.");
                }
            } catch (IOException e2) {
                throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec: " + e2);
            }
        } else {
            throw new InvalidKeySpecException("Unsupported key specification: " + keySpec.getClass() + ".");
        }
    }

    public PublicKey b(SubjectPublicKeyInfo pki) {
        McElieceCCA2PublicKey key = McElieceCCA2PublicKey.g(pki.i());
        return new BCMcElieceCCA2PublicKey(new McElieceCCA2PublicKeyParameters(key.h(), key.i(), key.f(), Utils.b(key.e()).b()));
    }

    public PrivateKey a(PrivateKeyInfo pki) {
        McElieceCCA2PrivateKey key = McElieceCCA2PrivateKey.h(pki.h().toASN1Primitive());
        return new BCMcElieceCCA2PrivateKey(new McElieceCCA2PrivateKeyParameters(key.k(), key.i(), key.f(), key.g(), key.n(), (String) null));
    }

    /* access modifiers changed from: protected */
    public KeySpec engineGetKeySpec(Key key, Class tClass) {
        return null;
    }

    /* access modifiers changed from: protected */
    public Key engineTranslateKey(Key key) {
        return null;
    }
}
