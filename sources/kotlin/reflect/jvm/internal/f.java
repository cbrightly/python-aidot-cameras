package kotlin.reflect.jvm.internal;

import java.lang.ref.WeakReference;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.pcollections.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: kClassCache.kt */
public final class f {
    private static b<String, Object> a;

    static {
        b<String, Object> b = b.b();
        k.b(b, "HashPMap.empty<String, Any>()");
        a = b;
    }

    @NotNull
    public static final <T> g<T> a(@NotNull Class<T> jClass) {
        k.f(jClass, "jClass");
        String name = jClass.getName();
        Object cached = a.c(name);
        Class<T> cls = null;
        if (cached instanceof WeakReference) {
            g kClass = (g) ((WeakReference) cached).get();
            if (kClass != null) {
                cls = kClass.b();
            }
            if (k.a(cls, jClass)) {
                return kClass;
            }
        } else if (cached != null) {
            WeakReference[] weakReferenceArr = (WeakReference[]) cached;
            for (WeakReference ref : (WeakReference[]) cached) {
                g kClass2 = (g) ref.get();
                if (k.a(kClass2 != null ? kClass2.b() : null, jClass)) {
                    return kClass2;
                }
            }
            int size = ((Object[]) cached).length;
            WeakReference[] newArray = new WeakReference[(size + 1)];
            System.arraycopy(cached, 0, newArray, 0, size);
            g newKClass = new g(jClass);
            newArray[size] = new WeakReference(newKClass);
            b<String, Object> f = a.f(name, newArray);
            k.b(f, "K_CLASS_CACHE.plus(name, newArray)");
            a = f;
            return newKClass;
        }
        g newKClass2 = new g(jClass);
        b<String, Object> f2 = a.f(name, new WeakReference(newKClass2));
        k.b(f2, "K_CLASS_CACHE.plus(name, WeakReference(newKClass))");
        a = f2;
        return newKClass2;
    }
}
