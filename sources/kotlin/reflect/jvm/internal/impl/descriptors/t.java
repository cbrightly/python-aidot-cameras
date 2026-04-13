package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.reflect.e;
import kotlin.reflect.jvm.internal.impl.incremental.components.d;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.sequences.m;
import kotlin.sequences.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: findClassInModule.kt */
public final class t {
    @Nullable
    public static final h b(@NotNull y $this$findClassifierAcrossModuleDependencies, @NotNull kotlin.reflect.jvm.internal.impl.name.a classId) {
        k.f($this$findClassifierAcrossModuleDependencies, "$this$findClassifierAcrossModuleDependencies");
        k.f(classId, "classId");
        kotlin.reflect.jvm.internal.impl.name.b h = classId.h();
        k.b(h, "classId.packageFqName");
        e0 packageViewDescriptor = $this$findClassifierAcrossModuleDependencies.e0(h);
        List segments = classId.i().f();
        k.b(segments, "classId.relativeClassName.pathSegments()");
        h l = packageViewDescriptor.l();
        Object S = y.S(segments);
        k.b(S, "segments.first()");
        h topLevelClass = l.c((f) S, d.FROM_DESERIALIZATION);
        if (topLevelClass == null) {
            return null;
        }
        h result = topLevelClass;
        for (f name : segments.subList(1, segments.size())) {
            if (!(result instanceof e)) {
                return null;
            }
            h P = ((e) result).P();
            k.b(name, "name");
            h c = P.c(name, d.FROM_DESERIALIZATION);
            if (!(c instanceof e)) {
                c = null;
            }
            h hVar = (e) c;
            if (hVar == null) {
                return null;
            }
            result = hVar;
        }
        return result;
    }

    @Nullable
    public static final e a(@NotNull y $this$findClassAcrossModuleDependencies, @NotNull kotlin.reflect.jvm.internal.impl.name.a classId) {
        k.f($this$findClassAcrossModuleDependencies, "$this$findClassAcrossModuleDependencies");
        k.f(classId, "classId");
        h b2 = b($this$findClassAcrossModuleDependencies, classId);
        if (!(b2 instanceof e)) {
            b2 = null;
        }
        return (e) b2;
    }

    @NotNull
    public static final e c(@NotNull y $this$findNonGenericClassAcrossDependencies, @NotNull kotlin.reflect.jvm.internal.impl.name.a classId, @NotNull a0 notFoundClasses) {
        k.f($this$findNonGenericClassAcrossDependencies, "$this$findNonGenericClassAcrossDependencies");
        k.f(classId, "classId");
        k.f(notFoundClasses, "notFoundClasses");
        e existingClass = a($this$findNonGenericClassAcrossDependencies, classId);
        if (existingClass != null) {
            return existingClass;
        }
        return notFoundClasses.d(classId, o.C(o.w(m.h(classId, a.INSTANCE), b.INSTANCE)));
    }

    /* compiled from: findClassInModule.kt */
    public static final /* synthetic */ class a extends kotlin.jvm.internal.h implements l<kotlin.reflect.jvm.internal.impl.name.a, kotlin.reflect.jvm.internal.impl.name.a> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public final String getName() {
            return "getOuterClassId";
        }

        public final e getOwner() {
            return a0.b(kotlin.reflect.jvm.internal.impl.name.a.class);
        }

        public final String getSignature() {
            return "getOuterClassId()Lorg/jetbrains/kotlin/name/ClassId;";
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.name.a invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.a p1) {
            k.f(p1, "p1");
            return p1.g();
        }
    }

    /* compiled from: findClassInModule.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<kotlin.reflect.jvm.internal.impl.name.a, Integer> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Integer.valueOf(invoke((kotlin.reflect.jvm.internal.impl.name.a) obj));
        }

        public final int invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.a it) {
            k.f(it, "it");
            return 0;
        }
    }

    @Nullable
    public static final s0 d(@NotNull y $this$findTypeAliasAcrossModuleDependencies, @NotNull kotlin.reflect.jvm.internal.impl.name.a classId) {
        k.f($this$findTypeAliasAcrossModuleDependencies, "$this$findTypeAliasAcrossModuleDependencies");
        k.f(classId, "classId");
        h b2 = b($this$findTypeAliasAcrossModuleDependencies, classId);
        if (!(b2 instanceof s0)) {
            b2 = null;
        }
        return (s0) b2;
    }
}
