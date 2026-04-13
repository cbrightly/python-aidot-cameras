package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.model.p;
import org.jetbrains.annotations.NotNull;

/* compiled from: ClassicTypeSystemContext.kt */
public final class e {
    @NotNull
    public static final p a(@NotNull h1 $this$convertVariance) {
        k.f($this$convertVariance, "$this$convertVariance");
        switch (d.b[$this$convertVariance.ordinal()]) {
            case 1:
                return p.INV;
            case 2:
                return p.IN;
            case 3:
                return p.OUT;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
