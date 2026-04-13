package com.leedarson.log.tracker;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.leedarson.base.utils.w;
import com.leedarson.log.mgr.q;
import com.leedarson.log.tracker.BaseStepBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: EventTracker */
public abstract class a<T extends BaseStepBean> {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public Context a;
    private ArrayList<T> b;
    private HashMap<String, Object> c;
    private com.leedarson.log.elk.a d;
    /* access modifiers changed from: private */
    public String e;
    /* access modifiers changed from: private */
    public String f;
    private long g;
    private boolean h = false;

    public abstract HashMap<String, Object> f();

    public a(Context context) {
        this.a = context;
        this.b = new ArrayList<>();
        this.c = new HashMap<>();
        this.f = context.getCacheDir() + File.separator + "bufferLog";
        this.e = String.valueOf(System.currentTimeMillis());
    }

    public void j(String traceId, String module, String event) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{traceId, module, event}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_FTP_RESP, clsArr, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.a("EventTracker traceId=", traceId);
            this.h = true;
            this.e = traceId;
            this.d = com.leedarson.log.elk.a.y(this).x(traceId).t(module).e(event).o("info");
            HashMap<String, Object> f2 = f();
            this.c = f2;
            if (f2 == null) {
                this.c = new HashMap<>();
            }
            JSONObject buildBody = this.d.f();
            if (k(buildBody)) {
                q.r().m(this.f, traceId, buildBody.toString());
            }
        }
    }

    public boolean k(JSONObject buildBody) {
        return true;
    }

    public void e(T t) {
        if (!PatchProxy.proxy(new Object[]{t}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_FTP_REQ, new Class[]{BaseStepBean.class}, Void.TYPE).isSupported) {
            if (p()) {
                this.b.add(t);
                n(t);
                o(this.c, t);
                m();
            }
        }
    }

    private void n(T t) {
        if (!PatchProxy.proxy(new Object[]{t}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_FTP_RESP, new Class[]{BaseStepBean.class}, Void.TYPE).isSupported) {
            this.g += t.getDuration();
        }
    }

    public void d(String key, Object value) {
        HashMap<String, Object> hashMap;
        Class[] clsArr = {String.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{key, value}, this, changeQuickRedirect, false, 1375, clsArr, Void.TYPE).isSupported && (hashMap = this.c) != null) {
            hashMap.put(key, value);
        }
    }

    public void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1376, new Class[0], Void.TYPE).isSupported) {
            HashMap<String, Object> hashMap = this.c;
            if (hashMap != null) {
                for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                    this.d.u(entry.getKey(), entry.getValue());
                }
            }
            if (!this.c.containsKey(TypedValues.TransitionType.S_DURATION)) {
                this.d.u(TypedValues.TransitionType.S_DURATION, Long.valueOf(this.g));
            }
            ArrayList<T> arrayList = this.b;
            if (arrayList != null && !arrayList.isEmpty()) {
                JSONArray array = new JSONArray();
                Iterator<T> it = this.b.iterator();
                while (it.hasNext()) {
                    T t = (BaseStepBean) it.next();
                    try {
                        array.put((Object) t.toJson());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        array.put((Object) "Gson.toJson 转换异常 :" + e2.getMessage() + ",obj=" + t.toSimpleString());
                    }
                }
                this.d.r(array);
            }
            q.r().m(this.f, this.e, this.d.f().toString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void l() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 1377(0x561, float:1.93E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.concurrent.ExecutorService r1 = r0.h()
            if (r1 == 0) goto L_0x0038
            boolean r2 = r1.isShutdown()
            if (r2 == 0) goto L_0x0024
            goto L_0x0038
        L_0x0024:
            boolean r2 = r0.p()
            if (r2 != 0) goto L_0x002b
            return
        L_0x002b:
            java.util.concurrent.ExecutorService r2 = r0.h()
            com.leedarson.log.tracker.a$a r3 = new com.leedarson.log.tracker.a$a
            r3.<init>()
            r2.execute(r3)
            return
        L_0x0038:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.log.tracker.a.l():void");
    }

    /* renamed from: com.leedarson.log.tracker.a$a  reason: collision with other inner class name */
    /* compiled from: EventTracker */
    public class C0090a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0090a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1380, new Class[0], Void.TYPE).isSupported) {
                if (!TextUtils.isEmpty(a.this.e)) {
                    try {
                        File currentFile = new File(a.this.f, a.this.e);
                        if (currentFile.exists()) {
                            File o = q.r().o();
                            w.j(a.this.a, currentFile.getAbsolutePath(), new File(o, a.this.e + ".log").getAbsolutePath(), false);
                            currentFile.delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private ExecutorService h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1378, new Class[0], ExecutorService.class);
        if (proxy.isSupported) {
            return (ExecutorService) proxy.result;
        }
        return q.r().w();
    }

    public void o(HashMap<String, Object> hashMap, T t) {
    }

    public String i() {
        return this.e;
    }

    public ArrayList<T> g() {
        return this.b;
    }

    public boolean p() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1379, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!this.h) {
            Log.e("EventTracker", "EventTracker 需要调用 init ,请检查代码");
            com.leedarson.log.elk.a.y(this).c(getClass().getName()).p("EventTracker 需要调用 init ,请检查代码").t("LdsLogger").o("silly").a().b();
        }
        return this.h;
    }
}
