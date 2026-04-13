package com.google.android.libraries.places.internal;

import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue.Builder
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzhi {
    public abstract zzhi zza(List list);

    public abstract zzhi zzb(@Nullable String str);

    public abstract zzhi zzc(@Nullable String str);

    public abstract zzhi zzd(@Nullable LocationBias locationBias);

    public abstract zzhi zze(@Nullable LocationRestriction locationRestriction);

    public abstract zzhi zzf(AutocompleteActivityMode autocompleteActivityMode);

    public abstract zzhi zzg(zzhh zzhh);

    public abstract zzhi zzh(List list);

    public abstract zzhi zzi(int i);

    public abstract zzhi zzj(int i);

    @Deprecated
    public abstract zzhi zzk(@Nullable TypeFilter typeFilter);

    public abstract zzhi zzl(List list);

    public abstract zzhj zzm();

    @Deprecated
    public final zzhi zzn(@Nullable String str) {
        return zza(str == null ? zzjq.zzl() : zzjq.zzm(str));
    }
}
