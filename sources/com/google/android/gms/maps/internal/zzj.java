package com.google.android.gms.maps.internal;

import com.google.android.gms.internal.maps.zzb;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public abstract class zzj extends zzb implements ILocationSourceDelegate {
    public zzj() {
        super("com.google.android.gms.maps.internal.ILocationSourceDelegate");
    }

    /* JADX WARNING: type inference failed for: r5v2, types: [android.os.IInterface] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zza(int r2, android.os.Parcel r3, android.os.Parcel r4, int r5) {
        /*
            r1 = this;
            switch(r2) {
                case 1: goto L_0x0009;
                case 2: goto L_0x0005;
                default: goto L_0x0003;
            }
        L_0x0003:
            r2 = 0
            return r2
        L_0x0005:
            r1.deactivate()
            goto L_0x002c
        L_0x0009:
            android.os.IBinder r2 = r3.readStrongBinder()
            if (r2 != 0) goto L_0x0011
            r2 = 0
            goto L_0x0026
        L_0x0011:
            java.lang.String r5 = "com.google.android.gms.maps.internal.IOnLocationChangeListener"
            android.os.IInterface r5 = r2.queryLocalInterface(r5)
            boolean r0 = r5 instanceof com.google.android.gms.maps.internal.zzaj
            if (r0 == 0) goto L_0x0020
            r2 = r5
            com.google.android.gms.maps.internal.zzaj r2 = (com.google.android.gms.maps.internal.zzaj) r2
            goto L_0x0026
        L_0x0020:
            com.google.android.gms.maps.internal.zzai r5 = new com.google.android.gms.maps.internal.zzai
            r5.<init>(r2)
            r2 = r5
        L_0x0026:
            com.google.android.gms.internal.maps.zzc.zzc(r3)
            r1.activate(r2)
        L_0x002c:
            r4.writeNoException()
            r2 = 1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.internal.zzj.zza(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
