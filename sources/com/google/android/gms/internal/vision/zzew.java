package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzew extends zzee<Object> {
    private final transient Object[] zza;
    private final transient int zzb;
    private final transient int zzc;

    zzew(Object[] objArr, int i, int i2) {
        this.zza = objArr;
        this.zzb = i;
        this.zzc = i2;
    }

    public final Object get(int i) {
        zzde.zza(i, this.zzc);
        return this.zza[(i * 2) + this.zzb];
    }

    /* access modifiers changed from: package-private */
    public final boolean zzf() {
        return true;
    }

    public final int size() {
        return this.zzc;
    }
}
