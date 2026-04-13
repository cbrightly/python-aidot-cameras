package com.didichuxing.doraemonkit.kit.blockmonitor;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;

public class BlockMonitorKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_block_monitor;
    }

    public int getIcon() {
        return R.mipmap.dk_block_monitor;
    }

    public void onClick(Context context) {
        startUniversalActivity(context, 8);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_performance_ck_block";
    }
}
