package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Map;
import kotlin.collections.k0;
import kotlin.collections.l0;
import kotlin.collections.p;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.load.java.structure.b;
import kotlin.reflect.jvm.internal.impl.load.java.structure.e;
import kotlin.reflect.jvm.internal.impl.load.java.structure.m;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.k;
import kotlin.t;
import org.jetbrains.annotations.NotNull;

/* compiled from: JavaAnnotationMapper.kt */
public final class i extends b {
    static final /* synthetic */ k[] g = {a0.h(new u(a0.b(i.class), "allValueArguments", "getAllValueArguments()Ljava/util/Map;"))};
    @NotNull
    private final f h;

    @NotNull
    public Map<kotlin.reflect.jvm.internal.impl.name.f, g<?>> a() {
        return (Map) kotlin.reflect.jvm.internal.impl.storage.i.a(this.h, this, g[0]);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public i(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.a r3, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h r4) {
        /*
            r2 = this;
            java.lang.String r0 = "annotation"
            kotlin.jvm.internal.k.f(r3, r0)
            java.lang.String r0 = "c"
            kotlin.jvm.internal.k.f(r4, r0)
            kotlin.reflect.jvm.internal.impl.builtins.g$e r0 = kotlin.reflect.jvm.internal.impl.builtins.g.h
            kotlin.reflect.jvm.internal.impl.name.b r0 = r0.D
            java.lang.String r1 = "KotlinBuiltIns.FQ_NAMES.target"
            kotlin.jvm.internal.k.b(r0, r1)
            r2.<init>(r4, r3, r0)
            kotlin.reflect.jvm.internal.impl.storage.j r0 = r4.e()
            kotlin.reflect.jvm.internal.impl.load.java.components.i$a r1 = new kotlin.reflect.jvm.internal.impl.load.java.components.i$a
            r1.<init>(r2)
            kotlin.reflect.jvm.internal.impl.storage.f r0 = r0.c(r1)
            r2.h = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.components.i.<init>(kotlin.reflect.jvm.internal.impl.load.java.structure.a, kotlin.reflect.jvm.internal.impl.load.java.lazy.h):void");
    }

    /* compiled from: JavaAnnotationMapper.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Map<kotlin.reflect.jvm.internal.impl.name.f, ? extends g<?>>> {
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(i iVar) {
            super(0);
            this.this$0 = iVar;
        }

        @NotNull
        public final Map<kotlin.reflect.jvm.internal.impl.name.f, g<?>> invoke() {
            g targetArgument;
            b b = this.this$0.b();
            Map<K, V> map = null;
            if (b instanceof e) {
                targetArgument = d.c.c(((e) this.this$0.b()).getElements());
            } else if (b instanceof m) {
                targetArgument = d.c.c(p.b(this.this$0.b()));
            } else {
                targetArgument = null;
            }
            if (targetArgument != null) {
                map = k0.c(t.a(c.k.d(), targetArgument));
            }
            return map != null ? map : l0.f();
        }
    }
}
