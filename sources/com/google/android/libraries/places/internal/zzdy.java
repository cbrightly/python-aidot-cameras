package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzdy implements Continuation {
    public final /* synthetic */ zzeg zza;
    public final /* synthetic */ FetchPhotoRequest zzb;
    public final /* synthetic */ zzcw zzc;

    public /* synthetic */ zzdy(zzeg zzeg, FetchPhotoRequest fetchPhotoRequest, zzcw zzcw) {
        this.zza = zzeg;
        this.zzb = fetchPhotoRequest;
        this.zzc = zzcw;
    }

    public final Object then(Task task) {
        return this.zza.zzc(this.zzb, this.zzc, task);
    }
}
