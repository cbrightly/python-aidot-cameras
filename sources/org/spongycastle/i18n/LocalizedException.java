package org.spongycastle.i18n;

import java.util.Locale;

public class LocalizedException extends Exception {
    private Throwable cause;
    protected ErrorBundle message;

    public LocalizedException(ErrorBundle message2) {
        super(message2.e(Locale.getDefault()));
        this.message = message2;
    }

    public LocalizedException(ErrorBundle message2, Throwable throwable) {
        super(message2.e(Locale.getDefault()));
        this.message = message2;
        this.cause = throwable;
    }

    public ErrorBundle getErrorMessage() {
        return this.message;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
