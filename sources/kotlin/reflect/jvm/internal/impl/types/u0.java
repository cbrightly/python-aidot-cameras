package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.model.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeConstructor */
public interface u0 extends k {
    @NotNull
    u0 a(@NotNull i iVar);

    @NotNull
    Collection<b0> b();

    @Nullable
    h c();

    boolean d();

    @NotNull
    List<t0> getParameters();

    @NotNull
    g j();
}
