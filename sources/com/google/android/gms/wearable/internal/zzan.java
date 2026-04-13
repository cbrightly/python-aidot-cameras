package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.ChannelApi;
import javax.annotation.Nullable;

public final class zzan extends zzn<Status> {
    private final String zzce;
    private ChannelApi.ChannelListener zzcf;

    zzan(GoogleApiClient googleApiClient, ChannelApi.ChannelListener channelListener, @Nullable String str) {
        super(googleApiClient);
        this.zzcf = (ChannelApi.ChannelListener) Preconditions.checkNotNull(channelListener);
        this.zzce = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzhg) anyClient).zza(this, this.zzcf, this.zzce);
        this.zzcf = null;
    }

    public final /* synthetic */ Result createFailedResult(Status status) {
        this.zzcf = null;
        return status;
    }
}
