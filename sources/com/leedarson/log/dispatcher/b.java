package com.leedarson.log.dispatcher;

import android.content.Context;
import com.leedarson.log.mgr.q;
import com.leedarson.serviceinterface.LoggerService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.e;
import io.reactivex.functions.f;
import io.reactivex.l;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: Logger2Dispatcher */
public class b extends a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private final String b = "Logger2Dispatcher";

    public b(Context context) {
        this.a = context;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0069, code lost:
        if (r10.equals("trackSensorsData") != false) goto L_0x008d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(java.lang.String r19, android.app.Activity r20, java.lang.String r21, java.lang.String r22) {
        /*
            r18 = this;
            java.lang.String r0 = "VIPLevel"
            java.lang.String r1 = "isVIP"
            java.lang.String r2 = "isLogin"
            java.lang.String r3 = "developerLogServerUrl"
            java.lang.String r4 = "operatorLogServerUrl"
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r6 = 4
            java.lang.Object[] r7 = new java.lang.Object[r6]
            r14 = 0
            r7[r14] = r19
            r15 = 1
            r7[r15] = r20
            r16 = 2
            r7[r16] = r21
            r17 = 3
            r7[r17] = r22
            com.meituan.robust.ChangeQuickRedirect r9 = changeQuickRedirect
            java.lang.Class[] r12 = new java.lang.Class[r6]
            r12[r14] = r5
            java.lang.Class<android.app.Activity> r8 = android.app.Activity.class
            r12[r15] = r8
            r12[r16] = r5
            r12[r17] = r5
            java.lang.Class r13 = java.lang.Void.TYPE
            r10 = 0
            r11 = 1223(0x4c7, float:1.714E-42)
            r8 = r18
            com.meituan.robust.PatchProxyResult r5 = com.meituan.robust.PatchProxy.proxy(r7, r8, r9, r10, r11, r12, r13)
            boolean r5 = r5.isSupported
            if (r5 == 0) goto L_0x003b
            return
        L_0x003b:
            r5 = r18
            r7 = r20
            r8 = r22
            r9 = r19
            r10 = r21
            r11 = -1
            int r12 = r10.hashCode()
            switch(r12) {
                case -2009349140: goto L_0x0081;
                case -934521548: goto L_0x0077;
                case -657530826: goto L_0x006c;
                case 37907704: goto L_0x0062;
                case 126605892: goto L_0x0058;
                case 1163554325: goto L_0x004e;
                default: goto L_0x004d;
            }
        L_0x004d:
            goto L_0x008c
        L_0x004e:
            java.lang.String r6 = "reportByTraceId"
            boolean r6 = r10.equals(r6)
            if (r6 == 0) goto L_0x004d
            r6 = r15
            goto L_0x008d
        L_0x0058:
            java.lang.String r6 = "setConfig"
            boolean r6 = r10.equals(r6)
            if (r6 == 0) goto L_0x004d
            r6 = 5
            goto L_0x008d
        L_0x0062:
            java.lang.String r12 = "trackSensorsData"
            boolean r12 = r10.equals(r12)
            if (r12 == 0) goto L_0x004d
            goto L_0x008d
        L_0x006c:
            java.lang.String r6 = "getDeviceLogUploadUrl"
            boolean r6 = r10.equals(r6)
            if (r6 == 0) goto L_0x004d
            r6 = r17
            goto L_0x008d
        L_0x0077:
            java.lang.String r6 = "report"
            boolean r6 = r10.equals(r6)
            if (r6 == 0) goto L_0x004d
            r6 = r14
            goto L_0x008d
        L_0x0081:
            java.lang.String r6 = "saveByTraceId"
            boolean r6 = r10.equals(r6)
            if (r6 == 0) goto L_0x004d
            r6 = r16
            goto L_0x008d
        L_0x008c:
            r6 = r11
        L_0x008d:
            java.lang.String r11 = "\n"
            java.lang.String r12 = "traceId"
            java.lang.String r13 = "content"
            switch(r6) {
                case 0: goto L_0x023f;
                case 1: goto L_0x021b;
                case 2: goto L_0x0183;
                case 3: goto L_0x0181;
                case 4: goto L_0x011d;
                case 5: goto L_0x0099;
                default: goto L_0x0097;
            }
        L_0x0097:
            goto L_0x02a2
        L_0x0099:
            java.lang.String r6 = "SA.Logger2.setConfig"
            timber.log.a$b r6 = timber.log.a.g(r6)     // Catch:{ JSONException -> 0x0117 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0117 }
            r11.<init>()     // Catch:{ JSONException -> 0x0117 }
            java.lang.String r12 = "data=>"
            r11.append(r12)     // Catch:{ JSONException -> 0x0117 }
            r11.append(r8)     // Catch:{ JSONException -> 0x0117 }
            java.lang.String r11 = r11.toString()     // Catch:{ JSONException -> 0x0117 }
            java.lang.Object[] r12 = new java.lang.Object[r14]     // Catch:{ JSONException -> 0x0117 }
            r6.h(r11, r12)     // Catch:{ JSONException -> 0x0117 }
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0117 }
            r6.<init>((java.lang.String) r8)     // Catch:{ JSONException -> 0x0117 }
            boolean r11 = r6.has(r3)     // Catch:{ JSONException -> 0x0117 }
            if (r11 == 0) goto L_0x00cb
            java.lang.String r3 = r6.optString(r3)     // Catch:{ JSONException -> 0x0117 }
            android.content.Context r11 = r5.a     // Catch:{ JSONException -> 0x0117 }
            java.lang.String r12 = "logUploadUrl"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r11, r12, r3)     // Catch:{ JSONException -> 0x0117 }
        L_0x00cb:
            boolean r3 = r6.has(r4)     // Catch:{ JSONException -> 0x0117 }
            if (r3 == 0) goto L_0x00e1
            java.lang.String r3 = r6.optString(r4)     // Catch:{ JSONException -> 0x0117 }
            android.content.Context r11 = r5.a     // Catch:{ JSONException -> 0x0117 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r11, r4, r3)     // Catch:{ JSONException -> 0x0117 }
            com.leedarson.log.sensorsdata.a r4 = com.leedarson.log.sensorsdata.a.b()     // Catch:{ JSONException -> 0x0117 }
            r4.h(r3)     // Catch:{ JSONException -> 0x0117 }
        L_0x00e1:
            boolean r3 = r6.has(r2)     // Catch:{ JSONException -> 0x0117 }
            if (r3 == 0) goto L_0x00f2
            java.util.concurrent.atomic.AtomicBoolean r3 = new java.util.concurrent.atomic.AtomicBoolean     // Catch:{ JSONException -> 0x0117 }
            boolean r2 = r6.optBoolean(r2)     // Catch:{ JSONException -> 0x0117 }
            r3.<init>(r2)     // Catch:{ JSONException -> 0x0117 }
            com.leedarson.serviceinterface.Constans.isLogin = r3     // Catch:{ JSONException -> 0x0117 }
        L_0x00f2:
            boolean r2 = r6.has(r1)     // Catch:{ JSONException -> 0x0117 }
            if (r2 == 0) goto L_0x00fe
            boolean r1 = r6.optBoolean(r1)     // Catch:{ JSONException -> 0x0117 }
            com.leedarson.serviceinterface.Constans.isVIP = r1     // Catch:{ JSONException -> 0x0117 }
        L_0x00fe:
            boolean r1 = r6.has(r0)     // Catch:{ JSONException -> 0x0117 }
            if (r1 == 0) goto L_0x010a
            java.lang.String r0 = r6.optString(r0)     // Catch:{ JSONException -> 0x0117 }
            com.leedarson.serviceinterface.Constans.VIPLevel = r0     // Catch:{ JSONException -> 0x0117 }
        L_0x010a:
            org.json.JSONObject r0 = com.leedarson.base.utils.p.c()     // Catch:{ JSONException -> 0x0117 }
            java.lang.String r0 = r0.toString()     // Catch:{ JSONException -> 0x0117 }
            r5.a(r9, r0)     // Catch:{ JSONException -> 0x0117 }
            goto L_0x02a2
        L_0x0117:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x02a2
        L_0x011d:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x017b }
            r0.<init>((java.lang.String) r8)     // Catch:{ JSONException -> 0x017b }
            java.lang.String r1 = "event"
            java.lang.String r1 = r0.optString(r1)     // Catch:{ JSONException -> 0x017b }
            java.lang.String r2 = "properties"
            org.json.JSONObject r2 = r0.optJSONObject(r2)     // Catch:{ JSONException -> 0x017b }
            com.leedarson.log.sensorsdata.a r3 = com.leedarson.log.sensorsdata.a.b()     // Catch:{ JSONException -> 0x017b }
            boolean r3 = r3.c()     // Catch:{ JSONException -> 0x017b }
            if (r3 == 0) goto L_0x014d
            com.leedarson.log.sensorsdata.a r3 = com.leedarson.log.sensorsdata.a.b()     // Catch:{ JSONException -> 0x017b }
            java.lang.String r4 = "App"
            r3.n(r1, r2, r4)     // Catch:{ JSONException -> 0x017b }
            org.json.JSONObject r3 = com.leedarson.base.utils.p.c()     // Catch:{ JSONException -> 0x017b }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x017b }
            r5.a(r9, r3)     // Catch:{ JSONException -> 0x017b }
            goto L_0x0179
        L_0x014d:
            java.lang.String r3 = "SA.Logger2Dispatcher"
            timber.log.a$b r3 = timber.log.a.g(r3)     // Catch:{ JSONException -> 0x017b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x017b }
            r4.<init>()     // Catch:{ JSONException -> 0x017b }
            java.lang.String r6 = "trackSensorsData=>"
            r4.append(r6)     // Catch:{ JSONException -> 0x017b }
            r4.append(r8)     // Catch:{ JSONException -> 0x017b }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x017b }
            java.lang.Object[] r6 = new java.lang.Object[r14]     // Catch:{ JSONException -> 0x017b }
            r3.h(r4, r6)     // Catch:{ JSONException -> 0x017b }
            r3 = 400(0x190, float:5.6E-43)
            java.lang.String r4 = "sensors data has not init"
            org.json.JSONObject r3 = com.leedarson.base.utils.p.a(r3, r4)     // Catch:{ JSONException -> 0x017b }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x017b }
            r5.a(r9, r3)     // Catch:{ JSONException -> 0x017b }
        L_0x0179:
            goto L_0x02a2
        L_0x017b:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x02a2
        L_0x0181:
            goto L_0x02a2
        L_0x0183:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0207 }
            r0.<init>((java.lang.String) r8)     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r1 = r0.optString(r12)     // Catch:{ JSONException -> 0x0207 }
            com.leedarson.log.mgr.q r2 = com.leedarson.log.mgr.q.r()     // Catch:{ JSONException -> 0x0207 }
            java.io.File r2 = r2.q()     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ JSONException -> 0x0207 }
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0207 }
            r3.<init>()     // Catch:{ JSONException -> 0x0207 }
            boolean r4 = r0.has(r13)     // Catch:{ JSONException -> 0x0207 }
            if (r4 == 0) goto L_0x01a7
            r0.optJSONObject(r13)     // Catch:{ JSONException -> 0x0207 }
            goto L_0x01ae
        L_0x01a7:
            java.lang.String r4 = "item"
            org.json.JSONObject r4 = r0.optJSONObject(r4)     // Catch:{ JSONException -> 0x0207 }
            r3 = r4
        L_0x01ae:
            java.util.Iterator r4 = r3.keys()     // Catch:{ JSONException -> 0x0207 }
            boolean r4 = r4.hasNext()     // Catch:{ JSONException -> 0x0207 }
            if (r4 != 0) goto L_0x01ec
            java.lang.Class<com.leedarson.log.dispatcher.b> r4 = com.leedarson.log.dispatcher.b.class
            com.leedarson.log.elk.a r4 = com.leedarson.log.elk.a.y(r4)     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r6 = "LdsLogger"
            com.leedarson.log.elk.a r4 = r4.t(r6)     // Catch:{ JSONException -> 0x0207 }
            com.leedarson.log.elk.a r4 = r4.x(r1)     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r6 = "silly"
            com.leedarson.log.elk.a r4 = r4.o(r6)     // Catch:{ JSONException -> 0x0207 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0207 }
            r6.<init>()     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r11 = "logger2.saveByTraceId item 内容为空, data:"
            r6.append(r11)     // Catch:{ JSONException -> 0x0207 }
            r6.append(r8)     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0207 }
            com.leedarson.log.elk.a r4 = r4.p(r6)     // Catch:{ JSONException -> 0x0207 }
            com.leedarson.log.reporter.d r4 = r4.a()     // Catch:{ JSONException -> 0x0207 }
            r4.b()     // Catch:{ JSONException -> 0x0207 }
            goto L_0x0206
        L_0x01ec:
            com.leedarson.log.mgr.q r4 = com.leedarson.log.mgr.q.r()     // Catch:{ JSONException -> 0x0207 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0207 }
            r6.<init>()     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r12 = r3.toString()     // Catch:{ JSONException -> 0x0207 }
            r6.append(r12)     // Catch:{ JSONException -> 0x0207 }
            r6.append(r11)     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0207 }
            r4.h(r2, r1, r6)     // Catch:{ JSONException -> 0x0207 }
        L_0x0206:
            goto L_0x020e
        L_0x0207:
            r0 = move-exception
            r0.printStackTrace()
            r5.c(r8, r0)
        L_0x020e:
            org.json.JSONObject r0 = com.leedarson.base.utils.p.c()
            java.lang.String r0 = r0.toString()
            r5.a(r9, r0)
            goto L_0x02a2
        L_0x021b:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x022c }
            r0.<init>((java.lang.String) r8)     // Catch:{ Exception -> 0x022c }
            java.lang.String r1 = r0.optString(r12)     // Catch:{ Exception -> 0x022c }
            org.json.JSONObject r2 = r0.optJSONObject(r13)     // Catch:{ Exception -> 0x022c }
            r5.d(r1, r2)     // Catch:{ Exception -> 0x022c }
            goto L_0x0233
        L_0x022c:
            r0 = move-exception
            r0.printStackTrace()
            r5.c(r8, r0)
        L_0x0233:
            org.json.JSONObject r0 = com.leedarson.base.utils.p.c()
            java.lang.String r0 = r0.toString()
            r5.a(r9, r0)
            goto L_0x02a2
        L_0x023f:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0292 }
            java.lang.String r1 = r8.toString()     // Catch:{ Exception -> 0x0292 }
            r0.<init>((java.lang.String) r1)     // Catch:{ Exception -> 0x0292 }
            boolean r1 = r0.has(r13)     // Catch:{ Exception -> 0x0292 }
            if (r1 == 0) goto L_0x0291
            org.json.JSONObject r1 = r0.optJSONObject(r13)     // Catch:{ Exception -> 0x0292 }
            java.lang.String r2 = "create"
            java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0292 }
            java.lang.String r4 = "yyyy-MM-dd HH:mm:ss.SSS"
            r3.<init>(r4)     // Catch:{ Exception -> 0x0292 }
            java.util.Date r4 = new java.util.Date     // Catch:{ Exception -> 0x0292 }
            r4.<init>()     // Catch:{ Exception -> 0x0292 }
            java.lang.String r3 = r3.format(r4)     // Catch:{ Exception -> 0x0292 }
            r1.put((java.lang.String) r2, (java.lang.Object) r3)     // Catch:{ Exception -> 0x0292 }
            com.alibaba.android.arouter.launcher.a r2 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x0292 }
            java.lang.Class<com.leedarson.serviceinterface.LoggerService> r3 = com.leedarson.serviceinterface.LoggerService.class
            java.lang.Object r2 = r2.g(r3)     // Catch:{ Exception -> 0x0292 }
            com.leedarson.serviceinterface.LoggerService r2 = (com.leedarson.serviceinterface.LoggerService) r2     // Catch:{ Exception -> 0x0292 }
            r2.appendCommonProperties(r1)     // Catch:{ Exception -> 0x0292 }
            com.leedarson.log.mgr.q r3 = com.leedarson.log.mgr.q.r()     // Catch:{ Exception -> 0x0292 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0292 }
            r4.<init>()     // Catch:{ Exception -> 0x0292 }
            java.lang.String r6 = r1.toString()     // Catch:{ Exception -> 0x0292 }
            r4.append(r6)     // Catch:{ Exception -> 0x0292 }
            r4.append(r11)     // Catch:{ Exception -> 0x0292 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0292 }
            r3.g(r4)     // Catch:{ Exception -> 0x0292 }
        L_0x0291:
            goto L_0x0296
        L_0x0292:
            r0 = move-exception
            r5.c(r8, r0)
        L_0x0296:
            org.json.JSONObject r0 = com.leedarson.base.utils.p.c()
            java.lang.String r0 = r0.toString()
            r5.a(r9, r0)
        L_0x02a2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.log.dispatcher.b.b(java.lang.String, android.app.Activity, java.lang.String, java.lang.String):void");
    }

    private void c(String src, Exception e) {
        Class[] clsArr = {String.class, Exception.class};
        if (!PatchProxy.proxy(new Object[]{src, e}, this, changeQuickRedirect, false, 1224, clsArr, Void.TYPE).isSupported) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("data", (Object) src);
                jsonObject.put("exception", (Object) e.getMessage());
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            com.leedarson.log.elk.a.y(b.class).e("h5LogErr").x("h5LogErr").u("owner", "WebApp").p(jsonObject.toString()).a().b();
        }
    }

    private void d(String traceId, JSONObject contentJson) {
        Class[] clsArr = {String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{traceId, contentJson}, this, changeQuickRedirect, false, 1225, clsArr, Void.TYPE).isSupported) {
            File file = new File(q.r().q(), traceId);
            l.F(file).b0(io.reactivex.schedulers.a.b(q.r().w())).G(new c(traceId, contentJson)).Y(new a(file), new C0086b());
        }
    }

    /* compiled from: Logger2Dispatcher */
    public class c implements f<File, String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ JSONObject d;

        c(String str, JSONObject jSONObject) {
            this.c = str;
            this.d = jSONObject;
        }

        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1230, new Class[]{Object.class}, Object.class);
            return proxy.isSupported ? proxy.result : a((File) obj);
        }

        public String a(File file) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 1229, new Class[]{File.class}, String.class);
            if (proxy.isSupported) {
                return (String) proxy.result;
            }
            if (!file.exists()) {
                return com.leedarson.log.elk.a.y(b.class).t("LdsLogger").x(this.c).o("silly").p("logger2.reportByTraceId 找不到traceId 对应文件 traceId:" + this.c + ",content:" + this.d.toString()).f().toString();
            }
            JSONArray message = new JSONArray();
            for (String item : com.leedarson.base.utils.l.d(file).split("\n")) {
                try {
                    message.put((Object) new JSONObject(item));
                } catch (Exception e) {
                    message.put((Object) item);
                }
            }
            this.d.put("message", (Object) message);
            ((LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class)).appendCommonProperties(this.d);
            return this.d.toString();
        }
    }

    /* compiled from: Logger2Dispatcher */
    public class a implements e<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ File c;

        a(File file) {
            this.c = file;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1227, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((String) obj);
            }
        }

        public void a(String s) {
            if (!PatchProxy.proxy(new Object[]{s}, this, changeQuickRedirect, false, 1226, new Class[]{String.class}, Void.TYPE).isSupported) {
                q r = q.r();
                r.g(s + "\n");
                this.c.delete();
            }
        }
    }

    /* renamed from: com.leedarson.log.dispatcher.b$b  reason: collision with other inner class name */
    /* compiled from: Logger2Dispatcher */
    public class C0086b implements e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0086b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1228, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
        }
    }
}
