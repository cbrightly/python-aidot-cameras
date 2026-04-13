package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.model.l;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypeParameterDescriptor */
public interface t0 extends h, l {
    @NotNull
    j J();

    boolean N();

    @NotNull
    t0 a();

    int getIndex();

    @NotNull
    List<b0> getUpperBounds();

    @NotNull
    u0 i();

    boolean t();

    @NotNull
    h1 y();
}
