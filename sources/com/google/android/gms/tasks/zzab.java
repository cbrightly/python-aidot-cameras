package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: com.google.android.gms:play-services-tasks@@18.0.2 */
public final class zzab implements Continuation {
    final /* synthetic */ Collection zza;

    zzab(Collection collection) {
        this.zza = collection;
    }

    public final /* bridge */ /* synthetic */ Object then(@NonNull Task task) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.zza);
        return Tasks.forResult(arrayList);
    }
}
