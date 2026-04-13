package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: javaElements.kt */
public interface d extends l {
    @Nullable
    a c(@NotNull b bVar);

    @NotNull
    Collection<a> getAnnotations();

    boolean w();
}
