package org.apache.commons.lang3.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/* compiled from: ExceptionUtils */
public class d {
    private static final String[] a = {"getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", "getLinkedCause", "getThrowable"};

    public static String a(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
}
