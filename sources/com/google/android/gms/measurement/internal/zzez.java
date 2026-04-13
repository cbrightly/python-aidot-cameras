package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import java.net.URL;
import java.util.Map;

@WorkerThread
/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzez implements Runnable {
    final /* synthetic */ zzfa zza;
    private final URL zzb;
    private final byte[] zzc;
    private final zzew zzd;
    private final String zze;
    private final Map zzf;

    public zzez(zzfa zzfa, String str, URL url, byte[] bArr, Map map, zzew zzew) {
        this.zza = zzfa;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(zzew);
        this.zzb = url;
        this.zzc = bArr;
        this.zzd = zzew;
        this.zze = str;
        this.zzf = map;
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f6 A[SYNTHETIC, Splitter:B:43:0x00f6] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0129 A[SYNTHETIC, Splitter:B:64:0x0129] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0145  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0169 A[SYNTHETIC, Splitter:B:75:0x0169] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0185  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r14 = this;
            java.lang.String r0 = "Error closing HTTP compressed POST connection output stream. appId"
            com.google.android.gms.measurement.internal.zzfa r1 = r14.zza
            r1.zzaz()
            r1 = 0
            r2 = 0
            com.google.android.gms.measurement.internal.zzfa r3 = r14.zza     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            java.net.URL r4 = r14.zzb     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            java.net.URLConnection r4 = r4.openConnection()     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            boolean r5 = r4 instanceof java.net.HttpURLConnection     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            if (r5 == 0) goto L_0x011a
            java.net.HttpURLConnection r4 = (java.net.HttpURLConnection) r4     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            r4.setDefaultUseCaches(r1)     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            com.google.android.gms.measurement.internal.zzge r5 = r3.zzt     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            r5.zzf()     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            r5 = 60000(0xea60, float:8.4078E-41)
            r4.setConnectTimeout(r5)     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            com.google.android.gms.measurement.internal.zzge r3 = r3.zzt     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            r3.zzf()     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            r3 = 61000(0xee48, float:8.5479E-41)
            r4.setReadTimeout(r3)     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            r4.setInstanceFollowRedirects(r1)     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            r3 = 1
            r4.setDoInput(r3)     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            java.util.Map r5 = r14.zzf     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            if (r5 == 0) goto L_0x005f
            java.util.Set r5 = r5.entrySet()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
        L_0x0043:
            boolean r6 = r5.hasNext()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            if (r6 == 0) goto L_0x005f
            java.lang.Object r6 = r5.next()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            java.lang.Object r7 = r6.getKey()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            java.lang.Object r6 = r6.getValue()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            r4.addRequestProperty(r7, r6)     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            goto L_0x0043
        L_0x005f:
            byte[] r5 = r14.zzc     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            if (r5 == 0) goto L_0x00af
            com.google.android.gms.measurement.internal.zzfa r5 = r14.zza     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            com.google.android.gms.measurement.internal.zzlg r5 = r5.zzf     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            com.google.android.gms.measurement.internal.zzli r5 = r5.zzu()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            byte[] r6 = r14.zzc     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            byte[] r5 = r5.zzy(r6)     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            com.google.android.gms.measurement.internal.zzfa r6 = r14.zza     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            com.google.android.gms.measurement.internal.zzge r6 = r6.zzt     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzj()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            java.lang.String r7 = "Uploading data. size"
            int r8 = r5.length     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r8)     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            r6.zzb(r7, r9)     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            r4.setDoOutput(r3)     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            java.lang.String r3 = "Content-Encoding"
            java.lang.String r6 = "gzip"
            r4.addRequestProperty(r3, r6)     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            r4.setFixedLengthStreamingMode(r8)     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            r4.connect()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            java.io.OutputStream r3 = r4.getOutputStream()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            r3.write(r5)     // Catch:{ IOException -> 0x00a8, all -> 0x00a2 }
            r3.close()     // Catch:{ IOException -> 0x00a8, all -> 0x00a2 }
            goto L_0x00af
        L_0x00a2:
            r5 = move-exception
            r9 = r1
            r12 = r2
            r2 = r3
            goto L_0x0127
        L_0x00a8:
            r5 = move-exception
            r9 = r1
            r12 = r2
            r2 = r3
            r10 = r5
            goto L_0x0167
        L_0x00af:
            int r8 = r4.getResponseCode()     // Catch:{ IOException -> 0x0115, all -> 0x0110 }
            java.util.Map r11 = r4.getHeaderFields()     // Catch:{ IOException -> 0x010a, all -> 0x0106 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x00f2 }
            r3.<init>()     // Catch:{ all -> 0x00f2 }
            java.io.InputStream r5 = r4.getInputStream()     // Catch:{ all -> 0x00f2 }
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r6]     // Catch:{ all -> 0x00f0 }
        L_0x00c4:
            int r7 = r5.read(r6)     // Catch:{ all -> 0x00f0 }
            if (r7 <= 0) goto L_0x00ce
            r3.write(r6, r1, r7)     // Catch:{ all -> 0x00f0 }
            goto L_0x00c4
        L_0x00ce:
            byte[] r10 = r3.toByteArray()     // Catch:{ all -> 0x00f0 }
            r5.close()     // Catch:{ IOException -> 0x00ff, all -> 0x00fa }
            r4.disconnect()
            com.google.android.gms.measurement.internal.zzfa r0 = r14.zza
            com.google.android.gms.measurement.internal.zzge r0 = r0.zzt
            com.google.android.gms.measurement.internal.zzgb r0 = r0.zzaB()
            com.google.android.gms.measurement.internal.zzey r1 = new com.google.android.gms.measurement.internal.zzey
            java.lang.String r6 = r14.zze
            com.google.android.gms.measurement.internal.zzew r7 = r14.zzd
            r9 = 0
            r12 = 0
            r5 = r1
            r5.<init>(r6, r7, r8, r9, r10, r11, r12)
        L_0x00ec:
            r0.zzp(r1)
            return
        L_0x00f0:
            r1 = move-exception
            goto L_0x00f4
        L_0x00f2:
            r1 = move-exception
            r5 = r2
        L_0x00f4:
            if (r5 == 0) goto L_0x00f9
            r5.close()     // Catch:{ IOException -> 0x00ff, all -> 0x00fa }
        L_0x00f9:
            throw r1     // Catch:{ IOException -> 0x00ff, all -> 0x00fa }
        L_0x00fa:
            r1 = move-exception
            r5 = r1
            r9 = r8
            r12 = r11
            goto L_0x0127
        L_0x00ff:
            r1 = move-exception
            r5 = r1
            r10 = r5
            r9 = r8
            r12 = r11
            goto L_0x0167
        L_0x0106:
            r5 = move-exception
            r12 = r2
            r9 = r8
            goto L_0x0127
        L_0x010a:
            r5 = move-exception
            r12 = r2
            r10 = r5
            r9 = r8
            goto L_0x0167
        L_0x0110:
            r3 = move-exception
            r9 = r1
            r12 = r2
            r5 = r3
            goto L_0x0127
        L_0x0115:
            r3 = move-exception
            r9 = r1
            r12 = r2
            r10 = r3
            goto L_0x0167
        L_0x011a:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            java.lang.String r4 = "Failed to obtain HTTP connection"
            r3.<init>(r4)     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
            throw r3     // Catch:{ IOException -> 0x0161, all -> 0x0122 }
        L_0x0122:
            r3 = move-exception
            r5 = r3
            r9 = r1
            r4 = r2
            r12 = r4
        L_0x0127:
            if (r2 == 0) goto L_0x0143
            r2.close()     // Catch:{ IOException -> 0x012d }
            goto L_0x0143
        L_0x012d:
            r1 = move-exception
            com.google.android.gms.measurement.internal.zzfa r2 = r14.zza
            com.google.android.gms.measurement.internal.zzge r2 = r2.zzt
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()
            java.lang.String r3 = r14.zze
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)
            r2.zzc(r0, r3, r1)
        L_0x0143:
            if (r4 == 0) goto L_0x0148
            r4.disconnect()
        L_0x0148:
            com.google.android.gms.measurement.internal.zzfa r0 = r14.zza
            com.google.android.gms.measurement.internal.zzge r0 = r0.zzt
            com.google.android.gms.measurement.internal.zzgb r0 = r0.zzaB()
            com.google.android.gms.measurement.internal.zzey r1 = new com.google.android.gms.measurement.internal.zzey
            java.lang.String r7 = r14.zze
            com.google.android.gms.measurement.internal.zzew r8 = r14.zzd
            r10 = 0
            r11 = 0
            r13 = 0
            r6 = r1
            r6.<init>(r7, r8, r9, r10, r11, r12, r13)
            r0.zzp(r1)
            throw r5
        L_0x0161:
            r3 = move-exception
            r5 = r3
            r9 = r1
            r4 = r2
            r12 = r4
            r10 = r5
        L_0x0167:
            if (r2 == 0) goto L_0x0183
            r2.close()     // Catch:{ IOException -> 0x016d }
            goto L_0x0183
        L_0x016d:
            r1 = move-exception
            com.google.android.gms.measurement.internal.zzfa r2 = r14.zza
            com.google.android.gms.measurement.internal.zzge r2 = r2.zzt
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()
            java.lang.String r3 = r14.zze
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)
            r2.zzc(r0, r3, r1)
        L_0x0183:
            if (r4 == 0) goto L_0x0188
            r4.disconnect()
        L_0x0188:
            com.google.android.gms.measurement.internal.zzfa r0 = r14.zza
            com.google.android.gms.measurement.internal.zzge r0 = r0.zzt
            com.google.android.gms.measurement.internal.zzgb r0 = r0.zzaB()
            com.google.android.gms.measurement.internal.zzey r1 = new com.google.android.gms.measurement.internal.zzey
            java.lang.String r7 = r14.zze
            com.google.android.gms.measurement.internal.zzew r8 = r14.zzd
            r11 = 0
            r13 = 0
            r6 = r1
            r6.<init>(r7, r8, r9, r10, r11, r12, r13)
            goto L_0x00ec
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzez.run():void");
    }
}
