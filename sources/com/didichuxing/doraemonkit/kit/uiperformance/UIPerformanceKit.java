package com.didichuxing.doraemonkit.kit.uiperformance;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;

public class UIPerformanceKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_ui_performance;
    }

    public int getIcon() {
        return R.mipmap.dk_ui_performance;
    }

    public void onClick(Context context) {
        UIPerformanceManager.getInstance().start(context);
        DokitViewManager.getInstance().detachToolPanel();
        DokitIntent intent = new DokitIntent(UIPerformanceDisplayDokitView.class);
        intent.mode = 1;
        DokitViewManager.getInstance().attach(intent);
        DokitIntent intentInfo = new DokitIntent(UIPerformanceInfoDokitView.class);
        intentInfo.mode = 1;
        DokitViewManager.getInstance().attach(intentInfo);
        UIPerformanceManager.getInstance().initRefresh();
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_performance_ck_hierarchy";
    }
}
