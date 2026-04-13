package com.google.android.libraries.places.internal;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzlp {
    private static String zza = "com.google.android.libraries.places.internal.zzlu";
    private static String zzb = "com.google.common.flogger.backend.google.GooglePlatform";
    private static String zzc = "com.google.common.flogger.backend.system.DefaultPlatform";
    /* access modifiers changed from: private */
    public static final String[] zzd = {"com.google.android.libraries.places.internal.zzlu", "com.google.common.flogger.backend.google.GooglePlatform", "com.google.common.flogger.backend.system.DefaultPlatform"};

    public static int zza() {
        return zzmu.zza();
    }

    public static long zzb() {
        return zzln.zza.zzc();
    }

    public static zzky zzd(String className) {
        return zzln.zza.zze(className);
    }

    public static zzla zzf() {
        return zzi().zza();
    }

    public static zzlo zzg() {
        return zzln.zza.zzh();
    }

    public static zzmd zzi() {
        return zzln.zza.zzj();
    }

    public static zzmq zzk() {
        return zzi().zzc();
    }

    public static String zzl() {
        return zzln.zza.zzm();
    }

    public static boolean zzn(String loggerName, Level level, boolean isEnabled) {
        zzi().zzd(loggerName, level, isEnabled);
        return false;
    }

    /* access modifiers changed from: protected */
    public long zzc() {
        return TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis());
    }

    /* access modifiers changed from: protected */
    public abstract zzky zze(String str);

    /* access modifiers changed from: protected */
    public abstract zzlo zzh();

    /* access modifiers changed from: protected */
    public zzmd zzj() {
        return zzmd.zze();
    }

    /* access modifiers changed from: protected */
    public abstract String zzm();
}
