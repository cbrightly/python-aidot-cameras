package com.yanzhenjie.andserver.util;

import android.text.TextUtils;

/* compiled from: Assert */
public abstract class a {
    public static void d(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void b(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void c(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void a(String text, String message) {
        if (TextUtils.isEmpty(text)) {
            throw new IllegalArgumentException(message);
        }
    }
}
