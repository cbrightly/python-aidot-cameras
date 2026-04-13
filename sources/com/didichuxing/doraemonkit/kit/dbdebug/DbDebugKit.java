package com.didichuxing.doraemonkit.kit.dbdebug;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;

public class DbDebugKit extends AbstractKit {
    public int getName() {
        return R.string.dk_tools_dbdebug;
    }

    public int getIcon() {
        return R.mipmap.dk_db_view;
    }

    public void onClick(Context context) {
        startUniversalActivity(context, 31);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_comm_ck_dbview";
    }
}
