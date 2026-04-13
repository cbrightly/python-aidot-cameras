package org.spongycastle.jcajce.util;

import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;

public interface JcaJceHelper {
    Cipher a(String str);

    CertificateFactory b(String str);

    SecretKeyFactory c(String str);

    Signature d(String str);

    KeyFactory e(String str);

    Mac f(String str);

    AlgorithmParameters g(String str);
}
