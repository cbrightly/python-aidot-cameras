package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.location.zzd;
import com.google.android.gms.internal.location.zzdj;
import org.checkerframework.dataflow.qual.Pure;

@SafeParcelable.Class(creator = "LastLocationRequestCreator")
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class LastLocationRequest extends AbstractSafeParcelable {
    @NonNull
    public static final Parcelable.Creator<LastLocationRequest> CREATOR = new zzv();
    @SafeParcelable.Field(defaultValueUnchecked = "Long.MAX_VALUE", getter = "getMaxUpdateAgeMillis", id = 1)
    private final long zza;
    @SafeParcelable.Field(defaultValueUnchecked = "com.google.android.gms.location.Granularity.GRANULARITY_PERMISSION_LEVEL", getter = "getGranularity", id = 2)
    private final int zzb;
    @SafeParcelable.Field(defaultValue = "false", getter = "isBypass", id = 3)
    private final boolean zzc;
    @SafeParcelable.Field(getter = "getModuleId", id = 4)
    @Nullable
    private final String zzd;
    @SafeParcelable.Field(getter = "getImpersonation", id = 5)
    @Nullable
    private final zzd zze;

    /* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
    public static final class Builder {
        private long zza;
        private int zzb;
        private boolean zzc;
        @Nullable
        private String zzd;
        @Nullable
        private zzd zze;

        public Builder() {
            this.zza = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            this.zzb = 0;
            this.zzc = false;
            this.zzd = null;
            this.zze = null;
        }

        public Builder(@NonNull LastLocationRequest request) {
            this.zza = request.getMaxUpdateAgeMillis();
            this.zzb = request.getGranularity();
            this.zzc = request.zzc();
            this.zzd = request.zzb();
            this.zze = request.zza();
        }

        @NonNull
        public LastLocationRequest build() {
            return new LastLocationRequest(this.zza, this.zzb, this.zzc, this.zzd, this.zze);
        }

        @NonNull
        public Builder setGranularity(int granularity) {
            zzo.zza(granularity);
            this.zzb = granularity;
            return this;
        }

        @NonNull
        public Builder setMaxUpdateAgeMillis(long maxUpdateAgeMillis) {
            Preconditions.checkArgument(maxUpdateAgeMillis > 0, "maxUpdateAgeMillis must be greater than 0");
            this.zza = maxUpdateAgeMillis;
            return this;
        }
    }

    @SafeParcelable.Constructor
    LastLocationRequest(@SafeParcelable.Param(id = 1) long j, @SafeParcelable.Param(id = 2) int i, @SafeParcelable.Param(id = 3) boolean z, @SafeParcelable.Param(id = 4) @Nullable String str, @SafeParcelable.Param(id = 5) @Nullable zzd zzd2) {
        this.zza = j;
        this.zzb = i;
        this.zzc = z;
        this.zzd = str;
        this.zze = zzd2;
    }

    public boolean equals(@Nullable Object object) {
        if (!(object instanceof LastLocationRequest)) {
            return false;
        }
        LastLocationRequest lastLocationRequest = (LastLocationRequest) object;
        if (this.zza == lastLocationRequest.zza && this.zzb == lastLocationRequest.zzb && this.zzc == lastLocationRequest.zzc && Objects.equal(this.zzd, lastLocationRequest.zzd) && Objects.equal(this.zze, lastLocationRequest.zze)) {
            return true;
        }
        return false;
    }

    @Pure
    public int getGranularity() {
        return this.zzb;
    }

    @Pure
    public long getMaxUpdateAgeMillis() {
        return this.zza;
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.zza), Integer.valueOf(this.zzb), Boolean.valueOf(this.zzc));
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LastLocationRequest[");
        if (this.zza != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
            sb.append("maxAge=");
            zzdj.zzb(this.zza, sb);
        }
        if (this.zzb != 0) {
            sb.append(", ");
            sb.append(zzo.zzb(this.zzb));
        }
        if (this.zzc) {
            sb.append(", bypass");
        }
        if (this.zzd != null) {
            sb.append(", moduleId=");
            sb.append(this.zzd);
        }
        if (this.zze != null) {
            sb.append(", impersonation=");
            sb.append(this.zze);
        }
        sb.append(']');
        return sb.toString();
    }

    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, getMaxUpdateAgeMillis());
        SafeParcelWriter.writeInt(parcel, 2, getGranularity());
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzc);
        SafeParcelWriter.writeString(parcel, 4, this.zzd, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zze, flags, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    @Nullable
    @Pure
    public final zzd zza() {
        return this.zze;
    }

    @Deprecated
    @Nullable
    @Pure
    public final String zzb() {
        return this.zzd;
    }

    @Pure
    public final boolean zzc() {
        return this.zzc;
    }
}
