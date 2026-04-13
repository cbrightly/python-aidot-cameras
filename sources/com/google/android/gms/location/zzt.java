package com.google.android.gms.location;

import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.internal.location.zzb;
import com.google.android.gms.internal.location.zzc;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public abstract class zzt extends zzb implements zzu {
    public zzt() {
        super("com.google.android.gms.location.ILocationListener");
    }

    public static zzu zzb(IBinder iBinder) {
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.ILocationListener");
        return queryLocalInterface instanceof zzu ? (zzu) queryLocalInterface : new zzs(iBinder);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zzc.zzb(parcel);
                zzd((Location) zzc.zza(parcel, Location.CREATOR));
                return true;
            case 2:
                zze();
                return true;
            default:
                return false;
        }
    }
}
