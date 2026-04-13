package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.checker.g;
import org.jetbrains.annotations.NotNull;

/* compiled from: CapturedTypeApproximation.kt */
public final class d {
    @NotNull
    private final t0 a;
    @NotNull
    private final b0 b;
    @NotNull
    private final b0 c;

    public d(@NotNull t0 typeParameter, @NotNull b0 inProjection, @NotNull b0 outProjection) {
        k.f(typeParameter, "typeParameter");
        k.f(inProjection, "inProjection");
        k.f(outProjection, "outProjection");
        this.a = typeParameter;
        this.b = inProjection;
        this.c = outProjection;
    }

    @NotNull
    public final t0 c() {
        return this.a;
    }

    @NotNull
    public final b0 a() {
        return this.b;
    }

    @NotNull
    public final b0 b() {
        return this.c;
    }

    public final boolean d() {
        return g.a.d(this.b, this.c);
    }
}
