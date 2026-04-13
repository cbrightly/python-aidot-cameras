package com.google.android.libraries.places.api.model;

import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class RectangularBounds implements LocationBias, LocationRestriction {
    @RecentlyNonNull
    public static RectangularBounds newInstance(@RecentlyNonNull LatLng southwest, @RecentlyNonNull LatLng northeast) {
        return newInstance(new LatLngBounds(southwest, northeast));
    }

    @RecentlyNonNull
    public abstract LatLng getNortheast();

    @RecentlyNonNull
    public abstract LatLng getSouthwest();

    @RecentlyNonNull
    public static RectangularBounds newInstance(@RecentlyNonNull LatLngBounds bounds) {
        zzx zzx = new zzx();
        zzx.zzb(bounds.southwest);
        zzx.zza(bounds.northeast);
        return zzx.zzc();
    }
}
