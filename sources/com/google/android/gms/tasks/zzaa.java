package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: com.google.android.gms:play-services-tasks@@18.0.2 */
public final class zzaa implements Continuation {
    final /* synthetic */ Collection zza;

    zzaa(Collection collection) {
        this.zza = collection;
    }

    public final /* bridge */ /* synthetic */ Object then(@NonNull Task task) {
        ArrayList arrayList = new ArrayList();
        for (Task result : this.zza) {
            arrayList.add(result.getResult());
        }
        return arrayList;
    }
}
