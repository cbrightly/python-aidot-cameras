package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Modality.kt */
public final class x {
    public static final boolean a(@NotNull e $this$isFinalClass) {
        k.f($this$isFinalClass, "$this$isFinalClass");
        return $this$isFinalClass.p() == w.FINAL && $this$isFinalClass.h() != f.ENUM_CLASS;
    }
}
