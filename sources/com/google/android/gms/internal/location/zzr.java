package com.google.android.gms.internal.location;

import android.os.Parcel;
import com.google.android.gms.location.LocationSettingsResult;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public abstract class zzr extends zzb implements zzs {
    public zzr() {
        super("com.google.android.gms.location.internal.ISettingsCallbacks");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 1) {
            return false;
        }
        zzc.zzb(parcel);
        zzb((LocationSettingsResult) zzc.zza(parcel, LocationSettingsResult.CREATOR));
        return true;
    }
}
