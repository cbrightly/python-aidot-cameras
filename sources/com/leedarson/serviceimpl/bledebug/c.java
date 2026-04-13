package com.leedarson.serviceimpl.bledebug;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.amazonaws.kinesisvideo.producer.Time;
import com.leedarson.base.http.observer.l;
import com.leedarson.serviceimpl.bledebug.bean.ConnectBean;
import com.leedarson.serviceimpl.bledebug.ble.BleDebugController;
import com.leedarson.serviceimpl.bledebug.view.GattTestActivity;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import com.telink.ble.mesh.foundation.parameter.ScanParameters;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import meshsdk.MeshLog;
import meshsdk.cache.CacheHandler;
import meshsdk.ctrl.GroupCtrlAdapter;

/* compiled from: GattTestPresenter */
public class c extends com.leedarson.base.presenters.a<com.leedarson.serviceimpl.bledebug.view.a, GattTestActivity> implements b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private static final char[] f = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private BleDebugController g;
    /* access modifiers changed from: private */
    public ArrayList<ConnectBean> h;
    /* access modifiers changed from: private */
    public com.leedarson.serviceimpl.bledebug.strategy.a i;
    private Context j;
    private com.leedarson.serviceimpl.bledebug.bean.a k;
    private boolean l = false;
    /* access modifiers changed from: private */
    public Handler m;
    private int n = 0;
    ExecutorService o;
    Runnable p = new d();

    public c(com.leedarson.serviceimpl.bledebug.view.a view, GattTestActivity activity, ArrayList<ConnectBean> macList) {
        super(view, activity);
        this.h = macList;
        this.m = new Handler(Looper.getMainLooper());
    }

    public void x(Context context, com.leedarson.serviceimpl.bledebug.bean.a config) {
        if (!PatchProxy.proxy(new Object[]{context, config}, this, changeQuickRedirect, false, 6631, new Class[]{Context.class, com.leedarson.serviceimpl.bledebug.bean.a.class}, Void.TYPE).isSupported) {
            a.c("config:" + config.toString());
            this.k = config;
            this.j = context;
            BleDebugController bleDebugController = new BleDebugController();
            this.g = bleDebugController;
            bleDebugController.N(context);
            this.g.R();
            this.g.L(this);
            if (config.opInBackground) {
                this.o = l.i(1, "gatt-test-ble", 2);
            }
        }
    }

    public void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6632, new Class[0], Void.TYPE).isSupported) {
            this.l = false;
            Iterator<ConnectBean> it = this.h.iterator();
            while (it.hasNext()) {
                it.next().state = -1;
            }
            ((com.leedarson.serviceimpl.bledebug.view.a) m()).t();
            ScanParameters parameters = new ScanParameters();
            parameters.j(Time.NANOS_IN_A_MILLISECOND);
            this.g.P(parameters);
        }
    }

    public void g(AdvertisingDevice advertisingDevice) {
        if (!PatchProxy.proxy(new Object[]{advertisingDevice}, this, changeQuickRedirect, false, 6633, new Class[]{AdvertisingDevice.class}, Void.TYPE).isSupported) {
            MeshLog.v("GattTestPresenter.onDeviceFound  advertisingDevice=" + advertisingDevice.c);
            byte[] h2 = MeshUtils.h(advertisingDevice.f, true);
            Iterator<ConnectBean> it = this.h.iterator();
            while (it.hasNext()) {
                ConnectBean bean = it.next();
                if (bean.isMatch(advertisingDevice)) {
                    bean.advertisingDevice = advertisingDevice;
                    bean.bluetoothDevice = advertisingDevice.c;
                    bean.state = 0;
                    ((com.leedarson.serviceimpl.bledebug.view.a) m()).t();
                    if (u()) {
                        w();
                        return;
                    }
                    return;
                }
            }
        }
    }

    private void w() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6634, new Class[0], Void.TYPE).isSupported) {
            if (!this.l) {
                this.l = true;
                ((com.leedarson.serviceimpl.bledebug.view.a) m()).p();
                if (!this.k.containEnv(4)) {
                    a.a("停止扫描");
                    this.g.R();
                }
                this.m.postDelayed(new a(), CacheHandler.delayTime);
            }
        }
    }

    /* compiled from: GattTestPresenter */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6648, new Class[0], Void.TYPE).isSupported) {
                c.this.v();
            }
        }
    }

    public void c(String key) {
        if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6635, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.a(key + " 蓝牙已连接");
            d.d();
            B(key, 2);
        }
    }

    public void f(String key) {
        if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6636, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b(key + " 蓝牙连接失败");
            B(key, 3);
        }
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6638, new Class[0], Void.TYPE).isSupported) {
            y();
        }
    }

    private void B(String key, int state) {
        if (!PatchProxy.proxy(new Object[]{key, new Integer(state)}, this, changeQuickRedirect, false, 6639, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            Iterator<ConnectBean> it = this.h.iterator();
            while (it.hasNext()) {
                ConnectBean bean = it.next();
                if (bean.mac.equals(key)) {
                    bean.state = state;
                    ((com.leedarson.serviceimpl.bledebug.view.a) m()).t();
                }
            }
        }
    }

    public void d(String key) {
        if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6640, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.a(key + " 蓝牙已连接并搜索到服务");
        }
    }

    public void onDisconnect(String key) {
        if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6642, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b(key + " 蓝牙已断开");
            Iterator<ConnectBean> it = this.h.iterator();
            while (it.hasNext()) {
                ConnectBean bean = it.next();
                if (bean.mac.equals(key)) {
                    bean.state = -1;
                    ((com.leedarson.serviceimpl.bledebug.view.a) m()).t();
                }
            }
        }
    }

    private boolean u() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6643, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Iterator<ConnectBean> it = this.h.iterator();
        while (it.hasNext()) {
            if (it.next().state < 0) {
                return false;
            }
        }
        return true;
    }

    public void v() {
        ExecutorService executorService;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6645, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.bledebug.bean.a aVar = this.k;
            if (aVar != null) {
                if (aVar.containEnv(1)) {
                    this.i = new com.leedarson.serviceimpl.bledebug.strategy.b(this.j, this, this, this.k);
                } else if (this.k.containEnv(2)) {
                    this.i = new com.leedarson.serviceimpl.bledebug.strategy.c(this.j, this, this, this.k);
                } else if (this.k.containEnv(4)) {
                    this.i = new com.leedarson.serviceimpl.bledebug.strategy.b(this.j, this, this, this.k);
                } else {
                    this.i = new com.leedarson.serviceimpl.bledebug.strategy.b(this.j, this, this, this.k);
                }
            }
            d.e(this.h.size());
            if (!this.k.opInBackground || (executorService = this.o) == null) {
                this.i.a(this.h);
            } else {
                executorService.submit(new b());
            }
        }
    }

    /* compiled from: GattTestPresenter */
    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6649, new Class[0], Void.TYPE).isSupported) {
                c.this.i.a(c.this.h);
            }
        }
    }

    public void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6646, new Class[0], Void.TYPE).isSupported) {
            this.m.removeCallbacksAndMessages((Object) null);
            this.g.Q();
            com.leedarson.serviceimpl.bledebug.strategy.a aVar = this.i;
            if (aVar != null) {
                aVar.g();
            }
            ExecutorService executorService = this.o;
            if (executorService != null) {
                executorService.shutdown();
                this.o = null;
            }
        }
    }

    public void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6647, new Class[0], Void.TYPE).isSupported) {
            this.m.removeCallbacks(this.p);
            int i2 = this.n + 1;
            this.n = i2;
            if (i2 == this.k.testCount) {
                a.a("##### 所有自动化流程已结束 #####");
                return;
            }
            com.leedarson.serviceimpl.bledebug.strategy.a aVar = this.i;
            if (aVar != null) {
                aVar.g();
            }
            a.c("开启第" + this.n + "轮自动化，等待3秒开始扫描蓝牙");
            ((com.leedarson.serviceimpl.bledebug.view.a) m()).C(d.b(), d.c(), this.n);
            if (this.k.containEnv(4)) {
                a.a("重启扫描");
                this.g.R();
            }
            this.m.postDelayed(new C0131c(), GroupCtrlAdapter.RETRY_TIMEOUT);
        }
    }

    /* renamed from: com.leedarson.serviceimpl.bledebug.c$c  reason: collision with other inner class name */
    /* compiled from: GattTestPresenter */
    public class C0131c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0131c() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6650, new Class[0], Void.TYPE).isSupported) {
                c.this.m.postDelayed(c.this.p, 60000);
                c.this.A();
            }
        }
    }

    /* compiled from: GattTestPresenter */
    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6651, new Class[0], Void.TYPE).isSupported) {
                c.this.y();
            }
        }
    }
}
