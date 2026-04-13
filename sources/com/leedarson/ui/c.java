package com.leedarson.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.leedarson.R$string;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.utils.r;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.TcpService;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.listener.OnGetRecordListener;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.DownloadVideoUtils;
import com.leedarson.serviceinterface.utils.FileUtils;
import com.leedarson.serviceinterface.utils.IntentUtils;
import com.leedarson.serviceinterface.utils.PlayBackCacheUtils;
import com.leedarson.utils.j;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import okhttp3.e0;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: CloudPresenter */
public class c extends com.leedarson.base.presenters.a<d, CloudFragment> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private io.reactivex.disposables.b A;
    String B;
    int C = 17;
    int D = 0;
    private boolean E = false;
    private com.leedarson.smartcamera.codec.c F;
    private ExecutorService G;
    private Future H;
    private boolean f = true;
    private boolean g = false;
    private boolean h = false;
    private JsonArray i;
    private boolean j = false;
    /* access modifiers changed from: private */
    public ExecutorService k;
    private String l;
    private String m;
    private int n = 0;
    private long o = 0;
    /* access modifiers changed from: private */
    public long p;
    private int q = 0;
    /* access modifiers changed from: private */
    public int r = 0;
    private long s;
    private boolean t = false;
    private Timer u;
    private String v = "";
    /* access modifiers changed from: private */
    public int w;
    private int x = 100;
    /* access modifiers changed from: private */
    public Future y;
    private OnGetRecordListener z;

    static /* synthetic */ void A(c x0, String x1) {
        Class[] clsArr = {c.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10957, clsArr, Void.TYPE).isSupported) {
            x0.F(x1);
        }
    }

    static /* synthetic */ void C(c x0, boolean x1, String x2, String x3, String x4) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0), x2, x3, x4}, (Object) null, changeQuickRedirect, true, 10958, new Class[]{c.class, Boolean.TYPE, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.I(x1, x2, x3, x4);
        }
    }

    static /* synthetic */ void s(c x0, List x1) {
        Class[] clsArr = {c.class, List.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10954, clsArr, Void.TYPE).isSupported) {
            x0.E(x1);
        }
    }

    static /* synthetic */ void t(c x0, byte[] x1, String x2) {
        Class[] clsArr = {c.class, byte[].class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 10959, clsArr, Void.TYPE).isSupported) {
            x0.H(x1, x2);
        }
    }

    static /* synthetic */ int x(c x0) {
        int i2 = x0.w;
        x0.w = i2 + 1;
        return i2;
    }

    static /* synthetic */ void y(c x0, String x1) {
        Class[] clsArr = {c.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10955, clsArr, Void.TYPE).isSupported) {
            x0.N(x1);
        }
    }

    static /* synthetic */ void z(c x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {c.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 10956, clsArr, Void.TYPE).isSupported) {
            x0.D(x1, x2);
        }
    }

    public c(d view, CloudFragment fragment) {
        super(view, fragment);
        ThreadFactory namedThreadFactory = new r("cloudp-pool");
        this.k = Executors.newSingleThreadExecutor(namedThreadFactory);
        this.G = Executors.newSingleThreadExecutor(namedThreadFactory);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10929(0x2ab1, float:1.5315E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.u
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.u = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.c.c0():void");
    }

    public void Y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10930, new Class[0], Void.TYPE).isSupported) {
            c0();
        }
    }

    public void O() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10931, new Class[0], Void.TYPE).isSupported) {
            Z();
            if (l() != null && ((CloudFragment) l()).t3() != null) {
                ((CloudFragment) l()).t3().I();
            }
        }
    }

    public void Z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10932, new Class[0], Void.TYPE).isSupported) {
            this.f = true;
            com.leedarson.manager.b.h().o(true);
            com.leedarson.manager.b.h().p();
            TcpService tcpService = (TcpService) com.alibaba.android.arouter.launcher.a.c().g(TcpService.class);
            if (tcpService != null) {
                tcpService.disConnect();
            }
        }
    }

    public void M(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 10933, new Class[]{String.class}, Void.TYPE).isSupported) {
            String recordJsonData = str;
            try {
                com.leedarson.manager.b.h().k(false);
                this.g = false;
                this.j = false;
                this.f = true;
                this.h = false;
                Future future = this.y;
                if (future != null && !future.isCancelled()) {
                    this.y.cancel(true);
                }
                T();
                this.f = false;
                com.leedarson.manager.b.h().o(false);
                P();
                S();
                com.leedarson.manager.b.h().g();
                com.leedarson.manager.b.h().j();
                try {
                    JsonParser parser = new JsonParser();
                    JSONObject aa = new JSONObject(recordJsonData);
                    this.i = parser.parse(aa.get("times").toString()).getAsJsonArray();
                    this.l = aa.getString("sessionId");
                    if (aa.has("eventUuid")) {
                        this.m = aa.getString("eventUuid");
                    }
                    a.b g2 = timber.log.a.g("CloudPresenter");
                    g2.h("onSuccess=== play:" + this.i.size(), new Object[0]);
                    this.n = 0;
                    this.o = 0;
                    if (this.i.size() > 0) {
                        JSONObject time = new JSONObject(this.i.get(0).toString());
                        TcpService tcpService = (TcpService) com.alibaba.android.arouter.launcher.a.c().g(TcpService.class);
                        if (tcpService != null) {
                            this.p = time.getLong("sta");
                            timber.log.a.g("CloudPresenter").h("getRecordStream 1", new Object[0]);
                            TcpService tcpService2 = tcpService;
                            tcpService2.getRecordStream(this.l, this.p / 1000, 0, this.x, 1, this.z);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "current_devid", ""));
                            jsonObject.put("desc", (Object) "");
                            jsonObject.put("messageCode", 10001);
                            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                        }
                        JsonArray jsonArray = this.i;
                        this.q = (int) ((new JSONObject(jsonArray.get(jsonArray.size() - 1).toString()).getLong("end") / 1000) - (this.p / 1000));
                        ((d) m()).q0(this.q);
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public void P() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10934, new Class[0], Void.TYPE).isSupported) {
            this.g = false;
            if (!(l() == null || ((CloudFragment) l()).t3() == null)) {
                ((CloudFragment) l()).t3().J();
            }
            com.leedarson.manager.b.h().n();
            S();
        }
    }

    public void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10935, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smartcamera.utils.e.c("pausePlay", "");
            this.g = true;
            com.leedarson.manager.b.h().m();
            if (!(l() == null || ((CloudFragment) l()).t3() == null)) {
                ((CloudFragment) l()).t3().G();
            }
            R();
        }
    }

    public void a0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10936, new Class[0], Void.TYPE).isSupported) {
            this.f = true;
            com.leedarson.manager.b.h().o(true);
            com.leedarson.manager.b.h().q();
            com.leedarson.manager.b.h().r();
            Future future = this.y;
            if (future != null && !future.isCancelled()) {
                this.y.cancel(true);
            }
        }
    }

    /* compiled from: CloudPresenter */
    public class a implements OnGetRecordListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onSuccess(int code, List<byte[]> data) {
            Object[] objArr = {new Integer(code), data};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10960, new Class[]{Integer.TYPE, List.class}, Void.TYPE).isSupported) {
                int unused = c.this.r = code;
                Callable callable = new C0192a(data);
                c cVar = c.this;
                Future unused2 = cVar.y = cVar.k.submit(callable);
            }
        }

        /* renamed from: com.leedarson.ui.c$a$a  reason: collision with other inner class name */
        /* compiled from: CloudPresenter */
        public class C0192a implements Callable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ List c;

            C0192a(List list) {
                this.c = list;
            }

            public Object call() {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10964, new Class[0], Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                c.s(c.this, this.c);
                return null;
            }
        }

        public void onFailure(int i, String str) {
            Object[] objArr = {new Integer(i), str};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10961, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                ((d) c.this.m()).p();
            }
        }

        public void onException(Throwable th) {
            if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 10962, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                ((d) c.this.m()).p();
            }
        }

        public void onGetCacheTimestamp(long cacheTimestamp) {
            Object[] objArr = {new Long(cacheTimestamp)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10963, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
                ((d) c.this.m()).p();
                ((d) c.this.m()).P0(cacheTimestamp);
            }
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10937, new Class[0], Void.TYPE).isSupported) {
            this.z = null;
            this.z = new a();
        }
    }

    public void W(String deviceName) {
        if (!PatchProxy.proxy(new Object[]{deviceName}, this, changeQuickRedirect, false, 10938, new Class[]{String.class}, Void.TYPE).isSupported) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            this.v = com.leedarson.smartcamera.utils.f.b(((CloudFragment) l()).getContext()) + (deviceName + "_" + formatter.format(new Date()) + ".mp4");
            if (l() != null && ((CloudFragment) l()).t3() != null) {
                if (((CloudFragment) l()).t3().R(this.v) == 0) {
                    c0();
                    Timer timer = new Timer();
                    this.u = timer;
                    this.w = 0;
                    timer.schedule(new b(), 100, 1000);
                    ((d) m()).d();
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "current_devid", ""));
                        jsonObject.put("desc", (Object) "");
                        jsonObject.put("messageCode", 10005);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("deviceId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "current_devid", ""));
                        jsonObject2.put("desc", (Object) "");
                        jsonObject2.put("messageCode", 10006);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
        }
    }

    /* compiled from: CloudPresenter */
    public class b extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10965, new Class[0], Void.TYPE).isSupported) {
                if (c.this.m() != null) {
                    c.x(c.this);
                    ((d) c.this.m()).e(c.this.w);
                    a.b g = timber.log.a.g("CloudPresenter");
                    g.h("startRecord:" + c.this.w, new Object[0]);
                }
            }
        }
    }

    public void b0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10939, new Class[0], Void.TYPE).isSupported) {
            if (l() != null && ((CloudFragment) l()).t3() != null) {
                int result = ((CloudFragment) l()).t3().W();
                c0();
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "current_devid", ""));
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", 10006);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (this.w < 3) {
                    FileUtils.deleteFile(this.v);
                    ((d) m()).showToast(R$string.player_videotape_fail);
                    this.w = 0;
                    ((d) m()).c();
                    return;
                }
                this.w = 0;
                if (result == 0) {
                    ((d) m()).c();
                    try {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("deviceId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "current_devid", ""));
                        jsonObject2.put("desc", (Object) "");
                        jsonObject2.put("messageCode", 10007);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                        ((CloudFragment) l()).getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.v))));
                        ((d) m()).showToast(R$string.player_videotape_sucess);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } else {
                    ((d) m()).showToast(R$string.player_videotape_fail);
                }
            }
        }
    }

    public long G() {
        return this.p;
    }

    public void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10940, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "current_devid", ""));
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10002);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void S() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10941, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "current_devid", ""));
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10001);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void V(String path, int saveStatus) {
        if (!PatchProxy.proxy(new Object[]{path, new Integer(saveStatus)}, this, changeQuickRedirect, false, 10942, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                if (saveStatus == 2) {
                    this.B = com.leedarson.smartcamera.utils.f.b(((CloudFragment) l()).getContext()) + (path + "_" + formatter.format(new Date()) + ".jpg");
                } else if (saveStatus == 1) {
                    this.B = ((CloudFragment) l()).getContext().getFilesDir().getPath() + "/web/" + path.replace("build", "") + ".jpg";
                    a.b g2 = timber.log.a.g("CloudPresenter");
                    StringBuilder sb = new StringBuilder();
                    sb.append("path:");
                    sb.append(this.B);
                    g2.h(sb.toString(), new Object[0]);
                }
                File dirFile = new File(this.B).getParentFile();
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                ((CloudFragment) l()).t3().M(this.B, new C0193c(saveStatus));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.leedarson.ui.c$c  reason: collision with other inner class name */
    /* compiled from: CloudPresenter */
    public class C0193c implements com.leedarson.smartcamera.codec.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        C0193c(int i) {
            this.a = i;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10966, new Class[0], Void.TYPE).isSupported) {
                if (this.a == 2) {
                    ((d) c.this.m()).f(c.this.B);
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) c.this.l()).getContext(), "current_devid", ""));
                    jsonObject.put("desc", (Object) "");
                    if (this.a == 3) {
                        String base64 = j.e(c.this.B);
                        JSONObject dataObj = new JSONObject();
                        dataObj.put("imageData", (Object) base64);
                        jsonObject.put("data", (Object) dataObj);
                    }
                    jsonObject.put("messageCode", 10004);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10967, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (this.a == 2) {
                    ((d) c.this.m()).showToast(R$string.player_screenshot_fail);
                }
            }
        }
    }

    public void K() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10943, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "current_devid", ""));
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10009);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void L() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10944, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "current_devid", ""));
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10008);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void X() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10945, new Class[0], Void.TYPE).isSupported) {
            this.f = true;
            this.j = true;
            this.t = true;
            com.leedarson.manager.b.h().o(true);
            com.leedarson.manager.b.h().p();
            Future future = this.y;
            if (future != null && !future.isCancelled()) {
                this.y.cancel(true);
            }
            if (l() != null && ((CloudFragment) l()).t3() != null) {
                ((CloudFragment) l()).t3().A();
            }
        }
    }

    public void Q(long j2, int i2) {
        if (!PatchProxy.proxy(new Object[]{new Long(j2), new Integer(i2)}, this, changeQuickRedirect, false, 10946, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            long time = j2;
            int seekSec = i2;
            try {
                com.leedarson.manager.b.h().k(false);
                this.f = true;
                this.t = true;
                a.b g2 = timber.log.a.g("CloudPresenter");
                g2.h("getRecordStream seekPlay:" + time, new Object[0]);
                com.leedarson.manager.b.h().o(true);
                com.leedarson.manager.b.h().p();
                this.j = true;
                T();
                this.f = false;
                com.leedarson.manager.b.h().o(false);
                P();
                com.leedarson.manager.b.h().g();
                com.leedarson.manager.b.h().j();
                TcpService tcpService = (TcpService) com.alibaba.android.arouter.launcher.a.c().g(TcpService.class);
                if (tcpService != null) {
                    this.s = ((long) seekSec) + time;
                    a.b g3 = timber.log.a.g("CloudPresenter");
                    g3.h("getRecordStream seekPlay2:" + this.s, new Object[0]);
                    try {
                        long j3 = time;
                        try {
                            tcpService.getSeekStream(this.l, time, seekSec, 0, this.x, 1, this.z);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "current_devid", ""));
                            jsonObject.put("desc", (Object) "");
                            jsonObject.put("messageCode", 10001);
                            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                        } catch (JSONException e2) {
                            e = e2;
                        }
                    } catch (JSONException e3) {
                        e = e3;
                        long j4 = time;
                        try {
                            e.printStackTrace();
                        } catch (Exception e4) {
                            e = e4;
                        }
                    }
                }
            } catch (Exception e5) {
                e = e5;
                long j5 = time;
                e.printStackTrace();
            }
        }
    }

    private void E(List<byte[]> list) {
        Class cls = TcpService.class;
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 10947, new Class[]{List.class}, Void.TYPE).isSupported) {
            List<byte[]> bytes = list;
            if (!this.f) {
                int i2 = this.r;
                if (i2 == 200) {
                    if (bytes != null) {
                        if (!this.h) {
                            this.h = true;
                            ((d) m()).W();
                        }
                        int i3 = 0;
                        while (i3 < bytes.size()) {
                            try {
                                com.leedarson.smartcamera.utils.g.d().h(bytes.get(i3));
                                i3++;
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                        }
                        timber.log.a.g("CloudPresenter").h("onSuccess=== d2:" + bytes.size(), new Object[0]);
                        TcpService tcpService = (TcpService) com.alibaba.android.arouter.launcher.a.c().g(cls);
                        if (tcpService != null) {
                            timber.log.a.g("CloudPresenter").h(" 2 " + this.j + "==" + this.f, new Object[0]);
                            if (!this.f) {
                                if (this.j) {
                                    tcpService.getSeekStream(this.l, this.s, 1, this.x, 1, this.z);
                                } else {
                                    tcpService.getRecordStream(this.l, this.p / 1000, 1, this.x, 1, this.z);
                                }
                            }
                        }
                    }
                } else if (i2 == -15528) {
                    if (this.n < this.i.size() - 1) {
                        this.n++;
                        try {
                            JSONObject time = new JSONObject(this.i.get(this.n).toString());
                            TcpService tcpService2 = (TcpService) com.alibaba.android.arouter.launcher.a.c().g(cls);
                            if (tcpService2 != null) {
                                this.p = time.getLong("sta");
                                if (!this.f) {
                                    if (this.j) {
                                        tcpService2.getSeekStream(this.l, this.s, 0, this.x, 1, this.z);
                                    } else {
                                        timber.log.a.g("CloudPresenter").h("getRecordStream 3", new Object[0]);
                                        tcpService2.getRecordStream(this.l, this.p / 1000, 0, this.x, 1, this.z);
                                    }
                                }
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    } else {
                        timber.log.a.g("CloudPresenter").h("getRecordStream end! ", new Object[0]);
                        com.leedarson.manager.b.h().k(true);
                        this.f = true;
                    }
                } else if (i2 == 300) {
                    timber.log.a.g("CloudPresenter").h("------------------------------exit decode", new Object[0]);
                    ((d) m()).O0();
                    if (!this.h) {
                        this.h = true;
                        ((d) m()).W();
                    }
                    this.t = false;
                    if (bytes != null) {
                        try {
                            com.leedarson.smartcamera.utils.g.d().h(bytes.get(0));
                        } catch (InterruptedException e4) {
                            e4.printStackTrace();
                            return;
                        }
                    }
                    timber.log.a.g("CloudPresenter").h("------------------------------exit 111111=" + this.t, new Object[0]);
                    if (!this.t) {
                        if (this.n < this.i.size() - 1) {
                            this.n++;
                            try {
                                JSONObject time2 = new JSONObject(this.i.get(this.n).toString());
                                TcpService tcpService3 = (TcpService) com.alibaba.android.arouter.launcher.a.c().g(cls);
                                if (tcpService3 != null) {
                                    this.p = time2.getLong("sta");
                                    timber.log.a.g("CloudPresenter").h("getRecordStream 4", new Object[0]);
                                    tcpService3.getRecordStream(this.l, this.p / 1000, 0, this.x, 1, this.z);
                                }
                            } catch (Exception e5) {
                                e5.printStackTrace();
                            }
                        } else {
                            timber.log.a.g("CloudPresenter").h("getRecordStream end!", new Object[0]);
                            com.leedarson.manager.b.h().k(true);
                            this.f = true;
                            this.t = true;
                        }
                    }
                }
                while (this.g) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e6) {
                        e6.printStackTrace();
                        return;
                    }
                }
            }
        }
    }

    public void U(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 10948, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                if (!com.alibaba.android.arouter.utils.e.b(this.m)) {
                    Context context = ((CloudFragment) l()).getContext();
                    if (DownloadVideoUtils.ismp4FileExit(context, deviceId, (this.p / 1000) + "")) {
                        Context context2 = ((CloudFragment) l()).getContext();
                        Context context3 = ((CloudFragment) l()).getContext();
                        IntentUtils.shareVideo(context2, DownloadVideoUtils.getmp4File(context3, deviceId, (this.p / 1000) + ""), "");
                        return;
                    }
                    ((d) m()).b();
                    Context context4 = ((CloudFragment) l()).getContext();
                    if (PlayBackCacheUtils.isCacheFileExit(context4, deviceId, (this.p / 1000) + "")) {
                        Context context5 = ((CloudFragment) l()).getContext();
                        String savePath = DownloadVideoUtils.getSavePath(context5, deviceId, (this.p / 1000) + "");
                        I(true, deviceId, (this.p / 1000) + "", savePath);
                        return;
                    }
                    F(deviceId);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void F(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 10949, new Class[]{String.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            String baseUrl = SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            String accessToken = SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "accessToken", "");
            String owner = SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "owner", "");
            try {
                if (!TextUtils.isEmpty(accessToken)) {
                    headerJson.put("token", (Object) accessToken);
                }
                if (!TextUtils.isEmpty(owner)) {
                    headerJson.put("owner", (Object) owner);
                }
                headerJson.put("terminal", (Object) "app");
                paramsJson.put("eventUuids", (Object) this.m);
                paramsJson.put("deviceId", (Object) deviceId);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            timber.log.a.g("getDownloadUrl").h(headerJson.toString(), new Object[0]);
            timber.log.a.g("getDownloadUrl").h(paramsJson.toString(), new Object[0]);
            String url = baseUrl + "/api/ipc/playbackController/queryEventVideos";
            timber.log.a.g("getDownloadUrl").h("getAdvertInfo:request= " + url, new Object[0]);
            b0.b().K(((CloudFragment) l()).getContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new d(deviceId));
        }
    }

    /* compiled from: CloudPresenter */
    public class d extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        d(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 10971, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 10968, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getDownloadUrl").a("getDownloadUrl", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 10969, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getDownloadUrl");
                g.c("error=" + e.getMsg(), new Object[0]);
                if (e.getCode() == 21026) {
                    c.y(c.this, this.c);
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 10970, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getDownloadUrl").c(response, new Object[0]);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() > 0) {
                        String downloadUrl = jsonArray.getString(0);
                        a.b g = timber.log.a.g("getDownloadUrl");
                        g.c("url=" + downloadUrl, new Object[0]);
                        c.z(c.this, downloadUrl, this.c);
                        return;
                    }
                    ((d) c.this.m()).a();
                    ((d) c.this.m()).showToast(R$string.share_failed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void N(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 10950, new Class[]{String.class}, Void.TYPE).isSupported) {
            String base_url = SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "httpServer", "");
            JSONObject msgObj = new JSONObject();
            try {
                msgObj.put("refreshToken", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "refreshToken", ""));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            JSONObject headerJson = new JSONObject();
            String accessToken = SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "accessToken", "");
            try {
                headerJson.put("appId", (Object) SharePreferenceUtils.getPrefString(((CloudFragment) l()).getContext(), "APP_ID", ""));
                if (!TextUtils.isEmpty(accessToken)) {
                    headerJson.put("token", (Object) accessToken);
                }
                headerJson.put("terminal", (Object) "app");
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            b0 b2 = b0.b();
            Context context = ((CloudFragment) l()).getContext();
            b2.K(context, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, base_url + "/user/refreshUserToken", headerJson.toString(), msgObj.toString(), new e(deviceId));
        }
    }

    /* compiled from: CloudPresenter */
    public class e extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        e(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 10974, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException apiException) {
            if (!PatchProxy.proxy(new Object[]{apiException}, this, changeQuickRedirect, false, 10972, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                timber.log.a.g("CloudPresenter").h("refreshToke onError: ", new Object[0]);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 10973, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("CloudPresenter");
                g.h("refreshToke onSuccess: " + response, new Object[0]);
                try {
                    JSONObject dataObj = new JSONObject(response).getJSONObject("data");
                    if (dataObj.has("accessToken")) {
                        SharePreferenceUtils.setPrefString(((CloudFragment) c.this.l()).getContext(), "accessToken", dataObj.getString("accessToken"));
                    }
                    if (dataObj.has("refreshToken")) {
                        SharePreferenceUtils.setPrefString(((CloudFragment) c.this.l()).getContext(), "refreshToken", dataObj.getString("refreshToken"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                c.A(c.this, this.c);
            }
        }
    }

    private void D(String url, String deviceId) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{url, deviceId}, this, changeQuickRedirect, false, 10951, clsArr, Void.TYPE).isSupported) {
            Log.e("CloudPresenter", "downloadRecord: " + url);
            io.reactivex.disposables.b bVar = this.A;
            if (bVar != null) {
                bVar.dispose();
            }
            this.A = b0.b().a(((CloudFragment) l()).getContext(), (com.trello.rxlifecycle3.b) ((CloudFragment) l()).getActivity(), url, new f(deviceId));
        }
    }

    /* compiled from: CloudPresenter */
    public class f extends com.leedarson.base.http.observer.i<e0> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        f(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 10978, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((e0) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 10975, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("downloadRecord").c("onStart", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 10976, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("downloadRecord");
                g.c("downloadFile onError:" + e.toString(), new Object[0]);
                ((d) c.this.m()).a();
                ((d) c.this.m()).showToast(R$string.share_failed);
            }
        }

        public void a(e0 response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 10977, new Class[]{e0.class}, Void.TYPE).isSupported) {
                long time = c.this.p / 1000;
                a.b g = timber.log.a.g("downloadRecord");
                g.c("onSuccess:deviceId=" + this.c + " staTime=" + time, new Object[0]);
                try {
                    Context context = ((CloudFragment) c.this.l()).getContext();
                    String str = this.c;
                    boolean d2 = DownloadVideoUtils.writeDownloadFile(context, str, time + "", response.byteStream());
                    a.b g2 = timber.log.a.g("downloadRecord");
                    g2.c("onSuccess: " + d2, new Object[0]);
                    if (d2) {
                        Context context2 = ((CloudFragment) c.this.l()).getContext();
                        String str2 = this.c;
                        String savePath = DownloadVideoUtils.getSavePath(context2, str2, time + "");
                        c cVar = c.this;
                        String str3 = this.c;
                        c.C(cVar, false, str3, time + "", savePath);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a4 A[Catch:{ Exception -> 0x0193 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0161 A[Catch:{ Exception -> 0x0191 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void H(byte[] r22, java.lang.String r23) {
        /*
            r21 = this;
            java.lang.String r0 = "CloudPresenter"
            r1 = 2
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r22
            r10 = 1
            r2[r10] = r23
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class<byte[]> r3 = byte[].class
            r7[r9] = r3
            java.lang.Class<java.lang.String> r3 = java.lang.String.class
            r7[r10] = r3
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 10952(0x2ac8, float:1.5347E-41)
            r3 = r21
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x0027
            return
        L_0x0027:
            r2 = r21
            r3 = r23
            r4 = r22
            r2.E = r10
            com.leedarson.smartcamera.codec.c r5 = new com.leedarson.smartcamera.codec.c     // Catch:{ Exception -> 0x0193 }
            r5.<init>()     // Catch:{ Exception -> 0x0193 }
            r2.F = r5     // Catch:{ Exception -> 0x0193 }
            r5.Q(r3)     // Catch:{ Exception -> 0x0193 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0193 }
            r5.<init>()     // Catch:{ Exception -> 0x0193 }
            java.lang.String r6 = "muxerMP4: "
            r5.append(r6)     // Catch:{ Exception -> 0x0193 }
            r5.append(r3)     // Catch:{ Exception -> 0x0193 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0193 }
            android.util.Log.e(r0, r5)     // Catch:{ Exception -> 0x0193 }
            r5 = 0
        L_0x004e:
            int r6 = r4.length     // Catch:{ Exception -> 0x0193 }
            int r6 = r6 - r5
            r7 = 17
            if (r6 < r7) goto L_0x0171
            boolean r6 = r2.E     // Catch:{ Exception -> 0x0193 }
            if (r6 == 0) goto L_0x0171
            byte r6 = r4[r5]     // Catch:{ Exception -> 0x0193 }
            if (r6 != r10) goto L_0x0072
            int r6 = r5 + 1
            byte r6 = r4[r6]     // Catch:{ Exception -> 0x006d }
            if (r6 != r10) goto L_0x0072
            r6 = 22
            r2.C = r6     // Catch:{ Exception -> 0x006d }
            int r6 = r5 + 17
            byte r6 = r4[r6]     // Catch:{ Exception -> 0x006d }
            r2.D = r6     // Catch:{ Exception -> 0x006d }
            goto L_0x0076
        L_0x006d:
            r0 = move-exception
            r22 = r4
            goto L_0x0196
        L_0x0072:
            r2.D = r9     // Catch:{ Exception -> 0x0193 }
            r2.C = r7     // Catch:{ Exception -> 0x0193 }
        L_0x0076:
            int r6 = r5 + 2
            byte r6 = r4[r6]     // Catch:{ Exception -> 0x0193 }
            int r7 = r5 + 3
            byte r7 = r4[r7]     // Catch:{ Exception -> 0x0193 }
            r8 = 8
            byte[] r11 = new byte[r8]     // Catch:{ Exception -> 0x0193 }
            int r12 = r5 + 4
            java.lang.System.arraycopy(r4, r12, r11, r9, r8)     // Catch:{ Exception -> 0x0193 }
            long r12 = com.leedarson.utils.j.d(r11)     // Catch:{ Exception -> 0x0193 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0193 }
            r8.<init>()     // Catch:{ Exception -> 0x0193 }
            r8.append(r12)     // Catch:{ Exception -> 0x0193 }
            java.lang.String r14 = ""
            r8.append(r14)     // Catch:{ Exception -> 0x0193 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0193 }
            int r8 = r8.length()     // Catch:{ Exception -> 0x0193 }
            int r8 = 13 - r8
            if (r8 <= 0) goto L_0x00ad
            r14 = 0
        L_0x00a5:
            if (r14 >= r8) goto L_0x00ad
            r15 = 10
            long r12 = r12 * r15
            int r14 = r14 + 1
            goto L_0x00a5
        L_0x00ad:
            int r14 = r5 + 12
            byte r14 = r4[r14]     // Catch:{ Exception -> 0x0193 }
            r15 = 4
            byte[] r1 = new byte[r15]     // Catch:{ Exception -> 0x0193 }
            int r10 = r5 + 13
            java.lang.System.arraycopy(r4, r10, r1, r9, r15)     // Catch:{ Exception -> 0x0193 }
            int r10 = com.leedarson.utils.j.c(r1)     // Catch:{ Exception -> 0x0193 }
            if (r14 != 0) goto L_0x0161
            r15 = 5
            if (r6 != r15) goto L_0x00fa
            byte[] r15 = new byte[r10]     // Catch:{ Exception -> 0x006d }
            int r9 = r2.C     // Catch:{ Exception -> 0x006d }
            int r9 = r9 + r5
            r23 = r1
            r1 = 0
            java.lang.System.arraycopy(r4, r9, r15, r1, r10)     // Catch:{ Exception -> 0x006d }
            if (r7 != 0) goto L_0x00e1
            com.leedarson.smartcamera.codec.c r1 = r2.F     // Catch:{ Exception -> 0x006d }
            int r9 = r15.length     // Catch:{ Exception -> 0x006d }
            r20 = 1
            r22 = r15
            r15 = r1
            r16 = r12
            r18 = r22
            r19 = r9
            r15.a0(r16, r18, r19, r20)     // Catch:{ Exception -> 0x006d }
            goto L_0x0106
        L_0x00e1:
            r22 = r15
            r1 = 1
            if (r7 != r1) goto L_0x00f7
            com.leedarson.smartcamera.codec.c r15 = r2.F     // Catch:{ Exception -> 0x006d }
            r1 = r22
            int r9 = r1.length     // Catch:{ Exception -> 0x006d }
            r20 = 2
            r16 = r12
            r18 = r1
            r19 = r9
            r15.a0(r16, r18, r19, r20)     // Catch:{ Exception -> 0x006d }
            goto L_0x0106
        L_0x00f7:
            r1 = r22
            goto L_0x0106
        L_0x00fa:
            r23 = r1
            r1 = 2
            if (r6 == r1) goto L_0x010a
            r9 = 3
            if (r6 == r9) goto L_0x010a
            r9 = 4
            if (r6 != r9) goto L_0x0106
            goto L_0x010a
        L_0x0106:
            r22 = r4
            r4 = 1
            goto L_0x0166
        L_0x010a:
            int r9 = r4.length     // Catch:{ Exception -> 0x0193 }
            int r9 = r9 - r5
            int r15 = r2.C     // Catch:{ Exception -> 0x0193 }
            int r1 = r10 + r15
            if (r9 < r1) goto L_0x015d
            byte[] r1 = new byte[r10]     // Catch:{ Exception -> 0x0193 }
            int r15 = r15 + r5
            r9 = 0
            java.lang.System.arraycopy(r4, r15, r1, r9, r10)     // Catch:{ Exception -> 0x0193 }
            int r15 = r2.D     // Catch:{ Exception -> 0x0193 }
            if (r15 != 0) goto L_0x0149
            com.leedarson.smartcamera.codec.c r15 = r2.F     // Catch:{ Exception -> 0x0193 }
            int r9 = r1.length     // Catch:{ Exception -> 0x0193 }
            r20 = 1
            r16 = r12
            r18 = r1
            r19 = r9
            int r9 = r15.b0(r16, r18, r19, r20)     // Catch:{ Exception -> 0x0193 }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0193 }
            r15.<init>()     // Catch:{ Exception -> 0x0193 }
            r22 = r4
            int r4 = r1.length     // Catch:{ Exception -> 0x0191 }
            r15.append(r4)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r4 = " writeVideoData: "
            r15.append(r4)     // Catch:{ Exception -> 0x0191 }
            r15.append(r9)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r4 = r15.toString()     // Catch:{ Exception -> 0x0191 }
            android.util.Log.e(r0, r4)     // Catch:{ Exception -> 0x0191 }
            r4 = 1
            goto L_0x0166
        L_0x0149:
            r22 = r4
            r4 = 1
            if (r15 != r4) goto L_0x0166
            com.leedarson.smartcamera.codec.c r15 = r2.F     // Catch:{ Exception -> 0x0191 }
            int r9 = r1.length     // Catch:{ Exception -> 0x0191 }
            r20 = 2
            r16 = r12
            r18 = r1
            r19 = r9
            r15.b0(r16, r18, r19, r20)     // Catch:{ Exception -> 0x0191 }
            goto L_0x0166
        L_0x015d:
            r22 = r4
            r4 = 1
            goto L_0x0166
        L_0x0161:
            r23 = r1
            r22 = r4
            r4 = 1
        L_0x0166:
            int r1 = r2.C     // Catch:{ Exception -> 0x0191 }
            int r1 = r1 + r10
            int r5 = r5 + r1
            r10 = r4
            r1 = 2
            r9 = 0
            r4 = r22
            goto L_0x004e
        L_0x0171:
            r22 = r4
            r0 = 50
            android.os.SystemClock.sleep(r0)     // Catch:{ Exception -> 0x0191 }
            com.leedarson.smartcamera.codec.c r0 = r2.F     // Catch:{ Exception -> 0x0191 }
            r0.V()     // Catch:{ Exception -> 0x0191 }
            java.lang.Object r0 = r2.l()
            com.leedarson.ui.CloudFragment r0 = (com.leedarson.ui.CloudFragment) r0
            androidx.fragment.app.FragmentActivity r0 = r0.getActivity()
            com.leedarson.ui.c$h r1 = new com.leedarson.ui.c$h
            r1.<init>(r3)
            r0.runOnUiThread(r1)
            return
        L_0x0191:
            r0 = move-exception
            goto L_0x0196
        L_0x0193:
            r0 = move-exception
            r22 = r4
        L_0x0196:
            r0.printStackTrace()
            com.leedarson.serviceinterface.utils.FileUtils.delete((java.lang.String) r3)
            java.lang.Object r1 = r2.l()
            com.leedarson.ui.CloudFragment r1 = (com.leedarson.ui.CloudFragment) r1
            androidx.fragment.app.FragmentActivity r1 = r1.getActivity()
            com.leedarson.ui.c$g r4 = new com.leedarson.ui.c$g
            r4.<init>()
            r1.runOnUiThread(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.c.H(byte[], java.lang.String):void");
    }

    /* compiled from: CloudPresenter */
    public class g implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10979, new Class[0], Void.TYPE).isSupported) {
                ((d) c.this.m()).a();
                ((d) c.this.m()).showToast(R$string.share_failed);
            }
        }
    }

    /* compiled from: CloudPresenter */
    public class h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        h(String str) {
            this.c = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10980, new Class[0], Void.TYPE).isSupported) {
                IntentUtils.shareVideo(((CloudFragment) c.this.l()).getContext(), new File(this.c), "");
                ((d) c.this.m()).a();
            }
        }
    }

    private void I(boolean z2, String str, String str2, String str3) {
        Class<String> cls = String.class;
        Object[] objArr = {new Byte(z2 ? (byte) 1 : 0), str, str2, str3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10953, new Class[]{Boolean.TYPE, cls, cls, cls}, Void.TYPE).isSupported) {
            String deviceId = str;
            String savePath = str3;
            boolean isCache = z2;
            String time = str2;
            this.E = false;
            Future future = this.H;
            if (future != null && !future.isCancelled()) {
                this.H.cancel(true);
            }
            this.H = this.G.submit(new i(isCache, deviceId, time, savePath));
        }
    }

    /* compiled from: CloudPresenter */
    public class i implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean c;
        final /* synthetic */ String d;
        final /* synthetic */ String f;
        final /* synthetic */ String q;

        i(boolean z, String str, String str2, String str3) {
            this.c = z;
            this.d = str;
            this.f = str2;
            this.q = str3;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10981, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            try {
                if (this.c) {
                    c cVar = c.this;
                    c.t(cVar, PlayBackCacheUtils.readCacheFile(((CloudFragment) cVar.l()).getContext(), this.d, this.f), this.q);
                    return null;
                }
                c cVar2 = c.this;
                Context context = ((CloudFragment) cVar2.l()).getContext();
                String str = this.d;
                c.t(cVar2, DownloadVideoUtils.readDownloadFile(context, str, this.f + ""), this.q);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
