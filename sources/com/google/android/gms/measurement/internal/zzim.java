package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import java.net.URL;
import java.util.Map;

@WorkerThread
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzim implements Runnable {
    final /* synthetic */ zzin zza;
    private final URL zzb;
    private final String zzc;
    private final zzgc zzd;

    public zzim(zzin zzin, String str, URL url, byte[] bArr, Map map, zzgc zzgc) {
        this.zza = zzin;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(zzgc);
        this.zzb = url;
        this.zzd = zzgc;
        this.zzc = str;
    }

    private final void zzb(int i, Exception exc, byte[] bArr, Map map) {
        this.zza.zzt.zzaB().zzp(new zzil(this, i, exc, bArr, map));
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0068 A[SYNTHETIC, Splitter:B:26:0x0068] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00a2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r10 = this;
            com.google.android.gms.measurement.internal.zzin r0 = r10.zza
            r0.zzaz()
            r0 = 0
            r1 = 0
            com.google.android.gms.measurement.internal.zzin r2 = r10.zza     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            java.net.URL r3 = r10.zzb     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            java.net.URLConnection r3 = r3.openConnection()     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            boolean r4 = r3 instanceof java.net.HttpURLConnection     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            if (r4 == 0) goto L_0x0082
            java.net.HttpURLConnection r3 = (java.net.HttpURLConnection) r3     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            r3.setDefaultUseCaches(r0)     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            com.google.android.gms.measurement.internal.zzge r4 = r2.zzt     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            r4.zzf()     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            r4 = 60000(0xea60, float:8.4078E-41)
            r3.setConnectTimeout(r4)     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            com.google.android.gms.measurement.internal.zzge r2 = r2.zzt     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            r2.zzf()     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            r2 = 61000(0xee48, float:8.5479E-41)
            r3.setReadTimeout(r2)     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            r3.setInstanceFollowRedirects(r0)     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            r2 = 1
            r3.setDoInput(r2)     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            int r2 = r3.getResponseCode()     // Catch:{ IOException -> 0x007c, all -> 0x0076 }
            java.util.Map r4 = r3.getHeaderFields()     // Catch:{ IOException -> 0x0073, all -> 0x0070 }
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0064 }
            r5.<init>()     // Catch:{ all -> 0x0064 }
            java.io.InputStream r6 = r3.getInputStream()     // Catch:{ all -> 0x0064 }
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r7 = new byte[r7]     // Catch:{ all -> 0x0062 }
        L_0x004a:
            int r8 = r6.read(r7)     // Catch:{ all -> 0x0062 }
            if (r8 <= 0) goto L_0x0054
            r5.write(r7, r0, r8)     // Catch:{ all -> 0x0062 }
            goto L_0x004a
        L_0x0054:
            byte[] r0 = r5.toByteArray()     // Catch:{ all -> 0x0062 }
            r6.close()     // Catch:{ IOException -> 0x006e, all -> 0x006c }
            r3.disconnect()
            r10.zzb(r2, r1, r0, r4)
            return
        L_0x0062:
            r0 = move-exception
            goto L_0x0066
        L_0x0064:
            r0 = move-exception
            r6 = r1
        L_0x0066:
            if (r6 == 0) goto L_0x006b
            r6.close()     // Catch:{ IOException -> 0x006e, all -> 0x006c }
        L_0x006b:
            throw r0     // Catch:{ IOException -> 0x006e, all -> 0x006c }
        L_0x006c:
            r0 = move-exception
            goto L_0x0090
        L_0x006e:
            r0 = move-exception
            goto L_0x00a0
        L_0x0070:
            r0 = move-exception
            r4 = r1
            goto L_0x0090
        L_0x0073:
            r0 = move-exception
            r4 = r1
            goto L_0x00a0
        L_0x0076:
            r2 = move-exception
            r4 = r1
            r9 = r2
            r2 = r0
            r0 = r9
            goto L_0x0090
        L_0x007c:
            r2 = move-exception
            r4 = r1
            r9 = r2
            r2 = r0
            r0 = r9
            goto L_0x00a0
        L_0x0082:
            java.io.IOException r2 = new java.io.IOException     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            java.lang.String r3 = "Failed to obtain HTTP connection"
            r2.<init>(r3)     // Catch:{ IOException -> 0x009a, all -> 0x008a }
            throw r2     // Catch:{ IOException -> 0x009a, all -> 0x008a }
        L_0x008a:
            r2 = move-exception
            r3 = r1
            r4 = r3
            r9 = r2
            r2 = r0
            r0 = r9
        L_0x0090:
            if (r3 == 0) goto L_0x0095
            r3.disconnect()
        L_0x0095:
            r10.zzb(r2, r1, r1, r4)
            throw r0
        L_0x009a:
            r2 = move-exception
            r3 = r1
            r4 = r3
            r9 = r2
            r2 = r0
            r0 = r9
        L_0x00a0:
            if (r3 == 0) goto L_0x00a5
            r3.disconnect()
        L_0x00a5:
            r10.zzb(r2, r0, r1, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzim.run():void");
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zza(int i, Exception exc, byte[] bArr, Map map) {
        zzgc zzgc = this.zzd;
        zzgc.zza.zzC(this.zzc, i, exc, bArr, map);
    }
}
