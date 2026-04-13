package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(creator = "LocationSettingsRequestCreator")
@SafeParcelable.Reserved({4, 5, 1000})
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class LocationSettingsRequest extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<LocationSettingsRequest> CREATOR = new zzz();
    @SafeParcelable.Field(getter = "getLocationRequests", id = 1)
    private final List zza;
    @SafeParcelable.Field(defaultValue = "false", getter = "alwaysShow", id = 2)
    private final boolean zzb;
    @SafeParcelable.Field(getter = "needBle", id = 3)
    private final boolean zzc;

    /* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
    public static final class Builder {
        private final ArrayList zza = new ArrayList();
        private boolean zzb = false;
        private boolean zzc = false;

        @NonNull
        public Builder addAllLocationRequests(@NonNull Collection<LocationRequest> requests) {
            for (LocationRequest next : requests) {
                if (next != null) {
                    this.zza.add(next);
                }
            }
            return this;
        }

        @NonNull
        public Builder addLocationRequest(@NonNull LocationRequest request) {
            if (request != null) {
                this.zza.add(request);
            }
            return this;
        }

        @NonNull
        public LocationSettingsRequest build() {
            return new LocationSettingsRequest(this.zza, this.zzb, this.zzc);
        }

        @NonNull
        public Builder setAlwaysShow(boolean z) {
            this.zzb = z;
            return this;
        }

        @NonNull
        public Builder setNeedBle(boolean z) {
            this.zzc = z;
            return this;
        }
    }

    @SafeParcelable.Constructor
    LocationSettingsRequest(@SafeParcelable.Param(id = 1) List list, @SafeParcelable.Param(id = 2) boolean z, @SafeParcelable.Param(id = 3) boolean z2) {
        this.zza = list;
        this.zzb = z;
        this.zzc = z2;
    }

    public void writeToParcel(@NonNull Parcel dest, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(dest);
        SafeParcelWriter.writeTypedList(dest, 1, Collections.unmodifiableList(this.zza), false);
        SafeParcelWriter.writeBoolean(dest, 2, this.zzb);
        SafeParcelWriter.writeBoolean(dest, 3, this.zzc);
        SafeParcelWriter.finishObjectHeader(dest, beginObjectHeader);
    }
}
