package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;

public final class zzcj extends DataClient {
    private final DataApi zzdi = new zzbw();

    public zzcj(@NonNull Context context, @NonNull GoogleApi.Settings settings) {
        super(context, settings);
    }

    public zzcj(@NonNull Activity activity, @NonNull GoogleApi.Settings settings) {
        super(activity, settings);
    }

    public final Task<DataItem> putDataItem(@NonNull PutDataRequest putDataRequest) {
        return PendingResultUtil.toTask(this.zzdi.putDataItem(asGoogleApiClient(), putDataRequest), zzck.zzbx);
    }

    public final Task<DataItem> getDataItem(@NonNull Uri uri) {
        return PendingResultUtil.toTask(this.zzdi.getDataItem(asGoogleApiClient(), uri), zzcl.zzbx);
    }

    public final Task<DataItemBuffer> getDataItems() {
        return PendingResultUtil.toTask(this.zzdi.getDataItems(asGoogleApiClient()), zzcm.zzbx);
    }

    public final Task<DataItemBuffer> getDataItems(@NonNull Uri uri) {
        return PendingResultUtil.toTask(this.zzdi.getDataItems(asGoogleApiClient(), uri), zzcn.zzbx);
    }

    public final Task<DataItemBuffer> getDataItems(@NonNull Uri uri, int i) {
        return PendingResultUtil.toTask(this.zzdi.getDataItems(asGoogleApiClient(), uri, i), zzco.zzbx);
    }

    public final Task<Integer> deleteDataItems(@NonNull Uri uri) {
        return PendingResultUtil.toTask(this.zzdi.deleteDataItems(asGoogleApiClient(), uri), zzcp.zzbx);
    }

    public final Task<Integer> deleteDataItems(@NonNull Uri uri, int i) {
        return PendingResultUtil.toTask(this.zzdi.deleteDataItems(asGoogleApiClient(), uri, i), zzcq.zzbx);
    }

    public final Task<DataClient.GetFdForAssetResponse> getFdForAsset(@NonNull Asset asset) {
        return PendingResultUtil.toTask(this.zzdi.getFdForAsset(asGoogleApiClient(), asset), zzcr.zzbx);
    }

    public final Task<DataClient.GetFdForAssetResponse> getFdForAsset(@NonNull DataItemAsset dataItemAsset) {
        return PendingResultUtil.toTask(this.zzdi.getFdForAsset(asGoogleApiClient(), dataItemAsset), zzcs.zzbx);
    }

    public final Task<Void> addListener(@NonNull DataClient.OnDataChangedListener onDataChangedListener) {
        return zza(onDataChangedListener, new IntentFilter[]{zzgj.zzc("com.google.android.gms.wearable.DATA_CHANGED")});
    }

    public final Task<Void> addListener(@NonNull DataClient.OnDataChangedListener onDataChangedListener, @NonNull Uri uri, int i) {
        Asserts.checkNotNull(uri, "uri must not be null");
        Preconditions.checkArgument(i == 0 || i == 1, "invalid filter type");
        return zza(onDataChangedListener, new IntentFilter[]{zzgj.zza("com.google.android.gms.wearable.DATA_CHANGED", uri, i)});
    }

    private final Task<Void> zza(DataClient.OnDataChangedListener onDataChangedListener, IntentFilter[] intentFilterArr) {
        ListenerHolder createListenerHolder = ListenerHolders.createListenerHolder(onDataChangedListener, getLooper(), "DataListener");
        return doRegisterEventListener(new zzcv(onDataChangedListener, intentFilterArr, createListenerHolder), new zzcw(onDataChangedListener, createListenerHolder.getListenerKey()));
    }

    public final Task<Boolean> removeListener(@NonNull DataClient.OnDataChangedListener onDataChangedListener) {
        return doUnregisterEventListener(ListenerHolders.createListenerHolder(onDataChangedListener, getLooper(), "DataListener").getListenerKey());
    }
}
