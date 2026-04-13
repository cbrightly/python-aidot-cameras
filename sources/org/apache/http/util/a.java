package org.apache.http.util;

import java.util.Collection;

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

    public static <T> T i(T argument, String name) {
        if (argument != null) {
            return argument;
        }
        throw new IllegalArgumentException(name + " may not be null");
    }

    public static <T extends CharSequence> T e(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        } else if (!j.c(argument)) {
            return argument;
        } else {
            throw new IllegalArgumentException(name + " may not be empty");
        }
    }

    public static <T extends CharSequence> T d(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        } else if (!j.b(argument)) {
            return argument;
        } else {
            throw new IllegalArgumentException(name + " may not be blank");
        }
    }

    public static <T extends CharSequence> T c(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        } else if (!j.a(argument)) {
            return argument;
        } else {
            throw new IllegalArgumentException(name + " may not contain blanks");
        }
    }

    public static <E, T extends Collection<E>> T f(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        } else if (!argument.isEmpty()) {
            return argument;
        } else {
            throw new IllegalArgumentException(name + " may not be empty");
        }
    }

    public static int j(int n, String name) {
        if (n > 0) {
            return n;
        }
        throw new IllegalArgumentException(name + " may not be negative or zero");
    }

    public static int g(int n, String name) {
        if (n >= 0) {
            return n;
        }
        throw new IllegalArgumentException(name + " may not be negative");
    }

    public static long h(long n, String name) {
        if (n >= 0) {
            return n;
        }
        throw new IllegalArgumentException(name + " may not be negative");
    }
}
