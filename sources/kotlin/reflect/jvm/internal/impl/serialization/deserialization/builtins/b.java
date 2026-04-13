package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.collections.r;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.h;
import kotlin.jvm.internal.k;
import kotlin.reflect.e;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.c0;
import kotlin.reflect.jvm.internal.impl.descriptors.d0;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.incremental.components.c;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.m;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.o;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.s;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.v;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.checker.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BuiltInsLoaderImpl.kt */
public final class b implements kotlin.reflect.jvm.internal.impl.builtins.a {
    private final d b = new d();

    @NotNull
    public c0 a(@NotNull j storageManager, @NotNull y builtInsModule, @NotNull Iterable<? extends kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b> classDescriptorFactories, @NotNull c platformDependentDeclarationFilter, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a additionalClassPartsProvider, boolean isFallback) {
        k.f(storageManager, "storageManager");
        k.f(builtInsModule, "builtInsModule");
        k.f(classDescriptorFactories, "classDescriptorFactories");
        k.f(platformDependentDeclarationFilter, "platformDependentDeclarationFilter");
        k.f(additionalClassPartsProvider, "additionalClassPartsProvider");
        Set<kotlin.reflect.jvm.internal.impl.name.b> set = g.g;
        k.b(set, "KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAMES");
        return b(storageManager, builtInsModule, set, classDescriptorFactories, platformDependentDeclarationFilter, additionalClassPartsProvider, isFallback, new a(this.b));
    }

    /* compiled from: BuiltInsLoaderImpl.kt */
    public static final /* synthetic */ class a extends h implements l<String, InputStream> {
        a(d dVar) {
            super(1, dVar);
        }

        public final String getName() {
            return "loadResource";
        }

        public final e getOwner() {
            return a0.b(d.class);
        }

        public final String getSignature() {
            return "loadResource(Ljava/lang/String;)Ljava/io/InputStream;";
        }

        @Nullable
        public final InputStream invoke(@NotNull String p1) {
            k.f(p1, "p1");
            return ((d) this.receiver).a(p1);
        }
    }

    @NotNull
    public final c0 b(@NotNull j storageManager, @NotNull y module, @NotNull Set<kotlin.reflect.jvm.internal.impl.name.b> packageFqNames, @NotNull Iterable<? extends kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b> classDescriptorFactories, @NotNull c platformDependentDeclarationFilter, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a additionalClassPartsProvider, boolean isFallback, @NotNull l<? super String, ? extends InputStream> loadResource) {
        j jVar = storageManager;
        y yVar = module;
        l<? super String, ? extends InputStream> lVar = loadResource;
        k.f(jVar, "storageManager");
        k.f(yVar, "module");
        k.f(packageFqNames, "packageFqNames");
        k.f(classDescriptorFactories, "classDescriptorFactories");
        k.f(platformDependentDeclarationFilter, "platformDependentDeclarationFilter");
        k.f(additionalClassPartsProvider, "additionalClassPartsProvider");
        k.f(lVar, "loadResource");
        Iterable<kotlin.reflect.jvm.internal.impl.name.b> $this$map$iv = packageFqNames;
        List arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.name.b fqName : $this$map$iv) {
            String resourcePath = a.n.n(fqName);
            InputStream inputStream = (InputStream) lVar.invoke(resourcePath);
            if (inputStream != null) {
                Iterable $this$map$iv2 = $this$map$iv;
                String str = resourcePath;
                kotlin.reflect.jvm.internal.impl.name.b bVar = fqName;
                arrayList.add(c.p3.a(fqName, storageManager, module, inputStream, isFallback));
                $this$map$iv = $this$map$iv2;
            } else {
                throw new IllegalStateException("Resource not found in classpath: " + resourcePath);
            }
        }
        List packageFragments = arrayList;
        d0 provider = new d0(packageFragments);
        d0 d0Var = provider;
        kotlin.reflect.jvm.internal.impl.descriptors.a0 notFoundClasses = new kotlin.reflect.jvm.internal.impl.descriptors.a0(jVar, yVar);
        kotlin.reflect.jvm.internal.impl.descriptors.a0 a0Var = notFoundClasses;
        m.a aVar = m.a.a;
        o oVar = r5;
        o oVar2 = new o(provider);
        d0 provider2 = provider;
        a aVar2 = a.n;
        List<c> packageFragments2 = packageFragments;
        kotlin.reflect.jvm.internal.impl.serialization.deserialization.e eVar = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.e(yVar, notFoundClasses, aVar2);
        kotlin.reflect.jvm.internal.impl.descriptors.a0 a0Var2 = notFoundClasses;
        v.a aVar3 = v.a.a;
        kotlin.reflect.jvm.internal.impl.serialization.deserialization.r rVar = kotlin.reflect.jvm.internal.impl.serialization.deserialization.r.a;
        k.b(rVar, "ErrorReporter.DO_NOTHING");
        kotlin.reflect.jvm.internal.impl.serialization.deserialization.l components = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.l(storageManager, module, aVar, oVar, eVar, d0Var, aVar3, rVar, c.a.a, s.a.a, classDescriptorFactories, a0Var, kotlin.reflect.jvm.internal.impl.serialization.deserialization.k.a.a(), additionalClassPartsProvider, platformDependentDeclarationFilter, aVar2.e(), (n) null, 65536, (DefaultConstructorMarker) null);
        for (c packageFragment : packageFragments2) {
            packageFragment.A0(components);
        }
        return provider2;
    }
}
