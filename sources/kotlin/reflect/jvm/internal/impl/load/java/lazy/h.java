package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.g;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.c;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: context.kt */
public final class h {
    @Nullable
    private final g a;
    @NotNull
    private final c b;
    @NotNull
    private final b c;
    @NotNull
    private final m d;
    @NotNull
    private final g<d> e;

    @Nullable
    public final d b() {
        return (d) this.a.getValue();
    }

    public h(@NotNull b components, @NotNull m typeParameterResolver, @NotNull g<d> delegateForDefaultTypeQualifiers) {
        k.f(components, "components");
        k.f(typeParameterResolver, "typeParameterResolver");
        k.f(delegateForDefaultTypeQualifiers, "delegateForDefaultTypeQualifiers");
        this.c = components;
        this.d = typeParameterResolver;
        this.e = delegateForDefaultTypeQualifiers;
        this.a = delegateForDefaultTypeQualifiers;
        this.b = new c(this, typeParameterResolver);
    }

    @NotNull
    public final b a() {
        return this.c;
    }

    @NotNull
    public final m f() {
        return this.d;
    }

    @NotNull
    public final g<d> c() {
        return this.e;
    }

    @NotNull
    public final c g() {
        return this.b;
    }

    @NotNull
    public final j e() {
        return this.c.s();
    }

    @NotNull
    public final y d() {
        return this.c.k();
    }
}
