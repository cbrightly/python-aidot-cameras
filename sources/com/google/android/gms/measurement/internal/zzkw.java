package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzkw implements Runnable {
    final /* synthetic */ zzlh zza;
    final /* synthetic */ zzlg zzb;

    zzkw(zzlg zzlg, zzlh zzlh) {
        this.zzb = zzlg;
        this.zza = zzlh;
    }

    public final void run() {
        zzlg.zzy(this.zzb, this.zza);
        this.zzb.zzS();
    }
}
