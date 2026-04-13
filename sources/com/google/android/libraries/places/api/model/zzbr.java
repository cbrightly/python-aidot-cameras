package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.libraries.places.api.model.Place;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzbr implements Parcelable.Creator {
    zzbr() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        String readString = parcel.readString();
        if (readString != null) {
            return Place.BooleanPlaceAttributeValue.valueOf(readString);
        }
        throw null;
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Place.BooleanPlaceAttributeValue[i];
    }
}
