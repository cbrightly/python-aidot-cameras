package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzhz implements OnCompleteListener {
    public final /* synthetic */ zzid zza;
    public final /* synthetic */ String zzb;

    public /* synthetic */ zzhz(zzid zzid, String str) {
        this.zza = zzid;
        this.zzb = str;
    }

    public final void onComplete(Task task) {
        this.zza.zzb(this.zzb, task);
    }
}
