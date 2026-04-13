package com.google.android.gms.measurement.internal;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzkx implements zzew {
    final /* synthetic */ String zza;
    final /* synthetic */ zzlg zzb;

    zzkx(zzlg zzlg, String str) {
        this.zzb = zzlg;
        this.zza = str;
    }

    public final void zza(String str, int i, Throwable th, byte[] bArr, Map map) {
        this.zzb.zzK(i, th, bArr, this.zza);
    }
}
