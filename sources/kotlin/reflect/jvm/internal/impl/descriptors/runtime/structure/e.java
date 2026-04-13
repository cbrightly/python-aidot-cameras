package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.a;
import kotlin.reflect.jvm.internal.impl.load.java.structure.c;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaAnnotationArguments.kt */
public final class e extends d implements c {
    private final Annotation c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public e(@Nullable f name, @NotNull Annotation annotation) {
        super(name);
        k.f(annotation, "annotation");
        this.c = annotation;
    }

    @NotNull
    public a a() {
        return new c(this.c);
    }
}
