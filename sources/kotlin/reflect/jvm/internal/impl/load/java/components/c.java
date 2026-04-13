package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.e;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.h;
import kotlin.reflect.jvm.internal.impl.load.java.structure.a;
import kotlin.reflect.jvm.internal.impl.load.java.structure.d;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaAnnotationMapper.kt */
public final class c {
    private static final b a;
    private static final b b;
    private static final b c;
    private static final b d;
    private static final b e;
    @NotNull
    private static final f f;
    @NotNull
    private static final f g;
    @NotNull
    private static final f h;
    private static final Map<b, b> i;
    @NotNull
    private static final Map<b, b> j;
    public static final c k = new c();

    static {
        b bVar = new b(Target.class.getCanonicalName());
        a = bVar;
        b bVar2 = new b(Retention.class.getCanonicalName());
        b = bVar2;
        b bVar3 = new b(Deprecated.class.getCanonicalName());
        c = bVar3;
        b bVar4 = new b(Documented.class.getCanonicalName());
        d = bVar4;
        b bVar5 = new b("java.lang.annotation.Repeatable");
        e = bVar5;
        f f2 = f.f("message");
        k.b(f2, "Name.identifier(\"message\")");
        f = f2;
        f f3 = f.f("allowedTargets");
        k.b(f3, "Name.identifier(\"allowedTargets\")");
        g = f3;
        f f4 = f.f("value");
        k.b(f4, "Name.identifier(\"value\")");
        h = f4;
        g.e eVar = g.h;
        i = l0.h(t.a(eVar.D, bVar), t.a(eVar.G, bVar2), t.a(eVar.H, bVar5), t.a(eVar.I, bVar4));
        j = l0.h(t.a(bVar, eVar.D), t.a(bVar2, eVar.G), t.a(bVar3, eVar.x), t.a(bVar5, eVar.H), t.a(bVar4, eVar.I));
    }

    private c() {
    }

    @NotNull
    public final f b() {
        return f;
    }

    @NotNull
    public final f d() {
        return g;
    }

    @NotNull
    public final f c() {
        return h;
    }

    @Nullable
    public final kotlin.reflect.jvm.internal.impl.descriptors.annotations.c e(@NotNull a annotation, @NotNull h c2) {
        k.f(annotation, "annotation");
        k.f(c2, "c");
        kotlin.reflect.jvm.internal.impl.name.a d2 = annotation.d();
        if (k.a(d2, kotlin.reflect.jvm.internal.impl.name.a.m(a))) {
            return new i(annotation, c2);
        }
        if (k.a(d2, kotlin.reflect.jvm.internal.impl.name.a.m(b))) {
            return new h(annotation, c2);
        }
        if (k.a(d2, kotlin.reflect.jvm.internal.impl.name.a.m(e))) {
            b bVar = g.h.H;
            k.b(bVar, "KotlinBuiltIns.FQ_NAMES.repeatable");
            return new b(c2, annotation, bVar);
        } else if (k.a(d2, kotlin.reflect.jvm.internal.impl.name.a.m(d))) {
            b bVar2 = g.h.I;
            k.b(bVar2, "KotlinBuiltIns.FQ_NAMES.mustBeDocumented");
            return new b(c2, annotation, bVar2);
        } else if (k.a(d2, kotlin.reflect.jvm.internal.impl.name.a.m(c))) {
            return null;
        } else {
            return new e(c2, annotation);
        }
    }

    @Nullable
    public final kotlin.reflect.jvm.internal.impl.descriptors.annotations.c a(@NotNull b kotlinName, @NotNull d annotationOwner, @NotNull h c2) {
        a annotation;
        a javaAnnotation;
        k.f(kotlinName, "kotlinName");
        k.f(annotationOwner, "annotationOwner");
        k.f(c2, "c");
        if (k.a(kotlinName, g.h.x) && ((javaAnnotation = annotationOwner.c(c)) != null || annotationOwner.w())) {
            return new e(javaAnnotation, c2);
        }
        b javaName = i.get(kotlinName);
        if (javaName == null || (annotation = annotationOwner.c(javaName)) == null) {
            return null;
        }
        return k.e(annotation, c2);
    }
}
