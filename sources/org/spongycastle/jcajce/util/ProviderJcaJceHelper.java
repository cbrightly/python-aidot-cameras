package org.spongycastle.jcajce.util;

import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.Provider;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;

public class ProviderJcaJceHelper implements JcaJceHelper {
    protected final Provider a;

    public ProviderJcaJceHelper(Provider provider) {
        this.a = provider;
    }

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
