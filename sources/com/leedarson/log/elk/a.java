package com.leedarson.log.elk;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.w;
import com.leedarson.log.reporter.b;
import com.leedarson.log.reporter.c;
import com.leedarson.log.reporter.d;
import com.leedarson.log.reporter.e;
import com.leedarson.log.reporter.f;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.HttpReportService;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: ELKBuilder */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long a = 0;
    private String b = "";
    private String c = "info";
    private String d = "unknown";
    private String e = "";
    private String f = "";
    private String g = "";
    private String h = "";
    private String i = "";
    private HashMap<String, Object> j;
    private HashMap<String, Object> k;
    private JSONArray l;
    private int m = 8;

    public a b(int type) {
        this.m = type;
        return this;
    }

    public a u(String key, Object value) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key, value}, this, changeQuickRedirect2, false, 1236, new Class[]{String.class, Object.class}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        if (this.k == null) {
            this.k = new HashMap<>();
        }
        this.k.put(key, value);
        return this;
    }

    private long l() {
        return this.a;
    }

    private a w(long time) {
        this.a = time;
        return this;
    }

    private a d(String time) {
        this.b = time;
        return this;
    }

    private String i() {
        return this.c;
    }

    public a o(String level) {
        this.c = level;
        return this;
    }

    public a t(String module) {
        this.d = module;
        return this;
    }

    private String m() {
        return this.e;
    }

    public a x(String traceId) {
        this.e = traceId == null ? "" : traceId;
        return this;
    }

    public a s(String method) {
        this.h = method;
        return this;
    }

    public a e(String eventName) {
        this.i = eventName;
        return this;
    }

    private String g() {
        return this.f;
    }

    public a c(String className) {
        this.f = className;
        return this;
    }

    private String k() {
        return this.g;
    }

    private a v(String threadId) {
        this.g = threadId;
        return this;
    }

    private HashMap<String, Object> j() {
        return this.j;
    }

    public a q(HashMap<String, Object> messageMap) {
        this.j = messageMap;
        return this;
    }

    public a p(String msg) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 1237, new Class[]{String.class}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        if (this.j == null) {
            this.j = new HashMap<>();
        }
        this.j.put("custom", msg);
        return this;
    }

    public a r(JSONArray array) {
        this.l = array;
        return this;
    }

    public static a y(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, (Object) null, changeQuickRedirect, true, 1238, new Class[]{Object.class}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        a builder = new a();
        builder.w(System.currentTimeMillis());
        builder.d(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        builder.c(obj != null ? obj.getClass().getName() : "");
        builder.v(String.format(Locale.US, "%s#%s", new Object[]{Thread.currentThread().getName(), String.valueOf(Thread.currentThread().getId())}));
        return builder;
    }

    public d a() {
        d reporter;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1239, new Class[0], d.class);
        if (proxy.isSupported) {
            return (d) proxy.result;
        }
        int i2 = this.m;
        if (i2 == 1) {
            reporter = new b(b.b().a(), b.b().c());
        } else if (i2 == 2) {
            reporter = new f();
        } else if (i2 == 4) {
            reporter = new com.leedarson.log.reporter.a();
        } else if (i2 == 8) {
            reporter = new c(b.b().a());
        } else {
            reporter = new e(i2, b.b().a());
        }
        reporter.a(f().toString());
        return reporter;
    }

    public JSONObject f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1240, new Class[0], JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject jsonObject = new JSONObject();
        if (this.l == null) {
            this.l = new JSONArray();
        }
        try {
            Context context = b.b().a();
            if (context != null) {
                jsonObject.put("username", (Object) !TextUtils.isEmpty(Constans.userName) ? Constans.userName : SharePreferenceUtils.getPrefString(context, "username", ""));
                jsonObject.put("userId", (Object) !TextUtils.isEmpty(Constans.userId) ? Constans.userId : SharePreferenceUtils.getPrefString(context, "userId", ""));
                jsonObject.put("appName", (Object) w.p(context));
            }
            jsonObject.put("time", l());
            jsonObject.put("cpu", (Object) h());
            jsonObject.put("isSupportV8a", n());
            jsonObject.put("create", (Object) this.b);
            jsonObject.put(FirebaseAnalytics.Param.LEVEL, (Object) i());
            jsonObject.put(NotificationCompat.CATEGORY_SERVICE, (Object) "AndroidApp");
            jsonObject.put(FirebaseAnalytics.Param.METHOD, (Object) this.h);
            jsonObject.put("traceId", (Object) m());
            jsonObject.put("className", (Object) g());
            jsonObject.put("threadId", (Object) k());
            jsonObject.put("module", (Object) this.d);
            jsonObject.put(NotificationCompat.CATEGORY_EVENT, (Object) this.i);
            jsonObject.put("buildType", (Object) "Release");
            jsonObject.put("hostname", (Object) Constans.hostname);
            jsonObject.put("appBuildNumber", (Object) BaseApplication.b().e());
            HttpReportService httpReportService = (HttpReportService) com.alibaba.android.arouter.launcher.a.c().g(HttpReportService.class);
            if (httpReportService != null) {
                jsonObject.put("networkInfo", (Object) httpReportService.getNetWorkInfoDetail());
            }
            HashMap<String, Object> map = j();
            if (map != null && map.size() > 0) {
                Iterator<String> iterator = map.keySet().iterator();
                if (this.m == 2) {
                    while (iterator.hasNext()) {
                        this.l.put((Object) String.valueOf(map.get(iterator.next())));
                    }
                } else {
                    JSONObject item = new JSONObject();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        item.put(key, (Object) String.valueOf(map.get(key)));
                    }
                    this.l.put((Object) item);
                }
            }
            jsonObject.put("message", (Object) this.l);
            Context ctx = b.b().a();
            jsonObject.put("houseId", (Object) SharePreferenceUtils.getPrefString(ctx, "houseId", ""));
            jsonObject.put("userId", (Object) SharePreferenceUtils.getPrefString(ctx, "userId", ""));
            jsonObject.put("APP_ID", (Object) SharePreferenceUtils.getPrefString(ctx, "APP_ID", ""));
            ((LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class)).appendCommonProperties(jsonObject);
            HashMap<String, Object> hashMap = this.k;
            if (hashMap != null) {
                for (String key2 : hashMap.keySet()) {
                    jsonObject.put(key2, this.k.get(key2));
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jsonObject;
    }

    private String h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1241, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuffer sb = new StringBuffer();
        for (String str : Build.SUPPORTED_ABIS) {
            sb.append(str);
            sb.append(" ");
        }
        return sb.toString();
    }

    private int n() {
        String[] abis = Build.SUPPORTED_64_BIT_ABIS;
        if (abis == null || abis.length <= 0) {
            return 0;
        }
        return 1;
    }
}
