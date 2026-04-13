package com.didichuxing.doraemonkit.kit.layoutborder;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.LayoutBorderConfig;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;

public class LayoutBorderKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_layout_border;
    }

    public int getIcon() {
        return R.mipmap.dk_view_border;
    }

    public void onClick(Context context) {
        DokitViewManager.getInstance().detachToolPanel();
        DokitIntent intent = new DokitIntent(LayoutLevelDokitView.class);
        intent.mode = 1;
        DokitViewManager.getInstance().attach(intent);
        LayoutBorderManager.getInstance().start();
        LayoutBorderConfig.setLayoutBorderOpen(true);
        LayoutBorderConfig.setLayoutLevelOpen(true);
    }

    public void onAppInit(Context context) {
        LayoutBorderConfig.setLayoutBorderOpen(false);
        LayoutBorderConfig.setLayoutLevelOpen(false);
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_ui_ck_border";
    }
}
