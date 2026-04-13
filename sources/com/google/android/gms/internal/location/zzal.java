package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.LocationRequest;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzal extends zzat {
    final /* synthetic */ ListenerHolder zza;
    final /* synthetic */ LocationRequest zzb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzal(zzau zzau, GoogleApiClient googleApiClient, ListenerHolder listenerHolder, LocationRequest locationRequest) {
        super(googleApiClient);
        this.zza = listenerHolder;
        this.zzb = locationRequest;
    }

    /* access modifiers changed from: protected */
    public final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzda) anyClient).zzu(new zzas(this.zza), this.zzb, zzau.zza(this));
    }
}
