package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "StreetViewPanoramaOrientationCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class StreetViewPanoramaOrientation extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<StreetViewPanoramaOrientation> CREATOR = new zzs();
    @SafeParcelable.Field(id = 3)
    public final float bearing;
    @SafeParcelable.Field(id = 2)
    public final float tilt;

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public static final class Builder {
        public float bearing;
        public float tilt;

        public Builder() {
        }

        public Builder(@NonNull StreetViewPanoramaOrientation previous) {
            Preconditions.checkNotNull(previous, "StreetViewPanoramaOrientation must not be null.");
            this.bearing = previous.bearing;
            this.tilt = previous.tilt;
        }

        @NonNull
        public Builder bearing(float f) {
            this.bearing = f;
            return this;
        }

        @NonNull
        public StreetViewPanoramaOrientation build() {
            return new StreetViewPanoramaOrientation(this.tilt, this.bearing);
        }

        @NonNull
        public Builder tilt(float f) {
            this.tilt = f;
            return this;
        }
    }

    @SafeParcelable.Constructor
    public StreetViewPanoramaOrientation(@SafeParcelable.Param(id = 2) float tilt2, @SafeParcelable.Param(id = 3) float bearing2) {
        boolean z = false;
        if (tilt2 >= -90.0f && tilt2 <= 90.0f) {
            z = true;
        }
        Preconditions.checkArgument(z, "Tilt needs to be between -90 and 90 inclusive: " + tilt2);
        this.tilt = tilt2 + 0.0f;
        this.bearing = (((double) bearing2) <= 0.0d ? (bearing2 % 360.0f) + 360.0f : bearing2) % 360.0f;
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    @NonNull
    public static Builder builder(@NonNull StreetViewPanoramaOrientation orientation) {
        return new Builder(orientation);
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StreetViewPanoramaOrientation)) {
            return false;
        }
        StreetViewPanoramaOrientation streetViewPanoramaOrientation = (StreetViewPanoramaOrientation) o;
        return Float.floatToIntBits(this.tilt) == Float.floatToIntBits(streetViewPanoramaOrientation.tilt) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(streetViewPanoramaOrientation.bearing);
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.tilt), Float.valueOf(this.bearing));
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("tilt", Float.valueOf(this.tilt)).add("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(@NonNull Parcel out, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeFloat(out, 2, this.tilt);
        SafeParcelWriter.writeFloat(out, 3, this.bearing);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
