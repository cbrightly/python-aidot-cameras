package com.google.android.gms.maps.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.GoogleMapOptions;

@SafeParcelable.Class(creator = "CameraPositionCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class CameraPosition extends AbstractSafeParcelable implements ReflectedParcelable {
    @NonNull
    public static final Parcelable.Creator<CameraPosition> CREATOR = new zza();
    @SafeParcelable.Field(id = 5)
    public final float bearing;
    @SafeParcelable.Field(id = 2)
    @NonNull
    public final LatLng target;
    @SafeParcelable.Field(id = 4)
    public final float tilt;
    @SafeParcelable.Field(id = 3)
    public final float zoom;

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public static final class Builder {
        private LatLng zza;
        private float zzb;
        private float zzc;
        private float zzd;

        public Builder() {
        }

        public Builder(@NonNull CameraPosition previous) {
            CameraPosition cameraPosition = (CameraPosition) Preconditions.checkNotNull(previous, "previous must not be null.");
            this.zza = cameraPosition.target;
            this.zzb = cameraPosition.zoom;
            this.zzc = cameraPosition.tilt;
            this.zzd = cameraPosition.bearing;
        }

        @NonNull
        public Builder bearing(float f) {
            this.zzd = f;
            return this;
        }

        @NonNull
        public CameraPosition build() {
            return new CameraPosition(this.zza, this.zzb, this.zzc, this.zzd);
        }

        @NonNull
        public Builder target(@NonNull LatLng location) {
            this.zza = (LatLng) Preconditions.checkNotNull(location, "location must not be null.");
            return this;
        }

        @NonNull
        public Builder tilt(float f) {
            this.zzc = f;
            return this;
        }

        @NonNull
        public Builder zoom(float f) {
            this.zzb = f;
            return this;
        }
    }

    @SafeParcelable.Constructor
    public CameraPosition(@SafeParcelable.Param(id = 2) @NonNull LatLng target2, @SafeParcelable.Param(id = 3) float zoom2, @SafeParcelable.Param(id = 4) float tilt2, @SafeParcelable.Param(id = 5) float bearing2) {
        boolean z;
        Preconditions.checkNotNull(target2, "camera target must not be null.");
        if (tilt2 < 0.0f || tilt2 > 90.0f) {
            z = false;
        } else {
            z = true;
        }
        Preconditions.checkArgument(z, "Tilt needs to be between 0 and 90 inclusive: %s", Float.valueOf(tilt2));
        this.target = target2;
        this.zoom = zoom2;
        this.tilt = tilt2 + 0.0f;
        this.bearing = (((double) bearing2) <= 0.0d ? (bearing2 % 360.0f) + 360.0f : bearing2) % 360.0f;
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    @NonNull
    public static Builder builder(@NonNull CameraPosition camera) {
        return new Builder(camera);
    }

    @Nullable
    public static CameraPosition createFromAttributes(@Nullable Context context, @Nullable AttributeSet attrs) {
        return GoogleMapOptions.zza(context, attrs);
    }

    @NonNull
    public static final CameraPosition fromLatLngZoom(@NonNull LatLng target2, float zoom2) {
        return new CameraPosition(target2, zoom2, 0.0f, 0.0f);
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CameraPosition)) {
            return false;
        }
        CameraPosition cameraPosition = (CameraPosition) o;
        return this.target.equals(cameraPosition.target) && Float.floatToIntBits(this.zoom) == Float.floatToIntBits(cameraPosition.zoom) && Float.floatToIntBits(this.tilt) == Float.floatToIntBits(cameraPosition.tilt) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(cameraPosition.bearing);
    }

    public int hashCode() {
        return Objects.hashCode(this.target, Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing));
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add(TypedValues.AttributesType.S_TARGET, this.target).add("zoom", Float.valueOf(this.zoom)).add("tilt", Float.valueOf(this.tilt)).add("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(@NonNull Parcel out, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeParcelable(out, 2, this.target, flags, false);
        SafeParcelWriter.writeFloat(out, 3, this.zoom);
        SafeParcelWriter.writeFloat(out, 4, this.tilt);
        SafeParcelWriter.writeFloat(out, 5, this.bearing);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
