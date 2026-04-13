package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;

/* compiled from: typeParameterUtils.kt */
public final class c implements t0 {
    private final t0 c;
    private final m d;
    private final int f;

    @NotNull
    public j J() {
        return this.c.J();
    }

    @NotNull
    public g getAnnotations() {
        return this.c.getAnnotations();
    }

    @NotNull
    public f getName() {
        return this.c.getName();
    }

    @NotNull
    public List<b0> getUpperBounds() {
        return this.c.getUpperBounds();
    }

    @NotNull
    public u0 i() {
        return this.c.i();
    }

    @NotNull
    public i0 m() {
        return this.c.m();
    }

    @NotNull
    public o0 n() {
        return this.c.n();
    }

    public boolean t() {
        return this.c.t();
    }

    public <R, D> R w(o<R, D> oVar, D d2) {
        return this.c.w(oVar, d2);
    }

    @NotNull
    public h1 y() {
        return this.c.y();
    }

    public c(@NotNull t0 originalDescriptor, @NotNull m declarationDescriptor, int declaredTypeParametersCount) {
        k.f(originalDescriptor, "originalDescriptor");
        k.f(declarationDescriptor, "declarationDescriptor");
        this.c = originalDescriptor;
        this.d = declarationDescriptor;
        this.f = declaredTypeParametersCount;
    }

    public boolean N() {
        return true;
    }

    @NotNull
    public t0 a() {
        t0 a = this.c.a();
        k.b(a, "originalDescriptor.original");
        return a;
    }

    @NotNull
    public m b() {
        return this.d;
    }

    public int getIndex() {
        return this.f + this.c.getIndex();
    }

    @NotNull
    public String toString() {
        return this.c + "[inner-copy]";
    }
}
