package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zah extends zad {
    public final ListenerHolder.ListenerKey zab;

    public zah(ListenerHolder.ListenerKey listenerKey, TaskCompletionSource taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zab = listenerKey;
    }

    public final boolean zaa(zabq zabq) {
        zaci zaci = (zaci) zabq.zah().get(this.zab);
        return zaci != null && zaci.zaa.zab();
    }

    @Nullable
    public final Feature[] zab(zabq zabq) {
        zaci zaci = (zaci) zabq.zah().get(this.zab);
        if (zaci == null) {
            return null;
        }
        return zaci.zaa.getRequiredFeatures();
    }

    public final void zac(zabq zabq) {
        zaci zaci = (zaci) zabq.zah().remove(this.zab);
        if (zaci != null) {
            zaci.zab.unregisterListener(zabq.zaf(), this.zaa);
            zaci.zaa.clearListener();
            return;
        }
        this.zaa.trySetResult(false);
    }

    public final /* bridge */ /* synthetic */ void zag(@NonNull zaad zaad, boolean z) {
    }
}
