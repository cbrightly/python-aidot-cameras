package com.leedarson.newui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.leedarson.bean.Constants;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;

public class HeadsetPlugReceiver extends BroadcastReceiver {
    public static ChangeQuickRedirect changeQuickRedirect;
    private a a;

    public interface a {
        void a(boolean z);

        void b();

        void c(boolean z);
    }

    public HeadsetPlugReceiver(a headsetPlugListener) {
        this.a = headsetPlugListener;
    }

    public void onReceive(Context context, Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 4408, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
            String action = intent.getAction();
            Log.d("HeadsetPlugReceiver", action);
            if ("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
                int state = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                Log.d("HeadsetPlugReceiver", "onReceive: " + state);
                if (2 == state) {
                    this.a.a(true);
                }
                if (state == 0) {
                    this.a.a(false);
                }
            } else if ("android.intent.action.HEADSET_PLUG".equals(action)) {
                if (!intent.hasExtra(Constants.ACTION_STATE)) {
                    return;
                }
                if (intent.getIntExtra(Constants.ACTION_STATE, 0) == 0) {
                    this.a.c(false);
                } else if (intent.getIntExtra(Constants.ACTION_STATE, 0) == 1) {
                    this.a.c(true);
                }
            } else if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action) && intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0) == 10) {
                this.a.b();
            }
        }
    }
}
