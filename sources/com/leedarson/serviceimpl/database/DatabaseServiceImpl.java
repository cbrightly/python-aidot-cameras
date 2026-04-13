package com.leedarson.serviceimpl.database;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.utils.p;
import com.leedarson.serviceimpl.database.manager.d;
import com.leedarson.serviceinterface.DatabaseService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import meshsdk.ConfigUtil;
import org.json.JSONObject;
import timber.log.a;

public class DatabaseServiceImpl implements DatabaseService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a = "database_leedarson";
    private Context b;
    private ScheduledExecutorService c = Executors.newScheduledThreadPool(5);

    static /* synthetic */ void a(DatabaseServiceImpl x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {DatabaseServiceImpl.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 7842, clsArr, Void.TYPE).isSupported) {
            x0.k(x1, x2);
        }
    }

    static /* synthetic */ void h(DatabaseServiceImpl x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {DatabaseServiceImpl.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 7843, clsArr, Void.TYPE).isSupported) {
            x0.j(x1, x2);
        }
    }

    static /* synthetic */ void i(DatabaseServiceImpl x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {DatabaseServiceImpl.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 7844, clsArr, Void.TYPE).isSupported) {
            x0.m(x1, x2);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x005b, code lost:
        if (r5.equals("executeSQL") != false) goto L_0x00c9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r17, java.lang.String r18, java.lang.String r19) {
        /*
            r16 = this;
            java.lang.String r1 = "#"
            java.lang.String r2 = "{\"code\":-1000}"
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r3 = 3
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r11 = 0
            r4[r11] = r17
            r12 = 1
            r4[r12] = r18
            r13 = 2
            r4[r13] = r19
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r9 = new java.lang.Class[r3]
            r9[r11] = r0
            r9[r12] = r0
            r9[r13] = r0
            java.lang.Class r10 = java.lang.Void.TYPE
            r7 = 0
            r8 = 7833(0x1e99, float:1.0976E-41)
            r5 = r16
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r4, r5, r6, r7, r8, r9, r10)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002c
            return
        L_0x002c:
            r4 = r16
            r5 = r18
            r6 = r17
            r7 = r19
            r0 = -1
            int r8 = r5.hashCode()
            switch(r8) {
                case -1335458389: goto L_0x00bd;
                case -1166666778: goto L_0x00b3;
                case -1121200045: goto L_0x00a8;
                case -1121188513: goto L_0x009e;
                case -893726730: goto L_0x0094;
                case -541929364: goto L_0x008a;
                case -28481479: goto L_0x0080;
                case 3522941: goto L_0x0076;
                case 107944136: goto L_0x006b;
                case 204095374: goto L_0x005f;
                case 539367961: goto L_0x0055;
                case 604942951: goto L_0x0049;
                case 688274264: goto L_0x003e;
                default: goto L_0x003c;
            }
        L_0x003c:
            goto L_0x00c8
        L_0x003e:
            java.lang.String r3 = "db.query"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = 4
            goto L_0x00c9
        L_0x0049:
            java.lang.String r3 = "kv.remove"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = 10
            goto L_0x00c9
        L_0x0055:
            java.lang.String r8 = "executeSQL"
            boolean r8 = r5.equals(r8)
            if (r8 == 0) goto L_0x003c
            goto L_0x00c9
        L_0x005f:
            java.lang.String r3 = "kv.delete"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = 11
            goto L_0x00c9
        L_0x006b:
            java.lang.String r3 = "query"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = 9
            goto L_0x00c9
        L_0x0076:
            java.lang.String r3 = "save"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = 7
            goto L_0x00c9
        L_0x0080:
            java.lang.String r3 = "db.update"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = r13
            goto L_0x00c9
        L_0x008a:
            java.lang.String r3 = "db.create"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = r11
            goto L_0x00c9
        L_0x0094:
            java.lang.String r3 = "initDataBaseName"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = r12
            goto L_0x00c9
        L_0x009e:
            java.lang.String r3 = "kv.set"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = 6
            goto L_0x00c9
        L_0x00a8:
            java.lang.String r3 = "kv.get"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = 8
            goto L_0x00c9
        L_0x00b3:
            java.lang.String r3 = "querySQL"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = 5
            goto L_0x00c9
        L_0x00bd:
            java.lang.String r3 = "delete"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x003c
            r3 = 12
            goto L_0x00c9
        L_0x00c8:
            r3 = r0
        L_0x00c9:
            java.lang.String r8 = "\""
            java.lang.String r9 = "keys"
            java.lang.String r10 = "key"
            java.lang.String r14 = "{\"code\":200}"
            java.lang.String r15 = ""
            switch(r3) {
                case 0: goto L_0x0266;
                case 1: goto L_0x0266;
                case 2: goto L_0x0255;
                case 3: goto L_0x0255;
                case 4: goto L_0x0244;
                case 5: goto L_0x0244;
                case 6: goto L_0x01de;
                case 7: goto L_0x01de;
                case 8: goto L_0x012e;
                case 9: goto L_0x012e;
                case 10: goto L_0x00d8;
                case 11: goto L_0x00d8;
                case 12: goto L_0x00d8;
                default: goto L_0x00d6;
            }
        L_0x00d6:
            goto L_0x0277
        L_0x00d8:
            com.google.gson.JsonParser r0 = new com.google.gson.JsonParser     // Catch:{ Exception -> 0x011c }
            r0.<init>()     // Catch:{ Exception -> 0x011c }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x011c }
            r1.<init>((java.lang.String) r7)     // Catch:{ Exception -> 0x011c }
            java.lang.Object r2 = r1.get(r9)     // Catch:{ Exception -> 0x011c }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x011c }
            com.google.gson.JsonElement r2 = r0.parse((java.lang.String) r2)     // Catch:{ Exception -> 0x011c }
            com.google.gson.JsonArray r2 = r2.getAsJsonArray()     // Catch:{ Exception -> 0x011c }
            r3 = 0
        L_0x00f3:
            int r9 = r2.size()     // Catch:{ Exception -> 0x011c }
            if (r3 >= r9) goto L_0x011b
            com.google.gson.JsonElement r9 = r2.get(r3)     // Catch:{ Exception -> 0x011c }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x011c }
            java.lang.String r9 = r9.replace(r8, r15)     // Catch:{ Exception -> 0x011c }
            android.content.Context r10 = r4.b     // Catch:{ Exception -> 0x011c }
            android.content.Context r10 = r10.getApplicationContext()     // Catch:{ Exception -> 0x011c }
            r11 = 0
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r10, r9, r11)     // Catch:{ Exception -> 0x011c }
            android.content.Context r10 = r4.b     // Catch:{ Exception -> 0x011c }
            android.content.Context r10 = r10.getApplicationContext()     // Catch:{ Exception -> 0x011c }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.deleteByKey(r10, r9)     // Catch:{ Exception -> 0x011c }
            int r3 = r3 + 1
            goto L_0x00f3
        L_0x011b:
            goto L_0x0120
        L_0x011c:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0120:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            r1.<init>(r6, r14)
            r0.l(r1)
            goto L_0x0277
        L_0x012e:
            com.google.gson.JsonParser r0 = new com.google.gson.JsonParser     // Catch:{ Exception -> 0x01cc }
            r0.<init>()     // Catch:{ Exception -> 0x01cc }
            r3 = r0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x01cc }
            r0.<init>((java.lang.String) r7)     // Catch:{ Exception -> 0x01cc }
            r11 = r0
            r12 = 0
            java.lang.Object r0 = r11.get(r10)     // Catch:{ Exception -> 0x014d }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x014d }
            com.google.gson.JsonElement r0 = r3.parse((java.lang.String) r0)     // Catch:{ Exception -> 0x014d }
            com.google.gson.JsonArray r0 = r0.getAsJsonArray()     // Catch:{ Exception -> 0x014d }
            r12 = r0
            goto L_0x014e
        L_0x014d:
            r0 = move-exception
        L_0x014e:
            if (r12 != 0) goto L_0x0161
            java.lang.Object r0 = r11.get(r9)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x01cc }
            com.google.gson.JsonElement r0 = r3.parse((java.lang.String) r0)     // Catch:{ Exception -> 0x01cc }
            com.google.gson.JsonArray r0 = r0.getAsJsonArray()     // Catch:{ Exception -> 0x01cc }
            r12 = r0
        L_0x0161:
            if (r12 == 0) goto L_0x01be
            com.google.gson.JsonObject r0 = new com.google.gson.JsonObject     // Catch:{ Exception -> 0x01cc }
            r0.<init>()     // Catch:{ Exception -> 0x01cc }
            r9 = 0
        L_0x0169:
            int r10 = r12.size()     // Catch:{ Exception -> 0x01cc }
            if (r9 >= r10) goto L_0x0191
            com.google.gson.JsonElement r10 = r12.get(r9)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x01cc }
            java.lang.String r10 = r10.replace(r8, r15)     // Catch:{ Exception -> 0x01cc }
            android.content.Context r13 = r4.b     // Catch:{ Exception -> 0x01cc }
            android.content.Context r13 = r13.getApplicationContext()     // Catch:{ Exception -> 0x01cc }
            java.lang.String r13 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r13, r10, r1)     // Catch:{ Exception -> 0x01cc }
            boolean r14 = r1.equals(r13)     // Catch:{ Exception -> 0x01cc }
            if (r14 != 0) goto L_0x018e
            r0.addProperty((java.lang.String) r10, (java.lang.String) r13)     // Catch:{ Exception -> 0x01cc }
        L_0x018e:
            int r9 = r9 + 1
            goto L_0x0169
        L_0x0191:
            java.lang.String r1 = r0.toString()     // Catch:{ Exception -> 0x01cc }
            java.lang.String r8 = "\\\""
            java.lang.String r1 = r1.replace(r8, r15)     // Catch:{ Exception -> 0x01cc }
            org.greenrobot.eventbus.c r8 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x01cc }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r9 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x01cc }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01cc }
            r10.<init>()     // Catch:{ Exception -> 0x01cc }
            java.lang.String r13 = "{\"code\":200,\"desc\":\"\",\"data\":"
            r10.append(r13)     // Catch:{ Exception -> 0x01cc }
            r10.append(r1)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r13 = "}"
            r10.append(r13)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x01cc }
            r9.<init>(r6, r10)     // Catch:{ Exception -> 0x01cc }
            r8.l(r9)     // Catch:{ Exception -> 0x01cc }
            goto L_0x01ca
        L_0x01be:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x01cc }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x01cc }
            r1.<init>(r6, r2)     // Catch:{ Exception -> 0x01cc }
            r0.l(r1)     // Catch:{ Exception -> 0x01cc }
        L_0x01ca:
            goto L_0x0277
        L_0x01cc:
            r0 = move-exception
            r0.printStackTrace()
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r3 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            r3.<init>(r6, r2)
            r1.l(r3)
            goto L_0x0277
        L_0x01de:
            com.google.gson.internal.LinkedTreeMap r1 = com.leedarson.serviceimpl.database.manager.d.a(r7)
            if (r1 == 0) goto L_0x0277
            java.util.Set r0 = r1.keySet()
            java.util.Iterator r2 = r0.iterator()
        L_0x01ec:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0237
            java.lang.Object r0 = r2.next()
            r3 = r0
            java.lang.String r3 = (java.lang.String) r3
            boolean r0 = r3.equals(r10)     // Catch:{ Exception -> 0x0235 }
            if (r0 != 0) goto L_0x0234
            java.lang.String r0 = "security"
            boolean r0 = r3.equals(r0)     // Catch:{ Exception -> 0x0235 }
            if (r0 != 0) goto L_0x0234
            java.lang.Object r0 = r1.get(r3)     // Catch:{ Exception -> 0x0235 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0235 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0235 }
            r8.<init>()     // Catch:{ Exception -> 0x0235 }
            java.lang.String r9 = "ACTION_SAVE: "
            r8.append(r9)     // Catch:{ Exception -> 0x0235 }
            r8.append(r3)     // Catch:{ Exception -> 0x0235 }
            java.lang.String r9 = "==="
            r8.append(r9)     // Catch:{ Exception -> 0x0235 }
            r8.append(r0)     // Catch:{ Exception -> 0x0235 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0235 }
            java.lang.Object[] r9 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0235 }
            timber.log.a.c(r8, r9)     // Catch:{ Exception -> 0x0235 }
            android.content.Context r8 = r4.b     // Catch:{ Exception -> 0x0235 }
            android.content.Context r8 = r8.getApplicationContext()     // Catch:{ Exception -> 0x0235 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r8, r3, r0)     // Catch:{ Exception -> 0x0235 }
        L_0x0234:
            goto L_0x0236
        L_0x0235:
            r0 = move-exception
        L_0x0236:
            goto L_0x01ec
        L_0x0237:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r3 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            r3.<init>(r6, r14)
            r0.l(r3)
            goto L_0x0277
        L_0x0244:
            com.leedarson.serviceimpl.database.DatabaseServiceImpl$c r0 = new com.leedarson.serviceimpl.database.DatabaseServiceImpl$c
            r0.<init>()
            java.util.concurrent.ScheduledExecutorService r1 = r4.c
            java.lang.String[] r2 = new java.lang.String[r13]
            r2[r11] = r6
            r2[r12] = r7
            r0.executeOnExecutor(r1, r2)
            goto L_0x0277
        L_0x0255:
            com.leedarson.serviceimpl.database.DatabaseServiceImpl$a r0 = new com.leedarson.serviceimpl.database.DatabaseServiceImpl$a
            r0.<init>()
            java.util.concurrent.ScheduledExecutorService r1 = r4.c
            java.lang.String[] r2 = new java.lang.String[r13]
            r2[r11] = r6
            r2[r12] = r7
            r0.executeOnExecutor(r1, r2)
            goto L_0x0277
        L_0x0266:
            com.leedarson.serviceimpl.database.DatabaseServiceImpl$b r0 = new com.leedarson.serviceimpl.database.DatabaseServiceImpl$b
            r0.<init>()
            java.util.concurrent.ScheduledExecutorService r1 = r4.c
            java.lang.String[] r2 = new java.lang.String[r13]
            r2[r11] = r6
            r2[r12] = r7
            r0.executeOnExecutor(r1, r2)
        L_0x0277:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.database.DatabaseServiceImpl.handleData(java.lang.String, java.lang.String, java.lang.String):void");
    }

    public class b extends AsyncTask<String, Void, Void> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 7848, new Class[]{Object[].class}, Object.class);
            return proxy.isSupported ? proxy.result : a((String[]) objArr);
        }

        public Void a(String... v) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 7847, new Class[]{String[].class}, Void.class);
            if (proxy.isSupported) {
                return (Void) proxy.result;
            }
            DatabaseServiceImpl.a(DatabaseServiceImpl.this, v[0], v[1]);
            return null;
        }
    }

    public class a extends AsyncTask<String, Void, Void> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 7846, new Class[]{Object[].class}, Object.class);
            return proxy.isSupported ? proxy.result : a((String[]) objArr);
        }

        public Void a(String... v) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 7845, new Class[]{String[].class}, Void.class);
            if (proxy.isSupported) {
                return (Void) proxy.result;
            }
            DatabaseServiceImpl.h(DatabaseServiceImpl.this, v[0], v[1]);
            return null;
        }
    }

    public class c extends AsyncTask<String, Void, Void> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 7850, new Class[]{Object[].class}, Object.class);
            return proxy.isSupported ? proxy.result : a((String[]) objArr);
        }

        public Void a(String... v) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 7849, new Class[]{String[].class}, Void.class);
            if (proxy.isSupported) {
                return (Void) proxy.result;
            }
            DatabaseServiceImpl.i(DatabaseServiceImpl.this, v[0], v[1]);
            return null;
        }
    }

    public void closeDatabase() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7834, new Class[0], Void.TYPE).isSupported) {
            try {
                com.leedarson.serviceimpl.database.manager.a.b(this.b).a().getDatabase().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void init(Context context) {
        this.b = context;
    }

    private void k(String callbackKey, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, message}, this, changeQuickRedirect, false, 7835, clsArr, Void.TYPE).isSupported) {
            try {
                LinkedTreeMap linkedTreeMap = d.a(message);
                if (linkedTreeMap != null) {
                    com.leedarson.serviceimpl.database.manager.a.a = linkedTreeMap.get("name").toString();
                    if (linkedTreeMap.containsKey(ConfigUtil.VERSION_FILE)) {
                        Double.parseDouble(linkedTreeMap.get(ConfigUtil.VERSION_FILE).toString());
                    }
                    com.leedarson.serviceimpl.database.manager.a.b(this.b);
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":200}"));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void j(String callbackKey, String message) {
        Class<String> cls = String.class;
        synchronized (this) {
            if (!PatchProxy.proxy(new Object[]{callbackKey, message}, this, changeQuickRedirect, false, 7836, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                try {
                    LinkedTreeMap linkedTreeMap = d.a(message);
                    if (linkedTreeMap != null) {
                        String sql = linkedTreeMap.get("sql").toString();
                        if (!TextUtils.isEmpty(sql)) {
                            try {
                                com.leedarson.serviceimpl.database.manager.a.b(this.b).a().getDatabase().execSQL(sql);
                                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, p.c().toString()));
                            } catch (Exception ex) {
                                a.b g = timber.log.a.g("DatabaseServiceImpl");
                                g.c("Sql execute Error=" + ex.toString() + "  sql=" + sql, new Object[0]);
                                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":-1000}"));
                            }
                        } else {
                            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":-2000}"));
                        }
                    } else {
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":-3000}"));
                    }
                } catch (Exception e) {
                    a.b g2 = timber.log.a.g("DatabaseServiceImpl");
                    g2.c("Sql execute Error=" + e.toString(), new Object[0]);
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(-2000, e.getMessage()).toString()));
                }
            } else {
                return;
            }
        }
        return;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00cc, code lost:
        if (r4 != null) goto L_0x00ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00e6, code lost:
        if (r4 == null) goto L_0x00fa;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m(java.lang.String r14, java.lang.String r15) {
        /*
            r13 = this;
            java.lang.String r0 = "sql"
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            r2 = 2
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r10 = 0
            r3[r10] = r14
            r4 = 1
            r3[r4] = r15
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect
            java.lang.Class[] r8 = new java.lang.Class[r2]
            r8[r10] = r1
            r8[r4] = r1
            java.lang.Class r9 = java.lang.Void.TYPE
            r6 = 0
            r7 = 7837(0x1e9d, float:1.0982E-41)
            r4 = r13
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r3, r4, r5, r6, r7, r8, r9)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0024
            return
        L_0x0024:
            r1 = r13
            r2 = -2000(0xfffffffffffff830, float:NaN)
            com.google.gson.internal.LinkedTreeMap r3 = com.leedarson.serviceimpl.database.manager.d.a(r15)     // Catch:{ Exception -> 0x0119 }
            if (r3 == 0) goto L_0x010a
            java.lang.Object r4 = r3.get(r0)     // Catch:{ Exception -> 0x0119 }
            if (r4 == 0) goto L_0x00fb
            java.lang.Object r0 = r3.get(r0)     // Catch:{ Exception -> 0x0119 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0119 }
            boolean r4 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0119 }
            if (r4 != 0) goto L_0x00f1
            r4 = 0
            android.content.Context r5 = r1.b     // Catch:{ Exception -> 0x00d4 }
            com.leedarson.serviceimpl.database.manager.a r5 = com.leedarson.serviceimpl.database.manager.a.b(r5)     // Catch:{ Exception -> 0x00d4 }
            com.leedarson.serviceimpl.database.manager.c r5 = r5.a()     // Catch:{ Exception -> 0x00d4 }
            org.greenrobot.greendao.database.Database r5 = r5.getDatabase()     // Catch:{ Exception -> 0x00d4 }
            r6 = 0
            android.database.Cursor r5 = r5.rawQuery(r0, r6)     // Catch:{ Exception -> 0x00d4 }
            r4 = r5
            if (r4 == 0) goto L_0x00cc
            com.google.gson.JsonArray r5 = new com.google.gson.JsonArray     // Catch:{ Exception -> 0x00d4 }
            r5.<init>()     // Catch:{ Exception -> 0x00d4 }
            r4.moveToFirst()     // Catch:{ Exception -> 0x00d4 }
        L_0x0060:
            boolean r6 = r4.isAfterLast()     // Catch:{ Exception -> 0x00d4 }
            if (r6 != 0) goto L_0x00ac
            int r6 = r4.getColumnCount()     // Catch:{ Exception -> 0x00d4 }
            com.google.gson.JsonObject r7 = new com.google.gson.JsonObject     // Catch:{ Exception -> 0x00d4 }
            r7.<init>()     // Catch:{ Exception -> 0x00d4 }
            r8 = 0
        L_0x0070:
            if (r8 >= r6) goto L_0x00a4
            java.lang.String r9 = r4.getColumnName(r8)     // Catch:{ Exception -> 0x00d4 }
            if (r9 == 0) goto L_0x00a1
            java.lang.String r9 = r4.getString(r8)     // Catch:{ Exception -> 0x0094 }
            if (r9 == 0) goto L_0x008a
            java.lang.String r9 = r4.getColumnName(r8)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r11 = r4.getString(r8)     // Catch:{ Exception -> 0x0094 }
            r7.addProperty((java.lang.String) r9, (java.lang.String) r11)     // Catch:{ Exception -> 0x0094 }
            goto L_0x0093
        L_0x008a:
            java.lang.String r9 = r4.getColumnName(r8)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r11 = ""
            r7.addProperty((java.lang.String) r9, (java.lang.String) r11)     // Catch:{ Exception -> 0x0094 }
        L_0x0093:
            goto L_0x00a1
        L_0x0094:
            r9 = move-exception
            r9.printStackTrace()     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r11 = r9.getMessage()     // Catch:{ Exception -> 0x00d4 }
            java.lang.Object[] r12 = new java.lang.Object[r10]     // Catch:{ Exception -> 0x00d4 }
            timber.log.a.c(r11, r12)     // Catch:{ Exception -> 0x00d4 }
        L_0x00a1:
            int r8 = r8 + 1
            goto L_0x0070
        L_0x00a4:
            r5.add((com.google.gson.JsonElement) r7)     // Catch:{ Exception -> 0x00d4 }
            r4.moveToNext()     // Catch:{ Exception -> 0x00d4 }
            goto L_0x0060
        L_0x00ac:
            r4.close()     // Catch:{ Exception -> 0x00d4 }
            com.leedarson.serviceimpl.database.manager.DatabaseResultBean r6 = new com.leedarson.serviceimpl.database.manager.DatabaseResultBean     // Catch:{ Exception -> 0x00d4 }
            r6.<init>()     // Catch:{ Exception -> 0x00d4 }
            r7 = 200(0xc8, float:2.8E-43)
            r6.setCode(r7)     // Catch:{ Exception -> 0x00d4 }
            r6.setData(r5)     // Catch:{ Exception -> 0x00d4 }
            org.greenrobot.eventbus.c r7 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x00d4 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r8 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r9 = com.leedarson.serviceimpl.database.manager.d.b(r6)     // Catch:{ Exception -> 0x00d4 }
            r8.<init>(r14, r9)     // Catch:{ Exception -> 0x00d4 }
            r7.l(r8)     // Catch:{ Exception -> 0x00d4 }
        L_0x00cc:
            if (r4 == 0) goto L_0x00e9
        L_0x00ce:
            r4.close()     // Catch:{ Exception -> 0x0119 }
            goto L_0x00e9
        L_0x00d2:
            r5 = move-exception
            goto L_0x00ea
        L_0x00d4:
            r5 = move-exception
            r5.printStackTrace()     // Catch:{ all -> 0x00d2 }
            org.greenrobot.eventbus.c r6 = org.greenrobot.eventbus.c.c()     // Catch:{ all -> 0x00d2 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r7 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ all -> 0x00d2 }
            java.lang.String r8 = "{\"code\":-1000}"
            r7.<init>(r14, r8)     // Catch:{ all -> 0x00d2 }
            r6.l(r7)     // Catch:{ all -> 0x00d2 }
            if (r4 == 0) goto L_0x00e9
            goto L_0x00ce
        L_0x00e9:
            goto L_0x00fa
        L_0x00ea:
            if (r4 == 0) goto L_0x00ef
            r4.close()     // Catch:{ Exception -> 0x0119 }
        L_0x00ef:
            throw r5     // Catch:{ Exception -> 0x0119 }
        L_0x00f1:
            java.lang.String r4 = "sql is null"
            org.json.JSONObject r4 = com.leedarson.base.utils.p.a(r2, r4)     // Catch:{ Exception -> 0x0119 }
            r1.l(r14, r4)     // Catch:{ Exception -> 0x0119 }
        L_0x00fa:
            goto L_0x0118
        L_0x00fb:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0119 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r4 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x0119 }
            java.lang.String r5 = "{\"code\":-2000}"
            r4.<init>(r14, r5)     // Catch:{ Exception -> 0x0119 }
            r0.l(r4)     // Catch:{ Exception -> 0x0119 }
            goto L_0x0118
        L_0x010a:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0119 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r4 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x0119 }
            java.lang.String r5 = "{\"code\":-3000}"
            r4.<init>(r14, r5)     // Catch:{ Exception -> 0x0119 }
            r0.l(r4)     // Catch:{ Exception -> 0x0119 }
        L_0x0118:
            goto L_0x0139
        L_0x0119:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "ex:"
            r3.append(r4)
            java.lang.String r4 = r0.getMessage()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            org.json.JSONObject r2 = com.leedarson.base.utils.p.a(r2, r3)
            r1.l(r14, r2)
        L_0x0139:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.database.DatabaseServiceImpl.m(java.lang.String, java.lang.String):void");
    }

    public void execSQL(String sql) {
        if (!PatchProxy.proxy(new Object[]{sql}, this, changeQuickRedirect, false, 7838, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                com.leedarson.serviceimpl.database.manager.a.b(this.b).a().getDatabase().execSQL(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0090, code lost:
        if (r1 != null) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0092, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009c, code lost:
        if (r1 == null) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009f, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.gson.JsonArray rawQuery(java.lang.String r11) {
        /*
            r10 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r11
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r8] = r0
            java.lang.Class<com.google.gson.JsonArray> r7 = com.google.gson.JsonArray.class
            r4 = 0
            r5 = 7839(0x1e9f, float:1.0985E-41)
            r2 = r10
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0021
            java.lang.Object r11 = r0.result
            com.google.gson.JsonArray r11 = (com.google.gson.JsonArray) r11
            return r11
        L_0x0021:
            r0 = r10
            r1 = 0
            com.google.gson.JsonArray r2 = new com.google.gson.JsonArray
            r2.<init>()
            android.content.Context r3 = r0.b     // Catch:{ Exception -> 0x0098 }
            com.leedarson.serviceimpl.database.manager.a r3 = com.leedarson.serviceimpl.database.manager.a.b(r3)     // Catch:{ Exception -> 0x0098 }
            com.leedarson.serviceimpl.database.manager.c r3 = r3.a()     // Catch:{ Exception -> 0x0098 }
            org.greenrobot.greendao.database.Database r3 = r3.getDatabase()     // Catch:{ Exception -> 0x0098 }
            r4 = 0
            android.database.Cursor r3 = r3.rawQuery(r11, r4)     // Catch:{ Exception -> 0x0098 }
            r1 = r3
            if (r1 == 0) goto L_0x0090
            r1.moveToFirst()     // Catch:{ Exception -> 0x0098 }
        L_0x0041:
            boolean r3 = r1.isAfterLast()     // Catch:{ Exception -> 0x0098 }
            if (r3 != 0) goto L_0x008d
            int r3 = r1.getColumnCount()     // Catch:{ Exception -> 0x0098 }
            com.google.gson.JsonObject r4 = new com.google.gson.JsonObject     // Catch:{ Exception -> 0x0098 }
            r4.<init>()     // Catch:{ Exception -> 0x0098 }
            r5 = 0
        L_0x0051:
            if (r5 >= r3) goto L_0x0085
            java.lang.String r6 = r1.getColumnName(r5)     // Catch:{ Exception -> 0x0098 }
            if (r6 == 0) goto L_0x0082
            java.lang.String r6 = r1.getString(r5)     // Catch:{ Exception -> 0x0075 }
            if (r6 == 0) goto L_0x006b
            java.lang.String r6 = r1.getColumnName(r5)     // Catch:{ Exception -> 0x0075 }
            java.lang.String r7 = r1.getString(r5)     // Catch:{ Exception -> 0x0075 }
            r4.addProperty((java.lang.String) r6, (java.lang.String) r7)     // Catch:{ Exception -> 0x0075 }
            goto L_0x0074
        L_0x006b:
            java.lang.String r6 = r1.getColumnName(r5)     // Catch:{ Exception -> 0x0075 }
            java.lang.String r7 = ""
            r4.addProperty((java.lang.String) r6, (java.lang.String) r7)     // Catch:{ Exception -> 0x0075 }
        L_0x0074:
            goto L_0x0082
        L_0x0075:
            r6 = move-exception
            r6.printStackTrace()     // Catch:{ Exception -> 0x0098 }
            java.lang.String r7 = r6.getMessage()     // Catch:{ Exception -> 0x0098 }
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x0098 }
            timber.log.a.c(r7, r9)     // Catch:{ Exception -> 0x0098 }
        L_0x0082:
            int r5 = r5 + 1
            goto L_0x0051
        L_0x0085:
            r2.add((com.google.gson.JsonElement) r4)     // Catch:{ Exception -> 0x0098 }
            r1.moveToNext()     // Catch:{ Exception -> 0x0098 }
            goto L_0x0041
        L_0x008d:
            r1.close()     // Catch:{ Exception -> 0x0098 }
        L_0x0090:
            if (r1 == 0) goto L_0x009f
        L_0x0092:
            r1.close()
            goto L_0x009f
        L_0x0096:
            r3 = move-exception
            goto L_0x00a0
        L_0x0098:
            r3 = move-exception
            r3.printStackTrace()     // Catch:{ all -> 0x0096 }
            if (r1 == 0) goto L_0x009f
            goto L_0x0092
        L_0x009f:
            return r2
        L_0x00a0:
            if (r1 == 0) goto L_0x00a5
            r1.close()
        L_0x00a5:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.database.DatabaseServiceImpl.rawQuery(java.lang.String):com.google.gson.JsonArray");
    }

    public void preCreateDb() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7840, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.database.manager.a.b(this.b);
        }
    }

    public void l(String callbackKey, JSONObject jsonObject) {
        Class[] clsArr = {String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{callbackKey, jsonObject}, this, changeQuickRedirect, false, 7841, clsArr, Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject.toString()));
        }
    }
}
