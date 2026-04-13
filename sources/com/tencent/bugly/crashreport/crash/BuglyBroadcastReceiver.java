package com.tencent.bugly.crashreport.crash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;

/* compiled from: BUGLY */
public class BuglyBroadcastReceiver extends BroadcastReceiver {
    /* access modifiers changed from: private */
    public static BuglyBroadcastReceiver d = null;
    /* access modifiers changed from: private */
    public IntentFilter a = new IntentFilter();
    /* access modifiers changed from: private */
    public Context b;
    private String c;
    private boolean e = true;

    public static synchronized BuglyBroadcastReceiver getInstance() {
        BuglyBroadcastReceiver buglyBroadcastReceiver;
        synchronized (BuglyBroadcastReceiver.class) {
            if (d == null) {
                d = new BuglyBroadcastReceiver();
            }
            buglyBroadcastReceiver = d;
        }
        return buglyBroadcastReceiver;
    }

    public synchronized void addFilter(String str) {
        if (!this.a.hasAction(str)) {
            this.a.addAction(str);
        }
        x.c("add action %s", str);
    }

    public synchronized void register(Context context) {
        this.b = context;
        z.a((Runnable) new Runnable() {
            public final void run() {
                try {
                    x.a((Class) BuglyBroadcastReceiver.d.getClass(), "Register broadcast receiver of Bugly.", new Object[0]);
                    synchronized (this) {
                        BuglyBroadcastReceiver.this.b.registerReceiver(BuglyBroadcastReceiver.d, BuglyBroadcastReceiver.this.a);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    public synchronized void unregister(Context context) {
        try {
            x.a((Class) getClass(), "Unregister broadcast receiver of Bugly.", new Object[0]);
            context.unregisterReceiver(this);
            this.b = context;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    public final void onReceive(Context context, Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        try {
            a(context, intent);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00be, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00cc, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean a(android.content.Context r12, android.content.Intent r13) {
        /*
            r11 = this;
            monitor-enter(r11)
            r0 = 0
            if (r12 == 0) goto L_0x00cb
            if (r13 == 0) goto L_0x00cb
            java.lang.String r13 = r13.getAction()     // Catch:{ all -> 0x00c8 }
            java.lang.String r1 = "android.net.conn.CONNECTIVITY_CHANGE"
            boolean r13 = r13.equals(r1)     // Catch:{ all -> 0x00c8 }
            if (r13 != 0) goto L_0x0014
            goto L_0x00cb
        L_0x0014:
            boolean r13 = r11.e     // Catch:{ all -> 0x00c8 }
            r1 = 1
            if (r13 == 0) goto L_0x001d
            r11.e = r0     // Catch:{ all -> 0x00c8 }
            monitor-exit(r11)
            return r1
        L_0x001d:
            android.content.Context r13 = r11.b     // Catch:{ all -> 0x00c8 }
            java.lang.String r13 = com.tencent.bugly.crashreport.common.info.b.c(r13)     // Catch:{ all -> 0x00c8 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c8 }
            java.lang.String r3 = "is Connect BC "
            r2.<init>(r3)     // Catch:{ all -> 0x00c8 }
            r2.append(r13)     // Catch:{ all -> 0x00c8 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00c8 }
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ all -> 0x00c8 }
            com.tencent.bugly.proguard.x.c(r2, r3)     // Catch:{ all -> 0x00c8 }
            java.lang.String r2 = "network %s changed to %s"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00c8 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c8 }
            r4.<init>()     // Catch:{ all -> 0x00c8 }
            java.lang.String r5 = r11.c     // Catch:{ all -> 0x00c8 }
            r4.append(r5)     // Catch:{ all -> 0x00c8 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00c8 }
            r3[r0] = r4     // Catch:{ all -> 0x00c8 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c8 }
            r4.<init>()     // Catch:{ all -> 0x00c8 }
            r4.append(r13)     // Catch:{ all -> 0x00c8 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00c8 }
            r3[r1] = r4     // Catch:{ all -> 0x00c8 }
            com.tencent.bugly.proguard.x.a(r2, r3)     // Catch:{ all -> 0x00c8 }
            if (r13 != 0) goto L_0x0063
            r12 = 0
            r11.c = r12     // Catch:{ all -> 0x00c8 }
            monitor-exit(r11)
            return r1
        L_0x0063:
            java.lang.String r2 = r11.c     // Catch:{ all -> 0x00c8 }
            r11.c = r13     // Catch:{ all -> 0x00c8 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00c8 }
            com.tencent.bugly.crashreport.common.strategy.a r5 = com.tencent.bugly.crashreport.common.strategy.a.a()     // Catch:{ all -> 0x00c8 }
            com.tencent.bugly.proguard.u r6 = com.tencent.bugly.proguard.u.a()     // Catch:{ all -> 0x00c8 }
            com.tencent.bugly.crashreport.common.info.a r12 = com.tencent.bugly.crashreport.common.info.a.a((android.content.Context) r12)     // Catch:{ all -> 0x00c8 }
            if (r5 == 0) goto L_0x00bf
            if (r6 == 0) goto L_0x00bf
            if (r12 != 0) goto L_0x007e
            goto L_0x00bf
        L_0x007e:
            boolean r12 = r13.equals(r2)     // Catch:{ all -> 0x00c8 }
            if (r12 != 0) goto L_0x00bd
            int r12 = com.tencent.bugly.crashreport.crash.c.a     // Catch:{ all -> 0x00c8 }
            long r12 = r6.a((int) r12)     // Catch:{ all -> 0x00c8 }
            long r12 = r3 - r12
            r7 = 30000(0x7530, double:1.4822E-319)
            int r12 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r12 <= 0) goto L_0x00a5
            java.lang.String r12 = "try to upload crash on network changed."
            java.lang.Object[] r13 = new java.lang.Object[r0]     // Catch:{ all -> 0x00c8 }
            com.tencent.bugly.proguard.x.a(r12, r13)     // Catch:{ all -> 0x00c8 }
            com.tencent.bugly.crashreport.crash.c r12 = com.tencent.bugly.crashreport.crash.c.a()     // Catch:{ all -> 0x00c8 }
            if (r12 == 0) goto L_0x00a5
            r9 = 0
            r12.a((long) r9)     // Catch:{ all -> 0x00c8 }
        L_0x00a5:
            r12 = 1001(0x3e9, float:1.403E-42)
            long r12 = r6.a((int) r12)     // Catch:{ all -> 0x00c8 }
            long r3 = r3 - r12
            int r12 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r12 <= 0) goto L_0x00bd
            java.lang.String r12 = "try to upload userinfo on network changed."
            java.lang.Object[] r13 = new java.lang.Object[r0]     // Catch:{ all -> 0x00c8 }
            com.tencent.bugly.proguard.x.a(r12, r13)     // Catch:{ all -> 0x00c8 }
            com.tencent.bugly.crashreport.biz.a r12 = com.tencent.bugly.crashreport.biz.b.a     // Catch:{ all -> 0x00c8 }
            r12.b()     // Catch:{ all -> 0x00c8 }
        L_0x00bd:
            monitor-exit(r11)
            return r1
        L_0x00bf:
            java.lang.String r12 = "not inited BC not work"
            java.lang.Object[] r13 = new java.lang.Object[r0]     // Catch:{ all -> 0x00c8 }
            com.tencent.bugly.proguard.x.d(r12, r13)     // Catch:{ all -> 0x00c8 }
            monitor-exit(r11)
            return r1
        L_0x00c8:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        L_0x00cb:
            monitor-exit(r11)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.BuglyBroadcastReceiver.a(android.content.Context, android.content.Intent):boolean");
    }
}
