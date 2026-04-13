package com.leedarson.serviceimpl.bledebug.strategy;

import android.content.Context;
import android.os.Handler;
import com.leedarson.serviceimpl.bledebug.bean.ConnectBean;
import com.leedarson.serviceimpl.bledebug.ble.BleDebugController;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import java.util.List;

/* compiled from: PartSerialConn */
public class c extends a {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public com.leedarson.serviceimpl.bledebug.b g;
    /* access modifiers changed from: private */
    public int h = -1;
    /* access modifiers changed from: private */
    public List<ConnectBean> i;
    private Handler j;
    /* access modifiers changed from: private */
    public com.leedarson.serviceimpl.bledebug.b k = new a();

    static /* synthetic */ void j(c x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 6850, new Class[]{c.class}, Void.TYPE).isSupported) {
            x0.n();
        }
    }

    public c(Context context, com.leedarson.serviceimpl.bledebug.c presenter, com.leedarson.serviceimpl.bledebug.b globalListener, com.leedarson.serviceimpl.bledebug.bean.a config) {
        super(context, presenter, config);
        this.g = globalListener;
        this.j = new Handler();
    }

    public void a(List<ConnectBean> list) {
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 6848, new Class[]{List.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.bledebug.a.c("开启半串行");
            this.i = list;
            n();
        }
    }

    /* compiled from: PartSerialConn */
    public class a implements com.leedarson.serviceimpl.bledebug.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void g(AdvertisingDevice device) {
        }

        public void c(String key) {
            if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6851, new Class[]{String.class}, Void.TYPE).isSupported) {
                c.this.e(key);
                if (c.this.g != null) {
                    c.this.g.c(key);
                }
                c cVar = c.this;
                if (cVar.d.closeWhenConnected && cVar.a.containsKey(key)) {
                    c.this.h(c.this.a.get(key));
                    if (c.this.g != null) {
                        c.this.g.onDisconnect(key);
                    }
                }
                c.j(c.this);
            }
        }

        public void d(String key) {
        }

        public void f(String key) {
            if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6852, new Class[]{String.class}, Void.TYPE).isSupported) {
                c.this.c(key);
                if (c.this.g != null) {
                    c.this.g.f(key);
                }
                c.j(c.this);
            }
        }

        public void onDisconnect(String key) {
        }

        public void e() {
        }
    }

    private void n() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6849, new Class[0], Void.TYPE).isSupported) {
            int i2 = this.h + 1;
            this.h = i2;
            if (i2 == this.i.size()) {
                com.leedarson.serviceimpl.bledebug.b bVar = this.g;
                if (bVar != null) {
                    bVar.e();
                    return;
                }
                return;
            }
            this.j.postDelayed(new b(), (long) this.d.connectInterval);
        }
    }

    /* compiled from: PartSerialConn */
    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6854, new Class[0], Void.TYPE).isSupported) {
                ConnectBean bean = (ConnectBean) c.this.i.get(c.this.h);
                BleDebugController controller = new BleDebugController();
                controller.x = bean.mac;
                controller.N(c.this.b);
                controller.L(c.this.k);
                c.this.a.put(bean.mac, controller);
                com.leedarson.serviceimpl.bledebug.a.a(bean.mac + "发起连接");
                ((com.leedarson.serviceimpl.bledebug.view.a) c.this.c.m()).t();
                bean.state = 1;
                controller.p(bean.bluetoothDevice);
            }
        }
    }
}
