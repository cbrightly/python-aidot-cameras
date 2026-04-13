package io.netty.handler.ssl;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

public final class OpenSslX509Certificate extends X509Certificate {
    private final byte[] bytes;
    private X509Certificate wrapped;

    public OpenSslX509Certificate(byte[] bytes2) {
        this.bytes = bytes2;
    }

    public void checkValidity() {
        unwrap().checkValidity();
    }

    public void checkValidity(Date date) {
        unwrap().checkValidity(date);
    }

    public int getVersion() {
        return unwrap().getVersion();
    }

    public BigInteger getSerialNumber() {
        return unwrap().getSerialNumber();
    }

    public Principal getIssuerDN() {
        return unwrap().getIssuerDN();
    }

    public Principal getSubjectDN() {
        return unwrap().getSubjectDN();
    }

    public Date getNotBefore() {
        return unwrap().getNotBefore();
    }

    public Date getNotAfter() {
        return unwrap().getNotAfter();
    }

    public byte[] getTBSCertificate() {
        return unwrap().getTBSCertificate();
    }

    public byte[] getSignature() {
        return unwrap().getSignature();
    }

    public String getSigAlgName() {
        return unwrap().getSigAlgName();
    }

    public String getSigAlgOID() {
        return unwrap().getSigAlgOID();
    }

    public byte[] getSigAlgParams() {
        return unwrap().getSigAlgParams();
    }

    public boolean[] getIssuerUniqueID() {
        return unwrap().getIssuerUniqueID();
    }

    public boolean[] getSubjectUniqueID() {
        return unwrap().getSubjectUniqueID();
    }

    public boolean[] getKeyUsage() {
        return unwrap().getKeyUsage();
    }

    public int getBasicConstraints() {
        return unwrap().getBasicConstraints();
    }

    public byte[] getEncoded() {
        return (byte[]) this.bytes.clone();
    }

    public void verify(PublicKey key) {
        unwrap().verify(key);
    }

    public void verify(PublicKey key, String sigProvider) {
        unwrap().verify(key, sigProvider);
    }

    public String toString() {
        return unwrap().toString();
    }

    public PublicKey getPublicKey() {
        return unwrap().getPublicKey();
    }

    public boolean hasUnsupportedCriticalExtension() {
        return unwrap().hasUnsupportedCriticalExtension();
    }

    public Set<String> getCriticalExtensionOIDs() {
        return unwrap().getCriticalExtensionOIDs();
    }

    public Set<String> getNonCriticalExtensionOIDs() {
        return unwrap().getNonCriticalExtensionOIDs();
    }

    public byte[] getExtensionValue(String oid) {
        return unwrap().getExtensionValue(oid);
    }

    private X509Certificate unwrap() {
        X509Certificate wrapped2 = this.wrapped;
        if (wrapped2 != null) {
            return wrapped2;
        }
        try {
            X509Certificate wrapped3 = (X509Certificate) SslContext.X509_CERT_FACTORY.generateCertificate(new ByteArrayInputStream(this.bytes));
            this.wrapped = wrapped3;
            return wrapped3;
        } catch (CertificateException e) {
            throw new IllegalStateException(e);
        }
    }
}
