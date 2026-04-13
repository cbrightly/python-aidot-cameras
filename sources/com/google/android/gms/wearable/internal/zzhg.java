package com.google.android.gms.wearable.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.WearableStatusCodes;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.annotation.Nullable;

public final class zzhg extends GmsClient<zzep> {
    private final ExecutorService zzew;
    private final zzer<Object> zzex;
    private final zzer<Object> zzey;
    private final zzer<ChannelApi.ChannelListener> zzez;
    private final zzer<DataApi.DataListener> zzfa;
    private final zzer<MessageApi.MessageListener> zzfb;
    private final zzer<Object> zzfc;
    private final zzer<Object> zzfd;
    private final zzer<CapabilityApi.CapabilityListener> zzfe;
    private final zzhp zzff;

    public zzhg(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, ClientSettings clientSettings) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, clientSettings, Executors.newCachedThreadPool(), zzhp.zza(context));
    }

    @VisibleForTesting
    private zzhg(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, ClientSettings clientSettings, ExecutorService executorService, zzhp zzhp) {
        super(context, looper, 14, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzex = new zzer<>();
        this.zzey = new zzer<>();
        this.zzez = new zzer<>();
        this.zzfa = new zzer<>();
        this.zzfb = new zzer<>();
        this.zzfc = new zzer<>();
        this.zzfd = new zzer<>();
        this.zzfe = new zzer<>();
        this.zzew = (ExecutorService) Preconditions.checkNotNull(executorService);
        this.zzff = zzhp;
    }

    /* access modifiers changed from: protected */
    public final String getStartServiceAction() {
        return "com.google.android.gms.wearable.BIND";
    }

    /* access modifiers changed from: protected */
    public final String getStartServicePackage() {
        if (this.zzff.zze("com.google.android.wearable.app.cn")) {
            return "com.google.android.wearable.app.cn";
        }
        return "com.google.android.gms";
    }

    /* access modifiers changed from: protected */
    public final String getServiceDescriptor() {
        return "com.google.android.gms.wearable.internal.IWearableService";
    }

    public final boolean requiresGooglePlayServices() {
        return !this.zzff.zze("com.google.android.wearable.app.cn");
    }

    /* access modifiers changed from: protected */
    public final void onPostInitHandler(int i, IBinder iBinder, Bundle bundle, int i2) {
        if (Log.isLoggable("WearableClient", 2)) {
            StringBuilder sb = new StringBuilder(41);
            sb.append("onPostInitHandler: statusCode ");
            sb.append(i);
            Log.v("WearableClient", sb.toString());
        }
        if (i == 0) {
            this.zzex.zza(iBinder);
            this.zzey.zza(iBinder);
            this.zzez.zza(iBinder);
            this.zzfa.zza(iBinder);
            this.zzfb.zza(iBinder);
            this.zzfc.zza(iBinder);
            this.zzfd.zza(iBinder);
            this.zzfe.zza(iBinder);
        }
        super.onPostInitHandler(i, iBinder, bundle, i2);
    }

    public final void connect(@NonNull BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        if (!requiresGooglePlayServices()) {
            try {
                Bundle bundle = getContext().getPackageManager().getApplicationInfo("com.google.android.wearable.app.cn", 128).metaData;
                int i = bundle != null ? bundle.getInt("com.google.android.wearable.api.version", 0) : 0;
                if (i < 8600000) {
                    StringBuilder sb = new StringBuilder(82);
                    sb.append("The Wear OS app is out of date. Requires API version 8600000 but found ");
                    sb.append(i);
                    Log.w("WearableClient", sb.toString());
                    Context context = getContext();
                    Context context2 = getContext();
                    Intent intent = new Intent("com.google.android.wearable.app.cn.UPDATE_ANDROID_WEAR").setPackage("com.google.android.wearable.app.cn");
                    if (context2.getPackageManager().resolveActivity(intent, 65536) == null) {
                        intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details").buildUpon().appendQueryParameter("id", "com.google.android.wearable.app.cn").build());
                    }
                    triggerNotAvailable(connectionProgressReportCallbacks, 6, PendingIntent.getActivity(context, 0, intent, 0));
                    return;
                }
            } catch (PackageManager.NameNotFoundException e) {
                triggerNotAvailable(connectionProgressReportCallbacks, 16, (PendingIntent) null);
                return;
            }
        }
        super.connect(connectionProgressReportCallbacks);
    }

    public final void zza(BaseImplementation.ResultHolder<DataApi.DataItemResult> resultHolder, PutDataRequest putDataRequest) {
        BaseImplementation.ResultHolder<DataApi.DataItemResult> resultHolder2 = resultHolder;
        for (Map.Entry<String, Asset> value : putDataRequest.getAssets().entrySet()) {
            Asset asset = (Asset) value.getValue();
            if (asset.getData() == null && asset.getDigest() == null && asset.getFd() == null && asset.getUri() == null) {
                String valueOf = String.valueOf(putDataRequest.getUri());
                String valueOf2 = String.valueOf(asset);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 33 + String.valueOf(valueOf2).length());
                sb.append("Put for ");
                sb.append(valueOf);
                sb.append(" contains invalid asset: ");
                sb.append(valueOf2);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        PutDataRequest zza = PutDataRequest.zza(putDataRequest.getUri());
        zza.setData(putDataRequest.getData());
        if (putDataRequest.isUrgent()) {
            zza.setUrgent();
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry next : putDataRequest.getAssets().entrySet()) {
            Asset asset2 = (Asset) next.getValue();
            if (asset2.getData() != null) {
                try {
                    ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
                    if (Log.isLoggable("WearableClient", 3)) {
                        String valueOf3 = String.valueOf(asset2);
                        String valueOf4 = String.valueOf(createPipe[0]);
                        String valueOf5 = String.valueOf(createPipe[1]);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 61 + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length());
                        sb2.append("processAssets: replacing data with FD in asset: ");
                        sb2.append(valueOf3);
                        sb2.append(" read:");
                        sb2.append(valueOf4);
                        sb2.append(" write:");
                        sb2.append(valueOf5);
                        Log.d("WearableClient", sb2.toString());
                    }
                    zza.putAsset((String) next.getKey(), Asset.createFromFd(createPipe[0]));
                    FutureTask futureTask = new FutureTask(new zzhh(this, createPipe[1], asset2.getData()));
                    arrayList.add(futureTask);
                    this.zzew.submit(futureTask);
                } catch (IOException e) {
                    String valueOf6 = String.valueOf(putDataRequest);
                    StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf6).length() + 60);
                    sb3.append("Unable to create ParcelFileDescriptor for asset in request: ");
                    sb3.append(valueOf6);
                    throw new IllegalStateException(sb3.toString(), e);
                }
            } else if (asset2.getUri() != null) {
                try {
                    zza.putAsset((String) next.getKey(), Asset.createFromFd(getContext().getContentResolver().openFileDescriptor(asset2.getUri(), "r")));
                } catch (FileNotFoundException e2) {
                    new zzhb(resultHolder2, arrayList).zza(new zzfu(WearableStatusCodes.ASSET_UNAVAILABLE, (zzdd) null));
                    String valueOf7 = String.valueOf(asset2.getUri());
                    StringBuilder sb4 = new StringBuilder(String.valueOf(valueOf7).length() + 28);
                    sb4.append("Couldn't resolve asset URI: ");
                    sb4.append(valueOf7);
                    Log.w("WearableClient", sb4.toString());
                    return;
                }
            } else {
                zza.putAsset((String) next.getKey(), asset2);
            }
        }
        ((zzep) getService()).zza((zzek) new zzhb(resultHolder2, arrayList), zza);
    }

    public final void zza(BaseImplementation.ResultHolder<DataApi.GetFdForAssetResult> resultHolder, Asset asset) {
        ((zzep) getService()).zza((zzek) new zzgx(resultHolder), asset);
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, String str, Uri uri, boolean z) {
        try {
            ExecutorService executorService = this.zzew;
            Preconditions.checkNotNull(resultHolder);
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(uri);
            executorService.execute(new zzhi(this, uri, resultHolder, z, str));
        } catch (RuntimeException e) {
            resultHolder.setFailedResult(new Status(8));
            throw e;
        }
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, String str, Uri uri, long j, long j2) {
        try {
            ExecutorService executorService = this.zzew;
            Preconditions.checkNotNull(resultHolder);
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(uri);
            Preconditions.checkArgument(j >= 0, "startOffset is negative: %s", Long.valueOf(j));
            Preconditions.checkArgument(j2 >= -1, "invalid length: %s", Long.valueOf(j2));
            executorService.execute(new zzhj(this, uri, resultHolder, str, j, j2));
        } catch (RuntimeException e) {
            BaseImplementation.ResultHolder<Status> resultHolder2 = resultHolder;
            resultHolder.setFailedResult(new Status(8));
            throw e;
        }
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, DataApi.DataListener dataListener, ListenerHolder<DataApi.DataListener> listenerHolder, IntentFilter[] intentFilterArr) {
        this.zzfa.zza(this, resultHolder, dataListener, zzhk.zza(listenerHolder, intentFilterArr));
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, MessageApi.MessageListener messageListener, ListenerHolder<MessageApi.MessageListener> listenerHolder, IntentFilter[] intentFilterArr) {
        this.zzfb.zza(this, resultHolder, messageListener, zzhk.zzb(listenerHolder, intentFilterArr));
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, CapabilityApi.CapabilityListener capabilityListener, ListenerHolder<CapabilityApi.CapabilityListener> listenerHolder, IntentFilter[] intentFilterArr) {
        this.zzfe.zza(this, resultHolder, capabilityListener, zzhk.zzd(listenerHolder, intentFilterArr));
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, ChannelApi.ChannelListener channelListener, ListenerHolder<ChannelApi.ChannelListener> listenerHolder, @Nullable String str, IntentFilter[] intentFilterArr) {
        if (str == null) {
            this.zzez.zza(this, resultHolder, channelListener, zzhk.zzc(listenerHolder, intentFilterArr));
            return;
        }
        this.zzez.zza(this, resultHolder, new zzgc(str, channelListener), zzhk.zza(listenerHolder, str, intentFilterArr));
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, DataApi.DataListener dataListener) {
        this.zzfa.zza(this, resultHolder, dataListener);
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, MessageApi.MessageListener messageListener) {
        this.zzfb.zza(this, resultHolder, messageListener);
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, CapabilityApi.CapabilityListener capabilityListener) {
        this.zzfe.zza(this, resultHolder, capabilityListener);
    }

    public final void zza(BaseImplementation.ResultHolder<Status> resultHolder, ChannelApi.ChannelListener channelListener, String str) {
        if (str == null) {
            this.zzez.zza(this, resultHolder, channelListener);
            return;
        }
        this.zzez.zza(this, resultHolder, new zzgc(str, channelListener));
    }

    public final int getMinApkVersion() {
        return 8600000;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableService");
        if (queryLocalInterface instanceof zzep) {
            return (zzep) queryLocalInterface;
        }
        return new zzeq(iBinder);
    }
}
