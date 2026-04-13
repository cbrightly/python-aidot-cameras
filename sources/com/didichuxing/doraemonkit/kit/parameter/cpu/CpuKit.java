package com.didichuxing.doraemonkit.kit.parameter.cpu;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;

public class CpuKit extends AbstractKit {
    public int getName() {
        return R.string.dk_frameinfo_cpu;
    }

    public int getIcon() {
        return R.mipmap.dk_cpu;
    }

    public void onClick(Context context) {
        startUniversalActivity(context, 14);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_performance_ck_cpu";
    }
}
