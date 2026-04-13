package com.google.android.libraries.places.internal;

import com.android.volley.toolbox.m;
import com.google.android.gms.tasks.OnTokenCanceledListener;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzdl implements OnTokenCanceledListener {
    public final /* synthetic */ m zza;

    public /* synthetic */ zzdl(m mVar) {
        this.zza = mVar;
    }

    public final void onCanceled() {
        this.zza.cancel();
    }
}
