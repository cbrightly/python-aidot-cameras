package org.spongycastle.jce;

public class ECKeyUtil {

    public static class UnexpectedException extends RuntimeException {
        private Throwable cause;

        UnexpectedException(Throwable cause2) {
            super(cause2.toString());
            this.cause = cause2;
        }

        public Throwable getCause() {
            return this.cause;
        }
    }
}
