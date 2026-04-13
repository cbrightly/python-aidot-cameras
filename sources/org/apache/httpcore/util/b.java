package org.apache.httpcore.util;

/* compiled from: Asserts */
public class b {
    public static void a(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void b(Object object, String name) {
        if (object == null) {
            throw new IllegalStateException(name + " is null");
        }
    }
}
