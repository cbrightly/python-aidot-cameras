package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.load.java.structure.d;
import org.jetbrains.annotations.NotNull;

/* compiled from: LazyJavaAnnotations.kt */
public final class f {
    @NotNull
    public static final g a(@NotNull h $this$resolveAnnotations, @NotNull d annotationsOwner) {
        k.f($this$resolveAnnotations, "$this$resolveAnnotations");
        k.f(annotationsOwner, "annotationsOwner");
        return new e($this$resolveAnnotations, annotationsOwner);
    }
}
