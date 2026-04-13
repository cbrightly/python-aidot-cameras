package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.lang.annotation.Annotation;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.p0;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReflectAnnotationSource.kt */
public final class b implements o0 {
    @NotNull
    private final Annotation b;

    public b(@NotNull Annotation annotation) {
        k.f(annotation, "annotation");
        this.b = annotation;
    }

    @NotNull
    public final Annotation d() {
        return this.b;
    }

    @NotNull
    public p0 b() {
        p0 p0Var = p0.a;
        k.b(p0Var, "SourceFile.NO_SOURCE_FILE");
        return p0Var;
    }
}
