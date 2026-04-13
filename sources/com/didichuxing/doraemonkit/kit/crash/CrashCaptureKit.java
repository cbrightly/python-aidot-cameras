package com.didichuxing.doraemonkit.kit.crash;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.CrashCaptureConfig;
import com.didichuxing.doraemonkit.kit.AbstractKit;

public class CrashCaptureKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_crash;
    }

    public int getIcon() {
        return R.mipmap.dk_crash_catch;
    }

    public void onClick(Context context) {
        startUniversalActivity(context, 11);
    }

    public void onAppInit(Context context) {
        CrashCaptureManager.getInstance().init(context);
        if (CrashCaptureConfig.isCrashCaptureOpen()) {
            CrashCaptureManager.getInstance().start();
        } else {
            CrashCaptureManager.getInstance().stop();
        }
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_comm_ck_crash";
    }
}
