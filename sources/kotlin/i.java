package kotlin;

import kotlin.jvm.functions.a;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: LazyJVM.kt */
public class i {
    @NotNull
    public static final <T> g<T> b(@NotNull a<? extends T> initializer) {
        k.e(initializer, "initializer");
        return new r(initializer, (Object) null, 2, (DefaultConstructorMarker) null);
    }

    @NotNull
    public static final <T> g<T> a(@NotNull k mode, @NotNull a<? extends T> initializer) {
        k.e(mode, "mode");
        k.e(initializer, "initializer");
        switch (h.a[mode.ordinal()]) {
            case 1:
                return new r(initializer, (Object) null, 2, (DefaultConstructorMarker) null);
            case 2:
                return new q(initializer);
            case 3:
                return new y(initializer);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
