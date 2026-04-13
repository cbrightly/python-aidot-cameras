package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzam extends zzh {
    public static final Parcelable.Creator<zzam> CREATOR = new zzal();

    zzam(ParcelUuid parcelUuid) {
        super(parcelUuid);
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(zza(), i);
    }
}
