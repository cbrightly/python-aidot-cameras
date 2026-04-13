package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzbf implements Parcelable.Creator {
    zzbf() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        LocalDate localDate = (LocalDate) parcel.readParcelable(SpecialDay.class.getClassLoader());
        boolean z = true;
        if (parcel.readInt() != 1) {
            z = false;
        }
        return new zzbg(localDate, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbg[i];
    }
}
