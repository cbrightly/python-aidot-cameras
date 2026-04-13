package com.google.android.gms.internal.vision;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
public final class zzac extends zzb implements zzad {
    zzac(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.text.internal.client.INativeTextRecognizer");
    }

    public final zzah[] zza(IObjectWrapper iObjectWrapper, zzs zzs, zzaj zzaj) {
        Parcel a_ = a_();
        zzd.zza(a_, (IInterface) iObjectWrapper);
        zzd.zza(a_, (Parcelable) zzs);
        zzd.zza(a_, (Parcelable) zzaj);
        Parcel zza = zza(3, a_);
        zzah[] zzahArr = (zzah[]) zza.createTypedArray(zzah.CREATOR);
        zza.recycle();
        return zzahArr;
    }

    public final void zzb() {
        zzb(2, a_());
    }
}
