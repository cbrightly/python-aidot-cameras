package com.didichuxing.doraemonkit.kit.network;

import android.content.Context;
import android.text.TextUtils;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.aop.DokitPluginConfig;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.util.DokitUtil;

public class MockKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_network_mock;
    }

    public int getIcon() {
        return R.mipmap.dk_net_mock;
    }

    public void onClick(Context context) {
        if (!DokitPluginConfig.SWITCH_DOKIT_PLUGIN) {
            e0.n(DokitUtil.getString(R.string.dk_plugin_close_tip));
        } else if (!DokitPluginConfig.SWITCH_NETWORK) {
            e0.n(DokitUtil.getString(R.string.dk_plugin_network_close_tip));
        } else if (TextUtils.isEmpty(DokitConstant.PRODUCT_ID)) {
            e0.n(DokitUtil.getString(R.string.dk_platform_tip));
        } else {
            startUniversalActivity(context, 25);
        }
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_platform_ck_mock";
    }
}
