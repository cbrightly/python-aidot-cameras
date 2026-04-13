package com.sensorsdata.analytics.android.sdk.aop.push;

import com.didichuxing.doraemonkit.util.SystemUtil;

public class PushUtils {
    public static String getJPushSDKName(byte whichPushSDK) {
        switch (whichPushSDK) {
            case 1:
                return "Xiaomi";
            case 2:
                return SystemUtil.PHONE_HUAWEI;
            case 3:
                return SystemUtil.PHONE_MEIZU;
            case 4:
                return "OPPO";
            case 5:
                return SystemUtil.PHONE_VIVO;
            default:
                return null;
        }
    }
}
