package io.netty.handler.ssl;

import io.netty.internal.tcnative.CertificateVerifier;
import java.security.cert.CertificateException;

public final class OpenSslCertificateException extends CertificateException {
    private static final long serialVersionUID = 5542675253797129798L;
    private final int errorCode;

    public OpenSslCertificateException(int errorCode2) {
        this((String) null, errorCode2);
    }

    public OpenSslCertificateException(String msg, int errorCode2) {
        super(msg);
        this.errorCode = checkErrorCode(errorCode2);
    }

    public OpenSslCertificateException(String message, Throwable cause, int errorCode2) {
        super(message, cause);
        this.errorCode = checkErrorCode(errorCode2);
    }

    public OpenSslCertificateException(Throwable cause, int errorCode2) {
        this((String) null, cause, errorCode2);
    }

    public int errorCode() {
        return this.errorCode;
    }

    private static int checkErrorCode(int errorCode2) {
        if (CertificateVerifier.isValid(errorCode2)) {
            return errorCode2;
        }
        throw new IllegalArgumentException("errorCode '" + errorCode2 + "' invalid, see https://www.openssl.org/docs/man1.0.2/apps/verify.html.");
    }
}
