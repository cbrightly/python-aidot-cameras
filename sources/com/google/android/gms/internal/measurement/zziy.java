package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zziy extends zzis {
    private final zzja zza;

    zziy(zzja zzja, int i) {
        super(zzja.size(), i);
        this.zza = zzja;
    }

    /* access modifiers changed from: protected */
    public final Object zza(int i) {
        return this.zza.get(i);
    }
}
