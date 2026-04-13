package com.google.android.gms.maps.internal;

import com.google.android.gms.internal.maps.zzb;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public abstract class zzbr extends zzb implements zzbs {
    public zzbr() {
        super("com.google.android.gms.maps.internal.IOnStreetViewPanoramaReadyCallback");
    }

    /* JADX WARNING: type inference failed for: r0v1, types: [android.os.IInterface] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zza(int r3, android.os.Parcel r4, android.os.Parcel r5, int r6) {
        /*
            r2 = this;
            r6 = 1
            if (r3 != r6) goto L_0x002a
            android.os.IBinder r3 = r4.readStrongBinder()
            if (r3 != 0) goto L_0x000b
            r3 = 0
            goto L_0x0020
        L_0x000b:
            java.lang.String r0 = "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate"
            android.os.IInterface r0 = r3.queryLocalInterface(r0)
            boolean r1 = r0 instanceof com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate
            if (r1 == 0) goto L_0x001a
            r3 = r0
            com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate r3 = (com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate) r3
            goto L_0x0020
        L_0x001a:
            com.google.android.gms.maps.internal.zzbw r0 = new com.google.android.gms.maps.internal.zzbw
            r0.<init>(r3)
            r3 = r0
        L_0x0020:
            com.google.android.gms.internal.maps.zzc.zzc(r4)
            r2.zzb(r3)
            r5.writeNoException()
            return r6
        L_0x002a:
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.internal.zzbr.zza(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
