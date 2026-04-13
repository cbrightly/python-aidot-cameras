package com.leedarson.serviceimpl.blec075;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.leedarson.base.utils.c;
import com.leedarson.base.utils.w;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import timber.log.a;

/* compiled from: LdsBleMeshWakeUpDeviceAdvertise */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;
    int a = 0;
    private int b = 10000;
    private BluetoothLeAdvertiser c;
    private b d;
    public io.reactivex.processors.b<Pair<Integer, String>> e = io.reactivex.processors.b.Y();
    private final int f = 0;
    private final int g = -1;
    private final int h = -2;
    private final int i = -3;
    private final int j = -4;
    private Handler k = new a(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public boolean l;

    /* compiled from: LdsBleMeshWakeUpDeviceAdvertise */
    public class b extends AdvertiseCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    static /* synthetic */ void c(g x0, String x1) {
        Class[] clsArr = {g.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6427, clsArr, Void.TYPE).isSupported) {
            x0.b(x1);
        }
    }

    /* compiled from: LdsBleMeshWakeUpDeviceAdvertise */
    public class a extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        a(Looper looper) {
            super(looper);
        }

        public void handleMessage(@NonNull Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6429, new Class[]{Message.class}, Void.TYPE).isSupported) {
                super.handleMessage(msg);
                g.c(g.this, "10s 唤醒结束，stop");
                g.this.e();
                boolean unused = g.this.l = false;
            }
        }
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6422, new Class[0], Void.TYPE).isSupported) {
            b("正在结束蓝牙广播...");
            if (this.c == null) {
                return;
            }
            if (!w.R() || ContextCompat.checkSelfPermission(c.h().c(), "android.permission.BLUETOOTH_ADVERTISE") == 0) {
                b("正在关闭蓝牙唤醒广播...");
                this.c.stopAdvertising(this.d);
                return;
            }
            a("android12以上广播权限未授权...不用关闭");
        }
    }

    private void b(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6425, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("blewakeup");
            g2.h("auto_connect_mesh Ble设备唤醒日志：" + msg, new Object[0]);
        }
    }

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6426, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("blewakeup");
            g2.c("auto_connect_mesh Ble设备唤醒日志：" + msg, new Object[0]);
        }
    }
}
