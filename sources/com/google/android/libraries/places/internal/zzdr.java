package com.google.android.libraries.places.internal;

import com.android.volley.toolbox.l;
import com.google.android.gms.tasks.OnTokenCanceledListener;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzdr implements OnTokenCanceledListener {
    public final /* synthetic */ l zza;

    public /* synthetic */ zzdr(l lVar) {
        this.zza = lVar;
    }

    public final void onCanceled() {
        this.zza.cancel();
    }
}
