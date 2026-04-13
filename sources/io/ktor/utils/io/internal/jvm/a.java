package io.ktor.utils.io.internal.jvm;

import org.jetbrains.annotations.NotNull;

/* compiled from: Errors.kt */
public final class a {
    @NotNull
    public static final Void a(int delta, int size) {
        throw new IllegalStateException("Wrong buffer position change: " + delta + ". " + "Position should be moved forward only by at most size bytes (size = " + size + ')');
    }
}
