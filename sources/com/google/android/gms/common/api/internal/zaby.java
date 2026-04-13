package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.base.zaa;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zaby extends zaa implements IStatusCallback {
    zaby(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.api.internal.IStatusCallback");
    }

    public final void onResult(Status status) {
        throw null;
    }
}
