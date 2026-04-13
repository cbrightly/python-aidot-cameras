package com.leedarson.newui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import com.leedarson.base.views.common.LDSTextView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Locale;

public class TrafficStatsTextView extends LDSTextView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long a1;
    private long a2;
    /* access modifiers changed from: private */
    public int p0;
    private long p1;
    /* access modifiers changed from: private */
    public b p2;
    /* access modifiers changed from: private */
    public Handler y;
    private a z;

    public interface b {
        void a();
    }

    public TrafficStatsTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public TrafficStatsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.p0 = 0;
        this.a1 = 0;
        this.p1 = 0;
        this.a2 = 0;
        this.y = new Handler();
        this.z = new a();
    }

    public void k() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5282, new Class[0], Void.TYPE).isSupported) {
            l();
            this.y.postDelayed(this.z, 1000);
            this.p0 = 0;
        }
    }

    public void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5283, new Class[0], Void.TYPE).isSupported) {
            this.y.removeCallbacksAndMessages((Object) null);
        }
    }

    public void h(String[] arr) {
        if (!PatchProxy.proxy(new Object[]{arr}, this, changeQuickRedirect, false, 5284, new Class[]{String[].class}, Void.TYPE).isSupported) {
            setText(arr[0] + "/s");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ac, code lost:
        return r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String[] j(android.content.Context r23) {
        /*
            r22 = this;
            monitor-enter(r22)
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x00ad }
            r8 = 0
            r1[r8] = r23     // Catch:{ all -> 0x00ad }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x00ad }
            r4 = 0
            r5 = 5285(0x14a5, float:7.406E-42)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x00ad }
            java.lang.Class<android.content.Context> r2 = android.content.Context.class
            r6[r8] = r2     // Catch:{ all -> 0x00ad }
            java.lang.Class<java.lang.String[]> r7 = java.lang.String[].class
            r2 = r22
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00ad }
            boolean r2 = r1.isSupported     // Catch:{ all -> 0x00ad }
            if (r2 == 0) goto L_0x0024
            java.lang.Object r0 = r1.result     // Catch:{ all -> 0x00ad }
            java.lang.String[] r0 = (java.lang.String[]) r0     // Catch:{ all -> 0x00ad }
            monitor-exit(r22)
            return r0
        L_0x0024:
            r1 = r22
            r2 = r23
            java.lang.String r3 = "0 kb/s"
            java.lang.String r4 = "0 kb/s"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4}     // Catch:{ all -> 0x00ad }
            java.lang.String r4 = "0 kb/s"
            java.lang.String r5 = "0 kb/s"
            android.content.pm.ApplicationInfo r6 = r2.getApplicationInfo()     // Catch:{ all -> 0x00ad }
            int r6 = r6.uid     // Catch:{ all -> 0x00ad }
            long r6 = android.net.TrafficStats.getUidRxBytes(r6)     // Catch:{ all -> 0x00ad }
            r9 = -1
            int r6 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            r11 = 0
            if (r6 != 0) goto L_0x0048
            r6 = r11
            goto L_0x004c
        L_0x0048:
            long r6 = android.net.TrafficStats.getTotalRxBytes()     // Catch:{ all -> 0x00ad }
        L_0x004c:
            android.content.pm.ApplicationInfo r13 = r2.getApplicationInfo()     // Catch:{ all -> 0x00ad }
            int r13 = r13.uid     // Catch:{ all -> 0x00ad }
            long r13 = android.net.TrafficStats.getUidTxBytes(r13)     // Catch:{ all -> 0x00ad }
            int r9 = (r13 > r9 ? 1 : (r13 == r9 ? 0 : -1))
            if (r9 != 0) goto L_0x005c
            r9 = r11
            goto L_0x0060
        L_0x005c:
            long r9 = android.net.TrafficStats.getTotalTxBytes()     // Catch:{ all -> 0x00ad }
        L_0x0060:
            long r13 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00ad }
            r16 = r9
            long r8 = r1.a2     // Catch:{ all -> 0x00ad }
            long r8 = r13 - r8
            int r10 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            if (r10 != 0) goto L_0x0070
            r8 = 1
        L_0x0070:
            long r10 = r1.a1     // Catch:{ all -> 0x00ad }
            long r10 = r6 - r10
            r18 = 1000(0x3e8, double:4.94E-321)
            long r10 = r10 * r18
            long r10 = r10 / r8
            r23 = r4
            r12 = r5
            long r4 = r1.p1     // Catch:{ all -> 0x00ad }
            long r4 = r16 - r4
            long r4 = r4 * r18
            long r4 = r4 / r8
            r1.a2 = r13     // Catch:{ all -> 0x00ad }
            r1.a1 = r6     // Catch:{ all -> 0x00ad }
            r18 = r6
            r6 = r16
            r1.p1 = r6     // Catch:{ all -> 0x00ad }
            java.lang.String r16 = r1.i(r10)     // Catch:{ all -> 0x00ad }
            java.lang.String r17 = r1.i(r4)     // Catch:{ all -> 0x00ad }
            r12 = r17
            r15 = 0
            r3[r15] = r16     // Catch:{ all -> 0x00ad }
            r3[r0] = r12     // Catch:{ all -> 0x00ad }
            r20 = 5
            int r15 = (r10 > r20 ? 1 : (r10 == r20 ? 0 : -1))
            if (r15 < 0) goto L_0x00a6
            int r15 = (r4 > r20 ? 1 : (r4 == r20 ? 0 : -1))
            if (r15 >= 0) goto L_0x00ab
        L_0x00a6:
            int r15 = r1.p0     // Catch:{ all -> 0x00ad }
            int r15 = r15 + r0
            r1.p0 = r15     // Catch:{ all -> 0x00ad }
        L_0x00ab:
            monitor-exit(r22)
            return r3
        L_0x00ad:
            r0 = move-exception
            monitor-exit(r22)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.TrafficStatsTextView.j(android.content.Context):java.lang.String[]");
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        public a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5287, new Class[0], Void.TYPE).isSupported) {
                TrafficStatsTextView trafficStatsTextView = TrafficStatsTextView.this;
                TrafficStatsTextView.this.h(trafficStatsTextView.j(trafficStatsTextView.getContext()));
                if (TrafficStatsTextView.this.p0 != 5 || TrafficStatsTextView.this.p2 == null) {
                    TrafficStatsTextView.this.y.postDelayed(this, 1000);
                    return;
                }
                TrafficStatsTextView.this.y.removeCallbacks(this);
                TrafficStatsTextView.this.p2.a();
                int unused = TrafficStatsTextView.this.p0 = 0;
            }
        }
    }

    public void setOnLowSpeedListener(b onLowSpeedListener) {
        this.p2 = onLowSpeedListener;
    }

    @SuppressLint({"DefaultLocale"})
    private String i(long b2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Long(b2)}, this, changeQuickRedirect, false, 5286, new Class[]{Long.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (b2 < 1024) {
            return b2 + "B";
        } else if (b2 < 1048576) {
            return String.format(Locale.US, "%.0fK", new Object[]{Float.valueOf((((float) b2) * 1.0f) / 1024.0f)});
        } else {
            return String.format(Locale.US, "%.2fM", new Object[]{Float.valueOf(((((float) b2) * 1.0f) / 1024.0f) / 1024.0f)});
        }
    }
}
