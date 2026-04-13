package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.WorkSource;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@ShowFirstParty
@SafeParcelable.Class(creator = "ActivityRecognitionRequestCreator")
@SafeParcelable.Reserved({1000})
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzb extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzb> CREATOR = new zzc();
    @SafeParcelable.Field(getter = "getIntervalMillis", id = 1)
    private final long zza;
    @SafeParcelable.Field(getter = "getTriggerUpdate", id = 2)
    private final boolean zzb;
    @SafeParcelable.Field(getter = "getWorkSource", id = 3)
    @Nullable
    private final WorkSource zzc;
    @SafeParcelable.Field(getter = "getTag", id = 4)
    @Nullable
    private final String zzd;
    @SafeParcelable.Field(getter = "getNondefaultActivities", id = 5)
    @Nullable
    private final int[] zze;
    @SafeParcelable.Field(getter = "getRequestSensorData", id = 6)
    private final boolean zzf;
    @SafeParcelable.Field(getter = "getAccountName", id = 7)
    @Nullable
    private final String zzg;
    @SafeParcelable.Field(defaultValueUnchecked = "ActivityRecognitionRequest.DEFAULT_MAX_REPORT_LATENCY_MILLIS", getter = "getMaxReportLatencyMillis", id = 8)
    private final long zzh;
    @SafeParcelable.Field(getter = "getContextAttributionTag", id = 9)
    @Nullable
    private String zzi;

    @SafeParcelable.Constructor
    zzb(@SafeParcelable.Param(id = 1) long j, @SafeParcelable.Param(id = 2) boolean z, @SafeParcelable.Param(id = 3) @Nullable WorkSource workSource, @SafeParcelable.Param(id = 4) @Nullable String str, @SafeParcelable.Param(id = 5) @Nullable int[] iArr, @SafeParcelable.Param(id = 6) boolean z2, @SafeParcelable.Param(id = 7) @Nullable String str2, @SafeParcelable.Param(id = 8) long j2, @SafeParcelable.Param(id = 9) @Nullable String str3) {
        this.zza = j;
        this.zzb = z;
        this.zzc = workSource;
        this.zzd = str;
        this.zze = iArr;
        this.zzf = z2;
        this.zzg = str2;
        this.zzh = j2;
        this.zzi = str3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        Preconditions.checkNotNull(parcel);
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, this.zza);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzb);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzc, i, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzd, false);
        SafeParcelWriter.writeIntArray(parcel, 5, this.zze, false);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzf);
        SafeParcelWriter.writeString(parcel, 7, this.zzg, false);
        SafeParcelWriter.writeLong(parcel, 8, this.zzh);
        SafeParcelWriter.writeString(parcel, 9, this.zzi, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzb zza(@Nullable String str) {
        this.zzi = str;
        return this;
    }
}
