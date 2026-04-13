package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public abstract class zzgy extends zzgx {
    private boolean zza;

    zzgy(zzge zzge) {
        super(zzge);
        this.zzt.zzD();
    }

    /* access modifiers changed from: protected */
    public void zzaC() {
    }

    /* access modifiers changed from: protected */
    public abstract boolean zzf();

    /* access modifiers changed from: protected */
    public final void zzv() {
        if (!zzy()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzw() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        } else if (!zzf()) {
            this.zzt.zzB();
            this.zza = true;
        }
    }

    public final void zzx() {
        if (!this.zza) {
            zzaC();
            this.zzt.zzB();
            this.zza = true;
            return;
        }
        throw new IllegalStateException("Can't initialize twice");
    }

    /* access modifiers changed from: package-private */
    public final boolean zzy() {
        return this.zza;
    }
}
