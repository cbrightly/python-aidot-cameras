package com.didichuxing.doraemonkit.kit.parameter.ram;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;

public class RamKit extends AbstractKit {
    public int getName() {
        return R.string.dk_frameinfo_ram;
    }

    public int getIcon() {
        return R.mipmap.dk_ram;
    }

    public void onClick(Context context) {
        startUniversalActivity(context, 15);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_performance_ck_arm";
    }
}
