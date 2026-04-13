package org.glassfish.grizzly;

import java.io.IOException;

public class CloseReason {
    private static final IOException LOCALLY_CLOSED;
    public static final CloseReason LOCALLY_CLOSED_REASON;
    private static final IOException REMOTELY_CLOSED;
    public static final CloseReason REMOTELY_CLOSED_REASON;
    private final IOException cause;
    private final CloseType type;

    static {
        IOException iOException = new IOException("Locally closed");
        LOCALLY_CLOSED = iOException;
        iOException.setStackTrace(new StackTraceElement[0]);
        IOException iOException2 = new IOException("Remotely closed");
        REMOTELY_CLOSED = iOException2;
        iOException2.setStackTrace(new StackTraceElement[0]);
        LOCALLY_CLOSED_REASON = new CloseReason(CloseType.LOCALLY, iOException);
        REMOTELY_CLOSED_REASON = new CloseReason(CloseType.REMOTELY, iOException2);
    }

    public CloseReason(CloseType type2, IOException cause2) {
        this.type = type2;
        this.cause = cause2 != null ? cause2 : type2 == CloseType.LOCALLY ? LOCALLY_CLOSED : REMOTELY_CLOSED;
    }

    public CloseType getType() {
        return this.type;
    }

    public IOException getCause() {
        return this.cause;
    }

    public String toString() {
        return super.toString() + "[type=" + getType() + ", cause=" + getCause() + "]";
    }
}
