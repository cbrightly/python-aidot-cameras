package com.google.firebase.analytics.connector;

import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-measurement-api@@21.2.2 */
public final /* synthetic */ class zza implements Executor {
    public static final /* synthetic */ zza zza = new zza();

    private /* synthetic */ zza() {
    }

    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
