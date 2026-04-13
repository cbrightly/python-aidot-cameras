package com.didichuxing.doraemonkit.kit.dataclean;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;

public class DataCleanKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_data_clean;
    }

    public int getIcon() {
        return R.mipmap.dk_data_clean;
    }

    public void onClick(Context context) {
        startUniversalActivity(context, 10);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_comm_ck_cache";
    }
}
