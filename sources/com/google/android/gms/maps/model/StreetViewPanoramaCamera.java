package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

@SafeParcelable.Class(creator = "StreetViewPanoramaCameraCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class StreetViewPanoramaCamera extends AbstractSafeParcelable implements ReflectedParcelable {
    @NonNull
    public static final Parcelable.Creator<StreetViewPanoramaCamera> CREATOR = new zzp();
    @SafeParcelable.Field(id = 4)
    public final float bearing;
    @SafeParcelable.Field(id = 3)
    public final float tilt;
    @SafeParcelable.Field(id = 2)
    public final float zoom;
    private final StreetViewPanoramaOrientation zza;

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public static final class Builder {
        public float bearing;
        public float tilt;
        public float zoom;

        public Builder() {
        }

        public Builder(@NonNull StreetViewPanoramaCamera previous) {
            Preconditions.checkNotNull(previous, "StreetViewPanoramaCamera must not be null.");
            this.zoom = previous.zoom;
            this.bearing = previous.bearing;
            this.tilt = previous.tilt;
        }

        @NonNull
        public Builder bearing(float f) {
            this.bearing = f;
            return this;
        }

        @NonNull
        public StreetViewPanoramaCamera build() {
            return new StreetViewPanoramaCamera(this.zoom, this.tilt, this.bearing);
        }

        @NonNull
        public Builder orientation(@NonNull StreetViewPanoramaOrientation orientation) {
            Preconditions.checkNotNull(orientation, "orientation must not be null.");
            this.tilt = orientation.tilt;
            this.bearing = orientation.bearing;
            return this;
        }

        @NonNull
        public Builder tilt(float f) {
            this.tilt = f;
            return this;
        }

        @NonNull
        public Builder zoom(float f) {
            this.zoom = f;
            return this;
        }
    }

    @SafeParcelable.Constructor
    public StreetViewPanoramaCamera(@SafeParcelable.Param(id = 2) float zoom2, @SafeParcelable.Param(id = 3) float tilt2, @SafeParcelable.Param(id = 4) float bearing2) {
        float f;
        boolean z = false;
        if (tilt2 >= -90.0f && tilt2 <= 90.0f) {
            z = true;
        }
        Preconditions.checkArgument(z, "Tilt needs to be between -90 and 90 inclusive: " + tilt2);
        this.zoom = ((double) zoom2) <= 0.0d ? 0.0f : zoom2;
        this.tilt = 0.0f + tilt2;
        if (((double) bearing2) <= 0.0d) {
            f = (bearing2 % 360.0f) + 360.0f;
        } else {
            f = bearing2;
        }
        this.bearing = f % 360.0f;
        StreetViewPanoramaOrientation.Builder builder = new StreetViewPanoramaOrientation.Builder();
        builder.tilt(tilt2);
        builder.bearing(bearing2);
        this.zza = builder.build();
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    @NonNull
    public static Builder builder(@NonNull StreetViewPanoramaCamera camera) {
        return new Builder(camera);
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StreetViewPanoramaCamera)) {
            return false;
        }
        StreetViewPanoramaCamera streetViewPanoramaCamera = (StreetViewPanoramaCamera) o;
        return Float.floatToIntBits(this.zoom) == Float.floatToIntBits(streetViewPanoramaCamera.zoom) && Float.floatToIntBits(this.tilt) == Float.floatToIntBits(streetViewPanoramaCamera.tilt) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(streetViewPanoramaCamera.bearing);
    }

    @NonNull
    public StreetViewPanoramaOrientation getOrientation() {
        return this.zza;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing));
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("zoom", Float.valueOf(this.zoom)).add("tilt", Float.valueOf(this.tilt)).add("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(@NonNull Parcel out, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeFloat(out, 2, this.zoom);
        SafeParcelWriter.writeFloat(out, 3, this.tilt);
        SafeParcelWriter.writeFloat(out, 4, this.bearing);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
