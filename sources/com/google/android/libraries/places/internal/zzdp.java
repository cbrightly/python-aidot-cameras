package com.google.android.libraries.places.internal;

import android.graphics.Bitmap;
import com.android.volley.k;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzdp implements k.b {
    public final /* synthetic */ TaskCompletionSource zza;
    public final /* synthetic */ zzek zzb;

    public /* synthetic */ zzdp(zzek zzek, TaskCompletionSource taskCompletionSource) {
        this.zzb = zzek;
        this.zza = taskCompletionSource;
    }

    public final void onResponse(Object obj) {
        zzdt.zzc(this.zzb, this.zza, (Bitmap) obj);
    }
}
