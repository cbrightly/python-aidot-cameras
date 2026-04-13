package coil.util;

import android.graphics.Bitmap;
import android.os.Build;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Bitmaps.kt */
public final class c {
    public static final int b(@Nullable Bitmap.Config $this$bytesPerPixel) {
        if ($this$bytesPerPixel == Bitmap.Config.ALPHA_8) {
            return 1;
        }
        if ($this$bytesPerPixel == Bitmap.Config.RGB_565 || $this$bytesPerPixel == Bitmap.Config.ARGB_4444) {
            return 2;
        }
        if (Build.VERSION.SDK_INT < 26 || $this$bytesPerPixel != Bitmap.Config.RGBA_F16) {
            return 4;
        }
        return 8;
    }

    public static final int a(@NotNull Bitmap $this$allocationByteCountCompat) {
        k.e($this$allocationByteCountCompat, "<this>");
        if (!$this$allocationByteCountCompat.isRecycled()) {
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    return $this$allocationByteCountCompat.getAllocationByteCount();
                }
                return $this$allocationByteCountCompat.getRowBytes() * $this$allocationByteCountCompat.getHeight();
            } catch (Exception e) {
                return o.a.a($this$allocationByteCountCompat.getWidth(), $this$allocationByteCountCompat.getHeight(), $this$allocationByteCountCompat.getConfig());
            }
        } else {
            throw new IllegalStateException(("Cannot obtain size for recycled bitmap: " + $this$allocationByteCountCompat + " [" + $this$allocationByteCountCompat.getWidth() + " x " + $this$allocationByteCountCompat.getHeight() + "] + " + $this$allocationByteCountCompat.getConfig()).toString());
        }
    }

    public static final boolean d(@NotNull Bitmap.Config $this$isHardware) {
        k.e($this$isHardware, "<this>");
        return Build.VERSION.SDK_INT >= 26 && $this$isHardware == Bitmap.Config.HARDWARE;
    }

    @NotNull
    public static final Bitmap.Config c(@NotNull Bitmap $this$safeConfig) {
        k.e($this$safeConfig, "<this>");
        Bitmap.Config config = $this$safeConfig.getConfig();
        return config == null ? Bitmap.Config.ARGB_8888 : config;
    }

    @NotNull
    public static final Bitmap.Config e(@Nullable Bitmap.Config $this$toSoftware) {
        return ($this$toSoftware == null || d($this$toSoftware)) ? Bitmap.Config.ARGB_8888 : $this$toSoftware;
    }
}
