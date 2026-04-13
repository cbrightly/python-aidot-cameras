package com.google.android.gms.common.api;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Result;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public abstract class ResultCallbacks<R extends Result> implements ResultCallback<R> {
    public abstract void onFailure(@NonNull Status status);

    @KeepForSdk
    public final void onResult(@NonNull R result) {
        Status status = result.getStatus();
        if (status.isSuccess()) {
            onSuccess(result);
            return;
        }
        onFailure(status);
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                Log.w("ResultCallbacks", "Unable to release ".concat(String.valueOf(String.valueOf(result))), e);
            }
        }
    }

    public abstract void onSuccess(@NonNull R r);
}
