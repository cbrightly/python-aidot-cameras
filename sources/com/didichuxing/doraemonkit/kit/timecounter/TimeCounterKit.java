package com.didichuxing.doraemonkit.kit.timecounter;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;

public class TimeCounterKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_time_counter;
    }

    public int getIcon() {
        return R.mipmap.dk_time_counter;
    }

    public void onClick(Context context) {
        startUniversalActivity(context, 17);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_performance_ck_open_coast";
    }
}
