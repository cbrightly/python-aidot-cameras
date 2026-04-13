package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.MessageApi;

public final class zzev extends zzn<MessageApi.SendMessageResult> {
    private final /* synthetic */ String val$action;
    private final /* synthetic */ String zzcb;
    private final /* synthetic */ byte[] zzee;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzev(zzeu zzeu, GoogleApiClient googleApiClient, String str, String str2, byte[] bArr) {
        super(googleApiClient);
        this.zzcb = str;
        this.val$action = str2;
        this.zzee = bArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzep) ((zzhg) anyClient).getService()).zza(new zzhe(this), this.zzcb, this.val$action, this.zzee);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result createFailedResult(Status status) {
        return new zzey(status, -1);
    }
}
