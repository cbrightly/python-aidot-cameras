package io.ktor.http;

import io.ktor.http.m;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContentDisposition.kt */
public final class b extends m {
    @NotNull
    private static final b d = new b("file", (List) null, 2, (DefaultConstructorMarker) null);
    @NotNull
    private static final b e = new b("mixed", (List) null, 2, (DefaultConstructorMarker) null);
    @NotNull
    private static final b f = new b("attachment", (List) null, 2, (DefaultConstructorMarker) null);
    @NotNull
    private static final b g = new b("inline", (List) null, 2, (DefaultConstructorMarker) null);
    public static final a h = new a((DefaultConstructorMarker) null);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public b(@NotNull String disposition, @NotNull List<l> parameters) {
        super(disposition, parameters);
        k.f(disposition, "disposition");
        k.f(parameters, "parameters");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ b(String str, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? q.g() : list);
    }

    @NotNull
    public final String d() {
        return a();
    }

    @Nullable
    public final String e() {
        return c("name");
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof b) && k.a(d(), ((b) other).d()) && k.a(b(), ((b) other).b());
    }

    public int hashCode() {
        return (d().hashCode() * 31) + b().hashCode();
    }

    /* compiled from: ContentDisposition.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final b a(@NotNull String value) {
            k.f(value, "value");
            m.a aVar = m.a;
            k headerValue$iv = (k) y.q0(r.c(value));
            return new b(headerValue$iv.d(), headerValue$iv.b());
        }
    }
}
