package com.leedarson.serviceimpl.bledebug.strategy;

import android.content.Context;
import com.leedarson.serviceimpl.bledebug.bean.ConnectBean;
import com.leedarson.serviceimpl.bledebug.ble.BleDebugController;
import com.leedarson.serviceimpl.bledebug.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import java.util.List;

/* compiled from: DefaultConn */
public class b extends a {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public com.leedarson.serviceimpl.bledebug.b g;
    int h;
    int i;
    private com.leedarson.serviceimpl.bledebug.b j = new a();

    public b(Context context, c presenter, com.leedarson.serviceimpl.bledebug.b globalListener, com.leedarson.serviceimpl.bledebug.bean.a config) {
        super(context, presenter, config);
        this.g = globalListener;
    }

    public void a(List<ConnectBean> macList) {
        if (!PatchProxy.proxy(new Object[]{macList}, this, changeQuickRedirect, false, 6840, new Class[]{List.class}, Void.TYPE).isSupported) {
            this.h = 0;
            this.i = macList.size();
            for (ConnectBean bean : macList) {
                BleDebugController controller = new BleDebugController();
                controller.x = bean.mac;
                controller.N(this.b);
                controller.L(this.j);
                controller.M(this.d.reconnectParams);
                this.a.put(bean.mac, controller);
                com.leedarson.serviceimpl.bledebug.a.a(bean.mac + "发起连接");
                ((com.leedarson.serviceimpl.bledebug.view.a) this.c.m()).t();
                bean.state = 1;
                controller.p(bean.bluetoothDevice);
            }
        }
    }

    /* compiled from: DefaultConn */
    public class a implements com.leedarson.serviceimpl.bledebug.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void g(AdvertisingDevice device) {
        }

        public void c(String key) {
            if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6842, new Class[]{String.class}, Void.TYPE).isSupported) {
                b.this.e(key);
                if (b.this.g != null) {
                    b.this.g.c(key);
                }
                b bVar = b.this;
                bVar.h++;
                bVar.j();
            }
        }

        public void d(String key) {
            if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6843, new Class[]{String.class}, Void.TYPE).isSupported) {
                if (b.this.g != null) {
                    b.this.g.d(key);
                }
            }
        }

        public void f(String key) {
            if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6846, new Class[]{String.class}, Void.TYPE).isSupported) {
                b.this.c(key);
                if (b.this.g != null) {
                    b.this.g.f(key);
                }
                b bVar = b.this;
                bVar.h++;
                bVar.j();
            }
        }

        public void onDisconnect(String key) {
            if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6847, new Class[]{String.class}, Void.TYPE).isSupported) {
                if (b.this.g != null) {
                    b.this.g.onDisconnect(key);
                }
            }
        }

        public void e() {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void j() {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0030 }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x0030 }
            r4 = 0
            r5 = 6841(0x1ab9, float:9.586E-42)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x0030 }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x0030 }
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0030 }
            boolean r0 = r0.isSupported     // Catch:{ all -> 0x0030 }
            if (r0 == 0) goto L_0x0018
            monitor-exit(r8)
            return
        L_0x0018:
            r0 = r8
            int r1 = r0.h     // Catch:{ all -> 0x0030 }
            int r2 = r0.i     // Catch:{ all -> 0x0030 }
            if (r1 != r2) goto L_0x002e
            java.lang.String r1 = "#### 流程结束 ####"
            com.leedarson.serviceimpl.bledebug.a.b(r1)     // Catch:{ all -> 0x0030 }
            r0.b()     // Catch:{ all -> 0x0030 }
            com.leedarson.serviceimpl.bledebug.b r1 = r0.g     // Catch:{ all -> 0x0030 }
            if (r1 == 0) goto L_0x002e
            r1.e()     // Catch:{ all -> 0x0030 }
        L_0x002e:
            monitor-exit(r8)
            return
        L_0x0030:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.bledebug.strategy.b.j():void");
    }
}
