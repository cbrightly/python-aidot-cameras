package kotlin.reflect.jvm.internal;

import java.util.Collection;
import kotlin.jvm.internal.k;
import kotlin.reflect.b;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.l;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EmptyContainerForLocal.kt */
public final class a extends j {
    public static final a q = new a();

    private a() {
    }

    @NotNull
    public Class<?> b() {
        H();
        throw null;
    }

    @NotNull
    public Collection<b<?>> g() {
        H();
        throw null;
    }

    @NotNull
    public Collection<l> v() {
        H();
        throw null;
    }

    @NotNull
    public Collection<i0> A(@NotNull f name) {
        k.f(name, "name");
        H();
        throw null;
    }

    @NotNull
    public Collection<u> w(@NotNull f name) {
        k.f(name, "name");
        H();
        throw null;
    }

    @Nullable
    public i0 x(int index) {
        return null;
    }

    private final Void H() {
        throw new y("Introspecting local functions, lambdas, anonymous functions and local variables is not yet fully supported in Kotlin reflection");
    }
}
