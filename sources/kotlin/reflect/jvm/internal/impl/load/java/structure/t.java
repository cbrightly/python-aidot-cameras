package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: javaElements.kt */
public interface t extends d, l {
    @NotNull
    b e();

    @NotNull
    Collection<t> p();

    @NotNull
    Collection<g> y(@NotNull l<? super f, Boolean> lVar);
}
