package com.leedarson.serviceimpl.blec075.strategy;

import com.clj.fastble.data.BleDevice;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import java.util.concurrent.TimeUnit;
import timber.log.a;

/* compiled from: SeekBleTargetFlowable */
public class l {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a = 1;
    private int b = 2;
    private int c = 0;
    private String d = "";
    private String e = "";

    public l(int retryMaxTimes, int retryAfterDelayMillis, String devMac, String advertisingData) {
        this.a = retryMaxTimes;
        this.b = retryAfterDelayMillis;
        this.d = devMac;
        this.e = advertisingData;
    }

    public e<BleDevice> b(BleDevice bleDevice) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6590, new Class[]{BleDevice.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        if (bleDevice.a() != null) {
            a("已经重新搜索到设备.....");
            return e.w(bleDevice);
        }
        int i = this.c + 1;
        this.c = i;
        if (i < this.a) {
            a("进行设备重新搜索(当前).....  retryCount=" + this.c + ",retryTimes=" + this.a);
            return e.R((long) this.b, TimeUnit.MILLISECONDS).o(new h(this, new i())).o(new g(this));
        }
        a("搜索尝试次数已用尽，认命吧，搜索不到设备....retryCount=" + this.c + ",retryTimes=" + this.a);
        return e.w(bleDevice);
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public /* synthetic */ e d(i bleConnectDeviceImproveStrategyV2, Long l) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleConnectDeviceImproveStrategyV2, l}, this, changeQuickRedirect2, false, 6593, new Class[]{i.class, Long.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        a("延迟一段时间，retryAfterDelayMillis=" + this.b + "ms,开始重新开始搜索......");
        return bleConnectDeviceImproveStrategyV2.j(this.d, this.e, 5, "SeekBleTargetFlowable createSeekFlowable");
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public /* synthetic */ e f(BleDevice bleDevice1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleDevice1}, this, changeQuickRedirect, false, 6592, new Class[]{BleDevice.class}, e.class);
        return proxy.isSupported ? (e) proxy.result : b(bleDevice1);
    }

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6591, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = a.g("seekTarget");
            g.m("LdsConnectDevice: " + msg, new Object[0]);
        }
    }
}
