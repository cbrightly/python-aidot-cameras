package com.google.android.gms.measurement.internal;

import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import java.util.HashMap;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final /* synthetic */ class zzfo implements Callable {
    public final /* synthetic */ zzfv zza;
    public final /* synthetic */ String zzb;

    public /* synthetic */ zzfo(zzfv zzfv, String str) {
        this.zza = zzfv;
        this.zzb = str;
    }

    public final Object call() {
        zzfv zzfv = this.zza;
        String str = this.zzb;
        zzh zzj = zzfv.zzf.zzi().zzj(str);
        HashMap hashMap = new HashMap();
        hashMap.put("platform", "android");
        hashMap.put("package_name", str);
        zzfv.zzt.zzf().zzh();
        hashMap.put("gmp_version", 77000L);
        if (zzj != null) {
            String zzx = zzj.zzx();
            if (zzx != null) {
                hashMap.put(SharePreferenceUtils.KEY_APP_VERSION, zzx);
            }
            hashMap.put("app_version_int", Long.valueOf(zzj.zzb()));
            hashMap.put("dynamite_version", Long.valueOf(zzj.zzk()));
        }
        return hashMap;
    }
}
