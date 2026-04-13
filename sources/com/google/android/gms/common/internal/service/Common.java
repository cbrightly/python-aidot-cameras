package com.google.android.gms.common.internal.service;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class Common {
    @NonNull
    @KeepForSdk
    public static final Api<Api.ApiOptions.NoOptions> API;
    @NonNull
    @KeepForSdk
    public static final Api.ClientKey<zah> CLIENT_KEY;
    public static final zae zaa = new zae();
    private static final Api.AbstractClientBuilder zab;

    static {
        Api.ClientKey<zah> clientKey = new Api.ClientKey<>();
        CLIENT_KEY = clientKey;
        zab zab2 = new zab();
        zab = zab2;
        API = new Api<>("Common.API", zab2, clientKey);
    }
}
