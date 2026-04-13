package com.leedarson.smarthome.util;

import android.content.Context;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.iotsolution.preaidot.R;
import com.leedarson.serviceinterface.ShakeService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: LDSEnvKitDebug */
public class a extends AbstractKit {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int getIcon() {
        return R.mipmap.logo;
    }

    public int getName() {
        return R.string.app_name;
    }

    public void onAppInit(Context context) {
    }

    public void onClick(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 10739, new Class[]{Context.class}, Void.TYPE).isSupported) {
            ShakeService shakeService = (ShakeService) com.alibaba.android.arouter.launcher.a.c().g(ShakeService.class);
            if (shakeService != null) {
                shakeService.jumpShakeActivity(context);
            }
        }
    }
}
