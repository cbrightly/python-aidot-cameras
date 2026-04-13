package io.ktor.http.cio.internals;

import io.ktor.utils.io.pool.b;
import io.ktor.utils.io.pool.d;
import org.jetbrains.annotations.NotNull;

/* compiled from: CharArrayPool.kt */
public final class c {
    @NotNull
    private static final d<char[]> a = new a(4096);

    /* compiled from: CharArrayPool.kt */
    public static final class a extends b<char[]> {
        a(int $super_call_param$0) {
            super($super_call_param$0);
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: s */
        public char[] l() {
            return new char[2048];
        }
    }

    @NotNull
    public static final d<char[]> a() {
        return a;
    }
}
