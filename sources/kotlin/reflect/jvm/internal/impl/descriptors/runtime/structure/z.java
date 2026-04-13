package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import kotlin.collections.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaWildcardType.kt */
public final class z extends w implements kotlin.reflect.jvm.internal.impl.load.java.structure.z {
    @NotNull
    private final WildcardType b;

    public z(@NotNull WildcardType reflectType) {
        k.f(reflectType, "reflectType");
        this.b = reflectType;
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: L */
    public WildcardType J() {
        return this.b;
    }

    @Nullable
    /* renamed from: K */
    public w r() {
        Type[] upperBounds = J().getUpperBounds();
        Type[] lowerBounds = J().getLowerBounds();
        if (upperBounds.length > 1 || lowerBounds.length > 1) {
            throw new UnsupportedOperationException("Wildcard types with many bounds are not yet supported: " + J());
        } else if (lowerBounds.length == 1) {
            w.a aVar = w.a;
            k.b(lowerBounds, "lowerBounds");
            Object J = l.J(lowerBounds);
            k.b(J, "lowerBounds.single()");
            return aVar.a((Type) J);
        } else if (upperBounds.length != 1) {
            return null;
        } else {
            k.b(upperBounds, "upperBounds");
            Type ub = (Type) l.J(upperBounds);
            if (!(true ^ k.a(ub, Object.class))) {
                return null;
            }
            w.a aVar2 = w.a;
            k.b(ub, "ub");
            return aVar2.a(ub);
        }
    }

    public boolean F() {
        Type[] upperBounds = J().getUpperBounds();
        k.b(upperBounds, "reflectType.upperBounds");
        return !k.a((Type) l.v(upperBounds), Object.class);
    }
}
