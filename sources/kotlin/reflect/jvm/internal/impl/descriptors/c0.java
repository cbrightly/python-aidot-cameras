package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: PackageFragmentProvider.kt */
public interface c0 {
    @NotNull
    List<b0> a(@NotNull b bVar);

    @NotNull
    Collection<b> k(@NotNull b bVar, @NotNull l<? super f, Boolean> lVar);
}
