package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class zzr implements Handler.Callback {
    final /* synthetic */ zzs zza;

    /* synthetic */ zzr(zzs zzs, zzq zzq) {
        this.zza = zzs;
    }

    public final boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                synchronized (this.zza.zzb) {
                    zzo zzo = (zzo) message.obj;
                    zzp zzp = (zzp) this.zza.zzb.get(zzo);
                    if (zzp != null && zzp.zzi()) {
                        if (zzp.zzj()) {
                            zzp.zzg("GmsClientSupervisor");
                        }
                        this.zza.zzb.remove(zzo);
                    }
                }
                return true;
            case 1:
                synchronized (this.zza.zzb) {
                    zzo zzo2 = (zzo) message.obj;
                    zzp zzp2 = (zzp) this.zza.zzb.get(zzo2);
                    if (zzp2 != null && zzp2.zza() == 3) {
                        String valueOf = String.valueOf(zzo2);
                        Log.e("GmsClientSupervisor", "Timeout waiting for ServiceConnection callback " + valueOf, new Exception());
                        ComponentName zzb = zzp2.zzb();
                        if (zzb == null) {
                            zzb = zzo2.zza();
                        }
                        if (zzb == null) {
                            String zzc = zzo2.zzc();
                            Preconditions.checkNotNull(zzc);
                            zzb = new ComponentName(zzc, "unknown");
                        }
                        zzp2.onServiceDisconnected(zzb);
                    }
                }
                return true;
            default:
                return false;
        }
    }
}
