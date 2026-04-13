package com.google.android.libraries.places.internal;

import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzhl {
    public static zzhl zzg() {
        return zzr(3).zzf();
    }

    public static zzhl zzk() {
        return zzr(2).zzf();
    }

    public static zzhl zzl() {
        zzhk zzr = zzr(10);
        zzr.zze(new Status(16));
        return zzr.zzf();
    }

    public static zzhl zzo() {
        return zzr(1).zzf();
    }

    public static zzhl zzp() {
        return zzr(4).zzf();
    }

    private static zzhk zzr(int i) {
        zzhd zzhd = new zzhd();
        zzhd.zzg(i);
        return zzhd;
    }

    @Nullable
    public abstract Status zza();

    @Nullable
    public abstract AutocompletePrediction zzb();

    @Nullable
    public abstract Place zzc();

    @Nullable
    public abstract zzjq zzd();

    @Nullable
    public abstract String zze();

    public abstract int zzf();

    public static zzhl zzh(String str) {
        if (str != null) {
            zzhk zzr = zzr(6);
            zzr.zzd(str);
            return zzr.zzf();
        }
        throw null;
    }

    public static zzhl zzi(String str, Status status) {
        if (str == null) {
            throw null;
        } else if (status != null) {
            zzhk zzr = zzr(7);
            zzr.zzd(str);
            zzr.zze(status);
            return zzr.zzf();
        } else {
            throw null;
        }
    }

    public static zzhl zzj(List list) {
        if (list != null) {
            zzhk zzr = zzr(5);
            zzr.zzc(list);
            return zzr.zzf();
        }
        throw null;
    }

    public static zzhl zzm(AutocompletePrediction autocompletePrediction, Status status) {
        if (status != null) {
            zzhk zzr = zzr(9);
            zzr.zzb(autocompletePrediction);
            zzr.zze(status);
            return zzr.zzf();
        }
        throw null;
    }

    public static zzhl zzn(Place place) {
        if (place != null) {
            zzhk zzr = zzr(8);
            zzr.zza(place);
            return zzr.zzf();
        }
        throw null;
    }

    public static zzhl zzq(Status status) {
        if (status != null) {
            zzhk zzr = zzr(10);
            zzr.zze(status);
            return zzr.zzf();
        }
        throw null;
    }
}
