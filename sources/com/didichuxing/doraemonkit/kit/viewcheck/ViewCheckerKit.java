package com.didichuxing.doraemonkit.kit.viewcheck;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;

public class ViewCheckerKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_view_check;
    }

    public int getIcon() {
        return R.mipmap.dk_view_check;
    }

    public void onClick(Context context) {
        DokitViewManager.getInstance().detachToolPanel();
        DokitIntent intent = new DokitIntent(ViewCheckDokitView.class);
        intent.mode = 1;
        DokitViewManager.getInstance().attach(intent);
        DokitIntent intent2 = new DokitIntent(ViewCheckDrawDokitView.class);
        intent2.mode = 1;
        DokitViewManager.getInstance().attach(intent2);
        DokitIntent intent3 = new DokitIntent(ViewCheckInfoDokitView.class);
        intent3.mode = 1;
        DokitViewManager.getInstance().attach(intent3);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_ui_ck_widget";
    }
}
