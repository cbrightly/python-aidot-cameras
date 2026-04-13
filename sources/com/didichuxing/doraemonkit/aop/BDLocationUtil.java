package com.didichuxing.doraemonkit.aop;

import com.baidu.location.BDLocation;
import com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager;

public class BDLocationUtil {
    public static BDLocation proxy(BDLocation bdLocation) {
        if (GpsMockManager.getInstance().isMocking()) {
            try {
                bdLocation.setLatitude(GpsMockManager.getInstance().getLatitude());
                bdLocation.setLongitude(GpsMockManager.getInstance().getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bdLocation;
    }
}
