package io.ktor.utils.io.core.internal;

/* compiled from: UTF8.kt */
public final class c {
    public static int a(int value) {
        return value;
    }

    public static int b(short characters, short bytes) {
        return a((65535 & bytes) | ((characters & 65535) << 16));
    }
}
