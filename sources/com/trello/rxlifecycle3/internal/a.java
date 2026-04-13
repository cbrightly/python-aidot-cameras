package com.trello.rxlifecycle3.internal;

/* compiled from: Preconditions */
public final class a {
    public static <T> T a(T value, String message) {
        if (value != null) {
            return value;
        }
        throw new NullPointerException(message);
    }
}
