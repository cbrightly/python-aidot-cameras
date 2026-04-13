package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: PackageFragmentDescriptorImpl.kt */
public abstract class z extends k implements b0 {
    @NotNull
    private final b x;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public z(@NotNull y module, @NotNull b fqName) {
        super(module, g.b.b(), fqName.h(), o0.a);
        k.f(module, "module");
        k.f(fqName, "fqName");
        this.x = fqName;
    }

    @NotNull
    public final b e() {
        return this.x;
    }

    public <R, D> R w(@NotNull o<R, D> visitor, D data) {
        k.f(visitor, "visitor");
        return visitor.h(this, data);
    }

    @NotNull
    public y b() {
        m b = super.b();
        if (b != null) {
            return (y) b;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ModuleDescriptor");
    }

    @NotNull
    public o0 n() {
        o0 o0Var = o0.a;
        k.b(o0Var, "SourceElement.NO_SOURCE");
        return o0Var;
    }

    @NotNull
    public String toString() {
        return "package " + this.x;
    }
}
