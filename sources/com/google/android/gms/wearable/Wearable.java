package com.google.android.gms.wearable;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.util.Preconditions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.wearable.internal.zzaa;
import com.google.android.gms.wearable.internal.zzaj;
import com.google.android.gms.wearable.internal.zzao;
import com.google.android.gms.wearable.internal.zzbv;
import com.google.android.gms.wearable.internal.zzbw;
import com.google.android.gms.wearable.internal.zzcj;
import com.google.android.gms.wearable.internal.zzeu;
import com.google.android.gms.wearable.internal.zzez;
import com.google.android.gms.wearable.internal.zzfg;
import com.google.android.gms.wearable.internal.zzfl;
import com.google.android.gms.wearable.internal.zzgi;
import com.google.android.gms.wearable.internal.zzh;
import com.google.android.gms.wearable.internal.zzhg;
import com.google.android.gms.wearable.internal.zzhq;
import com.google.android.gms.wearable.internal.zzk;
import com.google.android.gms.wearable.internal.zzo;

public class Wearable {
    @Deprecated
    public static final Api<WearableOptions> API;
    private static final Api.AbstractClientBuilder<zzhg, WearableOptions> CLIENT_BUILDER;
    private static final Api.ClientKey<zzhg> CLIENT_KEY;
    @Deprecated
    public static final CapabilityApi CapabilityApi = new zzo();
    @Deprecated
    public static final ChannelApi ChannelApi = new zzaj();
    @Deprecated
    public static final DataApi DataApi = new zzbw();
    @Deprecated
    public static final MessageApi MessageApi = new zzeu();
    @Deprecated
    public static final NodeApi NodeApi = new zzfg();
    @Deprecated
    private static final zzi zzaa = new zzgi();
    @Deprecated
    private static final zzu zzab = new zzhq();
    @Deprecated
    private static final zzc zzx = new zzk();
    @Deprecated
    private static final zza zzy = new zzh();
    @Deprecated
    private static final zzf zzz = new zzbv();

    private Wearable() {
    }

    public static DataClient getDataClient(@NonNull Context context) {
        return new zzcj(context, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public static final class WearableOptions implements Api.ApiOptions.Optional {
        private final Looper zzac;

        public static class Builder {
            /* access modifiers changed from: private */
            public Looper zzac;

            public WearableOptions build() {
                return new WearableOptions(this, (zzj) null);
            }

            public Builder setLooper(Looper looper) {
                this.zzac = looper;
                return this;
            }
        }

        private WearableOptions(Builder builder) {
            this.zzac = builder.zzac;
        }

        /* access modifiers changed from: private */
        public final GoogleApi.Settings zza() {
            if (this.zzac != null) {
                return new GoogleApi.Settings.Builder().setLooper(this.zzac).build();
            }
            return GoogleApi.Settings.DEFAULT_SETTINGS;
        }

        /* synthetic */ WearableOptions(Builder builder, zzj zzj) {
            this(builder);
        }
    }

    public static DataClient getDataClient(@NonNull Context context, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzcj(context, wearableOptions.zza());
    }

    public static DataClient getDataClient(@NonNull Activity activity) {
        return new zzcj(activity, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public static DataClient getDataClient(@NonNull Activity activity, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzcj(activity, wearableOptions.zza());
    }

    public static MessageClient getMessageClient(@NonNull Context context) {
        return new zzez(context, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public static MessageClient getMessageClient(@NonNull Context context, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzez(context, wearableOptions.zza());
    }

    public static MessageClient getMessageClient(@NonNull Activity activity) {
        return new zzez(activity, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public static MessageClient getMessageClient(@NonNull Activity activity, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzez(activity, wearableOptions.zza());
    }

    public static CapabilityClient getCapabilityClient(@NonNull Context context) {
        return new zzaa(context, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public static CapabilityClient getCapabilityClient(@NonNull Context context, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzaa(context, wearableOptions.zza());
    }

    public static CapabilityClient getCapabilityClient(@NonNull Activity activity) {
        return new zzaa(activity, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public static CapabilityClient getCapabilityClient(@NonNull Activity activity, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzaa(activity, wearableOptions.zza());
    }

    public static NodeClient getNodeClient(@NonNull Context context) {
        return new zzfl(context, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public static NodeClient getNodeClient(@NonNull Context context, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzfl(context, wearableOptions.zza());
    }

    public static NodeClient getNodeClient(@NonNull Activity activity) {
        return new zzfl(activity, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public static NodeClient getNodeClient(@NonNull Activity activity, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzfl(activity, wearableOptions.zza());
    }

    public static ChannelClient getChannelClient(@NonNull Context context) {
        return new zzao(context, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public static ChannelClient getChannelClient(@NonNull Context context, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzao(context, wearableOptions.zza());
    }

    public static ChannelClient getChannelClient(@NonNull Activity activity) {
        return new zzao(activity, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public static ChannelClient getChannelClient(@NonNull Activity activity, @NonNull WearableOptions wearableOptions) {
        Preconditions.checkNotNull(wearableOptions, "options must not be null");
        return new zzao(activity, wearableOptions.zza());
    }

    /* JADX WARNING: type inference failed for: r0v5, types: [com.google.android.gms.wearable.internal.zzk, com.google.android.gms.wearable.zzc] */
    /* JADX WARNING: type inference failed for: r0v6, types: [com.google.android.gms.wearable.zza, com.google.android.gms.wearable.internal.zzh] */
    /* JADX WARNING: type inference failed for: r0v7, types: [com.google.android.gms.wearable.internal.zzbv, com.google.android.gms.wearable.zzf] */
    /* JADX WARNING: type inference failed for: r0v8, types: [com.google.android.gms.wearable.zzi, com.google.android.gms.wearable.internal.zzgi] */
    /* JADX WARNING: type inference failed for: r0v9, types: [com.google.android.gms.wearable.internal.zzhq, com.google.android.gms.wearable.zzu] */
    static {
        Api.ClientKey<zzhg> clientKey = new Api.ClientKey<>();
        CLIENT_KEY = clientKey;
        zzj zzj = new zzj();
        CLIENT_BUILDER = zzj;
        API = new Api<>("Wearable.API", zzj, clientKey);
    }
}
