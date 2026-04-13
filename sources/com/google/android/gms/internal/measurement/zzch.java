package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.Parcel;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public abstract class zzch extends zzbn implements zzci {
    public zzch() {
        super("com.google.android.gms.measurement.api.internal.IEventHandlerProxy");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                long readLong = parcel.readLong();
                zzbo.zzc(parcel);
                zze(parcel.readString(), parcel.readString(), (Bundle) zzbo.zza(parcel, Bundle.CREATOR), readLong);
                parcel2.writeNoException();
                return true;
            case 2:
                int zzd = zzd();
                parcel2.writeNoException();
                parcel2.writeInt(zzd);
                return true;
            default:
                return false;
        }
    }
}
