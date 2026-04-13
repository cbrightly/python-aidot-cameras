package com.leedarson.base.webservice.listerners;

import android.util.Log;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.v;
import com.leedarson.base.webservice.event.ServerStatusEvent;
import com.leedarson.base.webservice.server.CoreService;
import com.leedarson.base.webservice.utils.b;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.yanzhenjie.andserver.e;
import org.greenrobot.eventbus.c;
import timber.log.a;

/* compiled from: AndServerStartListernerWrap */
public class a implements e.b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean a = true;
    private boolean b = false;
    private String c = "";
    private int d = 0;
    private String e = "CoreService";
    private String f = "";

    public void b(boolean isUsing) {
        this.a = isUsing;
    }

    public void e(boolean isRestart) {
        this.b = isRestart;
    }

    public void c(String url) {
        this.c = url;
    }

    public void d(int port) {
        this.d = port;
    }

    public void f(String reason) {
        this.f = reason;
    }

    public void onStarted() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 952, new Class[0], Void.TYPE).isSupported) {
            if (this.a) {
                CoreService.d = CoreService.x;
                a.b g = timber.log.a.g("LdsCore");
                g.h("CoreService andServer onStarted,mListener=" + toString(), new Object[0]);
                b.b().l(true);
                b.b().d = false;
                com.leedarson.base.manager.a g2 = com.leedarson.base.manager.a.g();
                g2.i("onHttpServerStarted", "isRestart:" + this.b);
                v.d("CoreActivity#launchHttpServer", "HttpServer初始化");
                if (!this.b) {
                    c.c().l(new ServerStatusEvent(this.c, 1));
                    int restartCount = SharePreferenceUtils.getPrefInt(BaseApplication.b(), "restartCount", 0);
                    if (restartCount < 5) {
                        SharePreferenceUtils.setPrefInt(BaseApplication.b(), "restartCount", restartCount + 1);
                        return;
                    }
                    Log.i("Ghunt", "server--restartCount=" + restartCount);
                    return;
                }
                c.c().l(new ServerStatusEvent(this.c, 5));
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 953, new Class[0], Void.TYPE).isSupported) {
            if (this.a) {
                CoreService.d = CoreService.y;
                a.b g = timber.log.a.g("LdsCore");
                g.h("CoreService andServer onStopped,mListener=" + toString(), new Object[0]);
                b.b().l(false);
                c.c().l(new ServerStatusEvent(this.c, 2));
                LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
                if (loggerService != null) {
                    BaseApplication b2 = BaseApplication.b();
                    loggerService.reportELK(b2, "httpServer:发生了停止,bzReason=" + this.f, "info", "startHttpServer");
                }
            }
        }
    }

    public void onException(Exception e2) {
        if (!PatchProxy.proxy(new Object[]{e2}, this, changeQuickRedirect, false, 954, new Class[]{Exception.class}, Void.TYPE).isSupported) {
            if (this.a) {
                CoreService.d = CoreService.y;
                a.b g = timber.log.a.g("LdsCore");
                g.h("CoreService andServer onException:" + e2.getMessage() + ",mListener=" + toString(), new Object[0]);
                com.leedarson.base.manager.a g2 = com.leedarson.base.manager.a.g();
                StringBuilder sb = new StringBuilder();
                sb.append("err:");
                sb.append(e2.getMessage());
                g2.i("onHttpServerError", sb.toString());
                LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
                try {
                    a.b g3 = timber.log.a.g(this.e);
                    g3.c("onException getLocalizedMessage = " + e2.getLocalizedMessage(), new Object[0]);
                    if (e2.getLocalizedMessage().contains("Address already in use")) {
                        if (loggerService != null) {
                            BaseApplication b2 = BaseApplication.b();
                            loggerService.reportELK(b2, "启动httpServer端口号冲突,randomPort:" + this.d + ",CoreService:" + toString() + ", exception:" + e2.toString() + ",   sharePrefencePort=" + SharePreferenceUtils.getPrefInt(BaseApplication.b(), "serverport", 9999) + ",startReason=" + this.f, "info", "startHttpServer");
                        }
                        timber.log.a.g(this.e).c("onException = SERVER_PORT_ERROR", new Object[0]);
                        c.c().l(new ServerStatusEvent(this.c, 4));
                        return;
                    }
                    timber.log.a.g(this.e).c("onException = SERVER_ERROR", new Object[0]);
                    if (loggerService != null) {
                        BaseApplication b3 = BaseApplication.b();
                        loggerService.reportELK(b3, "httpServer运行期间出错:" + e2.toString() + ",即将进行重启...,CoreService:" + toString() + ", startReason=" + this.f, "info", "startHttpServer");
                    }
                    c.c().l(new ServerStatusEvent(this.c, 3));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (loggerService != null) {
                        BaseApplication b4 = BaseApplication.b();
                        loggerService.reportELK(b4, "httpServer:11发生了停止 e:" + ex.toString() + ",  startReason=" + this.f, "info", "startHttpServer");
                    }
                }
            }
        }
    }
}
