package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzav implements Parcelable.Creator {
    zzav() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new zzaw(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readString());
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzaw[i];
    }
}
