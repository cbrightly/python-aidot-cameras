package com.leedarson.bean;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.sender.c;
import com.leedarson.sender.e;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.event.BleDeviceFoundEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.l;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

public class RhyBLEQhmDevice extends IRhyDevice {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String bleMac;
    private String devName;
    private String groupId;
    private String modelId = "RhyBleDevice";
    private String rhythmType;
    private e sender;
    private BleC075Service service;

    public RhyBLEQhmDevice(String rhythmType2, String protocolType, JSONObject protocolData) {
        super(rhythmType2, protocolType, protocolData);
        try {
            this.rhythmType = rhythmType2;
            if (protocolData.has("mac")) {
                this.devName = protocolData.getString("mac");
                setGroup(false);
            }
            if (protocolData.has("groupId")) {
                this.groupId = protocolData.getString("groupId");
                setGroup(true);
            }
            if (protocolData.has("modelId")) {
                this.modelId = protocolData.optString("modelId", "RhyBleDevice");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setRhyId(this.isGroup ? this.groupId : this.devName);
        if (this.sender == null) {
            this.sender = new c(this.groupId);
        }
        this.service = (BleC075Service) a.c().g(BleC075Service.class);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void start() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 1099(0x44b, float:1.54E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r8
            com.leedarson.serviceinterface.BleC075Service r2 = r1.service
            if (r2 == 0) goto L_0x004b
            java.lang.String r3 = r1.devName
            java.lang.String r2 = r2.getDeviceMac(r3)
            r1.bleMac = r2
            com.leedarson.serviceinterface.BleC075Service r3 = r1.service
            boolean r2 = r3.isConnected(r2)
            if (r2 == 0) goto L_0x003d
            com.leedarson.sender.e r0 = r1.sender
            if (r0 == 0) goto L_0x0036
            com.leedarson.sender.c r0 = (com.leedarson.sender.c) r0
            java.lang.String r2 = r1.bleMac
            r0.g(r2)
        L_0x0036:
            java.util.concurrent.atomic.AtomicBoolean r0 = r1.isRunning
            r2 = 1
            r0.set(r2)
            goto L_0x004b
        L_0x003d:
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            r2.p(r1)
            com.leedarson.serviceinterface.BleC075Service r2 = r1.service
            r3 = 10000(0x2710, float:1.4013E-41)
            r2.scan(r3, r0)
        L_0x004b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.bean.RhyBLEQhmDevice.start():void");
    }

    @l
    public void onBleDeviceFoundEvent(BleDeviceFoundEvent bleDeviceFoundEvent) {
        if (!PatchProxy.proxy(new Object[]{bleDeviceFoundEvent}, this, changeQuickRedirect, false, 1100, new Class[]{BleDeviceFoundEvent.class}, Void.TYPE).isSupported) {
            BleDeviceFoundEvent event = bleDeviceFoundEvent;
            a.b g = timber.log.a.g("Rhythm");
            g.a("onBleDeviceFoundEvent:" + event.bluetoothDevice.getAddress(), new Object[0]);
            if (this.service != null) {
                String name = event.bluetoothDevice.getName();
                a.b g2 = timber.log.a.g("Rhythm");
                g2.a("onBleDeviceFoundEvent devName:" + name, new Object[0]);
                if (!TextUtils.isEmpty(this.devName) && this.devName.equals(name)) {
                    this.service.stopScan();
                    String address = event.bluetoothDevice.getAddress();
                    this.bleMac = address;
                    e eVar = this.sender;
                    if (eVar != null) {
                        ((c) eVar).g(address);
                    }
                    a.b g3 = timber.log.a.g("Rhythm");
                    g3.a("onBleDeviceFoundEvent bleMac:" + this.bleMac, new Object[0]);
                    this.service.commonConnect(this.bleMac, this.devName, false, (BluetoothDevice) null, (BleC075Service.CommonBleConnectCallback) null, this.modelId, false, "RhyBLEQhmDevice.onBleDeviceFoundEvent");
                    this.isRunning.set(true);
                }
            }
        }
    }

    public void send() {
    }

    public void stop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1101, new Class[0], Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().r(this);
            BleC075Service bleC075Service = this.service;
            if (bleC075Service != null) {
                bleC075Service.stopScan();
                this.service.disConnectTask(this.devName, "");
            }
            this.isRunning.set(false);
        }
    }
}
