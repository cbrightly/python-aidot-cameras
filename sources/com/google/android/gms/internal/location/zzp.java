package com.google.android.gms.internal.location;

import android.location.Location;
import android.os.Parcel;
import com.google.android.gms.common.api.Status;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public abstract class zzp extends zzb implements zzq {
    public zzp() {
        super("com.google.android.gms.location.internal.ILocationStatusCallback");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 1) {
            return false;
        }
        zzc.zzb(parcel);
        zzb((Status) zzc.zza(parcel, Status.CREATOR), (Location) zzc.zza(parcel, Location.CREATOR));
        return true;
    }
}
