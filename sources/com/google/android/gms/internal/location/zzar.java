package com.google.android.gms.internal.location;

import android.location.Location;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzar extends zzat {
    final /* synthetic */ Location zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzar(zzau zzau, GoogleApiClient googleApiClient, Location location) {
        super(googleApiClient);
        this.zza = location;
    }

    /* access modifiers changed from: protected */
    public final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzda) anyClient).zzz(this.zza, zzau.zza(this));
    }
}
