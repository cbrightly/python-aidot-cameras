package org.spongycastle.jcajce.util;

import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;

public class DefaultJcaJceHelper implements JcaJceHelper {
    public Cipher a(String algorithm) {
        return Cipher.getInstance(algorithm);
    }

    public Mac f(String algorithm) {
        return Mac.getInstance(algorithm);
    }

    public AlgorithmParameters g(String algorithm) {
        return AlgorithmParameters.getInstance(algorithm);
    }

    public KeyFactory e(String algorithm) {
        return KeyFactory.getInstance(algorithm);
    }

    public SecretKeyFactory c(String algorithm) {
        return SecretKeyFactory.getInstance(algorithm);
    }

    public Signature d(String algorithm) {
        return Signature.getInstance(algorithm);
    }

    public CertificateFactory b(String algorithm) {
        return CertificateFactory.getInstance(algorithm);
    }
}
