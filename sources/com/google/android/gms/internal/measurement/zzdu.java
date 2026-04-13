package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public abstract class zzdu implements Runnable {
    final long zzh;
    final long zzi;
    final boolean zzj;
    final /* synthetic */ zzef zzk;

    zzdu(zzef zzef, boolean z) {
        this.zzk = zzef;
        this.zzh = zzef.zza.currentTimeMillis();
        this.zzi = zzef.zza.elapsedRealtime();
        this.zzj = z;
    }

    public final void run() {
        if (this.zzk.zzh) {
            zzb();
            return;
        }
        try {
            zza();
        } catch (Exception e) {
            this.zzk.zzT(e, false, this.zzj);
            zzb();
        }
    }

    /* access modifiers changed from: package-private */
    public abstract void zza();

    /* access modifiers changed from: protected */
    public void zzb() {
    }
}
