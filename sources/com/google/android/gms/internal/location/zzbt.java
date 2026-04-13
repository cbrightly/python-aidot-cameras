package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzbt extends zzbu {
    final /* synthetic */ List zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbt(zzbv zzbv, GoogleApiClient googleApiClient, List list) {
        super(googleApiClient);
        this.zza = list;
    }

    /* access modifiers changed from: protected */
    public final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) {
        ((zzda) anyClient).zzy(this.zza, zzbv.zza(this));
    }
}
