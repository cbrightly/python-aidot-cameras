package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Method;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DebugMetadata.kt */
public final class i {
    private static final a a = new a((Method) null, (Method) null, (Method) null);
    @Nullable
    public static a b;
    @NotNull
    public static final i c = new i();

    /* compiled from: DebugMetadata.kt */
    public static final class a {
        @Nullable
        public final Method a;
        @Nullable
        public final Method b;
        @Nullable
        public final Method c;

        public a(@Nullable Method getModuleMethod, @Nullable Method getDescriptorMethod, @Nullable Method nameMethod) {
            this.a = getModuleMethod;
            this.b = getDescriptorMethod;
            this.c = nameMethod;
        }
    }

    private i() {
    }

    @Nullable
    public final String b(@NotNull a continuation) {
        Method method;
        Object module;
        Method method2;
        Object descriptor;
        k.e(continuation, "continuation");
        a cache = b;
        if (cache == null) {
            cache = a(continuation);
        }
        Object obj = null;
        if (cache == a || (method = cache.a) == null || (module = method.invoke(continuation.getClass(), new Object[0])) == null || (method2 = cache.b) == null || (descriptor = method2.invoke(module, new Object[0])) == null) {
            return null;
        }
        Method method3 = cache.c;
        Object invoke = method3 != null ? method3.invoke(descriptor, new Object[0]) : null;
        if (invoke instanceof String) {
            obj = invoke;
        }
        return (String) obj;
    }

    private final a a(a continuation) {
        try {
            a it = new a(Class.class.getDeclaredMethod("getModule", new Class[0]), continuation.getClass().getClassLoader().loadClass("java.lang.Module").getDeclaredMethod("getDescriptor", new Class[0]), continuation.getClass().getClassLoader().loadClass("java.lang.module.ModuleDescriptor").getDeclaredMethod("name", new Class[0]));
            b = it;
            return it;
        } catch (Exception e) {
            a it2 = a;
            b = it2;
            return it2;
        }
    }
}
