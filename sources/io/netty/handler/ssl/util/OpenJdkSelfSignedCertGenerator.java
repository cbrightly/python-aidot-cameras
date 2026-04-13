package io.netty.handler.ssl.util;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Date;
import meshsdk.ConfigUtil;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

public final class OpenJdkSelfSignedCertGenerator {
    static String[] generate(String fqdn, KeyPair keypair, SecureRandom random, Date notBefore, Date notAfter) {
        PrivateKey key = keypair.getPrivate();
        X509CertInfo info = new X509CertInfo();
        X500Name owner = new X500Name("CN=" + fqdn);
        info.set(ConfigUtil.VERSION_FILE, new CertificateVersion(2));
        info.set("serialNumber", new CertificateSerialNumber(new BigInteger(64, random)));
        try {
            info.set("subject", new CertificateSubjectName(owner));
        } catch (CertificateException e) {
            info.set("subject", owner);
        }
        try {
            info.set("issuer", new CertificateIssuerName(owner));
        } catch (CertificateException e2) {
            info.set("issuer", owner);
        }
        info.set("validity", new CertificateValidity(notBefore, notAfter));
        info.set(CacheEntity.KEY, new CertificateX509Key(keypair.getPublic()));
        info.set("algorithmID", new CertificateAlgorithmId(new AlgorithmId(AlgorithmId.sha1WithRSAEncryption_oid)));
        X509CertImpl cert = new X509CertImpl(info);
        cert.sign(key, "SHA1withRSA");
        info.set("algorithmID.algorithm", cert.get("x509.algorithm"));
        X509CertImpl cert2 = new X509CertImpl(info);
        cert2.sign(key, "SHA1withRSA");
        cert2.verify(keypair.getPublic());
        return SelfSignedCertificate.newSelfSignedCertificate(fqdn, key, cert2);
    }

    private OpenJdkSelfSignedCertGenerator() {
    }
}
