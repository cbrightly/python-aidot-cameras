package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.reflect.jvm.internal.impl.builtins.i;
import kotlin.reflect.jvm.internal.impl.descriptors.r0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.incremental.components.c;
import kotlin.reflect.jvm.internal.impl.load.java.a;
import kotlin.reflect.jvm.internal.impl.load.java.components.f;
import kotlin.reflect.jvm.internal.impl.load.java.components.g;
import kotlin.reflect.jvm.internal.impl.load.java.components.k;
import kotlin.reflect.jvm.internal.impl.load.java.m;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l;
import kotlin.reflect.jvm.internal.impl.load.kotlin.e;
import kotlin.reflect.jvm.internal.impl.load.kotlin.n;
import kotlin.reflect.jvm.internal.impl.load.kotlin.u;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.r;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: context.kt */
public final class b {
    @NotNull
    private final j a;
    @NotNull
    private final m b;
    @NotNull
    private final n c;
    @NotNull
    private final e d;
    @NotNull
    private final k e;
    @NotNull
    private final r f;
    @NotNull
    private final g g;
    @NotNull
    private final f h;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.load.java.components.j i;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.load.java.sources.b j;
    @NotNull
    private final j k;
    @NotNull
    private final u l;
    @NotNull
    private final r0 m;
    @NotNull
    private final c n;
    @NotNull
    private final y o;
    @NotNull
    private final i p;
    @NotNull
    private final a q;
    @NotNull
    private final l r;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.load.java.n s;
    @NotNull
    private final c t;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.types.checker.n u;

    public b(@NotNull j storageManager, @NotNull m finder, @NotNull n kotlinClassFinder, @NotNull e deserializedDescriptorResolver, @NotNull k signaturePropagator, @NotNull r errorReporter, @NotNull g javaResolverCache, @NotNull f javaPropertyInitializerEvaluator, @NotNull kotlin.reflect.jvm.internal.impl.load.java.components.j samConversionResolver, @NotNull kotlin.reflect.jvm.internal.impl.load.java.sources.b sourceElementFactory, @NotNull j moduleClassResolver, @NotNull u packagePartProvider, @NotNull r0 supertypeLoopChecker, @NotNull c lookupTracker, @NotNull y module, @NotNull i reflectionTypes, @NotNull a annotationTypeQualifierResolver, @NotNull l signatureEnhancement, @NotNull kotlin.reflect.jvm.internal.impl.load.java.n javaClassesTracker, @NotNull c settings, @NotNull kotlin.reflect.jvm.internal.impl.types.checker.n kotlinTypeChecker) {
        j jVar = storageManager;
        m mVar = finder;
        n nVar = kotlinClassFinder;
        e eVar = deserializedDescriptorResolver;
        k kVar = signaturePropagator;
        r rVar = errorReporter;
        g gVar = javaResolverCache;
        f fVar = javaPropertyInitializerEvaluator;
        kotlin.reflect.jvm.internal.impl.load.java.components.j jVar2 = samConversionResolver;
        kotlin.reflect.jvm.internal.impl.load.java.sources.b bVar = sourceElementFactory;
        j jVar3 = moduleClassResolver;
        u uVar = packagePartProvider;
        r0 r0Var = supertypeLoopChecker;
        c cVar = lookupTracker;
        i iVar = reflectionTypes;
        kotlin.jvm.internal.k.f(jVar, "storageManager");
        kotlin.jvm.internal.k.f(mVar, "finder");
        kotlin.jvm.internal.k.f(nVar, "kotlinClassFinder");
        kotlin.jvm.internal.k.f(eVar, "deserializedDescriptorResolver");
        kotlin.jvm.internal.k.f(kVar, "signaturePropagator");
        kotlin.jvm.internal.k.f(rVar, "errorReporter");
        kotlin.jvm.internal.k.f(gVar, "javaResolverCache");
        kotlin.jvm.internal.k.f(fVar, "javaPropertyInitializerEvaluator");
        kotlin.jvm.internal.k.f(jVar2, "samConversionResolver");
        kotlin.jvm.internal.k.f(bVar, "sourceElementFactory");
        kotlin.jvm.internal.k.f(jVar3, "moduleClassResolver");
        kotlin.jvm.internal.k.f(uVar, "packagePartProvider");
        kotlin.jvm.internal.k.f(r0Var, "supertypeLoopChecker");
        kotlin.jvm.internal.k.f(cVar, "lookupTracker");
        kotlin.jvm.internal.k.f(module, "module");
        kotlin.jvm.internal.k.f(reflectionTypes, "reflectionTypes");
        kotlin.jvm.internal.k.f(annotationTypeQualifierResolver, "annotationTypeQualifierResolver");
        kotlin.jvm.internal.k.f(signatureEnhancement, "signatureEnhancement");
        kotlin.jvm.internal.k.f(javaClassesTracker, "javaClassesTracker");
        kotlin.jvm.internal.k.f(settings, "settings");
        kotlin.jvm.internal.k.f(kotlinTypeChecker, "kotlinTypeChecker");
        this.a = jVar;
        this.b = mVar;
        this.c = nVar;
        this.d = eVar;
        this.e = kVar;
        this.f = rVar;
        this.g = gVar;
        this.h = fVar;
        this.i = jVar2;
        this.j = bVar;
        this.k = jVar3;
        this.l = uVar;
        this.m = r0Var;
        this.n = cVar;
        this.o = module;
        this.p = reflectionTypes;
        this.q = annotationTypeQualifierResolver;
        this.r = signatureEnhancement;
        this.s = javaClassesTracker;
        this.t = settings;
        this.u = kotlinTypeChecker;
    }

    @NotNull
    public final j s() {
        return this.a;
    }

    @NotNull
    public final m d() {
        return this.b;
    }

    @NotNull
    public final n h() {
        return this.c;
    }

    @NotNull
    public final e b() {
        return this.d;
    }

    @NotNull
    public final k q() {
        return this.e;
    }

    @NotNull
    public final r c() {
        return this.f;
    }

    @NotNull
    public final g g() {
        return this.g;
    }

    @NotNull
    public final f f() {
        return this.h;
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.load.java.sources.b r() {
        return this.j;
    }

    @NotNull
    public final j l() {
        return this.k;
    }

    @NotNull
    public final u m() {
        return this.l;
    }

    @NotNull
    public final r0 t() {
        return this.m;
    }

    @NotNull
    public final c j() {
        return this.n;
    }

    @NotNull
    public final y k() {
        return this.o;
    }

    @NotNull
    public final i n() {
        return this.p;
    }

    @NotNull
    public final a a() {
        return this.q;
    }

    @NotNull
    public final l p() {
        return this.r;
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.load.java.n e() {
        return this.s;
    }

    @NotNull
    public final c o() {
        return this.t;
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.types.checker.n i() {
        return this.u;
    }

    @NotNull
    public final b u(@NotNull g javaResolverCache) {
        kotlin.jvm.internal.k.f(javaResolverCache, "javaResolverCache");
        return new b(this.a, this.b, this.c, this.d, this.e, this.f, javaResolverCache, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u);
    }
}
