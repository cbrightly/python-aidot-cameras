package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.load.java.structure.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: resolvers.kt */
public interface m {
    @Nullable
    t0 a(@NotNull w wVar);

    /* compiled from: resolvers.kt */
    public static final class a implements m {
        public static final a a = new a();

        private a() {
        }

        @Nullable
        public t0 a(@NotNull w javaTypeParameter) {
            k.f(javaTypeParameter, "javaTypeParameter");
            return null;
        }
    }
}
