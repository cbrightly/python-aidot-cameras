package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.g;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.metadata.u;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.a0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AnnotationAndConstantLoader.kt */
public interface c<A, C> {
    @NotNull
    List<A> a(@NotNull a0 a0Var, @NotNull o oVar, @NotNull b bVar, int i, @NotNull u uVar);

    @NotNull
    List<A> b(@NotNull a0.a aVar);

    @NotNull
    List<A> c(@NotNull q qVar, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar);

    @NotNull
    List<A> d(@NotNull a0 a0Var, @NotNull g gVar);

    @NotNull
    List<A> e(@NotNull a0 a0Var, @NotNull o oVar, @NotNull b bVar);

    @NotNull
    List<A> f(@NotNull s sVar, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar);

    @Nullable
    C g(@NotNull a0 a0Var, @NotNull n nVar, @NotNull b0 b0Var);

    @NotNull
    List<A> h(@NotNull a0 a0Var, @NotNull n nVar);

    @NotNull
    List<A> i(@NotNull a0 a0Var, @NotNull o oVar, @NotNull b bVar);

    @NotNull
    List<A> j(@NotNull a0 a0Var, @NotNull n nVar);
}
