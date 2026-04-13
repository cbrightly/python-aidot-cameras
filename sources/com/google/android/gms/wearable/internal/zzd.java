package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import javax.annotation.Nullable;

@SafeParcelable.Class(creator = "AddListenerRequestCreator")
@SafeParcelable.Reserved({1})
public final class zzd extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzd> CREATOR = new zze();
    @SafeParcelable.Field(getter = "getListenerAsBinder", id = 2, type = "android.os.IBinder")
    private final zzem zzaz;
    @SafeParcelable.Field(id = 3)
    private final IntentFilter[] zzba;
    @SafeParcelable.Field(id = 4)
    @Nullable
    private final String zzbb;
    @SafeParcelable.Field(id = 5)
    @Nullable
    private final String zzbc;

    @SafeParcelable.Constructor
    zzd(@SafeParcelable.Param(id = 2) IBinder iBinder, @SafeParcelable.Param(id = 3) IntentFilter[] intentFilterArr, @SafeParcelable.Param(id = 4) @Nullable String str, @SafeParcelable.Param(id = 5) @Nullable String str2) {
        zzem zzem;
        if (iBinder != null) {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableListener");
            if (queryLocalInterface instanceof zzem) {
                zzem = (zzem) queryLocalInterface;
            } else {
                zzem = new zzeo(iBinder);
            }
            this.zzaz = zzem;
        } else {
            this.zzaz = null;
        }
        this.zzba = intentFilterArr;
        this.zzbb = str;
        this.zzbc = str2;
    }

    public zzd(zzhk zzhk) {
        this.zzaz = zzhk;
        this.zzba = zzhk.zze();
        this.zzbb = zzhk.zzf();
        this.zzbc = null;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        zzem zzem = this.zzaz;
        SafeParcelWriter.writeIBinder(parcel, 2, zzem == null ? null : zzem.asBinder(), false);
        SafeParcelWriter.writeTypedArray(parcel, 3, this.zzba, i, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzbb, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzbc, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
