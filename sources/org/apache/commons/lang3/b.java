package org.apache.commons.lang3;

/* compiled from: Validate */
public class b {
    public static void a(boolean expression, String message, Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }
}
