package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.e1;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.k;
import kotlin.reflect.jvm.internal.impl.types.n;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.a;
import kotlin.reflect.jvm.internal.impl.types.v;
import org.jetbrains.annotations.NotNull;

/* compiled from: typeEnhancement.kt */
public final class f extends n implements k {
    @NotNull
    private final i0 d;

    public f(@NotNull i0 delegate) {
        kotlin.jvm.internal.k.f(delegate, "delegate");
        this.d = delegate;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public i0 R0() {
        return this.d;
    }

    public boolean u() {
        return true;
    }

    @NotNull
    public b0 c0(@NotNull b0 replacement) {
        kotlin.jvm.internal.k.f(replacement, "replacement");
        g1 unwrappedType = replacement.L0();
        if (!c1.l(unwrappedType) && !a.j(unwrappedType)) {
            return unwrappedType;
        }
        if (unwrappedType instanceof i0) {
            return U0((i0) unwrappedType);
        }
        if (unwrappedType instanceof v) {
            return e1.d(c0.d(U0(((v) unwrappedType).Q0()), U0(((v) unwrappedType).R0())), e1.a(unwrappedType));
        }
        throw new IllegalStateException(("Incorrect type: " + unwrappedType).toString());
    }

    public boolean J0() {
        return false;
    }

    private final i0 U0(@NotNull i0 $this$prepareReplacement) {
        i0 result = $this$prepareReplacement.P0(false);
        if (!a.j($this$prepareReplacement)) {
            return result;
        }
        return new f(result);
    }

    @NotNull
    /* renamed from: V0 */
    public f Q0(@NotNull g newAnnotations) {
        kotlin.jvm.internal.k.f(newAnnotations, "newAnnotations");
        return new f(R0().Q0(newAnnotations));
    }

    @NotNull
    /* renamed from: P0 */
    public i0 M0(boolean newNullability) {
        return newNullability ? R0().P0(true) : this;
    }

    @NotNull
    /* renamed from: W0 */
    public f T0(@NotNull i0 delegate) {
        kotlin.jvm.internal.k.f(delegate, "delegate");
        return new f(delegate);
    }
}
