package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.zzq;
import com.google.android.gms.location.zzr;
import com.google.android.gms.location.zzt;
import com.google.android.gms.location.zzu;

@SafeParcelable.Class(creator = "LocationRequestUpdateDataCreator")
@SafeParcelable.Reserved({1000})
@Deprecated
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzdf extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzdf> CREATOR = new zzdg();
    @SafeParcelable.Field(defaultValueUnchecked = "LocationRequestUpdateData.OPERATION_ADD", getter = "getOperation", id = 1)
    private final int zza;
    @SafeParcelable.Field(defaultValueUnchecked = "null", getter = "getLocationRequest", id = 2)
    @Nullable
    private final zzdd zzb;
    @SafeParcelable.Field(defaultValueUnchecked = "null", getter = "getLocationListenerAsBinder", id = 3, type = "android.os.IBinder")
    @Nullable
    private final zzu zzc;
    @SafeParcelable.Field(defaultValueUnchecked = "null", getter = "getLocationCallbackAsBinder", id = 5, type = "android.os.IBinder")
    @Nullable
    private final zzr zzd;
    @SafeParcelable.Field(defaultValueUnchecked = "null", getter = "getPendingIntent", id = 4)
    @Nullable
    private final PendingIntent zze;
    @SafeParcelable.Field(defaultValueUnchecked = "null", getter = "getFusedLocationProviderCallbackAsBinder", id = 6, type = "android.os.IBinder")
    @Nullable
    private final zzk zzf;
    @SafeParcelable.Field(defaultValueUnchecked = "null", getter = "getListenerId", id = 8)
    @Nullable
    private final String zzg;

    @SafeParcelable.Constructor
    zzdf(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) @Nullable zzdd zzdd, @SafeParcelable.Param(id = 3) @Nullable IBinder iBinder, @SafeParcelable.Param(id = 5) @Nullable IBinder iBinder2, @SafeParcelable.Param(id = 4) @Nullable PendingIntent pendingIntent, @SafeParcelable.Param(id = 6) @Nullable IBinder iBinder3, @SafeParcelable.Param(id = 8) @Nullable String str) {
        zzu zzu;
        zzr zzr;
        this.zza = i;
        this.zzb = zzdd;
        zzk zzk = null;
        if (iBinder != null) {
            zzu = zzt.zzb(iBinder);
        } else {
            zzu = null;
        }
        this.zzc = zzu;
        this.zze = pendingIntent;
        if (iBinder2 != null) {
            zzr = zzq.zzb(iBinder2);
        } else {
            zzr = null;
        }
        this.zzd = zzr;
        if (iBinder3 != null) {
            IInterface queryLocalInterface = iBinder3.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
            zzk = queryLocalInterface instanceof zzk ? (zzk) queryLocalInterface : new zzi(iBinder3);
        }
        this.zzf = zzk;
        this.zzg = str;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder;
        IBinder iBinder2;
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zza);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzb, i, false);
        zzu zzu = this.zzc;
        IBinder iBinder3 = null;
        if (zzu == null) {
            iBinder = null;
        } else {
            iBinder = zzu.asBinder();
        }
        SafeParcelWriter.writeIBinder(parcel, 3, iBinder, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zze, i, false);
        zzr zzr = this.zzd;
        if (zzr == null) {
            iBinder2 = null;
        } else {
            iBinder2 = zzr.asBinder();
        }
        SafeParcelWriter.writeIBinder(parcel, 5, iBinder2, false);
        zzk zzk = this.zzf;
        if (zzk != null) {
            iBinder3 = zzk.asBinder();
        }
        SafeParcelWriter.writeIBinder(parcel, 6, iBinder3, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzg, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
