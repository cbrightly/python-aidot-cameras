package com.bumptech.glide.util;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Collection;

/* compiled from: Preconditions */
public final class i {
    public static void a(boolean expression, @NonNull String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    @NonNull
    public static <T> T d(@Nullable T arg) {
        return e(arg, "Argument must not be null");
    }

    @NonNull
    public static <T> T e(@Nullable T arg, @NonNull String message) {
        if (arg != null) {
            return arg;
        }
        throw new NullPointerException(message);
    }

    @NonNull
    public static String b(@Nullable String string) {
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        throw new IllegalArgumentException("Must not be null or empty");
    }

    @NonNull
    public static <T extends Collection<Y>, Y> T c(@NonNull T collection) {
        if (!collection.isEmpty()) {
            return collection;
        }
        throw new IllegalArgumentException("Must not be empty.");
    }
}
