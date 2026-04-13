package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: FlexibleTypeDeserializer.kt */
public interface s {
    @NotNull
    b0 a(@NotNull q qVar, @NotNull String str, @NotNull i0 i0Var, @NotNull i0 i0Var2);

    /* compiled from: FlexibleTypeDeserializer.kt */
    public static final class a implements s {
        public static final a a = new a();

        private a() {
        }

        @NotNull
        public b0 a(@NotNull q proto, @NotNull String flexibleId, @NotNull i0 lowerBound, @NotNull i0 upperBound) {
            k.f(proto, "proto");
            k.f(flexibleId, "flexibleId");
            k.f(lowerBound, "lowerBound");
            k.f(upperBound, "upperBound");
            throw new IllegalArgumentException("This method should not be used.");
        }
    }
}
