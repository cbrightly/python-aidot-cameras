package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: constantValues.kt */
public abstract class g<T> {
    private final T a;

    @NotNull
    public abstract b0 a(@NotNull y yVar);

    public g(T value) {
        this.a = value;
    }

    public T b() {
        return this.a;
    }

    public boolean equals(@Nullable Object other) {
        if (this != other) {
            Object b = b();
            Object obj = null;
            g gVar = (g) (!(other instanceof g) ? null : other);
            if (gVar != null) {
                obj = gVar.b();
            }
            return k.a(b, obj);
        }
    }

    public int hashCode() {
        Object b = b();
        if (b != null) {
            return b.hashCode();
        }
        return 0;
    }

    @NotNull
    public String toString() {
        return String.valueOf(b());
    }
}
