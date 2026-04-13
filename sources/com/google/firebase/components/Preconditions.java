package com.google.firebase.components;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

public final class Preconditions {
    public static void checkArgument(boolean expression, String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException();
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T reference, String errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(errorMessage);
    }

    public static void checkState(boolean expression, String errorMesage) {
        if (!expression) {
            throw new IllegalStateException(errorMesage);
        }
    }
}
