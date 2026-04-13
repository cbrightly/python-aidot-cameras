package com.telink;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.bluetooth.event.b;
import com.telink.bluetooth.event.e;
import com.telink.bluetooth.event.f;
import com.telink.bluetooth.event.g;
import com.telink.bluetooth.light.DeviceInfo;
import com.telink.bluetooth.light.ErrorReportInfo;
import com.telink.bluetooth.light.NotificationInfo;
import com.telink.util.c;
import com.telink.util.d;

/* compiled from: TelinkApplication */
public class a extends Application {
    private static a c;
    public static ChangeQuickRedirect changeQuickRedirect;
    protected final d<String> d = new d<>();
    protected Context f;
    protected boolean q;
    protected final ServiceConnection x = new C0213a();
    protected DeviceInfo y;

    /* renamed from: com.telink.a$a  reason: collision with other inner class name */
    /* compiled from: TelinkApplication */
    public class C0213a implements ServiceConnection {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0213a() {
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            Class[] clsArr = {ComponentName.class, IBinder.class};
            if (!PatchProxy.proxy(new Object[]{name, service}, this, changeQuickRedirect, false, 12093, clsArr, Void.TYPE).isSupported) {
                a.this.k(name, service);
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            if (!PatchProxy.proxy(new Object[]{name}, this, changeQuickRedirect, false, 12094, new Class[]{ComponentName.class}, Void.TYPE).isSupported) {
                a.this.l(name);
            }
        }
    }

    public static a b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 12065, new Class[0], a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        if (c == null) {
            c = new a();
        }
        return c;
    }

    public void onCreate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12066, new Class[0], Void.TYPE).isSupported) {
            c = this;
            this.f = this;
            super.onCreate();
            com.telink.bluetooth.d.a("TelinkApplication Created.");
        }
    }

    public void a(c<String> event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 12077, new Class[]{c.class}, Void.TYPE).isSupported) {
            this.d.h(event);
        }
    }

    public void k(ComponentName name, IBinder service) {
        if (!PatchProxy.proxy(new Object[]{name, service}, this, changeQuickRedirect, false, 12080, new Class[]{ComponentName.class, IBinder.class}, Void.TYPE).isSupported) {
            com.telink.bluetooth.d.a("service connected --> " + name.getShortClassName());
            this.q = true;
            a(g.d(this, "com.telink.bluetooth.light.EVENT_SERVICE_CONNECTED", service));
        }
    }

    public void l(ComponentName name) {
        if (!PatchProxy.proxy(new Object[]{name}, this, changeQuickRedirect, false, 12081, new Class[]{ComponentName.class}, Void.TYPE).isSupported) {
            com.telink.bluetooth.d.a("service disconnected --> " + name.getShortClassName());
            this.q = false;
            a(g.d(this, "com.telink.bluetooth.light.EVENT_SERVICE_DISCONNECTED", (IBinder) null));
        }
    }

    public void e(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 12082, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            a(com.telink.bluetooth.event.d.d(this, "com.telink.bluetooth.light.EVENT_LE_SCAN", (DeviceInfo) intent.getParcelableExtra("com.telink.bluetooth.light.EXTRA_DEVICE")));
        }
    }

    public void f(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 12083, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            a(com.telink.bluetooth.event.d.d(this, "com.telink.bluetooth.light.EVENT_LE_SCAN_COMPLETED", (DeviceInfo) null));
        }
    }

    public void g(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 12084, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            a(com.telink.bluetooth.event.d.d(this, "com.telink.bluetooth.light.EVENT_LE_SCAN_TIMEOUT", (DeviceInfo) null));
        }
    }

    public void m(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 12085, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            DeviceInfo deviceInfo = (DeviceInfo) intent.getParcelableExtra("com.telink.bluetooth.light.EXTRA_DEVICE");
            int i = deviceInfo.z;
            if (i == 3) {
                this.y = deviceInfo;
                a(b.d(this, "com.telink.bluetooth.light.EVENT_CURRENT_CONNECT_CHANGED", deviceInfo));
            } else if (i == 4) {
                this.y = null;
            }
            a(b.d(this, "com.telink.bluetooth.light.EVENT_STATUS_CHANGED", deviceInfo));
        }
    }

    public void n(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 12086, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            a(e.d(this, "com.telink.bluetooth.light.EVENT_UPDATE_COMPLETED", -1));
        }
    }

    public void h(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 12087, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            a(e.d(this, "com.telink.bluetooth.light.EVENT_OFFLINE", -1));
        }
    }

    public void c(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 12088, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            a(e.d(this, "com.telink.bluetooth.light.EVENT_ERROR", Integer.valueOf(intent.getIntExtra("com.telink.bluetooth.light.EXTRA_ERROR_CODE", -1))));
        }
    }

    public void d(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 12089, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            a(com.telink.bluetooth.event.c.d(this, "com.telink.bluetooth.light.ERROR_REPORT", (ErrorReportInfo) intent.getParcelableExtra("com.telink.bluetooth.light.EXTRA_ERROR_REPORT_INFO")));
        }
    }

    public void i(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 12090, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            j((NotificationInfo) intent.getParcelableExtra("com.telink.bluetooth.light.EXTRA_NOTIFY"));
        }
    }

    public void j(NotificationInfo notifyInfo) {
        if (!PatchProxy.proxy(new Object[]{notifyInfo}, this, changeQuickRedirect, false, 12091, new Class[]{NotificationInfo.class}, Void.TYPE).isSupported) {
            String eventType = f.d((byte) notifyInfo.c);
            if (!com.telink.util.f.b(eventType)) {
                f event = f.e(this, eventType, notifyInfo);
                event.c(c.a.Background);
                a(event);
            }
        }
    }
}
