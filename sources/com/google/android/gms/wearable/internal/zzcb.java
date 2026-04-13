package com.google.android.gms.wearable.internal;

import android.net.Uri;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;

public final class zzcb extends zzn<DataApi.DeleteDataItemsResult> {
    private final /* synthetic */ Uri zzco;
    private final /* synthetic */ int zzdc;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcb(zzbw zzbw, GoogleApiClient googleApiClient, Uri uri, int i) {
        super(googleApiClient);
        this.zzco = uri;
        this.zzdc = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzep) ((zzhg) anyClient).getService()).zzb((zzek) new zzgp(this), this.zzco, this.zzdc);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result createFailedResult(Status status) {
        return new zzch(status, 0);
    }
}
