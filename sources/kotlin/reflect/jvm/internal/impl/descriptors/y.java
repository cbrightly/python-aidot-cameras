package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ModuleDescriptor.kt */
public interface y extends m {
    boolean H(@NotNull y yVar);

    @NotNull
    e0 e0(@NotNull kotlin.reflect.jvm.internal.impl.name.b bVar);

    @Nullable
    <T> T i0(@NotNull a<T> aVar);

    @NotNull
    g j();

    @NotNull
    Collection<kotlin.reflect.jvm.internal.impl.name.b> k(@NotNull kotlin.reflect.jvm.internal.impl.name.b bVar, @NotNull l<? super f, Boolean> lVar);

    @NotNull
    List<y> u0();

    /* compiled from: ModuleDescriptor.kt */
    public static final class b {
        @Nullable
        public static m b(y $this) {
            return null;
        }

        public static <R, D> R a(y $this, @NotNull o<R, D> visitor, D data) {
            k.f(visitor, "visitor");
            return visitor.k($this, data);
        }
    }

    /* compiled from: ModuleDescriptor.kt */
    public static final class a<T> {
        @NotNull
        private final String a;

        public a(@NotNull String name) {
            k.f(name, "name");
            this.a = name;
        }

        @NotNull
        public String toString() {
            return this.a;
        }
    }
}
