package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzpv implements zzpu {
    public static final zzib zza;
    public static final zzib zzb;
    public static final zzib zzc;

    static {
        zzhy zza2 = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zza();
        zza = zza2.zzd("measurement.id.lifecycle.app_in_background_parameter", 0);
        zzb = zza2.zzf("measurement.lifecycle.app_backgrounded_tracking", true);
        zzc = zza2.zzf("measurement.lifecycle.app_in_background_parameter", false);
    }

    public final boolean zza() {
        return ((Boolean) zzc.zzb()).booleanValue();
    }
}
