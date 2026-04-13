package io.ktor.application;

import io.ktor.util.internal.c;
import io.ktor.util.l;
import kotlin.TypeCastException;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.f1;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationEvents.kt */
public final class e {
    private final l<j<?>, io.ktor.util.internal.a> a = new l<>();

    /* compiled from: ApplicationEvents.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<j<?>, io.ktor.util.internal.a> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @NotNull
        public final io.ktor.util.internal.a invoke(@NotNull j<?> it) {
            k.f(it, "it");
            return new io.ktor.util.internal.a();
        }
    }

    @NotNull
    public final <T> f1 b(@NotNull j<T> definition, @NotNull kotlin.jvm.functions.l<? super T, x> handler) {
        k.f(definition, "definition");
        k.f(handler, "handler");
        a registration = new a(handler);
        this.a.a(definition, b.INSTANCE).a(registration);
        return registration;
    }

    public final <T> void c(@NotNull j<T> definition, @NotNull kotlin.jvm.functions.l<? super T, x> handler) {
        k.f(definition, "definition");
        k.f(handler, "handler");
        io.ktor.util.internal.a this_$iv = this.a.b(definition);
        if (this_$iv != null) {
            Object h = this_$iv.h();
            if (h != null) {
                for (c cur$iv = (c) h; !k.a(cur$iv, this_$iv); cur$iv = cur$iv.i()) {
                    if (cur$iv instanceof a) {
                        a it = (a) cur$iv;
                        if (k.a(it.q(), handler)) {
                            it.n();
                        }
                    }
                }
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.internal.Node /* = io.ktor.util.internal.LockFreeLinkedListNode */");
        }
    }

    public final <T> void a(@NotNull j<T> definition, T value) {
        k.f(definition, "definition");
        Throwable it = null;
        io.ktor.util.internal.a this_$iv = this.a.b(definition);
        if (this_$iv != null) {
            Object h = this_$iv.h();
            if (h != null) {
                for (c cur$iv = (c) h; !k.a(cur$iv, this_$iv); cur$iv = cur$iv.i()) {
                    if (cur$iv instanceof a) {
                        try {
                            kotlin.jvm.functions.l<?, x> q = ((a) cur$iv).q();
                            if (q != null) {
                                ((kotlin.jvm.functions.l) e0.e(q, 1)).invoke(value);
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type io.ktor.application.EventHandler<T> /* = (T) -> kotlin.Unit */");
                            }
                        } catch (Throwable e) {
                            if (it != null) {
                                it.addSuppressed(e);
                            } else {
                                it = e;
                            }
                        }
                    }
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.internal.Node /* = io.ktor.util.internal.LockFreeLinkedListNode */");
            }
        }
        if (it != null) {
            throw it;
        }
    }

    /* compiled from: ApplicationEvents.kt */
    public static final class a extends c implements f1 {
        @NotNull
        private final kotlin.jvm.functions.l<?, x> q;

        public a(@NotNull kotlin.jvm.functions.l<?, x> handler) {
            k.f(handler, "handler");
            this.q = handler;
        }

        @NotNull
        public final kotlin.jvm.functions.l<?, x> q() {
            return this.q;
        }

        public void dispose() {
            n();
        }
    }
}
