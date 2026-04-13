package com.airbnb.lottie.utils;

import com.airbnb.lottie.h0;

/* compiled from: Logger */
public class d {
    private static h0 a = new c();

    public static void a(String message) {
        a.debug(message);
    }

    public static void c(String message) {
        a.a(message);
    }

    public static void d(String message, Throwable exception) {
        a.b(message, exception);
    }

    public static void b(String message, Throwable exception) {
        a.error(message, exception);
    }
}
