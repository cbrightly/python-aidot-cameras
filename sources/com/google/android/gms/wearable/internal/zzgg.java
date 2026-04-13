package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzgg implements BaseImplementation.ResultHolder<Status> {
    private final TaskCompletionSource<Boolean> zzes;

    zzgg(TaskCompletionSource<Boolean> taskCompletionSource) {
        this.zzes = taskCompletionSource;
    }

    public final void setFailedResult(Status status) {
        this.zzes.setException(new ApiException(status));
    }

    public final /* synthetic */ void setResult(Object obj) {
        Status status = (Status) obj;
        int statusCode = status.getStatusCode();
        if (statusCode == 0) {
            this.zzes.setResult(true);
        } else if (statusCode == 4002) {
            this.zzes.setResult(false);
        } else {
            setFailedResult(status);
        }
    }
}
