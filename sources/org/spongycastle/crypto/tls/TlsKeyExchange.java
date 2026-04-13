package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;

public interface TlsKeyExchange {
    void a(TlsContext tlsContext);

    byte[] b();

    void c(InputStream inputStream);

    void d(Certificate certificate);

    void e(InputStream inputStream);

    void f(TlsCredentials tlsCredentials);

    void g();

    void h(OutputStream outputStream);

    void i(CertificateRequest certificateRequest);

    void j();

    void k(TlsCredentials tlsCredentials);

    byte[] l();

    void m(Certificate certificate);

    void n();
}
