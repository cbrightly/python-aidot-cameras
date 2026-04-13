package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzby implements RemoteCall {
    public final /* synthetic */ PendingIntent zza;

    public /* synthetic */ zzby(PendingIntent pendingIntent) {
        this.zza = pendingIntent;
    }

    public final void accept(Object obj, Object obj2) {
        ((zzda) obj).zzx(this.zza, (TaskCompletionSource) obj2);
    }
}
