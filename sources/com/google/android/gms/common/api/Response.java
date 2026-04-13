package com.google.android.gms.common.api;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Result;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class Response<T extends Result> {
    private Result zza;

    public Response() {
    }

    protected Response(@NonNull T t) {
        this.zza = t;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public T getResult() {
        return this.zza;
    }

    public void setResult(@NonNull T t) {
        this.zza = t;
    }
}
