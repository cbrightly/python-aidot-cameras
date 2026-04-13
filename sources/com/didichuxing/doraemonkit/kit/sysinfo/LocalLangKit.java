package com.didichuxing.doraemonkit.kit.sysinfo;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.util.SystemUtil;

public class LocalLangKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_local_lang;
    }

    public int getIcon() {
        return R.mipmap.dk_kit_local_lang;
    }

    public void onClick(Context context) {
        SystemUtil.startLocalActivity(context);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_comm_ck_local_lang";
    }
}
