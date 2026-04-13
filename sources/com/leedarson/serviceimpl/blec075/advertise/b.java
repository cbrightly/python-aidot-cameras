package com.leedarson.serviceimpl.blec075.advertise;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.text.TextUtils;
import com.clj.fastble.data.BleDevice;
import com.leedarson.base.utils.e;
import com.leedarson.base.utils.w;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.base.c;
import com.leedarson.serviceimpl.base.d;
import com.leedarson.serviceimpl.blec075.beans.BleScanCacheBean;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceimpl.blec075.onekeyreset.h;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.MatterService;
import com.leedarson.serviceinterface.event.BleDeviceFoundEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: AdvertiseDispatcher */
public class b implements d {
    public static ChangeQuickRedirect changeQuickRedirect;
    boolean a = false;
    private String b = "BleC075ServiceImpl-Advertise";
    private h c;
    private Map<String, BleDevice> d = new ConcurrentHashMap();
    private Map<String, BleScanCacheBean> e = new ConcurrentHashMap();

    public b(h resetDispatcher) {
        this.c = resetDispatcher;
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6458, new Class[0], Void.TYPE).isSupported) {
            this.d.clear();
            c.k().x(this);
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6459, new Class[0], Void.TYPE).isSupported) {
            c.k().B(this);
            this.d.clear();
        }
    }

    private boolean h(BleDevice bleDevice) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6460, new Class[]{BleDevice.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (!this.d.containsKey(bleDevice.b()) || !Arrays.equals(bleDevice.f(), this.d.get(bleDevice.b()).f())) {
                bleDevice.l(System.currentTimeMillis());
                this.d.put(bleDevice.b(), bleDevice);
                return true;
            } else if (System.currentTimeMillis() - this.d.get(bleDevice.b()).g() <= 10000) {
                return false;
            } else {
                bleDevice.l(System.currentTimeMillis());
                this.d.put(bleDevice.b(), bleDevice);
                return true;
            }
        } catch (Exception e2) {
            return true;
        }
    }

    public void c(BleDevice bleDevice) {
        byte[] record;
        String str;
        byte[] bArr;
        byte[] bArr2;
        if (!PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6461, new Class[]{BleDevice.class}, Void.TYPE).isSupported) {
            BleDevice bleDevice2 = bleDevice;
            try {
                if (h(bleDevice2) && (record = bleDevice2.f()) != null && record.length > 0) {
                    this.a = false;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("rssi", bleDevice2.e());
                        Map<Byte, h.a> k = com.leedarson.serviceimpl.blec075.h.k(record);
                        h.a customDataUnit = k.get((byte) -1);
                        h.a serviceDataUnit = k.get((byte) 22);
                        h.a restDataUnit = k.get((byte) 7);
                        String deviceName = "";
                        if (!TextUtils.isEmpty(bleDevice2.d())) {
                            deviceName = bleDevice2.d();
                        }
                        str = ", advertisingData:";
                        if (c.a(serviceDataUnit)) {
                            String advertisementData = com.leedarson.serviceimpl.blec075.h.b(serviceDataUnit.c);
                            jsonObject.put("advertisingData", (Object) advertisementData);
                            jsonObject.put("deviceName", (Object) deviceName);
                            Map<Byte, h.a> map = k;
                            a.b g = a.g(this.b);
                            h.a restDataUnit2 = restDataUnit;
                            StringBuilder sb = new StringBuilder();
                            String str2 = "";
                            try {
                                sb.append("onLeScan bleDevice: ServiceDataParser.validate");
                                sb.append(bleDevice2.c());
                                sb.append(", name: ");
                                sb.append(bleDevice2.d());
                                sb.append(", record:");
                                sb.append(com.leedarson.serviceimpl.blec075.h.b(record));
                                sb.append(str);
                                sb.append(advertisementData);
                                g.m(sb.toString(), new Object[0]);
                                byte[] bArr3 = serviceDataUnit.c;
                                if (bArr3[0] == -10 && bArr3[1] == -1) {
                                    if (!(customDataUnit == null || (bArr2 = customDataUnit.c) == null)) {
                                        advertisementData = advertisementData + com.leedarson.serviceimpl.blec075.h.b(bArr2);
                                    }
                                    JSONObject matterInfo = ((MatterService) com.alibaba.android.arouter.launcher.a.c().g(MatterService.class)).parseBle(advertisementData);
                                    if (matterInfo != null) {
                                        jsonObject.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) "matter");
                                        jsonObject.put("matterInfo", (Object) matterInfo);
                                        jsonObject.put("advertisingData", (Object) advertisementData);
                                    }
                                    a.i("ConnectRetry  ===> mac=" + bleDevice2.c() + "   advertisementData=  " + advertisementData, new Object[0]);
                                    bleDevice2.m(advertisementData);
                                    com.leedarson.serviceimpl.ble.manager.c.b().c().b(bleDevice2.c(), bleDevice2);
                                }
                                d(jsonObject);
                                h.a aVar = restDataUnit2;
                                str = str2;
                            } catch (Exception e2) {
                                e = e2;
                                str = str2;
                                e.printStackTrace();
                                jsonObject.put("advertisingData", (Object) str);
                                jsonObject.put("deviceName", (Object) bleDevice2.d());
                                d(jsonObject);
                            }
                        } else {
                            String str3 = "";
                            Map<Byte, h.a> map2 = k;
                            h.a restDataUnit3 = restDataUnit;
                            if (customDataUnit == null || (bArr = customDataUnit.c) == null || bArr.length <= 0) {
                                str = str3;
                                if (restDataUnit3 != null) {
                                    h.a restDataUnit4 = restDataUnit3;
                                    byte[] bArr4 = restDataUnit4.c;
                                    if (bArr4 != null && w.a(bArr4).startsWith("1115")) {
                                        bleDevice2.m(w.a(restDataUnit4.c));
                                        com.leedarson.serviceimpl.ble.manager.c.b().c().b(bleDevice2.c(), bleDevice2);
                                        a.c("rest - onLeScan bleDevice: 33333" + bleDevice2.c() + ", name: " + bleDevice2.d() + ", record:" + com.leedarson.serviceimpl.blec075.h.b(record), new Object[0]);
                                        this.c.o(restDataUnit4, jsonObject, deviceName);
                                    }
                                }
                                com.leedarson.serviceimpl.ble.manager.c.b().c().b(bleDevice2.c(), bleDevice2);
                            } else {
                                String advertisementData2 = com.leedarson.serviceimpl.blec075.h.b(bArr);
                                jsonObject.put("advertisingData", (Object) advertisementData2);
                                jsonObject.put("deviceName", (Object) deviceName);
                                a.g(this.b).m("onLeScan bleDevice: 222222 " + bleDevice2.c() + ", name: " + bleDevice2.d() + ", record:" + com.leedarson.serviceimpl.blec075.h.b(record) + str + advertisementData2, new Object[0]);
                                str = str3;
                                try {
                                    String lowerCase = bleDevice2.c().replace(":", str).toLowerCase();
                                    if (advertisementData2.contains("3030")) {
                                        a.g(this.b).c("find : 3030", new Object[0]);
                                    }
                                    bleDevice2.m(advertisementData2);
                                    com.leedarson.serviceimpl.ble.manager.c.b().c().b(bleDevice2.c(), bleDevice2);
                                    new com.leedarson.serviceimpl.blec075.d().b(record, bleDevice2.a());
                                    d(jsonObject);
                                    h.a aVar2 = restDataUnit3;
                                } catch (Exception e3) {
                                    e = e3;
                                    e.printStackTrace();
                                    jsonObject.put("advertisingData", (Object) str);
                                    jsonObject.put("deviceName", (Object) bleDevice2.d());
                                    d(jsonObject);
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(deviceName)) {
                            jsonObject.put("advertisingData", (Object) str);
                            jsonObject.put("deviceName", (Object) deviceName);
                            d(jsonObject);
                        }
                    } catch (Exception e4) {
                        e = e4;
                        str = "";
                        e.printStackTrace();
                        jsonObject.put("advertisingData", (Object) str);
                        jsonObject.put("deviceName", (Object) bleDevice2.d());
                        d(jsonObject);
                    }
                }
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
    }

    public void d(JSONObject json) {
        if (!PatchProxy.proxy(new Object[]{json}, this, changeQuickRedirect, false, 6462, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_NEW, BleMeshService.ON_BROAD_CAST, json.toString()));
        }
    }

    public void e(BluetoothDevice dev, int rssi, byte[] scanRecord) {
        Object[] objArr = {dev, new Integer(rssi), scanRecord};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6463, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            new AdvertisingDevice(dev, rssi, scanRecord);
            BleDeviceFoundEvent event = new BleDeviceFoundEvent(1);
            event.bluetoothDevice = dev;
            event.rssi = rssi;
            event.scanRecord = scanRecord;
            org.greenrobot.eventbus.c.c().l(event);
        }
    }

    public void onLeScan(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord, ScanRecord scanRecord2) {
        Object[] objArr = {bluetoothDevice, new Integer(rssi), scanRecord, scanRecord2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6464, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class, ScanRecord.class}, Void.TYPE).isSupported) {
            e(bluetoothDevice, rssi, scanRecord);
            BleDevice bleDevice = new BleDevice(bluetoothDevice, rssi, scanRecord, System.currentTimeMillis());
            c(bleDevice);
            BleScanCacheBean _bleScanCacheBean = new BleScanCacheBean();
            _bleScanCacheBean.createTime = System.currentTimeMillis();
            _bleScanCacheBean._bleDevice = bleDevice;
            bleDevice.z = true;
            String a2 = e.a(scanRecord);
            _bleScanCacheBean._adHexStr = a2;
            this.e.put(a2, _bleScanCacheBean);
        }
    }

    public void onScanFail(int errorCode) {
    }

    public String onFromBz() {
        return "BleC075ServiceImpl web->ble startscan or oneKeyReset";
    }

    public BleScanCacheBean b(String macId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{macId}, this, changeQuickRedirect, false, 6465, new Class[]{String.class}, BleScanCacheBean.class);
        if (proxy.isSupported) {
            return (BleScanCacheBean) proxy.result;
        }
        for (Map.Entry<String, BleScanCacheBean> item : this.e.entrySet()) {
            if (item.getKey().contains(macId)) {
                return item.getValue();
            }
        }
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(java.lang.String r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r4 = 0
            r5 = 6466(0x1942, float:9.061E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0025
            java.lang.Object r9 = r0.result
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            return r9
        L_0x0025:
            r0 = r8
            r1 = 0
            java.lang.String r2 = ""
            java.util.Map<java.lang.String, com.leedarson.serviceimpl.blec075.beans.BleScanCacheBean> r3 = r0.e
            java.util.Set r3 = r3.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x0033:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0055
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r5 = r4.getKey()
            java.lang.String r5 = (java.lang.String) r5
            boolean r5 = r5.contains(r9)
            if (r5 == 0) goto L_0x0054
            r1 = 1
            java.lang.Object r3 = r4.getKey()
            r2 = r3
            java.lang.String r2 = (java.lang.String) r2
            goto L_0x0055
        L_0x0054:
            goto L_0x0033
        L_0x0055:
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x0060
            java.util.Map<java.lang.String, com.leedarson.serviceimpl.blec075.beans.BleScanCacheBean> r3 = r0.e
            r3.remove(r2)
        L_0x0060:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.blec075.advertise.b.a(java.lang.String):boolean");
    }
}
