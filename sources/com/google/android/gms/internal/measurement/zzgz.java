package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzgz extends ContentObserver {
    zzgz(Handler handler) {
        super((Handler) null);
    }

    public final void onChange(boolean z) {
        zzha.zzl.set(true);
    }
}
