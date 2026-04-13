package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzbl implements Parcelable.Creator {
    zzbl() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        DayOfWeek dayOfWeek = DayOfWeek.SUNDAY;
        String readString = parcel.readString();
        if (readString != null) {
            return DayOfWeek.valueOf(readString);
        }
        throw null;
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DayOfWeek[i];
    }
}
