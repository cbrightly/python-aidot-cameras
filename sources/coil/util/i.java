package coil.util;

import android.graphics.Bitmap;
import android.os.Build;
import kotlin.jvm.internal.k;
import okio.e;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Extensions.kt */
public final class i {
    public static final long a(@NotNull e $this$indexOf, @NotNull f bytes, long fromIndex, long toIndex) {
        e eVar = $this$indexOf;
        f fVar = bytes;
        k.e($this$indexOf, "<this>");
        k.e(fVar, "bytes");
        if (bytes.size() > 0) {
            byte firstByte = fVar.getByte(0);
            long lastIndex = toIndex - ((long) bytes.size());
            long currentIndex = fromIndex;
            while (currentIndex < lastIndex) {
                long currentIndex2 = $this$indexOf.M(firstByte, currentIndex, lastIndex);
                if (currentIndex2 == -1 || $this$indexOf.V(currentIndex2, fVar)) {
                    return currentIndex2;
                }
                currentIndex = currentIndex2 + 1;
            }
            return -1;
        }
        throw new IllegalArgumentException("bytes is empty".toString());
    }

    public static final boolean b(@NotNull Bitmap.Config $this$isHardware) {
        k.e($this$isHardware, "<this>");
        return Build.VERSION.SDK_INT >= 26 && $this$isHardware == Bitmap.Config.HARDWARE;
    }

    @NotNull
    public static final Bitmap.Config c(@Nullable Bitmap.Config $this$toSoftware) {
        return ($this$toSoftware == null || b($this$toSoftware)) ? Bitmap.Config.ARGB_8888 : $this$toSoftware;
    }
}
