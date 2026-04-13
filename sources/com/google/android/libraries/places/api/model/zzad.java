package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzad implements Parcelable.Creator {
    zzad() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str;
        String readString = parcel.readString();
        if (parcel.readInt() == 0) {
            str = parcel.readString();
        } else {
            str = null;
        }
        return new zzae(readString, str, parcel.readArrayList(AddressComponent.class.getClassLoader()));
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzae[i];
    }
}
