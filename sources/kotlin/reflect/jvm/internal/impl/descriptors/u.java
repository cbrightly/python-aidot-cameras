package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.z0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FunctionDescriptor */
public interface u extends b {

    /* compiled from: FunctionDescriptor */
    public interface a<D extends u> {
        @NotNull
        a<D> a();

        @NotNull
        a<D> b(@NotNull List<w0> list);

        @Nullable
        D build();

        @NotNull
        a<D> c(@NotNull a1 a1Var);

        @NotNull
        a<D> d(@Nullable l0 l0Var);

        @NotNull
        a<D> e();

        @NotNull
        a<D> f(@Nullable l0 l0Var);

        @NotNull
        a<D> g(@NotNull z0 z0Var);

        @NotNull
        a<D> h();

        @NotNull
        a<D> i(@NotNull f fVar);

        @NotNull
        a<D> j(@NotNull w wVar);

        @NotNull
        a<D> k();

        @NotNull
        a<D> l(@NotNull b0 b0Var);

        @NotNull
        a<D> m(@Nullable b bVar);

        @NotNull
        a<D> n(boolean z);

        @NotNull
        a<D> o(@NotNull List<t0> list);

        @NotNull
        a<D> p(@NotNull m mVar);

        @NotNull
        a<D> q(@NotNull b.a aVar);

        @NotNull
        a<D> r(@NotNull g gVar);

        @NotNull
        a<D> s();
    }

    boolean A();

    @NotNull
    u a();

    @NotNull
    m b();

    @Nullable
    u c(@NotNull TypeSubstitutor typeSubstitutor);

    @NotNull
    Collection<? extends u> d();

    boolean isInfix();

    boolean isInline();

    boolean isOperator();

    boolean isSuspend();

    @Nullable
    u o0();

    @NotNull
    a<? extends u> r();

    boolean x0();

    boolean z0();
}
