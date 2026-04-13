package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "LocationSettingsResultCreator")
@SafeParcelable.Reserved({1000})
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class LocationSettingsResult extends AbstractSafeParcelable implements Result {
    @NonNull
    public static final Parcelable.Creator<LocationSettingsResult> CREATOR = new zzaa();
    @SafeParcelable.Field(getter = "getStatus", id = 1)
    private final Status zza;
    @SafeParcelable.Field(getter = "getLocationSettingsStates", id = 2)
    @Nullable
    private final LocationSettingsStates zzb;

    @SafeParcelable.Constructor
    public LocationSettingsResult(@SafeParcelable.Param(id = 1) @NonNull Status status, @SafeParcelable.Param(id = 2) @Nullable LocationSettingsStates locationSettingsStates) {
        this.zza = status;
        this.zzb = locationSettingsStates;
    }

    @Nullable
    public LocationSettingsStates getLocationSettingsStates() {
        return this.zzb;
    }

    @NonNull
    public Status getStatus() {
        return this.zza;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(dest);
        SafeParcelWriter.writeParcelable(dest, 1, getStatus(), flags, false);
        SafeParcelWriter.writeParcelable(dest, 2, getLocationSettingsStates(), flags, false);
        SafeParcelWriter.finishObjectHeader(dest, beginObjectHeader);
    }
}
