package io.ktor.http.cio.internals;

import org.jetbrains.annotations.NotNull;

/* compiled from: MutableRange.kt */
public final class e {
    private int a;
    private int b;

    public e(int start, int end) {
        this.a = start;
        this.b = end;
    }

    public final int a() {
        return this.b;
    }

    public final int b() {
        return this.a;
    }

    public final void c(int i) {
        this.b = i;
    }

    public final void d(int i) {
        this.a = i;
    }

    @NotNull
    public String toString() {
        return "MutableRange(start=" + this.a + ", end=" + this.b + ')';
    }
}
