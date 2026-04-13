package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.w;
import kotlin.reflect.jvm.internal.impl.load.java.structure.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReflectJavaArrayType.kt */
public final class i extends w implements f {
    @NotNull
    private final w b;
    @NotNull
    private final Type c;

    public i(@NotNull Type reflectType) {
        w wVar;
        k.f(reflectType, "reflectType");
        this.c = reflectType;
        Type $this$with = J();
        if ($this$with instanceof GenericArrayType) {
            w.a aVar = w.a;
            Type genericComponentType = ((GenericArrayType) $this$with).getGenericComponentType();
            k.b(genericComponentType, "genericComponentType");
            wVar = aVar.a(genericComponentType);
        } else if (!($this$with instanceof Class) || !((Class) $this$with).isArray()) {
            throw new IllegalArgumentException("Not an array type (" + J().getClass() + "): " + J());
        } else {
            w.a aVar2 = w.a;
            Class<?> componentType = ((Class) $this$with).getComponentType();
            k.b(componentType, "getComponentType()");
            wVar = aVar2.a(componentType);
        }
        this.b = wVar;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Type J() {
        return this.c;
    }

    @NotNull
    /* renamed from: K */
    public w k() {
        return this.b;
    }
}
