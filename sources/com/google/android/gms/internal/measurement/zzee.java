package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public final class zzee implements Application.ActivityLifecycleCallbacks {
    final /* synthetic */ zzef zza;

    zzee(zzef zzef) {
        this.zza = zzef;
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        this.zza.zzV(new zzdx(this, bundle, activity));
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zza.zzV(new zzed(this, activity));
    }

    public final void onActivityPaused(Activity activity) {
        this.zza.zzV(new zzea(this, activity));
    }

    public final void onActivityResumed(Activity activity) {
        this.zza.zzV(new zzdz(this, activity));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        zzbz zzbz = new zzbz();
        this.zza.zzV(new zzec(this, activity, zzbz));
        Bundle zzb = zzbz.zzb(50);
        if (zzb != null) {
            bundle.putAll(zzb);
        }
    }

    public final void onActivityStarted(Activity activity) {
        this.zza.zzV(new zzdy(this, activity));
    }

    public final void onActivityStopped(Activity activity) {
        this.zza.zzV(new zzeb(this, activity));
    }
}
