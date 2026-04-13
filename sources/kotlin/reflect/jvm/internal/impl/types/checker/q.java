package kotlin.reflect.jvm.internal.impl.types.checker;

import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinTypeRefiner.kt */
public final class q<T> {
    @Nullable
    private T a;

    public q(@Nullable T value) {
        this.a = value;
    }

    @Nullable
    public final T a() {
        return this.a;
    }
}
