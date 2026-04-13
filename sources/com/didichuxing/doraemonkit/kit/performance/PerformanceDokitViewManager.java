package com.didichuxing.doraemonkit.kit.performance;

import android.content.Context;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import java.util.TreeMap;

public class PerformanceDokitViewManager {
    public static TreeMap<String, performanceViewInfo> singleperformanceViewInfos = new TreeMap<>();

    public static void open(int performanceType, String title, PerformanceFragmentCloseListener listener) {
        open(performanceType, title, 1000, listener);
    }

    /* JADX WARNING: type inference failed for: r0v3, types: [com.didichuxing.doraemonkit.kit.core.AbsDokitView] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void open(int r5, java.lang.String r6, int r7, com.didichuxing.doraemonkit.kit.performance.PerformanceFragmentCloseListener r8) {
        /*
            java.lang.Class<com.didichuxing.doraemonkit.kit.performance.PerformanceDokitView> r0 = com.didichuxing.doraemonkit.kit.performance.PerformanceDokitView.class
            com.didichuxing.doraemonkit.kit.core.DokitViewManager r1 = com.didichuxing.doraemonkit.kit.core.DokitViewManager.getInstance()
            android.app.Activity r2 = com.blankj.utilcode.util.a.b()
            java.lang.String r3 = r0.getSimpleName()
            com.didichuxing.doraemonkit.kit.core.AbsDokitView r1 = r1.getDokitView(r2, r3)
            com.didichuxing.doraemonkit.kit.performance.PerformanceDokitView r1 = (com.didichuxing.doraemonkit.kit.performance.PerformanceDokitView) r1
            if (r1 != 0) goto L_0x003c
            com.didichuxing.doraemonkit.kit.core.DokitIntent r2 = new com.didichuxing.doraemonkit.kit.core.DokitIntent
            r2.<init>(r0)
            r3 = 1
            r2.mode = r3
            com.didichuxing.doraemonkit.kit.core.DokitViewManager r3 = com.didichuxing.doraemonkit.kit.core.DokitViewManager.getInstance()
            r3.attach(r2)
            com.didichuxing.doraemonkit.kit.core.DokitViewManager r3 = com.didichuxing.doraemonkit.kit.core.DokitViewManager.getInstance()
            android.app.Activity r4 = com.blankj.utilcode.util.a.b()
            java.lang.String r0 = r0.getSimpleName()
            com.didichuxing.doraemonkit.kit.core.AbsDokitView r0 = r3.getDokitView(r4, r0)
            r1 = r0
            com.didichuxing.doraemonkit.kit.performance.PerformanceDokitView r1 = (com.didichuxing.doraemonkit.kit.performance.PerformanceDokitView) r1
            r1.addItem(r5, r6, r7)
            goto L_0x003f
        L_0x003c:
            r1.addItem(r5, r6, r7)
        L_0x003f:
            r1.addPerformanceFragmentCloseListener(r8)
            java.util.TreeMap<java.lang.String, com.didichuxing.doraemonkit.kit.performance.performanceViewInfo> r0 = singleperformanceViewInfos
            com.didichuxing.doraemonkit.kit.performance.performanceViewInfo r2 = new com.didichuxing.doraemonkit.kit.performance.performanceViewInfo
            r2.<init>(r5, r6, r7)
            r0.put(r6, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.performance.PerformanceDokitViewManager.open(int, java.lang.String, int, com.didichuxing.doraemonkit.kit.performance.PerformanceFragmentCloseListener):void");
    }

    public static void onPerformanceSettingFragmentDestroy(PerformanceFragmentCloseListener listener) {
        PerformanceDokitView performanceDokitView = (PerformanceDokitView) DokitViewManager.getInstance().getDokitView(a.b(), PerformanceDokitView.class.getSimpleName());
        if (performanceDokitView != null) {
            performanceDokitView.removePerformanceFragmentCloseListener(listener);
        }
    }

    public static void close(int performanceType, String title) {
        PerformanceDokitView performanceDokitView = (PerformanceDokitView) DokitViewManager.getInstance().getDokitView(a.b(), PerformanceDokitView.class.getSimpleName());
        if (performanceDokitView != null) {
            performanceDokitView.removeItem(performanceType);
        }
        singleperformanceViewInfos.remove(title);
    }

    public static String getTitleByPerformanceType(Context context, int performanceType) {
        switch (performanceType) {
            case 1:
                return context.getString(R.string.dk_kit_net_monitor);
            case 2:
                return context.getString(R.string.dk_frameinfo_cpu);
            case 3:
                return context.getString(R.string.dk_ram_detection_title);
            case 4:
                return context.getString(R.string.dk_kit_frame_info_desc);
            default:
                return "";
        }
    }
}
