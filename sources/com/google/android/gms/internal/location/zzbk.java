package com.google.android.gms.internal.location;

import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzbk implements Executor {
    public static final /* synthetic */ zzbk zza = new zzbk();

    private /* synthetic */ zzbk() {
    }

    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
