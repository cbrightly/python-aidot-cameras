package com.leedarson.serviceimpl.blec075.reports;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.blec075.util.BleConnectedDeviceDiscoverUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;

/* compiled from: BleConnectDeviceItemReporter */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String a = "";
    public String b = "";
    private String c = "";
    public long d = 0;
    public long e = 0;
    public long f = BaseApplication.b().a1;
    public long g = BaseApplication.b().p1;
    public int h = 200;
    public boolean i = false;
    public int j = 0;
    public String k = "";
    public ArrayList<BleConnectStepBean> l = new ArrayList<>();
    public boolean m = false;
    public String n = "BleConnectDevice";
    private int o = -1;
    private int p = 0;
    BleConnectedDeviceDiscoverUtil.BlutoothConnectedDeviceListBean q = BleConnectedDeviceDiscoverUtil.a(BaseApplication.b());
    private C0127a r = C0127a.BLE_BLE;

    /* renamed from: com.leedarson.serviceimpl.blec075.reports.a$a  reason: collision with other inner class name */
    /* compiled from: BleConnectDeviceItemReporter */
    public enum C0127a {
        BLE_BLE_BUSINESS,
        BLE_BLE;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    private a() {
    }

    public static a c(String bzMac, String modelId, String traceId) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bzMac, modelId, traceId}, (Object) null, changeQuickRedirect2, true, 6545, new Class[]{cls, cls, cls}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        a reporter = new a();
        reporter.a = bzMac;
        reporter.b = modelId;
        reporter.c = traceId;
        reporter.d = System.currentTimeMillis();
        reporter.q = BleConnectedDeviceDiscoverUtil.a(BaseApplication.b());
        return reporter;
    }

    public static a d(String bzMac, String modelId, String traceId, String eventName) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bzMac, modelId, traceId, eventName}, (Object) null, changeQuickRedirect, true, 6546, new Class[]{cls, cls, cls, cls}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        a reporter = c(bzMac, modelId, traceId);
        reporter.n = eventName;
        return reporter;
    }

    public void a(BleConnectStepBean stepBean) {
        if (!PatchProxy.proxy(new Object[]{stepBean}, this, changeQuickRedirect, false, 6547, new Class[]{BleConnectStepBean.class}, Void.TYPE).isSupported) {
            this.l.add(stepBean);
        }
    }

    public void e() {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6548, new Class[0], Void.TYPE).isSupported) {
            try {
                this.e = System.currentTimeMillis();
                String stepName = "";
                for (int i3 = 0; i3 < this.l.size(); i3++) {
                    this.h = this.l.get(i3).code;
                    this.e = this.l.get(i3).endTime;
                    this.k = this.l.get(i3).desc;
                    stepName = this.l.get(i3).stepName;
                }
                long j2 = this.e;
                long duration = j2 - this.d;
                long appStartToDeviceConnectDuration = j2 - this.f;
                long appDidRenderToConnectDuration = j2 - this.g;
                BleConnectedDeviceDiscoverUtil.BlutoothConnectedDeviceListBean _tempResult = BleConnectedDeviceDiscoverUtil.a(BaseApplication.b());
                String steps = new Gson().toJson((Object) this.l);
                this.l.clear();
                com.leedarson.log.elk.a u = com.leedarson.log.elk.a.y(this).c(a.class.getSimpleName()).t(this.r == C0127a.BLE_BLE ? Constants.SERVICE_BLUETOOTH_NEW : Constants.SERVICE_BLUETOOTH_BUSINESS).x(this.c).o("info").e("BleConnectDevice".equals(this.n) ? "connect" : this.n).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(duration)).u("appStartToDeviceConnectDuration", Long.valueOf(appStartToDeviceConnectDuration)).u("appDidRenderToConnectDuration", Long.valueOf(appDidRenderToConnectDuration)).u("retryTimes", Integer.valueOf(this.j)).u("modelId", this.b).u("code", Integer.valueOf(this.h)).u("desc", TextUtils.isEmpty(this.k) ? stepName : this.k).u("mac", this.a).u("bleSysConnectDescAfter", _tempResult.printResult()).u("bleSysConnectedSizeAfter", Integer.valueOf(_tempResult.getConnectedDevices().size())).u("bleSysConnectDescBefor", this.q.dataResult).u("bleSysConnectedSizeBefor", Integer.valueOf(_tempResult.connectedDeviceSize)).u("checkPairingStatus", Integer.valueOf(h())).u("autoConnect", Integer.valueOf(f())).u("bleRssiNumber", Integer.valueOf(g()));
                if (this.m) {
                    i2 = 1;
                }
                u.u("isUseBleCacheDevice", Integer.valueOf(i2)).p(steps).a().b();
            } catch (Throwable th) {
            }
        }
    }

    public void b(C0127a mode) {
        this.r = mode;
    }

    public void j(int _checkPairingStatue) {
        this.o = _checkPairingStatue;
    }

    public int h() {
        return this.o;
    }

    public int f() {
        return this.i ? 1 : 0;
    }

    public void i(int _bleRssiNumber) {
        this.p = _bleRssiNumber;
    }

    public int g() {
        return this.p;
    }
}
