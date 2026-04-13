package com.telink.ble.mesh.core.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.Nullable;
import com.leedarson.serviceimpl.base.c;
import com.leedarson.serviceimpl.base.d;
import com.leedarson.serviceimpl.elkstrays.b;
import com.leedarson.serviceimpl.reporters.AutoConnectDeviceStepBean;
import com.leedarson.serviceimpl.reporters.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import com.telink.ble.mesh.foundation.MeshController;
import java.util.LinkedHashSet;
import java.util.Set;
import meshsdk.MeshScanLog;
import meshsdk.SIGMesh;

public class BleScanner implements d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ScannerCallback a;
    /* access modifiers changed from: private */
    public LeScanFilter b;
    private boolean c = false;
    /* access modifiers changed from: private */
    public MeshController d;
    private ScanTimeOutTask e = new ScanTimeOutTask();
    private Handler f;
    private Set<AdvertisingDevice> g = new LinkedHashSet();

    public interface ScannerCallback {
        void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr);

        void onScanFail(int i);
    }

    private BleScanner() {
    }

    public void d(ScannerCallback scannerCallback) {
        this.a = scannerCallback;
    }

    public BleScanner(HandlerThread handlerThread, MeshController meshController) {
        this.d = meshController;
        this.f = new Handler(handlerThread.getLooper());
    }

    private void c(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord) {
        ScannerCallback scannerCallback;
        Object[] objArr = {bluetoothDevice, new Integer(rssi), scanRecord};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12262, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            if (scanRecord != null && (scannerCallback = this.a) != null) {
                scannerCallback.onLeScan(bluetoothDevice, rssi, scanRecord);
            }
        }
    }

    public synchronized void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12263, new Class[0], Void.TYPE).isSupported) {
            Set<AdvertisingDevice> set = this.g;
            if (set != null) {
                set.clear();
            }
            h();
            c.k().B(this);
        }
    }

    public synchronized void e(@Nullable LeScanFilter leScanFilter) {
        if (!PatchProxy.proxy(new Object[]{leScanFilter}, this, changeQuickRedirect, false, 12264, new Class[]{LeScanFilter.class}, Void.TYPE).isSupported) {
            MeshController meshController = this.d;
            if (meshController != null) {
                meshController.j0();
            }
            Set<AdvertisingDevice> set = this.g;
            if (set != null) {
                set.clear();
            }
            this.c = false;
            if (leScanFilter != null) {
                this.b = leScanFilter;
            }
            MeshScanLog.d("BleScanner 开启10s超时计时:" + Thread.currentThread().getName());
            f(10000);
            c.k().x(this);
        }
    }

    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord, ScanRecord scanRecord2) {
        String[] strArr;
        int i = 0;
        Object[] objArr = {device, new Integer(rssi), scanRecord, scanRecord2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12265, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class, ScanRecord.class}, Void.TYPE).isSupported) {
            this.c = true;
            if (!(this.b == null || (strArr = this.b.c) == null || strArr.length == 0)) {
                int length = strArr.length;
                while (i < length) {
                    if (!strArr[i].equalsIgnoreCase(device.getAddress())) {
                        i++;
                    } else {
                        return;
                    }
                }
            }
            c(device, rssi, scanRecord);
        }
    }

    public void onScanFail(int errorCode) {
        ScannerCallback scannerCallback;
        Object[] objArr = {new Integer(errorCode)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12266, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (scannerCallback = this.a) != null) {
            scannerCallback.onScanFail(errorCode);
        }
    }

    public String onFromBz() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12267, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("blemesh ");
        MeshController meshController = this.d;
        sb.append(meshController != null ? meshController.t0() : "");
        sb.append(" scan");
        return sb.toString();
    }

    private void f(long timeout) {
        Object[] objArr = {new Long(timeout)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12268, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            this.f.removeCallbacks(this.e);
            this.f.postDelayed(this.e, timeout);
        }
    }

    private void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12269, new Class[0], Void.TYPE).isSupported) {
            this.f.removeCallbacks(this.e);
        }
    }

    public final class ScanTimeOutTask implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        ScanTimeOutTask() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12270, new Class[0], Void.TYPE).isSupported) {
                if (MeshController.Mode.MODE_AUTO_CONNECT == BleScanner.this.d.s0() && !SIGMesh.getInstance().hasConnected()) {
                    b.a("autoConnect埋点:10s未扫描到设备");
                    StringBuilder sb = new StringBuilder();
                    e eVar = e.CODE_SCAN_NOT_FOUND_DEVICE;
                    sb.append(eVar.getDesc());
                    sb.append(",蓝牙");
                    sb.append(BluetoothAdapter.getDefaultAdapter().isEnabled() ? "开启" : "关闭");
                    com.leedarson.serviceimpl.reporters.c.c(new AutoConnectDeviceStepBean(sb.toString(), eVar.getCode()));
                }
                MeshScanLog.d("BleScanner  开启10s扫描到了，准备移除扫描监听器: " + BleScanner.this.onFromBz() + ",继续扫描:" + Thread.currentThread().getName());
                BleScanner.this.g();
                BleScanner bleScanner = BleScanner.this;
                bleScanner.e(bleScanner.b);
            }
        }
    }
}
