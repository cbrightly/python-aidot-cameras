package com.didichuxing.doraemonkit.kit.alignruler;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.AlignRulerConfig;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;

public class AlignRulerKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_align_ruler;
    }

    public int getIcon() {
        return R.mipmap.dk_align_ruler;
    }

    public void onClick(Context context) {
        DokitViewManager.getInstance().detachToolPanel();
        DokitIntent pageIntent = new DokitIntent(AlignRulerMarkerDokitView.class);
        pageIntent.mode = 1;
        DokitViewManager.getInstance().attach(pageIntent);
        DokitIntent pageIntent2 = new DokitIntent(AlignRulerLineDokitView.class);
        pageIntent2.mode = 1;
        DokitViewManager.getInstance().attach(pageIntent2);
        DokitIntent pageIntent3 = new DokitIntent(AlignRulerInfoDokitView.class);
        pageIntent3.mode = 1;
        DokitViewManager.getInstance().attach(pageIntent3);
        AlignRulerConfig.setAlignRulerOpen(true);
    }

    public void onAppInit(Context context) {
        AlignRulerConfig.setAlignRulerOpen(false);
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_ui_ck_aligin_scaleplate";
    }
}
