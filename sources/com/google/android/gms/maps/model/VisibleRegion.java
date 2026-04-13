package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "VisibleRegionCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class VisibleRegion extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<VisibleRegion> CREATOR = new zzad();
    @SafeParcelable.Field(id = 4)
    @NonNull
    public final LatLng farLeft;
    @SafeParcelable.Field(id = 5)
    @NonNull
    public final LatLng farRight;
    @SafeParcelable.Field(id = 6)
    @NonNull
    public final LatLngBounds latLngBounds;
    @SafeParcelable.Field(id = 2)
    @NonNull
    public final LatLng nearLeft;
    @SafeParcelable.Field(id = 3)
    @NonNull
    public final LatLng nearRight;

    @SafeParcelable.Constructor
    public VisibleRegion(@SafeParcelable.Param(id = 2) @NonNull LatLng nearLeft2, @SafeParcelable.Param(id = 3) @NonNull LatLng nearRight2, @SafeParcelable.Param(id = 4) @NonNull LatLng farLeft2, @SafeParcelable.Param(id = 5) @NonNull LatLng farRight2, @SafeParcelable.Param(id = 6) @NonNull LatLngBounds latLngBounds2) {
        this.nearLeft = nearLeft2;
        this.nearRight = nearRight2;
        this.farLeft = farLeft2;
        this.farRight = farRight2;
        this.latLngBounds = latLngBounds2;
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisibleRegion)) {
            return false;
        }
        VisibleRegion visibleRegion = (VisibleRegion) o;
        return this.nearLeft.equals(visibleRegion.nearLeft) && this.nearRight.equals(visibleRegion.nearRight) && this.farLeft.equals(visibleRegion.farLeft) && this.farRight.equals(visibleRegion.farRight) && this.latLngBounds.equals(visibleRegion.latLngBounds);
    }

    public int hashCode() {
        return Objects.hashCode(this.nearLeft, this.nearRight, this.farLeft, this.farRight, this.latLngBounds);
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("nearLeft", this.nearLeft).add("nearRight", this.nearRight).add("farLeft", this.farLeft).add("farRight", this.farRight).add("latLngBounds", this.latLngBounds).toString();
    }

    public void writeToParcel(@NonNull Parcel out, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeParcelable(out, 2, this.nearLeft, flags, false);
        SafeParcelWriter.writeParcelable(out, 3, this.nearRight, flags, false);
        SafeParcelWriter.writeParcelable(out, 4, this.farLeft, flags, false);
        SafeParcelWriter.writeParcelable(out, 5, this.farRight, flags, false);
        SafeParcelWriter.writeParcelable(out, 6, this.latLngBounds, flags, false);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
