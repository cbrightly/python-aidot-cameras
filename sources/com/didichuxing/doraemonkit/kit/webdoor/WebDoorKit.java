package com.didichuxing.doraemonkit.kit.webdoor;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;

public class WebDoorKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_web_door;
    }

    public int getIcon() {
        return R.mipmap.dk_web_door;
    }

    public void onClick(Context context) {
        startUniversalActivity(context, 9);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_comm_ck_h5";
    }
}
