package com.google.android.gms.wearable;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.internal.zzhg;

public final class zzj extends Api.AbstractClientBuilder<zzhg, Wearable.WearableOptions> {
    zzj() {
    }

    public final /* synthetic */ Api.Client buildClient(Context context, Looper looper, ClientSettings clientSettings, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        if (((Wearable.WearableOptions) obj) == null) {
            new Wearable.WearableOptions(new Wearable.WearableOptions.Builder(), (zzj) null);
        }
        return new zzhg(context, looper, connectionCallbacks, onConnectionFailedListener, clientSettings);
    }
}
