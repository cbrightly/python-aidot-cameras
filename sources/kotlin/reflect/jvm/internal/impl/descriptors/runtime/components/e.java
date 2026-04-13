package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaClassFinder.kt */
public final class e {
    @Nullable
    public static final Class<?> a(@NotNull ClassLoader $this$tryLoadClass, @NotNull String fqName) {
        k.f($this$tryLoadClass, "$this$tryLoadClass");
        k.f(fqName, "fqName");
        try {
            return Class.forName(fqName, false, $this$tryLoadClass);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
