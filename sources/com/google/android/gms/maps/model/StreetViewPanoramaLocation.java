package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "StreetViewPanoramaLocationCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class StreetViewPanoramaLocation extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<StreetViewPanoramaLocation> CREATOR = new zzr();
    @SafeParcelable.Field(id = 2)
    @NonNull
    public final StreetViewPanoramaLink[] links;
    @SafeParcelable.Field(id = 4)
    @NonNull
    public final String panoId;
    @SafeParcelable.Field(id = 3)
    @NonNull
    public final LatLng position;

    @SafeParcelable.Constructor
    public StreetViewPanoramaLocation(@SafeParcelable.Param(id = 2) @NonNull StreetViewPanoramaLink[] links2, @SafeParcelable.Param(id = 3) @NonNull LatLng position2, @SafeParcelable.Param(id = 4) @NonNull String panoId2) {
        this.links = links2;
        this.position = position2;
        this.panoId = panoId2;
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StreetViewPanoramaLocation)) {
            return false;
        }
        StreetViewPanoramaLocation streetViewPanoramaLocation = (StreetViewPanoramaLocation) o;
        return this.panoId.equals(streetViewPanoramaLocation.panoId) && this.position.equals(streetViewPanoramaLocation.position);
    }

    public int hashCode() {
        return Objects.hashCode(this.position, this.panoId);
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("panoId", this.panoId).add("position", this.position.toString()).toString();
    }

    public void writeToParcel(@NonNull Parcel out, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeTypedArray(out, 2, this.links, flags, false);
        SafeParcelWriter.writeParcelable(out, 3, this.position, flags, false);
        SafeParcelWriter.writeString(out, 4, this.panoId, false);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
