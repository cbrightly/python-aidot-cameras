package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.ObjectWrapper;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public final class zzdx extends zzdu {
    final /* synthetic */ Bundle zza;
    final /* synthetic */ Activity zzb;
    final /* synthetic */ zzee zzc;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdx(zzee zzee, Bundle bundle, Activity activity) {
        super(zzee.zza, true);
        this.zzc = zzee;
        this.zza = bundle;
        this.zzb = activity;
    }

    /* access modifiers changed from: package-private */
    public final void zza() {
        Bundle bundle;
        if (this.zza != null) {
            bundle = new Bundle();
            if (this.zza.containsKey("com.google.app_measurement.screen_service")) {
                Object obj = this.zza.get("com.google.app_measurement.screen_service");
                if (obj instanceof Bundle) {
                    bundle.putBundle("com.google.app_measurement.screen_service", (Bundle) obj);
                }
            }
        } else {
            bundle = null;
        }
        ((zzcc) Preconditions.checkNotNull(this.zzc.zza.zzj)).onActivityCreated(ObjectWrapper.wrap(this.zzb), bundle, this.zzi);
    }
}
