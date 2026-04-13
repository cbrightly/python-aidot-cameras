package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import kotlin.reflect.jvm.internal.impl.load.java.structure.h;
import kotlin.reflect.jvm.internal.impl.load.java.structure.v;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaAnnotationArguments.kt */
public final class k extends d implements h {
    private final Class<?> c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public k(@Nullable f name, @NotNull Class<?> klass) {
        super(name);
        kotlin.jvm.internal.k.f(klass, "klass");
        this.c = klass;
    }

    @NotNull
    public v b() {
        return w.a.a(this.c);
    }
}
