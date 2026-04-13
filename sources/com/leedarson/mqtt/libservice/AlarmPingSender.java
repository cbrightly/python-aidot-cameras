package com.leedarson.mqtt.libservice;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.util.Timer;
import java.util.TimerTask;
import meshsdk.cache.CacheHandler;
import org.eclipse.paho.client.mqttv3.b;
import org.eclipse.paho.client.mqttv3.f;
import org.eclipse.paho.client.mqttv3.n;

public class AlarmPingSender implements n {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public org.eclipse.paho.client.mqttv3.internal.a a;
    /* access modifiers changed from: private */
    public MqttService b;
    private BroadcastReceiver c;
    /* access modifiers changed from: private */
    public AlarmPingSender d;
    private PendingIntent e;
    private volatile boolean f = false;
    private Timer g;

    public AlarmPingSender(MqttService service) {
        Timer timer = null;
        this.g = timer;
        if (service != null) {
            this.b = service;
            this.d = this;
            if (timer != null) {
                timer.cancel();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Neither service nor client can be null.");
    }

    public void a(org.eclipse.paho.client.mqttv3.internal.a comms) {
        if (!PatchProxy.proxy(new Object[]{comms}, this, changeQuickRedirect, false, 1519, new Class[]{org.eclipse.paho.client.mqttv3.internal.a.class}, Void.TYPE).isSupported) {
            this.a = comms;
            this.c = new AlarmReceiver();
        }
    }

    public void start() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1520, new Class[0], Void.TYPE).isSupported) {
            String action = "MqttService.pingSender." + this.a.t().f0();
            Log.d("AlarmPingSender", "Register alarmreceiver to MqttService" + action);
            this.b.registerReceiver(this.c, new IntentFilter(action));
            if (Build.VERSION.SDK_INT >= 31) {
                MqttService mqttService = this.b;
                Intent intent = new Intent(action);
                PushAutoTrackHelper.hookIntentGetBroadcast(mqttService, 0, intent, 67108864);
                PendingIntent broadcast = PendingIntent.getBroadcast(mqttService, 0, intent, 67108864);
                PushAutoTrackHelper.hookPendingIntentGetBroadcast(broadcast, mqttService, 0, intent, 67108864);
                this.e = broadcast;
            } else {
                MqttService mqttService2 = this.b;
                Intent intent2 = new Intent(action);
                PushAutoTrackHelper.hookIntentGetBroadcast(mqttService2, 0, intent2, 0);
                PendingIntent broadcast2 = PendingIntent.getBroadcast(mqttService2, 0, intent2, 0);
                PushAutoTrackHelper.hookPendingIntentGetBroadcast(broadcast2, mqttService2, 0, intent2, 0);
                this.e = broadcast2;
            }
            b(this.a.u());
            f();
            this.f = true;
        }
    }

    private void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1521, new Class[0], Void.TYPE).isSupported) {
            AlarmManager alarmManager = (AlarmManager) this.b.getSystemService(NotificationCompat.CATEGORY_ALARM);
            if (Build.VERSION.SDK_INT >= 31 && !alarmManager.canScheduleExactAlarms()) {
                Timer timer = this.g;
                if (timer != null) {
                    timer.cancel();
                }
                this.g = new Timer("native-mqtt-ping");
                Log.e("AlarmPingSender", "Alarm scheule Permision 创建自定义定时器  定时=" + this.a.u());
                this.g.schedule(new a(), CacheHandler.delayTime, 15000);
            }
        }
    }

    public class a extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1524, new Class[0], Void.TYPE).isSupported) {
                Log.e("AlarmPingSender", "Alarm scheule Permision 自定义广播器开始执行.....");
                AlarmPingSender.this.b.sendBroadcast(new Intent("MqttService.pingSender." + AlarmPingSender.this.a.t().f0()));
            }
        }
    }

    public void stop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1522, new Class[0], Void.TYPE).isSupported) {
            Log.d("AlarmPingSender", "Unregister alarmreceiver to MqttService" + this.a.t().f0());
            if (this.f) {
                if (this.e != null) {
                    ((AlarmManager) this.b.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(this.e);
                }
                Timer timer = this.g;
                if (timer != null) {
                    timer.cancel();
                }
                this.f = false;
                try {
                    this.b.unregisterReceiver(this.c);
                } catch (IllegalArgumentException e2) {
                }
            }
        }
    }

    public void b(long delayInMilliseconds) {
        if (!PatchProxy.proxy(new Object[]{new Long(delayInMilliseconds)}, this, changeQuickRedirect, false, 1523, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            long nextAlarmInMilliseconds = System.currentTimeMillis() + delayInMilliseconds;
            Log.d("AlarmPingSender", "Schedule next alarm at " + nextAlarmInMilliseconds);
            AlarmManager alarmManager = (AlarmManager) this.b.getSystemService(NotificationCompat.CATEGORY_ALARM);
            int i = Build.VERSION.SDK_INT;
            if (i >= 23) {
                Log.d("AlarmPingSender", "Alarm scheule using setExactAndAllowWhileIdle, next: " + delayInMilliseconds);
                if (i >= 31) {
                    boolean canAlarmPermision = alarmManager.canScheduleExactAlarms();
                    new Intent("android.settings.REQUEST_SCHEDULE_EXACT_ALARM", Uri.parse("package:" + BaseApplication.b().getPackageName()));
                    try {
                        if (c.h().k() != null) {
                            if (canAlarmPermision) {
                                Log.e("AlarmPingSender", "Alarm scheule Permision 用户已授权");
                                alarmManager.setExactAndAllowWhileIdle(0, nextAlarmInMilliseconds, this.e);
                            } else {
                                Log.e("AlarmPingSender", "Alarm scheule Permision 采用自定义定时器");
                            }
                        }
                    } catch (Exception e2) {
                        Log.e("AlarmPingSender", "Alarm scheule Permision error=" + e2.toString());
                    }
                    Log.d("AlarmPingSender", "Alarm scheule Permision check--> " + canAlarmPermision + " next:" + delayInMilliseconds);
                }
            } else if (i >= 19) {
                Log.d("AlarmPingSender", "Alarm scheule using setExact, delay: " + delayInMilliseconds);
                alarmManager.setExact(0, nextAlarmInMilliseconds, this.e);
            } else {
                alarmManager.set(0, nextAlarmInMilliseconds, this.e);
            }
        }
    }

    public class AlarmReceiver extends BroadcastReceiver {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public PowerManager.WakeLock a;
        /* access modifiers changed from: private */
        public final String b;

        AlarmReceiver() {
            this.b = "MqttService.client." + AlarmPingSender.this.d.a.t().f0();
        }

        @SuppressLint({"Wakelock"})
        public void onReceive(Context context, Intent intent) {
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 1525, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
                Log.d("AlarmPingSender", "Sending Ping at:" + System.currentTimeMillis());
                PowerManager.WakeLock newWakeLock = ((PowerManager) AlarmPingSender.this.b.getSystemService("power")).newWakeLock(1, this.b);
                this.a = newWakeLock;
                newWakeLock.acquire();
                if (AlarmPingSender.this.a.n(new a()) == null && this.a.isHeld()) {
                    this.a.release();
                }
            }
        }

        public class a implements b {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void onSuccess(f fVar) {
                if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 1526, new Class[]{f.class}, Void.TYPE).isSupported) {
                    Log.d("AlarmPingSender", "Success. Release lock(" + AlarmReceiver.this.b + "):" + System.currentTimeMillis());
                    AlarmReceiver.this.a.release();
                }
            }

            public void onFailure(f fVar, Throwable th) {
                Class[] clsArr = {f.class, Throwable.class};
                if (!PatchProxy.proxy(new Object[]{fVar, th}, this, changeQuickRedirect, false, 1527, clsArr, Void.TYPE).isSupported) {
                    Log.d("AlarmPingSender", "Failure. Release lock(" + AlarmReceiver.this.b + "):" + System.currentTimeMillis());
                    AlarmReceiver.this.a.release();
                }
            }
        }
    }
}
