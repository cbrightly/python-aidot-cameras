package com.google.android.gms.common.moduleinstall.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.moduleinstall.ModuleInstallResponse;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final /* synthetic */ class zak implements SuccessContinuation {
    public final /* synthetic */ AtomicReference zaa;

    public /* synthetic */ zak(AtomicReference atomicReference) {
        this.zaa = atomicReference;
    }

    public final Task then(Object obj) {
        AtomicReference atomicReference = this.zaa;
        Void voidR = (Void) obj;
        int i = zay.zab;
        if (atomicReference.get() != null) {
            return Tasks.forResult((ModuleInstallResponse) atomicReference.get());
        }
        return Tasks.forException(new ApiException(Status.RESULT_INTERNAL_ERROR));
    }
}
