package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import java.util.List;
import javax.annotation.Nullable;

@VisibleForTesting
public final class zzhk<T> extends zzen {
    private final IntentFilter[] zzba;
    @Nullable
    private final String zzbb;
    private ListenerHolder<Object> zzfj;
    private ListenerHolder<Object> zzfk;
    private ListenerHolder<DataApi.DataListener> zzfl;
    private ListenerHolder<MessageApi.MessageListener> zzfm;
    private ListenerHolder<Object> zzfn;
    private ListenerHolder<Object> zzfo;
    private ListenerHolder<ChannelApi.ChannelListener> zzfp;
    private ListenerHolder<CapabilityApi.CapabilityListener> zzfq;

    public static zzhk<DataApi.DataListener> zza(ListenerHolder<DataApi.DataListener> listenerHolder, IntentFilter[] intentFilterArr) {
        zzhk<DataApi.DataListener> zzhk = new zzhk<>(intentFilterArr, (String) null);
        zzhk.zzfl = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        return zzhk;
    }

    public static zzhk<MessageApi.MessageListener> zzb(ListenerHolder<MessageApi.MessageListener> listenerHolder, IntentFilter[] intentFilterArr) {
        zzhk<MessageApi.MessageListener> zzhk = new zzhk<>(intentFilterArr, (String) null);
        zzhk.zzfm = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        return zzhk;
    }

    public static zzhk<ChannelApi.ChannelListener> zzc(ListenerHolder<ChannelApi.ChannelListener> listenerHolder, IntentFilter[] intentFilterArr) {
        zzhk<ChannelApi.ChannelListener> zzhk = new zzhk<>(intentFilterArr, (String) null);
        zzhk.zzfp = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        return zzhk;
    }

    public static zzhk<ChannelApi.ChannelListener> zza(ListenerHolder<ChannelApi.ChannelListener> listenerHolder, String str, IntentFilter[] intentFilterArr) {
        zzhk<ChannelApi.ChannelListener> zzhk = new zzhk<>(intentFilterArr, (String) Preconditions.checkNotNull(str));
        zzhk.zzfp = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        return zzhk;
    }

    public static zzhk<CapabilityApi.CapabilityListener> zzd(ListenerHolder<CapabilityApi.CapabilityListener> listenerHolder, IntentFilter[] intentFilterArr) {
        zzhk<CapabilityApi.CapabilityListener> zzhk = new zzhk<>(intentFilterArr, (String) null);
        zzhk.zzfq = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        return zzhk;
    }

    private zzhk(IntentFilter[] intentFilterArr, @Nullable String str) {
        this.zzba = (IntentFilter[]) Preconditions.checkNotNull(intentFilterArr);
        this.zzbb = str;
    }

    public final void clear() {
        zza((ListenerHolder<?>) null);
        this.zzfj = null;
        zza((ListenerHolder<?>) null);
        this.zzfk = null;
        zza((ListenerHolder<?>) this.zzfl);
        this.zzfl = null;
        zza((ListenerHolder<?>) this.zzfm);
        this.zzfm = null;
        zza((ListenerHolder<?>) null);
        this.zzfn = null;
        zza((ListenerHolder<?>) null);
        this.zzfo = null;
        zza((ListenerHolder<?>) this.zzfp);
        this.zzfp = null;
        zza((ListenerHolder<?>) this.zzfq);
        this.zzfq = null;
    }

    public final void zza(zzl zzl) {
    }

    public final void zza(zzi zzi) {
    }

    public final void zza(DataHolder dataHolder) {
        ListenerHolder<DataApi.DataListener> listenerHolder = this.zzfl;
        if (listenerHolder != null) {
            listenerHolder.notifyListener(new zzhl(dataHolder));
        } else {
            dataHolder.close();
        }
    }

    public final void zza(zzfe zzfe) {
        ListenerHolder<MessageApi.MessageListener> listenerHolder = this.zzfm;
        if (listenerHolder != null) {
            listenerHolder.notifyListener(new zzhm(zzfe));
        }
    }

    public final void zza(zzfo zzfo2) {
    }

    public final void zzb(zzfo zzfo2) {
    }

    public final void onConnectedNodes(List<zzfo> list) {
    }

    public final void zza(zzaw zzaw) {
        ListenerHolder<ChannelApi.ChannelListener> listenerHolder = this.zzfp;
        if (listenerHolder != null) {
            listenerHolder.notifyListener(new zzhn(zzaw));
        }
    }

    public final void zza(zzah zzah) {
        ListenerHolder<CapabilityApi.CapabilityListener> listenerHolder = this.zzfq;
        if (listenerHolder != null) {
            listenerHolder.notifyListener(new zzho(zzah));
        }
    }

    public final IntentFilter[] zze() {
        return this.zzba;
    }

    @Nullable
    public final String zzf() {
        return this.zzbb;
    }

    private static void zza(ListenerHolder<?> listenerHolder) {
        if (listenerHolder != null) {
            listenerHolder.clear();
        }
    }
}
