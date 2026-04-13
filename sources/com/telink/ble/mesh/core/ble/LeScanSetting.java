package com.telink.ble.mesh.core.ble;

import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class LeScanSetting {
    public static ChangeQuickRedirect changeQuickRedirect;
    public long a;
    public long b;

    public static LeScanSetting a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 12393, new Class[0], LeScanSetting.class);
        if (proxy.isSupported) {
            return (LeScanSetting) proxy.result;
        }
        LeScanSetting setting = new LeScanSetting();
        setting.a = KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS;
        setting.b = 10000;
        return setting;
    }

    public LeScanSetting() {
    }

    public LeScanSetting(long spacing, long during) {
        this.a = spacing;
        this.b = during;
    }
}
