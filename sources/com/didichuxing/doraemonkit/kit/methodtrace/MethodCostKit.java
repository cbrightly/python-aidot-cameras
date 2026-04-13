package com.didichuxing.doraemonkit.kit.methodtrace;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;

public class MethodCostKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_method_cost;
    }

    public int getIcon() {
        return R.mipmap.dk_method_cost;
    }

    public void onClick(Context context) {
        startUniversalActivity(context, 23);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_performance_ck_method_coast";
    }
}
