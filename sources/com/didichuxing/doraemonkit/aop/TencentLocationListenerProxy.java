package com.didichuxing.doraemonkit.aop;

import com.blankj.utilcode.util.ReflectUtils;
import com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;

public class TencentLocationListenerProxy implements TencentLocationListener {
    private static final String TAG = "TencentLocationListenerProxy";
    TencentLocationListener tencentLocationListener;

    public TencentLocationListenerProxy(TencentLocationListener tencentLocationListener2) {
        this.tencentLocationListener = tencentLocationListener2;
    }

    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        if (GpsMockManager.getInstance().isMocking()) {
            try {
                Object b = ReflectUtils.g(tencentLocation).b("b").d();
                ReflectUtils.g(b).c("a", Double.valueOf(GpsMockManager.getInstance().getLatitude()));
                ReflectUtils.g(b).c("b", Double.valueOf(GpsMockManager.getInstance().getLongitude()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Exception e2 = this.tencentLocationListener;
        if (e2 != null) {
            e2.onLocationChanged(tencentLocation, i, s);
        }
    }

    public void onStatusUpdate(String s, int i, String s1) {
        TencentLocationListener tencentLocationListener2 = this.tencentLocationListener;
        if (tencentLocationListener2 != null) {
            tencentLocationListener2.onStatusUpdate(s, i, s);
        }
    }
}
