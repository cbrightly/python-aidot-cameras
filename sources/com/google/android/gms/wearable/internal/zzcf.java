package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.DataApi;

public final class zzcf extends zzn<Status> {
    private final /* synthetic */ DataApi.DataListener zzdf;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcf(zzbw zzbw, GoogleApiClient googleApiClient, DataApi.DataListener dataListener) {
        super(googleApiClient);
        this.zzdf = dataListener;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzhg) anyClient).zza((BaseImplementation.ResultHolder<Status>) this, this.zzdf);
    }

    public final /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }
}
