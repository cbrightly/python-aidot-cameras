package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.o;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaAnnotationArguments.kt */
public final class q extends d implements o {
    @NotNull
    private final Object c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public q(@Nullable f name, @NotNull Object value) {
        super(name);
        k.f(value, "value");
        this.c = value;
    }

    @NotNull
    public Object getValue() {
        return this.c;
    }
}
