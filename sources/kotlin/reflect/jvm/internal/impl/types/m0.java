package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: StarProjectionImpl.kt */
public final class m0 extends x0 {
    private final b0 a;

    public m0(@NotNull g kotlinBuiltIns) {
        k.f(kotlinBuiltIns, "kotlinBuiltIns");
        i0 K = kotlinBuiltIns.K();
        k.b(K, "kotlinBuiltIns.nullableAnyType");
        this.a = K;
    }

    public boolean b() {
        return true;
    }

    @NotNull
    public h1 c() {
        return h1.OUT_VARIANCE;
    }

    @NotNull
    public b0 getType() {
        return this.a;
    }

    @NotNull
    public w0 a(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }
}
