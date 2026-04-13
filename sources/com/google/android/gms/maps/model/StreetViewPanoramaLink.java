package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "StreetViewPanoramaLinkCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class StreetViewPanoramaLink extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<StreetViewPanoramaLink> CREATOR = new zzq();
    @SafeParcelable.Field(id = 3)
    public final float bearing;
    @SafeParcelable.Field(id = 2)
    @NonNull
    public final String panoId;

    @SafeParcelable.Constructor
    public StreetViewPanoramaLink(@SafeParcelable.Param(id = 2) @NonNull String str, @SafeParcelable.Param(id = 3) float f) {
        this.panoId = str;
        this.bearing = (((double) f) <= 0.0d ? (f % 360.0f) + 360.0f : f) % 360.0f;
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StreetViewPanoramaLink)) {
            return false;
        }
        StreetViewPanoramaLink streetViewPanoramaLink = (StreetViewPanoramaLink) o;
        return this.panoId.equals(streetViewPanoramaLink.panoId) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(streetViewPanoramaLink.bearing);
    }

    public int hashCode() {
        return Objects.hashCode(this.panoId, Float.valueOf(this.bearing));
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("panoId", this.panoId).add("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(@NonNull Parcel out, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeString(out, 2, this.panoId, false);
        SafeParcelWriter.writeFloat(out, 3, this.bearing);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
