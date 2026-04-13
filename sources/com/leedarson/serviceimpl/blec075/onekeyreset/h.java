package com.leedarson.serviceimpl.blec075.onekeyreset;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.clj.fastble.data.BleDevice;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.w;
import com.leedarson.bean.Constants;
import com.leedarson.log.elk.a;
import com.leedarson.log.tracker.BaseStepBean;
import com.leedarson.serviceimpl.blec075.BleC075ServiceImpl;
import com.leedarson.serviceimpl.blec075.e;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceimpl.blec075.strategy.i;
import com.leedarson.serviceimpl.blec075.util.c;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.disposables.b;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import meshsdk.BaseResp;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

/* compiled from: OneKeyResetDispatcher */
public class h {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "BleC075ServiceImplReset";
    private String b = "";
    private e c;
    private AtomicInteger d;
    private int e;
    private int f = 0;
    private Handler g = new Handler();
    private BleC075ServiceImpl h;
    private Context i;
    private f j = f.IDLE;
    private g k;
    b l;
    private Runnable m = new b(this);
    b n;

    public h(BleC075ServiceImpl bleC075Service, Context context) {
        this.i = context;
        this.h = bleC075Service;
    }

    public void o(h.a restDataUnit, JSONObject jSONObject, String deviceName) {
        Class[] clsArr = {h.a.class, JSONObject.class, String.class};
        if (!PatchProxy.proxy(new Object[]{restDataUnit, jSONObject, deviceName}, this, changeQuickRedirect, false, 6531, clsArr, Void.TYPE).isSupported) {
            JSONObject jsonObject = jSONObject;
            String advertisementData = com.leedarson.serviceimpl.blec075.h.b(restDataUnit.c);
            a("onAdvertise rest - find advertisementData : " + advertisementData + ", restCmd = " + this.f);
            jsonObject.put("advertisingData", (Object) advertisementData);
            jsonObject.put("deviceName", (Object) deviceName);
            String tid = advertisementData.substring(6, 8);
            String mac = advertisementData.substring(8, 20);
            String cmd = advertisementData.substring(20, 22);
            int curTid = new BigInteger(tid, 16).intValue();
            a("onAdvertise rest - find mac : " + mac + ", cmd = " + cmd + ", tid = " + curTid + ", macAddress=" + this.b + "  adverdata=" + advertisementData);
            if (!TextUtils.isEmpty(this.b) && this.b.toLowerCase().equals(mac.toLowerCase())) {
                if ("81".equals(cmd)) {
                    if (t(f.DISCOVERED)) {
                        a("rest - 81 Discover指令响应");
                        q(11);
                        this.c.i();
                        this.f = 3;
                        String randomCode = advertisementData.substring(22, 28);
                        a("rest - cmdData 随机码：" + randomCode);
                        int c2 = c();
                        this.e = c2;
                        String serviceData = this.c.h(this.f, c2, this.b, randomCode);
                        BaseStepBean discoveryStep = new BaseStepBean("deviceDiscovered", 200);
                        discoveryStep.putRequestParams("receiveData", advertisementData);
                        discoveryStep.putRequestParams("serviceData", serviceData);
                        discoveryStep.putRequestParams("cmd", 3);
                        discoveryStep.putRequestParams("receiveCmdData", randomCode);
                        discoveryStep.setDesc("发现设备，等待设备复位");
                        this.k.e(discoveryStep);
                        q(30);
                    }
                } else if ("82".equals(cmd)) {
                    a("rest - identify指令响应");
                    this.c.i();
                    String cmdData = advertisementData.substring(22, 28);
                    a("rest - cmdData 随机码：" + cmdData);
                    this.f = 3;
                    int c3 = c();
                    this.e = c3;
                    this.c.h(this.f, c3, this.b, cmdData);
                } else if ("83".equals(cmd) && t(f.RESETED)) {
                    a("rest - 83 复位响应");
                    this.f = 0;
                    this.c.i();
                    this.h.stopScan();
                    Handler handler = this.g;
                    if (handler != null) {
                        handler.removeCallbacks(this.m);
                    }
                    BaseStepBean discoveryStep2 = new BaseStepBean("deviceReset", 200);
                    discoveryStep2.putRequestParams("receiveData", advertisementData);
                    discoveryStep2.setDesc("设备已复位");
                    this.k.e(discoveryStep2);
                    this.k.l();
                    q(31);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public /* synthetic */ void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6542, new Class[0], Void.TYPE).isSupported) {
            try {
                c checkDeviceIsBoundedStatue = new c();
                i bleConnectDeviceImproveStrategyV2 = new i();
                String desc = "";
                int step = 0;
                int i2 = this.f;
                if (i2 == 1) {
                    step = 12;
                    desc = "设备无响应Discover指令";
                } else if (i2 == 2) {
                    step = 22;
                    desc = "设备无响应灯效提示指令";
                } else if (i2 == 3) {
                    step = 32;
                    desc = "设备无响应复位指令";
                }
                b bVar = this.l;
                if (bVar != null && !bVar.isDisposed()) {
                    this.l.dispose();
                }
                int finalStep = step;
                this.l = bleConnectDeviceImproveStrategyV2.j(this.b, "", 5, "onekeyResetDispatcher resetTimeoutTask").c(l.c()).I(new a(this, finalStep, checkDeviceIsBoundedStatue), new c(this, finalStep));
                BaseStepBean discoveryStep = new BaseStepBean("resetTimeout", BaseResp.ERR_MSG_SEND_FAIL);
                discoveryStep.setDesc("复位超时:" + desc);
                this.k.e(discoveryStep);
                this.k.l();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public /* synthetic */ void f(int finalStep, c checkDeviceIsBoundedStatue, BleDevice bleDevice) {
        Object[] objArr = {new Integer(finalStep), checkDeviceIsBoundedStatue, bleDevice};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6544, new Class[]{Integer.TYPE, c.class, BleDevice.class}, Void.TYPE).isSupported) {
            if (bleDevice.a() == null) {
                a("没有发现到待复位设备 .......  macAddress=" + this.b);
                q(finalStep);
            } else if (!checkDeviceIsBoundedStatue.c(bleDevice.i(), this.b)) {
                a("设备已处于wifi未绑定状态,已被复位");
                q(31);
            } else {
                q(finalStep);
            }
            b();
            this.h.stopScan();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public /* synthetic */ void h(int finalStep, Throwable throwable) {
        Object[] objArr = {new Integer(finalStep), throwable};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6543, new Class[]{Integer.TYPE, Throwable.class}, Void.TYPE).isSupported) {
            a("没有发现到设备...... exception=" + throwable.toString());
            b();
            this.h.stopScan();
            q(finalStep);
        }
    }

    public void p(String data, String callbackKey) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{data, callbackKey}, this, changeQuickRedirect, false, 6532, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            try {
                a("执行一键复位ing...-1-1-1-1-1 " + data);
                if (w.R()) {
                    if (!EasyPermissions.a(this.i, "android.permission.BLUETOOTH_ADVERTISE")) {
                        a("蓝牙权限判定 - 无蓝牙广播权限" + data);
                        a.y(h.class).t("LdsBle").e("OneKeyReset").u("code", Integer.valueOf(BaseResp.ERR_MSG_SEND_FAIL)).p("开启一键复位时 无蓝牙广播权限").a().b();
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorFailResp(BaseResp.ERR_MSG_SEND_FAIL, "无蓝牙广播权限").toString()));
                        return;
                    }
                }
                a("执行一键复位ing...000000 " + data);
                if (!t(f.DISCOVERING)) {
                    a("执行一键复位ing...111111111 " + data);
                    return;
                }
                a("执行一键复位ing... " + data);
                this.f = 1;
                String optString = new JSONObject(data).optString("mac");
                this.b = optString;
                if (!TextUtils.isEmpty(optString)) {
                    this.b = this.b.replace(":", "").toLowerCase();
                }
                this.e = c();
                e eVar = new e(this.h, this.i, callbackKey);
                this.c = eVar;
                eVar.j();
                String serviceData = this.c.h(this.f, this.e, this.b, "");
                this.g.removeCallbacks(this.m);
                this.g.postDelayed(this.m, 40000);
                a("广播开始干起来.....");
                i bleConnectDeviceImproveStrategyV2 = new i();
                b bVar = this.n;
                if (bVar != null && !bVar.isDisposed()) {
                    this.n.dispose();
                }
                this.n = bleConnectDeviceImproveStrategyV2.j(this.b, "", 5, "onekeyResetDispatcher onkeyReset").c(l.c()).I(new d(this), new e(this));
                g gVar = new g("BleC075ServiceImplReset", this.i, this.b);
                this.k = gVar;
                gVar.j(System.currentTimeMillis() + "", "LdsBle", "OneKeyReset");
                BaseStepBean startStep = new BaseStepBean("startSendBroadcast", 200);
                startStep.putRequestParams("serviceData", serviceData);
                startStep.putRequestParams("cmd", 1);
                startStep.putRequestParams("startTime", startStep.getFormatTime());
                startStep.setDesc("开始一键复位广播");
                this.k.e(startStep);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public /* synthetic */ void l(BleDevice bleDevice) {
        if (!PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6541, new Class[]{BleDevice.class}, Void.TYPE).isSupported) {
            if (bleDevice.a() == null) {
                a("没有发现到待复位设备 .......  macAddress=" + this.b);
                return;
            }
            a("设备已被发现(被唤醒): " + bleDevice.i() + "  taskId=" + this.b);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rssi", bleDevice.e());
            h.a restDataUnit = com.leedarson.serviceimpl.blec075.h.k(bleDevice.f()).get((byte) 7);
            String deviceName = "";
            if (!TextUtils.isEmpty(bleDevice.d())) {
                deviceName = bleDevice.d();
            }
            o(restDataUnit, jsonObject, deviceName);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: m */
    public /* synthetic */ void n(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 6540, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            a("没有发现到设备...... exception=" + throwable.toString());
        }
    }

    public void r(String value) {
        if (!PatchProxy.proxy(new Object[]{value}, this, changeQuickRedirect, false, 6533, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.f = 1;
            this.b = value;
            this.e = c();
            e eVar = new e(this.h, this.i, "");
            this.c = eVar;
            eVar.h(1, this.e, this.b, "");
        }
    }

    public void s() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6534, new Class[0], Void.TYPE).isSupported) {
            this.c.i();
            this.h.stopScan();
        }
    }

    public int c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6535, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.d == null) {
            this.d = new AtomicInteger(1);
        }
        if (this.d.get() != 255) {
            return this.d.incrementAndGet();
        }
        this.d.set(0);
        return this.d.get();
    }

    private void a(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6536, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("BleC075ServiceImplReset");
            g2.a("一键复位： oneKeyReset: mac:" + this.b + " -> " + str, new Object[0]);
        }
    }

    private boolean t(f mode) {
        if (mode == this.j) {
            return false;
        }
        this.j = mode;
        return true;
    }

    private void q(int step) {
        Object[] objArr = {new Integer(step)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6537, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("mac", (Object) this.b);
            jsonObject2.put("step", step);
            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_NEW, "onOneKeyResetStatusChange", jsonObject2.toString()));
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6538, new Class[0], Void.TYPE).isSupported) {
            this.j = f.IDLE;
            this.b = "";
            a("mac address reset to 空");
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6539, new Class[0], Void.TYPE).isSupported) {
            d();
            e eVar = this.c;
            if (eVar != null) {
                eVar.i();
            }
            Handler handler = this.g;
            if (handler != null) {
                handler.removeCallbacks(this.m);
            }
        }
    }
}
