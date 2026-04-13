package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinType.kt */
public abstract class i1 extends b0 {
    /* access modifiers changed from: protected */
    @NotNull
    public abstract b0 M0();

    public i1() {
        super((DefaultConstructorMarker) null);
    }

    public boolean N0() {
        return true;
    }

    @NotNull
    public g getAnnotations() {
        return M0().getAnnotations();
    }

    @NotNull
    public u0 I0() {
        return M0().I0();
    }

    @NotNull
    public List<w0> H0() {
        return M0().H0();
    }

    public boolean J0() {
        return M0().J0();
    }

    @NotNull
    public h l() {
        return M0().l();
    }

    @NotNull
    public final g1 L0() {
        b0 result = M0();
        while (result instanceof i1) {
            result = ((i1) result).M0();
        }
        if (result != null) {
            return (g1) result;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.UnwrappedType");
    }

    @NotNull
    public String toString() {
        if (N0()) {
            return M0().toString();
        }
        return "<Not computed yet>";
    }
}
