package org.spongycastle.x509;

import java.math.BigInteger;
import java.security.cert.X509Extension;
import java.util.Date;

public interface X509AttributeCertificate extends X509Extension {
    AttributeCertificateHolder a();

    X509Attribute[] b(String str);

    AttributeCertificateIssuer c();

    void checkValidity(Date date);

    byte[] getEncoded();

    Date getNotAfter();

    BigInteger getSerialNumber();
}
