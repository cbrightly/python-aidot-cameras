package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.location.LocationCallback;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzap extends zzat {
    final /* synthetic */ LocationCallback zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzap(zzau zzau, GoogleApiClient googleApiClient, LocationCallback locationCallback) {
        super(googleApiClient);
        this.zza = locationCallback;
    }

    /* access modifiers changed from: protected */
    public final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzda) anyClient).zzB(ListenerHolders.createListenerKey(this.zza, LocationCallback.class.getSimpleName()), true, zzau.zza(this));
    }
}
