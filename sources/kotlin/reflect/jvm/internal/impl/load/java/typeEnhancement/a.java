package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: typeEnhancement.kt */
public final class a implements c {
    public static final a a = new a();

    private a() {
    }

    @Nullable
    public b e() {
        return c.a.a(this);
    }

    private final Void b() {
        throw new IllegalStateException("No methods should be called on this descriptor. Only its presence matters".toString());
    }

    @NotNull
    public b0 getType() {
        b();
        throw null;
    }

    @NotNull
    public Map<f, g<?>> a() {
        b();
        throw null;
    }

    @NotNull
    public o0 n() {
        b();
        throw null;
    }

    @NotNull
    public String toString() {
        return "[EnhancedType]";
    }
}
