package com.didichuxing.doraemonkit.kit.loginfo;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.LogInfoConfig;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;

public class LogInfoKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_log_info;
    }

    public int getIcon() {
        return R.mipmap.dk_log_info;
    }

    public void onClick(Context context) {
        DokitIntent intent = new DokitIntent(LogInfoDokitView.class);
        intent.mode = 1;
        DokitViewManager.getInstance().attach(intent);
        LogInfoManager.getInstance().start();
    }

    public void onAppInit(Context context) {
        LogInfoConfig.setLogInfoOpen(false);
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_comm_ck_log";
    }
}
