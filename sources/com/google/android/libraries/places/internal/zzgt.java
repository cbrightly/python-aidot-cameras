package com.google.android.libraries.places.internal;

import android.content.Context;
import android.os.DropBoxManager;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import java.util.LinkedHashMap;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgt {
    @GuardedBy("CrashReporter.class")
    @Nullable
    private static DropBoxManager zza;
    @GuardedBy("CrashReporter.class")
    private static final LinkedHashMap zzb = new zzgs(16, 0.75f, true);
    @GuardedBy("CrashReporter.class")
    @Nullable
    private static String zzc;

    public static synchronized void zza(Context context, boolean z) {
        synchronized (zzgt.class) {
            if (zza == null) {
                zza = (DropBoxManager) context.getApplicationContext().getSystemService("dropbox");
                zzc = "com.google.android.libraries.places";
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0025, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void zzb(java.lang.Throwable r17) {
        /*
            java.lang.Class<com.google.android.libraries.places.internal.zzgt> r1 = com.google.android.libraries.places.internal.zzgt.class
            monitor-enter(r1)
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x00c1 }
            long r2 = r0.getId()     // Catch:{ all -> 0x00c1 }
            int r4 = r17.hashCode()     // Catch:{ all -> 0x00c1 }
            java.util.LinkedHashMap r0 = zzb     // Catch:{ all -> 0x00c1 }
            java.lang.Long r5 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x00c1 }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x00c1 }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ all -> 0x00c1 }
            if (r0 == 0) goto L_0x0026
            int r0 = r0.intValue()     // Catch:{ all -> 0x00c1 }
            if (r0 == r4) goto L_0x0024
            goto L_0x0026
        L_0x0024:
            monitor-exit(r1)
            return
        L_0x0026:
            android.os.DropBoxManager r0 = zza     // Catch:{ all -> 0x00c1 }
            if (r0 == 0) goto L_0x0024
            java.lang.String r5 = "system_app_crash"
            boolean r0 = r0.isTagEnabled(r5)     // Catch:{ all -> 0x00c1 }
            if (r0 == 0) goto L_0x0024
            android.os.DropBoxManager r5 = zza     // Catch:{ all -> 0x00c1 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c1 }
            r6.<init>()     // Catch:{ all -> 0x00c1 }
            r0 = 3
            java.lang.Object[] r7 = new java.lang.Object[r0]     // Catch:{ all -> 0x00c1 }
            java.lang.String r8 = zzc     // Catch:{ all -> 0x00c1 }
            r9 = 0
            r7[r9] = r8     // Catch:{ all -> 0x00c1 }
            r8 = 46
            com.google.android.libraries.places.internal.zzjc r8 = com.google.android.libraries.places.internal.zzjc.zzb(r8)     // Catch:{ all -> 0x00c1 }
            java.lang.String r10 = "3.1.0"
            java.util.List r8 = r8.zzc(r10)     // Catch:{ all -> 0x00c1 }
            int r10 = r8.size()     // Catch:{ all -> 0x00c1 }
            if (r10 == r0) goto L_0x0057
            r11 = -1
            goto L_0x0077
        L_0x0057:
            r13 = 0
            r0 = r9
        L_0x005a:
            int r10 = r8.size()     // Catch:{ NumberFormatException -> 0x0074 }
            if (r0 >= r10) goto L_0x0072
            r15 = 100
            long r13 = r13 * r15
            java.lang.Object r10 = r8.get(r0)     // Catch:{ NumberFormatException -> 0x0074 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ NumberFormatException -> 0x0074 }
            int r10 = java.lang.Integer.parseInt(r10)     // Catch:{ NumberFormatException -> 0x0074 }
            long r11 = (long) r10
            long r13 = r13 + r11
            int r0 = r0 + 1
            goto L_0x005a
        L_0x0072:
            r11 = r13
            goto L_0x0077
        L_0x0074:
            r0 = move-exception
            r11 = -1
        L_0x0077:
            java.lang.Long r0 = java.lang.Long.valueOf(r11)     // Catch:{ all -> 0x00c1 }
            r8 = 1
            r7[r8] = r0     // Catch:{ all -> 0x00c1 }
            r0 = 2
            java.lang.String r10 = "3.1.0"
            r7[r0] = r10     // Catch:{ all -> 0x00c1 }
            java.lang.String r0 = "Package: %s v%d (%s)\n"
            java.lang.String r0 = java.lang.String.format(r0, r7)     // Catch:{ all -> 0x00c1 }
            r6.append(r0)     // Catch:{ all -> 0x00c1 }
            java.lang.Object[] r0 = new java.lang.Object[r8]     // Catch:{ all -> 0x00c1 }
            java.lang.String r7 = android.os.Build.FINGERPRINT     // Catch:{ all -> 0x00c1 }
            r0[r9] = r7     // Catch:{ all -> 0x00c1 }
            java.lang.String r7 = "Build: %s\n"
            java.lang.String r0 = java.lang.String.format(r7, r0)     // Catch:{ all -> 0x00c1 }
            r6.append(r0)     // Catch:{ all -> 0x00c1 }
            java.lang.String r0 = "\n"
            r6.append(r0)     // Catch:{ all -> 0x00c1 }
            java.lang.String r0 = android.util.Log.getStackTraceString(r17)     // Catch:{ all -> 0x00c1 }
            r6.append(r0)     // Catch:{ all -> 0x00c1 }
            java.lang.String r0 = r6.toString()     // Catch:{ all -> 0x00c1 }
            java.lang.String r6 = "system_app_crash"
            r5.addText(r6, r0)     // Catch:{ all -> 0x00c1 }
            java.util.LinkedHashMap r0 = zzb     // Catch:{ all -> 0x00c1 }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x00c1 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x00c1 }
            r0.put(r2, r3)     // Catch:{ all -> 0x00c1 }
            monitor-exit(r1)
            return
        L_0x00c1:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.internal.zzgt.zzb(java.lang.Throwable):void");
    }
}
