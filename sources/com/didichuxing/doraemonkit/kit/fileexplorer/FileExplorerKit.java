package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;

public class FileExplorerKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_file_explorer;
    }

    public int getIcon() {
        return R.mipmap.dk_file_explorer;
    }

    public void onClick(Context context) {
        startUniversalActivity(context, 2);
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_comm_ck_sandbox";
    }
}
