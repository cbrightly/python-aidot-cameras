package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zzc;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzl extends zza implements IMapViewDelegate {
    zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IMapViewDelegate");
    }

    /* JADX WARNING: type inference failed for: r2v1, types: [android.os.IInterface] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.maps.internal.IGoogleMapDelegate getMap() {
        /*
            r4 = this;
            android.os.Parcel r0 = r4.zza()
            r1 = 1
            android.os.Parcel r0 = r4.zzH(r1, r0)
            android.os.IBinder r1 = r0.readStrongBinder()
            if (r1 != 0) goto L_0x0011
            r1 = 0
            goto L_0x0026
        L_0x0011:
            java.lang.String r2 = "com.google.android.gms.maps.internal.IGoogleMapDelegate"
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.maps.internal.IGoogleMapDelegate
            if (r3 == 0) goto L_0x0020
            r1 = r2
            com.google.android.gms.maps.internal.IGoogleMapDelegate r1 = (com.google.android.gms.maps.internal.IGoogleMapDelegate) r1
            goto L_0x0026
        L_0x0020:
            com.google.android.gms.maps.internal.zzg r2 = new com.google.android.gms.maps.internal.zzg
            r2.<init>(r1)
            r1 = r2
        L_0x0026:
            r0.recycle()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.internal.zzl.getMap():com.google.android.gms.maps.internal.IGoogleMapDelegate");
    }

    public final void getMapAsync(zzas zzas) {
        Parcel zza = zza();
        zzc.zzg(zza, zzas);
        zzc(9, zza);
    }

    public final IObjectWrapper getView() {
        Parcel zzH = zzH(8, zza());
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final void onCreate(Bundle bundle) {
        Parcel zza = zza();
        zzc.zze(zza, bundle);
        zzc(2, zza);
    }

    public final void onDestroy() {
        zzc(5, zza());
    }

    public final void onEnterAmbient(Bundle bundle) {
        Parcel zza = zza();
        zzc.zze(zza, bundle);
        zzc(10, zza);
    }

    public final void onExitAmbient() {
        zzc(11, zza());
    }

    public final void onLowMemory() {
        zzc(6, zza());
    }

    public final void onPause() {
        zzc(4, zza());
    }

    public final void onResume() {
        zzc(3, zza());
    }

    public final void onSaveInstanceState(Bundle bundle) {
        Parcel zza = zza();
        zzc.zze(zza, bundle);
        Parcel zzH = zzH(7, zza);
        if (zzH.readInt() != 0) {
            bundle.readFromParcel(zzH);
        }
        zzH.recycle();
    }

    public final void onStart() {
        zzc(12, zza());
    }

    public final void onStop() {
        zzc(13, zza());
    }
}
