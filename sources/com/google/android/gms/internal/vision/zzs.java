package com.google.android.gms.internal.vision;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.vision.Frame;

@SafeParcelable.Class(creator = "FrameMetadataParcelCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzs extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzs> CREATOR = new zzv();
    @SafeParcelable.Field(id = 2)
    public int zza;
    @SafeParcelable.Field(id = 3)
    public int zzb;
    @SafeParcelable.Field(id = 4)
    public int zzc;
    @SafeParcelable.Field(id = 5)
    public long zzd;
    @SafeParcelable.Field(id = 6)
    public int zze;

    public zzs() {
    }

    @SafeParcelable.Constructor
    public zzs(@SafeParcelable.Param(id = 2) int i, @SafeParcelable.Param(id = 3) int i2, @SafeParcelable.Param(id = 4) int i3, @SafeParcelable.Param(id = 5) long j, @SafeParcelable.Param(id = 6) int i4) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = i3;
        this.zzd = j;
        this.zze = i4;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zza);
        SafeParcelWriter.writeInt(parcel, 3, this.zzb);
        SafeParcelWriter.writeInt(parcel, 4, this.zzc);
        SafeParcelWriter.writeLong(parcel, 5, this.zzd);
        SafeParcelWriter.writeInt(parcel, 6, this.zze);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public static zzs zza(Frame frame) {
        zzs zzs = new zzs();
        zzs.zza = frame.getMetadata().getWidth();
        zzs.zzb = frame.getMetadata().getHeight();
        zzs.zze = frame.getMetadata().getRotation();
        zzs.zzc = frame.getMetadata().getId();
        zzs.zzd = frame.getMetadata().getTimestampMillis();
        return zzs;
    }
}
