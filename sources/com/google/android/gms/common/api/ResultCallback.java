package com.google.android.gms.common.api;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Result;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public interface ResultCallback<R extends Result> {
    void onResult(@NonNull R r);
}
