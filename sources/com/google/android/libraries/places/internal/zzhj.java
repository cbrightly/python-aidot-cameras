package com.google.android.libraries.places.internal;

import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.auto.value.AutoValue;
import java.util.ArrayList;
import java.util.List;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzhj implements Parcelable {
    public static zzhi zzn(AutocompleteActivityMode autocompleteActivityMode, List list, zzhh zzhh) {
        zzgz zzgz = new zzgz();
        zzgz.zza(new ArrayList());
        zzgz.zzl(new ArrayList());
        zzgz.zzf(autocompleteActivityMode);
        zzgz.zzh(list);
        zzgz.zzg(zzhh);
        zzgz.zzi(0);
        zzgz.zzj(0);
        return zzgz;
    }

    public abstract int zza();

    public abstract int zzb();

    @Nullable
    public abstract LocationBias zzc();

    @Nullable
    public abstract LocationRestriction zzd();

    @Deprecated
    @Nullable
    public abstract TypeFilter zze();

    public abstract zzhh zzf();

    public abstract zzhi zzg();

    public abstract AutocompleteActivityMode zzh();

    public abstract zzjq zzi();

    public abstract zzjq zzj();

    public abstract zzjq zzk();

    @Nullable
    public abstract String zzl();

    @Nullable
    public abstract String zzm();
}
