package com.google.android.libraries.places.internal;

import com.android.volley.k;
import com.google.android.gms.tasks.TaskCompletionSource;
import org.json.JSONObject;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzdj implements k.b {
    public final /* synthetic */ zzdn zza;
    public final /* synthetic */ Class zzb;
    public final /* synthetic */ TaskCompletionSource zzc;

    public /* synthetic */ zzdj(zzdn zzdn, Class cls, TaskCompletionSource taskCompletionSource) {
        this.zza = zzdn;
        this.zzb = cls;
        this.zzc = taskCompletionSource;
    }

    public final void onResponse(Object obj) {
        this.zza.zzb(this.zzb, this.zzc, (JSONObject) obj);
    }
}
