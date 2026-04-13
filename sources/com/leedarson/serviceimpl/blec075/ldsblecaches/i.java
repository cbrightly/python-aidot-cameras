package com.leedarson.serviceimpl.blec075.ldsblecaches;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.text.TextUtils;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.clj.fastble.callback.d;
import com.clj.fastble.data.BleDevice;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.l;
import com.leedarson.bean.Constants;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceimpl.blec075.reports.BleConnectStepBean;
import com.leedarson.serviceimpl.blec075.reports.a;
import com.leedarson.serviceimpl.blec075.strategy.j;
import com.leedarson.serviceimpl.blec075.util.BleConnectedDeviceDiscoverUtil;
import com.leedarson.serviceimpl.blec075.util.c;
import com.leedarson.serviceinterface.BleBusinessService;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: LdsConnectDeviceTaskJobManager */
public class i {
    public static ChangeQuickRedirect changeQuickRedirect;
    HashMap<String, LDSBleCacheItemBean> a = new HashMap<>();
    HashSet<String> b = new HashSet<>();
    public String c = "ldsConnectDevice";
    c d = new c();
    private io.reactivex.disposables.b e;

    static /* synthetic */ void b(i x0, String x1) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6508, clsArr, Void.TYPE).isSupported) {
            x0.a(x1);
        }
    }

    static /* synthetic */ void c(i x0, BluetoothGatt x1) {
        Class[] clsArr = {i.class, BluetoothGatt.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6509, clsArr, Void.TYPE).isSupported) {
            x0.G(x1);
        }
    }

    static /* synthetic */ void d(i x0, String x1, int x2, String x3) {
        Class<String> cls = String.class;
        Object[] objArr = {x0, x1, new Integer(x2), x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6510, new Class[]{i.class, cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            x0.E(x1, x2, x3);
        }
    }

    static /* synthetic */ void e(i x0, String x1, h x2, int x3) {
        Object[] objArr = {x0, x1, x2, new Integer(x3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6511, new Class[]{i.class, String.class, h.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.F(x1, x2, x3);
        }
    }

    public void f(h hVar, BleC075Service.CommonBleConnectCallback commonBleConnectCallback, String str) {
        String str2;
        LDSBleCacheItemBean cacheItemBean;
        int _tempRetryMaxCount = 2;
        if (!PatchProxy.proxy(new Object[]{hVar, commonBleConnectCallback, str}, this, changeQuickRedirect, false, 6484, new Class[]{h.class, BleC075Service.CommonBleConnectCallback.class, String.class}, Void.TYPE).isSupported) {
            BleC075Service.CommonBleConnectCallback _callbackProxy = commonBleConnectCallback;
            h taskItem = hVar;
            String ref = str;
            LDSBleCacheItemBean cacheItemBean2 = this.a.get(taskItem.d);
            this.b.remove(taskItem.d);
            String str3 = taskItem.d;
            LdsRequestConnectConfigBean ldsRequestConnectConfigBean = taskItem.c;
            String str4 = ldsRequestConnectConfigBean.modelId;
            if (TextUtils.isEmpty(ldsRequestConnectConfigBean.traceId)) {
                str2 = "" + System.currentTimeMillis();
            } else {
                str2 = taskItem.c.traceId;
            }
            com.leedarson.serviceimpl.blec075.reports.a _reporter = com.leedarson.serviceimpl.blec075.reports.a.c(str3, str4, str2);
            a("收到添加设备请求 taskItem=" + taskItem.d + " , ref=" + ref);
            LdsRequestConnectConfigBean ldsRequestConnectConfigBean2 = taskItem.c;
            _reporter.i = ldsRequestConnectConfigBean2.autoConnect;
            _reporter.j(ldsRequestConnectConfigBean2.checkPairingStatus ? 1 : 0);
            _reporter.b(_callbackProxy == null ? a.C0127a.BLE_BLE : a.C0127a.BLE_BLE_BUSINESS);
            BleConnectStepBean checkCache = BleConnectStepBean.create("检测蓝缓存状态");
            if (cacheItemBean2 != null) {
                cacheItemBean2._taskItemDesc = taskItem;
                cacheItemBean2._callbackProxy = _callbackProxy;
                int i = cacheItemBean2.CONNECT_STATUE;
                if (i == 2) {
                    cacheItemBean2.createTimeSpan = System.currentTimeMillis();
                    cacheItemBean2.endTimeSpan = System.currentTimeMillis();
                    F("设备已连接成功", taskItem, 200);
                    a("发现设备已连接成功 =" + taskItem.d);
                    BleC075Service.CommonBleConnectCallback commonBleConnectCallback2 = cacheItemBean2._callbackProxy;
                    if (commonBleConnectCallback2 != null) {
                        commonBleConnectCallback2.onConnectSuccess(cacheItemBean2.bzMac, cacheItemBean2._bleGattConnection, 200);
                    }
                    checkCache.putResponseData("desc", "设备已连接成功", 200);
                    _reporter.a(checkCache);
                    _reporter.e();
                    return;
                } else if (i == 1) {
                    a("设备正在连接中,不需要重复调用" + taskItem.d);
                    cacheItemBean2._callbackProxy = _callbackProxy;
                    _reporter.a(checkCache);
                    _reporter.e();
                    checkCache.putResponseData("desc", "设备正在连接中", 3);
                    return;
                } else {
                    cacheItemBean = cacheItemBean2;
                }
            } else {
                LDSBleCacheItemBean cacheItemBean3 = new LDSBleCacheItemBean();
                cacheItemBean3.bzMac = taskItem.d;
                cacheItemBean3._taskItemDesc = taskItem;
                cacheItemBean3._callbackProxy = _callbackProxy;
                a("准备新的接口请求参数");
                checkCache.putResponseData("desc", "缓存检测完毕 全新连接-开始准备连接引擎", 200);
                _reporter.a(checkCache);
                cacheItemBean = cacheItemBean3;
            }
            this.a.put(taskItem.d, cacheItemBean);
            cacheItemBean.createTimeSpan = System.currentTimeMillis();
            cacheItemBean.retryTimes = 0;
            a("启动自动连接任务....");
            BleConnectStepBean _searchStep = BleConnectStepBean.create("蓝牙连接引擎-启动扫描");
            _searchStep.putRequestData("taskId", taskItem.d);
            H();
            cacheItemBean.CONNECT_STATUE = 1;
            com.leedarson.serviceimpl.blec075.strategy.i bleConnectDeviceImproveStrategyV2 = new com.leedarson.serviceimpl.blec075.strategy.i();
            a("搜索设备任务开启....taskId=" + taskItem.d);
            if (!taskItem.c.autoConnect) {
                _tempRetryMaxCount = 5;
            }
            String str5 = taskItem.d;
            LDSBleCacheItemBean lDSBleCacheItemBean = cacheItemBean;
            c cVar = r2;
            BleC075Service.CommonBleConnectCallback commonBleConnectCallback3 = _callbackProxy;
            c cVar2 = new c(this, taskItem, _searchStep, _reporter, _tempRetryMaxCount);
            bleConnectDeviceImproveStrategyV2.k(bleConnectDeviceImproveStrategyV2.j(str5, str5, 8, "LDSConnectDeviceTaskJobManager addConnectTask").o(new b(_tempRetryMaxCount, taskItem)).c(l.c()).I(cVar, new f(this, _searchStep, _reporter, taskItem)));
        }
    }

    static /* synthetic */ e r(int _tempRetryMaxCount, h taskItem, BleDevice bleDevice) {
        Object[] objArr = {new Integer(_tempRetryMaxCount), taskItem, bleDevice};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 6507, new Class[]{Integer.TYPE, h.class, BleDevice.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        String str = taskItem.d;
        return new com.leedarson.serviceimpl.blec075.strategy.l(_tempRetryMaxCount, WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS, str, str).b(bleDevice);
    }

    /* access modifiers changed from: private */
    /* renamed from: s */
    public /* synthetic */ void t(h hVar, BleConnectStepBean bleConnectStepBean, com.leedarson.serviceimpl.blec075.reports.a aVar, int i, BleDevice bleDevice) {
        if (!PatchProxy.proxy(new Object[]{hVar, bleConnectStepBean, aVar, new Integer(i), bleDevice}, this, changeQuickRedirect, false, 6506, new Class[]{h.class, BleConnectStepBean.class, com.leedarson.serviceimpl.blec075.reports.a.class, Integer.TYPE, BleDevice.class}, Void.TYPE).isSupported) {
            BleConnectStepBean _searchStep = bleConnectStepBean;
            int _tempRetryMaxCount = i;
            h taskItem = hVar;
            com.leedarson.serviceimpl.blec075.reports.a _reporter = aVar;
            BleDevice bleDevice2 = bleDevice;
            if (bleDevice2.a() == null) {
                a("搜索任务反馈：10S内未发现到有该设备的广播  taskId=" + taskItem.d);
                if (!taskItem.c.autoConnect) {
                    _searchStep.putResponseData("desc", "蓝牙扫描引擎-设备扫描搜索超时(搜索设备超时-10S内未返回)", 402);
                    _searchStep.desc = "蓝牙扫描引擎-设备扫描搜索超时(搜索设备超时-10S内未返回)";
                    _reporter.a(_searchStep);
                    _reporter.e();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", (int) BaseResp.ERR_DISCONNECT_FAIL);
                    jsonObject.put("desc", (Object) "connect fail:  " + "蓝牙扫描引擎-设备扫描搜索超时(搜索设备超时-10S内未返回)");
                    JSONObject _dataResponse = new JSONObject();
                    _dataResponse.put("desc", (Object) "设备广播无法搜索到");
                    jsonObject.put("data", (Object) _dataResponse);
                    a("搜索任务结束，通知业务层本次连接设备失败");
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(taskItem.b, jsonObject.toString()));
                    if (!(this.a.get(taskItem.d) == null || this.a.get(taskItem.d)._callbackProxy == null)) {
                        this.a.get(taskItem.d)._callbackProxy.onConnectFail(0, taskItem.d, 402, "蓝牙扫描引擎-设备扫描搜索超时(搜索设备超时-10S内未返回)");
                    }
                }
                this.a.get(taskItem.d).CONNECT_STATUE = 4;
                this.a.get(taskItem.d).endTimeSpan = System.currentTimeMillis();
                return;
            }
            _reporter.m = bleDevice2.z;
            if (!taskItem.c.autoConnect) {
                a("BleBusiness.auto.BleC075ServiceImpl.connectDevice   2222 reset mac=");
                ((BleBusinessService) com.alibaba.android.arouter.launcher.a.c().g(BleBusinessService.class)).reset(taskItem.d);
            }
            _searchStep.putResponseData("desc", "蓝牙扫描引擎-成功扫描到设备", 200);
            _reporter.a(_searchStep);
            LDSBleCacheItemBean tempLDSCache = this.a.get(taskItem.d);
            tempLDSCache.bleDevice = bleDevice2;
            tempLDSCache.realDeviceMac = bleDevice2.c();
            _reporter.i(bleDevice2.e());
            timber.log.a.g(this.c).m("扫描到设备，scanRecordHex:" + tempLDSCache.bleDevice.i() + ",检测设备入网状态", new Object[0]);
            if (this.d.d(tempLDSCache.bleDevice.i(), taskItem.d) || !o(tempLDSCache)) {
                a("搜索任务：已搜索到设备--》准备连接ing....");
                BleConnectStepBean stepOfConnect = BleConnectStepBean.create("连接设备");
                stepOfConnect.putRequestData("taskId", taskItem.d);
                _reporter.a(stepOfConnect);
                if (this.a.get(taskItem.d).CONNECT_STATUE != 2) {
                    a("连接设备：二次确认设备未被连接，可开启你的连接");
                    tempLDSCache.CONNECT_STATUE = 1;
                    a("连接任务：启动 taskId=" + taskItem.d + " 尝试次数设定：retryMaxCount=" + _tempRetryMaxCount);
                    StringBuilder sb = new StringBuilder();
                    sb.append(_tempRetryMaxCount);
                    sb.append("");
                    stepOfConnect.putRequestData("retryTimes", sb.toString());
                    _reporter.j = _tempRetryMaxCount;
                    a aVar2 = new a(_tempRetryMaxCount, taskItem.d, tempLDSCache, taskItem, stepOfConnect, _reporter);
                    aVar2.v(taskItem.c.autoConnect ? 10000 : 60000);
                    aVar2.h(bleDevice2);
                    return;
                }
                a("连接设备：二次确认设备未被连接(设备已连接)");
                stepOfConnect.putResponseData("desc", "蓝牙扫描引擎-设备扫描搜索到 - 设备连接状态 - 设备已连接上 ", 200);
                _reporter.a(_searchStep);
                _reporter.e();
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(taskItem.b, "{\"code\":200}"));
                if (this.a.get(taskItem.d) != null && this.a.get(taskItem.d)._callbackProxy != null) {
                    this.a.get(taskItem.d)._callbackProxy.onConnectSuccess(taskItem.d, tempLDSCache._bleGattConnection, 200);
                    return;
                }
                return;
            }
            String tip = "自动连接任务 - 搜索任务 - 分析设备已被重置 - 设备已被重置 广播码：" + tempLDSCache.bleDevice.i();
            _searchStep.putResponseData("desc", tip, 1011);
            _reporter.e();
            a(tip);
            if (!(this.a.get(taskItem.d) == null || this.a.get(taskItem.d)._callbackProxy == null)) {
                timber.log.a.g(this.c).m("扫描到设备,未入网状态，返回411给web1, scanRecordHex:" + tempLDSCache.bleDevice.i(), new Object[0]);
                this.a.get(taskItem.d)._callbackProxy.onConnectFail(411, taskItem.d, 1011, tip);
            }
            F(tip, tempLDSCache._taskItemDesc, 411);
            this.a.remove(taskItem.d);
        }
    }

    /* compiled from: LdsConnectDeviceTaskJobManager */
    public class a extends j {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ LDSBleCacheItemBean m;
        final /* synthetic */ h n;
        final /* synthetic */ BleConnectStepBean o;
        final /* synthetic */ com.leedarson.serviceimpl.blec075.reports.a p;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(int maxRetroCount, String taskId, LDSBleCacheItemBean lDSBleCacheItemBean, h hVar, BleConnectStepBean bleConnectStepBean, com.leedarson.serviceimpl.blec075.reports.a aVar) {
            super(maxRetroCount, taskId);
            this.m = lDSBleCacheItemBean;
            this.n = hVar;
            this.o = bleConnectStepBean;
            this.p = aVar;
        }

        public void s(BleDevice bleDevice) {
            if (!PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6512, new Class[]{BleDevice.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(i.this.c);
                g.h("BLE.TRV onStartConnect:" + bleDevice.c(), new Object[0]);
                this.m.CONNECT_STATUE = 1;
            }
        }

        public void q(BleDevice bleDevice, BluetoothGatt gatt, int i) {
            Object[] objArr = {bleDevice, gatt, new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6513, new Class[]{BleDevice.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                this.o.putResponseData("desc", "连接任务: 连接成功 taskId=" + this.n.d, 200);
                i iVar = i.this;
                i.b(iVar, "连接任务: 连接成功 taskId=" + this.n.d + "   gatt=" + gatt.toString());
                LDSBleCacheItemBean lDSBleCacheItemBean = this.m;
                lDSBleCacheItemBean.CONNECT_STATUE = 2;
                lDSBleCacheItemBean._bleGattConnection = gatt;
                lDSBleCacheItemBean.endTimeSpan = System.currentTimeMillis();
                i.b(i.this, "连接任务：设置MTU ing...  ");
                BleConnectStepBean setMtuStep = BleConnectStepBean.create("setUpMtu");
                setMtuStep.putRequestData("mtu", "256");
                this.p.a(setMtuStep);
                com.clj.fastble.a.o().H(bleDevice, 256, new C0126a(setMtuStep, bleDevice));
            }
        }

        /* renamed from: com.leedarson.serviceimpl.blec075.ldsblecaches.i$a$a  reason: collision with other inner class name */
        /* compiled from: LdsConnectDeviceTaskJobManager */
        public class C0126a extends d {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ BleConnectStepBean a;
            final /* synthetic */ BleDevice b;

            C0126a(BleConnectStepBean bleConnectStepBean, BleDevice bleDevice) {
                this.a = bleConnectStepBean;
                this.b = bleDevice;
            }

            public void onSetMTUFailure(com.clj.fastble.exception.a exception) {
                if (!PatchProxy.proxy(new Object[]{exception}, this, changeQuickRedirect, false, 6518, new Class[]{com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
                    i iVar = i.this;
                    i.b(iVar, "连接任务： 设置MTU 失败  exception:" + exception.toString());
                    this.a.putResponseData("exception", exception.toString(), 10012);
                    a.this.p.e();
                    a.b g = timber.log.a.g(i.this.c);
                    g.c("BLE.TRV onSetMTUFailure=" + exception.getDescription() + ",mac:" + this.b.c(), new Object[0]);
                    a aVar = a.this;
                    if (i.this.a.get(aVar.n.d) != null) {
                        a aVar2 = a.this;
                        if (i.this.a.get(aVar2.n.d)._callbackProxy != null) {
                            a aVar3 = a.this;
                            BleC075Service.CommonBleConnectCallback commonBleConnectCallback = i.this.a.get(aVar3.n.d)._callbackProxy;
                            a aVar4 = a.this;
                            commonBleConnectCallback.onConnectSuccess(aVar4.n.d, aVar4.m._bleGattConnection, 200);
                        }
                    }
                    a aVar5 = a.this;
                    i.e(i.this, "设备连接成功", aVar5.n, 200);
                    a aVar6 = a.this;
                    i.d(i.this, aVar6.n.d, 1, "设备连接成功");
                }
            }

            public void onMtuChanged(int mtu) {
                if (!PatchProxy.proxy(new Object[]{new Integer(mtu)}, this, changeQuickRedirect, false, 6519, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                    i iVar = i.this;
                    i.b(iVar, "任务任务：设置MTU 成功 协商后的MTU=" + mtu);
                    BleConnectStepBean bleConnectStepBean = this.a;
                    bleConnectStepBean.putResponseData("mtu", "mtu set up success=" + mtu, 200);
                    a.this.p.e();
                    a.b g = timber.log.a.g(i.this.c);
                    g.h("BLE.TRV onMtuChanged=" + mtu + ",mac:" + this.b.c(), new Object[0]);
                    a aVar = a.this;
                    if (i.this.a.get(aVar.n.d) != null) {
                        a aVar2 = a.this;
                        if (i.this.a.get(aVar2.n.d)._callbackProxy != null) {
                            a aVar3 = a.this;
                            BleC075Service.CommonBleConnectCallback commonBleConnectCallback = i.this.a.get(aVar3.n.d)._callbackProxy;
                            a aVar4 = a.this;
                            commonBleConnectCallback.onConnectSuccess(aVar4.n.d, aVar4.m._bleGattConnection, 200);
                        }
                    }
                    a aVar5 = a.this;
                    i.e(i.this, "设备连接成功", aVar5.n, 200);
                    a aVar6 = a.this;
                    i.d(i.this, aVar6.n.d, 1, "设备连接成功");
                }
            }
        }

        public void r(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
            if (!PatchProxy.proxy(new Object[]{new Byte(isActiveDisConnected ? (byte) 1 : 0), bleDevice, gatt, new Integer(status)}, this, changeQuickRedirect, false, 6514, new Class[]{Boolean.TYPE, BleDevice.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                String tips = "连接任务：Ble通道断开  isActiveDisconnected=" + isActiveDisConnected + "  taskId=" + this.n.d + "  gatt=" + gatt.toString() + " status=" + status;
                this.o.putResponseData("desc", tips, 1003);
                this.o.desc = tips;
                this.p.e();
                ((BleBusinessService) com.alibaba.android.arouter.launcher.a.c().g(BleBusinessService.class)).onClientConnectDisConnect(this.n.d);
                i.b(i.this, tips);
                LDSBleCacheItemBean lDSBleCacheItemBean = this.m;
                lDSBleCacheItemBean.CONNECT_STATUE = 4;
                lDSBleCacheItemBean.endTimeSpan = System.currentTimeMillis();
                BluetoothGatt bluetoothGatt = this.m._bleGattConnection;
                if (!(bluetoothGatt == null || bluetoothGatt == gatt)) {
                    i.c(i.this, bluetoothGatt);
                }
                this.m._bleGattConnection = gatt;
                i.d(i.this, this.n.d, 0, "设备连接断开(设备离线)");
                if (i.this.a.get(this.n.d) != null && i.this.a.get(this.n.d)._callbackProxy != null) {
                    i.this.a.get(this.n.d)._callbackProxy.onDisConnected(this.n.d, this.m._bleGattConnection, 1003);
                }
            }
        }

        public void o(BleDevice bleDevice, com.clj.fastble.exception.a exception) {
            if (!PatchProxy.proxy(new Object[]{bleDevice, exception}, this, changeQuickRedirect, false, 6515, new Class[]{BleDevice.class, com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
                String tips = "连接任务： 连接失败  bleDevice=" + bleDevice.toString() + "  exception=" + exception.toString() + bleDevice.e();
                this.o.putResponseData("desc", tips, 1004);
                this.p.e();
                i.b(i.this, tips);
                LDSBleCacheItemBean lDSBleCacheItemBean = this.m;
                lDSBleCacheItemBean.CONNECT_STATUE = 4;
                lDSBleCacheItemBean.endTimeSpan = System.currentTimeMillis();
                BleConnectedDeviceDiscoverUtil.BlutoothConnectedDeviceListBean a = BleConnectedDeviceDiscoverUtil.a(BaseApplication.b());
                String errorTipsInfo = bleDevice.c() + ",rssi=" + bleDevice.e() + ",ex:" + exception.toString() + "且达到重连次数上限deviceMac =" + this.n.d;
                i.e(i.this, errorTipsInfo, this.n, -2);
                i.d(i.this, this.n.d, 0, "设备连接失败（设备离线）TIPS=" + errorTipsInfo);
                if (i.this.a.get(this.n.d) != null && i.this.a.get(this.n.d)._callbackProxy != null) {
                    i.this.a.get(this.n.d)._callbackProxy.onConnectFail(0, this.n.d, 1004, tips);
                }
            }
        }

        public void p(int i, String desc) {
            Object[] objArr = {new Integer(i), desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6516, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                BleConnectStepBean bleConnectState = BleConnectStepBean.create(desc);
                bleConnectState.putRequestData("desc", desc);
                bleConnectState.putResponseData("result", desc, 200);
                this.p.a(bleConnectState);
            }
        }

        public void n(BleDevice bleDevice) {
            if (!PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6517, new Class[]{BleDevice.class}, Void.TYPE).isSupported) {
                if (bleDevice != null && bleDevice.a() != null) {
                    LDSBleCacheItemBean lDSBleCacheItemBean = this.m;
                    lDSBleCacheItemBean.bleDevice = bleDevice;
                    lDSBleCacheItemBean.realDeviceMac = bleDevice.c();
                    this.p.i(bleDevice.e());
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: u */
    public /* synthetic */ void v(BleConnectStepBean _searchStep, com.leedarson.serviceimpl.blec075.reports.a _reporter, h taskItem, Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{_searchStep, _reporter, taskItem, throwable}, this, changeQuickRedirect, false, 6505, new Class[]{BleConnectStepBean.class, com.leedarson.serviceimpl.blec075.reports.a.class, h.class, Throwable.class}, Void.TYPE).isSupported) {
            String tip = "蓝牙扫描引擎-设备扫描搜索超时(搜索设备超时10S内未返回) exception:" + throwable.toString();
            a(tip);
            _searchStep.putResponseData("desc", tip, 402);
            _searchStep.desc = "" + tip;
            _reporter.a(_searchStep);
            _reporter.e();
            F(tip, taskItem, 1001);
            if (this.a.get(taskItem.d) != null && this.a.get(taskItem.d)._callbackProxy != null) {
                this.a.get(taskItem.d)._callbackProxy.onConnectFail(0, taskItem.d, 402, tip);
            }
        }
    }

    private void j() {
        a.C0127a aVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6485, new Class[0], Void.TYPE).isSupported) {
            for (Map.Entry<String, LDSBleCacheItemBean> itemBeanEntry : this.a.entrySet()) {
                LDSBleCacheItemBean itemBean = itemBeanEntry.getValue();
                if (this.b.contains(itemBean.bzMac)) {
                    BluetoothGatt bluetoothGatt = itemBean._bleGattConnection;
                    if (bluetoothGatt != null) {
                        G(bluetoothGatt);
                    }
                    a("自动连接任务-任务识别：当前任务已被移除 taskId=" + itemBean.bzMac);
                } else {
                    LdsRequestConnectConfigBean ldsRequestConnectConfigBean = itemBean._taskItemDesc.c;
                    if (!ldsRequestConnectConfigBean.autoConnect) {
                        a("自动连接任务-任务识别：当前任务非自动重连的任务 taskId=" + itemBean.bzMac);
                    } else {
                        int i = itemBean.CONNECT_STATUE;
                        if (i == 2) {
                            a("自动连接任务-任务识别：当前任务已连接成功 taskId=" + itemBean.bzMac);
                        } else if (i == 1) {
                            a("自动连接任务-任务识别：当前任务正在连接中 taskId=" + itemBean.bzMac);
                        } else {
                            String str = itemBean.bzMac;
                            String str2 = ldsRequestConnectConfigBean.modelId;
                            com.leedarson.serviceimpl.blec075.reports.a _reporter = com.leedarson.serviceimpl.blec075.reports.a.c(str, str2, System.currentTimeMillis() + "");
                            itemBean.CONNECT_STATUE = 1;
                            com.leedarson.serviceimpl.blec075.strategy.i bleConnectDeviceImproveStrategyV2 = new com.leedarson.serviceimpl.blec075.strategy.i();
                            a("自动连接任务 - 搜索任务启动ing");
                            BleConnectStepBean _scanStepBean = BleConnectStepBean.create("自动连接-设备扫描");
                            _scanStepBean.putRequestData("taskId", itemBean.bzMac);
                            _reporter.a(_scanStepBean);
                            _reporter.i = true;
                            _reporter.j(o(itemBean) ? 1 : 0);
                            if (itemBean._callbackProxy == null) {
                                aVar = a.C0127a.BLE_BLE;
                            } else {
                                aVar = a.C0127a.BLE_BLE_BUSINESS;
                            }
                            _reporter.b(aVar);
                            bleConnectDeviceImproveStrategyV2.k(bleConnectDeviceImproveStrategyV2.j(itemBean.bzMac, "", 10, "LDSConnectDeviceTaskJobManager forEachItemToConnect").A(l.d).I(new g(this, itemBean, _reporter, _scanStepBean), new d(this, _scanStepBean, _reporter, itemBean)));
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: w */
    public /* synthetic */ void x(LDSBleCacheItemBean itemBean, com.leedarson.serviceimpl.blec075.reports.a _reporter, BleConnectStepBean _scanStepBean, BleDevice bleDevice) {
        if (!PatchProxy.proxy(new Object[]{itemBean, _reporter, _scanStepBean, bleDevice}, this, changeQuickRedirect, false, 6504, new Class[]{LDSBleCacheItemBean.class, com.leedarson.serviceimpl.blec075.reports.a.class, BleConnectStepBean.class, BleDevice.class}, Void.TYPE).isSupported) {
            itemBean.endTimeSpan = System.currentTimeMillis();
            if (bleDevice.a() == null) {
                a("自动连接任务 - 搜索任务启动 - 未发现到指定设备  taskId=" + itemBean.bzMac);
                BleDevice bleDevice2 = itemBean.bleDevice;
                if (bleDevice2 == null || bleDevice2.a() == null) {
                    itemBean.CONNECT_STATUE = 4;
                    E(itemBean.bzMac, 0, "自动巡检时发现设备找不到");
                    String tip = "自动连接任务 - 搜索任务启动 - 设备没有找到（搜索不到设备的广播，本地也没有设备记录）  taskId=" + itemBean.bzMac;
                    a(tip);
                    if (this.a.get(itemBean.bzMac) != null && this.a.get(itemBean.bzMac)._callbackProxy != null) {
                        this.a.get(itemBean.bzMac)._callbackProxy.onConnectFail(0, itemBean.bzMac, 402, tip);
                    }
                } else if (BleConnectedDeviceDiscoverUtil.a(BaseApplication.b()).checkDeviceIsConnected(itemBean.bleDevice.a().getAddress()) != null) {
                    itemBean.CONNECT_STATUE = 2;
                    E(itemBean.bzMac, 1, "自动巡检时发现设备已上线");
                    a("自动连接任务 - 搜索任务启动 - 因为设备已被连接（所以未发现设备）  taskId=" + itemBean.bzMac + " 设备已经上线");
                    if (this.a.get(itemBean.bzMac) != null && this.a.get(itemBean.bzMac)._callbackProxy != null) {
                        this.a.get(itemBean.bzMac)._callbackProxy.onConnectSuccess(itemBean.bzMac, itemBean._bleGattConnection, 200);
                    }
                } else {
                    itemBean.CONNECT_STATUE = 4;
                    G(itemBean._bleGattConnection);
                    E(itemBean.bzMac, 0, "自动巡检时发现设备找不到");
                    String tip2 = "自动连接任务 - 搜索任务启动 - 设备有找到（可能原因是搜索不到设备的广播）  taskId=" + itemBean.bzMac;
                    a(tip2);
                    if (this.a.get(itemBean.bzMac) != null && this.a.get(itemBean.bzMac)._callbackProxy != null) {
                        this.a.get(itemBean.bzMac)._callbackProxy.onConnectFail(0, itemBean.bzMac, 402, tip2);
                    }
                }
            } else {
                _reporter.m = bleDevice.z;
                itemBean.bleDevice = bleDevice;
                itemBean.realDeviceMac = bleDevice.c();
                _reporter.i(bleDevice.e());
                a("自动连接任务 - 搜索任务 - 分析设备已被重置");
                boolean isNeedCheckPairStatue = o(itemBean);
                if (this.d.d(itemBean.bleDevice.i(), itemBean.bzMac) || !isNeedCheckPairStatue) {
                    a("自动连接任务 - 搜索任务 - 连接任务 - 启动连接....");
                    BleConnectStepBean _connectStep = BleConnectStepBean.create("蓝牙开始连接");
                    _connectStep.putRequestData("taskId", itemBean.bzMac);
                    _reporter.a(_connectStep);
                    com.clj.fastble.a.o().c(itemBean.bleDevice, new b(itemBean, _connectStep, _reporter));
                    return;
                }
                String tip3 = "自动连接任务 - 搜索任务 - 分析设备已被重置 当前设备已被重置-不需再重新连接了 adHex=" + itemBean.bleDevice.i();
                _scanStepBean.putResponseData("desc", tip3, 1005);
                _reporter.e();
                E(itemBean.bzMac, 0, tip3);
                a(tip3);
                if (!(this.a.get(itemBean.bzMac) == null || this.a.get(itemBean.bzMac)._callbackProxy == null)) {
                    this.a.get(itemBean.bzMac)._callbackProxy.onConnectFail(0, itemBean.bzMac, 1011, tip3);
                }
                timber.log.a.g("multiclient").m("BleBusiness.auto.BleC075ServiceImpl.connectDevice   2222 reset mac=", new Object[0]);
                ((BleBusinessService) com.alibaba.android.arouter.launcher.a.c().g(BleBusinessService.class)).onClientConnectDisConnect(itemBean.bzMac);
                this.a.remove(itemBean.bzMac);
            }
        }
    }

    /* compiled from: LdsConnectDeviceTaskJobManager */
    public class b extends com.clj.fastble.callback.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ LDSBleCacheItemBean a;
        final /* synthetic */ BleConnectStepBean b;
        final /* synthetic */ com.leedarson.serviceimpl.blec075.reports.a c;

        b(LDSBleCacheItemBean lDSBleCacheItemBean, BleConnectStepBean bleConnectStepBean, com.leedarson.serviceimpl.blec075.reports.a aVar) {
            this.a = lDSBleCacheItemBean;
            this.b = bleConnectStepBean;
            this.c = aVar;
        }

        public void e() {
            this.a.CONNECT_STATUE = 1;
        }

        public void b(BleDevice bleDevice, com.clj.fastble.exception.a exception) {
            if (!PatchProxy.proxy(new Object[]{bleDevice, exception}, this, changeQuickRedirect, false, 6520, new Class[]{BleDevice.class, com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
                String tips = "自动连接任务 - 搜索任务 - 连接任务 - 连接异常 exception=" + exception.toString() + "   rssi=" + bleDevice.e();
                this.b.putResponseData("desc", tips, 1004);
                this.c.e();
                i.b(i.this, tips);
                LDSBleCacheItemBean lDSBleCacheItemBean = this.a;
                lDSBleCacheItemBean.CONNECT_STATUE = 4;
                lDSBleCacheItemBean.endTimeSpan = System.currentTimeMillis();
                BluetoothGatt bluetoothGatt = this.a._bleGattConnection;
                if (bluetoothGatt != null) {
                    i.c(i.this, bluetoothGatt);
                }
                i.d(i.this, this.a.bzMac, 0, "自动巡检连接设备失败 exception=" + exception.toString());
                if (i.this.a.get(this.a.bzMac) != null && i.this.a.get(this.a.bzMac)._callbackProxy != null) {
                    i.this.a.get(this.a.bzMac)._callbackProxy.onConnectFail(0, this.a.bzMac, 1004, tips);
                }
            }
        }

        public void c(BleDevice bleDevice, BluetoothGatt gatt, int status) {
            if (!PatchProxy.proxy(new Object[]{bleDevice, gatt, new Integer(status)}, this, changeQuickRedirect, false, 6521, new Class[]{BleDevice.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                String tips = "自动连接任务 - 搜索任务 - 连接任务 - 连接成功 status=" + status;
                i.b(i.this, tips);
                LDSBleCacheItemBean lDSBleCacheItemBean = this.a;
                lDSBleCacheItemBean.CONNECT_STATUE = 2;
                lDSBleCacheItemBean.endTimeSpan = System.currentTimeMillis();
                BluetoothGatt bluetoothGatt = this.a._bleGattConnection;
                if (!(bluetoothGatt == null || bluetoothGatt == gatt)) {
                    i.c(i.this, bluetoothGatt);
                }
                this.a._bleGattConnection = gatt;
                i.b(i.this, "自动连接任务 - 搜索任务 - 连接任务 - 连接成功 分析是否是有效连接  gatt=" + gatt.toString());
                if (i.this.a.get(this.a.bzMac) == null) {
                    i.b(i.this, "自动重边任务 - 搜索任务 - 连接任务 - 连接成功 - 连接已失效 -强制关闭");
                    this.b.putResponseData("desc", tips + " " + "自动重边任务 - 搜索任务 - 连接任务 - 连接成功 - 连接已失效 -强制关闭", 1006);
                    this.c.e();
                    i.c(i.this, this.a._bleGattConnection);
                    return;
                }
                i.b(i.this, "自动连接任务 - 搜索任务 - 连接任务 - 连接成功 - 设置mtu");
                this.b.putResponseData("desc", "自动连接任务 - 搜索任务 - 连接任务 - 连接成功 - 设置mtu", 200);
                this.c.e();
                com.clj.fastble.a.o().H(this.a.bleDevice, 512, new a());
            }
        }

        /* compiled from: LdsConnectDeviceTaskJobManager */
        public class a extends d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void onSetMTUFailure(com.clj.fastble.exception.a exception) {
                if (!PatchProxy.proxy(new Object[]{exception}, this, changeQuickRedirect, false, 6524, new Class[]{com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
                    i iVar = i.this;
                    i.b(iVar, "自动连接任务 - 搜索任务 - 连接任务 - 连接成功 - 设置mtu - 异常" + exception.toString());
                    b bVar = b.this;
                    if (i.this.a.get(bVar.a.bzMac) != null) {
                        b bVar2 = b.this;
                        if (i.this.a.get(bVar2.a.bzMac)._callbackProxy != null) {
                            b bVar3 = b.this;
                            BleC075Service.CommonBleConnectCallback commonBleConnectCallback = i.this.a.get(bVar3.a.bzMac)._callbackProxy;
                            LDSBleCacheItemBean lDSBleCacheItemBean = b.this.a;
                            commonBleConnectCallback.onConnectSuccess(lDSBleCacheItemBean.bzMac, lDSBleCacheItemBean._bleGattConnection, 200);
                        }
                    }
                    b bVar4 = b.this;
                    i.d(i.this, bVar4.a.bzMac, 1, "自动巡检设备连接成功 ");
                }
            }

            public void onMtuChanged(int mtu) {
                Object[] objArr = {new Integer(mtu)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6525, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                    i iVar = i.this;
                    i.b(iVar, "自动连接任务 - 搜索任务 - 连接任务 - 连接成功 - 设置mtu - 成功" + mtu);
                    b bVar = b.this;
                    if (i.this.a.get(bVar.a.bzMac) != null) {
                        b bVar2 = b.this;
                        if (i.this.a.get(bVar2.a.bzMac)._callbackProxy != null) {
                            b bVar3 = b.this;
                            BleC075Service.CommonBleConnectCallback commonBleConnectCallback = i.this.a.get(bVar3.a.bzMac)._callbackProxy;
                            LDSBleCacheItemBean lDSBleCacheItemBean = b.this.a;
                            commonBleConnectCallback.onConnectSuccess(lDSBleCacheItemBean.bzMac, lDSBleCacheItemBean._bleGattConnection, 200);
                        }
                    }
                    b bVar4 = b.this;
                    i.d(i.this, bVar4.a.bzMac, 1, "自动巡检设备连接成功 ");
                }
            }
        }

        public void d(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
            if (!PatchProxy.proxy(new Object[]{new Byte(isActiveDisConnected ? (byte) 1 : 0), device, gatt, new Integer(status)}, this, changeQuickRedirect, false, 6522, new Class[]{Boolean.TYPE, BleDevice.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                String onDisConnectedTip = "自动连接任务 发现设备被断开: isActiveDisconnected:" + isActiveDisConnected + "  bleDevice:" + device + "  gatt:" + gatt.toString() + "  status:" + status;
                this.b.putResponseData("desc", onDisConnectedTip, 1003);
                this.c.e();
                i.b(i.this, onDisConnectedTip);
                LDSBleCacheItemBean lDSBleCacheItemBean = this.a;
                lDSBleCacheItemBean.CONNECT_STATUE = 4;
                lDSBleCacheItemBean.endTimeSpan = System.currentTimeMillis();
                BluetoothGatt bluetoothGatt = this.a._bleGattConnection;
                if (!(bluetoothGatt == null || bluetoothGatt == gatt)) {
                    i.c(i.this, bluetoothGatt);
                }
                LDSBleCacheItemBean lDSBleCacheItemBean2 = this.a;
                lDSBleCacheItemBean2._bleGattConnection = gatt;
                i.d(i.this, lDSBleCacheItemBean2.bzMac, 0, "自动连接任务 自动巡检发现设备被断开 status=" + status);
                timber.log.a.g("multiclient").m("BleBusiness.auto.BleC075ServiceImpl.connectDevice   2222 reset mac=", new Object[0]);
                ((BleBusinessService) com.alibaba.android.arouter.launcher.a.c().g(BleBusinessService.class)).onClientConnectDisConnect(this.a.bzMac);
                if (i.this.a.get(this.a.bzMac) != null && i.this.a.get(this.a.bzMac)._callbackProxy != null) {
                    BleC075Service.CommonBleConnectCallback commonBleConnectCallback = i.this.a.get(this.a.bzMac)._callbackProxy;
                    LDSBleCacheItemBean lDSBleCacheItemBean3 = this.a;
                    commonBleConnectCallback.onDisConnected(lDSBleCacheItemBean3.bzMac, lDSBleCacheItemBean3._bleGattConnection, 1003);
                }
            }
        }

        public void a(int code, String desc) {
            Object[] objArr = {new Integer(code), desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6523, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                if (-1200 != code) {
                    BleConnectStepBean bleConnectState = BleConnectStepBean.create(desc);
                    bleConnectState.putRequestData("desc", desc);
                    bleConnectState.putResponseData("result", desc, 200);
                    this.c.a(bleConnectState);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: y */
    public /* synthetic */ void z(BleConnectStepBean _scanStepBean, com.leedarson.serviceimpl.blec075.reports.a _reporter, LDSBleCacheItemBean itemBean, Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{_scanStepBean, _reporter, itemBean, throwable}, this, changeQuickRedirect, false, 6503, new Class[]{BleConnectStepBean.class, com.leedarson.serviceimpl.blec075.reports.a.class, LDSBleCacheItemBean.class, Throwable.class}, Void.TYPE).isSupported) {
            String tip = "自动连接任务 自动巡检-查找设备异常 (并未发现到设备10S内) exception=" + throwable.toString();
            _scanStepBean.putResponseData("desc", tip, 402);
            _scanStepBean.desc = tip;
            _reporter.e();
            itemBean.CONNECT_STATUE = 4;
            E(itemBean.bzMac, 0, "自动巡检-查找设备异常 exception=" + throwable.toString());
            if (this.a.get(itemBean.bzMac)._callbackProxy != null) {
                this.a.get(itemBean.bzMac)._callbackProxy.onConnectFail(0, itemBean.bzMac, 402, tip);
            }
        }
    }

    private boolean o(LDSBleCacheItemBean itemBean) {
        LdsRequestConnectConfigBean ldsRequestConnectConfigBean;
        h hVar = itemBean._taskItemDesc;
        if (hVar == null || (ldsRequestConnectConfigBean = hVar.c) == null || !ldsRequestConnectConfigBean.checkPairingStatus) {
            return false;
        }
        return ldsRequestConnectConfigBean.checkPairingStatus;
    }

    private void F(String errorTipsInfo, h taskItem, int code) {
        int i = 1;
        if (!PatchProxy.proxy(new Object[]{errorTipsInfo, taskItem, new Integer(code)}, this, changeQuickRedirect, false, 6486, new Class[]{String.class, h.class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", code);
                jsonObject.put("desc", (Object) errorTipsInfo);
                LdsRequestConnectConfigBean ldsRequestConnectConfigBean = taskItem.c;
                if (ldsRequestConnectConfigBean != null) {
                    if (!ldsRequestConnectConfigBean.checkPairingStatus) {
                        i = 0;
                    }
                    jsonObject.put("pairingStatus", i);
                }
                JSONObject _dataResponse = new JSONObject();
                _dataResponse.put("desc", (Object) errorTipsInfo);
                jsonObject.put("data", (Object) _dataResponse);
                jsonObject.put("taskId", (Object) taskItem.d);
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(taskItem.b, jsonObject.toString()));
            } catch (Exception e2) {
                timber.log.a.g(this.c).c(" responseStatueToWeb Exception=" + e2.toString(), new Object[0]);
            }
        }
    }

    @SuppressLint({"MissingPermission"})
    private void G(BluetoothGatt gatt) {
        if (!PatchProxy.proxy(new Object[]{gatt}, this, changeQuickRedirect, false, 6487, new Class[]{BluetoothGatt.class}, Void.TYPE).isSupported) {
            if (gatt != null) {
                gatt.disconnect();
                gatt.close();
            }
        }
    }

    private void E(String taskId, int onlineStatue, String desc) {
        BluetoothGatt bluetoothGatt;
        BleC075Service _bleServiceImpl;
        Class<String> cls = String.class;
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{taskId, new Integer(onlineStatue), desc}, this, changeQuickRedirect, false, 6488, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            if (onlineStatue == 0 && (_bleServiceImpl = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class)) != null) {
                a("[清除蓝牙扫描的缓存]  taskId=" + taskId + "  清除情况=" + _bleServiceImpl.deleteBleCacheDeviceBeanByBzMac(taskId));
            }
            a("BLE.TRV  通知设备在线状态变化：inConnect=" + onlineStatue + " taskId=" + taskId + "   desc=" + desc);
            JSONObject notifyMessageBody = new JSONObject();
            try {
                notifyMessageBody.put("mac", (Object) taskId);
                notifyMessageBody.put("status", onlineStatue);
                notifyMessageBody.put("desc", (Object) desc);
                if (!(onlineStatue != 1 || this.a.get(taskId) == null || this.a.get(taskId)._taskItemDesc == null || this.a.get(taskId)._taskItemDesc.c == null)) {
                    notifyMessageBody.put("pairingStatus", this.a.get(taskId)._taskItemDesc.c.checkPairingStatus ? 1 : 0);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_NEW, H5ActionName.ACTION_KVS_CON_STATUSCHANGE, notifyMessageBody.toString()));
            if (onlineStatue == 0) {
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_BUSINESS, H5ActionName.ACTION_KVS_CON_STATUSCHANGE, notifyMessageBody.toString()));
            }
            Intent intent = new Intent("com.leedarson.BLE_CONNECT_STATE_CHANGE");
            if (onlineStatue == 1) {
                i = 1;
            }
            intent.putExtra("com.leedarson.EXTRAS_BLE_STATE", i);
            BleDevice bleDevice = null;
            LDSBleCacheItemBean itemBean = this.a.get(taskId);
            if (itemBean != null) {
                bleDevice = this.a.get(taskId).bleDevice;
                if (!(onlineStatue != 1 || (bluetoothGatt = itemBean._bleGattConnection) == null || bluetoothGatt.getServices() == null)) {
                    intent.putParcelableArrayListExtra("com.leedarson.EXTRAS_BLE_SERVICE_LIST", (ArrayList) itemBean._bleGattConnection.getServices());
                }
            }
            if (onlineStatue == 0 && this.a.get(taskId) != null && this.a.get(taskId).bleDevice == null) {
                bleDevice = new BleDevice((BluetoothDevice) null);
            }
            intent.putExtra("com.leedarson.EXTRAS_BLE_DEVICE", bleDevice);
            intent.putExtra("com.leedarson.EXTRAS_BLE_H5MAC", taskId);
            LocalBroadcastManager.getInstance(BaseApplication.b()).sendBroadcast(intent);
        }
    }

    private void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6489, new Class[0], Void.TYPE).isSupported) {
            if (this.e != null) {
                a("自动连接任务：已启动，不需要重复调用");
                return;
            }
            a("收到开启自动连接任务...启动中");
            this.e = io.reactivex.l.C(10, TimeUnit.SECONDS).J(l.d).Y(new e(this), new a(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: A */
    public /* synthetic */ void B(Long l) {
        if (!PatchProxy.proxy(new Object[]{l}, this, changeQuickRedirect, false, 6502, new Class[]{Long.class}, Void.TYPE).isSupported) {
            a("自动连接任务：执行ing");
            j();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: C */
    public /* synthetic */ void D(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 6501, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g(this.c);
            g.c("自动重连连接任务被中断 exception=" + throwable.toString(), new Object[0]);
            io.reactivex.disposables.b bVar = this.e;
            if (bVar != null && !bVar.isDisposed()) {
                this.e.dispose();
            }
        }
    }

    public void h(String taskId, String callbackKey) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{taskId, callbackKey}, this, changeQuickRedirect, false, 6490, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            a("收到蓝牙连接请求断开任务： taskId=" + taskId);
            this.b.add(taskId);
            LDSBleCacheItemBean cacheItemBean = this.a.get(taskId);
            if (cacheItemBean != null) {
                a("收到蓝牙连接请求断开任务：发现缓存池中确实存在 taskId=" + taskId);
                if (cacheItemBean._bleGattConnection != null) {
                    a("收到蓝牙连接请求断开任务：发现缓存池中确实存在 强制断开蓝牙连接");
                    BleC075Service.CommonBleConnectCallback commonBleConnectCallback = cacheItemBean._callbackProxy;
                    if (commonBleConnectCallback != null) {
                        commonBleConnectCallback.onDisConnected(taskId, cacheItemBean._bleGattConnection, 1003);
                    }
                    G(cacheItemBean._bleGattConnection);
                }
                a("收到蓝牙连接请求断开任务: 移除缓存数中数据  taskId=" + taskId);
                this.a.remove(taskId);
                h requestItem = new h();
                requestItem.b = callbackKey;
                requestItem.d = taskId;
                F("断开设备成功 mac=" + taskId, requestItem, 200);
                E(taskId, 0, "断开设备成功 taskId=" + taskId);
                a("收到蓝牙连接请求断开任务: 状态同步完成  taskId=" + taskId);
                return;
            }
            a("没有查到这个任务，连接管理中心并未有这个任务");
            h requestItem2 = new h();
            requestItem2.b = callbackKey;
            requestItem2.d = taskId;
            F("断开设备成功 mac=" + taskId, requestItem2, 200);
            E(taskId, 0, "断开设备成功 taskId=" + taskId);
        }
    }

    public void i(String callbakKey) {
        if (!PatchProxy.proxy(new Object[]{callbakKey}, this, changeQuickRedirect, false, 6491, new Class[]{String.class}, Void.TYPE).isSupported) {
            a("收到断开所有蓝牙连接请求");
            HashMap<String, LDSBleCacheItemBean> tempCache = new HashMap<>();
            for (Map.Entry<String, LDSBleCacheItemBean> item : this.a.entrySet()) {
                tempCache.put(item.getKey(), item.getValue());
            }
            this.a.clear();
            this.b.clear();
            for (Map.Entry<String, LDSBleCacheItemBean> item2 : tempCache.entrySet()) {
                LDSBleCacheItemBean cacheItem = item2.getValue();
                if (cacheItem._bleGattConnection != null) {
                    if (!(tempCache.get(cacheItem.bzMac) == null || tempCache.get(cacheItem.bzMac)._callbackProxy == null)) {
                        tempCache.get(cacheItem.bzMac)._callbackProxy.onDisConnected(cacheItem.bzMac, cacheItem._bleGattConnection, 1003);
                    }
                    G(cacheItem._bleGattConnection);
                }
                E(cacheItem.bzMac, 0, "触发了强制断开");
            }
            h item3 = new h();
            item3.b = callbakKey;
            F("Ble的所有设备断开成功", item3, 200);
        }
    }

    public ArrayList<LDSBleCacheItemBean> q() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6492, new Class[0], ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        ArrayList<LDSBleCacheItemBean> resuts = new ArrayList<>();
        for (Map.Entry<String, LDSBleCacheItemBean> item : this.a.entrySet()) {
            LDSBleCacheItemBean cacheItem = item.getValue();
            int i = cacheItem.CONNECT_STATUE;
            if (!(i == 1 || i == 2 || cacheItem._taskItemDesc.c.autoConnect)) {
                resuts.add(cacheItem);
            }
        }
        return resuts;
    }

    public BleDevice k(String bzMac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bzMac}, this, changeQuickRedirect, false, 6493, new Class[]{String.class}, BleDevice.class);
        if (proxy.isSupported) {
            return (BleDevice) proxy.result;
        }
        LDSBleCacheItemBean cacheItemBean = this.a.get(bzMac);
        if (cacheItemBean == null) {
            return null;
        }
        return cacheItemBean.bleDevice;
    }

    public LDSBleCacheItemBean n(String deviceMacAddress) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceMacAddress}, this, changeQuickRedirect, false, 6494, new Class[]{String.class}, LDSBleCacheItemBean.class);
        if (proxy.isSupported) {
            return (LDSBleCacheItemBean) proxy.result;
        }
        for (Map.Entry<String, LDSBleCacheItemBean> itemBean : this.a.entrySet()) {
            LDSBleCacheItemBean item = itemBean.getValue();
            if (TextUtils.isEmpty(item.realDeviceMac) && deviceMacAddress.equals(item.realDeviceMac)) {
                return item;
            }
        }
        return null;
    }

    public BleDevice l(String bzMac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bzMac}, this, changeQuickRedirect, false, 6495, new Class[]{String.class}, BleDevice.class);
        if (proxy.isSupported) {
            return (BleDevice) proxy.result;
        }
        LDSBleCacheItemBean cacheItemBean = this.a.get(bzMac);
        if (cacheItemBean != null && cacheItemBean.CONNECT_STATUE == 1) {
            return cacheItemBean.bleDevice;
        }
        return null;
    }

    public BleDevice m(String deviceAddress) {
        BleDevice bleDevice;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceAddress}, this, changeQuickRedirect, false, 6496, new Class[]{String.class}, BleDevice.class);
        if (proxy.isSupported) {
            return (BleDevice) proxy.result;
        }
        for (Map.Entry<String, LDSBleCacheItemBean> itemBean : this.a.entrySet()) {
            LDSBleCacheItemBean item = itemBean.getValue();
            if (item.CONNECT_STATUE == 1 && (bleDevice = item.bleDevice) != null && bleDevice.c().equals(deviceAddress)) {
                return item.bleDevice;
            }
        }
        return null;
    }

    public LDSBleCacheItemBean p(String bzMac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bzMac}, this, changeQuickRedirect, false, 6497, new Class[]{String.class}, LDSBleCacheItemBean.class);
        if (proxy.isSupported) {
            return (LDSBleCacheItemBean) proxy.result;
        }
        LDSBleCacheItemBean resultBean = this.a.get(bzMac);
        if (resultBean == null || resultBean.CONNECT_STATUE == 1) {
            return null;
        }
        return resultBean;
    }

    public void g(h taskItem, BleC075Service.CommonBleConnectCallback _callbackProxy, String ref) {
        Class[] clsArr = {h.class, BleC075Service.CommonBleConnectCallback.class, String.class};
        if (!PatchProxy.proxy(new Object[]{taskItem, _callbackProxy, ref}, this, changeQuickRedirect, false, 6499, clsArr, Void.TYPE).isSupported) {
            f(taskItem, _callbackProxy, "proxyCallback " + ref);
        }
    }

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6500, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g(this.c);
            g.m("LdsConnectDevice: " + msg, new Object[0]);
        }
    }
}
