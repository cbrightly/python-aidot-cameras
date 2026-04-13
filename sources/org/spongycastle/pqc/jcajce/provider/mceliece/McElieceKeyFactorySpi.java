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
import org.spongycastle.pqc.asn1.McEliecePrivateKey;
import org.spongycastle.pqc.asn1.McEliecePublicKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.spongycastle.pqc.crypto.mceliece.McEliecePublicKeyParameters;

public class McElieceKeyFactorySpi extends KeyFactorySpi implements AsymmetricKeyInfoConverter {
    /* access modifiers changed from: protected */
    public PublicKey engineGeneratePublic(KeySpec keySpec) {
        if (keySpec instanceof X509EncodedKeySpec) {
            try {
                SubjectPublicKeyInfo pki = SubjectPublicKeyInfo.g(ASN1Primitive.h(((X509EncodedKeySpec) keySpec).getEncoded()));
                try {
                    if (PQCObjectIdentifiers.m.equals(pki.e().e())) {
                        McEliecePublicKey key = McEliecePublicKey.f(pki.i());
                        return new BCMcEliecePublicKey(new McEliecePublicKeyParameters(key.g(), key.h(), key.e()));
                    }
                    throw new InvalidKeySpecException("Unable to recognise OID in McEliece public key");
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
                    if (PQCObjectIdentifiers.m.equals(pki.g().e())) {
                        McEliecePrivateKey key = McEliecePrivateKey.g(pki.h());
                        return new BCMcEliecePrivateKey(new McEliecePrivateKeyParameters(key.i(), key.h(), key.e(), key.f(), key.k(), key.n(), key.o()));
                    }
                    throw new InvalidKeySpecException("Unable to recognise OID in McEliece private key");
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
        McEliecePublicKey key = McEliecePublicKey.f(pki.i());
        return new BCMcEliecePublicKey(new McEliecePublicKeyParameters(key.g(), key.h(), key.e()));
    }

    public PrivateKey a(PrivateKeyInfo pki) {
        McEliecePrivateKey key = McEliecePrivateKey.g(pki.h().toASN1Primitive());
        return new BCMcEliecePrivateKey(new McEliecePrivateKeyParameters(key.i(), key.h(), key.e(), key.f(), key.k(), key.n(), key.o()));
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
