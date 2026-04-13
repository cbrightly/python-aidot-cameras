package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzbh implements Parcelable.Creator {
    zzbh() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        LocalDate localDate = (LocalDate) parcel.readParcelable(TimeOfWeek.class.getClassLoader());
        DayOfWeek dayOfWeek = (DayOfWeek) parcel.readParcelable(TimeOfWeek.class.getClassLoader());
        LocalTime localTime = (LocalTime) parcel.readParcelable(TimeOfWeek.class.getClassLoader());
        boolean z = true;
        if (parcel.readInt() != 1) {
            z = false;
        }
        return new zzbi(localDate, dayOfWeek, localTime, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbi[i];
    }
}
