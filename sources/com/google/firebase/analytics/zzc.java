package com.google.firebase.analytics;

import androidx.annotation.Nullable;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement-api@@21.2.2 */
public final class zzc implements Callable {
    final /* synthetic */ FirebaseAnalytics zza;

    zzc(FirebaseAnalytics firebaseAnalytics) {
        this.zza = firebaseAnalytics;
    }

    @Nullable
    public final /* bridge */ /* synthetic */ Object call() {
        return this.zza.zzb.zzh();
    }
}
