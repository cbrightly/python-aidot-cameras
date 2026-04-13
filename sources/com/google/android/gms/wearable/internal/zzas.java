package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.ChannelClient;

public final class zzas implements ChannelApi.ChannelListener {
    private final ChannelClient.ChannelCallback zzch;

    public zzas(ChannelClient.ChannelCallback channelCallback) {
        this.zzch = channelCallback;
    }

    public final void onChannelOpened(Channel channel) {
        this.zzch.onChannelOpened(zzao.zza(channel));
    }

    public final void onChannelClosed(Channel channel, int i, int i2) {
        this.zzch.onChannelClosed(zzao.zza(channel), i, i2);
    }

    public final void onInputClosed(Channel channel, int i, int i2) {
        this.zzch.onInputClosed(zzao.zza(channel), i, i2);
    }

    public final void onOutputClosed(Channel channel, int i, int i2) {
        this.zzch.onOutputClosed(zzao.zza(channel), i, i2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || zzas.class != obj.getClass()) {
            return false;
        }
        return this.zzch.equals(((zzas) obj).zzch);
    }

    public final int hashCode() {
        return this.zzch.hashCode();
    }
}
