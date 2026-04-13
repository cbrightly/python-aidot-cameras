package com.didichuxing.doraemonkit.kit.sysinfo;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;

public class SysInfoKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_sysinfo;
    }

    public int getIcon() {
        return R.mipmap.dk_sys_info;
    }

    public void onClick(Context context) {
        startUniversalActivity(context, 1);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_comm_ck_appinfo";
    }
}
