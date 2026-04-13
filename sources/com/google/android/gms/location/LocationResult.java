package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(creator = "LocationResultCreator")
@SafeParcelable.Reserved({1000})
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class LocationResult extends AbstractSafeParcelable implements ReflectedParcelable {
    @NonNull
    public static final Parcelable.Creator<LocationResult> CREATOR = new zzy();
    static final List zza = Collections.emptyList();
    @SafeParcelable.Field(defaultValueUnchecked = "LocationResult.DEFAULT_LOCATIONS", getter = "getLocations", id = 1)
    private final List zzb;

    @SafeParcelable.Constructor
    LocationResult(@SafeParcelable.Param(id = 1) List list) {
        this.zzb = list;
    }

    @NonNull
    public static LocationResult create(@NonNull List<Location> locations) {
        if (locations == null) {
            locations = zza;
        }
        return new LocationResult(locations);
    }

    @Nullable
    public static LocationResult extractResult(@NonNull Intent intent) {
        if (!hasResult(intent)) {
            return null;
        }
        LocationResult locationResult = (LocationResult) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.android.gms.location.EXTRA_LOCATION_RESULT_BYTES", CREATOR);
        return locationResult == null ? (LocationResult) intent.getParcelableExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT") : locationResult;
    }

    public static boolean hasResult(@NonNull Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT") || intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT_BYTES");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0038  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(@androidx.annotation.Nullable java.lang.Object r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof com.google.android.gms.location.LocationResult
            r1 = 0
            if (r0 == 0) goto L_0x008d
            com.google.android.gms.location.LocationResult r9 = (com.google.android.gms.location.LocationResult) r9
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 31
            if (r0 < r2) goto L_0x0017
            java.util.List r0 = r8.zzb
            java.util.List r9 = r9.zzb
            boolean r9 = r0.equals(r9)
            return r9
        L_0x0017:
            java.util.List r0 = r8.zzb
            int r0 = r0.size()
            java.util.List r2 = r9.zzb
            int r2 = r2.size()
            if (r0 == r2) goto L_0x0026
            return r1
        L_0x0026:
            java.util.List r0 = r8.zzb
            java.util.Iterator r0 = r0.iterator()
            java.util.List r9 = r9.zzb
            java.util.Iterator r9 = r9.iterator()
        L_0x0032:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x008b
            java.lang.Object r2 = r0.next()
            android.location.Location r2 = (android.location.Location) r2
            java.lang.Object r3 = r9.next()
            android.location.Location r3 = (android.location.Location) r3
            double r4 = r2.getLatitude()
            double r6 = r3.getLatitude()
            int r4 = java.lang.Double.compare(r4, r6)
            if (r4 == 0) goto L_0x0053
            return r1
        L_0x0053:
            double r4 = r2.getLongitude()
            double r6 = r3.getLongitude()
            int r4 = java.lang.Double.compare(r4, r6)
            if (r4 == 0) goto L_0x0062
            return r1
        L_0x0062:
            long r4 = r2.getTime()
            long r6 = r3.getTime()
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 == 0) goto L_0x006f
            return r1
        L_0x006f:
            long r4 = r2.getElapsedRealtimeNanos()
            long r6 = r3.getElapsedRealtimeNanos()
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 == 0) goto L_0x007c
            return r1
        L_0x007c:
            java.lang.String r2 = r2.getProvider()
            java.lang.String r3 = r3.getProvider()
            boolean r2 = com.google.android.gms.common.internal.Objects.equal(r2, r3)
            if (r2 != 0) goto L_0x0032
            return r1
        L_0x008b:
            r9 = 1
            return r9
        L_0x008d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.location.LocationResult.equals(java.lang.Object):boolean");
    }

    @Nullable
    public Location getLastLocation() {
        int size = this.zzb.size();
        if (size == 0) {
            return null;
        }
        return (Location) this.zzb.get(size - 1);
    }

    @NonNull
    public List<Location> getLocations() {
        return this.zzb;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzb);
    }

    @NonNull
    public String toString() {
        return "LocationResult".concat(String.valueOf(String.valueOf(this.zzb)));
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, getLocations(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
