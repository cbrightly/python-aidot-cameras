package com.didichuxing.doraemonkit.aop;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager;

public class AMapLocationListenerProxy implements AMapLocationListener {
    AMapLocationListener aMapLocationListener;

    public AMapLocationListenerProxy(AMapLocationListener aMapLocationListener2) {
        this.aMapLocationListener = aMapLocationListener2;
    }

    public void onLocationChanged(AMapLocation mapLocation) {
        if (GpsMockManager.getInstance().isMocking()) {
            try {
                mapLocation.setLatitude(GpsMockManager.getInstance().getLatitude());
                mapLocation.setLongitude(GpsMockManager.getInstance().getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Exception e2 = this.aMapLocationListener;
        if (e2 != null) {
            e2.onLocationChanged(mapLocation);
        }
    }
}
