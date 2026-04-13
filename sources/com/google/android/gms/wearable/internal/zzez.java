package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.util.Preconditions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageClient;

public final class zzez extends MessageClient {
    @VisibleForTesting
    private final MessageApi zzei = new zzeu();

    public zzez(@NonNull Context context, @NonNull GoogleApi.Settings settings) {
        super(context, settings);
    }

    public zzez(@NonNull Activity activity, @NonNull GoogleApi.Settings settings) {
        super(activity, settings);
    }

    public final Task<Integer> sendMessage(String str, String str2, byte[] bArr) {
        return PendingResultUtil.toTask(this.zzei.sendMessage(asGoogleApiClient(), str, str2, bArr), zzfa.zzbx);
    }

    public final Task<Void> addListener(MessageClient.OnMessageReceivedListener onMessageReceivedListener) {
        return zza(onMessageReceivedListener, new IntentFilter[]{zzgj.zzc("com.google.android.gms.wearable.MESSAGE_RECEIVED")});
    }

    public final Task<Void> addListener(MessageClient.OnMessageReceivedListener onMessageReceivedListener, Uri uri, int i) {
        Preconditions.checkNotNull(uri, "uri must not be null");
        com.google.android.gms.common.internal.Preconditions.checkArgument(i == 0 || i == 1, "invalid filter type");
        return zza(onMessageReceivedListener, new IntentFilter[]{zzgj.zza("com.google.android.gms.wearable.MESSAGE_RECEIVED", uri, i)});
    }

    private final Task<Void> zza(MessageClient.OnMessageReceivedListener onMessageReceivedListener, IntentFilter[] intentFilterArr) {
        ListenerHolder createListenerHolder = ListenerHolders.createListenerHolder(onMessageReceivedListener, getLooper(), "MessageListener");
        return doRegisterEventListener(new zzfc(onMessageReceivedListener, intentFilterArr, createListenerHolder), new zzfd(onMessageReceivedListener, createListenerHolder.getListenerKey()));
    }

    public final Task<Boolean> removeListener(@NonNull MessageClient.OnMessageReceivedListener onMessageReceivedListener) {
        return doUnregisterEventListener(ListenerHolders.createListenerHolder(onMessageReceivedListener, getLooper(), "MessageListener").getListenerKey());
    }
}
