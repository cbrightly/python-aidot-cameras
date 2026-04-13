package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhe extends ContentObserver {
    final /* synthetic */ zzhf zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzhe(zzhf zzhf, Handler handler) {
        super((Handler) null);
        this.zza = zzhf;
    }

    public final void onChange(boolean z) {
        this.zza.zzf();
    }
}
