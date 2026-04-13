package com.google.android.gms.vision.face.internal.client;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.apps.common.proguard.UsedByNative;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@UsedByNative("wrapper.cc")
@SafeParcelable.Class(creator = "FaceParcelCreator")
/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
public class FaceParcel extends AbstractSafeParcelable {
    @RecentlyNonNull
    public static final Parcelable.Creator<FaceParcel> CREATOR = new zzd();
    @SafeParcelable.Field(id = 2)
    public final int zza;
    @SafeParcelable.Field(id = 3)
    public final float zzb;
    @SafeParcelable.Field(id = 4)
    public final float zzc;
    @SafeParcelable.Field(id = 5)
    public final float zzd;
    @SafeParcelable.Field(id = 6)
    public final float zze;
    @SafeParcelable.Field(defaultValue = "3.4028235E38f", id = 7)
    public final float zzf;
    @SafeParcelable.Field(defaultValue = "3.4028235E38f", id = 8)
    public final float zzg;
    @SafeParcelable.Field(defaultValue = "3.4028235E38f", id = 14)
    public final float zzh;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 9)
    public final LandmarkParcel[] zzi;
    @SafeParcelable.Field(id = 10)
    public final float zzj;
    @SafeParcelable.Field(id = 11)
    public final float zzk;
    @SafeParcelable.Field(id = 12)
    public final float zzl;
    @SafeParcelable.Field(id = 13)
    public final zza[] zzm;
    @SafeParcelable.Field(defaultValue = "-1.0f", id = 15)
    public final float zzn;
    @SafeParcelable.VersionField(id = 1)
    private final int zzo;

    @SafeParcelable.Constructor
    public FaceParcel(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) int i2, @SafeParcelable.Param(id = 3) float f, @SafeParcelable.Param(id = 4) float f2, @SafeParcelable.Param(id = 5) float f3, @SafeParcelable.Param(id = 6) float f4, @SafeParcelable.Param(id = 7) float f5, @SafeParcelable.Param(id = 8) float f6, @SafeParcelable.Param(id = 14) float f7, @SafeParcelable.Param(id = 9) LandmarkParcel[] landmarkParcelArr, @SafeParcelable.Param(id = 10) float f8, @SafeParcelable.Param(id = 11) float f9, @SafeParcelable.Param(id = 12) float f10, @SafeParcelable.Param(id = 13) zza[] zzaArr, @SafeParcelable.Param(id = 15) float f11) {
        this.zzo = i;
        this.zza = i2;
        this.zzb = f;
        this.zzc = f2;
        this.zzd = f3;
        this.zze = f4;
        this.zzf = f5;
        this.zzg = f6;
        this.zzh = f7;
        this.zzi = landmarkParcelArr;
        this.zzj = f8;
        this.zzk = f9;
        this.zzl = f10;
        this.zzm = zzaArr;
        this.zzn = f11;
    }

    @UsedByNative("wrapper.cc")
    public FaceParcel(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) int i2, @SafeParcelable.Param(id = 3) float f, @SafeParcelable.Param(id = 4) float f2, @SafeParcelable.Param(id = 5) float f3, @SafeParcelable.Param(id = 6) float f4, @SafeParcelable.Param(id = 7) float f5, @SafeParcelable.Param(id = 8) float f6, @RecentlyNonNull @SafeParcelable.Param(id = 9) LandmarkParcel[] landmarkParcelArr, @SafeParcelable.Param(id = 10) float f7, @SafeParcelable.Param(id = 11) float f8, @SafeParcelable.Param(id = 12) float f9) {
        this(i, i2, f, f2, f3, f4, f5, f6, 0.0f, landmarkParcelArr, f7, f8, f9, new zza[0], -1.0f);
    }

    public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzo);
        SafeParcelWriter.writeInt(parcel, 2, this.zza);
        SafeParcelWriter.writeFloat(parcel, 3, this.zzb);
        SafeParcelWriter.writeFloat(parcel, 4, this.zzc);
        SafeParcelWriter.writeFloat(parcel, 5, this.zzd);
        SafeParcelWriter.writeFloat(parcel, 6, this.zze);
        SafeParcelWriter.writeFloat(parcel, 7, this.zzf);
        SafeParcelWriter.writeFloat(parcel, 8, this.zzg);
        SafeParcelWriter.writeTypedArray(parcel, 9, this.zzi, i, false);
        SafeParcelWriter.writeFloat(parcel, 10, this.zzj);
        SafeParcelWriter.writeFloat(parcel, 11, this.zzk);
        SafeParcelWriter.writeFloat(parcel, 12, this.zzl);
        SafeParcelWriter.writeTypedArray(parcel, 13, this.zzm, i, false);
        SafeParcelWriter.writeFloat(parcel, 14, this.zzh);
        SafeParcelWriter.writeFloat(parcel, 15, this.zzn);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
