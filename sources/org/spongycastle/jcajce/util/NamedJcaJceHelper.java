package org.spongycastle.jcajce.util;

import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;

public class NamedJcaJceHelper implements JcaJceHelper {
    protected final String a;

    public Cipher a(String algorithm) {
        return Cipher.getInstance(algorithm, this.a);
    }

    public Mac f(String algorithm) {
        return Mac.getInstance(algorithm, this.a);
    }

    public AlgorithmParameters g(String algorithm) {
        return AlgorithmParameters.getInstance(algorithm, this.a);
    }

    public KeyFactory e(String algorithm) {
        return KeyFactory.getInstance(algorithm, this.a);
    }

    public SecretKeyFactory c(String algorithm) {
        return SecretKeyFactory.getInstance(algorithm, this.a);
    }

    public Signature d(String algorithm) {
        return Signature.getInstance(algorithm, this.a);
    }

    public CertificateFactory b(String algorithm) {
        return CertificateFactory.getInstance(algorithm, this.a);
    }
}
