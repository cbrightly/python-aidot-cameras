package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.a;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.checker.s;
import kotlin.reflect.jvm.internal.impl.types.model.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinType.kt */
public abstract class b0 implements a, g {
    private int c;

    @NotNull
    public abstract List<w0> H0();

    @NotNull
    public abstract u0 I0();

    public abstract boolean J0();

    @NotNull
    public abstract b0 K0(@NotNull i iVar);

    @NotNull
    public abstract g1 L0();

    @NotNull
    public abstract h l();

    private b0() {
    }

    public /* synthetic */ b0(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    private final int G0() {
        if (d0.a(this)) {
            return super.hashCode();
        }
        return (((I0().hashCode() * 31) + H0().hashCode()) * 31) + (J0() ? 1 : 0);
    }

    public final int hashCode() {
        int currentHashCode = this.c;
        if (currentHashCode != 0) {
            return currentHashCode;
        }
        int currentHashCode2 = G0();
        this.c = currentHashCode2;
        return currentHashCode2;
    }

    public final boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof b0)) {
            return false;
        }
        if (J0() != ((b0) other).J0() || !s.a.a(L0(), ((b0) other).L0())) {
            return false;
        }
        return true;
    }
}
