package com.leedarson.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.lang.ref.WeakReference;

public class VolumeChangeObserver {
    public static ChangeQuickRedirect changeQuickRedirect;
    private a a;
    private VolumeBroadcastReceiver b;
    private Context c;
    private AudioManager d;
    private boolean e = false;

    public interface a {
        void J0(int i);

        void e1(boolean z);
    }

    public VolumeChangeObserver(Context context) {
        this.c = context;
        this.d = (AudioManager) context.getApplicationContext().getSystemService("audio");
    }

    public int a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11561, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        AudioManager audioManager = this.d;
        if (audioManager != null) {
            return audioManager.getStreamVolume(3);
        }
        return -1;
    }

    public a b() {
        return this.a;
    }

    public void d(a volumeChangeListener) {
        this.a = volumeChangeListener;
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11563, new Class[0], Void.TYPE).isSupported) {
            this.b = new VolumeBroadcastReceiver(this);
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.media.VOLUME_CHANGED_ACTION");
            filter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
            this.c.registerReceiver(this.b, filter);
            this.e = true;
        }
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11564, new Class[0], Void.TYPE).isSupported) {
            if (this.e) {
                try {
                    this.c.unregisterReceiver(this.b);
                    this.a = null;
                    this.e = false;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static class VolumeBroadcastReceiver extends BroadcastReceiver {
        public static ChangeQuickRedirect changeQuickRedirect;
        private WeakReference<VolumeChangeObserver> a;

        public VolumeBroadcastReceiver(VolumeChangeObserver volumeChangeObserver) {
            this.a = new WeakReference<>(volumeChangeObserver);
        }

        public void onReceive(Context context, Intent intent) {
            a listener;
            int volume;
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            Class[] clsArr = {Context.class, Intent.class};
            if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 11565, clsArr, Void.TYPE).isSupported) {
                if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction()) && intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 3) {
                    VolumeChangeObserver observer = (VolumeChangeObserver) this.a.get();
                    if (observer != null && (listener = observer.b()) != null && (volume = observer.a()) >= 0) {
                        listener.J0(volume);
                    }
                } else if ("android.media.STREAM_MUTE_CHANGED_ACTION".equals(intent.getAction()) && intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 3) {
                    VolumeChangeObserver observer2 = (VolumeChangeObserver) this.a.get();
                    a listener2 = null;
                    if (observer2 != null) {
                        listener2 = observer2.b();
                    }
                    AudioManager audioManager = (AudioManager) context.getSystemService("audio");
                    if (Build.VERSION.SDK_INT >= 23) {
                        boolean muteFlag = audioManager.isStreamMute(3);
                        if (listener2 != null) {
                            listener2.e1(muteFlag);
                        }
                    }
                }
            }
        }
    }
}
