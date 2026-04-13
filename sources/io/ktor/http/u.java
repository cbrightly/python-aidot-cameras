package io.ktor.http;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpMethod.kt */
public final class u {
    /* access modifiers changed from: private */
    @NotNull
    public static final u a;
    /* access modifiers changed from: private */
    @NotNull
    public static final u b;
    /* access modifiers changed from: private */
    @NotNull
    public static final u c;
    /* access modifiers changed from: private */
    @NotNull
    public static final u d;
    /* access modifiers changed from: private */
    @NotNull
    public static final u e;
    /* access modifiers changed from: private */
    @NotNull
    public static final u f;
    /* access modifiers changed from: private */
    @NotNull
    public static final u g;
    /* access modifiers changed from: private */
    @NotNull
    public static final List<u> h;
    public static final a i = new a((DefaultConstructorMarker) null);
    @NotNull
    private final String j;

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            return (obj instanceof u) && k.a(this.j, ((u) obj).j);
        }
        return true;
    }

    public int hashCode() {
        String str = this.j;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    @NotNull
    public String toString() {
        return "HttpMethod(value=" + this.j + ")";
    }

    public u(@NotNull String value) {
        k.f(value, "value");
        this.j = value;
    }

    @NotNull
    public final String i() {
        return this.j;
    }

    /* compiled from: HttpMethod.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final u c() {
            return u.a;
        }

        @NotNull
        public final u g() {
            return u.b;
        }

        @NotNull
        public final u h() {
            return u.c;
        }

        @NotNull
        public final u f() {
            return u.d;
        }

        @NotNull
        public final u b() {
            return u.e;
        }

        @NotNull
        public final u d() {
            return u.f;
        }

        @NotNull
        public final u e() {
            return u.g;
        }

        @NotNull
        public final u i(@NotNull String method) {
            k.f(method, FirebaseAnalytics.Param.METHOD);
            if (k.a(method, c().i())) {
                return c();
            }
            if (k.a(method, g().i())) {
                return g();
            }
            if (k.a(method, h().i())) {
                return h();
            }
            if (k.a(method, f().i())) {
                return f();
            }
            if (k.a(method, b().i())) {
                return b();
            }
            if (k.a(method, d().i())) {
                return d();
            }
            if (k.a(method, e().i())) {
                return e();
            }
            return new u(method);
        }

        @NotNull
        public final List<u> a() {
            return u.h;
        }
    }

    static {
        u uVar = new u(Constants.GET);
        a = uVar;
        u uVar2 = new u(Constants.POST);
        b = uVar2;
        u uVar3 = new u("PUT");
        c = uVar3;
        u uVar4 = new u("PATCH");
        d = uVar4;
        u uVar5 = new u("DELETE");
        e = uVar5;
        u uVar6 = new u(Constants.HEAD);
        f = uVar6;
        u uVar7 = new u("OPTIONS");
        g = uVar7;
        h = q.j(uVar, uVar2, uVar3, uVar4, uVar5, uVar6, uVar7);
    }
}
