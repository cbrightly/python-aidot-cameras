package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzagt extends zzagv {
    private zzagt() {
        super((zzagu) null);
    }

    /* synthetic */ zzagt(zzags zzags) {
        super((zzagu) null);
    }

    /* access modifiers changed from: package-private */
    public final void zza(Object obj, long j) {
        ((zzagh) zzait.zzf(obj, j)).zzb();
    }

    /* access modifiers changed from: package-private */
    public final void zzb(Object obj, Object obj2, long j) {
        zzagh zzagh = (zzagh) zzait.zzf(obj, j);
        zzagh zzagh2 = (zzagh) zzait.zzf(obj2, j);
        int size = zzagh.size();
        int size2 = zzagh2.size();
        if (size > 0 && size2 > 0) {
            if (!zzagh.zzc()) {
                zzagh = zzagh.zzf(size2 + size);
            }
            zzagh.addAll(zzagh2);
        }
        if (size > 0) {
            zzagh2 = zzagh;
        }
        zzait.zzs(obj, j, zzagh2);
    }
}
