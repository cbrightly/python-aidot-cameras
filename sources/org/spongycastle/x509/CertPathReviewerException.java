package org.spongycastle.x509;

import java.security.cert.CertPath;
import org.spongycastle.i18n.ErrorBundle;
import org.spongycastle.i18n.LocalizedException;

public class CertPathReviewerException extends LocalizedException {
    private CertPath certPath = null;
    private int index = -1;

    public CertPathReviewerException(ErrorBundle errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }

    public CertPathReviewerException(ErrorBundle errorMessage) {
        super(errorMessage);
    }

    public CertPathReviewerException(ErrorBundle errorMessage, Throwable throwable, CertPath certPath2, int index2) {
        super(errorMessage, throwable);
        if (certPath2 == null || index2 == -1) {
            throw new IllegalArgumentException();
        } else if (index2 < -1 || index2 >= certPath2.getCertificates().size()) {
            throw new IndexOutOfBoundsException();
        } else {
            this.certPath = certPath2;
            this.index = index2;
        }
    }

    public CertPathReviewerException(ErrorBundle errorMessage, CertPath certPath2, int index2) {
        super(errorMessage);
        if (certPath2 == null || index2 == -1) {
            throw new IllegalArgumentException();
        } else if (index2 < -1 || index2 >= certPath2.getCertificates().size()) {
            throw new IndexOutOfBoundsException();
        } else {
            this.certPath = certPath2;
            this.index = index2;
        }
    }

    public CertPath getCertPath() {
        return this.certPath;
    }

    public int getIndex() {
        return this.index;
    }
}
