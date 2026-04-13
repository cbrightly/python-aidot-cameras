package coil.memory;

import coil.size.Size;
import coil.util.m;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HardwareBitmapService.kt */
public final class i extends h {
    private final boolean b;

    public i(boolean allowHardware) {
        super((DefaultConstructorMarker) null);
        this.b = allowHardware;
    }

    public boolean a(@NotNull Size size, @Nullable m logger) {
        k.e(size, "size");
        return this.b;
    }
}
