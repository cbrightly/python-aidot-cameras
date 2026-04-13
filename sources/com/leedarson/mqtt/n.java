package com.leedarson.mqtt;

import com.leedarson.base.http.observer.l;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.reactivex.disposables.b;
import io.reactivex.e;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* compiled from: MqttWatchDogService */
public class n {
    public static ChangeQuickRedirect changeQuickRedirect;
    b a;
    ArrayList<a> b = new ArrayList<>();

    /* compiled from: MqttWatchDogService */
    public interface a {
        void a(long j);
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1500, new Class[0], Void.TYPE).isSupported) {
            e();
            com.leedarson.mqtt.logs.b.b("Mqtt 看门狗开启 ....");
            this.a = e.v(500, TimeUnit.MILLISECONDS, l.i).I(new j(this), k.c);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public /* synthetic */ void c(Long l) {
        if (!PatchProxy.proxy(new Object[]{l}, this, changeQuickRedirect, false, 1507, new Class[]{Long.class}, Void.TYPE).isSupported) {
            int i = 0;
            while (i < this.b.size()) {
                try {
                    this.b.get(i).a(System.currentTimeMillis());
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                    com.leedarson.mqtt.logs.b.a("watchDog 发生异常 onTimeToCheck -->  e=" + e.toString());
                    return;
                }
            }
        }
    }

    static /* synthetic */ void d(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, (Object) null, changeQuickRedirect, true, 1506, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            throwable.printStackTrace();
            com.leedarson.mqtt.logs.b.a("watchDog 发生异常 onTimeToCheck1111111 throwable=" + throwable.toString());
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1501, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.b("Mqtt 看门狗关闭 ....");
            e();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void e() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 1502(0x5de, float:2.105E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.a
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.a
            r1.dispose()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.mqtt.n.e():void");
    }

    public void a(a _item) {
        if (!PatchProxy.proxy(new Object[]{_item}, this, changeQuickRedirect, false, 1503, new Class[]{a.class}, Void.TYPE).isSupported) {
            this.b.add(_item);
        }
    }
}
