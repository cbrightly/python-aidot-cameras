package com.leedarson.smarthome.application;

import android.app.Application;
import android.app.Instrumentation;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.l;
import com.leedarson.log.elk.b;
import com.leedarson.log.h;
import com.leedarson.log.mgr.q;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SmartHomeApplication extends smarthome.lds.a {
    private static final int E4;
    private static final int F4;
    public static ChangeQuickRedirect changeQuickRedirect;
    private CountDownLatch G4 = new CountDownLatch(1);

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        E4 = availableProcessors;
        F4 = Math.max(2, Math.min(availableProcessors - 1, 4));
    }

    public void onCreate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10642, new Class[0], Void.TYPE).isSupported) {
            I();
            super.onCreate();
            C();
            J();
            timber.log.a.i("MqttLog SmartHomeApplication.onCreate", new Object[0]);
            BaseApplication.b().a1 = System.currentTimeMillis();
        }
    }

    private void I() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10643, new Class[0], Void.TYPE).isSupported) {
            w(E());
            u(com.leedarson.smarthome.a.a);
            r("APK");
            x("M071-AiDot");
            this.D4 = "https://pre-US-datalink.arnoo.com/sa?project=pre_release";
            this.C4 = "https://pre-US-log.arnoo.com/logs/report";
            this.y = "M071-AiDot";
            t("en-US");
        }
    }

    public void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10644, new Class[0], Void.TYPE).isSupported) {
            b.b().d(this);
            l.l.submit(new b(this));
            try {
                this.G4.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("reportTree", "日志打印-->" + true + ", buildConfig=" + com.leedarson.smarthome.a.a + ",isAiDotAndNeedToOpenDebugLog=" + m() + ",getRuntimeDebugLevel" + q.r().u());
            h reportTree = new h(getApplicationContext(), true);
            reportTree.S(255);
            if (timber.log.a.h() <= 0) {
                timber.log.a.f(reportTree);
            }
            if (!E()) {
                List<AbstractKit> kits = new ArrayList<>();
                kits.add(new com.leedarson.smarthome.util.a());
                DoraemonKit.install((Application) this, kits);
            }
            Looper.myQueue().addIdleHandler(a.c);
            s("2979");
            q("1.33.0");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: F */
    public /* synthetic */ void G() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10650, new Class[0], Void.TYPE).isSupported) {
            com.alibaba.android.arouter.launcher.a.d(this);
            this.G4.countDown();
        }
    }

    static /* synthetic */ boolean H() {
        return false;
    }

    public boolean E() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10645, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return "pre".equals("prod");
    }

    public void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10646, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smarthome.util.b.c(BaseApplication.b(), "1.33.0.0407171259");
            D();
            super.l();
        }
    }

    public void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10647, new Class[0], Void.TYPE).isSupported) {
            new com.leedarson.smarthome.robust.h().o();
        }
    }

    public static class a extends Instrumentation {
        public static ChangeQuickRedirect changeQuickRedirect;

        public boolean onException(Object obj, Throwable e) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, e}, this, changeQuickRedirect, false, 10651, new Class[]{Object.class, Throwable.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (Build.VERSION.SDK_INT >= 24) {
                Log.e("exception", "LDSInstrumentation  onException " + obj);
                if (e.toString().contains("DeadSystemException")) {
                    return true;
                }
            }
            return super.onException(obj, e);
        }
    }

    private void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10648, new Class[0], Void.TYPE).isSupported) {
            try {
                a ins = new a();
                Object currentAT = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentActivityThread", (Class[]) null).invoke((Object) null, (Object[]) null);
                Field mInstrumentation = currentAT.getClass().getDeclaredField("mInstrumentation");
                mInstrumentation.setAccessible(true);
                mInstrumentation.set(currentAT, ins);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 10649, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
        }
    }
}
