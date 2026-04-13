package com.google.android.libraries.places.internal;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzhc extends zzha {
    public static final Parcelable.Creator<zzhc> CREATOR = new zzhb();

    zzhc(AutocompleteActivityMode autocompleteActivityMode, zzjq zzjq, zzhh zzhh, @Nullable String str, @Nullable String str2, @Nullable LocationBias locationBias, @Nullable LocationRestriction locationRestriction, zzjq zzjq2, @Nullable TypeFilter typeFilter, zzjq zzjq3, int i, int i2) {
        super(autocompleteActivityMode, zzjq, zzhh, str, str2, locationBias, locationRestriction, zzjq2, typeFilter, zzjq3, i, i2);
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(zzh(), i);
        parcel.writeList(zzj());
        parcel.writeParcelable(zzf(), i);
        if (zzm() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeString(zzm());
        }
        if (zzl() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeString(zzl());
        }
        parcel.writeParcelable(zzc(), i);
        parcel.writeParcelable(zzd(), i);
        parcel.writeList(zzi());
        parcel.writeParcelable(zze(), i);
        parcel.writeList(zzk());
        parcel.writeInt(zza());
        parcel.writeInt(zzb());
    }
}
