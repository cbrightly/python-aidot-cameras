package com.google.android.libraries.places.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzhg implements Parcelable.Creator {
    zzhg() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        String readString = parcel.readString();
        if (readString != null) {
            return (zzhh) Enum.valueOf(zzhh.class, readString);
        }
        throw null;
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzhh[i];
    }
}
