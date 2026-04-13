package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;
import javax.annotation.Nullable;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class ProcessUtils {
    @Nullable
    private static String zza = null;
    private static int zzb = 0;

    private ProcessUtils() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.lang.String} */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v4, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    @com.google.android.gms.common.annotation.KeepForSdk
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getMyProcessName() {
        /*
            java.lang.String r0 = zza
            if (r0 != 0) goto L_0x0068
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 28
            if (r0 < r1) goto L_0x0011
            java.lang.String r0 = android.app.Application.getProcessName()
            zza = r0
            goto L_0x0068
        L_0x0011:
            int r0 = zzb
            if (r0 != 0) goto L_0x001b
            int r0 = android.os.Process.myPid()
            zzb = r0
        L_0x001b:
            r1 = 0
            if (r0 > 0) goto L_0x001f
            goto L_0x0066
        L_0x001f:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0061, all -> 0x005c }
            r2.<init>()     // Catch:{ IOException -> 0x0061, all -> 0x005c }
            java.lang.String r3 = "/proc/"
            r2.append(r3)     // Catch:{ IOException -> 0x0061, all -> 0x005c }
            r2.append(r0)     // Catch:{ IOException -> 0x0061, all -> 0x005c }
            java.lang.String r0 = "/cmdline"
            r2.append(r0)     // Catch:{ IOException -> 0x0061, all -> 0x005c }
            java.lang.String r0 = r2.toString()     // Catch:{ IOException -> 0x0061, all -> 0x005c }
            android.os.StrictMode$ThreadPolicy r2 = android.os.StrictMode.allowThreadDiskReads()     // Catch:{ IOException -> 0x0061, all -> 0x005c }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ all -> 0x0057 }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ all -> 0x0057 }
            r4.<init>(r0)     // Catch:{ all -> 0x0057 }
            r3.<init>(r4)     // Catch:{ all -> 0x0057 }
            android.os.StrictMode.setThreadPolicy(r2)     // Catch:{ IOException -> 0x0061, all -> 0x005c }
            java.lang.String r0 = r3.readLine()     // Catch:{ IOException -> 0x0055, all -> 0x0052 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)     // Catch:{ IOException -> 0x0055, all -> 0x0052 }
            java.lang.String r1 = r0.trim()     // Catch:{ IOException -> 0x0055, all -> 0x0052 }
            goto L_0x0063
        L_0x0052:
            r0 = move-exception
            r1 = r3
            goto L_0x005d
        L_0x0055:
            r0 = move-exception
            goto L_0x0063
        L_0x0057:
            r0 = move-exception
            android.os.StrictMode.setThreadPolicy(r2)     // Catch:{ IOException -> 0x0061, all -> 0x005c }
            throw r0     // Catch:{ IOException -> 0x0061, all -> 0x005c }
        L_0x005c:
            r0 = move-exception
        L_0x005d:
            com.google.android.gms.common.util.IOUtils.closeQuietly((java.io.Closeable) r1)
            throw r0
        L_0x0061:
            r0 = move-exception
            r3 = r1
        L_0x0063:
            com.google.android.gms.common.util.IOUtils.closeQuietly((java.io.Closeable) r3)
        L_0x0066:
            zza = r1
        L_0x0068:
            java.lang.String r0 = zza
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.ProcessUtils.getMyProcessName():java.lang.String");
    }
}
