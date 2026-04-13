package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.MessageApi;

public final class zzex extends zzn<Status> {
    private ListenerHolder<MessageApi.MessageListener> zzax;
    private IntentFilter[] zzba;
    private MessageApi.MessageListener zzeg;

    private zzex(GoogleApiClient googleApiClient, MessageApi.MessageListener messageListener, ListenerHolder<MessageApi.MessageListener> listenerHolder, IntentFilter[] intentFilterArr) {
        super(googleApiClient);
        this.zzeg = (MessageApi.MessageListener) Preconditions.checkNotNull(messageListener);
        this.zzax = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        this.zzba = (IntentFilter[]) Preconditions.checkNotNull(intentFilterArr);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzhg) anyClient).zza((BaseImplementation.ResultHolder<Status>) this, this.zzeg, this.zzax, this.zzba);
        this.zzeg = null;
        this.zzax = null;
        this.zzba = null;
    }

    public final /* synthetic */ Result createFailedResult(Status status) {
        this.zzeg = null;
        this.zzax = null;
        this.zzba = null;
        return status;
    }

    /* synthetic */ zzex(GoogleApiClient googleApiClient, MessageApi.MessageListener messageListener, ListenerHolder listenerHolder, IntentFilter[] intentFilterArr, zzev zzev) {
        this(googleApiClient, messageListener, listenerHolder, intentFilterArr);
    }
}
