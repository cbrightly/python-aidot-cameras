package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.collections.l0;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.resolve.constants.j;
import kotlin.reflect.jvm.internal.impl.resolve.constants.w;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.t;
import org.jetbrains.annotations.NotNull;

/* compiled from: annotationUtil.kt */
public final class f {
    private static final kotlin.reflect.jvm.internal.impl.name.f a;
    private static final kotlin.reflect.jvm.internal.impl.name.f b;
    private static final kotlin.reflect.jvm.internal.impl.name.f c;
    private static final kotlin.reflect.jvm.internal.impl.name.f d;
    private static final kotlin.reflect.jvm.internal.impl.name.f e;

    public static /* synthetic */ c b(g gVar, String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "";
        }
        if ((i & 4) != 0) {
            str3 = "WARNING";
        }
        return a(gVar, str, str2, str3);
    }

    @NotNull
    public static final c a(@NotNull g $this$createDeprecatedAnnotation, @NotNull String message, @NotNull String replaceWith, @NotNull String level) {
        k.f($this$createDeprecatedAnnotation, "$this$createDeprecatedAnnotation");
        k.f(message, "message");
        k.f(replaceWith, "replaceWith");
        k.f(level, FirebaseAnalytics.Param.LEVEL);
        g.e eVar = g.h;
        b bVar = eVar.z;
        k.b(bVar, "KotlinBuiltIns.FQ_NAMES.replaceWith");
        j replaceWithAnnotation = new j($this$createDeprecatedAnnotation, bVar, l0.h(t.a(d, new w(replaceWith)), t.a(e, new kotlin.reflect.jvm.internal.impl.resolve.constants.b(q.g(), new a($this$createDeprecatedAnnotation)))));
        b bVar2 = eVar.x;
        k.b(bVar2, "KotlinBuiltIns.FQ_NAMES.deprecated");
        kotlin.reflect.jvm.internal.impl.name.f fVar = c;
        kotlin.reflect.jvm.internal.impl.name.a m = kotlin.reflect.jvm.internal.impl.name.a.m(eVar.y);
        k.b(m, "ClassId.topLevel(KotlinB…Q_NAMES.deprecationLevel)");
        kotlin.reflect.jvm.internal.impl.name.f f = kotlin.reflect.jvm.internal.impl.name.f.f(level);
        k.b(f, "Name.identifier(level)");
        return new j($this$createDeprecatedAnnotation, bVar2, l0.h(t.a(a, new w(message)), t.a(b, new kotlin.reflect.jvm.internal.impl.resolve.constants.a(replaceWithAnnotation)), t.a(fVar, new j(m, f))));
    }

    /* compiled from: annotationUtil.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<y, i0> {
        final /* synthetic */ g $this_createDeprecatedAnnotation;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(g gVar) {
            super(1);
            this.$this_createDeprecatedAnnotation = gVar;
        }

        @NotNull
        public final i0 invoke(@NotNull y module) {
            k.f(module, "module");
            i0 m = module.j().m(h1.INVARIANT, this.$this_createDeprecatedAnnotation.Y());
            k.b(m, "module.builtIns.getArray…ce.INVARIANT, stringType)");
            return m;
        }
    }

    static {
        kotlin.reflect.jvm.internal.impl.name.f f = kotlin.reflect.jvm.internal.impl.name.f.f("message");
        k.b(f, "Name.identifier(\"message\")");
        a = f;
        kotlin.reflect.jvm.internal.impl.name.f f2 = kotlin.reflect.jvm.internal.impl.name.f.f("replaceWith");
        k.b(f2, "Name.identifier(\"replaceWith\")");
        b = f2;
        kotlin.reflect.jvm.internal.impl.name.f f3 = kotlin.reflect.jvm.internal.impl.name.f.f(FirebaseAnalytics.Param.LEVEL);
        k.b(f3, "Name.identifier(\"level\")");
        c = f3;
        kotlin.reflect.jvm.internal.impl.name.f f4 = kotlin.reflect.jvm.internal.impl.name.f.f("expression");
        k.b(f4, "Name.identifier(\"expression\")");
        d = f4;
        kotlin.reflect.jvm.internal.impl.name.f f5 = kotlin.reflect.jvm.internal.impl.name.f.f("imports");
        k.b(f5, "Name.identifier(\"imports\")");
        e = f5;
    }
}
