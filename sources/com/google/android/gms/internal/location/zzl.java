package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.Parcel;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public abstract class zzl extends zzb implements zzm {
    public zzl() {
        super("com.google.android.gms.location.internal.IGeofencerCallbacks");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                int readInt = parcel.readInt();
                String[] createStringArray = parcel.createStringArray();
                zzc.zzb(parcel);
                zzb(readInt, createStringArray);
                return true;
            case 2:
                int readInt2 = parcel.readInt();
                String[] createStringArray2 = parcel.createStringArray();
                zzc.zzb(parcel);
                zzd(readInt2, createStringArray2);
                return true;
            case 3:
                zzc.zzb(parcel);
                zzc(parcel.readInt(), (PendingIntent) zzc.zza(parcel, PendingIntent.CREATOR));
                return true;
            default:
                return false;
        }
    }
}
