package jakarta.xml.bind;

import java.io.PrintStream;

public class TypeConstraintException extends RuntimeException {
    static final long serialVersionUID = -3059799699420143848L;
    private String errorCode;
    private volatile Throwable linkedException;

    public TypeConstraintException(String message) {
        this(message, (String) null, (Throwable) null);
    }

    public TypeConstraintException(String message, String errorCode2) {
        this(message, errorCode2, (Throwable) null);
    }

    public TypeConstraintException(Throwable exception) {
        this((String) null, (String) null, exception);
    }

    public TypeConstraintException(String message, Throwable exception) {
        this(message, (String) null, exception);
    }

    public TypeConstraintException(String message, String errorCode2, Throwable exception) {
        super(message);
        this.errorCode = errorCode2;
        this.linkedException = exception;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public Throwable getLinkedException() {
        return this.linkedException;
    }

    public void setLinkedException(Throwable exception) {
        this.linkedException = exception;
    }

    public String toString() {
        if (this.linkedException == null) {
            return super.toString();
        }
        return super.toString() + "\n - with linked exception:\n[" + this.linkedException.toString() + "]";
    }

    public void printStackTrace(PrintStream s) {
        if (this.linkedException != null) {
            this.linkedException.printStackTrace(s);
            s.println("--------------- linked to ------------------");
        }
        super.printStackTrace(s);
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }
}
