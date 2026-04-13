package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Map;
import java.util.Set;
import kotlin.collections.l0;
import kotlin.collections.o0;
import kotlin.collections.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.load.java.a;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.k;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.g;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.h;
import kotlin.t;
import org.jetbrains.annotations.NotNull;

/* compiled from: AnnotationTypeQualifierResolver.kt */
public final class b {
    @NotNull
    private static final kotlin.reflect.jvm.internal.impl.name.b a = new kotlin.reflect.jvm.internal.impl.name.b("javax.annotation.meta.TypeQualifierNickname");
    @NotNull
    private static final kotlin.reflect.jvm.internal.impl.name.b b = new kotlin.reflect.jvm.internal.impl.name.b("javax.annotation.meta.TypeQualifier");
    @NotNull
    private static final kotlin.reflect.jvm.internal.impl.name.b c = new kotlin.reflect.jvm.internal.impl.name.b("javax.annotation.meta.TypeQualifierDefault");
    @NotNull
    private static final kotlin.reflect.jvm.internal.impl.name.b d = new kotlin.reflect.jvm.internal.impl.name.b("kotlin.annotations.jvm.UnderMigration");
    @NotNull
    private static final Map<kotlin.reflect.jvm.internal.impl.name.b, k> e;
    @NotNull
    private static final Set<kotlin.reflect.jvm.internal.impl.name.b> f = o0.g(t.f(), t.e());

    static {
        kotlin.reflect.jvm.internal.impl.name.b bVar = new kotlin.reflect.jvm.internal.impl.name.b("javax.annotation.ParametersAreNullableByDefault");
        h hVar = new h(g.NULLABLE, false, 2, (DefaultConstructorMarker) null);
        a.C0356a aVar = a.C0356a.VALUE_PARAMETER;
        e = l0.h(t.a(bVar, new k(hVar, p.b(aVar))), t.a(new kotlin.reflect.jvm.internal.impl.name.b("javax.annotation.ParametersAreNonnullByDefault"), new k(new h(g.NOT_NULL, false, 2, (DefaultConstructorMarker) null), p.b(aVar))));
    }

    @NotNull
    public static final kotlin.reflect.jvm.internal.impl.name.b e() {
        return a;
    }

    @NotNull
    public static final kotlin.reflect.jvm.internal.impl.name.b d() {
        return c;
    }

    @NotNull
    public static final kotlin.reflect.jvm.internal.impl.name.b c() {
        return d;
    }

    @NotNull
    public static final Map<kotlin.reflect.jvm.internal.impl.name.b, k> b() {
        return e;
    }

    /* access modifiers changed from: private */
    public static final boolean f(@NotNull e $this$isAnnotatedWithTypeQualifier) {
        return f.contains(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j($this$isAnnotatedWithTypeQualifier)) || $this$isAnnotatedWithTypeQualifier.getAnnotations().I(b);
    }
}
