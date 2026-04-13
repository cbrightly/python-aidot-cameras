package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.g;
import kotlin.reflect.jvm.internal.impl.load.kotlin.n;
import kotlin.reflect.jvm.internal.impl.name.a;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinClassFinder.kt */
public final class o {
    @Nullable
    public static final p b(@NotNull n $this$findKotlinClass, @NotNull a classId) {
        k.f($this$findKotlinClass, "$this$findKotlinClass");
        k.f(classId, "classId");
        n.a c = $this$findKotlinClass.c(classId);
        if (c != null) {
            return c.a();
        }
        return null;
    }

    @Nullable
    public static final p a(@NotNull n $this$findKotlinClass, @NotNull g javaClass) {
        k.f($this$findKotlinClass, "$this$findKotlinClass");
        k.f(javaClass, "javaClass");
        n.a a = $this$findKotlinClass.a(javaClass);
        if (a != null) {
            return a.a();
        }
        return null;
    }
}
