package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzfk implements Continuation {
    public final /* synthetic */ zzfm zza;

    public /* synthetic */ zzfk(zzfm zzfm) {
        this.zza = zzfm;
    }

    public final Object then(Task task) {
        return FetchPhotoResponse.newInstance(((zzem) task.getResult()).zza);
    }
}
