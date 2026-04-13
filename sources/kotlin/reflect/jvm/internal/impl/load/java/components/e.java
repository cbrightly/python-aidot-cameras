package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Map;
import kotlin.collections.k0;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.resolve.constants.w;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.i;
import kotlin.reflect.k;
import kotlin.t;
import org.jetbrains.annotations.NotNull;

/* compiled from: JavaAnnotationMapper.kt */
public final class e extends b {
    static final /* synthetic */ k[] g = {a0.h(new u(a0.b(e.class), "allValueArguments", "getAllValueArguments()Ljava/util/Map;"))};
    @NotNull
    private final f h;

    @NotNull
    public Map<kotlin.reflect.jvm.internal.impl.name.f, g<?>> a() {
        return (Map) i.a(this.h, this, g[0]);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public e(@org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.load.java.structure.a r3, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h r4) {
        /*
            r2 = this;
            java.lang.String r0 = "c"
            kotlin.jvm.internal.k.f(r4, r0)
            kotlin.reflect.jvm.internal.impl.builtins.g$e r0 = kotlin.reflect.jvm.internal.impl.builtins.g.h
            kotlin.reflect.jvm.internal.impl.name.b r0 = r0.x
            java.lang.String r1 = "KotlinBuiltIns.FQ_NAMES.deprecated"
            kotlin.jvm.internal.k.b(r0, r1)
            r2.<init>(r4, r3, r0)
            kotlin.reflect.jvm.internal.impl.storage.j r0 = r4.e()
            kotlin.reflect.jvm.internal.impl.load.java.components.e$a r1 = kotlin.reflect.jvm.internal.impl.load.java.components.e.a.INSTANCE
            kotlin.reflect.jvm.internal.impl.storage.f r0 = r0.c(r1)
            r2.h = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.components.e.<init>(kotlin.reflect.jvm.internal.impl.load.java.structure.a, kotlin.reflect.jvm.internal.impl.load.java.lazy.h):void");
    }

    /* compiled from: JavaAnnotationMapper.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Map<kotlin.reflect.jvm.internal.impl.name.f, ? extends w>> {
        public static final a INSTANCE = new a();

        a() {
            super(0);
        }

        @NotNull
        public final Map<kotlin.reflect.jvm.internal.impl.name.f, w> invoke() {
            return k0.c(t.a(c.k.b(), new w("Deprecated in Java")));
        }
    }
}
