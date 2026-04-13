package kotlin.io;

import java.io.File;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileTreeWalk.kt */
public class i extends h {
    @NotNull
    public static final d a(@NotNull File $this$walk, @NotNull f direction) {
        k.e($this$walk, "$this$walk");
        k.e(direction, "direction");
        return new d($this$walk, direction);
    }

    @NotNull
    public static final d b(@NotNull File $this$walkBottomUp) {
        k.e($this$walkBottomUp, "$this$walkBottomUp");
        return a($this$walkBottomUp, f.BOTTOM_UP);
    }
}
