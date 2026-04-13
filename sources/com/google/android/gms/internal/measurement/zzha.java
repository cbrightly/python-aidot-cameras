package com.google.android.gms.internal.measurement;

import android.net.Uri;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzha {
    public static final Uri zza = Uri.parse("content://com.google.android.gsf.gservices");
    public static final Uri zzb = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzc = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzd = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    static HashMap zze;
    static final HashMap zzf = new HashMap(16, 1.0f);
    static final HashMap zzg = new HashMap(16, 1.0f);
    static final HashMap zzh = new HashMap(16, 1.0f);
    static final HashMap zzi = new HashMap(16, 1.0f);
    static boolean zzj;
    static final String[] zzk = new String[0];
    /* access modifiers changed from: private */
    public static final AtomicBoolean zzl = new AtomicBoolean();
    private static Object zzm;

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x006f, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0074, code lost:
        r11 = r11.query(zza, (java.lang.String[]) null, (java.lang.String) null, new java.lang.String[]{r12}, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0082, code lost:
        if (r11 != null) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0084, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0089, code lost:
        if (r11.moveToFirst() != false) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008b, code lost:
        zzc(r0, r12, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0091, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r13 = r11.getString(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0097, code lost:
        r11.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009a, code lost:
        if (r13 == null) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a0, code lost:
        if (r13.equals((java.lang.Object) null) == false) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a2, code lost:
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a5, code lost:
        zzc(r0, r12, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a8, code lost:
        if (r13 == null) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00aa, code lost:
        return r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ab, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ac, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ad, code lost:
        r11.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b0, code lost:
        throw r12;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zza(android.content.ContentResolver r11, java.lang.String r12, java.lang.String r13) {
        /*
            java.lang.Class<com.google.android.gms.internal.measurement.zzha> r13 = com.google.android.gms.internal.measurement.zzha.class
            monitor-enter(r13)
            java.util.HashMap r0 = zze     // Catch:{ all -> 0x00b1 }
            r1 = 1
            r2 = 0
            r3 = 0
            if (r0 != 0) goto L_0x002e
            java.util.concurrent.atomic.AtomicBoolean r0 = zzl     // Catch:{ all -> 0x00b1 }
            r0.set(r2)     // Catch:{ all -> 0x00b1 }
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ all -> 0x00b1 }
            r4 = 16
            r5 = 1065353216(0x3f800000, float:1.0)
            r0.<init>(r4, r5)     // Catch:{ all -> 0x00b1 }
            zze = r0     // Catch:{ all -> 0x00b1 }
            java.lang.Object r0 = new java.lang.Object     // Catch:{ all -> 0x00b1 }
            r0.<init>()     // Catch:{ all -> 0x00b1 }
            zzm = r0     // Catch:{ all -> 0x00b1 }
            zzj = r2     // Catch:{ all -> 0x00b1 }
            android.net.Uri r0 = zza     // Catch:{ all -> 0x00b1 }
            com.google.android.gms.internal.measurement.zzgz r4 = new com.google.android.gms.internal.measurement.zzgz     // Catch:{ all -> 0x00b1 }
            r4.<init>(r3)     // Catch:{ all -> 0x00b1 }
            r11.registerContentObserver(r0, r1, r4)     // Catch:{ all -> 0x00b1 }
            goto L_0x0058
        L_0x002e:
            java.util.concurrent.atomic.AtomicBoolean r0 = zzl     // Catch:{ all -> 0x00b1 }
            boolean r0 = r0.getAndSet(r2)     // Catch:{ all -> 0x00b1 }
            if (r0 == 0) goto L_0x0058
            java.util.HashMap r0 = zze     // Catch:{ all -> 0x00b1 }
            r0.clear()     // Catch:{ all -> 0x00b1 }
            java.util.HashMap r0 = zzf     // Catch:{ all -> 0x00b1 }
            r0.clear()     // Catch:{ all -> 0x00b1 }
            java.util.HashMap r0 = zzg     // Catch:{ all -> 0x00b1 }
            r0.clear()     // Catch:{ all -> 0x00b1 }
            java.util.HashMap r0 = zzh     // Catch:{ all -> 0x00b1 }
            r0.clear()     // Catch:{ all -> 0x00b1 }
            java.util.HashMap r0 = zzi     // Catch:{ all -> 0x00b1 }
            r0.clear()     // Catch:{ all -> 0x00b1 }
            java.lang.Object r0 = new java.lang.Object     // Catch:{ all -> 0x00b1 }
            r0.<init>()     // Catch:{ all -> 0x00b1 }
            zzm = r0     // Catch:{ all -> 0x00b1 }
            zzj = r2     // Catch:{ all -> 0x00b1 }
        L_0x0058:
            java.lang.Object r0 = zzm     // Catch:{ all -> 0x00b1 }
            java.util.HashMap r4 = zze     // Catch:{ all -> 0x00b1 }
            boolean r4 = r4.containsKey(r12)     // Catch:{ all -> 0x00b1 }
            if (r4 == 0) goto L_0x0070
            java.util.HashMap r11 = zze     // Catch:{ all -> 0x00b1 }
            java.lang.Object r11 = r11.get(r12)     // Catch:{ all -> 0x00b1 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ all -> 0x00b1 }
            if (r11 != 0) goto L_0x006d
            goto L_0x006e
        L_0x006d:
            r3 = r11
        L_0x006e:
            monitor-exit(r13)     // Catch:{ all -> 0x00b1 }
            return r3
        L_0x0070:
            java.lang.String[] r4 = zzk     // Catch:{ all -> 0x00b1 }
            int r4 = r4.length     // Catch:{ all -> 0x00b1 }
            monitor-exit(r13)     // Catch:{ all -> 0x00b1 }
            android.net.Uri r6 = zza
            r7 = 0
            r8 = 0
            java.lang.String[] r9 = new java.lang.String[r1]
            r9[r2] = r12
            r10 = 0
            r5 = r11
            android.database.Cursor r11 = r5.query(r6, r7, r8, r9, r10)
            if (r11 != 0) goto L_0x0085
            return r3
        L_0x0085:
            boolean r13 = r11.moveToFirst()     // Catch:{ all -> 0x00ac }
            if (r13 != 0) goto L_0x0092
            zzc(r0, r12, r3)     // Catch:{ all -> 0x00ac }
            r11.close()
            return r3
        L_0x0092:
            java.lang.String r13 = r11.getString(r1)     // Catch:{ all -> 0x00ac }
            r11.close()
            if (r13 == 0) goto L_0x00a4
            boolean r11 = r13.equals(r3)
            if (r11 == 0) goto L_0x00a4
            r13 = r3
            goto L_0x00a5
        L_0x00a4:
        L_0x00a5:
            zzc(r0, r12, r13)
            if (r13 == 0) goto L_0x00ab
            return r13
        L_0x00ab:
            return r3
        L_0x00ac:
            r12 = move-exception
            r11.close()
            throw r12
        L_0x00b1:
            r11 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x00b1 }
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzha.zza(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String");
    }

    private static void zzc(Object obj, String str, String str2) {
        synchronized (zzha.class) {
            if (obj == zzm) {
                zze.put(str, str2);
            }
        }
    }
}
