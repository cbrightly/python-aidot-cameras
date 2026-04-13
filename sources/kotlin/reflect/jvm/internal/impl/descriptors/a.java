package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CallableDescriptor */
public interface a extends n, q, q0<a> {

    /* renamed from: kotlin.reflect.jvm.internal.impl.descriptors.a$a  reason: collision with other inner class name */
    /* compiled from: CallableDescriptor */
    public interface C0348a<V> {
    }

    @Nullable
    l0 I();

    @Nullable
    l0 L();

    boolean Z();

    @NotNull
    a a();

    @NotNull
    Collection<? extends a> d();

    @NotNull
    List<w0> g();

    @Nullable
    b0 getReturnType();

    @NotNull
    List<t0> getTypeParameters();

    @Nullable
    <V> V q0(C0348a<V> aVar);
}
