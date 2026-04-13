package org.glassfish.grizzly.utils;

public class DebugPoint {
    private final Exception stackTrace;
    private final String threadName;

    public DebugPoint(Exception stackTrace2, String threadName2) {
        this.stackTrace = stackTrace2;
        this.threadName = threadName2;
    }

    public Exception getStackTrace() {
        return this.stackTrace;
    }

    public String getThreadName() {
        return this.threadName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(512);
        sb.append("Point [current-thread=");
        sb.append(Thread.currentThread().getName());
        sb.append(", debug-point-thread=");
        sb.append(this.threadName);
        sb.append(", stackTrace=\n");
        StackTraceElement[] trace = this.stackTrace.getStackTrace();
        for (StackTraceElement append : trace) {
            sb.append("\tat ");
            sb.append(append);
            sb.append(10);
        }
        return sb.toString();
    }
}
