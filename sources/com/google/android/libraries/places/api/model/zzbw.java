package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzbw implements Parcelable.Creator {
    zzbw() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        String readString = parcel.readString();
        if (readString != null) {
            return TypeFilter.valueOf(readString);
        }
        throw null;
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new TypeFilter[i];
    }
}
