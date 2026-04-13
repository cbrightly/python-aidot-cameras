package com.leedarson.base.application;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.leedarson.base.application.d;
import com.leedarson.serviceinterface.HttpReportService;
import com.leedarson.serviceinterface.event.BackAndFrontChangeEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import com.yanzhenjie.andserver.e;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.onAdaptListener;
import me.jessyan.autosize.unit.Subunits;
import me.jessyan.autosize.utils.ScreenUtils;

public class BaseApplication extends Application {
    private static BaseApplication c;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static boolean d = false;
    private String A4 = "";
    public BroadcastReceiver B4 = new BroadcastReceiver() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void onReceive(Context context, Intent intent) {
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, NeedPermissionEvent.PER_ANDROID_S_BLE, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
                String action = intent.getAction();
                if (intent.getAction().equals("android.intent.action.LOCALE_CHANGED")) {
                    Locale locale = PubUtils.getCurrentSystemLocal();
                    timber.log.a.i("lang----> 语言发生了变化--改变成:" + (locale.getLanguage() + "-" + locale.getCountry()), new Object[0]);
                    com.leedarson.base.utils.c.h().f();
                }
            }
        }
    };
    public long a1 = 0;
    private String a2 = "en-Us";
    public e f;
    public boolean p0 = true;
    public long p1 = 0;
    public long p2 = 0;
    public boolean p3 = false;
    private Map<String, String> p4 = new HashMap();
    private boolean q = false;
    private String x = "APK";
    public String y = "";
    private boolean z = false;
    private String z4 = "";

    static /* synthetic */ void a(BaseApplication x0, Activity x1, int x2) {
        Object[] objArr = {x0, x1, new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 134, new Class[]{BaseApplication.class, Activity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.y(x1, x2);
        }
    }

    public void onCreate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 121, new Class[0], Void.TYPE).isSupported) {
            super.onCreate();
            h();
            c = this;
            j();
            i();
            k();
            com.leedarson.base.http.b.b();
        }
    }

    public void k() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 122, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.plugins.a.x(a.c);
        }
    }

    static /* synthetic */ void p(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, (Object) null, changeQuickRedirect, true, 133, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            throwable.printStackTrace();
            Log.e("rxjava", "rxjava.exception--->" + throwable.toString() + "");
        }
    }

    public static BaseApplication b() {
        return c;
    }

    private void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 123, new Class[0], Void.TYPE).isSupported) {
            AutoSizeConfig.getInstance().getUnitsManager().setSupportSP(false).setSupportSubunits(Subunits.MM);
            AutoSizeConfig.getInstance().setCustomFragment(false);
        }
    }

    public class a implements d.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a(Activity activity) {
            if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 135, new Class[]{Activity.class}, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("BaseApp", "sufun.data App.CallbackChange  离开App (前后台切换) ");
                BackAndFrontChangeEvent event = new BackAndFrontChangeEvent();
                HttpReportService httpReportService = (HttpReportService) com.alibaba.android.arouter.launcher.a.c().g(HttpReportService.class);
                if (httpReportService != null) {
                    httpReportService.onBackgroundAndFrontSwitch(true);
                }
                event.isFrontFlag = false;
                org.greenrobot.eventbus.c.c().l(event);
                com.leedarson.base.webservice.utils.b.b().h("App前后台切换（切换至后台）", "");
                if (activity != null) {
                    BaseApplication.a(BaseApplication.this, activity, activity.getResources().getConfiguration().orientation);
                }
            }
        }

        public void b(Activity activity) {
            if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 136, new Class[]{Activity.class}, Void.TYPE).isSupported) {
                BaseApplication.this.p2 = System.currentTimeMillis();
                BackAndFrontChangeEvent event = new BackAndFrontChangeEvent();
                event.isFrontFlag = true;
                org.greenrobot.eventbus.c.c().l(event);
                com.leedarson.base.logger.a.c("BaseApp", "sufun.data App.CallbackChange 回到前台了 （前后台切换）");
                HttpReportService httpReportService = (HttpReportService) com.alibaba.android.arouter.launcher.a.c().g(HttpReportService.class);
                if (httpReportService != null) {
                    httpReportService.onBackgroundAndFrontSwitch(false);
                }
                com.leedarson.base.webservice.utils.b.b().h("App前后台切换（切换至前台）", "");
            }
        }
    }

    private void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 124, new Class[0], Void.TYPE).isSupported) {
            registerActivityLifecycleCallbacks(new d(new a()));
            AutoSizeConfig.getInstance().setOnAdaptListener(new b());
            registerComponentCallbacks(new c());
        }
    }

    public class b implements onAdaptListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onAdaptBefore(Object obj, Activity activity) {
            Class[] clsArr = {Object.class, Activity.class};
            if (!PatchProxy.proxy(new Object[]{obj, activity}, this, changeQuickRedirect, false, NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV, clsArr, Void.TYPE).isSupported) {
                BaseApplication.a(BaseApplication.this, activity, activity.getResources().getConfiguration().orientation);
            }
        }

        public void onAdaptAfter(Object target, Activity activity) {
        }
    }

    public class c implements ComponentCallbacks {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onConfigurationChanged(@NonNull Configuration newConfig) {
            if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 138, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
                BaseApplication.a(BaseApplication.this, com.leedarson.base.utils.c.h().c(), newConfig.orientation);
            }
        }

        public void onLowMemory() {
        }
    }

    private void y(Activity _currentActivity, int _orientation) {
        if (!PatchProxy.proxy(new Object[]{_currentActivity, new Integer(_orientation)}, this, changeQuickRedirect, false, 125, new Class[]{Activity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (_currentActivity != null) {
                int screenWidth = ScreenUtils.getScreenSize(_currentActivity)[0];
                int screenHeight = ScreenUtils.getScreenSize(_currentActivity)[1];
                AutoSizeConfig.getInstance().setScreenWidth(screenWidth);
                AutoSizeConfig.getInstance().setScreenHeight(screenHeight);
                if (_orientation == 1) {
                    AutoSize.autoConvertDensityBaseOnWidth(_currentActivity, 375.0f);
                    timber.log.a.i("updateBaseLineOfUIPage(竖屏)-->width=" + screenWidth + ",height=" + screenHeight, new Object[0]);
                    return;
                }
                AutoSize.autoConvertDensityBaseOnWidth(_currentActivity, 812.0f);
                timber.log.a.i("updateBaseLineOfUIPage(横屏取消)-->width=" + screenWidth + ",height=" + screenHeight, new Object[0]);
            }
        }
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 126, new Class[0], Void.TYPE).isSupported) {
            try {
                Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
                Field sCurrentActivityThread = activityThreadClazz.getDeclaredField("sCurrentActivityThread");
                sCurrentActivityThread.setAccessible(true);
                Object activityThread = sCurrentActivityThread.get(activityThreadClazz);
                Field mHField = activityThreadClazz.getDeclaredField("mH");
                mHField.setAccessible(true);
                Class<?> HClass = Class.forName("android.app.ActivityThread$H");
                Field scheduleCrashField = HClass.getDeclaredField("SCHEDULE_CRASH");
                scheduleCrashField.setAccessible(true);
                int whatForScheduleCrash = scheduleCrashField.getInt(HClass);
                Field mCallbackField = Class.forName("android.os.Handler").getDeclaredField("mCallback");
                mCallbackField.setAccessible(true);
                mCallbackField.set((Handler) mHField.get(activityThread), new d(whatForScheduleCrash));
            } catch (Throwable e) {
                timber.log.a.c("hookHandler 异常:" + e.getMessage(), new Object[0]);
            }
        }
    }

    public class d implements Handler.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        d(int i) {
            this.c = i;
        }

        public boolean handleMessage(@NonNull Message msg) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, NeedPermissionEvent.PER_GET_LOCATION_BLE, new Class[]{Message.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (msg.what != this.c) {
                return false;
            }
            timber.log.a.c("hookHandler 拦截到 SCHEDULE_CRASH 崩溃异常..", new Object[0]);
            return true;
        }
    }

    public boolean o() {
        return this.q;
    }

    public void w(boolean isProd) {
        this.q = isProd;
    }

    public boolean n() {
        return this.z;
    }

    public void u(boolean isDebug) {
        this.z = isDebug;
    }

    public void r(String archiveType) {
        this.x = archiveType;
    }

    public void x(String repositoryName) {
        this.y = repositoryName;
    }

    public boolean m() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, NeedPermissionEvent.PER_IPC_SPEAK_PERM, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!"APK".equals(this.x) || !"M071-AiDot".equals(this.y)) {
            return false;
        }
        return true;
    }

    public void l() {
        this.p3 = true;
    }

    public void s(String versionCode) {
        this.z4 = versionCode;
    }

    public String e() {
        return this.z4;
    }

    public void q(String appVersionName) {
        this.A4 = appVersionName;
    }

    public String c() {
        return this.A4;
    }

    public String d() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 128, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return SharePreferenceUtils.getPrefString(b(), "userId", "");
    }

    public void t(String defaultLanguage) {
        if (!PatchProxy.proxy(new Object[]{defaultLanguage}, this, changeQuickRedirect, false, NeedPermissionEvent.PER_IPC_ALBUM_PERM, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(defaultLanguage)) {
                this.a2 = defaultLanguage;
            }
        }
    }

    public String f() {
        return this.a2;
    }

    public void v(String key, String value) {
        Map<String, String> map;
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{key, value}, this, changeQuickRedirect, false, 131, clsArr, Void.TYPE).isSupported && (map = this.p4) != null) {
            map.put(key, value);
        }
    }

    public String g(String key) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 132, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Map<String, String> map = this.p4;
        if (map == null) {
            return "";
        }
        return map.get(key);
    }
}
