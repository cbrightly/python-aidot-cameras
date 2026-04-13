package com.leedarson.base.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.logger.a;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Iterator;
import java.util.Stack;
import meshsdk.BaseResp;
import timber.log.a;

/* compiled from: AppManager */
public class c {
    private static Stack<Activity> a;
    private static c b;
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean c;
    private Class d;
    private WVJBWebView e = null;
    private String[] f = {"com.luck.picture.lib.PictureSelectorActivity", "zendesk.messaging.android.internal.conversationscreen.ConversationActivity"};
    Handler g = new Handler(Looper.getMainLooper());
    public Boolean h = false;

    public boolean m(String className) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{className}, this, changeQuickRedirect, false, BaseResp.ERR_SCENE_LIMIT, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        for (String s : this.f) {
            if (s.contains(className)) {
                return true;
            }
        }
        return false;
    }

    private c() {
    }

    public static c h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, BaseResp.ERR_SMART_LIMIT, new Class[0], c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        if (b == null) {
            b = new c();
        }
        return b;
    }

    public void a(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, BaseResp.ERR_NO_MESH_NETWORK, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            if (a == null) {
                a = new Stack<>();
            }
            if (a.search(activity) <= 0) {
                b(h().c(), activity);
                a.add(activity);
                p(" addActivity:" + activity.getClass());
            }
        }
    }

    public Activity c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 431, new Class[0], Activity.class);
        if (proxy.isSupported) {
            return (Activity) proxy.result;
        }
        Stack<Activity> stack = a;
        if (stack == null || stack.size() == 0) {
            return null;
        }
        return (Activity) a.lastElement();
    }

    public void r(Class backClass) {
        this.d = backClass;
    }

    public Class i() {
        return this.d;
    }

    public void d(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 433, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            if (activity != null) {
                a.remove(activity);
                b(activity, c());
                p("finishActivity:" + activity.getClass());
                a.c("ipc_live", "LiveActClose  finishActivity" + activity.getClass().getSimpleName());
                activity.finish();
            }
        }
    }

    public void e(Class<?> cls) {
        if (!PatchProxy.proxy(new Object[]{cls}, this, changeQuickRedirect, false, 434, new Class[]{Class.class}, Void.TYPE).isSupported) {
            Activity targetActivity = null;
            Iterator it = a.iterator();
            while (it.hasNext()) {
                Activity activity = (Activity) it.next();
                if (activity.getClass().equals(cls)) {
                    targetActivity = activity;
                }
            }
            if (targetActivity != null) {
                d(targetActivity);
            }
        }
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 435, new Class[0], Void.TYPE).isSupported) {
            Stack<Activity> stack = a;
            if (stack != null) {
                int size = stack.size();
                for (int i = 0; i < size; i++) {
                    if (a.get(i) != null) {
                        ((Activity) a.get(i)).finish();
                    }
                }
                a.clear();
            }
        }
    }

    public void t(boolean isMain) {
        this.c = isMain;
    }

    public boolean l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 437, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        boolean isInActivity = false;
        try {
            if (h().c() != null && h().c().toString().contains("com.leedarson.smarthome.ui.MainActivity")) {
                isInActivity = true;
            }
            p(isInActivity + " isMainActivity: " + h().c().toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return isInActivity;
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 438, new Class[0], Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new PushCloseIpcActivityEvent());
            try {
                Iterator<Activity> iterator = a.iterator();
                while (iterator.hasNext()) {
                    Activity activity = iterator.next();
                    if (activity != null) {
                        if (!activity.toString().contains("com.leedarson.smarthome.ui.MainActivity")) {
                            activity.finish();
                            iterator.remove();
                        }
                    }
                    if (activity != null) {
                        activity.toString().contains("com.leedarson.smarthome.ui.MainActivity");
                    }
                }
            } catch (Exception e2) {
                a.b g2 = timber.log.a.g("AppManager");
                g2.c("退出页面 finishAllButMain 发生异常 ： e=" + e2.toString(), new Object[0]);
                e2.printStackTrace();
            }
        }
    }

    public void q() {
        Handler handler;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 439, new Class[0], Void.TYPE).isSupported && (handler = this.g) != null) {
            handler.removeCallbacksAndMessages((Object) null);
        }
    }

    private void p(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 440, new Class[]{String.class}, Void.TYPE).isSupported) {
            Log.e("AppManager", msg);
        }
    }

    private void b(Activity currentActivity, Activity nextActivity) {
        Class[] clsArr = {Activity.class, Activity.class};
        if (!PatchProxy.proxy(new Object[]{currentActivity, nextActivity}, this, changeQuickRedirect, false, 441, clsArr, Void.TYPE).isSupported) {
            if (!(currentActivity == null || nextActivity == null || !(currentActivity instanceof o) || !(nextActivity instanceof o) || currentActivity == nextActivity)) {
                if (((o) nextActivity).d0() && ((o) currentActivity).d0()) {
                    com.leedarson.base.logger.a.c(this, "SUFUN. CurrentClassName=" + currentActivity.getClass().getSimpleName() + " nextClass:" + nextActivity.getClass().getSimpleName());
                    if (!"MainActivity".equals(nextActivity.getClass().getSimpleName()) && !"MainWebViewShowActivity".equals(nextActivity.getClass().getSimpleName())) {
                        com.leedarson.base.logger.a.c(this, "SUFUN. 进入到原生页面----------------->");
                        this.g.postDelayed(new a(this, currentActivity, nextActivity), 800);
                    }
                } else {
                    return;
                }
            }
            IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
            if (ipcService != null) {
                ipcService.dismissNoNetTipView();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public /* synthetic */ void o(Activity currentActivity, Activity nextActivity) {
        WVJBWebView wVJBWebView;
        Class[] clsArr = {Activity.class, Activity.class};
        if (!PatchProxy.proxy(new Object[]{currentActivity, nextActivity}, this, changeQuickRedirect, false, 443, clsArr, Void.TYPE).isSupported) {
            if (!this.h.booleanValue() && (wVJBWebView = this.e) != null) {
                ((o) currentActivity).o0(wVJBWebView);
                Log.d("appmanager", "attachWebView: ");
                ((o) nextActivity).p0(this.e);
            }
        }
    }

    public Activity k() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 442, new Class[0], Activity.class);
        if (proxy.isSupported) {
            return (Activity) proxy.result;
        }
        Stack<Activity> stack = a;
        if (stack == null) {
            return null;
        }
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            if (a.get(i) != null && ((Activity) a.get(i)).toString().contains("com.leedarson.smarthome.ui.MainActivity")) {
                return (Activity) a.get(i);
            }
        }
        com.leedarson.base.logger.a.c("getMainActivity", a.size() + "=一共有这些个Activity");
        return null;
    }

    public void s(WVJBWebView webView) {
        this.e = webView;
    }

    public WVJBWebView j() {
        return this.e;
    }
}
