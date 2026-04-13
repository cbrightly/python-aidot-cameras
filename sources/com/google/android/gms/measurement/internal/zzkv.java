package com.google.android.gms.measurement.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.internal.measurement.zzff;
import com.google.android.gms.internal.measurement.zzra;
import java.util.HashMap;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzkv extends zzks {
    zzkv(zzlg zzlg) {
        super(zzlg);
    }

    private final String zzd(String str) {
        String zzi = this.zzf.zzo().zzi(str);
        if (TextUtils.isEmpty(zzi)) {
            return (String) zzeh.zzq.zza((Object) null);
        }
        Uri parse = Uri.parse((String) zzeh.zzq.zza((Object) null));
        Uri.Builder buildUpon = parse.buildUpon();
        String authority = parse.getAuthority();
        buildUpon.authority(zzi + "." + authority);
        return buildUpon.build().toString();
    }

    public final zzku zza(String str) {
        String str2;
        zzra.zzc();
        zzku zzku = null;
        if (this.zzt.zzf().zzs((String) null, zzeh.zzaq)) {
            this.zzt.zzaA().zzj().zza("sgtm feature flag enabled.");
            zzh zzj = this.zzf.zzi().zzj(str);
            if (zzj == null) {
                return new zzku(zzd(str));
            }
            if (zzj.zzan()) {
                this.zzt.zzaA().zzj().zza("sgtm upload enabled in manifest.");
                zzff zze = this.zzf.zzo().zze(zzj.zzu());
                if (zze != null) {
                    String zzj2 = zze.zzj();
                    if (!TextUtils.isEmpty(zzj2)) {
                        String zzi = zze.zzi();
                        zzes zzj3 = this.zzt.zzaA().zzj();
                        if (true != TextUtils.isEmpty(zzi)) {
                            str2 = "N";
                        } else {
                            str2 = "Y";
                        }
                        zzj3.zzc("sgtm configured with upload_url, server_info", zzj2, str2);
                        if (TextUtils.isEmpty(zzi)) {
                            this.zzt.zzay();
                            zzku = new zzku(zzj2);
                        } else {
                            HashMap hashMap = new HashMap();
                            hashMap.put("x-google-sgtm-server-info", zzi);
                            zzku = new zzku(zzj2, hashMap);
                        }
                    }
                }
            }
            if (zzku != null) {
                return zzku;
            }
        }
        return new zzku(zzd(str));
    }
}
