package com.leedarson.base.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.base.utils.c;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.event.BackAndFrontChangeImmediatelyEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: LDSActivityLifeCycle */
public class d implements Application.ActivityLifecycleCallbacks {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c = 0;
    private boolean d = true;
    private a f;

    /* compiled from: LDSActivityLifeCycle */
    public interface a {
        void a(Activity activity);

        void b(Activity activity);
    }

    public d(a handler) {
        this.f = handler;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (!PatchProxy.proxy(new Object[]{activity, bundle}, this, changeQuickRedirect, false, NeedPermissionEvent.PER_ANDROID_NOTIFICATION, new Class[]{Activity.class, Bundle.class}, Void.TYPE).isSupported) {
            String className = activity.getClass().getName();
            a.b g = timber.log.a.g("Core");
            g.a("onActivityCreated:" + className, new Object[0]);
            a("LDSActivityLifeCycle onActivityCreated=" + activity.getClass().getSimpleName());
            if (c.h().m(className)) {
                c.h().a(activity);
            }
        }
    }

    public void onActivityStarted(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 142, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            a("LDSActivityLifeCycle onActivityStarted=" + activity.getClass().getSimpleName());
            if (this.c == 0) {
                BaseApplication.d = false;
                this.d = true;
                if (this.f != null) {
                    Log.i("前后台切换", "sufun.data LiveCycle前台....");
                    IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                    if (ipcService != null) {
                        ipcService.preconnectAll();
                    }
                    BackAndFrontChangeImmediatelyEvent event = new BackAndFrontChangeImmediatelyEvent();
                    event.isFrontFlag = true;
                    org.greenrobot.eventbus.c.c().l(event);
                    new Handler(Looper.getMainLooper()).postDelayed(new c(this, activity), 0);
                }
            }
            this.c++;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public /* synthetic */ void c(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 150, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            this.f.b(activity);
        }
    }

    public void onActivityResumed(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 143, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            a("LDSActivityLifeCycle onActivityResumed=" + activity.getClass().getSimpleName());
        }
    }

    public void onActivityPaused(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, IjkMediaMeta.FF_PROFILE_H264_HIGH_444, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            a("LDSActivityLifeCycle onActivityPaused=" + activity.getClass().getSimpleName());
        }
    }

    public void onActivityStopped(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 145, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            a("LDSActivityLifeCycle onActivityStopped=" + activity.getClass().getSimpleName());
            int i = this.c - 1;
            this.c = i;
            if (i == 0) {
                this.d = false;
                BaseApplication.d = true;
                if (this.f != null) {
                    timber.log.a.g("Core").a("LDSActivityLifeCycle sufun.data LiveCycle 后台....", new Object[0]);
                    IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                    if (ipcService != null) {
                        ipcService.disConnectAllWebRtcChannel();
                    }
                    BackAndFrontChangeImmediatelyEvent event = new BackAndFrontChangeImmediatelyEvent();
                    event.isFrontFlag = false;
                    org.greenrobot.eventbus.c.c().l(event);
                    new Handler(Looper.getMainLooper()).postDelayed(new b(this, activity), 0);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public /* synthetic */ void e(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, Opcodes.FCMPL, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            this.f.a(activity);
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Class[] clsArr = {Activity.class, Bundle.class};
        if (!PatchProxy.proxy(new Object[]{activity, bundle}, this, changeQuickRedirect, false, 146, clsArr, Void.TYPE).isSupported) {
            a(" onActivitySaveInstanceState " + activity.getClass().getSimpleName());
        }
    }

    public void onActivityDestroyed(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 147, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            String className = activity.getClass().getName();
            a("LDSActivityLifeCycle onActivityDestroyed:" + className);
            if (c.h().m(className)) {
                c.h().d(activity);
            }
        }
    }

    public static void a(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, (Object) null, changeQuickRedirect, true, Opcodes.LCMP, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("Core");
            g.a("LDSActivityLifeCycle==>" + message + "  thread=" + Thread.currentThread().getName(), new Object[0]);
        }
    }
}
