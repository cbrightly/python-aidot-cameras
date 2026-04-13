package com.google.android.libraries.places.internal;

import com.android.volley.VolleyError;
import com.android.volley.k;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzdk implements k.a {
    public final /* synthetic */ TaskCompletionSource zza;

    public /* synthetic */ zzdk(TaskCompletionSource taskCompletionSource) {
        this.zza = taskCompletionSource;
    }

    public final void onErrorResponse(VolleyError volleyError) {
        zzdn.zzc(this.zza, volleyError);
    }
}
