package org.glassfish.grizzly.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class Exceptions {
    public static String getStackTraceAsString(Throwable t) {
        StringWriter stringWriter = new StringWriter(2048);
        PrintWriter pw = new PrintWriter(stringWriter);
        t.printStackTrace(pw);
        pw.close();
        return stringWriter.toString();
    }

    public static IOException makeIOException(Throwable t) {
        if (IOException.class.isAssignableFrom(t.getClass())) {
            return (IOException) t;
        }
        return new IOException(t);
    }

    public static String getAllStackTracesAsString() {
        StringBuilder sb = new StringBuilder(256);
        for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
            sb.append(entry.getKey());
            sb.append(10);
            for (StackTraceElement traceElement : entry.getValue()) {
                sb.append("\tat ");
                sb.append(traceElement);
                sb.append(10);
            }
        }
        return sb.toString();
    }
}
