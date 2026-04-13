package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.libraries.places.api.model.OpeningHours;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzbo implements Parcelable.Creator {
    zzbo() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        String readString = parcel.readString();
        if (readString != null) {
            return OpeningHours.HoursType.valueOf(readString);
        }
        throw null;
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new OpeningHours.HoursType[i];
    }
}
