package com.leedarson.base.manager;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.BuildConfig;
import com.leedarson.base.utils.v;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.IOException;
import meshsdk.ctrl.GroupCtrlAdapter;
import okhttp3.d0;
import okhttp3.e;
import okhttp3.f;
import org.json.JSONArray;
import timber.log.a;

/* compiled from: LoadErrorManager */
public class a {
    private static a a = null;
    public static ChangeQuickRedirect changeQuickRedirect;
    private String b = "onCreate";
    private String c;
    /* access modifiers changed from: private */
    public d d;
    /* access modifiers changed from: private */
    public String e;
    private Handler f = new C0081a(Looper.getMainLooper());
    JSONArray g = new JSONArray();

    /* compiled from: LoadErrorManager */
    public interface c {
        void a(String str, boolean z, int i);
    }

    /* compiled from: LoadErrorManager */
    public interface d {
        void a();

        void b(String str);
    }

    /* renamed from: com.leedarson.base.manager.a$a  reason: collision with other inner class name */
    /* compiled from: LoadErrorManager */
    public class C0081a extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0081a(Looper looper) {
            super(looper);
        }

        public void handleMessage(@NonNull Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 319, new Class[]{Message.class}, Void.TYPE).isSupported) {
                super.handleMessage(msg);
                int i = msg.what;
                if (i == 0) {
                    timber.log.a.g("CoreActivity").c("didRender time out,load error h5!!!!", new Object[0]);
                    v.a();
                    String url = msg.obj.toString();
                    if (a.this.d != null) {
                        a.this.d.b(url);
                    }
                } else if (i == 1 && a.this.d != null) {
                    a.this.d.a();
                }
            }
        }
    }

    public static a g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 311, new Class[0], a.class);
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

    public String e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 312, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "currentState:" + this.b + ",ex:" + this.c;
    }

    public JSONArray f() {
        return this.g;
    }

    public void i(String currentState, String extra) {
        this.b = currentState;
        this.c = extra;
    }

    public void j(String currentUrl) {
        if (!PatchProxy.proxy(new Object[]{currentUrl}, this, changeQuickRedirect, false, 313, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.e = currentUrl;
            c();
            timber.log.a.g("CoreActivity").a("run didRender Timeout Task", new Object[0]);
            Message obtain = Message.obtain(this.f);
            obtain.obj = currentUrl;
            obtain.what = 0;
            this.f.sendMessageDelayed(obtain, (long) (Build.VERSION.SDK_INT <= 23 ? 40000 : GroupCtrlAdapter.TIMEOUT_WAIT_MESH_ONLINE));
        }
    }

    public void h(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 314, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.g.put((Object) msg);
        }
    }

    public void setOnLoadErrorListener(d onLoadErrorListener) {
        this.d = onLoadErrorListener;
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 315, new Class[0], Void.TYPE).isSupported) {
            this.f.removeMessages(0);
            this.f.sendEmptyMessage(1);
            for (int i = 0; i < this.g.length(); i++) {
                this.g.remove(i);
            }
        }
    }

    /* compiled from: LoadErrorManager */
    public class b implements f {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ c c;

        b(c cVar) {
            this.c = cVar;
        }

        public void onFailure(e eVar, IOException e) {
            if (!PatchProxy.proxy(new Object[]{eVar, e}, this, changeQuickRedirect, false, BuildConfig.VERSION_CODE, new Class[]{e.class, IOException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("CoreActivity");
                g.a("httpServer 运行异常!!!! err:" + e.getMessage(), new Object[0]);
                c cVar = this.c;
                if (cVar != null) {
                    cVar.a(a.this.e + "httpServer 运行异常!!!! err:" + e.getMessage(), false, -1);
                }
            }
        }

        public void onResponse(e eVar, d0 response) {
            if (!PatchProxy.proxy(new Object[]{eVar, response}, this, changeQuickRedirect, false, 321, new Class[]{e.class, d0.class}, Void.TYPE).isSupported) {
                if (response.j() == 200) {
                    timber.log.a.g("CoreActivity").a("httpServer 运行正常 ", new Object[0]);
                    c cVar = this.c;
                    if (cVar != null) {
                        cVar.a(a.this.e + ",verify 成功，httpServer 运行正常 ,result:", true, 200);
                        return;
                    }
                    return;
                }
                timber.log.a.g("CoreActivity").c(",httpServer 运行异常!!!!", new Object[0]);
                c cVar2 = this.c;
                if (cVar2 != null) {
                    cVar2.a(a.this.e + ",httpServer 运行异常!!!! response:" + response.toString(), false, response.j());
                }
            }
        }
    }

    public void d(Activity activity, c listener) {
        Class[] clsArr = {Activity.class, c.class};
        if (!PatchProxy.proxy(new Object[]{activity, listener}, this, changeQuickRedirect, false, TypedValues.AttributesType.TYPE_PATH_ROTATE, clsArr, Void.TYPE).isSupported) {
            com.leedarson.base.webservice.utils.b b2 = com.leedarson.base.webservice.utils.b.b();
            b2.m(activity, this.e + "/index.html", new b(listener));
        }
    }
}
