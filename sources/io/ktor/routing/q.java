package io.ktor.routing;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.http.u;
import io.ktor.util.pipeline.d;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: RoutingBuilder.kt */
public final class q {
    @NotNull
    public static final i d(@NotNull i $this$route, @NotNull String path, @NotNull u method, @NotNull l<? super i, x> build) {
        k.f($this$route, "$this$route");
        k.f(path, "path");
        k.f(method, FirebaseAnalytics.Param.METHOD);
        k.f(build, "build");
        i F = a($this$route, path).F(new a(method));
        build.invoke(F);
        return F;
    }

    /* compiled from: RoutingBuilder.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<i, x> {
        final /* synthetic */ kotlin.jvm.functions.q $body;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(kotlin.jvm.functions.q qVar) {
            super(1);
            this.$body = qVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$route) {
            k.f($this$route, "$receiver");
            $this$route.J(this.$body);
        }
    }

    @NotNull
    public static final i b(@NotNull i $this$get, @NotNull String path, @NotNull kotlin.jvm.functions.q<? super d<x, io.ktor.application.b>, ? super x, ? super kotlin.coroutines.d<? super x>, ? extends Object> body) {
        k.f($this$get, "$this$get");
        k.f(path, "path");
        k.f(body, "body");
        return d($this$get, path, u.i.c(), new a(body));
    }

    /* compiled from: RoutingBuilder.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<i, x> {
        final /* synthetic */ kotlin.jvm.functions.q $body;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(kotlin.jvm.functions.q qVar) {
            super(1);
            this.$body = qVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$route) {
            k.f($this$route, "$receiver");
            $this$route.J(this.$body);
        }
    }

    @NotNull
    public static final i c(@NotNull i $this$post, @NotNull String path, @NotNull kotlin.jvm.functions.q<? super d<x, io.ktor.application.b>, ? super x, ? super kotlin.coroutines.d<? super x>, ? extends Object> body) {
        k.f($this$post, "$this$post");
        k.f(path, "path");
        k.f(body, "body");
        return d($this$post, path, u.i.g(), new b(body));
    }

    @NotNull
    public static final i a(@NotNull i $this$createRouteFromPath, @NotNull String path) {
        j selector;
        k.f($this$createRouteFromPath, "$this$createRouteFromPath");
        k.f(path, "path");
        i current = $this$createRouteFromPath;
        for (t next : s.b.b(path).b()) {
            String value = next.a();
            switch (p.a[next.b().ordinal()]) {
                case 1:
                    selector = e.a.b(value);
                    break;
                case 2:
                    selector = e.a.a(value);
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            current = current.F(selector);
        }
        return current;
    }
}
