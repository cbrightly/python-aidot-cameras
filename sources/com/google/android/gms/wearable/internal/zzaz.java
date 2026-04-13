package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public final class zzaz extends zzn<Status> {
    private final /* synthetic */ zzay zzcm;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzaz(zzay zzay, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzcm = zzay;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzep) ((zzhg) anyClient).getService()).zzc(new zzgn(this), this.zzcm.zzce);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }
}
