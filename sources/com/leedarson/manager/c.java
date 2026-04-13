package com.leedarson.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import com.leedarson.base.utils.r;
import com.leedarson.serviceimpl.tcp.manager.INettyManager;
import com.leedarson.serviceinterface.DatabaseService;
import com.leedarson.serviceinterface.utils.PlayBackCacheUtils;
import com.leedarson.tcp.ipc.g;
import com.leedarson.tcp.ipc.h;
import com.leedarson.utils.j;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: LdsPlayBackManager */
public class c {
    private static c a = null;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public int b = 100;
    private ExecutorService c;
    private Future d;
    private Future e;
    private ExecutorService f;
    private ExecutorService g;
    /* access modifiers changed from: private */
    public boolean h = false;
    /* access modifiers changed from: private */
    public boolean i = true;
    /* access modifiers changed from: private */
    public boolean j = false;
    private com.leedarson.smartcamera.codec.c k;
    private boolean l = false;
    /* access modifiers changed from: private */
    public d m;
    /* access modifiers changed from: private */
    public long n = 0;
    /* access modifiers changed from: private */
    public long o = 0;
    /* access modifiers changed from: private */
    public boolean p = false;
    /* access modifiers changed from: private */
    public boolean q = true;
    private int r = 35;
    int s = 17;
    int t = 0;
    long u = 0;
    long v = 0;

    static /* synthetic */ long k(c x0, long x1) {
        long j2 = x0.n + x1;
        x0.n = j2;
        return j2;
    }

    static /* synthetic */ void o(c x0, byte[] x1) {
        Class[] clsArr = {c.class, byte[].class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 1421, clsArr, Void.TYPE).isSupported) {
            x0.s(x1);
        }
    }

    private c() {
        ThreadFactory namedThreadFactory = new r("cloud-man-pool");
        this.c = Executors.newSingleThreadExecutor(namedThreadFactory);
        this.f = Executors.newSingleThreadExecutor(namedThreadFactory);
        this.g = Executors.newSingleThreadExecutor(namedThreadFactory);
    }

    public static c r() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1411, new Class[0], c.class);
        return proxy.isSupported ? (c) proxy.result : new c();
    }

    public static c u() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1412, new Class[0], c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        if (a == null) {
            synchronized (c.class) {
                if (a == null) {
                    a = new c();
                }
            }
        }
        return a;
    }

    public void B(int frameDelayTime) {
        this.r = frameDelayTime;
    }

    public void A(com.leedarson.smartcamera.codec.c ldsCodec) {
        this.k = ldsCodec;
    }

    public void C(boolean pause) {
        this.l = pause;
    }

    public boolean w() {
        return this.i;
    }

    public void D(boolean saveToCache) {
        this.q = saveToCache;
    }

    public void E(String str, String str2, int i2, String str3, int i3, com.leedarson.tcp.callback.a aVar) {
        Class<String> cls = String.class;
        Object[] objArr = {str, str2, new Integer(i2), str3, new Integer(i3), aVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1413, new Class[]{cls, cls, cls2, cls, cls2, com.leedarson.tcp.callback.a.class}, Void.TYPE).isSupported) {
            String clientId = str2;
            com.leedarson.tcp.callback.a callback = aVar;
            String token = str3;
            String deviceId = str;
            int taskId = i2;
            int heartbeat = i3;
            g head = new g();
            head.u(256);
            head.r(new Random().nextInt(9999999));
            head.k(257);
            head.s(1);
            head.l(2);
            head.t(System.currentTimeMillis());
            head.m(1005);
            head.n(0);
            head.q(4);
            head.p(2);
            try {
                JSONObject msgObj = new JSONObject();
                msgObj.put("clientId", (Object) clientId);
                msgObj.put("taskId", taskId);
                msgObj.put("heartbeat", heartbeat);
                msgObj.put("token", (Object) token);
                INettyManager.h().i(deviceId, new h(head, msgObj.toString().getBytes("UTF-8")), callback);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: LdsPlayBackManager */
    public class a implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ long d;
        final /* synthetic */ Context f;
        final /* synthetic */ String q;
        final /* synthetic */ d x;

        a(int i, long j, Context context, String str, d dVar) {
            this.c = i;
            this.d = j;
            this.f = context;
            this.q = str;
            this.x = dVar;
        }

        @SuppressLint({"DefaultLocale"})
        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1422, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Log.d("LdsPlayBackManager task", "getCloudRecordStream start! " + this.c + " time:" + this.d);
            boolean unused = c.this.h = false;
            boolean unused2 = c.this.i = false;
            if (this.c == 0 && c.this.q) {
                boolean unused3 = c.this.j = false;
                Context context = this.f;
                String str = this.q;
                PlayBackCacheUtils.createDevCacheFile(context, str, this.d + "");
                long unused4 = c.this.o = 0;
                long unused5 = c.this.n = 0;
                DatabaseService databaseService = (DatabaseService) com.alibaba.android.arouter.launcher.a.c().g(DatabaseService.class);
                if (databaseService != null) {
                    databaseService.execSQL(String.format(Locale.US, "DELETE FROM t_c_video_time WHERE _devId='%s' AND _startTime=%d", new Object[]{this.q, Long.valueOf(this.d)}));
                }
            }
            int seq = new Random().nextInt(9999999);
            g head = new g();
            head.u(256);
            head.r(seq);
            head.k(265);
            head.s(1);
            head.l(2);
            head.t(System.currentTimeMillis());
            head.m(1005);
            head.n(0);
            head.q(4);
            head.p(2);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("begin", this.d);
                jsonObject.put(IjkMediaMeta.IJKM_KEY_TYPE, this.c);
                jsonObject.put("framenums", c.this.b);
                jsonObject.put("speed", 1);
                jsonObject.put("networkqos", 1);
                jsonObject.put("playId", 1);
                INettyManager.h().j(this.q, new h(head, jsonObject.toString().getBytes("UTF-8")), new C0094a());
            } catch (Exception e) {
                e.printStackTrace();
                d dVar = this.x;
                dVar.onFail("CloudStreamJsonParseError=" + e.toString());
            }
            Log.d("LdsPlayBackManager task", "getCloudRecordStream end222! ");
            return null;
        }

        /* renamed from: com.leedarson.manager.c$a$a  reason: collision with other inner class name */
        /* compiled from: LdsPlayBackManager */
        public class C0094a implements com.leedarson.tcp.callback.a {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0094a() {
            }

            public void a(Object o, boolean z) {
                d dVar;
                if (!PatchProxy.proxy(new Object[]{o, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 1423, new Class[]{Object.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                    boolean z2 = z;
                    h resq = (h) o;
                    a aVar = a.this;
                    if (aVar.c == 0 && !c.this.j && (dVar = a.this.x) != null) {
                        dVar.onSuccess();
                        boolean unused = c.this.j = true;
                    }
                    if (resq.a().a() == 273) {
                        if (c.this.q) {
                            byte[] data = resq.b();
                            byte[] tbs = new byte[8];
                            System.arraycopy(data, 4, tbs, 0, 8);
                            long getTimeSec = com.leedarson.serviceimpl.tcp.manager.c.a(tbs);
                            a aVar2 = a.this;
                            Context context = aVar2.f;
                            String str = aVar2.q;
                            StringBuilder sb = new StringBuilder();
                            byte[] bArr = tbs;
                            sb.append(a.this.d);
                            sb.append("");
                            PlayBackCacheUtils.writeCacheFile(context, str, sb.toString(), resq.b());
                            if (getTimeSec > 0 && c.this.o != getTimeSec) {
                                long unused2 = c.this.o = getTimeSec;
                                d dVar2 = a.this.x;
                                if (dVar2 != null) {
                                    dVar2.onGetCacheTimestamp(getTimeSec);
                                }
                                DatabaseService databaseService = (DatabaseService) com.alibaba.android.arouter.launcher.a.c().g(DatabaseService.class);
                                if (databaseService != null) {
                                    Locale locale = Locale.US;
                                    a aVar3 = a.this;
                                    databaseService.execSQL(String.format(locale, "INSERT INTO t_c_video_time (_devId, _startTime, _playTime, _position) VALUES('%s', %d,%d,%d)", new Object[]{aVar3.q, Long.valueOf(aVar3.d), Long.valueOf(getTimeSec), Long.valueOf(c.this.n)}));
                                }
                            }
                            c.k(c.this, (long) data.length);
                        }
                        try {
                            com.leedarson.smartcamera.utils.g.d().h(resq.b());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (resq.a().h() != 0 && resq.a().h() == 1) {
                            a aVar4 = a.this;
                            c.this.t(aVar4.f, aVar4.q, aVar4.d, 1, aVar4.x);
                        }
                    } else if (resq.a().a() != 272) {
                    } else {
                        if (resq.a().f() == -15520 || resq.a().f() == -15528) {
                            timber.log.a.g("LdsPlayBackManager").h("ACTION_GETRECORD: complete", new Object[0]);
                            if (c.this.q) {
                                a aVar5 = a.this;
                                Context context2 = aVar5.f;
                                String str2 = aVar5.q;
                                PlayBackCacheUtils.writeCacheFileComplete(context2, str2, a.this.d + "");
                                INettyManager.h().g(a.this.q, new C0095a());
                            }
                            boolean unused3 = c.this.h = true;
                            d dVar3 = a.this.x;
                            if (dVar3 != null) {
                                dVar3.b();
                            }
                        }
                    }
                }
            }

            /* renamed from: com.leedarson.manager.c$a$a$a  reason: collision with other inner class name */
            /* compiled from: LdsPlayBackManager */
            public class C0095a implements com.leedarson.tcp.b {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0095a() {
                }

                public void onSuccess() {
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1426, new Class[0], Void.TYPE).isSupported) {
                        Log.d("LdsPlayBackManager", "disconnect onSuccess: ");
                    }
                }
            }

            public void onFailure(int code, String errorStr) {
                d dVar;
                Object[] objArr = {new Integer(code), errorStr};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1424, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported && (dVar = a.this.x) != null) {
                    dVar.onFail("sendTcpCommand:  code=" + code + "  errorStr=" + errorStr);
                }
            }
        }
    }

    public void t(Context context, String str, long startTime, int i2, d dVar) {
        if (!PatchProxy.proxy(new Object[]{context, str, new Long(startTime), new Integer(i2), dVar}, this, changeQuickRedirect, false, 1414, new Class[]{Context.class, String.class, Long.TYPE, Integer.TYPE, d.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            d listener = dVar;
            int type = i2;
            this.m = listener;
            this.e = this.f.submit(new a(type, startTime, context, deviceId, listener));
        }
    }

    /* compiled from: LdsPlayBackManager */
    public class b implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Context c;
        final /* synthetic */ String d;
        final /* synthetic */ long f;
        final /* synthetic */ int q;
        final /* synthetic */ d x;
        final /* synthetic */ int y;

        b(Context context, String str, long j, int i, d dVar, int i2) {
            this.c = context;
            this.d = str;
            this.f = j;
            this.q = i;
            this.x = dVar;
            this.y = i2;
        }

        /* JADX WARNING: Removed duplicated region for block: B:37:0x0167 A[Catch:{ Exception -> 0x016d }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object call() {
            /*
                r18 = this;
                java.lang.String r0 = "_position"
                r1 = 0
                java.lang.Object[] r2 = new java.lang.Object[r1]
                com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
                java.lang.Class[] r7 = new java.lang.Class[r1]
                java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
                r5 = 0
                r6 = 1427(0x593, float:2.0E-42)
                r3 = r18
                com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
                boolean r3 = r2.isSupported
                if (r3 == 0) goto L_0x001b
                java.lang.Object r0 = r2.result
                return r0
            L_0x001b:
                r2 = r18
                com.alibaba.android.arouter.launcher.a r3 = com.alibaba.android.arouter.launcher.a.c()
                java.lang.Class<com.leedarson.serviceinterface.DatabaseService> r4 = com.leedarson.serviceinterface.DatabaseService.class
                java.lang.Object r3 = r3.g(r4)
                com.leedarson.serviceinterface.DatabaseService r3 = (com.leedarson.serviceinterface.DatabaseService) r3
                android.content.Context r4 = r2.c
                java.lang.String r5 = r2.d
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                long r7 = r2.f
                r6.append(r7)
                java.lang.String r7 = ""
                r6.append(r7)
                java.lang.String r6 = r6.toString()
                boolean r4 = com.leedarson.serviceinterface.utils.PlayBackCacheUtils.isCacheFileExit(r4, r5, r6)
                java.lang.String r5 = "LdsPlayBackManager"
                if (r4 == 0) goto L_0x017b
                if (r3 == 0) goto L_0x017b
                android.content.Context r4 = r2.c
                java.lang.String r6 = r2.d
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                long r9 = r2.f
                r8.append(r9)
                r8.append(r7)
                java.lang.String r8 = r8.toString()
                byte[] r4 = com.leedarson.serviceinterface.utils.PlayBackCacheUtils.readCacheFile(r4, r6, r8)
                java.lang.String r6 = "SELECT * FROM t_c_video_time WHERE _startTime=%d AND _playTime>=%d ORDER BY _id limit 0,1"
                java.util.Locale r8 = java.util.Locale.US
                r9 = 2
                java.lang.Object[] r10 = new java.lang.Object[r9]
                long r11 = r2.f
                java.lang.Long r11 = java.lang.Long.valueOf(r11)
                r10[r1] = r11
                long r11 = r2.f
                int r13 = r2.q
                long r13 = (long) r13
                long r11 = r11 + r13
                java.lang.Long r11 = java.lang.Long.valueOf(r11)
                r12 = 1
                r10[r12] = r11
                java.lang.String r10 = java.lang.String.format(r8, r6, r10)
                com.google.gson.JsonArray r10 = r3.rawQuery(r10)
                int r11 = r10.size()
                if (r11 <= 0) goto L_0x0179
                org.json.JSONObject r11 = new org.json.JSONObject     // Catch:{ Exception -> 0x0173 }
                com.google.gson.JsonElement r13 = r10.get(r1)     // Catch:{ Exception -> 0x0173 }
                java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0173 }
                r11.<init>((java.lang.String) r13)     // Catch:{ Exception -> 0x0173 }
                java.lang.String r13 = r11.getString(r0)     // Catch:{ Exception -> 0x0173 }
                int r13 = java.lang.Integer.parseInt(r13)     // Catch:{ Exception -> 0x0173 }
                java.lang.String r14 = "_playTime"
                java.lang.String r14 = r11.getString(r14)     // Catch:{ Exception -> 0x0173 }
                int r14 = r14.length()     // Catch:{ Exception -> 0x0173 }
                r15 = 13
                if (r14 != r15) goto L_0x011c
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0119 }
                r14.<init>()     // Catch:{ Exception -> 0x0119 }
                r16 = r13
                long r12 = r2.f     // Catch:{ Exception -> 0x0119 }
                r14.append(r12)     // Catch:{ Exception -> 0x0119 }
                r14.append(r7)     // Catch:{ Exception -> 0x0119 }
                java.lang.String r7 = r14.toString()     // Catch:{ Exception -> 0x0119 }
                int r7 = r7.length()     // Catch:{ Exception -> 0x0119 }
                r12 = 10
                if (r7 != r12) goto L_0x0116
                java.lang.Object[] r7 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x0119 }
                long r12 = r2.f     // Catch:{ Exception -> 0x0119 }
                java.lang.Long r9 = java.lang.Long.valueOf(r12)     // Catch:{ Exception -> 0x0119 }
                r7[r1] = r9     // Catch:{ Exception -> 0x0119 }
                long r12 = r2.f     // Catch:{ Exception -> 0x0119 }
                int r9 = r2.q     // Catch:{ Exception -> 0x0119 }
                r17 = r2
                long r1 = (long) r9
                long r12 = r12 + r1
                r1 = 1000(0x3e8, double:4.94E-321)
                long r12 = r12 * r1
                java.lang.Long r1 = java.lang.Long.valueOf(r12)     // Catch:{ Exception -> 0x0112 }
                r2 = 1
                r7[r2] = r1     // Catch:{ Exception -> 0x0112 }
                java.lang.String r1 = java.lang.String.format(r8, r6, r7)     // Catch:{ Exception -> 0x0112 }
                com.google.gson.JsonArray r1 = r3.rawQuery(r1)     // Catch:{ Exception -> 0x0112 }
                r10 = r1
                int r1 = r10.size()     // Catch:{ Exception -> 0x0112 }
                if (r1 <= 0) goto L_0x010f
                org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0112 }
                r2 = 0
                com.google.gson.JsonElement r7 = r10.get(r2)     // Catch:{ Exception -> 0x0112 }
                java.lang.String r2 = r7.toString()     // Catch:{ Exception -> 0x0112 }
                r1.<init>((java.lang.String) r2)     // Catch:{ Exception -> 0x0112 }
                r11 = r1
                java.lang.String r0 = r11.getString(r0)     // Catch:{ Exception -> 0x0112 }
                int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0112 }
                r13 = r0
                goto L_0x0122
            L_0x010f:
                r13 = r16
                goto L_0x0122
            L_0x0112:
                r0 = move-exception
                r1 = r17
                goto L_0x0175
            L_0x0116:
                r17 = r2
                goto L_0x0120
            L_0x0119:
                r0 = move-exception
                r1 = r2
                goto L_0x0175
            L_0x011c:
                r17 = r2
                r16 = r13
            L_0x0120:
                r13 = r16
            L_0x0122:
                int r0 = r4.length     // Catch:{ Exception -> 0x016f }
                int r0 = r0 - r13
                byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x016f }
                int r1 = r4.length     // Catch:{ Exception -> 0x016f }
                int r1 = r1 - r13
                r2 = 0
                java.lang.System.arraycopy(r4, r13, r0, r2, r1)     // Catch:{ Exception -> 0x016f }
                timber.log.a$b r1 = timber.log.a.g(r5)     // Catch:{ Exception -> 0x016f }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x016f }
                r2.<init>()     // Catch:{ Exception -> 0x016f }
                int r5 = r4.length     // Catch:{ Exception -> 0x016f }
                r2.append(r5)     // Catch:{ Exception -> 0x016f }
                java.lang.String r5 = " ACTION_GETRECORD: seek: position="
                r2.append(r5)     // Catch:{ Exception -> 0x016f }
                r2.append(r13)     // Catch:{ Exception -> 0x016f }
                java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x016f }
                r5 = 0
                java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x016f }
                r1.h(r2, r7)     // Catch:{ Exception -> 0x016f }
                r1 = r17
                com.leedarson.manager.c r2 = com.leedarson.manager.c.this     // Catch:{ Exception -> 0x016d }
                boolean unused = r2.i = r5     // Catch:{ Exception -> 0x016d }
                com.leedarson.manager.c r2 = com.leedarson.manager.c.this     // Catch:{ Exception -> 0x016d }
                r5 = 1
                boolean unused = r2.p = r5     // Catch:{ Exception -> 0x016d }
                com.leedarson.manager.c r2 = com.leedarson.manager.c.this     // Catch:{ Exception -> 0x016d }
                com.leedarson.manager.c.o(r2, r0)     // Catch:{ Exception -> 0x016d }
                com.leedarson.manager.c r2 = com.leedarson.manager.c.this     // Catch:{ Exception -> 0x016d }
                r5 = 1
                boolean unused = r2.i = r5     // Catch:{ Exception -> 0x016d }
                com.leedarson.manager.d r2 = r1.x     // Catch:{ Exception -> 0x016d }
                if (r2 == 0) goto L_0x016a
                r2.a()     // Catch:{ Exception -> 0x016d }
            L_0x016a:
                r2 = 0
                goto L_0x017a
            L_0x016d:
                r0 = move-exception
                goto L_0x0175
            L_0x016f:
                r0 = move-exception
                r1 = r17
                goto L_0x0175
            L_0x0173:
                r0 = move-exception
                r1 = r2
            L_0x0175:
                r0.printStackTrace()
                goto L_0x017a
            L_0x0179:
                r1 = r2
            L_0x017a:
                goto L_0x01af
            L_0x017b:
                r1 = r2
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r2 = "1111getSeekStream: "
                r0.append(r2)
                long r6 = r1.f
                r0.append(r6)
                java.lang.String r2 = "=="
                r0.append(r2)
                int r2 = r1.q
                r0.append(r2)
                java.lang.String r0 = r0.toString()
                android.util.Log.d(r5, r0)
                com.leedarson.manager.c r6 = com.leedarson.manager.c.this
                android.content.Context r7 = r1.c
                java.lang.String r8 = r1.d
                long r4 = r1.f
                int r0 = r1.q
                long r9 = (long) r0
                long r9 = r9 + r4
                int r11 = r1.y
                com.leedarson.manager.d r12 = r1.x
                r6.t(r7, r8, r9, r11, r12)
            L_0x01af:
                r0 = 0
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.manager.c.b.call():java.lang.Object");
        }
    }

    public void v(Context context, String deviceId, long startTime, int seekTime, int type, d dVar) {
        Object[] objArr = {context, deviceId, new Long(startTime), new Integer(seekTime), new Integer(type), dVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {Context.class, String.class, Long.TYPE, cls, cls, d.class};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1415, clsArr, Void.TYPE).isSupported) {
            d listener = dVar;
            this.m = listener;
            this.e = this.f.submit(new b(context, deviceId, startTime, seekTime, listener, type));
        }
    }

    /* renamed from: com.leedarson.manager.c$c  reason: collision with other inner class name */
    /* compiled from: LdsPlayBackManager */
    public class C0096c implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0096c() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1428, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Log.d("LdsPlayBackManager task", "parseAndDecode start! ");
            while (true) {
                if (!c.this.p) {
                    break;
                } else if (c.this.h && com.leedarson.smartcamera.utils.g.d().f() == 0) {
                    boolean unused = c.this.p = false;
                    Log.d("LdsPlayBackManager task", "parseAndDecode end! ");
                    boolean unused2 = c.this.i = true;
                    if (c.this.m != null) {
                        c.this.m.a();
                    }
                } else if (com.leedarson.smartcamera.utils.g.d().f() != 0) {
                    c.o(c.this, com.leedarson.smartcamera.utils.g.d().e());
                }
            }
            Log.d("LdsPlayBackManager task", "parseAndDecode end222! ");
            return null;
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1416, new Class[0], Void.TYPE).isSupported) {
            this.p = true;
            this.d = this.c.submit(new C0096c());
        }
    }

    private void s(byte[] bArr) {
        int i2;
        int payloadLen;
        int payloadLen2;
        int i3 = 0;
        if (!PatchProxy.proxy(new Object[]{bArr}, this, changeQuickRedirect, false, 1417, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            byte[] data = bArr;
            int hasDecode = 0;
            while (data.length - hasDecode >= 17 && this.p) {
                if (!this.l) {
                    try {
                        if (data[hasDecode] == 1 && data[hasDecode + 1] == 1) {
                            this.s = 22;
                            this.t = data[hasDecode + 17];
                        } else {
                            this.t = i3;
                            this.s = 17;
                        }
                        byte frameType = data[hasDecode + 2];
                        byte audioCodec = data[hasDecode + 3];
                        byte[] tbs = new byte[8];
                        System.arraycopy(data, hasDecode + 4, tbs, i3, 8);
                        long timeStamp1 = j.d(tbs);
                        int temp = 13 - (timeStamp1 + "").length();
                        if (temp > 0) {
                            for (int i4 = 0; i4 < temp; i4++) {
                                timeStamp1 *= 10;
                            }
                        }
                        byte encryptionType = data[hasDecode + 12];
                        byte[] pbs = new byte[4];
                        System.arraycopy(data, hasDecode + 13, pbs, i3, 4);
                        int payloadLen3 = j.c(pbs);
                        if (encryptionType == 0) {
                            if (frameType == 5) {
                                byte[] abs = new byte[payloadLen3];
                                System.arraycopy(data, this.s + hasDecode, abs, i3, payloadLen3);
                                long timeStamp12 = timeStamp1;
                                long dif = System.currentTimeMillis() - this.u;
                                if (dif < 22) {
                                    SystemClock.sleep(22 - dif);
                                }
                                if (audioCodec == 0) {
                                    com.leedarson.smartcamera.codec.c cVar = this.k;
                                    if (cVar != null) {
                                        payloadLen2 = payloadLen3;
                                        byte[] bArr2 = pbs;
                                        cVar.v(timeStamp12, abs, abs.length, 1);
                                    } else {
                                        payloadLen2 = payloadLen3;
                                        byte[] bArr3 = abs;
                                        byte[] bArr4 = pbs;
                                    }
                                    this.u = System.currentTimeMillis();
                                } else {
                                    payloadLen2 = payloadLen3;
                                    byte[] abs2 = abs;
                                    byte[] bArr5 = pbs;
                                    if (audioCodec == 1) {
                                        com.leedarson.smartcamera.codec.c cVar2 = this.k;
                                        if (cVar2 != null) {
                                            byte[] abs3 = abs2;
                                            cVar2.v(timeStamp12, abs3, abs3.length, 2);
                                        }
                                        this.u = System.currentTimeMillis();
                                    }
                                }
                            } else {
                                long timeStamp13 = timeStamp1;
                                payloadLen2 = payloadLen3;
                                byte[] bArr6 = pbs;
                                if (!(frameType == 2 || frameType == 3)) {
                                    if (frameType == 4) {
                                    }
                                }
                                int length = data.length - hasDecode;
                                int i5 = this.s;
                                payloadLen = payloadLen2;
                                if (length >= payloadLen + i5) {
                                    byte[] abs4 = new byte[payloadLen];
                                    System.arraycopy(data, i5 + hasDecode, abs4, 0, payloadLen);
                                    byte b2 = frameType;
                                    byte b3 = audioCodec;
                                    long dif2 = System.currentTimeMillis() - this.v;
                                    int i6 = this.r;
                                    if (dif2 < ((long) i6)) {
                                        SystemClock.sleep(((long) i6) - dif2);
                                    }
                                    int i7 = this.t;
                                    if (i7 == 0) {
                                        com.leedarson.smartcamera.codec.c cVar3 = this.k;
                                        if (cVar3 != null) {
                                            i2 = 0;
                                            cVar3.Z(timeStamp13, abs4, abs4.length, 1);
                                        } else {
                                            i2 = 0;
                                        }
                                        this.v = System.currentTimeMillis();
                                    } else {
                                        i2 = 0;
                                        if (i7 == 1) {
                                            com.leedarson.smartcamera.codec.c cVar4 = this.k;
                                            if (cVar4 != null) {
                                                cVar4.Z(timeStamp13, abs4, abs4.length, 2);
                                            }
                                            this.v = System.currentTimeMillis();
                                        }
                                    }
                                } else {
                                    byte b4 = audioCodec;
                                    i2 = 0;
                                }
                            }
                            byte b5 = frameType;
                            byte b6 = audioCodec;
                            payloadLen = payloadLen2;
                            i2 = 0;
                        } else {
                            i2 = i3;
                            long j2 = timeStamp1;
                            payloadLen = payloadLen3;
                            byte[] bArr7 = pbs;
                            byte b7 = audioCodec;
                        }
                        hasDecode += payloadLen + this.s;
                        i3 = i2;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    int i8 = i3;
                    SystemClock.sleep(50);
                }
            }
            Log.v("LdsPlayBackManager", "decodeStream end");
        }
    }

    public void y(Context context, String deviceId, String s2) {
        Class<String> cls = String.class;
        Class[] clsArr = {Context.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{context, deviceId, s2}, this, changeQuickRedirect, false, 1418, clsArr, Void.TYPE).isSupported) {
            Log.v("LdsPlayBackManager", "readParseCacheFile: ");
            this.p = true;
            this.d = this.c.submit(new d(context, deviceId, s2));
        }
    }

    /* compiled from: LdsPlayBackManager */
    public class d implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Context c;
        final /* synthetic */ String d;
        final /* synthetic */ String f;

        d(Context context, String str, String str2) {
            this.c = context;
            this.d = str;
            this.f = str2;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1429, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Log.d("LdsPlayBackManager task", "readParseCacheFile start! ");
            boolean unused = c.this.i = false;
            c.o(c.this, PlayBackCacheUtils.readCacheFile(this.c, this.d, this.f));
            boolean unused2 = c.this.i = true;
            if (c.this.m != null) {
                c.this.m.a();
            }
            Log.d("LdsPlayBackManager task", "readParseCacheFile end222! ");
            return null;
        }
    }

    public void z(String deviceId, boolean needDisconnect) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Byte(needDisconnect ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 1419, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            Log.d("LdsPlayBackManager task", "releaseTask start! ");
            if (needDisconnect) {
                INettyManager.h().g(deviceId, (com.leedarson.tcp.b) null);
            }
            INettyManager.h().d();
            this.p = false;
            Future future = this.e;
            if (future != null && !future.isCancelled()) {
                this.e.cancel(true);
            }
            Future future2 = this.d;
            if (future2 != null && !future2.isCancelled()) {
                this.d.cancel(true);
            }
            com.leedarson.smartcamera.utils.g.d().b();
            this.l = false;
        }
    }

    /* compiled from: LdsPlayBackManager */
    public class e implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Surface c;

        e(Surface surface) {
            this.c = surface;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1430, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            try {
                EGLDisplay display = EGL14.eglGetDisplay(0);
                int[] version = new int[2];
                EGL14.eglInitialize(display, version, 0, version, 1);
                EGLConfig[] configs = new EGLConfig[1];
                EGLDisplay eGLDisplay = display;
                EGLConfig[] eGLConfigArr = configs;
                EGL14.eglChooseConfig(eGLDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12344, 0, 12344}, 0, eGLConfigArr, 0, configs.length, new int[1], 0);
                EGLConfig config = configs[0];
                EGLContext context = EGL14.eglCreateContext(display, config, EGL14.EGL_NO_CONTEXT, new int[]{12440, 2, 12344}, 0);
                EGLSurface eglSurface = EGL14.eglCreateWindowSurface(display, config, this.c, new int[]{12344}, 0);
                EGL14.eglMakeCurrent(display, eglSurface, eglSurface, context);
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(16384);
                EGL14.eglSwapBuffers(display, eglSurface);
                EGL14.eglDestroySurface(display, eglSurface);
                EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
                EGL14.eglMakeCurrent(display, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
                EGL14.eglDestroyContext(display, context);
                EGL14.eglTerminate(display);
                return null;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public void q(Surface surface) {
        if (!PatchProxy.proxy(new Object[]{surface}, this, changeQuickRedirect, false, 1420, new Class[]{Surface.class}, Void.TYPE).isSupported) {
            this.g.submit(new e(surface));
        }
    }
}
