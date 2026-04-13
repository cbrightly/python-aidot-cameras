package com.google.android.gms.wearable.internal;

import android.net.Uri;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;

public final class zzby extends zzn<DataApi.DataItemResult> {
    private final /* synthetic */ Uri zzco;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzby(zzbw zzbw, GoogleApiClient googleApiClient, Uri uri) {
        super(googleApiClient);
        this.zzco = uri;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzep) ((zzhg) anyClient).getService()).zza((zzek) new zzgv(this), this.zzco);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result createFailedResult(Status status) {
        return new zzcg(status, (DataItem) null);
    }
}
