package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.m;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaAnnotationArguments.kt */
public final class o extends d implements m {
    private final Enum<?> c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public o(@Nullable f name, @NotNull Enum<?> value) {
        super(name);
        k.f(value, "value");
        this.c = value;
    }

    @Nullable
    public a c() {
        Class clazz = this.c.getClass();
        Class enumClass = clazz.isEnum() ? clazz : clazz.getEnclosingClass();
        k.b(enumClass, "enumClass");
        return b.b(enumClass);
    }

    @Nullable
    public f d() {
        return f.f(this.c.name());
    }
}
