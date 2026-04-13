package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.h;
import kotlin.reflect.jvm.internal.impl.load.java.structure.u;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaPrimitiveType.kt */
public final class v extends w implements u {
    @NotNull
    private final Class<?> b;

    public v(@NotNull Class<?> reflectType) {
        k.f(reflectType, "reflectType");
        this.b = reflectType;
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: K */
    public Class<?> J() {
        return this.b;
    }

    @Nullable
    public h getType() {
        if (k.a(J(), Void.TYPE)) {
            return null;
        }
        d dVar = d.get(J().getName());
        k.b(dVar, "JvmPrimitiveType.get(reflectType.name)");
        return dVar.getPrimitiveType();
    }
}
