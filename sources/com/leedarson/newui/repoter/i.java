package com.leedarson.newui.repoter;

import android.content.Context;
import android.text.TextUtils;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.H5ActionName;
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

/* compiled from: NewIPCLiveReporter */
public class i {
    private static i a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private int b = 0;
    private Random c = new Random();
    long d;
    SimpleDateFormat e;
    String f;
    com.leedarson.smartcamera.logreport.b g;

    static /* synthetic */ void a(i x0, HashMap x1, String x2) {
        Class[] clsArr = {i.class, HashMap.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 4630, clsArr, Void.TYPE).isSupported) {
            x0.k(x1, x2);
        }
    }

    private i() {
    }

    public static i c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 4601, new Class[0], i.class);
        if (proxy.isSupported) {
            return (i) proxy.result;
        }
        if (a == null) {
            synchronized (i.class) {
                if (a == null) {
                    a = new i();
                }
            }
        }
        return a;
    }

    /* compiled from: NewIPCLiveReporter */
    public class a extends com.leedarson.smartcamera.logreport.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(Context context, String str) {
            super(context);
            this.i = str;
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4631, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            i.a(i.this, fields, this.i);
            fields.put("module", "LiveTUTK");
            return fields;
        }
    }

    public void f(Context context, String deviceId) {
        if (!PatchProxy.proxy(new Object[]{context, deviceId}, this, changeQuickRedirect, false, 4602, new Class[]{Context.class, String.class}, Void.TYPE).isSupported) {
            com.leedarson.smartcamera.logreport.b tracker = new a(context, deviceId);
            tracker.j(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), "LdsPlayer", "FirstFrameRender");
            u.d().b(tracker);
        }
    }

    public void s(String deviceId, String p2pId, String account, String password, String dtls, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls, cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{deviceId, p2pId, account, password, dtls, message}, this, changeQuickRedirect, false, 4603, clsArr, Void.TYPE).isSupported) {
            this.b = 0;
            IPCLiveStepBean bean = new IPCLiveStepBean("tutkConnect", 200);
            bean.setDesc(message);
            bean.putRequestParams("p2pId", p2pId);
            bean.putRequestParams("account", account);
            bean.putRequestParams("password", com.leedarson.base.utils.d.a(password));
            bean.putRequestParams("dtls", dtls);
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), bean);
        }
    }

    public void w(String deviceId, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, message}, this, changeQuickRedirect, false, 4604, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("tutkStateChange", this.b);
            bean.setDesc(message);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}));
        }
    }

    public void x(String deviceId, long duration, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, new Long(duration), message}, this, changeQuickRedirect, false, 4605, new Class[]{cls, Long.TYPE, cls}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("tutkConnectSuccess", 200);
            bean.setDesc(message);
            bean.setDuration(duration);
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), bean);
        }
    }

    public void v(String deviceId, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, message}, this, changeQuickRedirect, false, 4606, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("tutkConnectMode", 200);
            bean.setDesc(message);
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), bean);
        }
    }

    public void A(String deviceId, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, message}, this, changeQuickRedirect, false, 4607, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("tutkGetStreamSuccess", 200);
            bean.setDesc(message);
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), bean);
        }
    }

    public void y(String deviceId, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, message}, this, changeQuickRedirect, false, 4608, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("autoReduceBitRate", 200);
            bean.setDesc(message);
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), bean);
        }
    }

    public void B(String deviceId, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, message}, this, changeQuickRedirect, false, 4609, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("tutkReceiveIFrame", 200);
            bean.setDesc(message);
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), bean);
        }
    }

    public void z(String deviceId, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, message}, this, changeQuickRedirect, false, 4610, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("tutkGetStreamFail", this.b);
            bean.setDesc(message);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}));
        }
    }

    public void t(String deviceId, int errorCode) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Integer(errorCode)}, this, changeQuickRedirect, false, 4611, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            this.b = errorCode;
            IPCLiveStepBean bean = new IPCLiveStepBean("tutkConnectFail", errorCode);
            bean.setDesc("TUTK指令异常");
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), bean);
        }
    }

    public void u(String deviceId, int errorCode, boolean appMqttConnected, boolean deviceMqttConnected) {
        Object[] objArr = {deviceId, new Integer(errorCode), new Byte(appMqttConnected ? (byte) 1 : 0), new Byte(deviceMqttConnected ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4612, new Class[]{String.class, Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
            this.b = errorCode;
            IPCLiveStepBean bean = new IPCLiveStepBean("tutkConnectFail", errorCode);
            bean.setDeviceOnlieStatue(deviceMqttConnected ? 1 : 0);
            Locale locale = Locale.US;
            bean.setDesc(String.format(locale, "设备TUTK离线:app状态[%s] 设备状态[%s]", new Object[]{Boolean.valueOf(appMqttConnected), Boolean.valueOf(deviceMqttConnected)}));
            u.d().a(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), bean);
        }
    }

    public void r(String str, long renderDuration, long totalTime, String message) {
        Class<String> cls = String.class;
        Object[] objArr = {str, new Long(renderDuration), new Long(totalTime), message};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Long.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4613, new Class[]{cls, cls2, cls2, cls}, Void.TYPE).isSupported) {
            String deviceId = str;
            IPCLiveStepBean bean = new IPCLiveStepBean(H5ActionName.ACTION_START_PLAY, 200);
            bean.setDesc(message);
            bean.setDuration(renderDuration);
            bean.setTotalDuration(totalTime);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}));
        }
    }

    public void i(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4614, new Class[]{String.class}, Void.TYPE).isSupported) {
            u.d().e(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_TRACE_ID", deviceId}));
        }
    }

    /* compiled from: NewIPCLiveReporter */
    public class b extends com.leedarson.smartcamera.logreport.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(Context context, String str) {
            super(context);
            this.i = str;
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4632, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            i.a(i.this, fields, this.i);
            fields.put("module", "LiveTUTK");
            return fields;
        }
    }

    public void h(Context context, String deviceId) {
        if (!PatchProxy.proxy(new Object[]{context, deviceId}, this, changeQuickRedirect, false, 4615, new Class[]{Context.class, String.class}, Void.TYPE).isSupported) {
            com.leedarson.smartcamera.logreport.b tracker = new b(context, deviceId);
            tracker.j(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_START_TRACE_ID", deviceId}), "LdsPlayer", "LIVE_TUTK_TALKBACK");
            u.d().b(tracker);
        }
    }

    public void m(String deviceId, int talkMode) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Integer(talkMode)}, this, changeQuickRedirect, false, 4616, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("speakStart", 200);
            bean.setDesc("TUTK对讲开始");
            bean.putRequestParams("talkMode", Integer.valueOf(talkMode));
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_START_TRACE_ID", deviceId}), bean);
        }
    }

    public void q(String deviceId, long duration) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Long(duration)}, this, changeQuickRedirect, false, 4617, new Class[]{String.class, Long.TYPE}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("speakSuccess", 200);
            bean.setDesc("TUTK对讲成功");
            bean.setDuration(duration);
            bean.setTotalDuration(duration);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_START_TRACE_ID", deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_START_TRACE_ID", deviceId}));
        }
    }

    public void l(String deviceId, int errorCode) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Integer(errorCode)}, this, changeQuickRedirect, false, 4618, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("speakFail", errorCode);
            bean.setDesc("TUTK对讲异常");
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_START_TRACE_ID", deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_START_TRACE_ID", deviceId}));
        }
    }

    /* compiled from: NewIPCLiveReporter */
    public class c extends com.leedarson.smartcamera.logreport.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(Context context, String str) {
            super(context);
            this.i = str;
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4633, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            i.a(i.this, fields, this.i);
            fields.put("module", "LiveTUTK");
            return fields;
        }
    }

    public void g(Context context, String deviceId) {
        if (!PatchProxy.proxy(new Object[]{context, deviceId}, this, changeQuickRedirect, false, 4619, new Class[]{Context.class, String.class}, Void.TYPE).isSupported) {
            com.leedarson.smartcamera.logreport.b tracker = new c(context, deviceId);
            tracker.j(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_STOP_TRACE_ID", deviceId}), "LdsPlayer", "LIVE_TUTK_TALKBACK");
            u.d().b(tracker);
        }
    }

    public void n(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4620, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.d = System.currentTimeMillis();
            IPCLiveStepBean bean = new IPCLiveStepBean("speakStop", 200);
            bean.setDesc("TUTK对讲开始关闭");
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_STOP_TRACE_ID", deviceId}), bean);
        }
    }

    public void p(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4621, new Class[]{String.class}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("speakStopSuccess", 200);
            bean.setDesc("TUTK关闭成功");
            bean.setDuration(System.currentTimeMillis() - this.d);
            bean.setTotalDuration(System.currentTimeMillis() - this.d);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_STOP_TRACE_ID", deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_STOP_TRACE_ID", deviceId}));
        }
    }

    public void o(String deviceId, int errorCode) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Integer(errorCode)}, this, changeQuickRedirect, false, 4622, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("speakStopFail", errorCode);
            bean.setDesc("TUTK对讲关闭异常");
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_STOP_TRACE_ID", deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_SPEAK_STOP_TRACE_ID", deviceId}));
        }
    }

    public void j(Context context, String str, String str2, String str3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, str, str2, str3}, this, changeQuickRedirect, false, 4623, new Class[]{Context.class, cls, cls, cls}, Void.TYPE).isSupported) {
            String deviceId = str;
            String message = str3;
            Context context2 = context;
            String step = str2;
            try {
                if (this.e == null) {
                    this.e = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.US);
                }
                com.leedarson.smartcamera.logreport.b tracker = new d(context2, deviceId);
                String traceId = String.format(Locale.US, "%s:%s", new Object[]{"IPC_LIVE_CUSTOM_TRACE_ID", Integer.valueOf(this.c.nextInt())});
                tracker.j(traceId, "LdsPlayer", "LIVE_CUSTOM");
                u.d().b(tracker);
                IPCLiveStepBean bean = new IPCLiveStepBean(step, 0);
                String currentTimeStr = "";
                SimpleDateFormat simpleDateFormat = this.e;
                if (simpleDateFormat != null) {
                    currentTimeStr = simpleDateFormat.format(Calendar.getInstance().getTime());
                }
                bean.setDesc("time:" + currentTimeStr + " msg:" + message);
                u.d().a(traceId, bean);
                u.d().e(traceId);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewIPCLiveReporter */
    public class d extends com.leedarson.smartcamera.logreport.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(Context context, String str) {
            super(context);
            this.i = str;
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4634, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            i.a(i.this, fields, this.i);
            return fields;
        }
    }

    /* compiled from: NewIPCLiveReporter */
    public class e extends com.leedarson.smartcamera.logreport.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        e(Context context) {
            super(context);
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4635, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            fields.put("module", "Live");
            return fields;
        }
    }

    public void e(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 4624, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.g = new e(context);
            Locale locale = Locale.US;
            String format = String.format(locale, "%s:%s", new Object[]{"IPC_LIVE_LIFECYCLE", System.currentTimeMillis() + ""});
            this.f = format;
            this.g.j(format, "LdsPlayer", "LIVE_LIFECYCLE");
            u.d().b(this.g);
        }
    }

    public void b(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4625, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                if (this.e == null) {
                    this.e = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.US);
                }
                IPCLiveStepBean bean = new IPCLiveStepBean("lifecycle", 0);
                String currentTimeStr = "";
                SimpleDateFormat simpleDateFormat = this.e;
                if (simpleDateFormat != null) {
                    currentTimeStr = simpleDateFormat.format(Calendar.getInstance().getTime());
                }
                bean.setDesc("time:" + currentTimeStr + " msg:" + message);
                u.d().a(this.f, bean);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void C(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4626, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.g != null && !TextUtils.isEmpty(deviceId)) {
                this.g.d("deviceId", deviceId);
            }
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4627, new Class[0], Void.TYPE).isSupported) {
            try {
                u.d().e(this.f);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void k(HashMap<String, Object> fields, String deviceId) {
        Class[] clsArr = {HashMap.class, String.class};
        if (!PatchProxy.proxy(new Object[]{fields, deviceId}, this, changeQuickRedirect, false, 4629, clsArr, Void.TYPE).isSupported) {
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
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
