package com.google.android.gms.internal.location;

import android.os.RemoteException;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzcf implements OnTokenCanceledListener {
    public final /* synthetic */ ICancelToken zza;

    public /* synthetic */ zzcf(ICancelToken iCancelToken) {
        this.zza = iCancelToken;
    }

    public final void onCanceled() {
        ICancelToken iCancelToken = this.zza;
        int i = zzda.zze;
        try {
            iCancelToken.cancel();
        } catch (RemoteException e) {
        }
    }
}
