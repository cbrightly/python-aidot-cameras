package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzlb implements zzln {
    final /* synthetic */ zzlg zza;

    zzlb(zzlg zzlg) {
        this.zza = zzlg;
    }

    public final void zza(String str, String str2, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            zzlg zzlg = this.zza;
            if (zzlg.zzn != null) {
                zzlg.zzn.zzaA().zzd().zzb("AppId not known when logging event", "_err");
                return;
            }
            return;
        }
        this.zza.zzaB().zzp(new zzla(this, str, "_err", bundle));
    }
}
