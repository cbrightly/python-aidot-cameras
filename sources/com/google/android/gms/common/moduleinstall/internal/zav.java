package com.google.android.gms.common.moduleinstall.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zav extends IStatusCallback.Stub {
    final /* synthetic */ TaskCompletionSource zaa;

    zav(zay zay, TaskCompletionSource taskCompletionSource) {
        this.zaa = taskCompletionSource;
    }

    public final void onResult(Status status) {
        TaskUtil.trySetResultOrApiException(status, true, this.zaa);
    }
}
