package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzak extends zzg {
    public static final Parcelable.Creator<zzak> CREATOR = new zzaj();

    zzak(int i, int i2) {
        super(i, i2);
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(zzb());
        parcel.writeInt(zza());
    }
}
