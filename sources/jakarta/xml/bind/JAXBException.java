package jakarta.xml.bind;

import java.io.PrintStream;
import java.io.PrintWriter;

public class JAXBException extends Exception {
    static final long serialVersionUID = -5621384651494307979L;
    private String errorCode;
    private volatile Throwable linkedException;

    public JAXBException(String message) {
        this(message, (String) null, (Throwable) null);
    }

    public JAXBException(String message, String errorCode2) {
        this(message, errorCode2, (Throwable) null);
    }

    public JAXBException(Throwable exception) {
        this((String) null, (String) null, exception);
    }

    public JAXBException(String message, Throwable exception) {
        this(message, (String) null, exception);
    }

    public JAXBException(String message, String errorCode2, Throwable exception) {
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
        super.printStackTrace(s);
    }

    public void printStackTrace() {
        super.printStackTrace();
    }

    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    public Throwable getCause() {
        return this.linkedException;
    }
}
