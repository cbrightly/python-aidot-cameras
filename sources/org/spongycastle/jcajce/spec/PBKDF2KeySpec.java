package org.spongycastle.jcajce.spec;

import javax.crypto.spec.PBEKeySpec;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class PBKDF2KeySpec extends PBEKeySpec {
    private static final AlgorithmIdentifier c = new AlgorithmIdentifier(PKCSObjectIdentifiers.u0, DERNull.c);
    private AlgorithmIdentifier d;

    public PBKDF2KeySpec(char[] password, byte[] salt, int iterationCount, int keySize, AlgorithmIdentifier prf) {
        super(password, salt, iterationCount, keySize);
        this.d = prf;
    }

    public AlgorithmIdentifier a() {
        return this.d;
    }
}
