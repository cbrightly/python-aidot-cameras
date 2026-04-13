package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.StrictMode;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.internal.zzag;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.errorprone.annotations.CheckReturnValue;
import java.security.MessageDigest;

@CheckReturnValue
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class zzn {
    static final zzl zza = new zzf(zzj.zze("0\u0005È0\u0003° \u0003\u0002\u0001\u0002\u0002\u0014\u0010e\bsù/Qí"));
    static final zzl zzb = new zzg(zzj.zze("0\u0006\u00040\u0003ì \u0003\u0002\u0001\u0002\u0002\u0014\u0003£²­×árÊkì"));
    static final zzl zzc = new zzh(zzj.zze("0\u0004C0\u0003+ \u0003\u0002\u0001\u0002\u0002\t\u0000ÂàFdJ00"));
    static final zzl zzd = new zzi(zzj.zze("0\u0004¨0\u0003 \u0003\u0002\u0001\u0002\u0002\t\u0000Õ¸l}ÓNõ0"));
    private static volatile zzag zze;
    private static final Object zzf = new Object();
    private static Context zzg;

    static zzx zza(String str, zzj zzj, boolean z, boolean z2) {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            return zzh(str, zzj, z, z2);
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }

    static zzx zzb(String str, boolean z, boolean z2, boolean z3) {
        return zzi(str, z, false, false, true);
    }

    static zzx zzc(String str, boolean z, boolean z2, boolean z3) {
        return zzi(str, z, false, false, false);
    }

    static /* synthetic */ String zzd(boolean z, String str, zzj zzj) {
        boolean z2;
        String str2;
        if (z || !zzh(str, zzj, true, false).zza) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (true != z2) {
            str2 = "not allowed";
        } else {
            str2 = "debug cert rejected";
        }
        MessageDigest zza2 = AndroidUtilsLight.zza("SHA-256");
        Preconditions.checkNotNull(zza2);
        return String.format("%s: pkg=%s, sha256=%s, atk=%s, ver=%s", new Object[]{str2, str, Hex.bytesToStringLowercase(zza2.digest(zzj.zzf())), Boolean.valueOf(z), "12451000.false"});
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0010, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized void zze(android.content.Context r2) {
        /*
            java.lang.Class<com.google.android.gms.common.zzn> r0 = com.google.android.gms.common.zzn.class
            monitor-enter(r0)
            android.content.Context r1 = zzg     // Catch:{ all -> 0x001a }
            if (r1 != 0) goto L_0x0011
            if (r2 == 0) goto L_0x000f
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ all -> 0x001a }
            zzg = r2     // Catch:{ all -> 0x001a }
        L_0x000f:
            monitor-exit(r0)
            return
        L_0x0011:
            java.lang.String r2 = "GoogleCertificates"
            java.lang.String r1 = "GoogleCertificates has been initialized already"
            android.util.Log.w(r2, r1)     // Catch:{ all -> 0x001a }
            monitor-exit(r0)
            return
        L_0x001a:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.zzn.zze(android.content.Context):void");
    }

    /* JADX INFO: finally extract failed */
    static boolean zzf() {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            zzj();
            boolean zzg2 = zze.zzg();
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return zzg2;
        } catch (RemoteException | DynamiteModule.LoadingException e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return false;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    static boolean zzg() {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            zzj();
            boolean zzi = zze.zzi();
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return zzi;
        } catch (RemoteException | DynamiteModule.LoadingException e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return false;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th;
        }
    }

    private static zzx zzh(String str, zzj zzj, boolean z, boolean z2) {
        try {
            zzj();
            Preconditions.checkNotNull(zzg);
            try {
                return zze.zzh(new zzs(str, zzj, z, z2), ObjectWrapper.wrap(zzg.getPackageManager())) ? zzx.zzb() : new zzv(new zze(z, str, zzj), (zzu) null);
            } catch (RemoteException e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                return zzx.zzd("module call", e);
            }
        } catch (DynamiteModule.LoadingException e2) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e2);
            return zzx.zzd("module init: ".concat(String.valueOf(e2.getMessage())), e2);
        }
    }

    /* JADX WARNING: type inference failed for: r6v0, types: [com.google.android.gms.dynamic.IObjectWrapper, android.os.IBinder] */
    private static zzx zzi(String str, boolean z, boolean z2, boolean z3, boolean z4) {
        zzx zzx;
        zzq zzq;
        PackageManager.NameNotFoundException nameNotFoundException;
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            Preconditions.checkNotNull(zzg);
            try {
                zzj();
                zzo zzo = new zzo(str, z, false, ObjectWrapper.wrap(zzg), false, true);
                if (z4) {
                    zzq = zze.zze(zzo);
                } else {
                    zzq = zze.zzf(zzo);
                }
                if (zzq.zzb()) {
                    zzx = zzx.zzf(zzq.zzc());
                } else {
                    String zza2 = zzq.zza();
                    if (zzq.zzd() == 4) {
                        nameNotFoundException = new PackageManager.NameNotFoundException();
                    } else {
                        nameNotFoundException = null;
                    }
                    if (zza2 == null) {
                        zza2 = "error checking package certificate";
                    }
                    zzx = zzx.zzg(zzq.zzc(), zzq.zzd(), zza2, nameNotFoundException);
                }
            } catch (DynamiteModule.LoadingException e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                zzx = zzx.zzd("module init: ".concat(String.valueOf(e.getMessage())), e);
            }
        } catch (RemoteException e2) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e2);
            zzx = zzx.zzd("module call", e2);
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th;
        }
        StrictMode.setThreadPolicy(allowThreadDiskReads);
        return zzx;
    }

    private static void zzj() {
        if (zze == null) {
            Preconditions.checkNotNull(zzg);
            synchronized (zzf) {
                if (zze == null) {
                    zze = zzaf.zzb(DynamiteModule.load(zzg, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, "com.google.android.gms.googlecertificates").instantiate("com.google.android.gms.common.GoogleCertificatesImpl"));
                }
            }
        }
    }
}
