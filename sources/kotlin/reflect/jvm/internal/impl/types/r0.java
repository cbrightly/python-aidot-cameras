package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeAliasExpansion.kt */
public final class r0 {
    public static final a a = new a((DefaultConstructorMarker) null);
    @Nullable
    private final r0 b;
    @NotNull
    private final s0 c;
    @NotNull
    private final List<w0> d;
    @NotNull
    private final Map<t0, w0> e;

    private r0(r0 parent, s0 descriptor, List<? extends w0> arguments, Map<t0, ? extends w0> mapping) {
        this.b = parent;
        this.c = descriptor;
        this.d = arguments;
        this.e = mapping;
    }

    public /* synthetic */ r0(r0 parent, s0 descriptor, List arguments, Map mapping, DefaultConstructorMarker $constructor_marker) {
        this(parent, descriptor, arguments, mapping);
    }

    @NotNull
    public final s0 b() {
        return this.c;
    }

    @NotNull
    public final List<w0> a() {
        return this.d;
    }

    @Nullable
    public final w0 c(@NotNull u0 constructor) {
        k.f(constructor, "constructor");
        h descriptor = constructor.c();
        if (descriptor instanceof t0) {
            return this.e.get(descriptor);
        }
        return null;
    }

    public final boolean d(@NotNull s0 descriptor) {
        k.f(descriptor, "descriptor");
        if (!k.a(this.c, descriptor)) {
            r0 r0Var = this.b;
            return r0Var != null ? r0Var.d(descriptor) : false;
        }
    }

    /* compiled from: TypeAliasExpansion.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final r0 a(@Nullable r0 parent, @NotNull s0 typeAliasDescriptor, @NotNull List<? extends w0> arguments) {
            k.f(typeAliasDescriptor, "typeAliasDescriptor");
            k.f(arguments, "arguments");
            u0 i = typeAliasDescriptor.i();
            k.b(i, "typeAliasDescriptor.typeConstructor");
            Iterable<t0> $this$mapTo$iv$iv = i.getParameters();
            k.b($this$mapTo$iv$iv, "typeAliasDescriptor.typeConstructor.parameters");
            List typeParameters = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (t0 it : $this$mapTo$iv$iv) {
                k.b(it, "it");
                typeParameters.add(it.a());
            }
            return new r0(parent, typeAliasDescriptor, arguments, l0.o(y.K0(typeParameters, arguments)), (DefaultConstructorMarker) null);
        }
    }
}
