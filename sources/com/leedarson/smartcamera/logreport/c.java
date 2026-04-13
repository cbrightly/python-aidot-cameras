package com.leedarson.smartcamera.logreport;

import android.content.Context;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.l;
import com.leedarson.log.mgr.u;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.smartcamera.bean.IPCLiveStepBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

/* compiled from: WebRTCLiveReporter */
public class c {
    private static c a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private Random b = new Random();
    SimpleDateFormat c;

    static /* synthetic */ void a(c x0, HashMap x1, String x2) {
        Class[] clsArr = {c.class, HashMap.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 10158, clsArr, Void.TYPE).isSupported) {
            x0.f(x1, x2);
        }
    }

    private c() {
    }

    public static c b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 10154, new Class[0], c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        if (a == null) {
            synchronized (c.class) {
                if (a == null) {
                    a = new c();
                }
            }
        }
        return a;
    }

    public void e(String deviceId, String peerId, String step, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, peerId, step, message}, this, changeQuickRedirect, false, 10155, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
            l.l.execute(new a(this, peerId, deviceId, step, message));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public /* synthetic */ void d(String str, String str2, String str3, String str4) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, str3, str4}, this, changeQuickRedirect, false, 10157, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
            String deviceId = str2;
            String message = str4;
            String peerId = str;
            String step = str3;
            try {
                if (this.c == null) {
                    this.c = new SimpleDateFormat("HH:mm:ss", Locale.US);
                }
                b tracker = new a(BaseApplication.b(), peerId, deviceId);
                String traceId = String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_CUSTOM_TRACE_ID", Integer.valueOf(this.b.nextInt())});
                tracker.j(traceId, "LdsPlayer", "LIVE_CUSTOM");
                u.d().b(tracker);
                IPCLiveStepBean bean = new IPCLiveStepBean(step, 0);
                String currentTimeStr = "";
                SimpleDateFormat simpleDateFormat = this.c;
                if (simpleDateFormat != null) {
                    currentTimeStr = simpleDateFormat.format(Calendar.getInstance().getTime());
                }
                bean.setDesc("time:" + currentTimeStr + " msg:" + message);
                u.d().a(traceId, bean);
                u.d().e(traceId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* compiled from: WebRTCLiveReporter */
    public class a extends b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String i;
        final /* synthetic */ String j;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(Context context, String str, String str2) {
            super(context);
            this.i = str;
            this.j = str2;
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10159, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap fields = new HashMap();
            fields.put("peerId", this.i);
            c.a(c.this, fields, this.j);
            return fields;
        }
    }

    private void f(HashMap<String, Object> fields, String deviceId) {
        Class[] clsArr = {HashMap.class, String.class};
        if (!PatchProxy.proxy(new Object[]{fields, deviceId}, this, changeQuickRedirect, false, 10156, clsArr, Void.TYPE).isSupported) {
            if (fields != null) {
                fields.put("deviceId", deviceId);
                try {
                    BaseApplication b2 = BaseApplication.b();
                    String firVer = SharePreferenceUtils.getPrefString(b2, "firmware_" + deviceId, "");
                    BaseApplication b3 = BaseApplication.b();
                    String hardVer = SharePreferenceUtils.getPrefString(b3, "hardware_" + deviceId, "");
                    BaseApplication b4 = BaseApplication.b();
                    String modelId = SharePreferenceUtils.getPrefString(b4, "modelId_" + deviceId, "");
                    fields.put("firmwareVersion", firVer);
                    fields.put("hardwareVersion", hardVer);
                    fields.put("modelId", modelId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
