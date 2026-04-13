package kotlin.reflect.jvm.internal;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.k;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: moduleByClassLoader.kt */
public final class z {
    private static final ConcurrentMap<i0, WeakReference<k>> a = new ConcurrentHashMap();

    @NotNull
    public static final k a(@NotNull Class<?> $this$getOrCreateModule) {
        kotlin.jvm.internal.k.f($this$getOrCreateModule, "$this$getOrCreateModule");
        ClassLoader classLoader = b.g($this$getOrCreateModule);
        i0 key = new i0(classLoader);
        ConcurrentMap<i0, WeakReference<k>> concurrentMap = a;
        WeakReference cached = (WeakReference) concurrentMap.get(key);
        if (cached != null) {
            k kVar = (k) cached.get();
            if (kVar != null) {
                k it = kVar;
                kotlin.jvm.internal.k.b(it, "it");
                return it;
            }
            concurrentMap.remove(key, cached);
        }
        k module = k.a.a(classLoader);
        while (true) {
            try {
                ConcurrentMap<i0, WeakReference<k>> concurrentMap2 = a;
                WeakReference ref = concurrentMap2.putIfAbsent(key, new WeakReference(module));
                if (ref != null) {
                    k result = (k) ref.get();
                    if (result != null) {
                        return result;
                    }
                    concurrentMap2.remove(key, ref);
                } else {
                    key.a((ClassLoader) null);
                    return module;
                }
            } finally {
                key.a((ClassLoader) null);
            }
        }
    }
}
