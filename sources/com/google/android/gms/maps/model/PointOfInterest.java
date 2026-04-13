package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "PointOfInterestCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class PointOfInterest extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<PointOfInterest> CREATOR = new zzk();
    @SafeParcelable.Field(id = 2)
    @NonNull
    public final LatLng latLng;
    @SafeParcelable.Field(id = 4)
    @NonNull
    public final String name;
    @SafeParcelable.Field(id = 3)
    @NonNull
    public final String placeId;

    @SafeParcelable.Constructor
    public PointOfInterest(@SafeParcelable.Param(id = 2) @NonNull LatLng latLng2, @SafeParcelable.Param(id = 3) @NonNull String placeId2, @SafeParcelable.Param(id = 4) @NonNull String name2) {
        this.latLng = latLng2;
        this.placeId = placeId2;
        this.name = name2;
    }

    public void writeToParcel(@NonNull Parcel out, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeParcelable(out, 2, this.latLng, flags, false);
        SafeParcelWriter.writeString(out, 3, this.placeId, false);
        SafeParcelWriter.writeString(out, 4, this.name, false);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
