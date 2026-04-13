package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzbi extends zzac {
    public static final Parcelable.Creator<zzbi> CREATOR = new zzbh();

    zzbi(@Nullable LocalDate localDate, DayOfWeek dayOfWeek, LocalTime localTime, boolean z) {
        super(localDate, dayOfWeek, localTime, z);
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(getDate(), i);
        parcel.writeParcelable(getDay(), i);
        parcel.writeParcelable(getTime(), i);
        parcel.writeInt(isTruncated() ? 1 : 0);
    }
}
