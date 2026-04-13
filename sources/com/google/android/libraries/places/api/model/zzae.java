package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzae extends zzb {
    public static final Parcelable.Creator<zzae> CREATOR = new zzad();

    zzae(String str, @Nullable String str2, List list) {
        super(str, str2, list);
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getName());
        if (getShortName() == null) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
            parcel.writeString(getShortName());
        }
        parcel.writeList(getTypes());
    }
}
