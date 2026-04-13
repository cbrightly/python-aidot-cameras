package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ConditionalUserPropertyParcelCreator")
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzac extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzac> CREATOR = new zzad();
    @SafeParcelable.Field(id = 2)
    @Nullable
    public String zza;
    @SafeParcelable.Field(id = 3)
    public String zzb;
    @SafeParcelable.Field(id = 4)
    public zzlj zzc;
    @SafeParcelable.Field(id = 5)
    public long zzd;
    @SafeParcelable.Field(id = 6)
    public boolean zze;
    @SafeParcelable.Field(id = 7)
    @Nullable
    public String zzf;
    @SafeParcelable.Field(id = 8)
    @Nullable
    public final zzaw zzg;
    @SafeParcelable.Field(id = 9)
    public long zzh;
    @SafeParcelable.Field(id = 10)
    @Nullable
    public zzaw zzi;
    @SafeParcelable.Field(id = 11)
    public final long zzj;
    @SafeParcelable.Field(id = 12)
    @Nullable
    public final zzaw zzk;

    zzac(zzac zzac) {
        Preconditions.checkNotNull(zzac);
        this.zza = zzac.zza;
        this.zzb = zzac.zzb;
        this.zzc = zzac.zzc;
        this.zzd = zzac.zzd;
        this.zze = zzac.zze;
        this.zzf = zzac.zzf;
        this.zzg = zzac.zzg;
        this.zzh = zzac.zzh;
        this.zzi = zzac.zzi;
        this.zzj = zzac.zzj;
        this.zzk = zzac.zzk;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zza, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzb, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzc, i, false);
        SafeParcelWriter.writeLong(parcel, 5, this.zzd);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zze);
        SafeParcelWriter.writeString(parcel, 7, this.zzf, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzg, i, false);
        SafeParcelWriter.writeLong(parcel, 9, this.zzh);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzi, i, false);
        SafeParcelWriter.writeLong(parcel, 11, this.zzj);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zzk, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    @SafeParcelable.Constructor
    zzac(@SafeParcelable.Param(id = 2) @Nullable String str, @SafeParcelable.Param(id = 3) String str2, @SafeParcelable.Param(id = 4) zzlj zzlj, @SafeParcelable.Param(id = 5) long j, @SafeParcelable.Param(id = 6) boolean z, @SafeParcelable.Param(id = 7) @Nullable String str3, @SafeParcelable.Param(id = 8) @Nullable zzaw zzaw, @SafeParcelable.Param(id = 9) long j2, @SafeParcelable.Param(id = 10) @Nullable zzaw zzaw2, @SafeParcelable.Param(id = 11) long j3, @SafeParcelable.Param(id = 12) @Nullable zzaw zzaw3) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = zzlj;
        this.zzd = j;
        this.zze = z;
        this.zzf = str3;
        this.zzg = zzaw;
        this.zzh = j2;
        this.zzi = zzaw2;
        this.zzj = j3;
        this.zzk = zzaw3;
    }
}
