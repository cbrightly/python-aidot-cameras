package org.junit.internal;

/* compiled from: Throwables */
public final class a {
    public static Exception b(Throwable e) {
        a(e);
        return null;
    }

    private static <T extends Throwable> void a(Throwable e) {
        throw e;
    }
}
