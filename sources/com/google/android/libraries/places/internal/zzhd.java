package com.google.android.libraries.places.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzhd extends zzhk {
    private String zza;
    private zzjq zzb;
    private Place zzc;
    private AutocompletePrediction zzd;
    private Status zze;
    private int zzf;

    zzhd() {
    }

    public final zzhk zza(Place place) {
        this.zzc = place;
        return this;
    }

    public final zzhk zzb(AutocompletePrediction autocompletePrediction) {
        this.zzd = autocompletePrediction;
        return this;
    }

    public final zzhk zzc(List list) {
        this.zzb = zzjq.zzj(list);
        return this;
    }

    public final zzhk zzd(String str) {
        this.zza = str;
        return this;
    }

    public final zzhk zze(Status status) {
        this.zze = status;
        return this;
    }

    public final zzhl zzf() {
        int i = this.zzf;
        if (i != 0) {
            return new zzhf(i, this.zza, this.zzb, this.zzc, this.zzd, this.zze, (zzhe) null);
        }
        throw new IllegalStateException("Missing required properties: type");
    }

    public final zzhk zzg(int i) {
        this.zzf = i;
        return this;
    }
}
