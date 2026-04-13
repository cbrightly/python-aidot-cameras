package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public abstract class zzkt extends zzks {
    private boolean zza;

    zzkt(zzlg zzlg) {
        super(zzlg);
        this.zzf.zzM();
    }

    /* access modifiers changed from: protected */
    public final void zzW() {
        if (!zzY()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzX() {
        if (!this.zza) {
            zzb();
            this.zzf.zzH();
            this.zza = true;
            return;
        }
        throw new IllegalStateException("Can't initialize twice");
    }

    /* access modifiers changed from: package-private */
    public final boolean zzY() {
        return this.zza;
    }

    /* access modifiers changed from: protected */
    public abstract boolean zzb();
}
