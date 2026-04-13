package com.squareup.okhttp.internal.http;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class RouteException extends Exception {
    private static final Method c;
    private IOException lastException;

    static {
        Method m;
        try {
            m = Throwable.class.getDeclaredMethod("addSuppressed", new Class[]{Throwable.class});
        } catch (Exception e) {
            m = null;
        }
        c = m;
    }

    public RouteException(IOException cause) {
        super(cause);
        this.lastException = cause;
    }

    public IOException getLastConnectException() {
        return this.lastException;
    }

    public void addConnectException(IOException e) {
        a(e, this.lastException);
        this.lastException = e;
    }

    private void a(IOException e, IOException suppressed) {
        Method method = c;
        if (method != null) {
            try {
                method.invoke(e, new Object[]{suppressed});
            } catch (IllegalAccessException | InvocationTargetException e2) {
            }
        }
    }
}
