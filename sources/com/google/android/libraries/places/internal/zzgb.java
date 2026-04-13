package com.google.android.libraries.places.internal;

import android.os.Handler;
import android.os.HandlerThread;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgb {
    private final zzfx zza;
    private final Map zzb = new HashMap();

    public zzgb(zzfx zzfx) {
        this.zza = zzfx;
    }

    public final boolean zza(TaskCompletionSource taskCompletionSource, long j, String str) {
        if (this.zzb.containsKey(taskCompletionSource)) {
            return false;
        }
        HandlerThread handlerThread = new HandlerThread("timeoutHandlerThread");
        handlerThread.start();
        this.zzb.put(taskCompletionSource, handlerThread);
        return new Handler(handlerThread.getLooper()).postDelayed(new zzfy(taskCompletionSource, "Location timeout."), j);
    }

    public final boolean zzb(TaskCompletionSource taskCompletionSource) {
        HandlerThread handlerThread = (HandlerThread) this.zzb.remove(taskCompletionSource);
        if (handlerThread == null) {
            return false;
        }
        return handlerThread.quit();
    }
}
