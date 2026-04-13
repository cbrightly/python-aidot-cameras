package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzbq implements OnCompleteListener {
    public final /* synthetic */ BaseImplementation.ResultHolder zza;

    public /* synthetic */ zzbq(BaseImplementation.ResultHolder resultHolder) {
        this.zza = resultHolder;
    }

    public final void onComplete(Task task) {
        BaseImplementation.ResultHolder resultHolder = this.zza;
        if (task.isSuccessful()) {
            resultHolder.setResult(Status.RESULT_SUCCESS);
        } else if (task.isCanceled()) {
            resultHolder.setFailedResult(Status.RESULT_CANCELED);
        } else {
            Exception exception = task.getException();
            if (exception instanceof ApiException) {
                resultHolder.setFailedResult(((ApiException) exception).getStatus());
            } else {
                resultHolder.setFailedResult(Status.RESULT_INTERNAL_ERROR);
            }
        }
    }
}
