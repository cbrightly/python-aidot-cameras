package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessengerUtils {
    /* access modifiers changed from: private */
    public static ConcurrentHashMap<String, a> a = new ConcurrentHashMap<>();
    private static Map<String, ?> b = new HashMap();

    public interface a {
        void a(Bundle bundle);
    }

    public static class ServerService extends Service {
        /* access modifiers changed from: private */
        public final ConcurrentHashMap<Integer, Messenger> c = new ConcurrentHashMap<>();
        @SuppressLint({"HandlerLeak"})
        private final Handler d;
        private final Messenger f;

        public ServerService() {
            a aVar = new a();
            this.d = aVar;
            this.f = new Messenger(aVar);
        }

        public class a extends Handler {
            a() {
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        ServerService.this.c.put(Integer.valueOf(msg.arg1), msg.replyTo);
                        return;
                    case 1:
                        ServerService.this.c.remove(Integer.valueOf(msg.arg1));
                        return;
                    case 2:
                        ServerService.this.e(msg);
                        ServerService.this.d(msg);
                        return;
                    default:
                        super.handleMessage(msg);
                        return;
                }
            }
        }

        @Nullable
        public IBinder onBind(Intent intent) {
            return this.f.getBinder();
        }

        public int onStartCommand(Intent intent, int i, int i2) {
            Bundle extras;
            PushAutoTrackHelper.onServiceStartCommand(this, intent, i, i2);
            if (!(intent == null || (extras = intent.getExtras()) == null)) {
                Message msg = Message.obtain(this.d, 2);
                msg.replyTo = this.f;
                msg.setData(extras);
                e(msg);
                d(msg);
            }
            return 2;
        }

        /* access modifiers changed from: private */
        public void e(Message msg) {
            for (Messenger client : this.c.values()) {
                if (client != null) {
                    try {
                        client.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        public void d(Message msg) {
            String key;
            a callback;
            Bundle data = msg.getData();
            if (data != null && (key = data.getString("MESSENGER_UTILS")) != null && (callback = (a) MessengerUtils.a.get(key)) != null) {
                callback.a(data);
            }
        }
    }
}
