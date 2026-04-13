package org.spongycastle.crypto.tls;

public interface TlsAuthentication {
    TlsCredentials a(CertificateRequest certificateRequest);

    void b(Certificate certificate);
}
