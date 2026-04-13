package com.leedarson.log;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.utils.w;
import com.leedarson.bean.Constants;
import com.leedarson.log.dispatcher.b;
import com.leedarson.log.dispatcher.c;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONObject;

public class LoggerServiceImpl implements LoggerService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private c a;
    private b b;
    private Context c;
    private String d = "";

    static /* synthetic */ void a(LoggerServiceImpl x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1176, new Class[]{LoggerServiceImpl.class}, Void.TYPE).isSupported) {
            x0.i();
        }
    }

    public void init(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 1161, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.c = context;
            this.a = new c(context);
            this.b = new b(context);
        }
    }

    public void handleData(String callbackKey, Activity activity, String service, String action, String data) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{callbackKey, activity, service, action, data}, this, changeQuickRedirect, false, 1162, new Class[]{cls, Activity.class, cls, cls, cls}, Void.TYPE).isSupported) {
            if (Constants.SERVICE_LOGGER.equals(service) || "getDeviceLogUploadUrl".equals(action)) {
                this.a.c(callbackKey, activity, action, data);
            } else if (Constants.SERVICE_LOGGER2.equals(service)) {
                this.b.b(callbackKey, activity, action, data);
            }
        }
    }

    public void handleDebugEvent(Activity activity, String data) {
        Class[] clsArr = {Activity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{activity, data}, this, changeQuickRedirect, false, 1163, clsArr, Void.TYPE).isSupported) {
            this.a.d(activity, data);
        }
    }

    public void uploadLogger1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1164, new Class[0], Void.TYPE).isSupported) {
            this.a.f();
        }
    }

    public void release() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1165, new Class[0], Void.TYPE).isSupported) {
            this.a.e();
        }
    }

    public void debugMode() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1166, new Class[0], Void.TYPE).isSupported) {
            this.a.b();
        }
    }

    public JSONObject appendCommonProperties(JSONObject jsonObject) {
        int i = 1;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonObject}, this, changeQuickRedirect, false, 1167, new Class[]{JSONObject.class}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        h();
        try {
            jsonObject.put("webVersion", (Object) SharePreferenceUtils.getPrefString(this.c, "WEB_VERSION", ""));
            jsonObject.put("appVersion", (Object) w.H(this.c));
            jsonObject.put("appName", (Object) w.p(this.c));
            String str = Build.BRAND;
            jsonObject.put("brand", (Object) str);
            jsonObject.put("manufacturer", (Object) str);
            jsonObject.put("model", (Object) Build.MODEL);
            jsonObject.put("os", (Object) "Android");
            jsonObject.put("osVersion", (Object) Build.VERSION.SDK_INT + "");
            jsonObject.put("hostname", (Object) Constans.hostname);
            jsonObject.put("AndroidOsProduct", (Object) Build.PRODUCT);
            jsonObject.put("AndroidFingerPrinter", (Object) Build.FINGERPRINT);
            jsonObject.put("network", (Object) j());
            if (BaseApplication.d) {
                i = 0;
            }
            jsonObject.put("AndroidFlagFront", i);
            String str2 = this.d;
            if (str2 != null) {
                jsonObject.put("webViewVersion", (Object) str2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private String j() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1168, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            return com.leedarson.log.utils.a.a(BaseApplication.b());
        } catch (Exception e) {
            return "发生错误(未错取到网络信息):" + e.toString();
        }
    }

    private void i() {
        WVJBWebView instanceWebView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1169, new Class[0], Void.TYPE).isSupported) {
            if (TextUtils.isEmpty(this.d) && (instanceWebView = com.leedarson.base.utils.c.h().j()) != null) {
                this.d = w.I(instanceWebView.getSettings().getUserAgentString());
            }
        }
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1177, new Class[0], Void.TYPE).isSupported) {
                LoggerServiceImpl.a(LoggerServiceImpl.this);
            }
        }
    }

    private void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1170, new Class[0], Void.TYPE).isSupported) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new a());
            } else {
                i();
            }
        }
    }

    public void reportELK(Object obj, String msg, String level, String eventName) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{obj, msg, level, eventName}, this, changeQuickRedirect, false, 1171, new Class[]{Object.class, cls, cls, cls}, Void.TYPE).isSupported) {
            com.leedarson.log.elk.a.y(obj).p(msg).o(level).e(eventName).a().b();
        }
    }

    public void reportELK(Object obj, String msg, String level, String eventName, String module) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{obj, msg, level, eventName, module}, this, changeQuickRedirect, false, 1172, new Class[]{Object.class, cls, cls, cls, cls}, Void.TYPE).isSupported) {
            com.leedarson.log.elk.a.y(obj).p(msg).o(level).t(module).e(eventName).a().b();
        }
    }

    public void loginSensorsData(String loginId) {
        if (!PatchProxy.proxy(new Object[]{loginId}, this, changeQuickRedirect, false, 1173, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.log.sensorsdata.a.b().f(loginId);
        }
    }

    public void reportSensorsData(String event, JSONObject jsonObject) {
        Class[] clsArr = {String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{event, jsonObject}, this, changeQuickRedirect, false, 1174, clsArr, Void.TYPE).isSupported) {
            com.leedarson.log.sensorsdata.a.b().m(event, jsonObject);
        }
    }

    public void reportPermissionSensorsData(String event, boolean result) {
        if (!PatchProxy.proxy(new Object[]{event, new Byte(result ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 1175, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            com.leedarson.log.sensorsdata.a.b().r(event, result);
        }
    }
}
