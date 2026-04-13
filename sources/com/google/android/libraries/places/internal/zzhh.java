package com.google.android.libraries.places.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public enum zzhh implements Parcelable {
    FRAGMENT,
    INTENT;
    
    public static final Parcelable.Creator<zzhh> CREATOR = null;

    static {
        CREATOR = new zzhg();
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
