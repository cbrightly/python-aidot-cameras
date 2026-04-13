package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzam extends zzat {
    final /* synthetic */ PendingIntent zza;
    final /* synthetic */ LocationRequest zzb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzam(zzau zzau, GoogleApiClient googleApiClient, PendingIntent pendingIntent, LocationRequest locationRequest) {
        super(googleApiClient);
        this.zza = pendingIntent;
        this.zzb = locationRequest;
    }

    /* access modifiers changed from: protected */
    public final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzda) anyClient).zzw(this.zza, this.zzb, zzau.zza(this));
    }
}
