package com.leedarson.log.mgr;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.w;
import com.leedarson.log.elk.b;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import io.reactivex.m;
import io.reactivex.o;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/* compiled from: ELKLogManager */
public class q {
    private static q a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String b = "elkLog";
    private final String c = "elkLogBuffer";
    private final String d = "h5Log";
    private final String e = "jsBridge";
    /* access modifiers changed from: private */
    public Context f;
    private t g;
    private volatile String h;
    private int i = 0;
    private ExecutorService j;
    private Semaphore k;
    private boolean l = false;
    private Timer m;
    long n = 0;
    /* access modifiers changed from: private */
    public boolean o = false;

    public /* synthetic */ Boolean G(Boolean bool) {
        F(bool);
        return bool;
    }

    static /* synthetic */ void e(q x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_REQ, new Class[]{q.class}, Void.TYPE).isSupported) {
            x0.k();
        }
    }

    public int u() {
        return this.i;
    }

    public void T(int level) {
        Object[] objArr = {new Integer(level)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1245, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.i = level;
            SharePreferenceUtils.setPrefInt(this.f, "runtimeDebugLevel", level);
        }
    }

    public ExecutorService w() {
        return this.j;
    }

    public static q r() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1246, new Class[0], q.class);
        if (proxy.isSupported) {
            return (q) proxy.result;
        }
        if (a == null) {
            synchronized (q.class) {
                if (a == null) {
                    a = new q();
                }
            }
        }
        return a;
    }

    public q() {
        Context a2 = b.b().a();
        this.f = a2;
        this.g = new t(a2);
        this.h = t();
        this.j = l.i(1, "elkog-manager-", 1);
        this.i = SharePreferenceUtils.getPrefInt(this.f, "runtimeDebugLevel", 0);
        this.k = new Semaphore(1);
    }

    public synchronized String v() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1247, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        File file = new File(o(), this.h);
        if (this.g.g(file)) {
            this.h = t();
        }
        if (file.exists() && file.length() > 1048576) {
            this.h = t();
        }
        return this.h;
    }

    private String t() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1248, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmssSSS");
        return "runtime-" + sdf.format(new Date(System.currentTimeMillis())) + ".log";
    }

    private void R(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 1249, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("ELK").a(msg, new Object[0]);
        }
    }

    public File o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1250, new Class[0], File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        return new File(this.f.getCacheDir() + File.separator + "elkLog");
    }

    public File p() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1251, new Class[0], File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        File _elkBufferLogDir = new File(this.f.getCacheDir() + File.separator + "elkLogBuffer");
        if (!_elkBufferLogDir.exists()) {
            _elkBufferLogDir.mkdir();
        }
        return _elkBufferLogDir;
    }

    public File q() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1252, new Class[0], File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        return new File(this.f.getCacheDir() + File.separator + "h5Log");
    }

    public File s() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1253, new Class[0], File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        return new File(this.f.getCacheDir() + File.separator + "jsBridge");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00d5, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void f(java.lang.String r12, java.lang.String r13, java.lang.String r14, boolean r15) {
        /*
            r11 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            monitor-enter(r11)
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ all -> 0x00d6 }
            r9 = 0
            r2[r9] = r12     // Catch:{ all -> 0x00d6 }
            r3 = 1
            r2[r3] = r13     // Catch:{ all -> 0x00d6 }
            r4 = 2
            r2[r4] = r14     // Catch:{ all -> 0x00d6 }
            java.lang.Byte r5 = new java.lang.Byte     // Catch:{ all -> 0x00d6 }
            r5.<init>(r15)     // Catch:{ all -> 0x00d6 }
            r6 = 3
            r2[r6] = r5     // Catch:{ all -> 0x00d6 }
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect     // Catch:{ all -> 0x00d6 }
            r7 = 0
            r8 = 1254(0x4e6, float:1.757E-42)
            java.lang.Class[] r1 = new java.lang.Class[r1]     // Catch:{ all -> 0x00d6 }
            r1[r9] = r0     // Catch:{ all -> 0x00d6 }
            r1[r3] = r0     // Catch:{ all -> 0x00d6 }
            r1[r4] = r0     // Catch:{ all -> 0x00d6 }
            java.lang.Class r0 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x00d6 }
            r1[r6] = r0     // Catch:{ all -> 0x00d6 }
            java.lang.Class r0 = java.lang.Void.TYPE     // Catch:{ all -> 0x00d6 }
            r3 = r11
            r4 = r5
            r5 = r7
            r6 = r8
            r7 = r1
            r8 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00d6 }
            boolean r0 = r0.isSupported     // Catch:{ all -> 0x00d6 }
            if (r0 == 0) goto L_0x003a
            monitor-exit(r11)
            return
        L_0x003a:
            r0 = r11
            boolean r1 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x00d6 }
            if (r1 != 0) goto L_0x0067
            boolean r1 = android.text.TextUtils.isEmpty(r13)     // Catch:{ all -> 0x00d6 }
            if (r1 == 0) goto L_0x0048
            goto L_0x0067
        L_0x0048:
            java.lang.String r1 = r0.v()     // Catch:{ all -> 0x00d6 }
            java.io.File r2 = r0.o()     // Catch:{ all -> 0x00d6 }
            boolean r3 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x00d6 }
            if (r3 != 0) goto L_0x005c
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x00d6 }
            r3.<init>(r12)     // Catch:{ all -> 0x00d6 }
            r2 = r3
        L_0x005c:
            boolean r3 = android.text.TextUtils.isEmpty(r13)     // Catch:{ all -> 0x00d6 }
            if (r3 != 0) goto L_0x0063
            r1 = r13
        L_0x0063:
            com.leedarson.base.utils.l.h(r2, r1, r14, r15)     // Catch:{ all -> 0x00d6 }
            goto L_0x00d4
        L_0x0067:
            java.io.File r1 = r0.p()     // Catch:{ all -> 0x00d6 }
            java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat     // Catch:{ all -> 0x00d6 }
            java.lang.String r3 = "MMddHHmmssSSS"
            r2.<init>(r3)     // Catch:{ all -> 0x00d6 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d6 }
            r3.<init>()     // Catch:{ all -> 0x00d6 }
            java.lang.String r4 = "buffer-"
            r3.append(r4)     // Catch:{ all -> 0x00d6 }
            java.util.Date r4 = new java.util.Date     // Catch:{ all -> 0x00d6 }
            r4.<init>()     // Catch:{ all -> 0x00d6 }
            java.lang.String r4 = r2.format(r4)     // Catch:{ all -> 0x00d6 }
            r3.append(r4)     // Catch:{ all -> 0x00d6 }
            java.lang.String r4 = ".log"
            r3.append(r4)     // Catch:{ all -> 0x00d6 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00d6 }
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x00d6 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d6 }
            r5.<init>()     // Catch:{ all -> 0x00d6 }
            java.lang.String r6 = r1.getAbsolutePath()     // Catch:{ all -> 0x00d6 }
            r5.append(r6)     // Catch:{ all -> 0x00d6 }
            java.lang.String r6 = java.io.File.separator     // Catch:{ all -> 0x00d6 }
            r5.append(r6)     // Catch:{ all -> 0x00d6 }
            r5.append(r3)     // Catch:{ all -> 0x00d6 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00d6 }
            r4.<init>(r5)     // Catch:{ all -> 0x00d6 }
            com.leedarson.base.utils.l.h(r1, r3, r14, r15)     // Catch:{ all -> 0x00d6 }
            android.content.Context r5 = r0.f     // Catch:{ all -> 0x00d6 }
            java.lang.String r7 = r4.getAbsolutePath()     // Catch:{ all -> 0x00d6 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d6 }
            r8.<init>()     // Catch:{ all -> 0x00d6 }
            java.io.File r10 = r0.o()     // Catch:{ all -> 0x00d6 }
            r8.append(r10)     // Catch:{ all -> 0x00d6 }
            r8.append(r6)     // Catch:{ all -> 0x00d6 }
            r8.append(r3)     // Catch:{ all -> 0x00d6 }
            java.lang.String r6 = r8.toString()     // Catch:{ all -> 0x00d6 }
            com.leedarson.base.utils.w.j(r5, r7, r6, r9)     // Catch:{ all -> 0x00d6 }
            r4.delete()     // Catch:{ all -> 0x00d6 }
        L_0x00d4:
            monitor-exit(r11)
            return
        L_0x00d6:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.log.mgr.q.f(java.lang.String, java.lang.String, java.lang.String, boolean):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: x */
    public /* synthetic */ void y(String dir, String fileName, String content) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{dir, fileName, content}, this, changeQuickRedirect, false, 1279, clsArr, Void.TYPE).isSupported) {
            f(dir, fileName, content, true);
        }
    }

    public void h(String dir, String fileName, String content) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{dir, fileName, content}, this, changeQuickRedirect, false, 1255, clsArr, Void.TYPE).isSupported) {
            this.j.execute(new c(this, dir, fileName, content));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: B */
    public /* synthetic */ void C(String dir, String fileName, String content) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{dir, fileName, content}, this, changeQuickRedirect, false, 1278, clsArr, Void.TYPE).isSupported) {
            f(dir, fileName, content, false);
        }
    }

    public void m(String dir, String fileName, String content) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{dir, fileName, content}, this, changeQuickRedirect, false, 1256, clsArr, Void.TYPE).isSupported) {
            this.j.execute(new f(this, dir, fileName, content));
        }
    }

    public void g(String content) {
        if (!PatchProxy.proxy(new Object[]{content}, this, changeQuickRedirect, false, 1257, new Class[]{String.class}, Void.TYPE).isSupported) {
            h((String) null, (String) null, content);
        }
    }

    public void n(Context context) {
        File[] listFiles;
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 1259, new Class[]{Context.class}, Void.TYPE).isSupported) {
            if (this.g == null) {
                this.g = new t(context);
            }
            File dirs = o();
            if (dirs.exists() && (listFiles = dirs.listFiles()) != null && listFiles.length != 0 && !TextUtils.isEmpty(SharePreferenceUtils.getPrefString(context, "logUploadUrl", "")) && !TextUtils.isEmpty(SharePreferenceUtils.getPrefString(context, "accessToken", "")) && w.w(context) > 0 && !this.l) {
                this.l = true;
                l().u(new e(this)).G(new d(this)).u(new b(dirs)).t(new a(this)).u(new k(this)).J(l.a).Y(new h(context), new j(this));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: D */
    public /* synthetic */ o E(Boolean bool) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bool}, this, changeQuickRedirect, false, 1277, new Class[]{Boolean.class}, o.class);
        return proxy.isSupported ? (o) proxy.result : S();
    }

    private /* synthetic */ Boolean F(Boolean aBoolean) {
        this.l = false;
        return aBoolean;
    }

    static /* synthetic */ io.reactivex.l H(File dirs, Boolean bool) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirs, bool}, (Object) null, changeQuickRedirect, true, 1276, new Class[]{File.class, Boolean.class}, io.reactivex.l.class);
        if (proxy.isSupported) {
            return (io.reactivex.l) proxy.result;
        }
        int _tempTotal = dirs.listFiles().length;
        return io.reactivex.l.y(dirs.listFiles()).c0(_tempTotal > 5 ? 5 : (long) _tempTotal);
    }

    /* access modifiers changed from: private */
    /* renamed from: I */
    public /* synthetic */ boolean J(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 1275, new Class[]{File.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : true ^ this.g.g(file);
    }

    /* access modifiers changed from: private */
    /* renamed from: K */
    public /* synthetic */ o L(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 1274, new Class[]{File.class}, o.class);
        if (proxy.isSupported) {
            return (o) proxy.result;
        }
        return this.g.f(file);
    }

    static /* synthetic */ void M(Context context, File s) {
        if (!PatchProxy.proxy(new Object[]{context, s}, (Object) null, changeQuickRedirect, true, 1273, new Class[]{Context.class, File.class}, Void.TYPE).isSupported) {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getCacheDir());
            String str = File.separator;
            sb.append(str);
            sb.append("elkLogBackup");
            sb.append(str);
            sb.append(s.getName());
            sb.append("-backup");
            w.j(context, s.getAbsolutePath(), sb.toString(), false);
            s.delete();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: N */
    public /* synthetic */ void O(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 1272, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            this.l = false;
            if (throwable instanceof ApiException) {
                ApiException e2 = (ApiException) throwable;
                R("elk运行日志补发上传出错:" + e2.getMsg() + ",code:" + e2.getCode());
                return;
            }
            R("elk运行日志补发上传出错:" + throwable.getMessage());
        }
    }

    public void U() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1260, new Class[0], Void.TYPE).isSupported) {
            if (this.m == null) {
                R("***开启定时上传任务***");
                Timer timer = new Timer();
                this.m = timer;
                timer.schedule(new a(), 10000, KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
            }
        }
    }

    /* compiled from: ELKLogManager */
    public class a extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_RESP, new Class[0], Void.TYPE).isSupported) {
                q qVar = q.this;
                long j = qVar.n + 1;
                qVar.n = j;
                if (j % 2 == 0) {
                    qVar.n(qVar.f);
                    if (!q.this.o) {
                        boolean unused = q.this.o = true;
                        q.this.i();
                        q.this.j();
                        q.e(q.this);
                    }
                }
                if (q.this.f != null && Constans.isScreenOn && Constans.appLogin) {
                    q.this.f.sendBroadcast(new Intent("android.net.conn.LDS_CONNECTIVITY_CHANGE"));
                }
            }
        }
    }

    public void V() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1261, new Class[0], Void.TYPE).isSupported) {
            Timer timer = this.m;
            if (timer != null) {
                timer.cancel();
                this.m = null;
            }
            this.g.d();
        }
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1262, new Class[0], Void.TYPE).isSupported) {
            File dir = new File(this.f.getCacheDir() + File.separator + "elkLogBackup");
            if (dir.exists()) {
                for (File item : dir.listFiles()) {
                    if (System.currentTimeMillis() - item.lastModified() >= 604800000) {
                        item.delete();
                    }
                }
            }
        }
    }

    public void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1263, new Class[0], Void.TYPE).isSupported) {
            long launchTime = System.currentTimeMillis() - 10000;
            File dir = q();
            if (dir.exists()) {
                for (File item : dir.listFiles()) {
                    if (item.lastModified() < launchTime) {
                        item.delete();
                    }
                }
            }
        }
    }

    private io.reactivex.l<Boolean> l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1264, new Class[0], io.reactivex.l.class);
        return proxy.isSupported ? (io.reactivex.l) proxy.result : io.reactivex.l.k(new g(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: z */
    public /* synthetic */ void A(m emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 1271, new Class[]{m.class}, Void.TYPE).isSupported) {
            try {
                File[] listFiles = new File(o().getAbsolutePath()).listFiles();
                if (listFiles != null) {
                    if (listFiles.length != 0) {
                        for (File file : listFiles) {
                            if (file.exists() && file.isFile() && file.length() > 1048576) {
                                R("elk 开始删除超过1M的大文件，用于解决上传队列的问题.... file=" + file.getAbsolutePath());
                                file.delete();
                            }
                        }
                        emitter.onNext(true);
                        return;
                    }
                }
                emitter.onNext(true);
            } catch (Exception e2) {
                R("删除过大文件发生异常： e=" + e2.toString());
            }
        }
    }

    private io.reactivex.l<Boolean> S() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1265, new Class[0], io.reactivex.l.class);
        return proxy.isSupported ? (io.reactivex.l) proxy.result : io.reactivex.l.k(new i(this));
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0127  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0138  */
    /* renamed from: P */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void Q(io.reactivex.m r32) {
        /*
            r31 = this;
            r0 = 1
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r0)
            java.lang.Object[] r2 = new java.lang.Object[r0]
            r9 = 0
            r2[r9] = r32
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r0]
            java.lang.Class<io.reactivex.m> r3 = io.reactivex.m.class
            r7[r9] = r3
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 1270(0x4f6, float:1.78E-42)
            r3 = r31
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x0022
            return
        L_0x0022:
            r2 = r31
            r3 = r32
            java.io.File r4 = r2.o()
            java.lang.String r4 = r4.getAbsolutePath()
            java.io.File r5 = r2.o()
            java.lang.String r5 = r5.getAbsolutePath()
            java.lang.String r6 = "elk_log_combine_"
            r7 = 512000(0x7d000, double:2.529616E-318)
            r10 = 1
            r11 = 0
            r12 = 0
            java.io.File r14 = new java.io.File
            r14.<init>(r4)
            r15 = 0
            java.io.File[] r9 = r14.listFiles()
            int r9 = r9.length
            java.io.File[] r0 = r14.listFiles()
            if (r0 == 0) goto L_0x0163
            r17 = r2
            int r2 = r0.length
            r32 = r4
            r4 = 5
            if (r2 > r4) goto L_0x0062
            r20 = r0
            r26 = r6
            r27 = r7
            r23 = r14
            goto L_0x016f
        L_0x0062:
            r2 = 0
            java.lang.String r4 = ""
            r18 = r2
            int r2 = r0.length
            r29 = r12
            r12 = r10
            r13 = r15
            r10 = 0
            r15 = r29
        L_0x006f:
            if (r10 >= r2) goto L_0x015c
            r19 = r0[r10]
            boolean r20 = r19.isFile()
            if (r20 == 0) goto L_0x013f
            r20 = r0
            java.lang.String r0 = r19.getName()
            r21 = r2
            java.lang.String r2 = "_merged_"
            boolean r0 = r0.contains(r2)
            if (r0 == 0) goto L_0x0094
            r22 = r4
            r26 = r6
            r27 = r7
            r23 = r14
            r7 = 1
            goto L_0x014c
        L_0x0094:
            int r13 = r13 + 1
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.lang.String r2 = "yyyyMMddHHmmss"
            r0.<init>(r2)
            java.lang.String r2 = com.leedarson.base.utils.l.d(r19)
            if (r18 == 0) goto L_0x00bd
            r22 = r4
            byte[] r4 = r2.getBytes()
            int r4 = r4.length
            r24 = r13
            r23 = r14
            long r13 = (long) r4
            long r13 = r13 + r15
            int r4 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r4 <= 0) goto L_0x00b6
            goto L_0x00c3
        L_0x00b6:
            r26 = r6
            r27 = r7
            r4 = r22
            goto L_0x0121
        L_0x00bd:
            r22 = r4
            r24 = r13
            r23 = r14
        L_0x00c3:
            r4 = 0
            r13 = 0
            if (r18 != 0) goto L_0x00c9
            r4 = 1
            goto L_0x00da
        L_0x00c9:
            byte[] r14 = r2.getBytes()
            int r14 = r14.length
            r25 = r13
            long r13 = (long) r14
            long r13 = r13 + r15
            int r13 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r13 <= 0) goto L_0x00d8
            r13 = 1
            goto L_0x00da
        L_0x00d8:
            r13 = r25
        L_0x00da:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r6)
            r25 = r4
            java.lang.String r4 = "merged_"
            r14.append(r4)
            r14.append(r12)
            java.lang.String r4 = "_"
            r14.append(r4)
            r26 = r6
            java.util.Date r6 = new java.util.Date
            r6.<init>()
            java.lang.String r6 = r0.format(r6)
            r14.append(r6)
            r14.append(r4)
            r27 = r7
            long r6 = java.lang.System.currentTimeMillis()
            r14.append(r6)
            java.lang.String r4 = ".txt"
            r14.append(r4)
            java.lang.String r4 = r14.toString()
            java.io.File r6 = new java.io.File
            r6.<init>(r5, r4)
            r18 = r6
            int r12 = r12 + 1
            r15 = 0
        L_0x0121:
            boolean r6 = android.text.TextUtils.isEmpty(r2)
            if (r6 != 0) goto L_0x0138
            java.io.File r6 = new java.io.File
            r6.<init>(r5)
            r7 = 1
            com.leedarson.base.utils.l.h(r6, r4, r2, r7)
            byte[] r6 = r2.getBytes()
            int r6 = r6.length
            long r13 = (long) r6
            long r15 = r15 + r13
            goto L_0x0139
        L_0x0138:
            r7 = 1
        L_0x0139:
            r19.delete()
            r13 = r24
            goto L_0x014e
        L_0x013f:
            r20 = r0
            r21 = r2
            r22 = r4
            r26 = r6
            r27 = r7
            r23 = r14
            r7 = 1
        L_0x014c:
            r4 = r22
        L_0x014e:
            int r10 = r10 + 1
            r0 = r20
            r2 = r21
            r14 = r23
            r6 = r26
            r7 = r27
            goto L_0x006f
        L_0x015c:
            r3.onNext(r1)
            r3.onComplete()
            return
        L_0x0163:
            r20 = r0
            r17 = r2
            r32 = r4
            r26 = r6
            r27 = r7
            r23 = r14
        L_0x016f:
            r3.onNext(r1)
            r3.onComplete()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.log.mgr.q.Q(io.reactivex.m):void");
    }

    private void k() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1266, new Class[0], Void.TYPE).isSupported) {
            try {
                File dir = new File(String.format(Locale.US, "%s/web/log", new Object[]{BaseApplication.b().getFilesDir().getPath()}));
                if (dir.exists()) {
                    for (File item : dir.listFiles()) {
                        if (System.currentTimeMillis() - item.lastModified() >= 345600000) {
                            item.delete();
                        }
                    }
                }
            } catch (Exception e2) {
                a("cleanRuntimeLogs  --> 发生异常 e=" + e2.toString());
            }
        }
    }

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 1269, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("ELKCombine").c(msg, new Object[0]);
        }
    }
}
