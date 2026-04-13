package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Collection;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: typeSignatureMapping.kt */
public interface w<T> {
    @Nullable
    T a(@NotNull e eVar);

    @Nullable
    String b(@NotNull e eVar);

    @Nullable
    String c(@NotNull e eVar);

    @Nullable
    b0 d(@NotNull b0 b0Var);

    boolean e();

    void f(@NotNull b0 b0Var, @NotNull e eVar);

    @NotNull
    b0 g(@NotNull Collection<b0> collection);

    /* compiled from: typeSignatureMapping.kt */
    public static final class a {
        @Nullable
        public static <T> String a(w<? extends T> $this, @NotNull e classDescriptor) {
            k.f(classDescriptor, "classDescriptor");
            return null;
        }

        @Nullable
        public static <T> b0 b(w<? extends T> $this, @NotNull b0 kotlinType) {
            k.f(kotlinType, "kotlinType");
            return null;
        }

        public static <T> boolean c(w<? extends T> $this) {
            return true;
        }
    }
}
