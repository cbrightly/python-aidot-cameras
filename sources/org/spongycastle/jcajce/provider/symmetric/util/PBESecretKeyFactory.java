package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.jcajce.provider.symmetric.util.PBE;

public class PBESecretKeyFactory extends BaseSecretKeyFactory implements PBE {
    private boolean f;
    private int q;
    private int x;
    private int y;
    private int z;

    public PBESecretKeyFactory(String algorithm, ASN1ObjectIdentifier oid, boolean forCipher, int scheme, int digest, int keySize, int ivSize) {
        super(algorithm, oid);
        this.f = forCipher;
        this.q = scheme;
        this.x = digest;
        this.y = keySize;
        this.z = ivSize;
    }

    /* access modifiers changed from: protected */
    public SecretKey engineGenerateSecret(KeySpec keySpec) {
        CipherParameters param;
        if (keySpec instanceof PBEKeySpec) {
            PBEKeySpec pbeSpec = (PBEKeySpec) keySpec;
            if (pbeSpec.getSalt() == null) {
                return new BCPBEKey(this.c, this.d, this.q, this.x, this.y, this.z, pbeSpec, (CipherParameters) null);
            }
            if (this.f) {
                param = PBE.Util.f(pbeSpec, this.q, this.x, this.y, this.z);
            } else {
                param = PBE.Util.d(pbeSpec, this.q, this.x, this.y);
            }
            return new BCPBEKey(this.c, this.d, this.q, this.x, this.y, this.z, pbeSpec, param);
        }
        throw new InvalidKeySpecException("Invalid KeySpec");
    }
}
