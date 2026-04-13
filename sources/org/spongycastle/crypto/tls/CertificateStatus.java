package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.asn1.ocsp.OCSPResponse;

public class CertificateStatus {
    protected short a;
    protected Object b;

    public CertificateStatus(short statusType, Object response) {
        if (b(statusType, response)) {
            this.a = statusType;
            this.b = response;
            return;
        }
        throw new IllegalArgumentException("'response' is not an instance of the correct type");
    }

    public void a(OutputStream output) {
        TlsUtils.L0(this.a, output);
        switch (this.a) {
            case 1:
                TlsUtils.B0(((OCSPResponse) this.b).getEncoded("DER"), output);
                return;
            default:
                throw new TlsFatalAlert(80);
        }
    }

    public static CertificateStatus c(InputStream input) {
        short status_type = TlsUtils.q0(input);
        switch (status_type) {
            case 1:
                return new CertificateStatus(status_type, OCSPResponse.e(TlsUtils.e0(TlsUtils.h0(input))));
            default:
                throw new TlsFatalAlert(50);
        }
    }

    protected static boolean b(short statusType, Object response) {
        switch (statusType) {
            case 1:
                return response instanceof OCSPResponse;
            default:
                throw new IllegalArgumentException("'statusType' is an unsupported CertificateStatusType");
        }
    }
}
