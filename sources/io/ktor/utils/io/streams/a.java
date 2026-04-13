package io.ktor.utils.io.streams;

import io.ktor.utils.io.pool.b;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ByteArrays.kt */
public final class a {
    @NotNull
    private static final b<byte[]> a = new C0293a(128);

    /* renamed from: io.ktor.utils.io.streams.a$a  reason: collision with other inner class name */
    /* compiled from: ByteArrays.kt */
    public static final class C0293a extends b<byte[]> {
        C0293a(int $super_call_param$0) {
            super($super_call_param$0);
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: s */
        public final byte[] l() {
            return new byte[4096];
        }

        /* access modifiers changed from: protected */
        /* renamed from: t */
        public final void r(@NotNull byte[] instance) {
            k.f(instance, "instance");
            if (instance.length == 4096) {
                super.r(instance);
                return;
            }
            throw new IllegalArgumentException(("Unable to recycle buffer of wrong size: " + instance.length + " != 4096").toString());
        }
    }

    @NotNull
    public static final b<byte[]> a() {
        return a;
    }
}
