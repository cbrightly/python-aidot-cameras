package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzox implements zzow {
    public static final zzib zza;
    public static final zzib zzb;
    public static final zzib zzc;
    public static final zzib zzd;

    static {
        zzhy zza2 = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zza();
        zza = zza2.zzf("measurement.service.audience.fix_skip_audience_with_failed_filters", true);
        zzb = zza2.zzf("measurement.audience.refresh_event_count_filters_timestamp", false);
        zzc = zza2.zzf("measurement.audience.use_bundle_end_timestamp_for_non_sequence_property_filters", false);
        zzd = zza2.zzf("measurement.audience.use_bundle_timestamp_for_event_count_filters", false);
    }

    public final boolean zza() {
        return true;
    }

    public final boolean zzb() {
        return ((Boolean) zzb.zzb()).booleanValue();
    }

    public final boolean zzc() {
        return ((Boolean) zzc.zzb()).booleanValue();
    }

    public final boolean zzd() {
        return ((Boolean) zzd.zzb()).booleanValue();
    }
}
