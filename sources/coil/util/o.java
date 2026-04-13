package coil.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StatFs;
import androidx.annotation.Px;
import androidx.core.content.ContextCompat;
import java.io.File;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Utils.kt */
public final class o {
    @NotNull
    public static final o a = new o();

    private o() {
    }

    @NotNull
    public final Bitmap.Config d() {
        return Build.VERSION.SDK_INT >= 26 ? Bitmap.Config.HARDWARE : Bitmap.Config.ARGB_8888;
    }

    public final int a(@Px int width, @Px int height, @Nullable Bitmap.Config config) {
        return width * height * c.b(config);
    }

    @NotNull
    public final File g(@NotNull Context context) {
        k.e(context, "context");
        File $this$getDefaultCacheDirectory_u24lambda_u2d0 = new File(context.getCacheDir(), "image_cache");
        $this$getDefaultCacheDirectory_u24lambda_u2d0.mkdirs();
        return $this$getDefaultCacheDirectory_u24lambda_u2d0;
    }

    public final long c(@NotNull File cacheDirectory) {
        k.e(cacheDirectory, "cacheDirectory");
        try {
            StatFs cacheDir = new StatFs(cacheDirectory.getAbsolutePath());
            StatFs $this$blockCountCompat$iv = cacheDir;
            int i = Build.VERSION.SDK_INT;
            double blockCountLong = 0.02d * ((double) (i >= 18 ? $this$blockCountCompat$iv.getBlockCountLong() : (long) $this$blockCountCompat$iv.getBlockCount()));
            StatFs $this$blockSizeCompat$iv = cacheDir;
            return n.i((long) (blockCountLong * ((double) (i >= 18 ? $this$blockSizeCompat$iv.getBlockSizeLong() : (long) $this$blockSizeCompat$iv.getBlockSize()))), Constants.MAX_LARGE_FILE_CACHE_SIZE, 262144000);
        } catch (Exception e) {
            return Constants.MAX_LARGE_FILE_CACHE_SIZE;
        }
    }

    public final long b(@NotNull Context context, double percentage) {
        int memoryClassMegabytes;
        k.e(context, "context");
        try {
            Object systemService = ContextCompat.getSystemService(context, ActivityManager.class);
            if (systemService != null) {
                ActivityManager activityManager = (ActivityManager) systemService;
                memoryClassMegabytes = (context.getApplicationInfo().flags & 1048576) != 0 ? activityManager.getLargeMemoryClass() : activityManager.getMemoryClass();
                double d = (double) 1024;
                return (long) (((double) memoryClassMegabytes) * percentage * d * d);
            }
            throw new IllegalStateException(("System service of type " + ActivityManager.class + " was not found.").toString());
        } catch (Exception e) {
            memoryClassMegabytes = 256;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002e A[Catch:{ Exception -> 0x0058 }] */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final double e(@org.jetbrains.annotations.NotNull android.content.Context r8) {
        /*
            r7 = this;
            java.lang.String r0 = "context"
            kotlin.jvm.internal.k.e(r8, r0)
            r0 = r8
            r1 = 0
            r2 = r0
            r3 = 0
            r4 = 4596373779694328218(0x3fc999999999999a, double:0.2)
            java.lang.Class<android.app.ActivityManager> r6 = android.app.ActivityManager.class
            java.lang.Object r6 = androidx.core.content.ContextCompat.getSystemService(r2, r6)     // Catch:{ Exception -> 0x0058 }
            if (r6 == 0) goto L_0x0035
            android.app.ActivityManager r6 = (android.app.ActivityManager) r6     // Catch:{ Exception -> 0x0058 }
            r0 = r6
            r1 = r0
            r2 = 0
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0058 }
            r6 = 19
            if (r3 < r6) goto L_0x002b
            boolean r3 = r1.isLowRamDevice()     // Catch:{ Exception -> 0x0058 }
            if (r3 == 0) goto L_0x0029
            goto L_0x002b
        L_0x0029:
            r3 = 0
            goto L_0x002c
        L_0x002b:
            r3 = 1
        L_0x002c:
            if (r3 == 0) goto L_0x005a
            r1 = 4594572339843380019(0x3fc3333333333333, double:0.15)
            r4 = r1
            goto L_0x005a
        L_0x0035:
            r2 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0058 }
            r3.<init>()     // Catch:{ Exception -> 0x0058 }
            java.lang.String r6 = "System service of type "
            r3.append(r6)     // Catch:{ Exception -> 0x0058 }
            java.lang.Class<android.app.ActivityManager> r6 = android.app.ActivityManager.class
            r3.append(r6)     // Catch:{ Exception -> 0x0058 }
            java.lang.String r6 = " was not found."
            r3.append(r6)     // Catch:{ Exception -> 0x0058 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0058 }
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException     // Catch:{ Exception -> 0x0058 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0058 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0058 }
            throw r2     // Catch:{ Exception -> 0x0058 }
        L_0x0058:
            r0 = move-exception
        L_0x005a:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.util.o.e(android.content.Context):double");
    }

    public final double f() {
        int i = Build.VERSION.SDK_INT;
        if (i >= 24) {
            return 0.0d;
        }
        if (i >= 19) {
            return 0.5d;
        }
        return 0.25d;
    }
}
