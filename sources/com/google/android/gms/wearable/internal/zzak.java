package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;

public final class zzak extends zzn<ChannelApi.OpenChannelResult> {
    private final /* synthetic */ String zzcb;
    private final /* synthetic */ String zzcc;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzak(zzaj zzaj, GoogleApiClient googleApiClient, String str, String str2) {
        super(googleApiClient);
        this.zzcb = str;
        this.zzcc = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzep) ((zzhg) anyClient).getService()).zza((zzek) new zzha(this), this.zzcb, this.zzcc);
    }

    public final /* synthetic */ Result createFailedResult(Status status) {
        return new zzam(status, (Channel) null);
    }
}
