package com.leedarson.skiprope.player;

import com.leedarson.skiprope.bean.StartConfigBean;
import com.leedarson.skiprope.ctrl.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.Timer;
import java.util.TimerTask;

/* compiled from: BroadcastHelper */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int a = 1;
    private final int b = 2;
    private StartConfigBean c;
    /* access modifiers changed from: private */
    public c d;
    private int e;
    private int f;
    /* access modifiers changed from: private */
    public int g = 0;
    private int h = 0;
    private Timer i;
    private boolean j = false;

    public d(c playerController) {
        this.d = playerController;
    }

    public void f(StartConfigBean configBean) {
        boolean z = true;
        if (!PatchProxy.proxy(new Object[]{configBean}, this, changeQuickRedirect, false, 9601, new Class[]{StartConfigBean.class}, Void.TYPE).isSupported) {
            this.c = configBean;
            this.g = 0;
            this.h = 0;
            if (configBean == null || configBean.broadcast == null) {
                com.leedarson.log.elk.a.y(this).t("LdsSkipRope").e("setConfigBean").p("播报模式为空").a().b();
                return;
            }
            e("播报模式" + configBean.broadcast.mode + ",间隔：" + configBean.broadcast.interval);
            StartConfigBean.BroadcastMode broadcastMode = configBean.broadcast;
            int i2 = broadcastMode.mode;
            this.e = i2;
            this.f = broadcastMode.interval;
            if (i2 != 1) {
                z = false;
            }
            g(z);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r1 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c(com.leedarson.skiprope.bean.SRDeviceNotifyDataBean r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.skiprope.bean.SRDeviceNotifyDataBean> r2 = com.leedarson.skiprope.bean.SRDeviceNotifyDataBean.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 9602(0x2582, float:1.3455E-41)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r9
            com.leedarson.skiprope.bean.StartConfigBean r2 = r1.c
            if (r2 == 0) goto L_0x0050
            com.leedarson.skiprope.bean.StartConfigBean$BroadcastMode r2 = r2.broadcast
            if (r2 != 0) goto L_0x0027
            goto L_0x0050
        L_0x0027:
            int r2 = r10.totalCount
            r1.h = r2
            int r3 = r1.e
            r4 = 2
            if (r3 != r4) goto L_0x004b
            int r3 = r1.g
            int r3 = r2 - r3
            int r4 = r1.f
            int r3 = r3 / r4
            if (r3 < r0) goto L_0x004f
            com.leedarson.skiprope.ctrl.c r0 = r1.d
            r0.m(r8, r2)
            int r0 = r1.g
            int r2 = r10.totalCount
            int r2 = r2 - r0
            int r3 = r1.f
            int r2 = r2 / r3
            int r2 = r2 * r3
            int r0 = r0 + r2
            r1.g = r0
            goto L_0x004f
        L_0x004b:
            if (r3 != r0) goto L_0x004f
            r1.g = r2
        L_0x004f:
            return
        L_0x0050:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.skiprope.player.d.c(com.leedarson.skiprope.bean.SRDeviceNotifyDataBean):void");
    }

    private void e(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 9603, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.skiprope.util.a.a("broadcast--> " + msg);
        }
    }

    public void g(boolean startTimer) {
        if (!PatchProxy.proxy(new Object[]{new Byte(startTimer ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 9604, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.j = false;
            if (startTimer) {
                h();
            }
        }
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9605, new Class[0], Void.TYPE).isSupported) {
            j();
            e("开启定时器,间隔:" + this.f);
            Timer timer = new Timer();
            this.i = timer;
            timer.schedule(new a(), 0, (long) (this.f * 1000));
        }
    }

    /* compiled from: BroadcastHelper */
    public class a extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9608, new Class[0], Void.TYPE).isSupported) {
                if (d.this.g != 0) {
                    d.this.d.m(false, d.this.g);
                }
            }
        }
    }

    public void i(int finalNum) {
        if (!PatchProxy.proxy(new Object[]{new Integer(finalNum)}, this, changeQuickRedirect, false, 9606, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.j = true;
            if (finalNum != -1) {
                this.d.e(false, finalNum);
            } else if (this.h != 0) {
                com.leedarson.skiprope.util.a.c("未收到结果数据直接停止，使用 lastRealtimeNum:" + this.h);
                this.d.e(false, this.h);
            }
            j();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void j() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 9607(0x2587, float:1.3462E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.i
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.i = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.skiprope.player.d.j():void");
    }

    public boolean d() {
        return this.j;
    }
}
