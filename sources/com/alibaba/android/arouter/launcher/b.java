package com.alibaba.android.arouter.launcher;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.alibaba.android.arouter.exception.HandlerException;
import com.alibaba.android.arouter.exception.InitException;
import com.alibaba.android.arouter.exception.NoRouteFoundException;
import com.alibaba.android.arouter.facade.service.AutowiredService;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.alibaba.android.arouter.facade.service.InterceptorService;
import com.alibaba.android.arouter.facade.service.PathReplaceService;
import com.alibaba.android.arouter.facade.service.PretreatmentService;
import com.alibaba.android.arouter.utils.e;
import java.util.concurrent.ThreadPoolExecutor;

/* compiled from: _ARouter */
public final class b {
    static com.alibaba.android.arouter.facade.template.b a = new com.alibaba.android.arouter.utils.b("ARouter::");
    private static volatile boolean b = false;
    private static volatile boolean c = false;
    private static volatile boolean d = false;
    private static volatile b e = null;
    private static volatile boolean f = false;
    private static volatile ThreadPoolExecutor g = com.alibaba.android.arouter.thread.b.a();
    private static Handler h;
    /* access modifiers changed from: private */
    public static Context i;
    private static InterceptorService j;

    private b() {
    }

    protected static synchronized boolean k(Application application) {
        synchronized (b.class) {
            i = application;
            com.alibaba.android.arouter.core.a.d(application, g);
            a.c("ARouter::", "ARouter init success!");
            f = true;
            h = new Handler(Looper.getMainLooper());
        }
        return true;
    }

    protected static b j() {
        if (f) {
            if (e == null) {
                synchronized (b.class) {
                    if (e == null) {
                        e = new b();
                    }
                }
            }
            return e;
        }
        throw new InitException("ARouterCore::Init::Invoke init(context) first!");
    }

    static boolean h() {
        return c;
    }

    static void l(Object thiz) {
        AutowiredService autowiredService = (AutowiredService) a.c().a("/arouter/service/autowired").C();
        if (autowiredService != null) {
            autowiredService.c(thiz);
        }
    }

    /* access modifiers changed from: protected */
    public com.alibaba.android.arouter.facade.a f(String path) {
        if (!e.b(path)) {
            PathReplaceService pService = (PathReplaceService) a.c().g(PathReplaceService.class);
            if (pService != null) {
                path = pService.f(path);
            }
            return g(path, i(path), true);
        }
        throw new HandlerException("ARouter::Parameter is invalid!");
    }

    /* access modifiers changed from: protected */
    public com.alibaba.android.arouter.facade.a g(String path, String group, Boolean afterReplace) {
        PathReplaceService pService;
        if (e.b(path) || e.b(group)) {
            throw new HandlerException("ARouter::Parameter is invalid!");
        }
        if (!afterReplace.booleanValue() && (pService = (PathReplaceService) a.c().g(PathReplaceService.class)) != null) {
            path = pService.f(path);
        }
        return new com.alibaba.android.arouter.facade.a(path, group);
    }

    private String i(String path) {
        if (e.b(path) || !path.startsWith("/")) {
            throw new HandlerException("ARouter::Extract the default group failed, the path must be start with '/' and contain more than 2 '/'!");
        }
        try {
            String defaultGroup = path.substring(1, path.indexOf("/", 1));
            if (!e.b(defaultGroup)) {
                return defaultGroup;
            }
            throw new HandlerException("ARouter::Extract the default group failed! There's nothing between 2 '/'!");
        } catch (Exception e2) {
            com.alibaba.android.arouter.facade.template.b bVar = a;
            bVar.e("ARouter::", "Failed to extract default group! " + e2.getMessage());
            return null;
        }
    }

    static void e() {
        j = (InterceptorService) a.c().a("/arouter/service/interceptor").C();
    }

    /* access modifiers changed from: protected */
    public <T> T n(Class<? extends T> service) {
        try {
            com.alibaba.android.arouter.facade.a postcard = com.alibaba.android.arouter.core.a.b(service.getName());
            if (postcard == null) {
                postcard = com.alibaba.android.arouter.core.a.b(service.getSimpleName());
            }
            if (postcard == null) {
                return null;
            }
            postcard.H(i);
            com.alibaba.android.arouter.core.a.c(postcard);
            return postcard.w();
        } catch (NoRouteFoundException ex) {
            a.e("ARouter::", ex.getMessage());
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public Object m(Context context, com.alibaba.android.arouter.facade.a postcard, int requestCode, com.alibaba.android.arouter.facade.callback.b callback) {
        PretreatmentService pretreatmentService = (PretreatmentService) a.c().g(PretreatmentService.class);
        if (pretreatmentService != null && !pretreatmentService.d(context, postcard)) {
            return null;
        }
        postcard.H(context == null ? i : context);
        try {
            com.alibaba.android.arouter.core.a.c(postcard);
            if (callback != null) {
                callback.c(postcard);
            }
            if (postcard.B()) {
                return a(postcard, requestCode, callback);
            }
            j.e(postcard, new C0013b(requestCode, callback, postcard));
            return null;
        } catch (NoRouteFoundException ex) {
            a.e("ARouter::", ex.getMessage());
            if (h()) {
                o(new a(postcard));
            }
            if (callback != null) {
                callback.a(postcard);
            } else {
                DegradeService degradeService = (DegradeService) a.c().g(DegradeService.class);
                if (degradeService != null) {
                    degradeService.b(context, postcard);
                }
            }
            return null;
        }
    }

    /* compiled from: _ARouter */
    public class a implements Runnable {
        final /* synthetic */ com.alibaba.android.arouter.facade.a c;

        a(com.alibaba.android.arouter.facade.a aVar) {
            this.c = aVar;
        }

        public void run() {
            Context b = b.i;
            Toast.makeText(b, "There's no route matched!\n Path = [" + this.c.f() + "]\n Group = [" + this.c.d() + "]", 1).show();
        }
    }

    /* renamed from: com.alibaba.android.arouter.launcher.b$b  reason: collision with other inner class name */
    /* compiled from: _ARouter */
    public class C0013b implements com.alibaba.android.arouter.facade.callback.a {
        final /* synthetic */ int a;
        final /* synthetic */ com.alibaba.android.arouter.facade.callback.b b;
        final /* synthetic */ com.alibaba.android.arouter.facade.a c;

        C0013b(int i, com.alibaba.android.arouter.facade.callback.b bVar, com.alibaba.android.arouter.facade.a aVar) {
            this.a = i;
            this.b = bVar;
            this.c = aVar;
        }

        public void a(com.alibaba.android.arouter.facade.a postcard) {
            Object unused = b.this.a(postcard, this.a, this.b);
        }

        public void b(Throwable exception) {
            com.alibaba.android.arouter.facade.callback.b bVar = this.b;
            if (bVar != null) {
                bVar.b(this.c);
            }
            com.alibaba.android.arouter.facade.template.b bVar2 = b.a;
            bVar2.c("ARouter::", "Navigation failed, termination by interceptor : " + exception.getMessage());
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0014, code lost:
        r1 = r13;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(com.alibaba.android.arouter.facade.a r14, int r15, com.alibaba.android.arouter.facade.callback.b r16) {
        /*
            r13 = this;
            android.content.Context r8 = r14.q()
            int[] r0 = com.alibaba.android.arouter.launcher.b.d.a
            com.alibaba.android.arouter.facade.enums.a r1 = r14.h()
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r9 = 0
            switch(r0) {
                case 1: goto L_0x006e;
                case 2: goto L_0x0069;
                case 3: goto L_0x0017;
                case 4: goto L_0x0017;
                case 5: goto L_0x0017;
                default: goto L_0x0014;
            }
        L_0x0014:
            r1 = r13
            goto L_0x00b0
        L_0x0017:
            java.lang.Class r1 = r14.b()
            r0 = 0
            java.lang.Class[] r2 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x0046 }
            java.lang.reflect.Constructor r2 = r1.getConstructor(r2)     // Catch:{ Exception -> 0x0046 }
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0046 }
            java.lang.Object r0 = r2.newInstance(r0)     // Catch:{ Exception -> 0x0046 }
            boolean r2 = r0 instanceof android.app.Fragment     // Catch:{ Exception -> 0x0046 }
            if (r2 == 0) goto L_0x0037
            r2 = r0
            android.app.Fragment r2 = (android.app.Fragment) r2     // Catch:{ Exception -> 0x0046 }
            android.os.Bundle r3 = r14.t()     // Catch:{ Exception -> 0x0046 }
            r2.setArguments(r3)     // Catch:{ Exception -> 0x0046 }
            goto L_0x0045
        L_0x0037:
            boolean r2 = r0 instanceof androidx.fragment.app.Fragment     // Catch:{ Exception -> 0x0046 }
            if (r2 == 0) goto L_0x0045
            r2 = r0
            androidx.fragment.app.Fragment r2 = (androidx.fragment.app.Fragment) r2     // Catch:{ Exception -> 0x0046 }
            android.os.Bundle r3 = r14.t()     // Catch:{ Exception -> 0x0046 }
            r2.setArguments(r3)     // Catch:{ Exception -> 0x0046 }
        L_0x0045:
            return r0
        L_0x0046:
            r0 = move-exception
            com.alibaba.android.arouter.facade.template.b r2 = a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Fetch fragment instance error, "
            r3.append(r4)
            java.lang.StackTraceElement[] r4 = r0.getStackTrace()
            java.lang.String r4 = com.alibaba.android.arouter.utils.e.a(r4)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "ARouter::"
            r2.d(r4, r3)
            r1 = r13
            goto L_0x00b0
        L_0x0069:
            com.alibaba.android.arouter.facade.template.c r0 = r14.w()
            return r0
        L_0x006e:
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class r1 = r14.b()
            r0.<init>(r8, r1)
            android.os.Bundle r1 = r14.t()
            r0.putExtras(r1)
            int r10 = r14.u()
            if (r10 == 0) goto L_0x0087
            r0.setFlags(r10)
        L_0x0087:
            boolean r1 = r8 instanceof android.app.Activity
            if (r1 != 0) goto L_0x0090
            r1 = 268435456(0x10000000, float:2.5243549E-29)
            r0.addFlags(r1)
        L_0x0090:
            java.lang.String r11 = r14.p()
            boolean r1 = com.alibaba.android.arouter.utils.e.b(r11)
            if (r1 != 0) goto L_0x009d
            r0.setAction(r11)
        L_0x009d:
            com.alibaba.android.arouter.launcher.b$c r12 = new com.alibaba.android.arouter.launcher.b$c
            r1 = r12
            r2 = r13
            r3 = r15
            r4 = r8
            r5 = r0
            r6 = r14
            r7 = r16
            r1.<init>(r3, r4, r5, r6, r7)
            r1 = r13
            r13.o(r12)
            return r9
        L_0x00b0:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.android.arouter.launcher.b.a(com.alibaba.android.arouter.facade.a, int, com.alibaba.android.arouter.facade.callback.b):java.lang.Object");
    }

    /* compiled from: _ARouter */
    public static /* synthetic */ class d {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[com.alibaba.android.arouter.facade.enums.a.values().length];
            a = iArr;
            try {
                iArr[com.alibaba.android.arouter.facade.enums.a.ACTIVITY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[com.alibaba.android.arouter.facade.enums.a.PROVIDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[com.alibaba.android.arouter.facade.enums.a.BOARDCAST.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[com.alibaba.android.arouter.facade.enums.a.CONTENT_PROVIDER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[com.alibaba.android.arouter.facade.enums.a.FRAGMENT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[com.alibaba.android.arouter.facade.enums.a.METHOD.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[com.alibaba.android.arouter.facade.enums.a.SERVICE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* compiled from: _ARouter */
    public class c implements Runnable {
        final /* synthetic */ int c;
        final /* synthetic */ Context d;
        final /* synthetic */ Intent f;
        final /* synthetic */ com.alibaba.android.arouter.facade.a q;
        final /* synthetic */ com.alibaba.android.arouter.facade.callback.b x;

        c(int i, Context context, Intent intent, com.alibaba.android.arouter.facade.a aVar, com.alibaba.android.arouter.facade.callback.b bVar) {
            this.c = i;
            this.d = context;
            this.f = intent;
            this.q = aVar;
            this.x = bVar;
        }

        public void run() {
            b.this.p(this.c, this.d, this.f, this.q, this.x);
        }
    }

    private void o(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            h.post(runnable);
        } else {
            runnable.run();
        }
    }

    /* access modifiers changed from: private */
    public void p(int requestCode, Context currentContext, Intent intent, com.alibaba.android.arouter.facade.a postcard, com.alibaba.android.arouter.facade.callback.b callback) {
        if (requestCode < 0) {
            ContextCompat.startActivity(currentContext, intent, postcard.v());
        } else if (currentContext instanceof Activity) {
            ActivityCompat.startActivityForResult((Activity) currentContext, intent, requestCode, postcard.v());
        } else {
            a.e("ARouter::", "Must use [navigation(activity, ...)] to support [startActivityForResult]");
        }
        if (!(-1 == postcard.r() || -1 == postcard.s() || !(currentContext instanceof Activity))) {
            ((Activity) currentContext).overridePendingTransition(postcard.r(), postcard.s());
        }
        if (callback != null) {
            callback.d(postcard);
        }
    }
}
