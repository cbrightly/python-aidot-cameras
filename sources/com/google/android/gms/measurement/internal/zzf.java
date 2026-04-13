package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public abstract class zzf extends zze {
    private boolean zza;

    zzf(zzge zzge) {
        super(zzge);
        this.zzt.zzD();
    }

    /* access modifiers changed from: protected */
    public final void zza() {
        if (!zze()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzb() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        } else if (!zzf()) {
            this.zzt.zzB();
            this.zza = true;
        }
    }

    public final void zzc() {
        if (!this.zza) {
            zzd();
            this.zzt.zzB();
            this.zza = true;
            return;
        }
        throw new IllegalStateException("Can't initialize twice");
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public void zzd() {
    }

    /* access modifiers changed from: package-private */
    public final boolean zze() {
        return this.zza;
    }

    /* access modifiers changed from: protected */
    public abstract boolean zzf();
}
