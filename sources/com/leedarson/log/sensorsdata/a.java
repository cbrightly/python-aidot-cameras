package com.leedarson.log.sensorsdata;

import android.content.Context;
import android.text.TextUtils;
import com.leedarson.base.utils.w;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.MapService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataDynamicSuperProperties;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

/* compiled from: LDSSensorsData */
public class a {
    private static a a;
    private static double[] b;
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context c;
    private long d = 0;
    private String e = "";
    private String f;

    public void k(String repositoryName) {
        this.e = repositoryName;
    }

    public void j(String modelId) {
        this.f = modelId;
    }

    public static a b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1343, new Class[0], a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    public void d(Context context) {
        this.c = context;
    }

    public void l(String event, HashMap<String, String> map) {
        Class[] clsArr = {String.class, HashMap.class};
        if (!PatchProxy.proxy(new Object[]{event, map}, this, changeQuickRedirect, false, 1344, clsArr, Void.TYPE).isSupported) {
            JSONObject jsonObject = new JSONObject();
            for (String key : map.keySet()) {
                try {
                    jsonObject.put(key, map.get(key));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            m(event, jsonObject);
        }
    }

    public void o(String event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 1345, new Class[]{String.class}, Void.TYPE).isSupported) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("product_model", (Object) this.f);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            n(event, jsonObject, "App");
        }
    }

    public void p(String event, String sku_id) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{event, sku_id}, this, changeQuickRedirect, false, 1346, clsArr, Void.TYPE).isSupported) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("product_model", (Object) this.f);
                jsonObject.put("sku_id", (Object) sku_id);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            n(event, jsonObject, "App");
        }
    }

    public void q(String event, String page) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{event, page}, this, changeQuickRedirect, false, 1347, clsArr, Void.TYPE).isSupported) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("product_model", (Object) this.f);
                jsonObject.put("tab_type", (Object) page);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            n(event, jsonObject, "App");
        }
    }

    public void m(String event, JSONObject jsonObject) {
        Class[] clsArr = {String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{event, jsonObject}, this, changeQuickRedirect, false, 1348, clsArr, Void.TYPE).isSupported) {
            n(event, jsonObject, "App");
        }
    }

    public void n(String event, JSONObject jsonObject, String platformType) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, JSONObject.class, cls};
        if (!PatchProxy.proxy(new Object[]{event, jsonObject, platformType}, this, changeQuickRedirect, false, 1349, clsArr, Void.TYPE).isSupported) {
            if (jsonObject == null) {
                jsonObject = new JSONObject();
            }
            g(jsonObject, platformType);
            SensorsDataAPI.sharedInstance().track(event, jsonObject);
        }
    }

    private void g(JSONObject jsonObject, String platformType) {
        boolean z = false;
        if (!PatchProxy.proxy(new Object[]{jsonObject, platformType}, this, changeQuickRedirect, false, 1350, new Class[]{JSONObject.class, String.class}, Void.TYPE).isSupported) {
            a();
            try {
                jsonObject.put("platform_type", (Object) platformType);
                AtomicBoolean atomicBoolean = Constans.isLogin;
                if (atomicBoolean != null) {
                    jsonObject.put("is_login", atomicBoolean.get());
                } else {
                    if (!TextUtils.isEmpty(Constans.userName)) {
                        z = true;
                    }
                    jsonObject.put("is_login", z);
                }
                jsonObject.put("vip_level", (Object) Constans.VIPLevel);
                jsonObject.put("is_vip", Constans.isVIP);
                jsonObject.put("username", (Object) Constans.userName);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1351, new Class[0], Void.TYPE).isSupported) {
            if (b == null) {
                String[] perms = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
                Context context = this.c;
                if (context != null && !EasyPermissions.a(context, perms)) {
                    float longitude = SharePreferenceUtils.getPrefFloat(this.c, "longitude", 0.0f);
                    float latitude = SharePreferenceUtils.getPrefFloat(this.c, "latitude", 0.0f);
                    if (longitude == 0.0f || latitude == 0.0f) {
                        MapService mapService = (MapService) com.alibaba.android.arouter.launcher.a.c().g(MapService.class);
                        if (mapService != null) {
                            try {
                                if (System.currentTimeMillis() - this.d > 60000) {
                                    this.d = System.currentTimeMillis() - this.d;
                                    mapService.getLocation(this.c);
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    } else {
                        b = new double[]{(double) latitude, (double) longitude};
                        SensorsDataAPI.sharedInstance().setGPSLocation((double) latitude, (double) longitude);
                    }
                }
            }
        }
    }

    public void h(String SA_SERVER_URL) {
        if (!PatchProxy.proxy(new Object[]{SA_SERVER_URL}, this, changeQuickRedirect, false, 1352, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("SA.LDSSensorsData");
            g.h("重定向神策上报地址:" + SA_SERVER_URL, new Object[0]);
            if (c()) {
                AbstractSensorsDataAPI.getConfigOptions().setServerUrl(SA_SERVER_URL);
                return;
            }
            timber.log.a.g("SA.LDSSensorsData").h("重定向神策上报地址 sensors data SDK have not init,start init sdk", new Object[0]);
            e(this.c, SA_SERVER_URL, false);
        }
    }

    public boolean c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1353, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (AbstractSensorsDataAPI.getConfigOptions() != null) {
            return true;
        }
        return false;
    }

    public void e(Context context, String serverURL, boolean enableLog) {
        Object[] objArr = {context, serverURL, new Byte(enableLog ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1354, new Class[]{Context.class, String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            if ("M071-AiDot".equals(this.e) || "M071-Linkind".equals(this.e)) {
                SAConfigOptions saConfigOptions = new SAConfigOptions(serverURL);
                saConfigOptions.setAutoTrackEventType(11).enableLog(enableLog);
                saConfigOptions.setFlushInterval(10000);
                saConfigOptions.enableTrackScreenOrientation(true);
                saConfigOptions.enableJavaScriptBridge(true);
                SensorsDataAPI.startWithConfigOptions(context, saConfigOptions);
                try {
                    JSONObject properties = new JSONObject();
                    properties.put("app_name", (Object) w.p(context));
                    properties.put("platform_type", (Object) "App");
                    SensorsDataAPI.sharedInstance().registerSuperProperties(properties);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                SensorsDataAPI.sharedInstance().registerDynamicSuperProperties(new C0089a());
                SensorsDataAPI.sharedInstance().trackAppInstall();
                f(SharePreferenceUtils.getPrefString(context, "userId", ""));
                return;
            }
            timber.log.a.g("SA.LDSApplication").a("非M071-AiDot 或 M071-Linkind，不初始化神策", new Object[0]);
        }
    }

    /* renamed from: com.leedarson.log.sensorsdata.a$a  reason: collision with other inner class name */
    /* compiled from: LDSSensorsData */
    public class C0089a implements SensorsDataDynamicSuperProperties {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0089a() {
        }

        public JSONObject getDynamicSuperProperties() {
            boolean z = false;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1358, new Class[0], JSONObject.class);
            if (proxy.isSupported) {
                return (JSONObject) proxy.result;
            }
            JSONObject jsonObject = new JSONObject();
            try {
                AtomicBoolean atomicBoolean = Constans.isLogin;
                if (atomicBoolean != null) {
                    jsonObject.put("is_login", atomicBoolean.get());
                } else {
                    if (!TextUtils.isEmpty(Constans.userName)) {
                        z = true;
                    }
                    jsonObject.put("is_login", z);
                }
                jsonObject.put("vip_level", (Object) Constans.VIPLevel);
                jsonObject.put("is_vip", Constans.isVIP);
                jsonObject.put("username", (Object) Constans.userName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }
    }

    public void f(String userId) {
        if (!PatchProxy.proxy(new Object[]{userId}, this, changeQuickRedirect, false, 1355, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(userId) && c()) {
                a.b g = timber.log.a.g("SA.LDSSensorsData");
                g.h("神策登录 login:" + userId, new Object[0]);
                SensorsDataAPI.sharedInstance().login(userId);
            }
        }
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1356, new Class[0], Void.TYPE).isSupported && c()) {
            timber.log.a.g("SA.LDSSensorsData").h("release sensorsData SDK", new Object[0]);
            SensorsDataAPI.sharedInstance().stopTrackThread();
            SensorsDataAPI.disableSDK();
        }
    }

    public void r(String event, boolean result) {
        Object[] objArr = {event, new Byte(result ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1357, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("SA.LDSSensorsData");
            g.a("trackPermissionResult event:" + event + ",result:" + result, new Object[0]);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("settings_result", result);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            m(event, jsonObject);
        }
    }
}
