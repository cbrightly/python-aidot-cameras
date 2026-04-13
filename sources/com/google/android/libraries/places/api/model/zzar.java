package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.libraries.places.api.model.OpeningHours;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzar implements Parcelable.Creator {
    zzar() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new zzas((OpeningHours.HoursType) parcel.readParcelable(OpeningHours.class.getClassLoader()), parcel.readArrayList(OpeningHours.class.getClassLoader()), parcel.readArrayList(OpeningHours.class.getClassLoader()), parcel.readArrayList(OpeningHours.class.getClassLoader()));
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzas[i];
    }
}
