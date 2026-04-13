package io.ktor.http.content;

import io.ktor.http.o;
import io.ktor.http.s;
import io.ktor.utils.io.core.w;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Multipart.kt */
public abstract class k {
    @Nullable
    private final g a;
    @Nullable
    private final g b;
    @NotNull
    private final kotlin.jvm.functions.a<x> c;
    @NotNull
    private final o d;

    @Nullable
    public final io.ktor.http.b a() {
        return (io.ktor.http.b) this.a.getValue();
    }

    private k(kotlin.jvm.functions.a<x> dispose, o headers) {
        this.c = dispose;
        this.d = headers;
        kotlin.k kVar = kotlin.k.NONE;
        this.a = i.a(kVar, new c(this));
        this.b = i.a(kVar, new d(this));
    }

    public /* synthetic */ k(kotlin.jvm.functions.a dispose, o headers, DefaultConstructorMarker $constructor_marker) {
        this(dispose, headers);
    }

    @NotNull
    public final kotlin.jvm.functions.a<x> b() {
        return this.c;
    }

    @NotNull
    public final o c() {
        return this.d;
    }

    /* compiled from: Multipart.kt */
    public static final class b extends k {
        @NotNull
        private final String e;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull String value, @NotNull kotlin.jvm.functions.a<x> dispose, @NotNull o partHeaders) {
            super(dispose, partHeaders, (DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.f(value, "value");
            kotlin.jvm.internal.k.f(dispose, "dispose");
            kotlin.jvm.internal.k.f(partHeaders, "partHeaders");
            this.e = value;
        }

        @NotNull
        public final String e() {
            return this.e;
        }
    }

    /* compiled from: Multipart.kt */
    public static final class a extends k {
        @Nullable
        private final String e;
        @NotNull
        private final kotlin.jvm.functions.a<w> f;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull kotlin.jvm.functions.a<? extends w> provider, @NotNull kotlin.jvm.functions.a<x> dispose, @NotNull o partHeaders) {
            super(dispose, partHeaders, (DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.f(provider, "provider");
            kotlin.jvm.internal.k.f(dispose, "dispose");
            kotlin.jvm.internal.k.f(partHeaders, "partHeaders");
            String str = null;
            this.f = provider;
            io.ktor.http.b a = a();
            this.e = a != null ? a.c("filename") : str;
        }

        @NotNull
        public final kotlin.jvm.functions.a<w> f() {
            return this.f;
        }

        @Nullable
        public final String e() {
            return this.e;
        }
    }

    /* compiled from: Multipart.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<io.ktor.http.b> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(k kVar) {
            super(0);
            this.this$0 = kVar;
        }

        @Nullable
        public final io.ktor.http.b invoke() {
            String it = this.this$0.c().get(s.V0.p());
            if (it != null) {
                return io.ktor.http.b.h.a(it);
            }
            return null;
        }
    }

    /* compiled from: Multipart.kt */
    public static final class d extends l implements kotlin.jvm.functions.a<io.ktor.http.c> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(k kVar) {
            super(0);
            this.this$0 = kVar;
        }

        @Nullable
        public final io.ktor.http.c invoke() {
            String it = this.this$0.c().get(s.V0.s());
            if (it != null) {
                return io.ktor.http.c.e.b(it);
            }
            return null;
        }
    }

    @Nullable
    public final String d() {
        io.ktor.http.b a2 = a();
        if (a2 != null) {
            return a2.e();
        }
        return null;
    }
}
