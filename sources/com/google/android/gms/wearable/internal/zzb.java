package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;

public final class zzb<T> extends zzn<Status> {
    private T zzaw;
    private ListenerHolder<T> zzax;
    private zzc<T> zzay;

    static <T> PendingResult<Status> zza(GoogleApiClient googleApiClient, zzc<T> zzc, T t) {
        return googleApiClient.enqueue(new zzb(googleApiClient, t, googleApiClient.registerListener(t), zzc));
    }

    private zzb(GoogleApiClient googleApiClient, T t, ListenerHolder<T> listenerHolder, zzc<T> zzc) {
        super(googleApiClient);
        this.zzaw = Preconditions.checkNotNull(t);
        this.zzax = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        this.zzay = (zzc) Preconditions.checkNotNull(zzc);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        this.zzay.zza((zzhg) anyClient, this, this.zzaw, this.zzax);
        this.zzaw = null;
        this.zzax = null;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result createFailedResult(Status status) {
        this.zzaw = null;
        this.zzax = null;
        return status;
    }
}
