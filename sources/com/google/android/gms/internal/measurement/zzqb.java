package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzqb implements zzqa {
    public static final zzib zza;
    public static final zzib zzb;
    public static final zzib zzc;
    public static final zzib zzd;
    public static final zzib zze;

    static {
        zzhy zza2 = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zza();
        zza = zza2.zzf("measurement.test.boolean_flag", false);
        zzb = zza2.zzc("measurement.test.double_flag", -3.0d);
        zzc = zza2.zzd("measurement.test.int_flag", -2);
        zzd = zza2.zzd("measurement.test.long_flag", -1);
        zze = zza2.zze("measurement.test.string_flag", "---");
    }

    public final double zza() {
        return ((Double) zzb.zzb()).doubleValue();
    }

    public final long zzb() {
        return ((Long) zzc.zzb()).longValue();
    }

    public final long zzc() {
        return ((Long) zzd.zzb()).longValue();
    }

    public final String zzd() {
        return (String) zze.zzb();
    }

    public final boolean zze() {
        return ((Boolean) zza.zzb()).booleanValue();
    }
}
