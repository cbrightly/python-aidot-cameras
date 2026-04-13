package com.clj.fastble.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.clj.fastble.callback.e;
import com.clj.fastble.callback.f;
import com.clj.fastble.callback.g;
import com.clj.fastble.callback.h;
import com.clj.fastble.data.BleDevice;
import com.leedarson.base.http.observer.l;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import timber.log.a;

@TargetApi(18)
/* compiled from: BleBluetooth */
public class d {
    /* access modifiers changed from: private */
    public com.clj.fastble.callback.b a;
    /* access modifiers changed from: private */
    public g b;
    /* access modifiers changed from: private */
    public com.clj.fastble.callback.d c;
    /* access modifiers changed from: private */
    public com.clj.fastble.callback.d d;
    /* access modifiers changed from: private */
    public final HashMap<String, e> e = new HashMap<>();
    /* access modifiers changed from: private */
    public final HashMap<String, com.clj.fastble.callback.c> f = new HashMap<>();
    /* access modifiers changed from: private */
    public final HashMap<String, h> g = new HashMap<>();
    /* access modifiers changed from: private */
    public final HashMap<String, f> h = new HashMap<>();
    /* access modifiers changed from: private */
    public b i;
    /* access modifiers changed from: private */
    public boolean j = false;
    /* access modifiers changed from: private */
    public final BleDevice k;
    /* access modifiers changed from: private */
    public BluetoothGatt l;
    /* access modifiers changed from: private */
    public final c m = new c(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public int n = 0;
    int o = 0;
    int p = 5;
    io.reactivex.disposables.b q;
    private BluetoothGattCallback r = new a();
    /* access modifiers changed from: private */
    public int s = -1100;
    /* access modifiers changed from: private */
    public int t = -1300;
    /* access modifiers changed from: private */
    public int u = -1400;

    /* compiled from: BleBluetooth */
    public enum b {
        CONNECT_IDLE,
        CONNECT_CONNECTING,
        CONNECT_CONNECTED,
        CONNECT_FAILURE,
        CONNECT_DISCONNECT
    }

    static /* synthetic */ int r(d x0) {
        int i2 = x0.n + 1;
        x0.n = i2;
        return i2;
    }

    public d(BleDevice bleDevice) {
        this.k = bleDevice;
    }

    public e P() {
        return new e(this);
    }

    public synchronized void A(com.clj.fastble.callback.b callback) {
        this.a = callback;
    }

    public synchronized void D(String uuid, e bleNotifyCallback) {
        this.e.put(uuid, bleNotifyCallback);
    }

    public synchronized void B(String uuid, com.clj.fastble.callback.c bleIndicateCallback) {
        this.f.put(uuid, bleIndicateCallback);
    }

    public synchronized void F(String uuid, h bleWriteCallback) {
        this.g.put(uuid, bleWriteCallback);
    }

    public synchronized void E(String uuid, f bleReadCallback) {
        this.h.put(uuid, bleReadCallback);
    }

    public synchronized void T(String uuid) {
        if (this.e.containsKey(uuid)) {
            this.e.remove(uuid);
        }
    }

    public synchronized void R(String uuid) {
        if (this.f.containsKey(uuid)) {
            this.f.remove(uuid);
        }
    }

    public synchronized void G() {
        this.e.clear();
        this.f.clear();
        this.g.clear();
        this.h.clear();
    }

    public synchronized void U() {
        this.b = null;
    }

    public synchronized void C(com.clj.fastble.callback.d callback) {
        this.c = callback;
    }

    public synchronized void S() {
        this.c = null;
    }

    public String O() {
        return this.k.b();
    }

    public BleDevice N() {
        return this.k;
    }

    public BluetoothGatt M() {
        return this.l;
    }

    public synchronized BluetoothGatt I(BleDevice bleDevice, boolean autoConnect, com.clj.fastble.callback.b callback) {
        return J(bleDevice, autoConnect, callback, 0);
    }

    public synchronized BluetoothGatt J(BleDevice bleDevice, boolean autoConnect, com.clj.fastble.callback.b callback, int connectRetryCount) {
        com.clj.fastble.utils.a.b("BleBluetooth: connect deviceName: " + bleDevice.d() + "\nmac: " + bleDevice.c() + "\nautoConnect: " + autoConnect + "\ncurrentThread: " + Thread.currentThread().toString() + "\nconnectCount:" + (connectRetryCount + 1) + "\ngetmScanRecordHex:" + bleDevice.i() + "\ngetmBleAdvertisementData:" + bleDevice.h() + "\ngetRssi:" + bleDevice.e());
        boolean z = false;
        if (connectRetryCount == 0) {
            this.n = 0;
        }
        A(callback);
        this.i = b.CONNECT_CONNECTING;
        if (Build.VERSION.SDK_INT >= 23) {
            this.l = bleDevice.a().connectGatt(com.clj.fastble.a.o().n(), autoConnect, this.r, 2);
        } else {
            this.l = bleDevice.a().connectGatt(com.clj.fastble.a.o().n(), autoConnect, this.r);
        }
        if (this.l != null) {
            com.clj.fastble.callback.b bVar = this.a;
            if (bVar != null) {
                bVar.e();
            }
            Message message = this.m.obtainMessage();
            message.what = 7;
            this.m.sendMessageDelayed(message, com.clj.fastble.a.o().l());
        } else {
            L();
            Q();
            H();
            this.i = b.CONNECT_FAILURE;
            com.clj.fastble.a.o().q().k(this);
            com.clj.fastble.callback.b bVar2 = this.a;
            if (bVar2 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("GATT connect exception occurred! bluetoothGatt==null?");
                if (this.l == null) {
                    z = true;
                }
                sb.append(z);
                bVar2.b(bleDevice, new com.clj.fastble.exception.d(sb.toString()));
            }
        }
        return this.l;
    }

    public synchronized void K() {
        this.j = true;
        L();
    }

    /* access modifiers changed from: private */
    public synchronized void L() {
        if (this.l != null) {
            BleDevice bleDevice = this.k;
            if (bleDevice != null && !TextUtils.isEmpty(bleDevice.c())) {
                a.b g2 = timber.log.a.g("BleC075ServiceImpl-fastble");
                g2.a("BleBusiness.auto gatt disconnect,ble mac:" + this.k.c() + ",bleDevice hash:" + this.k.toString(), new Object[0]);
            }
            this.l.disconnect();
            this.l.close();
        }
    }

    /* access modifiers changed from: private */
    public synchronized void Q() {
        BluetoothGatt bluetoothGatt;
        try {
            Method refresh = BluetoothGatt.class.getMethod("refresh", new Class[0]);
            if (!(refresh == null || (bluetoothGatt = this.l) == null)) {
                boolean success = ((Boolean) refresh.invoke(bluetoothGatt, new Object[0])).booleanValue();
                com.clj.fastble.utils.a.b("refreshDeviceCache, is success:  " + success);
            }
        } catch (Exception e2) {
            com.clj.fastble.utils.a.b("exception occur while refreshing device: " + e2.getMessage());
            e2.printStackTrace();
        }
        return;
    }

    /* access modifiers changed from: private */
    public synchronized void H() {
        BluetoothGatt bluetoothGatt = this.l;
        if (bluetoothGatt != null) {
            bluetoothGatt.close();
        }
    }

    /* compiled from: BleBluetooth */
    public final class c extends Handler {
        c(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    d.this.L();
                    d.this.Q();
                    d.this.H();
                    if (d.this.n < com.clj.fastble.a.o().s()) {
                        com.clj.fastble.utils.a.a("Connect fail, try reconnect " + com.clj.fastble.a.o().t() + " millisecond later");
                        d.r(d.this);
                        Message message = d.this.m.obtainMessage();
                        message.what = 3;
                        d.this.m.sendMessageDelayed(message, com.clj.fastble.a.o().t());
                        return;
                    }
                    b unused = d.this.i = b.CONNECT_FAILURE;
                    com.clj.fastble.a.o().q().k(d.this);
                    int status = ((com.clj.fastble.data.a) msg.obj).a();
                    if (d.this.a != null) {
                        d.this.a.b(d.this.k, new com.clj.fastble.exception.b(d.this.l, status));
                        return;
                    }
                    return;
                case 2:
                    b unused2 = d.this.i = b.CONNECT_DISCONNECT;
                    a.b g = timber.log.a.g("BLEBluetooth");
                    g.m(" ble发生异常断开 ---> " + d.this.O(), new Object[0]);
                    com.clj.fastble.a.o().q().j(d.this);
                    d.this.K();
                    d.this.Q();
                    d.this.H();
                    d.this.U();
                    d.this.S();
                    d.this.G();
                    d.this.m.removeCallbacksAndMessages((Object) null);
                    com.clj.fastble.data.a para = (com.clj.fastble.data.a) msg.obj;
                    boolean isActive = para.b();
                    int status2 = para.a();
                    if (d.this.a != null) {
                        d.this.a.d(isActive, d.this.k, d.this.l, status2);
                        return;
                    }
                    return;
                case 3:
                    d dVar = d.this;
                    dVar.J(dVar.k, false, d.this.a, d.this.n);
                    return;
                case 4:
                    d dVar2 = d.this;
                    dVar2.a(dVar2.s, "服务发现/收到服务发现请求");
                    if (d.this.l != null) {
                        d dVar3 = d.this;
                        dVar3.a(dVar3.s, "服务发现/启动服务发现");
                        boolean discoverServiceResult = d.this.l.discoverServices();
                        d dVar4 = d.this;
                        int z = dVar4.s;
                        dVar4.a(z, "服务发现/启动服务发现服务 结果：" + discoverServiceResult);
                        if (!discoverServiceResult) {
                            d dVar5 = d.this;
                            dVar5.a(dVar5.s, "服务发现/启动服务发现失败");
                            Message message2 = d.this.m.obtainMessage();
                            message2.what = 5;
                            d.this.m.sendMessage(message2);
                            return;
                        }
                        io.reactivex.disposables.b bVar = d.this.q;
                        if (bVar != null && !bVar.isDisposed()) {
                            d.this.q.dispose();
                        }
                        d dVar6 = d.this;
                        dVar6.o = 0;
                        dVar6.p = 5;
                        dVar6.q = io.reactivex.e.u(600, TimeUnit.MILLISECONDS).o(new b(this)).c(l.c()).I(new c(this), new a(this));
                        return;
                    }
                    d dVar7 = d.this;
                    dVar7.a(dVar7.s, "服务发现/启动服务发现失败（bluetoothGatt 为空）");
                    Message message3 = d.this.m.obtainMessage();
                    message3.what = 5;
                    d.this.m.sendMessage(message3);
                    return;
                case 5:
                    d.this.L();
                    d.this.Q();
                    d.this.H();
                    b unused3 = d.this.i = b.CONNECT_FAILURE;
                    com.clj.fastble.a.o().q().k(d.this);
                    if (d.this.a != null) {
                        d.this.a.b(d.this.k, new com.clj.fastble.exception.d("GATT discover services exception occurred!"));
                        return;
                    }
                    return;
                case 6:
                    b unused4 = d.this.i = b.CONNECT_CONNECTED;
                    boolean unused5 = d.this.j = false;
                    com.clj.fastble.a.o().q().k(d.this);
                    com.clj.fastble.a.o().q().a(d.this);
                    int status3 = ((com.clj.fastble.data.a) msg.obj).a();
                    if (d.this.a != null) {
                        d dVar8 = d.this;
                        dVar8.a(dVar8.s, "连接成功  通知上层业务层连接成功 onConnectSuccess ");
                        d.this.a.c(d.this.k, d.this.l, status3);
                        return;
                    }
                    d dVar9 = d.this;
                    dVar9.a(dVar9.s, "连接成功 但无法通知上层业务（blecallback为空）");
                    return;
                case 7:
                    d.this.L();
                    d.this.Q();
                    d.this.H();
                    b unused6 = d.this.i = b.CONNECT_FAILURE;
                    com.clj.fastble.a.o().q().k(d.this);
                    if (d.this.a != null) {
                        d.this.a.b(d.this.k, new com.clj.fastble.exception.e("BleBluetooth.MSG_CONNECT_OVER_TIME  "));
                        return;
                    }
                    return;
                default:
                    super.handleMessage(msg);
                    return;
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ org.reactivestreams.a b(Long aLong) {
            d dVar = d.this;
            int i = dVar.o + 1;
            dVar.o = i;
            if (i <= dVar.p) {
                return io.reactivex.e.w(1);
            }
            return io.reactivex.e.m(new Exception("服务发现指令->已达到了尝试的最大次数"));
        }

        /* access modifiers changed from: private */
        /* renamed from: c */
        public /* synthetic */ void d(Object aLong) {
            if (d.this.l != null) {
                d dVar = d.this;
                int z = dVar.s;
                dVar.a(z, "正在重新催促服务发现： " + d.this.l.discoverServices());
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: e */
        public /* synthetic */ void f(Throwable throwable) {
            d dVar = d.this;
            int z = dVar.s;
            dVar.a(z, "服务发现/启动服务发现失败" + throwable.toString());
            Message message = d.this.m.obtainMessage();
            message.what = 5;
            d.this.m.sendMessage(message);
        }
    }

    /* compiled from: BleBluetooth */
    public class a extends BluetoothGattCallback {
        a() {
        }

        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            com.clj.fastble.utils.a.b("BluetoothGattCallback: connect device BluetoothGattCallback：onConnectionStateChange \nstatus: " + status + 10 + "newState: " + newState + 10 + "currentThread: " + Thread.currentThread().getId());
            BluetoothGatt unused = d.this.l = gatt;
            d.this.m.removeMessages(7);
            if (newState == 2) {
                d dVar = d.this;
                int z = dVar.s;
                dVar.a(z, "设备连接成功(物理层)--->" + d.this.k.c() + " bluegatt=" + d.this.l.toString());
                Message message = d.this.m.obtainMessage();
                message.what = 4;
                d dVar2 = d.this;
                int z2 = dVar2.s;
                dVar2.a(z2, "准备开始发现服务  gattHash=" + d.this.l.toString());
                d.this.m.sendMessageDelayed(message, 600);
            } else if (newState == 0) {
                io.reactivex.disposables.b bVar = d.this.q;
                if (bVar != null && !bVar.isDisposed()) {
                    d.this.q.dispose();
                }
                if (d.this.i == b.CONNECT_CONNECTING) {
                    Message message2 = d.this.m.obtainMessage();
                    message2.what = 1;
                    message2.obj = new com.clj.fastble.data.a(status);
                    d.this.m.sendMessage(message2);
                } else if (d.this.i == b.CONNECT_CONNECTED) {
                    Message message3 = d.this.m.obtainMessage();
                    message3.what = 2;
                    com.clj.fastble.data.a para = new com.clj.fastble.data.a(status);
                    para.c(d.this.j);
                    message3.obj = para;
                    d.this.m.sendMessage(message3);
                }
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            d dVar = d.this;
            int z = dVar.s;
            dVar.a(z, "服务发现回调  onServicesDiscovered statue=" + status + "   gatt=" + gatt.toString() + "   bluegatt=" + d.this.l.toString());
            StringBuilder sb = new StringBuilder();
            sb.append("BluetoothGattCallback：onServicesDiscovered \nstatus: ");
            sb.append(status);
            sb.append(10);
            sb.append("currentThread: ");
            sb.append(Thread.currentThread().getId());
            com.clj.fastble.utils.a.b(sb.toString());
            BluetoothGatt unused = d.this.l = gatt;
            io.reactivex.disposables.b bVar = d.this.q;
            if (bVar != null && !bVar.isDisposed()) {
                d.this.q.dispose();
            }
            if (status == 0) {
                d dVar2 = d.this;
                int z2 = dVar2.s;
                dVar2.a(z2, "服务发现 onServicesDiscovered GATT_SUCCESS   gatt=" + gatt.toString() + "   blueGatt=" + d.this.l);
                Message message = d.this.m.obtainMessage();
                message.what = 6;
                message.obj = new com.clj.fastble.data.a(status);
                d.this.m.sendMessage(message);
                return;
            }
            d dVar3 = d.this;
            int z3 = dVar3.s;
            dVar3.a(z3, "服务发现  onServicesDiscovered 发现失败 statues=" + status + "  gatt=" + gatt.toString() + "   blueGatt=" + d.this.l);
            Message message2 = d.this.m.obtainMessage();
            message2.what = 5;
            d.this.m.sendMessage(message2);
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Handler handler;
            Handler handler2;
            super.onCharacteristicChanged(gatt, characteristic);
            d dVar = d.this;
            int g = dVar.t;
            dVar.a(g, "LdsConnectDevice  收到写入消息通知 characterUuid=" + characteristic.getUuid() + "   value=" + com.leedarson.base.utils.e.a(characteristic.getValue()));
            for (Map.Entry entry : d.this.e.entrySet()) {
                Object callback = entry.getValue();
                if (callback instanceof e) {
                    e bleNotifyCallback = (e) callback;
                    if (characteristic.getUuid().toString().equalsIgnoreCase(bleNotifyCallback.getKey()) && (handler2 = bleNotifyCallback.getHandler()) != null) {
                        Message message = handler2.obtainMessage();
                        message.what = 19;
                        message.obj = bleNotifyCallback;
                        Bundle bundle = new Bundle();
                        bundle.putByteArray("notify_value", characteristic.getValue());
                        message.setData(bundle);
                        handler2.sendMessage(message);
                    }
                }
            }
            for (Map.Entry entry2 : d.this.f.entrySet()) {
                Object callback2 = entry2.getValue();
                if (callback2 instanceof com.clj.fastble.callback.c) {
                    com.clj.fastble.callback.c bleIndicateCallback = (com.clj.fastble.callback.c) callback2;
                    if (characteristic.getUuid().toString().equalsIgnoreCase(bleIndicateCallback.getKey()) && (handler = bleIndicateCallback.getHandler()) != null) {
                        Message message2 = handler.obtainMessage();
                        message2.what = 35;
                        message2.obj = bleIndicateCallback;
                        Bundle bundle2 = new Bundle();
                        bundle2.putByteArray("indicate_value", characteristic.getValue());
                        message2.setData(bundle2);
                        handler.sendMessage(message2);
                    }
                }
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Handler handler;
            Handler handler2;
            super.onDescriptorWrite(gatt, descriptor, status);
            d dVar = d.this;
            dVar.a(-1200, "onDescriptorWrite  --> 往远程写数据DescriptorWirte： gatt=" + gatt.toString() + "  descriptor=" + descriptor.toString() + "   status=" + status);
            for (Map.Entry entry : d.this.e.entrySet()) {
                Object callback = entry.getValue();
                if (callback instanceof e) {
                    e bleNotifyCallback = (e) callback;
                    if (descriptor.getCharacteristic().getUuid().toString().equalsIgnoreCase(bleNotifyCallback.getKey()) && (handler2 = bleNotifyCallback.getHandler()) != null) {
                        Message message = handler2.obtainMessage();
                        message.what = 18;
                        message.obj = bleNotifyCallback;
                        Bundle bundle = new Bundle();
                        bundle.putInt("notify_status", status);
                        message.setData(bundle);
                        handler2.sendMessage(message);
                    }
                }
            }
            for (Map.Entry entry2 : d.this.f.entrySet()) {
                Object callback2 = entry2.getValue();
                if (callback2 instanceof com.clj.fastble.callback.c) {
                    com.clj.fastble.callback.c bleIndicateCallback = (com.clj.fastble.callback.c) callback2;
                    if (descriptor.getCharacteristic().getUuid().toString().equalsIgnoreCase(bleIndicateCallback.getKey()) && (handler = bleIndicateCallback.getHandler()) != null) {
                        Message message2 = handler.obtainMessage();
                        message2.what = 34;
                        message2.obj = bleIndicateCallback;
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("indicate_status", status);
                        message2.setData(bundle2);
                        handler.sendMessage(message2);
                    }
                }
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Handler handler;
            super.onCharacteristicWrite(gatt, characteristic, status);
            d dVar = d.this;
            dVar.a(-1200, d.this.k.c() + " 写向远程的 character数据   " + characteristic.getUuid() + "    value=" + com.leedarson.base.utils.e.a(characteristic.getValue()) + "  status=" + status + "  gatt=" + gatt.toString());
            for (Map.Entry entry : d.this.g.entrySet()) {
                Object callback = entry.getValue();
                if (callback instanceof h) {
                    h bleWriteCallback = (h) callback;
                    if (characteristic.getUuid().toString().equalsIgnoreCase(bleWriteCallback.getKey()) && (handler = bleWriteCallback.getHandler()) != null) {
                        Message message = handler.obtainMessage();
                        message.what = 50;
                        message.obj = bleWriteCallback;
                        Bundle bundle = new Bundle();
                        bundle.putInt("write_status", status);
                        bundle.putByteArray("write_value", characteristic.getValue());
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                }
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Handler handler;
            super.onCharacteristicRead(gatt, characteristic, status);
            d dVar = d.this;
            int k = dVar.u;
            dVar.a(k, "从远程设备读取数据：  uuId=" + characteristic.getUuid() + "  value=" + com.leedarson.base.utils.e.a(characteristic.getValue()) + "  status=" + status);
            for (Map.Entry entry : d.this.h.entrySet()) {
                Object callback = entry.getValue();
                if (callback instanceof f) {
                    f bleReadCallback = (f) callback;
                    if (characteristic.getUuid().toString().equalsIgnoreCase(bleReadCallback.getKey()) && (handler = bleReadCallback.getHandler()) != null) {
                        Message message = handler.obtainMessage();
                        message.what = 66;
                        message.obj = bleReadCallback;
                        Bundle bundle = new Bundle();
                        bundle.putInt("read_status", status);
                        bundle.putByteArray("read_value", characteristic.getValue());
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                }
            }
        }

        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            Handler handler;
            super.onReadRemoteRssi(gatt, rssi, status);
            if (d.this.b != null && (handler = d.this.b.getHandler()) != null) {
                Message message = handler.obtainMessage();
                message.what = 82;
                message.obj = d.this.b;
                Bundle bundle = new Bundle();
                bundle.putInt("rssi_status", status);
                bundle.putInt("rssi_value", rssi);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }

        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            Handler handler;
            super.onMtuChanged(gatt, mtu, status);
            timber.log.a.i("SUFUN.onMtuChanged.BleBluetooth   setMtu --> mtu=" + mtu + "   status=" + status, new Object[0]);
            if (!(d.this.c == null || (handler = d.this.c.getHandler()) == null)) {
                Message message = handler.obtainMessage();
                message.what = 98;
                message.obj = d.this.c;
                Bundle bundle = new Bundle();
                bundle.putInt("mtu_status", status);
                bundle.putInt("mtu_value", mtu);
                message.setData(bundle);
                handler.sendMessage(message);
            }
            if (d.this.d == null) {
                return;
            }
            if (status == 0) {
                d.this.d.onMtuChanged(mtu);
                return;
            }
            com.clj.fastble.callback.d p = d.this.d;
            p.onSetMTUFailure(new com.clj.fastble.exception.d("setUp mtu fail   gatt=" + gatt.toString() + "    mtu=" + mtu + "   status=" + status));
        }
    }

    /* access modifiers changed from: private */
    public void a(int bzcode, String msg) {
        com.clj.fastble.callback.b bVar = this.a;
        if (bVar != null) {
            bVar.a(bzcode, msg);
        }
        a.b g2 = timber.log.a.g("BleBluetooth");
        g2.m("LdsConnectDevice.BleBluetooth " + msg, new Object[0]);
    }
}
