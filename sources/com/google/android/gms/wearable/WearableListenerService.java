package com.google.android.gms.wearable;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.ChannelClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.internal.zzah;
import com.google.android.gms.wearable.internal.zzas;
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.internal.zzen;
import com.google.android.gms.wearable.internal.zzfe;
import com.google.android.gms.wearable.internal.zzfo;
import com.google.android.gms.wearable.internal.zzhp;
import com.google.android.gms.wearable.internal.zzi;
import com.google.android.gms.wearable.internal.zzl;
import java.util.List;

public class WearableListenerService extends Service implements CapabilityApi.CapabilityListener, ChannelApi.ChannelListener, DataApi.DataListener, MessageApi.MessageListener {
    public static final String BIND_LISTENER_INTENT_ACTION = "com.google.android.gms.wearable.BIND_LISTENER";
    /* access modifiers changed from: private */
    public ComponentName service;
    /* access modifiers changed from: private */
    public zzc zzad;
    private IBinder zzae;
    /* access modifiers changed from: private */
    public Intent zzaf;
    private Looper zzag;
    /* access modifiers changed from: private */
    public final Object zzah = new Object();
    /* access modifiers changed from: private */
    public boolean zzai;
    /* access modifiers changed from: private */
    public zzas zzaj = new zzas(new zza());

    public final class zza extends ChannelClient.ChannelCallback {
        private zza() {
        }

        public final void onChannelOpened(@NonNull ChannelClient.Channel channel) {
            WearableListenerService.this.onChannelOpened(channel);
        }

        public final void onChannelClosed(@NonNull ChannelClient.Channel channel, int i, int i2) {
            WearableListenerService.this.onChannelClosed(channel, i, i2);
        }

        public final void onInputClosed(@NonNull ChannelClient.Channel channel, int i, int i2) {
            WearableListenerService.this.onInputClosed(channel, i, i2);
        }

        public final void onOutputClosed(@NonNull ChannelClient.Channel channel, int i, int i2) {
            WearableListenerService.this.onOutputClosed(channel, i, i2);
        }
    }

    public final class zzb implements ServiceConnection {
        private zzb(WearableListenerService wearableListenerService) {
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        }

        public final void onServiceDisconnected(ComponentName componentName) {
        }
    }

    public final class zzd extends zzen {
        private volatile int zzam;

        private zzd() {
            this.zzam = -1;
        }

        public final void zza(DataHolder dataHolder) {
            zzl zzl = new zzl(this, dataHolder);
            try {
                String valueOf = String.valueOf(dataHolder);
                int count = dataHolder.getCount();
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
                sb.append(valueOf);
                sb.append(", rows=");
                sb.append(count);
                if (zza(zzl, "onDataItemChanged", sb.toString())) {
                }
            } finally {
                dataHolder.close();
            }
        }

        public final void zza(zzfe zzfe) {
            zza(new zzm(this, zzfe), "onMessageReceived", zzfe);
        }

        public final void zza(zzfo zzfo) {
            zza(new zzn(this, zzfo), "onPeerConnected", zzfo);
        }

        public final void zzb(zzfo zzfo) {
            zza(new zzo(this, zzfo), "onPeerDisconnected", zzfo);
        }

        public final void onConnectedNodes(List<zzfo> list) {
            zza(new zzp(this, list), "onConnectedNodes", list);
        }

        public final void zza(zzah zzah) {
            zza(new zzq(this, zzah), "onConnectedCapabilityChanged", zzah);
        }

        public final void zza(zzl zzl) {
            zza(new zzr(this, zzl), "onNotificationReceived", zzl);
        }

        public final void zza(zzi zzi) {
            zza(new zzs(this, zzi), "onEntityUpdate", zzi);
        }

        public final void zza(zzaw zzaw) {
            zza(new zzt(this, zzaw), "onChannelEvent", zzaw);
        }

        private final boolean zza(Runnable runnable, String str, Object obj) {
            boolean z;
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", String.format("%s: %s %s", new Object[]{str, WearableListenerService.this.service.toString(), obj}));
            }
            int callingUid = Binder.getCallingUid();
            if (callingUid == this.zzam) {
                z = true;
            } else if (zzhp.zza(WearableListenerService.this).zze("com.google.android.wearable.app.cn") && UidVerifier.uidHasPackageName(WearableListenerService.this, callingUid, "com.google.android.wearable.app.cn")) {
                this.zzam = callingUid;
                z = true;
            } else if (UidVerifier.isGooglePlayServicesUid(WearableListenerService.this, callingUid)) {
                this.zzam = callingUid;
                z = true;
            } else {
                StringBuilder sb = new StringBuilder(57);
                sb.append("Caller is not GooglePlayServices; caller UID: ");
                sb.append(callingUid);
                Log.e("WearableLS", sb.toString());
                z = false;
            }
            if (!z) {
                return false;
            }
            synchronized (WearableListenerService.this.zzah) {
                if (WearableListenerService.this.zzai) {
                    return false;
                }
                WearableListenerService.this.zzad.post(runnable);
                return true;
            }
        }
    }

    public final class zzc extends Handler {
        private boolean started;
        private final zzb zzal;

        zzc(Looper looper) {
            super(looper);
            this.zzal = new zzb();
        }

        public final void dispatchMessage(Message message) {
            zzb();
            try {
                super.dispatchMessage(message);
            } finally {
                if (!hasMessages(0)) {
                    zzb("dispatch");
                }
            }
        }

        /* access modifiers changed from: package-private */
        public final void quit() {
            getLooper().quit();
            zzb("quit");
        }

        @SuppressLint({"UntrackedBindService"})
        private final synchronized void zzb() {
            if (!this.started) {
                if (Log.isLoggable("WearableLS", 2)) {
                    String valueOf = String.valueOf(WearableListenerService.this.service);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 13);
                    sb.append("bindService: ");
                    sb.append(valueOf);
                    Log.v("WearableLS", sb.toString());
                }
                WearableListenerService wearableListenerService = WearableListenerService.this;
                wearableListenerService.bindService(wearableListenerService.zzaf, this.zzal, 1);
                this.started = true;
            }
        }

        @SuppressLint({"UntrackedBindService"})
        private final synchronized void zzb(String str) {
            if (this.started) {
                if (Log.isLoggable("WearableLS", 2)) {
                    String valueOf = String.valueOf(WearableListenerService.this.service);
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 17 + String.valueOf(valueOf).length());
                    sb.append("unbindService: ");
                    sb.append(str);
                    sb.append(", ");
                    sb.append(valueOf);
                    Log.v("WearableLS", sb.toString());
                }
                try {
                    WearableListenerService.this.unbindService(this.zzal);
                } catch (RuntimeException e) {
                    Log.e("WearableLS", "Exception when unbinding from local service", e);
                }
                this.started = false;
            }
        }
    }

    public void onCreate() {
        super.onCreate();
        this.service = new ComponentName(this, getClass().getName());
        if (Log.isLoggable("WearableLS", 3)) {
            String valueOf = String.valueOf(this.service);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 10);
            sb.append("onCreate: ");
            sb.append(valueOf);
            Log.d("WearableLS", sb.toString());
        }
        this.zzad = new zzc(getLooper());
        Intent intent = new Intent(BIND_LISTENER_INTENT_ACTION);
        this.zzaf = intent;
        intent.setComponent(this.service);
        this.zzae = new zzd();
    }

    public void onDestroy() {
        if (Log.isLoggable("WearableLS", 3)) {
            String valueOf = String.valueOf(this.service);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 11);
            sb.append("onDestroy: ");
            sb.append(valueOf);
            Log.d("WearableLS", sb.toString());
        }
        synchronized (this.zzah) {
            this.zzai = true;
            zzc zzc2 = this.zzad;
            if (zzc2 != null) {
                zzc2.quit();
            } else {
                String valueOf2 = String.valueOf(this.service);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 111);
                sb2.append("onDestroy: mServiceHandler not set, did you override onCreate() but forget to call super.onCreate()? component=");
                sb2.append(valueOf2);
                throw new IllegalStateException(sb2.toString());
            }
        }
        super.onDestroy();
    }

    public final IBinder onBind(Intent intent) {
        if (BIND_LISTENER_INTENT_ACTION.equals(intent.getAction())) {
            return this.zzae;
        }
        return null;
    }

    public Looper getLooper() {
        if (this.zzag == null) {
            HandlerThread handlerThread = new HandlerThread("WearableListenerService");
            handlerThread.start();
            this.zzag = handlerThread.getLooper();
        }
        return this.zzag;
    }

    public void onDataChanged(DataEventBuffer dataEventBuffer) {
    }

    public void onMessageReceived(MessageEvent messageEvent) {
    }

    public void onPeerConnected(Node node) {
    }

    public void onPeerDisconnected(Node node) {
    }

    public void onConnectedNodes(List<Node> list) {
    }

    public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
    }

    public void onChannelOpened(Channel channel) {
    }

    public void onChannelClosed(Channel channel, int i, int i2) {
    }

    public void onInputClosed(Channel channel, int i, int i2) {
    }

    public void onOutputClosed(Channel channel, int i, int i2) {
    }

    public void onChannelOpened(ChannelClient.Channel channel) {
    }

    public void onChannelClosed(ChannelClient.Channel channel, int i, int i2) {
    }

    public void onInputClosed(ChannelClient.Channel channel, int i, int i2) {
    }

    public void onOutputClosed(ChannelClient.Channel channel, int i, int i2) {
    }

    public void onNotificationReceived(zzd zzd2) {
    }

    public void onEntityUpdate(zzb zzb2) {
    }
}
