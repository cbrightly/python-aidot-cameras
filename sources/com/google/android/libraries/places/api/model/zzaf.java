package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaf implements Parcelable.Creator {
    zzaf() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new zzag(parcel.readArrayList(AddressComponents.class.getClassLoader()));
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzag[i];
    }
}
