package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public class BaseSecretKeyFactory extends SecretKeyFactorySpi implements PBE {
    protected String c;
    protected ASN1ObjectIdentifier d;

    protected BaseSecretKeyFactory(String algName, ASN1ObjectIdentifier algOid) {
        this.c = algName;
        this.d = algOid;
    }

    /* access modifiers changed from: protected */
    public SecretKey engineGenerateSecret(KeySpec keySpec) {
        if (keySpec instanceof SecretKeySpec) {
            return new SecretKeySpec(((SecretKeySpec) keySpec).getEncoded(), this.c);
        }
        throw new InvalidKeySpecException("Invalid KeySpec");
    }

    /* access modifiers changed from: protected */
    public KeySpec engineGetKeySpec(SecretKey key, Class keySpec) {
        if (keySpec == null) {
            throw new InvalidKeySpecException("keySpec parameter is null");
        } else if (key == null) {
            throw new InvalidKeySpecException("key parameter is null");
        } else if (SecretKeySpec.class.isAssignableFrom(keySpec)) {
            return new SecretKeySpec(key.getEncoded(), this.c);
        } else {
            try {
                return (KeySpec) keySpec.getConstructor(new Class[]{byte[].class}).newInstance(new Object[]{key.getEncoded()});
            } catch (Exception e) {
                throw new InvalidKeySpecException(e.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public SecretKey engineTranslateKey(SecretKey key) {
        if (key == null) {
            throw new InvalidKeyException("key parameter is null");
        } else if (key.getAlgorithm().equalsIgnoreCase(this.c)) {
            return new SecretKeySpec(key.getEncoded(), this.c);
        } else {
            throw new InvalidKeyException("Key not of type " + this.c + ".");
        }
    }
}
