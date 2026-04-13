package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: IntegerValueTypeConstructor.kt */
public final class q implements u0 {
    private final ArrayList<b0> a;
    private final long b;
    private final y c;

    public /* bridge */ /* synthetic */ h c() {
        return (h) e();
    }

    @NotNull
    public Collection<b0> b() {
        return this.a;
    }

    @NotNull
    public List<t0> getParameters() {
        return kotlin.collections.q.g();
    }

    public boolean d() {
        return false;
    }

    @Nullable
    public Void e() {
        return null;
    }

    @NotNull
    public g j() {
        return this.c.j();
    }

    @NotNull
    public u0 a(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @NotNull
    public String toString() {
        return "IntegerValueType(" + this.b + ')';
    }
}
