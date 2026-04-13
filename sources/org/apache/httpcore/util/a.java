package org.apache.httpcore.util;

/* compiled from: Args */
public class a {
    public static void a(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void b(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }

    public static <T> T g(T argument, String name) {
        if (argument != null) {
            return argument;
        }
        throw new IllegalArgumentException(name + " may not be null");
    }

    public static <T extends CharSequence> T d(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        } else if (!i.b(argument)) {
            return argument;
        } else {
            throw new IllegalArgumentException(name + " may not be empty");
        }
    }

    public static <T extends CharSequence> T c(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        } else if (!i.a(argument)) {
            return argument;
        } else {
            throw new IllegalArgumentException(name + " may not be blank");
        }
    }

    public static int h(int n, String name) {
        if (n > 0) {
            return n;
        }
        throw new IllegalArgumentException(name + " may not be negative or zero");
    }

    public static int e(int n, String name) {
        if (n >= 0) {
            return n;
        }
        throw new IllegalArgumentException(name + " may not be negative");
    }

    public static long f(long n, String name) {
        if (n >= 0) {
            return n;
        }
        throw new IllegalArgumentException(name + " may not be negative");
    }
}
