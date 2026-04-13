package com.leedarson.manager;

import com.leedarson.base.utils.r;
import com.leedarson.event.CloudRecordGetEndEvent;
import com.leedarson.smartcamera.codec.c;
import com.leedarson.smartcamera.utils.g;
import com.leedarson.smartcamera.utils.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* compiled from: LdsCloudManager */
public class b {
    private static b a = null;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public boolean b = false;
    private boolean c = true;
    private boolean d = false;
    /* access modifiers changed from: private */
    public boolean e = false;
    ExecutorService f = Executors.newFixedThreadPool(2, new r("ldscloud-pool"));
    /* access modifiers changed from: private */
    public c g;
    private Future h;
    private Future i;
    long j = 0;
    long k = 0;
    int l = 17;
    int m = 0;

    static /* synthetic */ void b(b x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1408, new Class[]{b.class}, Void.TYPE).isSupported) {
            x0.i();
        }
    }

    private b() {
    }

    public static b h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1400, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        if (a == null) {
            synchronized (b.class) {
                if (a == null) {
                    a = new b();
                }
            }
        }
        return a;
    }

    public void m() {
        this.d = true;
    }

    public void n() {
        this.d = false;
    }

    public void l(c ldsCodec) {
        this.g = ldsCodec;
    }

    public void k(boolean cloudRecEnd) {
        this.b = cloudRecEnd;
    }

    public void o(boolean stopRecord) {
        this.c = stopRecord;
    }

    public void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1401, new Class[0], Void.TYPE).isSupported) {
            r();
            this.c = false;
            this.h = this.f.submit(new a());
        }
    }

    /* compiled from: LdsCloudManager */
    public class a implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1409, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            g.d().a();
            g.d().b();
            if (b.this.g != null) {
                b.this.g.A();
            }
            b.b(b.this);
            return null;
        }
    }

    public void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1402, new Class[0], Void.TYPE).isSupported) {
            this.c = true;
            Future future = this.h;
            if (future != null && !future.isCancelled()) {
                this.h.cancel(true);
            }
        }
    }

    public void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1403, new Class[0], Void.TYPE).isSupported) {
            q();
            r();
            g.d().b();
            g.d().a();
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1404, new Class[0], Void.TYPE).isSupported) {
            g.d().b();
            g.d().a();
            q();
            this.e = true;
            this.b = false;
            this.i = this.f.submit(new C0093b());
        }
    }

    /* renamed from: com.leedarson.manager.b$b  reason: collision with other inner class name */
    /* compiled from: LdsCloudManager */
    public class C0093b implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0093b() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1410, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            while (b.this.e) {
                if (b.this.b && g.d().f() == 0) {
                    org.greenrobot.eventbus.c.c().l(new CloudRecordGetEndEvent());
                    boolean unused = b.this.e = false;
                }
                if (g.d().f() != 0) {
                    b.this.f(g.d().e());
                }
            }
            g.d().b();
            return null;
        }
    }

    public void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1405, new Class[0], Void.TYPE).isSupported) {
            this.e = false;
            Future future = this.i;
            if (future != null && !future.isCancelled()) {
                this.i.cancel(true);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0079 A[Catch:{ InterruptedException -> 0x0199 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0182 A[Catch:{ InterruptedException -> 0x0197 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void f(byte[] r22) {
        /*
            r21 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r22
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r2 = byte[].class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 1406(0x57e, float:1.97E-42)
            r2 = r21
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001e
            return
        L_0x001e:
            r1 = r21
            r2 = r22
            r3 = 0
        L_0x0023:
            int r4 = r2.length
            int r4 = r4 - r3
            r5 = 17
            if (r4 < r5) goto L_0x01a0
            boolean r4 = r1.c
            if (r4 != 0) goto L_0x01a0
            byte r4 = r2[r3]     // Catch:{ InterruptedException -> 0x0199 }
            if (r4 != r0) goto L_0x0047
            int r4 = r3 + 1
            byte r4 = r2[r4]     // Catch:{ InterruptedException -> 0x0042 }
            if (r4 != r0) goto L_0x0047
            r4 = 22
            r1.l = r4     // Catch:{ InterruptedException -> 0x0042 }
            int r4 = r3 + 17
            byte r4 = r2[r4]     // Catch:{ InterruptedException -> 0x0042 }
            r1.m = r4     // Catch:{ InterruptedException -> 0x0042 }
            goto L_0x004b
        L_0x0042:
            r0 = move-exception
            r18 = r2
            goto L_0x019c
        L_0x0047:
            r1.m = r8     // Catch:{ InterruptedException -> 0x0199 }
            r1.l = r5     // Catch:{ InterruptedException -> 0x0199 }
        L_0x004b:
            int r4 = r3 + 2
            byte r4 = r2[r4]     // Catch:{ InterruptedException -> 0x0199 }
            int r5 = r3 + 3
            byte r5 = r2[r5]     // Catch:{ InterruptedException -> 0x0199 }
            r6 = 8
            byte[] r7 = new byte[r6]     // Catch:{ InterruptedException -> 0x0199 }
            int r9 = r3 + 4
            java.lang.System.arraycopy(r2, r9, r7, r8, r6)     // Catch:{ InterruptedException -> 0x0199 }
            long r9 = com.leedarson.utils.j.d(r7)     // Catch:{ InterruptedException -> 0x0199 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x0199 }
            r6.<init>()     // Catch:{ InterruptedException -> 0x0199 }
            r6.append(r9)     // Catch:{ InterruptedException -> 0x0199 }
            java.lang.String r11 = ""
            r6.append(r11)     // Catch:{ InterruptedException -> 0x0199 }
            java.lang.String r6 = r6.toString()     // Catch:{ InterruptedException -> 0x0199 }
            int r6 = r6.length()     // Catch:{ InterruptedException -> 0x0199 }
            int r6 = 13 - r6
            if (r6 <= 0) goto L_0x0082
            r11 = 0
        L_0x007a:
            if (r11 >= r6) goto L_0x0082
            r12 = 10
            long r9 = r9 * r12
            int r11 = r11 + 1
            goto L_0x007a
        L_0x0082:
            int r11 = r3 + 12
            byte r11 = r2[r11]     // Catch:{ InterruptedException -> 0x0199 }
            r12 = 4
            byte[] r13 = new byte[r12]     // Catch:{ InterruptedException -> 0x0199 }
            r14 = r13
            int r13 = r3 + 13
            java.lang.System.arraycopy(r2, r13, r14, r8, r12)     // Catch:{ InterruptedException -> 0x0199 }
            int r13 = com.leedarson.utils.j.c(r14)     // Catch:{ InterruptedException -> 0x0199 }
            r15 = r13
            if (r11 != 0) goto L_0x0182
            r13 = 5
            if (r4 != r13) goto L_0x0111
            if (r5 != 0) goto L_0x00d1
            byte[] r12 = new byte[r15]     // Catch:{ InterruptedException -> 0x0042 }
            r13 = r12
            int r12 = r1.l     // Catch:{ InterruptedException -> 0x0042 }
            int r12 = r12 + r3
            java.lang.System.arraycopy(r2, r12, r13, r8, r15)     // Catch:{ InterruptedException -> 0x0042 }
            boolean r12 = r1.c     // Catch:{ InterruptedException -> 0x0042 }
            if (r12 != 0) goto L_0x00c3
            com.leedarson.smartcamera.utils.g r12 = com.leedarson.smartcamera.utils.g.d()     // Catch:{ InterruptedException -> 0x0042 }
            com.leedarson.smartcamera.utils.h r8 = new com.leedarson.smartcamera.utils.h     // Catch:{ InterruptedException -> 0x0042 }
            r16 = 1
            r17 = 1
            r0 = r12
            r12 = r8
            r19 = r13
            r22 = r6
            r20 = r14
            r6 = r15
            r14 = r9
            r12.<init>(r13, r14, r16, r17)     // Catch:{ InterruptedException -> 0x0042 }
            r0.g(r8)     // Catch:{ InterruptedException -> 0x0042 }
            goto L_0x00ca
        L_0x00c3:
            r22 = r6
            r19 = r13
            r20 = r14
            r6 = r15
        L_0x00ca:
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ InterruptedException -> 0x0042 }
            r1.j = r12     // Catch:{ InterruptedException -> 0x0042 }
            goto L_0x010b
        L_0x00d1:
            r22 = r6
            r20 = r14
            r6 = r15
            r0 = 1
            if (r5 != r0) goto L_0x010b
            byte[] r0 = new byte[r6]     // Catch:{ InterruptedException -> 0x0042 }
            int r8 = r1.l     // Catch:{ InterruptedException -> 0x0042 }
            int r8 = r8 + r3
            r12 = 0
            java.lang.System.arraycopy(r2, r8, r0, r12, r6)     // Catch:{ InterruptedException -> 0x0042 }
            boolean r8 = r1.c     // Catch:{ InterruptedException -> 0x0042 }
            if (r8 != 0) goto L_0x00fd
            com.leedarson.smartcamera.utils.g r8 = com.leedarson.smartcamera.utils.g.d()     // Catch:{ InterruptedException -> 0x0042 }
            com.leedarson.smartcamera.utils.h r14 = new com.leedarson.smartcamera.utils.h     // Catch:{ InterruptedException -> 0x0042 }
            r16 = 1
            r17 = 2
            r12 = r14
            r13 = r0
            r19 = r0
            r0 = r14
            r14 = r9
            r12.<init>(r13, r14, r16, r17)     // Catch:{ InterruptedException -> 0x0042 }
            r8.g(r0)     // Catch:{ InterruptedException -> 0x0042 }
            goto L_0x00ff
        L_0x00fd:
            r19 = r0
        L_0x00ff:
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ InterruptedException -> 0x0042 }
            r1.j = r12     // Catch:{ InterruptedException -> 0x0042 }
            r18 = r2
            r19 = 0
            goto L_0x018b
        L_0x010b:
            r18 = r2
            r19 = 0
            goto L_0x018b
        L_0x0111:
            r22 = r6
            r20 = r14
            r6 = r15
            r0 = 2
            if (r4 == r0) goto L_0x0125
            r0 = 3
            if (r4 == r0) goto L_0x0125
            if (r4 != r12) goto L_0x011f
            goto L_0x0125
        L_0x011f:
            r18 = r2
            r19 = 0
            goto L_0x018b
        L_0x0125:
            int r0 = r2.length     // Catch:{ InterruptedException -> 0x0199 }
            int r0 = r0 - r3
            int r8 = r1.l     // Catch:{ InterruptedException -> 0x0199 }
            int r15 = r6 + r8
            if (r0 < r15) goto L_0x017d
            byte[] r0 = new byte[r6]     // Catch:{ InterruptedException -> 0x0199 }
            int r8 = r8 + r3
            r14 = 0
            java.lang.System.arraycopy(r2, r8, r0, r14, r6)     // Catch:{ InterruptedException -> 0x0199 }
            boolean r8 = r1.c     // Catch:{ InterruptedException -> 0x0199 }
            if (r8 != 0) goto L_0x0172
            int r8 = r1.m     // Catch:{ InterruptedException -> 0x0199 }
            if (r8 != 0) goto L_0x0156
            com.leedarson.smartcamera.utils.g r8 = com.leedarson.smartcamera.utils.g.d()     // Catch:{ InterruptedException -> 0x0199 }
            com.leedarson.smartcamera.utils.h r15 = new com.leedarson.smartcamera.utils.h     // Catch:{ InterruptedException -> 0x0199 }
            r16 = 0
            r17 = 1
            r12 = r15
            r13 = r0
            r18 = r2
            r19 = r14
            r2 = r15
            r14 = r9
            r12.<init>(r13, r14, r16, r17)     // Catch:{ InterruptedException -> 0x0197 }
            r8.g(r2)     // Catch:{ InterruptedException -> 0x0197 }
            goto L_0x0176
        L_0x0156:
            r18 = r2
            r19 = r14
            r2 = 1
            if (r8 != r2) goto L_0x0176
            com.leedarson.smartcamera.utils.g r8 = com.leedarson.smartcamera.utils.g.d()     // Catch:{ InterruptedException -> 0x0197 }
            com.leedarson.smartcamera.utils.h r14 = new com.leedarson.smartcamera.utils.h     // Catch:{ InterruptedException -> 0x0197 }
            r16 = 0
            r17 = 2
            r12 = r14
            r13 = r0
            r2 = r14
            r14 = r9
            r12.<init>(r13, r14, r16, r17)     // Catch:{ InterruptedException -> 0x0197 }
            r8.g(r2)     // Catch:{ InterruptedException -> 0x0197 }
            goto L_0x0176
        L_0x0172:
            r18 = r2
            r19 = r14
        L_0x0176:
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ InterruptedException -> 0x0197 }
            r1.k = r12     // Catch:{ InterruptedException -> 0x0197 }
            goto L_0x018b
        L_0x017d:
            r18 = r2
            r19 = 0
            goto L_0x018b
        L_0x0182:
            r18 = r2
            r22 = r6
            r19 = r8
            r20 = r14
            r6 = r15
        L_0x018b:
            int r0 = r1.l     // Catch:{ InterruptedException -> 0x0197 }
            int r15 = r6 + r0
            int r3 = r3 + r15
            r2 = r18
            r8 = r19
            r0 = 1
            goto L_0x0023
        L_0x0197:
            r0 = move-exception
            goto L_0x019c
        L_0x0199:
            r0 = move-exception
            r18 = r2
        L_0x019c:
            r0.printStackTrace()
            goto L_0x01a2
        L_0x01a0:
            r18 = r2
        L_0x01a2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.manager.b.f(byte[]):void");
    }

    private void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1407, new Class[0], Void.TYPE).isSupported) {
            while (!this.c) {
                try {
                    if (!this.d) {
                        h streamData = g.d().c();
                        if (streamData != null) {
                            if (streamData.isAudio()) {
                                long dif = System.currentTimeMillis() - this.j;
                                if (dif < 22) {
                                    while (1 != 0 && dif < 22) {
                                        try {
                                            dif = System.currentTimeMillis() - this.j;
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                }
                                c cVar = this.g;
                                if (cVar != null) {
                                    cVar.v(streamData.getTimestap(), streamData.getBytes(), streamData.getBytes().length, streamData.getCodec());
                                }
                                this.j = System.currentTimeMillis();
                            } else {
                                long dif2 = System.currentTimeMillis() - this.k;
                                if (dif2 < 35) {
                                    while (1 != 0 && dif2 < 35) {
                                        try {
                                            dif2 = System.currentTimeMillis() - this.k;
                                        } catch (Exception e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                }
                                c cVar2 = this.g;
                                if (cVar2 != null) {
                                    cVar2.Z(streamData.getTimestap(), streamData.getBytes(), streamData.getBytes().length, streamData.getCodec());
                                }
                                this.k = System.currentTimeMillis();
                            }
                        }
                    } else {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e4) {
                            e4.printStackTrace();
                            return;
                        }
                    }
                } catch (InterruptedException e5) {
                    e5.printStackTrace();
                    return;
                }
            }
        }
    }
}
