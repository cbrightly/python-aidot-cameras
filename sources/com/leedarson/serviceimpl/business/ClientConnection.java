package com.leedarson.serviceimpl.business;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.le.ScanRecord;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.alibaba.android.arouter.launcher.a;
import com.clj.fastble.data.BleDevice;
import com.leedarson.serviceimpl.base.c;
import com.leedarson.serviceimpl.base.d;
import com.leedarson.serviceimpl.blec075.f;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceimpl.business.bean.BleBusinessConnectBean;
import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.reactivex.functions.e;
import timber.log.a;

public class ClientConnection implements d {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public String TAG = "BleC075ServiceImpl-Connection";
    /* access modifiers changed from: private */
    public BluetoothGatt _gatt;
    private boolean autoConnect = false;
    private BleC075Service bleC075Service;
    private BluetoothDevice bluetoothDevice;
    public boolean checkPairingStatus = false;
    /* access modifiers changed from: private */
    public OnGattConnectListener gattConnectListener;
    private Handler handler;
    private String modelId = "";
    public boolean needWakeUp;
    private volatile boolean shutdown = false;
    private String wifiMac;

    public interface OnGattConnectEventChangeHandler {
        void onConnectFail(int i, String str, String str2, BleBusinessConnectBean bleBusinessConnectBean);

        void onConnected(String str, BleBusinessConnectBean bleBusinessConnectBean, ClientConnection clientConnection);

        void onDisconnect(String str, String str2, BleBusinessConnectBean bleBusinessConnectBean);
    }

    public interface OnGattConnectListener {
        void onConnectFail(int i, String str);

        void onConnected(ClientConnection clientConnection);

        void onDisconnect(String str);

        void onReconnected(ClientConnection clientConnection);

        void onWakeupFail(Pair<Integer, String> pair);
    }

    static /* synthetic */ void access$100(ClientConnection x0, String x1) {
        Class[] clsArr = {ClientConnection.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 7044, clsArr, Void.TYPE).isSupported) {
            x0._startConect(x1);
        }
    }

    public ClientConnection(String wifiMac2, String modelId2, boolean autoConnect2, boolean _checkPairingStatus, boolean needWakeUp2) {
        this.wifiMac = wifiMac2;
        this.checkPairingStatus = _checkPairingStatus;
        this.modelId = modelId2;
        this.autoConnect = autoConnect2;
        this.needWakeUp = needWakeUp2;
        this.bleC075Service = (BleC075Service) a.c().g(BleC075Service.class);
    }

    public String getWifiMac() {
        return this.wifiMac;
    }

    public BluetoothGatt getBluetoothGatt() {
        return this._gatt;
    }

    public void connect(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 7037, new Class[]{String.class}, Void.TYPE).isSupported) {
            startConnect(ref + ".ClientConnection.connect");
        }
    }

    private void startConnect(final String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 7038, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g(this.TAG);
            g.a("LdsConnectDevice  ClientConnection 发起连接:" + this.wifiMac, new Object[0]);
            if (this.needWakeUp) {
                f advertise = new f();
                advertise.f().H(new e<Pair<Integer, String>>() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public /* bridge */ /* synthetic */ void accept(Object obj) {
                        if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7046, new Class[]{Object.class}, Void.TYPE).isSupported) {
                            accept((Pair<Integer, String>) (Pair) obj);
                        }
                    }

                    public void accept(Pair<Integer, String> pair) {
                        if (!PatchProxy.proxy(new Object[]{pair}, this, changeQuickRedirect, false, 7045, new Class[]{Pair.class}, Void.TYPE).isSupported) {
                            a.b g = timber.log.a.g(ClientConnection.this.TAG);
                            g.c("ble startConnect pre needWakeUp:" + pair.first + ",value:" + ((String) pair.second) + ", thread:" + Thread.currentThread().getName(), new Object[0]);
                            if (((Integer) pair.first).intValue() == 0) {
                                ClientConnection clientConnection = ClientConnection.this;
                                ClientConnection.access$100(clientConnection, ref + ".ClientConnection.startConnect(NeedWakeUp)");
                                return;
                            }
                            timber.log.a.g("ldsConnectDevice").m("唤醒设备失败", new Object[0]);
                            ClientConnection.this.gattConnectListener.onWakeupFail(pair);
                        }
                    }
                });
                advertise.h(this.wifiMac, "", 1);
                return;
            }
            _startConect(ref + ".ClientConnection.startConnect(NoNeedWakeUp)");
        }
    }

    private void _startConect(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 7039, new Class[]{String.class}, Void.TYPE).isSupported) {
            BleC075Service bleC075Service2 = this.bleC075Service;
            String str = this.wifiMac;
            boolean z = this.autoConnect;
            AnonymousClass2 r6 = new BleC075Service.CommonBleConnectCallback() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onConnectFail(int code, String str, int i, String exception) {
                    Class<String> cls = String.class;
                    Object[] objArr = {new Integer(code), str, new Integer(i), exception};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    Class cls2 = Integer.TYPE;
                    if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7047, new Class[]{cls2, cls, cls2, cls}, Void.TYPE).isSupported) {
                        if (ClientConnection.this.gattConnectListener != null) {
                            ClientConnection.this.gattConnectListener.onConnectFail(code, exception);
                        }
                    }
                }

                public void onConnectSuccess(String str, BluetoothGatt gatt, int i) {
                    Object[] objArr = {str, gatt, new Integer(i)};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7048, new Class[]{String.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                        BluetoothGatt unused = ClientConnection.this._gatt = gatt;
                        if (ClientConnection.this.gattConnectListener != null) {
                            ClientConnection.this.gattConnectListener.onConnected(ClientConnection.this);
                        }
                    }
                }

                public void onDisConnected(String str, BluetoothGatt gatt, int status) {
                    Object[] objArr = {str, gatt, new Integer(status)};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7049, new Class[]{String.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                        BluetoothGatt unused = ClientConnection.this._gatt = gatt;
                        if (ClientConnection.this.gattConnectListener != null) {
                            OnGattConnectListener access$200 = ClientConnection.this.gattConnectListener;
                            access$200.onDisconnect("status :" + status);
                        }
                    }
                }
            };
            String str2 = this.modelId;
            boolean z2 = this.checkPairingStatus;
            bleC075Service2.commonConnect("", str, z, (BluetoothDevice) null, r6, str2, z2, ref + ".ClientConnection._startConnect");
        }
    }

    public void disconnect() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7040, new Class[0], Void.TYPE).isSupported) {
            this.shutdown = true;
            c.k().B(this);
        }
    }

    private void scanDevice() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7041, new Class[0], Void.TYPE).isSupported) {
            this.shutdown = false;
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
            c.k().x(this);
        }
    }

    public void onLeScan(BluetoothDevice bluetoothDevice2, int rssi, byte[] scanRecord, @Nullable ScanRecord scanRecord2) {
        Object[] objArr = {bluetoothDevice2, new Integer(rssi), scanRecord, scanRecord2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7042, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class, ScanRecord.class}, Void.TYPE).isSupported) {
            if (!this.shutdown) {
                a.b g = timber.log.a.g(this.TAG);
                g.m("rssi:" + rssi + " , scanRecord" + com.leedarson.base.utils.e.a(scanRecord), new Object[0]);
                if (matches(h.k(scanRecord).get((byte) -1), this.wifiMac)) {
                    a.b g2 = timber.log.a.g(this.TAG);
                    g2.c("发现BleBusiness设备:" + com.leedarson.base.utils.e.a(scanRecord), new Object[0]);
                    c.k().B(this);
                    BleDevice bleDevice = new BleDevice(bluetoothDevice2);
                    bleDevice.k(scanRecord);
                    bleDevice.j(rssi);
                    com.leedarson.serviceimpl.ble.manager.c.b().c().b(this.wifiMac, bleDevice);
                }
            }
        }
    }

    public String onFromBz() {
        return "ClientConnection scan";
    }

    public void setGattConnectListener(OnGattConnectListener gattConnectListener2) {
        this.gattConnectListener = gattConnectListener2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x002f, code lost:
        r1 = r11.c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean matches(com.leedarson.serviceimpl.blec075.h.a r11, java.lang.String r12) {
        /*
            r10 = this;
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r11
            r9 = 1
            r1[r9] = r12
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.serviceimpl.blec075.h$a> r0 = com.leedarson.serviceimpl.blec075.h.a.class
            r6[r8] = r0
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r9] = r0
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r4 = 0
            r5 = 7043(0x1b83, float:9.87E-42)
            r2 = r10
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x002c
            java.lang.Object r11 = r0.result
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            return r11
        L_0x002c:
            r0 = r10
            if (r11 == 0) goto L_0x0046
            byte[] r1 = r11.c
            if (r1 == 0) goto L_0x0046
            java.lang.String r1 = com.leedarson.base.utils.e.a(r1)
            java.lang.String r1 = r1.toLowerCase()
            java.lang.String r2 = r12.toLowerCase()
            boolean r1 = r1.contains(r2)
            if (r1 == 0) goto L_0x0046
            return r9
        L_0x0046:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.ClientConnection.matches(com.leedarson.serviceimpl.blec075.h$a, java.lang.String):boolean");
    }

    public void onScanFail(int errorCode) {
    }
}
