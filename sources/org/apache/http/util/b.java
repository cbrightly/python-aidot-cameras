package org.apache.http.util;

/* compiled from: Asserts */
public class b {
    public static void a(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void b(boolean expression, String message, Object arg) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, new Object[]{arg}));
        }
    }

    public static void c(Object object, String name) {
        if (object == null) {
            throw new IllegalStateException(name + " is null");
        }
    }
}
