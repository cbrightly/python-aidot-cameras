package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.Wearable;

public abstract class zzn<R extends Result> extends BaseImplementation.ApiMethodImpl<R, zzhg> {
    public zzn(GoogleApiClient googleApiClient) {
        super((Api<?>) Wearable.API, googleApiClient);
    }
}
