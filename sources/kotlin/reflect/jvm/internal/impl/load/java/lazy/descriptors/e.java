package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.collections.r;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.i;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.h;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.d;
import kotlin.reflect.jvm.internal.impl.load.java.s;
import kotlin.reflect.jvm.internal.impl.load.java.structure.m;
import kotlin.reflect.jvm.internal.impl.load.java.structure.o;
import kotlin.reflect.jvm.internal.impl.load.java.structure.v;
import kotlin.reflect.jvm.internal.impl.resolve.constants.j;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.g;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.k;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: LazyJavaAnnotationDescriptor.kt */
public final class e implements kotlin.reflect.jvm.internal.impl.descriptors.annotations.c, i {
    static final /* synthetic */ k[] a;
    @Nullable
    private final g b;
    @NotNull
    private final f c;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.load.java.sources.a d;
    @NotNull
    private final f e;
    private final boolean f;
    /* access modifiers changed from: private */
    public final h g;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.load.java.structure.a h;

    static {
        Class<e> cls = e.class;
        a = new k[]{a0.h(new u(a0.b(cls), "fqName", "getFqName()Lorg/jetbrains/kotlin/name/FqName;")), a0.h(new u(a0.b(cls), IjkMediaMeta.IJKM_KEY_TYPE, "getType()Lorg/jetbrains/kotlin/types/SimpleType;")), a0.h(new u(a0.b(cls), "allValueArguments", "getAllValueArguments()Ljava/util/Map;"))};
    }

    @NotNull
    public Map<kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> a() {
        return (Map) kotlin.reflect.jvm.internal.impl.storage.i.a(this.e, this, a[2]);
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.name.b e() {
        return (kotlin.reflect.jvm.internal.impl.name.b) kotlin.reflect.jvm.internal.impl.storage.i.b(this.b, this, a[0]);
    }

    @NotNull
    /* renamed from: j */
    public i0 getType() {
        return (i0) kotlin.reflect.jvm.internal.impl.storage.i.a(this.c, this, a[1]);
    }

    public e(@NotNull h c2, @NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.a javaAnnotation) {
        kotlin.jvm.internal.k.f(c2, "c");
        kotlin.jvm.internal.k.f(javaAnnotation, "javaAnnotation");
        this.g = c2;
        this.h = javaAnnotation;
        this.b = c2.e().e(new b(this));
        this.c = c2.e().c(new c(this));
        this.d = c2.a().r().a(javaAnnotation);
        this.e = c2.e().c(new a(this));
        this.f = javaAnnotation.h();
    }

    /* compiled from: LazyJavaAnnotationDescriptor.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.impl.name.b> {
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(e eVar) {
            super(0);
            this.this$0 = eVar;
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.name.b invoke() {
            kotlin.reflect.jvm.internal.impl.name.a d = this.this$0.h.d();
            if (d != null) {
                return d.b();
            }
            return null;
        }
    }

    /* compiled from: LazyJavaAnnotationDescriptor.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<i0> {
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(e eVar) {
            super(0);
            this.this$0 = eVar;
        }

        @NotNull
        public final i0 invoke() {
            kotlin.reflect.jvm.internal.impl.name.b fqName = this.this$0.e();
            if (fqName != null) {
                kotlin.jvm.internal.k.b(fqName, "fqName ?: return@createL…fqName: $javaAnnotation\")");
                kotlin.reflect.jvm.internal.impl.descriptors.e annotationClass = kotlin.reflect.jvm.internal.impl.builtins.jvm.c.w(kotlin.reflect.jvm.internal.impl.builtins.jvm.c.m, fqName, this.this$0.g.d().j(), (Integer) null, 4, (Object) null);
                if (annotationClass == null) {
                    kotlin.reflect.jvm.internal.impl.load.java.structure.g javaClass = this.this$0.h.resolve();
                    annotationClass = javaClass != null ? this.this$0.g.a().l().a(javaClass) : null;
                }
                if (annotationClass == null) {
                    annotationClass = this.this$0.g(fqName);
                }
                return annotationClass.m();
            }
            return kotlin.reflect.jvm.internal.impl.types.u.j("No fqName: " + this.this$0.h);
        }
    }

    @NotNull
    /* renamed from: i */
    public kotlin.reflect.jvm.internal.impl.load.java.sources.a n() {
        return this.d;
    }

    /* compiled from: LazyJavaAnnotationDescriptor.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Map<kotlin.reflect.jvm.internal.impl.name.f, ? extends kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>>> {
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(e eVar) {
            super(0);
            this.this$0 = eVar;
        }

        @NotNull
        public final Map<kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> invoke() {
            Iterable<kotlin.reflect.jvm.internal.impl.load.java.structure.b> $this$mapNotNullTo$iv$iv = this.this$0.h.getArguments();
            Collection destination$iv$iv = new ArrayList();
            for (kotlin.reflect.jvm.internal.impl.load.java.structure.b arg : $this$mapNotNullTo$iv$iv) {
                kotlin.reflect.jvm.internal.impl.name.f name = arg.getName();
                if (name == null) {
                    name = s.c;
                }
                kotlin.reflect.jvm.internal.impl.resolve.constants.g value = this.this$0.k(arg);
                Object it$iv$iv = value != null ? t.a(name, value) : null;
                if (it$iv$iv != null) {
                    destination$iv$iv.add(it$iv$iv);
                }
            }
            return l0.o(destination$iv$iv);
        }
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> k(kotlin.reflect.jvm.internal.impl.load.java.structure.b argument) {
        if (argument instanceof o) {
            return kotlin.reflect.jvm.internal.impl.resolve.constants.h.a.c(((o) argument).getValue());
        }
        if (argument instanceof m) {
            return o(((m) argument).c(), ((m) argument).d());
        }
        if (argument instanceof kotlin.reflect.jvm.internal.impl.load.java.structure.e) {
            kotlin.reflect.jvm.internal.impl.name.f name = argument.getName();
            if (name == null) {
                name = s.c;
                kotlin.jvm.internal.k.b(name, "DEFAULT_ANNOTATION_MEMBER_NAME");
            }
            return m(name, ((kotlin.reflect.jvm.internal.impl.load.java.structure.e) argument).getElements());
        } else if (argument instanceof kotlin.reflect.jvm.internal.impl.load.java.structure.c) {
            return l(((kotlin.reflect.jvm.internal.impl.load.java.structure.c) argument).a());
        } else {
            if (argument instanceof kotlin.reflect.jvm.internal.impl.load.java.structure.h) {
                return p(((kotlin.reflect.jvm.internal.impl.load.java.structure.h) argument).b());
            }
            return null;
        }
    }

    private final kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> l(kotlin.reflect.jvm.internal.impl.load.java.structure.a javaAnnotation) {
        return new kotlin.reflect.jvm.internal.impl.resolve.constants.a(new e(this.g, javaAnnotation));
    }

    private final kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> m(kotlin.reflect.jvm.internal.impl.name.f argumentName, List<? extends kotlin.reflect.jvm.internal.impl.load.java.structure.b> elements) {
        b0 arrayType;
        i0 j = getType();
        kotlin.jvm.internal.k.b(j, IjkMediaMeta.IJKM_KEY_TYPE);
        if (d0.a(j)) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.e g2 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.g(this);
        if (g2 == null) {
            kotlin.jvm.internal.k.n();
        }
        w0 b2 = kotlin.reflect.jvm.internal.impl.load.java.components.a.b(argumentName, g2);
        if (b2 == null || (arrayType = b2.getType()) == null) {
            arrayType = this.g.a().k().j().m(h1.INVARIANT, kotlin.reflect.jvm.internal.impl.types.u.j("Unknown array element type"));
        }
        kotlin.jvm.internal.k.b(arrayType, "DescriptorResolverUtils.… type\")\n                )");
        Iterable<kotlin.reflect.jvm.internal.impl.load.java.structure.b> $this$mapTo$iv$iv = elements;
        List values = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.load.java.structure.b argument : $this$mapTo$iv$iv) {
            Object k = k(argument);
            if (k == null) {
                k = new kotlin.reflect.jvm.internal.impl.resolve.constants.t();
            }
            values.add(k);
        }
        return kotlin.reflect.jvm.internal.impl.resolve.constants.h.a.b(values, arrayType);
    }

    private final kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> o(kotlin.reflect.jvm.internal.impl.name.a enumClassId, kotlin.reflect.jvm.internal.impl.name.f entryName) {
        if (enumClassId == null || entryName == null) {
            return null;
        }
        return new j(enumClassId, entryName);
    }

    private final kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> p(v javaType) {
        return kotlin.reflect.jvm.internal.impl.resolve.constants.r.b.a(this.g.g().l(javaType, d.f(kotlin.reflect.jvm.internal.impl.load.java.components.l.COMMON, false, (t0) null, 3, (Object) null)));
    }

    @NotNull
    public String toString() {
        return kotlin.reflect.jvm.internal.impl.renderer.c.t(kotlin.reflect.jvm.internal.impl.renderer.c.f, this, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.e) null, 2, (Object) null);
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.descriptors.e g(kotlin.reflect.jvm.internal.impl.name.b fqName) {
        y d2 = this.g.d();
        kotlin.reflect.jvm.internal.impl.name.a m = kotlin.reflect.jvm.internal.impl.name.a.m(fqName);
        kotlin.jvm.internal.k.b(m, "ClassId.topLevel(fqName)");
        return kotlin.reflect.jvm.internal.impl.descriptors.t.c(d2, m, this.g.a().b().d().q());
    }

    public boolean h() {
        return this.f;
    }
}
