package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;

public final class zzp extends zzn<CapabilityApi.GetCapabilityResult> {
    private final /* synthetic */ String zzbp;
    private final /* synthetic */ int zzbq;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzp(zzo zzo, GoogleApiClient googleApiClient, String str, int i) {
        super(googleApiClient);
        this.zzbp = str;
        this.zzbq = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzep) ((zzhg) anyClient).getService()).zza((zzek) new zzgr(this), this.zzbp, this.zzbq);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result createFailedResult(Status status) {
        return new zzy(status, (CapabilityInfo) null);
    }
}
