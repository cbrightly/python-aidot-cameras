package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzkd implements Runnable {
    final /* synthetic */ zzlg zza;
    final /* synthetic */ Runnable zzb;

    zzkd(zzkf zzkf, zzlg zzlg, Runnable runnable) {
        this.zza = zzlg;
        this.zzb = runnable;
    }

    public final void run() {
        this.zza.zzA();
        this.zza.zzz(this.zzb);
        this.zza.zzX();
    }
}
