package com.didichuxing.doraemonkit.kit.weaknetwork;

import android.content.Context;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.aop.DokitPluginConfig;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.util.DokitUtil;

public class WeakNetworkKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_weak_network;
    }

    public int getIcon() {
        return R.mipmap.dk_weak_network;
    }

    public void onClick(Context context) {
        if (!DokitPluginConfig.SWITCH_DOKIT_PLUGIN) {
            e0.n(DokitUtil.getString(R.string.dk_plugin_close_tip));
        } else if (!DokitPluginConfig.SWITCH_NETWORK) {
            e0.n(DokitUtil.getString(R.string.dk_plugin_network_close_tip));
        } else {
            startUniversalActivity(context, 21);
        }
    }

    public void onAppInit(Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_comm_ck_weaknetwork";
    }
}
