package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import android.os.Process;
import com.tencent.bugly.crashreport.common.info.b;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.y;
import com.tencent.bugly.proguard.z;
import java.lang.Thread;
import java.util.HashMap;
import meshsdk.ctrl.GroupCtrlAdapter;

/* compiled from: BUGLY */
public final class e implements Thread.UncaughtExceptionHandler {
    private static String h = null;
    private static final Object i = new Object();
    private Context a;
    private b b;
    private a c;
    private com.tencent.bugly.crashreport.common.info.a d;
    private Thread.UncaughtExceptionHandler e;
    private Thread.UncaughtExceptionHandler f;
    private boolean g = false;
    private int j;

    public e(Context context, b bVar, a aVar, com.tencent.bugly.crashreport.common.info.a aVar2) {
        this.a = context;
        this.b = bVar;
        this.c = aVar;
        this.d = aVar2;
    }

    public final synchronized void a() {
        if (this.j >= 10) {
            x.a("java crash handler over %d, no need set.", 10);
            return;
        }
        this.g = true;
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler != null) {
            if (!getClass().getName().equals(defaultUncaughtExceptionHandler.getClass().getName())) {
                if ("com.android.internal.os.RuntimeInit$UncaughtHandler".equals(defaultUncaughtExceptionHandler.getClass().getName())) {
                    x.a("backup system java handler: %s", defaultUncaughtExceptionHandler.toString());
                    this.f = defaultUncaughtExceptionHandler;
                    this.e = defaultUncaughtExceptionHandler;
                } else {
                    x.a("backup java handler: %s", defaultUncaughtExceptionHandler.toString());
                    this.e = defaultUncaughtExceptionHandler;
                }
            } else {
                return;
            }
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.j++;
        x.a("registered java monitor: %s", toString());
    }

    public final synchronized void b() {
        this.g = false;
        x.a("close java monitor!", new Object[0]);
        if (Thread.getDefaultUncaughtExceptionHandler().getClass().getName().contains("bugly")) {
            x.a("Java monitor to unregister: %s", toString());
            Thread.setDefaultUncaughtExceptionHandler(this.e);
            this.j--;
        }
    }

    private CrashDetailBean b(Thread thread, Throwable th, boolean z, String str, byte[] bArr) {
        String str2;
        String str3;
        Throwable th2 = th;
        String str4 = str;
        byte[] bArr2 = bArr;
        boolean z2 = false;
        if (th2 == null) {
            x.d("We can do nothing with a null throwable.", new Object[0]);
            return null;
        }
        boolean k = c.a().k();
        String str5 = (!k || !z) ? "" : " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]";
        if (k && z) {
            x.e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.C = b.k();
        crashDetailBean.D = b.i();
        crashDetailBean.E = b.m();
        crashDetailBean.F = this.d.p();
        crashDetailBean.G = this.d.o();
        crashDetailBean.H = this.d.q();
        crashDetailBean.w = z.a(this.a, c.e, (String) null);
        byte[] a2 = y.a();
        crashDetailBean.y = a2;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(a2 == null ? 0 : a2.length);
        x.a("user log size:%d", objArr);
        crashDetailBean.b = z ? 0 : 2;
        crashDetailBean.e = this.d.h();
        com.tencent.bugly.crashreport.common.info.a aVar = this.d;
        crashDetailBean.f = aVar.j;
        crashDetailBean.g = aVar.w();
        crashDetailBean.m = this.d.g();
        String name = th.getClass().getName();
        String b2 = b(th2, 1000);
        if (b2 == null) {
            b2 = "";
        }
        Object[] objArr2 = new Object[2];
        objArr2[0] = Integer.valueOf(th.getStackTrace().length);
        objArr2[1] = Boolean.valueOf(th.getCause() != null);
        x.e("stack frame :%d, has cause %b", objArr2);
        if (th.getStackTrace().length > 0) {
            str2 = th.getStackTrace()[0].toString();
        } else {
            str2 = "";
        }
        Throwable th3 = th2;
        while (th3 != null && th3.getCause() != null) {
            th3 = th3.getCause();
        }
        if (th3 == null || th3 == th2) {
            crashDetailBean.n = name;
            String str6 = b2 + str5;
            crashDetailBean.o = str6;
            if (str6 == null) {
                crashDetailBean.o = "";
            }
            crashDetailBean.p = str2;
            str3 = a(th2, c.f);
            crashDetailBean.q = str3;
        } else {
            crashDetailBean.n = th3.getClass().getName();
            String b3 = b(th3, 1000);
            crashDetailBean.o = b3;
            if (b3 == null) {
                crashDetailBean.o = "";
            }
            if (th3.getStackTrace().length > 0) {
                crashDetailBean.p = th3.getStackTrace()[0].toString();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(":");
            sb.append(b2);
            sb.append("\n");
            sb.append(str2);
            sb.append("\n......");
            sb.append("\nCaused by:\n");
            sb.append(crashDetailBean.n);
            sb.append(":");
            sb.append(crashDetailBean.o);
            sb.append("\n");
            str3 = a(th3, c.f);
            sb.append(str3);
            crashDetailBean.q = sb.toString();
        }
        crashDetailBean.r = System.currentTimeMillis();
        crashDetailBean.u = z.b(crashDetailBean.q.getBytes());
        try {
            crashDetailBean.z = z.a(c.f, false);
            crashDetailBean.A = this.d.d;
            String str7 = thread.getName() + "(" + thread.getId() + ")";
            crashDetailBean.B = str7;
            crashDetailBean.z.put(str7, str3);
            crashDetailBean.I = this.d.y();
            crashDetailBean.h = this.d.v();
            crashDetailBean.i = this.d.J();
            com.tencent.bugly.crashreport.common.info.a aVar2 = this.d;
            crashDetailBean.M = aVar2.a;
            crashDetailBean.N = aVar2.a();
            crashDetailBean.P = this.d.H();
            crashDetailBean.Q = this.d.I();
            crashDetailBean.R = this.d.B();
            crashDetailBean.S = this.d.G();
        } catch (Throwable th4) {
            x.e("handle crash error %s", th4.toString());
        }
        if (z) {
            this.b.c(crashDetailBean);
        } else {
            boolean z3 = str4 != null && str.length() > 0;
            if (bArr2 != null && bArr2.length > 0) {
                z2 = true;
            }
            if (z3) {
                HashMap hashMap = new HashMap(1);
                crashDetailBean.O = hashMap;
                hashMap.put("UserData", str4);
            }
            if (z2) {
                crashDetailBean.T = bArr2;
            }
        }
        return crashDetailBean;
    }

    private static boolean a(Thread thread) {
        synchronized (i) {
            if (h != null && thread.getName().equals(h)) {
                return true;
            }
            h = thread.getName();
            return false;
        }
    }

    public final void a(Thread thread, Throwable th, boolean z, String str, byte[] bArr) {
        Thread thread2 = thread;
        Throwable th2 = th;
        boolean z2 = z;
        if (z2) {
            x.e("Java Crash Happen cause by %s(%d)", thread.getName(), Long.valueOf(thread.getId()));
            if (a(thread)) {
                x.a("this class has handled this exception", new Object[0]);
                if (this.f != null) {
                    x.a("call system handler", new Object[0]);
                    this.f.uncaughtException(thread2, th2);
                } else {
                    x.e("current process die", new Object[0]);
                    Process.killProcess(Process.myPid());
                    System.exit(1);
                }
            }
        } else {
            x.e("Java Catch Happen", new Object[0]);
        }
        try {
            if (!this.g) {
                x.c("Java crash handler is disable. Just return.", new Object[0]);
                if (z2) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.e;
                    if (uncaughtExceptionHandler != null && a(uncaughtExceptionHandler)) {
                        x.e("sys default last handle start!", new Object[0]);
                        this.e.uncaughtException(thread2, th2);
                        x.e("sys default last handle end!", new Object[0]);
                    } else if (this.f != null) {
                        x.e("system handle start!", new Object[0]);
                        this.f.uncaughtException(thread2, th2);
                        x.e("system handle end!", new Object[0]);
                    } else {
                        x.e("crashreport last handle start!", new Object[0]);
                        x.e("current process die", new Object[0]);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                        x.e("crashreport last handle end!", new Object[0]);
                    }
                }
            } else {
                if (!this.c.b()) {
                    x.d("no remote but still store!", new Object[0]);
                }
                if (!this.c.c().g) {
                    if (this.c.b()) {
                        x.e("crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
                        b.a(z2 ? "JAVA_CRASH" : "JAVA_CATCH", z.a(), this.d.d, thread.getName(), z.a(th), (CrashDetailBean) null);
                        if (z2) {
                            Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = this.e;
                            if (uncaughtExceptionHandler2 != null && a(uncaughtExceptionHandler2)) {
                                x.e("sys default last handle start!", new Object[0]);
                                this.e.uncaughtException(thread2, th2);
                                x.e("sys default last handle end!", new Object[0]);
                                return;
                            } else if (this.f != null) {
                                x.e("system handle start!", new Object[0]);
                                this.f.uncaughtException(thread2, th2);
                                x.e("system handle end!", new Object[0]);
                                return;
                            } else {
                                x.e("crashreport last handle start!", new Object[0]);
                                x.e("current process die", new Object[0]);
                                Process.killProcess(Process.myPid());
                                System.exit(1);
                                x.e("crashreport last handle end!", new Object[0]);
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                }
                CrashDetailBean b2 = b(thread, th, z, str, bArr);
                if (b2 == null) {
                    x.e("pkg crash datas fail!", new Object[0]);
                    if (z2) {
                        Thread.UncaughtExceptionHandler uncaughtExceptionHandler3 = this.e;
                        if (uncaughtExceptionHandler3 != null && a(uncaughtExceptionHandler3)) {
                            x.e("sys default last handle start!", new Object[0]);
                            this.e.uncaughtException(thread2, th2);
                            x.e("sys default last handle end!", new Object[0]);
                        } else if (this.f != null) {
                            x.e("system handle start!", new Object[0]);
                            this.f.uncaughtException(thread2, th2);
                            x.e("system handle end!", new Object[0]);
                        } else {
                            x.e("crashreport last handle start!", new Object[0]);
                            x.e("current process die", new Object[0]);
                            Process.killProcess(Process.myPid());
                            System.exit(1);
                            x.e("crashreport last handle end!", new Object[0]);
                        }
                    }
                } else {
                    b.a(z2 ? "JAVA_CRASH" : "JAVA_CATCH", z.a(), this.d.d, thread.getName(), z.a(th), b2);
                    if (!this.b.a(b2)) {
                        this.b.a(b2, (long) GroupCtrlAdapter.RETRY_TIMEOUT, z2);
                    }
                    if (z2) {
                        this.b.b(b2);
                    }
                    if (z2) {
                        Thread.UncaughtExceptionHandler uncaughtExceptionHandler4 = this.e;
                        if (uncaughtExceptionHandler4 != null && a(uncaughtExceptionHandler4)) {
                            x.e("sys default last handle start!", new Object[0]);
                            this.e.uncaughtException(thread2, th2);
                            x.e("sys default last handle end!", new Object[0]);
                        } else if (this.f != null) {
                            x.e("system handle start!", new Object[0]);
                            this.f.uncaughtException(thread2, th2);
                            x.e("system handle end!", new Object[0]);
                        } else {
                            x.e("crashreport last handle start!", new Object[0]);
                            x.e("current process die", new Object[0]);
                            Process.killProcess(Process.myPid());
                            System.exit(1);
                            x.e("crashreport last handle end!", new Object[0]);
                        }
                    }
                }
            }
        } catch (Throwable th3) {
            if (z2) {
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler5 = this.e;
                if (uncaughtExceptionHandler5 != null && a(uncaughtExceptionHandler5)) {
                    x.e("sys default last handle start!", new Object[0]);
                    this.e.uncaughtException(thread2, th2);
                    x.e("sys default last handle end!", new Object[0]);
                } else if (this.f != null) {
                    x.e("system handle start!", new Object[0]);
                    this.f.uncaughtException(thread2, th2);
                    x.e("system handle end!", new Object[0]);
                } else {
                    x.e("crashreport last handle start!", new Object[0]);
                    x.e("current process die", new Object[0]);
                    Process.killProcess(Process.myPid());
                    System.exit(1);
                    x.e("crashreport last handle end!", new Object[0]);
                }
            }
            throw th3;
        }
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        synchronized (i) {
            a(thread, th, true, (String) null, (byte[]) null);
        }
    }

    private static boolean a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        if (uncaughtExceptionHandler == null) {
            return true;
        }
        String name = uncaughtExceptionHandler.getClass().getName();
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            if (name.equals(className) && "uncaughtException".equals(methodName)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0029, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(com.tencent.bugly.crashreport.common.strategy.StrategyBean r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 == 0) goto L_0x0028
            boolean r0 = r5.g     // Catch:{ all -> 0x0025 }
            boolean r1 = r4.g     // Catch:{ all -> 0x0025 }
            if (r0 == r1) goto L_0x0028
            java.lang.String r1 = "java changed to %b"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0025 }
            r3 = 0
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ all -> 0x0025 }
            r2[r3] = r0     // Catch:{ all -> 0x0025 }
            com.tencent.bugly.proguard.x.a(r1, r2)     // Catch:{ all -> 0x0025 }
            boolean r5 = r5.g     // Catch:{ all -> 0x0025 }
            if (r5 == 0) goto L_0x0021
            r4.a()     // Catch:{ all -> 0x0025 }
            monitor-exit(r4)
            return
        L_0x0021:
            r4.b()     // Catch:{ all -> 0x0025 }
            goto L_0x0028
        L_0x0025:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        L_0x0028:
            monitor-exit(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.e.a(com.tencent.bugly.crashreport.common.strategy.StrategyBean):void");
    }

    private static String a(Throwable th, int i2) {
        if (th == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            if (th.getStackTrace() != null) {
                StackTraceElement[] stackTrace = th.getStackTrace();
                int length = stackTrace.length;
                int i3 = 0;
                while (i3 < length) {
                    StackTraceElement stackTraceElement = stackTrace[i3];
                    if (i2 <= 0 || sb.length() < i2) {
                        sb.append(stackTraceElement.toString());
                        sb.append("\n");
                        i3++;
                    } else {
                        sb.append("\n[Stack over limit size :" + i2 + " , has been cutted !]");
                        return sb.toString();
                    }
                }
            }
        } catch (Throwable th2) {
            x.e("gen stack error %s", th2.toString());
        }
        return sb.toString();
    }

    private static String b(Throwable th, int i2) {
        if (th.getMessage() == null) {
            return "";
        }
        if (th.getMessage().length() <= 1000) {
            return th.getMessage();
        }
        return th.getMessage().substring(0, 1000) + "\n[Message over limit size:1000" + ", has been cutted!]";
    }
}
