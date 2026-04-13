package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzal implements Parcelable.Creator {
    zzal() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new zzam((ParcelUuid) parcel.readParcelable(AutocompleteSessionToken.class.getClassLoader()));
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzam[i];
    }
}
