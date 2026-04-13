package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.base.zau;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zacz extends zau {
    final /* synthetic */ zada zaa;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zacz(zada zada, Looper looper) {
        super(looper);
        this.zaa = zada;
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        switch (i) {
            case 0:
                PendingResult pendingResult = (PendingResult) message.obj;
                synchronized (this.zaa.zae) {
                    zada zada = (zada) Preconditions.checkNotNull(this.zaa.zab);
                    if (pendingResult == null) {
                        zada.zaj(new Status(13, "Transform returned null"));
                    } else if (pendingResult instanceof zacp) {
                        zada.zaj(((zacp) pendingResult).zaa());
                    } else {
                        zada.zai(pendingResult);
                    }
                }
                return;
            case 1:
                RuntimeException runtimeException = (RuntimeException) message.obj;
                Log.e("TransformedResultImpl", "Runtime exception on the transformation worker thread: ".concat(String.valueOf(runtimeException.getMessage())));
                throw runtimeException;
            default:
                Log.e("TransformedResultImpl", "TransformationResultHandler received unknown message type: " + i);
                return;
        }
    }
}
