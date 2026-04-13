package coil.memory;

import androidx.annotation.WorkerThread;
import coil.size.PixelSize;
import coil.size.Size;
import coil.util.m;
import java.io.File;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HardwareBitmapService.kt */
public final class l extends h {
    @NotNull
    public static final l b = new l();
    @NotNull
    private static final File c = new File("/proc/self/fd");
    private static volatile int d;
    private static volatile boolean e = true;

    private l() {
        super((DefaultConstructorMarker) null);
    }

    public boolean a(@NotNull Size size, @Nullable m logger) {
        k.e(size, "size");
        if (!(size instanceof PixelSize) || (((PixelSize) size).d() >= 75 && ((PixelSize) size).c() >= 75)) {
            return b(logger);
        }
        return false;
    }

    @WorkerThread
    private final synchronized boolean b(m logger) {
        int i = d;
        d = i + 1;
        if (i >= 50) {
            boolean z = false;
            d = 0;
            Object[] $this$orEmpty$iv = c.list();
            int numUsedFileDescriptors = ($this$orEmpty$iv != null ? $this$orEmpty$iv : new String[0]).length;
            if (numUsedFileDescriptors < 750) {
                z = true;
            }
            e = z;
            if (!e) {
                if (logger != null) {
                    m $this$log$iv = logger;
                    if ($this$log$iv.b() <= 5) {
                        $this$log$iv.a("LimitedFileDescriptorHardwareBitmapService", 5, k.l("Unable to allocate more hardware bitmaps. Number of used file descriptors: ", Integer.valueOf(numUsedFileDescriptors)), (Throwable) null);
                    }
                }
            }
        }
        return e;
    }
}
