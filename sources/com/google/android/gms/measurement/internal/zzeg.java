package com.google.android.gms.measurement.internal;

import androidx.annotation.GuardedBy;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzeg {
    private static final Object zza = new Object();
    private final String zzb;
    private final zzed zzc;
    private final Object zzd;
    private final Object zze;
    private final Object zzf = new Object();
    @GuardedBy("overrideLock")
    private volatile Object zzg = null;
    @GuardedBy("cachingLock")
    private volatile Object zzh = null;

    /* synthetic */ zzeg(String str, Object obj, Object obj2, zzed zzed, zzef zzef) {
        this.zzb = str;
        this.zzd = obj;
        this.zze = obj2;
        this.zzc = zzed;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0021, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r4 = com.google.android.gms.measurement.internal.zzeh.zzb().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x002f, code lost:
        if (r4.hasNext() == false) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0031, code lost:
        r0 = (com.google.android.gms.measurement.internal.zzeg) r4.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x003b, code lost:
        if (com.google.android.gms.measurement.internal.zzab.zza() != false) goto L_0x0053;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x003d, code lost:
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r2 = r0.zzc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0040, code lost:
        if (r2 == null) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0042, code lost:
        r1 = r2.zza();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x005a, code lost:
        throw new java.lang.IllegalStateException("Refreshing flag cache must be done on a worker thread.");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object zza(java.lang.Object r4) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.zzf
            monitor-enter(r0)
            monitor-exit(r0)     // Catch:{ all -> 0x006f }
            if (r4 == 0) goto L_0x0007
            return r4
        L_0x0007:
            com.google.android.gms.measurement.internal.zzab r4 = com.google.android.gms.measurement.internal.zzee.zza
            if (r4 != 0) goto L_0x000e
            java.lang.Object r4 = r3.zzd
            return r4
        L_0x000e:
            java.lang.Object r4 = zza
            monitor-enter(r4)
            boolean r0 = com.google.android.gms.measurement.internal.zzab.zza()     // Catch:{ all -> 0x006c }
            if (r0 == 0) goto L_0x0022
            java.lang.Object r0 = r3.zzh     // Catch:{ all -> 0x006c }
            if (r0 != 0) goto L_0x001e
            java.lang.Object r0 = r3.zzd     // Catch:{ all -> 0x006c }
            goto L_0x0020
        L_0x001e:
            java.lang.Object r0 = r3.zzh     // Catch:{ all -> 0x006c }
        L_0x0020:
            monitor-exit(r4)     // Catch:{ all -> 0x006c }
            return r0
        L_0x0022:
            monitor-exit(r4)     // Catch:{ all -> 0x006c }
            java.util.List r4 = com.google.android.gms.measurement.internal.zzeh.zzaI     // Catch:{ SecurityException -> 0x005b }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ SecurityException -> 0x005b }
        L_0x002b:
            boolean r0 = r4.hasNext()     // Catch:{ SecurityException -> 0x005b }
            if (r0 == 0) goto L_0x005c
            java.lang.Object r0 = r4.next()     // Catch:{ SecurityException -> 0x005b }
            com.google.android.gms.measurement.internal.zzeg r0 = (com.google.android.gms.measurement.internal.zzeg) r0     // Catch:{ SecurityException -> 0x005b }
            boolean r1 = com.google.android.gms.measurement.internal.zzab.zza()     // Catch:{ SecurityException -> 0x005b }
            if (r1 != 0) goto L_0x0053
            r1 = 0
            com.google.android.gms.measurement.internal.zzed r2 = r0.zzc     // Catch:{ IllegalStateException -> 0x0048 }
            if (r2 == 0) goto L_0x0047
            java.lang.Object r1 = r2.zza()     // Catch:{ IllegalStateException -> 0x0048 }
            goto L_0x0049
        L_0x0047:
            goto L_0x0049
        L_0x0048:
            r2 = move-exception
        L_0x0049:
            java.lang.Object r2 = zza     // Catch:{ SecurityException -> 0x005b }
            monitor-enter(r2)     // Catch:{ SecurityException -> 0x005b }
            r0.zzh = r1     // Catch:{ all -> 0x0050 }
            monitor-exit(r2)     // Catch:{ all -> 0x0050 }
            goto L_0x002b
        L_0x0050:
            r4 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0050 }
            throw r4     // Catch:{ SecurityException -> 0x005b }
        L_0x0053:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ SecurityException -> 0x005b }
            java.lang.String r0 = "Refreshing flag cache must be done on a worker thread."
            r4.<init>(r0)     // Catch:{ SecurityException -> 0x005b }
            throw r4     // Catch:{ SecurityException -> 0x005b }
        L_0x005b:
            r4 = move-exception
        L_0x005c:
            com.google.android.gms.measurement.internal.zzed r4 = r3.zzc
            if (r4 != 0) goto L_0x0063
        L_0x0060:
            java.lang.Object r4 = r3.zzd
            return r4
        L_0x0063:
            java.lang.Object r4 = r4.zza()     // Catch:{ SecurityException -> 0x006a, IllegalStateException -> 0x0068 }
            return r4
        L_0x0068:
            r4 = move-exception
            goto L_0x0060
        L_0x006a:
            r4 = move-exception
            goto L_0x0060
        L_0x006c:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x006c }
            throw r0
        L_0x006f:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x006f }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzeg.zza(java.lang.Object):java.lang.Object");
    }

    public final String zzb() {
        return this.zzb;
    }
}
