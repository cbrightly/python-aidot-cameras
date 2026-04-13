package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.i;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.r0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.incremental.components.c;
import kotlin.reflect.jvm.internal.impl.load.java.a;
import kotlin.reflect.jvm.internal.impl.load.java.components.f;
import kotlin.reflect.jvm.internal.impl.load.java.components.j;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.b;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.c;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.g;
import kotlin.reflect.jvm.internal.impl.load.java.n;
import kotlin.reflect.jvm.internal.impl.load.kotlin.d;
import kotlin.reflect.jvm.internal.impl.load.kotlin.e;
import kotlin.reflect.jvm.internal.impl.load.kotlin.n;
import kotlin.reflect.jvm.internal.impl.load.kotlin.u;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.m;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: RuntimeModuleData.kt */
public final class l {
    public static /* synthetic */ g c(ClassLoader classLoader, y yVar, j jVar, a0 a0Var, n nVar, e eVar, kotlin.reflect.jvm.internal.impl.load.java.lazy.j jVar2, u uVar, int i, Object obj) {
        return b(classLoader, yVar, jVar, a0Var, nVar, eVar, jVar2, (i & 128) != 0 ? u.a.a : uVar);
    }

    @NotNull
    public static final g b(@NotNull ClassLoader classLoader, @NotNull y module, @NotNull j storageManager, @NotNull a0 notFoundClasses, @NotNull n reflectKotlinClassFinder, @NotNull e deserializedDescriptorResolver, @NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.j singleModuleClassResolver, @NotNull u packagePartProvider) {
        ClassLoader classLoader2 = classLoader;
        y yVar = module;
        j jVar = storageManager;
        a0 a0Var = notFoundClasses;
        e eVar = deserializedDescriptorResolver;
        kotlin.reflect.jvm.internal.impl.load.java.lazy.j jVar2 = singleModuleClassResolver;
        u uVar = packagePartProvider;
        k.f(classLoader2, "classLoader");
        k.f(yVar, "module");
        k.f(jVar, "storageManager");
        k.f(a0Var, "notFoundClasses");
        k.f(reflectKotlinClassFinder, "reflectKotlinClassFinder");
        k.f(deserializedDescriptorResolver, "deserializedDescriptorResolver");
        k.f(singleModuleClassResolver, "singleModuleClassResolver");
        k.f(packagePartProvider, "packagePartProvider");
        kotlin.reflect.jvm.internal.impl.utils.e eVar2 = kotlin.reflect.jvm.internal.impl.utils.e.b;
        a annotationTypeQualifierResolver = new a(jVar, eVar2);
        a aVar = annotationTypeQualifierResolver;
        kotlin.reflect.jvm.internal.impl.utils.e eVar3 = eVar2;
        a annotationTypeQualifierResolver2 = annotationTypeQualifierResolver;
        d dVar = r6;
        d dVar2 = new d(classLoader2);
        kotlin.reflect.jvm.internal.impl.load.java.components.k kVar = kotlin.reflect.jvm.internal.impl.load.java.components.k.a;
        kotlin.reflect.jvm.internal.impl.utils.e eVar4 = eVar3;
        k.b(kVar, "SignaturePropagator.DO_NOTHING");
        j jVar3 = j.b;
        kotlin.reflect.jvm.internal.impl.load.java.components.g gVar = kotlin.reflect.jvm.internal.impl.load.java.components.g.a;
        k.b(gVar, "JavaResolverCache.EMPTY");
        f.a aVar2 = f.a.a;
        j.a aVar3 = j.a.a;
        a0 a0Var2 = a0Var;
        m mVar = m.a;
        r0.a aVar4 = r0.a.a;
        d dVar3 = dVar;
        y yVar2 = yVar;
        c.a aVar5 = c.a.a;
        i iVar = r4;
        i iVar2 = new i(yVar2, a0Var2);
        kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l lVar = r4;
        kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l lVar2 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l(annotationTypeQualifierResolver2, eVar4);
        a aVar6 = annotationTypeQualifierResolver2;
        return new g(new b(storageManager, dVar3, reflectKotlinClassFinder, eVar, kVar, jVar3, gVar, aVar2, aVar3, mVar, jVar2, uVar, aVar4, aVar5, module, iVar, aVar, lVar, n.a.a, c.b.b, kotlin.reflect.jvm.internal.impl.types.checker.n.b.a()));
    }

    @NotNull
    public static final d a(@NotNull y module, @NotNull kotlin.reflect.jvm.internal.impl.storage.j storageManager, @NotNull a0 notFoundClasses, @NotNull g lazyJavaPackageFragmentProvider, @NotNull kotlin.reflect.jvm.internal.impl.load.kotlin.n reflectKotlinClassFinder, @NotNull e deserializedDescriptorResolver) {
        y yVar = module;
        kotlin.reflect.jvm.internal.impl.storage.j jVar = storageManager;
        a0 a0Var = notFoundClasses;
        kotlin.reflect.jvm.internal.impl.load.kotlin.n nVar = reflectKotlinClassFinder;
        e eVar = deserializedDescriptorResolver;
        k.f(yVar, "module");
        k.f(jVar, "storageManager");
        k.f(a0Var, "notFoundClasses");
        k.f(lazyJavaPackageFragmentProvider, "lazyJavaPackageFragmentProvider");
        k.f(nVar, "reflectKotlinClassFinder");
        k.f(eVar, "deserializedDescriptorResolver");
        kotlin.reflect.jvm.internal.impl.load.kotlin.g javaClassDataFinder = new kotlin.reflect.jvm.internal.impl.load.kotlin.g(nVar, eVar);
        kotlin.reflect.jvm.internal.impl.load.kotlin.c binaryClassAnnotationAndConstantLoader = new kotlin.reflect.jvm.internal.impl.load.kotlin.c(yVar, a0Var, jVar, nVar);
        return new d(storageManager, module, m.a.a, javaClassDataFinder, binaryClassAnnotationAndConstantLoader, lazyJavaPackageFragmentProvider, notFoundClasses, j.b, c.a.a, kotlin.reflect.jvm.internal.impl.serialization.deserialization.k.a.a(), kotlin.reflect.jvm.internal.impl.types.checker.n.b.a());
    }
}
