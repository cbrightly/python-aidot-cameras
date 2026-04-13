package com.leedarson.serviceimpl.blec075;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.leedarson.base.utils.c;
import com.leedarson.base.utils.w;
import com.leedarson.serviceimpl.blec075.util.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import timber.log.a;

/* compiled from: LdsBleCombWakeUpDeviceAdvertise */
public class f {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a;
    int b = 0;
    private int c = 10000;
    private AdvertiseSettings d;
    private AdvertiseData e;
    private BluetoothAdapter f;
    private BluetoothLeAdvertiser g;
    private b h;
    public io.reactivex.processors.b<Pair<Integer, String>> i = io.reactivex.processors.b.Y();
    private e j;
    private final int k = -1;
    private final int l = -2;
    private final int m = -3;
    private final int n = -4;
    private Handler o = new a(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public boolean p;

    static /* synthetic */ void c(f x0, String x1) {
        Class[] clsArr = {f.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6415, clsArr, Void.TYPE).isSupported) {
            x0.b(x1);
        }
    }

    static /* synthetic */ void e(f x0, String x1) {
        Class[] clsArr = {f.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6416, clsArr, Void.TYPE).isSupported) {
            x0.a(x1);
        }
    }

    /* compiled from: LdsBleCombWakeUpDeviceAdvertise */
    public class a extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        a(Looper looper) {
            super(looper);
        }

        public void handleMessage(@NonNull Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6417, new Class[]{Message.class}, Void.TYPE).isSupported) {
                super.handleMessage(msg);
                f.c(f.this, "10s 唤醒结束，stop");
                f.this.i();
                boolean unused = f.this.p = false;
            }
        }
    }

    public void h(String mac, String cmdData, int cmd) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{mac, cmdData, new Integer(cmd)}, this, changeQuickRedirect, false, 6410, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.p) {
                b("isAdvertising...return ");
                return;
            }
            if (TextUtils.isEmpty(mac)) {
                this.a = "FFFFFFFFFFFF";
            } else {
                this.a = mac;
            }
            this.b = cmd;
            this.d = new AdvertiseSettings.Builder().setAdvertiseMode(2).setTxPowerLevel(3).setTimeout(this.c).setConnectable(false).build();
            byte[] data = g(1, this.b, this.a, cmdData);
            String serviceData = w.b(data);
            String serData = h.m(serviceData);
            b("serviceData:" + serviceData + ",serData:" + serData);
            this.e = new AdvertiseData.Builder().setIncludeDeviceName(false).setIncludeTxPowerLevel(true).addManufacturerData(65520, data).build();
            this.h = new b(this, (a) null);
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            this.f = defaultAdapter;
            this.g = defaultAdapter.getBluetoothLeAdvertiser();
            this.j = new e(c.h().c());
            if (!this.f.isEnabled()) {
                a("用户的蓝牙服务未开启-无法广播");
                this.i.onNext(new Pair(-1, "用户的蓝牙服务未开启-无法广播"));
            } else if (this.g == null) {
                a("用户手机当前设备不支持广播");
                this.i.onNext(new Pair(-2, "用户手机当前设备不支持广播"));
            } else if (!w.R() || ContextCompat.checkSelfPermission(c.h().c(), "android.permission.BLUETOOTH_ADVERTISE") == 0) {
                b("开启蓝牙唤醒低功耗....");
                this.g.startAdvertising(this.d, this.e, this.h);
                this.p = true;
                this.o.sendEmptyMessageDelayed(1, KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
            } else {
                a("android12以上手机未开启蓝牙广播授权权限(提示用户需要蓝牙广播服务)...");
                String[] strArr = {"android.permission.BLUETOOTH_ADVERTISE"};
                this.i.onNext(new Pair(-3, "手机未开启蓝牙广播授权权限(提示用户需要蓝牙广播服务)..."));
            }
        }
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6411, new Class[0], Void.TYPE).isSupported) {
            b("正在结束蓝牙广播...");
            if (this.g == null) {
                return;
            }
            if (!w.R() || ContextCompat.checkSelfPermission(c.h().c(), "android.permission.BLUETOOTH_ADVERTISE") == 0) {
                b("正在关闭蓝牙唤醒广播...");
                this.g.stopAdvertising(this.h);
                return;
            }
            a("android12以上广播权限未授权...不用关闭");
        }
    }

    private byte[] g(int version, int cmd, String mac, String str) {
        Class<String> cls = String.class;
        int i2 = version;
        Object[] objArr = {new Integer(version), new Integer(cmd), mac, str};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6412, new Class[]{cls2, cls2, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        String str2 = str;
        byte[] params = new byte[10];
        byte[] vidBytes = h.j(4456, 2);
        params[0] = vidBytes[0];
        params[1] = vidBytes[1];
        byte[] macBytes = h.n(mac);
        System.arraycopy(macBytes, 0, params, 2, macBytes.length);
        params[8] = (byte) version;
        params[9] = (byte) cmd;
        return params;
    }

    /* compiled from: LdsBleCombWakeUpDeviceAdvertise */
    public class b extends AdvertiseCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        private b() {
        }

        /* synthetic */ b(f x0, a x1) {
            this();
        }

        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            if (!PatchProxy.proxy(new Object[]{settingsInEffect}, this, changeQuickRedirect, false, 6418, new Class[]{AdvertiseSettings.class}, Void.TYPE).isSupported) {
                super.onStartSuccess(settingsInEffect);
                f.c(f.this, "BLE 唤醒广播开启成功");
                f.this.i.onNext(new Pair(0, "BLE 唤醒广播开启成功"));
            }
        }

        public void onStartFailure(int errorCode) {
            Object[] objArr = {new Integer(errorCode)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6419, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                super.onStartFailure(errorCode);
                f fVar = f.this;
                f.e(fVar, "BLE 唤醒广播开启失败 errorCode=" + errorCode);
                io.reactivex.processors.b<Pair<Integer, String>> bVar = f.this.i;
                bVar.onNext(new Pair(-4, "BLE 唤醒广播开启失败 errorCode=" + errorCode));
            }
        }
    }

    public io.reactivex.processors.b<Pair<Integer, String>> f() {
        return this.i;
    }

    private void b(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6413, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("LdsBleCombWakeUpDeviceAdvertise-BleC075ServiceImpl-Connection");
            g2.h("Ble设备唤醒日志：" + msg, new Object[0]);
        }
    }

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6414, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("LdsBleCombWakeUpDeviceAdvertise-BleC075ServiceImpl-Connection");
            g2.c("Ble设备唤醒日志：" + msg, new Object[0]);
        }
    }
}
