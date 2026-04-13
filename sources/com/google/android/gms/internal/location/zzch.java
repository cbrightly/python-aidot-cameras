package com.google.android.gms.internal.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzch implements OnTokenCanceledListener {
    public final /* synthetic */ zzda zza;
    public final /* synthetic */ ListenerHolder.ListenerKey zzb;

    public /* synthetic */ zzch(zzda zzda, ListenerHolder.ListenerKey listenerKey) {
        this.zza = zzda;
        this.zzb = listenerKey;
    }

    public final void onCanceled() {
        try {
            this.zza.zzB(this.zzb, true, new TaskCompletionSource());
        } catch (RemoteException e) {
        }
    }
}
