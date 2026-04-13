package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "LatLngCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class LatLng extends AbstractSafeParcelable implements ReflectedParcelable {
    @NonNull
    @KeepForSdk
    public static final Parcelable.Creator<LatLng> CREATOR = new zzg();
    @SafeParcelable.Field(id = 2)
    public final double latitude;
    @SafeParcelable.Field(id = 3)
    public final double longitude;

    @SafeParcelable.Constructor
    public LatLng(@SafeParcelable.Param(id = 2) double latitude2, @SafeParcelable.Param(id = 3) double longitude2) {
        if (longitude2 < -180.0d || longitude2 >= 180.0d) {
            this.longitude = ((((longitude2 - 0.02490234375d) % 360.0d) + 360.0d) % 360.0d) - 0.02490234375d;
        } else {
            this.longitude = longitude2;
        }
        this.latitude = Math.max(-90.0d, Math.min(90.0d, latitude2));
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LatLng)) {
            return false;
        }
        LatLng latLng = (LatLng) o;
        return Double.doubleToLongBits(this.latitude) == Double.doubleToLongBits(latLng.latitude) && Double.doubleToLongBits(this.longitude) == Double.doubleToLongBits(latLng.longitude);
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.latitude);
        long doubleToLongBits2 = Double.doubleToLongBits(this.longitude);
        return ((((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31) * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
    }

    @NonNull
    public String toString() {
        double d = this.latitude;
        double d2 = this.longitude;
        return "lat/lng: (" + d + "," + d2 + ")";
    }

    public void writeToParcel(@NonNull Parcel out, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeDouble(out, 2, this.latitude);
        SafeParcelWriter.writeDouble(out, 3, this.longitude);
        SafeParcelWriter.finishObjectHeader(out, beginObjectHeader);
    }
}
