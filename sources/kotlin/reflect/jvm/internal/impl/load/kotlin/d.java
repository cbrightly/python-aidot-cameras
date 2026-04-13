package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.List;
import kotlin.collections.q;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.e;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.incremental.components.c;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.g;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.i;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.k;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.l;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.m;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.r;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.v;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.checker.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: DeserializationComponentsForJava.kt */
public final class d {
    @NotNull
    private final l a;

    public d(@NotNull j storageManager, @NotNull y moduleDescriptor, @NotNull m configuration, @NotNull g classDataFinder, @NotNull c annotationAndConstantLoader, @NotNull g packageFragmentProvider, @NotNull a0 notFoundClasses, @NotNull r errorReporter, @NotNull c lookupTracker, @NotNull k contractDeserializer, @NotNull n kotlinTypeChecker) {
        a aVar;
        kotlin.jvm.internal.k.f(storageManager, "storageManager");
        kotlin.jvm.internal.k.f(moduleDescriptor, "moduleDescriptor");
        kotlin.jvm.internal.k.f(configuration, "configuration");
        kotlin.jvm.internal.k.f(classDataFinder, "classDataFinder");
        kotlin.jvm.internal.k.f(annotationAndConstantLoader, "annotationAndConstantLoader");
        kotlin.jvm.internal.k.f(packageFragmentProvider, "packageFragmentProvider");
        kotlin.jvm.internal.k.f(notFoundClasses, "notFoundClasses");
        kotlin.jvm.internal.k.f(errorReporter, "errorReporter");
        kotlin.jvm.internal.k.f(lookupTracker, "lookupTracker");
        kotlin.jvm.internal.k.f(contractDeserializer, "contractDeserializer");
        kotlin.jvm.internal.k.f(kotlinTypeChecker, "kotlinTypeChecker");
        kotlin.reflect.jvm.internal.impl.builtins.g j = moduleDescriptor.j();
        e jvmBuiltIns = (e) (!(j instanceof e) ? null : j);
        v.a aVar2 = v.a.a;
        h hVar = h.a;
        List g = q.g();
        a aVar3 = (jvmBuiltIns == null || (aVar = jvmBuiltIns.O0()) == null) ? a.C0350a.a : aVar;
        kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c cVar = (jvmBuiltIns == null || (cVar = jvmBuiltIns.O0()) == null) ? c.b.a : cVar;
        e eVar = jvmBuiltIns;
        l lVar = r1;
        l lVar2 = new l(storageManager, moduleDescriptor, configuration, classDataFinder, annotationAndConstantLoader, packageFragmentProvider, aVar2, errorReporter, lookupTracker, hVar, g, notFoundClasses, contractDeserializer, aVar3, cVar, i.b.a(), kotlinTypeChecker);
        this.a = lVar;
    }

    @NotNull
    public final l a() {
        return this.a;
    }
}
