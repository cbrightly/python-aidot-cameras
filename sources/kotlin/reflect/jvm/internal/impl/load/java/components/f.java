package kotlin.reflect.jvm.internal.impl.load.java.components;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.load.java.structure.n;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaPropertyInitializerEvaluator.kt */
public interface f {
    @Nullable
    g<?> a(@NotNull n nVar, @NotNull i0 i0Var);

    /* compiled from: JavaPropertyInitializerEvaluator.kt */
    public static final class a implements f {
        public static final a a = new a();

        private a() {
        }

        @Nullable
        public g<?> a(@NotNull n field, @NotNull i0 descriptor) {
            k.f(field, "field");
            k.f(descriptor, "descriptor");
            return null;
        }
    }
}
