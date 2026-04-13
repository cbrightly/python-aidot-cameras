package com.google.android.gms.measurement.internal;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.google.firebase.messaging.Constants;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzs {
    private final zzge zza;

    public zzs(zzge zzge) {
        this.zza = zzge;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zza(String str, Bundle bundle) {
        String str2;
        this.zza.zzaB().zzg();
        if (!this.zza.zzJ()) {
            if (bundle.isEmpty()) {
                str2 = null;
            } else {
                if (true == str.isEmpty()) {
                    str = "auto";
                }
                Uri.Builder builder = new Uri.Builder();
                builder.path(str);
                for (String str3 : bundle.keySet()) {
                    builder.appendQueryParameter(str3, bundle.getString(str3));
                }
                str2 = builder.build().toString();
            }
            if (!TextUtils.isEmpty(str2)) {
                this.zza.zzm().zzq.zzb(str2);
                this.zza.zzm().zzr.zzb(this.zza.zzax().currentTimeMillis());
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzb() {
        String str;
        this.zza.zzaB().zzg();
        if (zzd()) {
            if (zze()) {
                this.zza.zzm().zzq.zzb((String) null);
                Bundle bundle = new Bundle();
                bundle.putString("source", "(not set)");
                bundle.putString("medium", "(not set)");
                bundle.putString("_cis", "intent");
                bundle.putLong("_cc", 1);
                this.zza.zzq().zzG("auto", "_cmpx", bundle);
            } else {
                String zza2 = this.zza.zzm().zzq.zza();
                if (TextUtils.isEmpty(zza2)) {
                    this.zza.zzaA().zzh().zza("Cache still valid but referrer not found");
                } else {
                    long zza3 = this.zza.zzm().zzr.zza() / CostTimeUtil.HOUR;
                    Uri parse = Uri.parse(zza2);
                    Bundle bundle2 = new Bundle();
                    Pair pair = new Pair(parse.getPath(), bundle2);
                    for (String next : parse.getQueryParameterNames()) {
                        bundle2.putString(next, parse.getQueryParameter(next));
                    }
                    ((Bundle) pair.second).putLong("_cc", (zza3 - 1) * CostTimeUtil.HOUR);
                    Object obj = pair.first;
                    if (obj == null) {
                        str = "app";
                    } else {
                        str = (String) obj;
                    }
                    this.zza.zzq().zzG(str, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, (Bundle) pair.second);
                }
                this.zza.zzm().zzq.zzb((String) null);
            }
            this.zza.zzm().zzr.zzb(0);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzc() {
        if (zzd() && zze()) {
            this.zza.zzm().zzq.zzb((String) null);
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean zzd() {
        return this.zza.zzm().zzr.zza() > 0;
    }

    /* access modifiers changed from: package-private */
    public final boolean zze() {
        if (zzd() && this.zza.zzax().currentTimeMillis() - this.zza.zzm().zzr.zza() > this.zza.zzf().zzi((String) null, zzeh.zzS)) {
            return true;
        }
        return false;
    }
}
