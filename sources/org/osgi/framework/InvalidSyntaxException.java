package org.osgi.framework;

public class InvalidSyntaxException extends Exception {
    static final long serialVersionUID = -4295194420816491875L;
    private final String filter;

    public InvalidSyntaxException(String msg, String filter2) {
        super(a(msg, filter2));
        this.filter = filter2;
    }

    public InvalidSyntaxException(String msg, String filter2, Throwable cause) {
        super(a(msg, filter2), cause);
        this.filter = filter2;
    }

    private static String a(String msg, String filter2) {
        if (msg == null || filter2 == null || msg.indexOf(filter2) >= 0) {
            return msg;
        }
        return msg + ": " + filter2;
    }

    public String getFilter() {
        return this.filter;
    }

    public Throwable getCause() {
        return super.getCause();
    }

    public Throwable initCause(Throwable cause) {
        return super.initCause(cause);
    }
}
