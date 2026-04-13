package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzbd implements Parcelable.Creator {
    zzbd() {
    }

    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new zzbe((LatLng) parcel.readParcelable(RectangularBounds.class.getClassLoader()), (LatLng) parcel.readParcelable(RectangularBounds.class.getClassLoader()));
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbe[i];
    }
}
