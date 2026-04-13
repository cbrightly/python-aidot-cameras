package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Channel;
import java.io.InputStream;

public final class zzbb extends zzn<Channel.GetInputStreamResult> {
    private final /* synthetic */ zzay zzcm;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbb(zzay zzay, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzcm = zzay;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        String zza = this.zzcm.zzce;
        zzbr zzbr = new zzbr();
        ((zzep) ((zzhg) anyClient).getService()).zza((zzek) new zzgs(this, zzbr), (zzei) zzbr, zza);
    }

    public final /* synthetic */ Result createFailedResult(Status status) {
        return new zzbg(status, (InputStream) null);
    }
}
