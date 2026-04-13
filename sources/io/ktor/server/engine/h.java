package io.ktor.server.engine;

import io.ktor.application.i;
import io.ktor.server.engine.a;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: BaseApplicationEngine.kt */
public abstract class h implements a {
    @NotNull
    private final b a;
    @NotNull
    private final s b;

    /* compiled from: BaseApplicationEngine.kt */
    public static class c extends a.C0270a {
    }

    /* compiled from: BaseApplicationEngine.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<io.ktor.application.a, x> {
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(h hVar) {
            super(1);
            this.this$0 = hVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((io.ktor.application.a) obj);
            return x.a;
        }

        public final void invoke(@NotNull io.ktor.application.a it) {
            k.f(it, "it");
            it.B().r(this.this$0.d().A());
            it.C().r(this.this$0.d().B());
            n.c(it.B());
            n.d(it.C());
        }
    }

    /* compiled from: BaseApplicationEngine.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<io.ktor.application.a, x> {
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(h hVar) {
            super(1);
            this.this$0 = hVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((io.ktor.application.a) obj);
            return x.a;
        }

        public final void invoke(@NotNull io.ktor.application.a it) {
            k.f(it, "it");
            for (r it2 : this.this$0.b().c()) {
                org.slf4j.b f = this.this$0.b().f();
                StringBuilder sb = new StringBuilder();
                sb.append("Responding at ");
                String c = it2.getType().c();
                if (c != null) {
                    String lowerCase = c.toLowerCase();
                    k.b(lowerCase, "(this as java.lang.String).toLowerCase()");
                    sb.append(lowerCase);
                    sb.append("://");
                    sb.append(it2.b());
                    sb.append(':');
                    sb.append(it2.a());
                    f.info(sb.toString());
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
            }
        }
    }

    public h(@NotNull b bVar, @NotNull s sVar) {
        k.f(bVar, "environment");
        k.f(sVar, "pipeline");
        this.a = bVar;
        this.b = sVar;
        BaseApplicationResponse.b.b(sVar.B());
        bVar.b().b(i.b(), new a(this));
        bVar.b().b(i.a(), new b(this));
    }

    @NotNull
    public io.ktor.application.a c() {
        return a.b.a(this);
    }

    @NotNull
    public final b b() {
        return this.a;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ h(b bVar, s sVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bVar, (i & 2) != 0 ? m.b(bVar) : sVar);
    }

    @NotNull
    public final s d() {
        return this.b;
    }
}
