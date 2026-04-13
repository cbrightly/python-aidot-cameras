package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzat implements Parcelable.Creator {
    zzat() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new zzau((TimeOfWeek) parcel.readParcelable(Period.class.getClassLoader()), (TimeOfWeek) parcel.readParcelable(Period.class.getClassLoader()));
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzau[i];
    }
}
