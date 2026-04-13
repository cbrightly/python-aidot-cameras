package io.ktor.server.engine;

import io.ktor.config.a;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.q;
import kotlin.coroutines.g;
import kotlin.coroutines.h;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.slf4j.b;

/* compiled from: ApplicationEngineEnvironment.kt */
public final class c {
    @NotNull
    private g a = h.INSTANCE;
    @NotNull
    private List<String> b = q.g();
    @NotNull
    private ClassLoader c;
    @NotNull
    private b d;
    @NotNull
    private a e;
    @NotNull
    private final List<r> f;
    @NotNull
    private final List<l<io.ktor.application.a, x>> g;
    @NotNull
    private String h;

    public c() {
        ClassLoader classLoader = b.class.getClassLoader();
        k.b(classLoader, "ApplicationEngineEnviron…t::class.java.classLoader");
        this.c = classLoader;
        b j = org.slf4j.c.j("Application");
        k.b(j, "LoggerFactory.getLogger(\"Application\")");
        this.d = j;
        this.e = new io.ktor.config.c();
        this.f = new ArrayList();
        this.g = new ArrayList();
        this.h = "";
    }

    public final void e(@NotNull g gVar) {
        k.f(gVar, "<set-?>");
        this.a = gVar;
    }

    public final void f(@NotNull List<String> list) {
        k.f(list, "<set-?>");
        this.b = list;
    }

    public final void d(@NotNull b bVar) {
        k.f(bVar, "<set-?>");
        this.d = bVar;
    }

    @NotNull
    public final List<r> b() {
        return this.f;
    }

    public final void c(@NotNull l<? super io.ktor.application.a, x> body) {
        k.f(body, "body");
        this.g.add(body);
    }

    @NotNull
    public final b a(@NotNull l<? super c, x> builder) {
        k.f(builder, "builder");
        builder.invoke(this);
        return new e(this.c, this.d, this.e, this.f, this.g, this.b, this.a, this.h);
    }
}
