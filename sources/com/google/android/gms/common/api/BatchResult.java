package com.google.android.gms.common.api;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class BatchResult implements Result {
    private final Status zaa;
    private final PendingResult[] zab;

    BatchResult(Status status, PendingResult[] pendingResultArr) {
        this.zaa = status;
        this.zab = pendingResultArr;
    }

    @NonNull
    public Status getStatus() {
        return this.zaa;
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    public <R extends Result> R take(@NonNull BatchResultToken<R> resultToken) {
        boolean z;
        if (resultToken.mId < this.zab.length) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "The result token does not belong to this batch");
        return this.zab[resultToken.mId].await(0, TimeUnit.MILLISECONDS);
    }
}
