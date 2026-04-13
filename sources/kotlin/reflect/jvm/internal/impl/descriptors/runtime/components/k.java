package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.util.Map;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.e;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.i;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.x;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.g;
import kotlin.reflect.jvm.internal.impl.load.kotlin.d;
import kotlin.reflect.jvm.internal.impl.load.kotlin.u;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.l;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.m;
import kotlin.reflect.jvm.internal.impl.storage.b;
import kotlin.reflect.jvm.internal.impl.types.checker.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: RuntimeModuleData.kt */
public final class k {
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final l b;
    @NotNull
    private final a c;

    private k(l deserialization, a packagePartScopeCache) {
        this.b = deserialization;
        this.c = packagePartScopeCache;
    }

    public /* synthetic */ k(l deserialization, a packagePartScopeCache, DefaultConstructorMarker $constructor_marker) {
        this(deserialization, packagePartScopeCache);
    }

    @NotNull
    public final l a() {
        return this.b;
    }

    @NotNull
    public final a c() {
        return this.c;
    }

    @NotNull
    public final y b() {
        return this.b.p();
    }

    /* compiled from: RuntimeModuleData.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final k a(@NotNull ClassLoader classLoader) {
            ClassLoader classLoader2 = classLoader;
            kotlin.jvm.internal.k.f(classLoader2, "classLoader");
            b storageManager = new b("RuntimeModuleData");
            e builtIns = new e(storageManager, e.a.FROM_DEPENDENCIES);
            f k = f.k("<runtime module for " + classLoader2 + '>');
            kotlin.jvm.internal.k.b(k, "Name.special(\"<runtime module for $classLoader>\")");
            x module = new x(k, storageManager, builtIns, (kotlin.reflect.jvm.internal.impl.platform.a) null, (Map) null, (f) null, 56, (DefaultConstructorMarker) null);
            builtIns.K0(module);
            builtIns.P0(module, true);
            g reflectKotlinClassFinder = new g(classLoader2);
            kotlin.reflect.jvm.internal.impl.load.kotlin.e deserializedDescriptorResolver = new kotlin.reflect.jvm.internal.impl.load.kotlin.e();
            kotlin.reflect.jvm.internal.impl.load.java.lazy.l singleModuleClassResolver = new kotlin.reflect.jvm.internal.impl.load.java.lazy.l();
            a0 notFoundClasses = new a0(storageManager, module);
            e builtIns2 = builtIns;
            b storageManager2 = storageManager;
            g lazyJavaPackageFragmentProvider = l.c(classLoader, module, storageManager, notFoundClasses, reflectKotlinClassFinder, deserializedDescriptorResolver, singleModuleClassResolver, (u) null, 128, (Object) null);
            d deserializationComponentsForJava = l.a(module, storageManager2, notFoundClasses, lazyJavaPackageFragmentProvider, reflectKotlinClassFinder, deserializedDescriptorResolver);
            deserializedDescriptorResolver.l(deserializationComponentsForJava);
            kotlin.reflect.jvm.internal.impl.load.java.components.g gVar = kotlin.reflect.jvm.internal.impl.load.java.components.g.a;
            kotlin.jvm.internal.k.b(gVar, "JavaResolverCache.EMPTY");
            kotlin.reflect.jvm.internal.impl.resolve.jvm.b javaDescriptorResolver = new kotlin.reflect.jvm.internal.impl.resolve.jvm.b(lazyJavaPackageFragmentProvider, gVar);
            singleModuleClassResolver.b(javaDescriptorResolver);
            ClassLoader stdlibClassLoader = kotlin.x.class.getClassLoader();
            kotlin.jvm.internal.k.b(stdlibClassLoader, "stdlibClassLoader");
            kotlin.reflect.jvm.internal.impl.load.java.lazy.l lVar = singleModuleClassResolver;
            kotlin.reflect.jvm.internal.impl.load.kotlin.e deserializedDescriptorResolver2 = deserializedDescriptorResolver;
            g reflectKotlinClassFinder2 = reflectKotlinClassFinder;
            kotlin.reflect.jvm.internal.impl.builtins.jvm.g gVar2 = new kotlin.reflect.jvm.internal.impl.builtins.jvm.g(storageManager2, new g(stdlibClassLoader), module, notFoundClasses, builtIns2.O0(), builtIns2.O0(), m.a.a, n.b.a());
            x module2 = module;
            module2.Q0(module2);
            module2.K0(new i(q.j(javaDescriptorResolver.a(), gVar2)));
            return new k(deserializationComponentsForJava.a(), new a(deserializedDescriptorResolver2, reflectKotlinClassFinder2), (DefaultConstructorMarker) null);
        }
    }
}
