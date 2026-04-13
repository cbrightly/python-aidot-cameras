package com.didichuxing.doraemonkit.kit.largepicture;

import android.content.Context;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.aop.DokitPluginConfig;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.util.DokitUtil;

public class LargePictureKit extends AbstractKit {
    public int getName() {
        return R.string.dk_frameinfo_big_img;
    }

    public int getIcon() {
        return R.mipmap.dk_performance_large_picture;
    }

    public void onClick(Context context) {
        if (!DokitPluginConfig.SWITCH_DOKIT_PLUGIN) {
            e0.n(DokitUtil.getString(R.string.dk_plugin_close_tip));
        } else if (!DokitPluginConfig.SWITCH_BIG_IMG) {
            e0.n(DokitUtil.getString(R.string.dk_plugin_big_img_close_tip));
        } else {
            startUniversalActivity(context, 22);
        }
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_performance_ck_img";
    }
}
