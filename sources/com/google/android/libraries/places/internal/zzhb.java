package com.google.android.libraries.places.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzhb implements Parcelable.Creator {
    zzhb() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str;
        String str2;
        Parcel parcel2 = parcel;
        Class<String> cls = String.class;
        AutocompleteActivityMode autocompleteActivityMode = (AutocompleteActivityMode) parcel2.readParcelable(zzhj.class.getClassLoader());
        zzjq zzj = zzjq.zzj(parcel2.readArrayList(Place.Field.class.getClassLoader()));
        zzhh zzhh = (zzhh) parcel2.readParcelable(zzhj.class.getClassLoader());
        if (parcel.readInt() == 0) {
            str = parcel.readString();
        } else {
            str = null;
        }
        if (parcel.readInt() == 0) {
            str2 = parcel.readString();
        } else {
            str2 = null;
        }
        return new zzhc(autocompleteActivityMode, zzj, zzhh, str, str2, (LocationBias) parcel2.readParcelable(zzhj.class.getClassLoader()), (LocationRestriction) parcel2.readParcelable(zzhj.class.getClassLoader()), zzjq.zzj(parcel2.readArrayList(cls.getClassLoader())), (TypeFilter) parcel2.readParcelable(zzhj.class.getClassLoader()), zzjq.zzj(parcel2.readArrayList(cls.getClassLoader())), parcel.readInt(), parcel.readInt());
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzhc[i];
    }
}
