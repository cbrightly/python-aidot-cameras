package com.leedarson.serviceimpl.shake.blectrl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.shake.R$id;
import com.leedarson.serviceimpl.shake.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;
import java.util.UUID;

public class BleCtrlAct extends BaseActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    BluetoothGattService A4;
    BluetoothGattCharacteristic B4;
    BluetoothGattDescriptor C4;
    BluetoothGattCharacteristic D4;
    private TextView a1;
    private SeekBar a2;
    private String p0 = "BleCtrlAct";
    private Switch p1;
    /* access modifiers changed from: private */
    public TextView p2;
    /* access modifiers changed from: private */
    public e p3 = new e();
    /* access modifiers changed from: private */
    public String p4 = "";
    BluetoothGatt z4;

    static /* synthetic */ void j0(BleCtrlAct x0, String x1) {
        Class[] clsArr = {BleCtrlAct.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8709, clsArr, Void.TYPE).isSupported) {
            x0.X(x1);
        }
    }

    static /* synthetic */ void k0(BleCtrlAct x0, BluetoothDevice x1, String x2) {
        Class[] clsArr = {BleCtrlAct.class, BluetoothDevice.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 8710, clsArr, Void.TYPE).isSupported) {
            x0.Z(x1, x2);
        }
    }

    static /* synthetic */ void l0(BleCtrlAct x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8711, new Class[]{BleCtrlAct.class}, Void.TYPE).isSupported) {
            x0.a0();
        }
    }

    public int O() {
        return R$layout.layout_ble_ctrl_demo;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8696, new Class[0], Void.TYPE).isSupported) {
            this.p2 = (TextView) findViewById(R$id.tv_dim);
            this.a1 = (TextView) findViewById(R$id.tv_log);
            this.a2 = (SeekBar) findViewById(R$id.sk_dim);
            this.p1 = (Switch) findViewById(R$id.sw_switch);
            findViewById(R$id.btnConnect).setOnClickListener(new c(this));
            findViewById(R$id.btnRegisterNotify).setOnClickListener(new b(this));
            findViewById(R$id.btnSendData).setOnClickListener(new a(this));
            this.p1.setOnCheckedChangeListener(new a());
            this.a2.setOnSeekBarChangeListener(new b());
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: m0 */
    public /* synthetic */ void n0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8708, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        b0();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: q0 */
    public /* synthetic */ void s0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8707, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        a0();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: t0 */
    public /* synthetic */ void u0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8706, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        c0();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class a implements CompoundButton.OnCheckedChangeListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            int i = 0;
            if (PatchProxy.proxy(new Object[]{compoundButton, new Byte(isChecked ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8712, new Class[]{CompoundButton.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(compoundButton);
                return;
            }
            CompoundButton compoundButton2 = compoundButton;
            e e0 = BleCtrlAct.this.p3;
            if (isChecked) {
                i = 1;
            }
            e0.d(i);
            SensorsDataAutoTrackHelper.trackViewOnClick(compoundButton);
        }
    }

    public class b implements SeekBar.OnSeekBarChangeListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean z) {
            if (!PatchProxy.proxy(new Object[]{seekBar, new Integer(progress), new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8713, new Class[]{SeekBar.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
                BleCtrlAct.this.v0("onProgressChanged");
                BleCtrlAct.this.p2.setText(String.valueOf(progress));
                BleCtrlAct.this.p3.a(progress);
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @SensorsDataInstrumented
        public void onStopTrackingTouch(SeekBar seekBar) {
            SeekBar seekBar2 = seekBar;
            SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
        }
    }

    private void X(String desc) {
        if (!PatchProxy.proxy(new Object[]{desc}, this, changeQuickRedirect, false, 8697, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.i("BleCtrlAct  desc=" + desc, new Object[0]);
        }
    }

    public class c extends ScanCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ BluetoothLeScanner a;

        c(BluetoothLeScanner bluetoothLeScanner) {
            this.a = bluetoothLeScanner;
        }

        public void onScanResult(int callbackType, ScanResult result) {
            Object[] objArr = {new Integer(callbackType), result};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8714, new Class[]{Integer.TYPE, ScanResult.class}, Void.TYPE).isSupported) {
                super.onScanResult(callbackType, result);
                String scanRecordHexStr = e.a(result.getScanRecord().getBytes());
                if (scanRecordHexStr.contains(BleCtrlAct.this.p4)) {
                    this.a.stopScan(new a());
                    BleCtrlAct.k0(BleCtrlAct.this, result.getDevice(), scanRecordHexStr);
                }
            }
        }

        public class a extends ScanCallback {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void onScanResult(int callbackType, ScanResult result) {
                Object[] objArr = {new Integer(callbackType), result};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8717, new Class[]{Integer.TYPE, ScanResult.class}, Void.TYPE).isSupported) {
                    super.onScanResult(callbackType, result);
                    BleCtrlAct.j0(BleCtrlAct.this, "结束扫描：onScanResult");
                }
            }

            public void onBatchScanResults(List<ScanResult> results) {
                if (!PatchProxy.proxy(new Object[]{results}, this, changeQuickRedirect, false, 8718, new Class[]{List.class}, Void.TYPE).isSupported) {
                    super.onBatchScanResults(results);
                    BleCtrlAct.j0(BleCtrlAct.this, "结束扫描：onBatchScanResults");
                }
            }

            public void onScanFailed(int errorCode) {
                Object[] objArr = {new Integer(errorCode)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8719, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                    super.onScanFailed(errorCode);
                    BleCtrlAct bleCtrlAct = BleCtrlAct.this;
                    BleCtrlAct.j0(bleCtrlAct, "结束扫描：onScanFailed  errorCode=" + errorCode);
                }
            }
        }

        public void onBatchScanResults(List<ScanResult> results) {
            if (!PatchProxy.proxy(new Object[]{results}, this, changeQuickRedirect, false, 8715, new Class[]{List.class}, Void.TYPE).isSupported) {
                super.onBatchScanResults(results);
            }
        }

        public void onScanFailed(int errorCode) {
            Object[] objArr = {new Integer(errorCode)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8716, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                super.onScanFailed(errorCode);
            }
        }
    }

    private void b0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8698, new Class[0], Void.TYPE).isSupported) {
            BluetoothLeScanner mLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
            mLeScanner.startScan(new c(mLeScanner));
        }
    }

    public class d extends BluetoothGattCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            Object[] objArr = {gatt, new Integer(txPhy), new Integer(rxPhy), new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {BluetoothGatt.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8720, clsArr, Void.TYPE).isSupported) {
                super.onPhyUpdate(gatt, txPhy, rxPhy, status);
                BleCtrlAct bleCtrlAct = BleCtrlAct.this;
                BleCtrlAct.j0(bleCtrlAct, "onPhyUpdate gatt=" + gatt.toString() + JustifyTextView.TWO_CHINESE_BLANK);
            }
        }

        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            Object[] objArr = {gatt, new Integer(txPhy), new Integer(rxPhy), new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {BluetoothGatt.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8721, clsArr, Void.TYPE).isSupported) {
                super.onPhyRead(gatt, txPhy, rxPhy, status);
            }
        }

        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Object[] objArr = {gatt, new Integer(status), new Integer(newState)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8722, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
                super.onConnectionStateChange(gatt, status, newState);
                BleCtrlAct bleCtrlAct = BleCtrlAct.this;
                BleCtrlAct.j0(bleCtrlAct, "onConnectionStateChange  newStatus" + newState);
                if (newState == 2) {
                    BleCtrlAct.j0(BleCtrlAct.this, "执行服务发现");
                    gatt.requestMtu(256);
                }
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (!PatchProxy.proxy(new Object[]{gatt, new Integer(status)}, this, changeQuickRedirect, false, 8723, new Class[]{BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                super.onServicesDiscovered(gatt, status);
                BleCtrlAct bleCtrlAct = BleCtrlAct.this;
                BleCtrlAct.j0(bleCtrlAct, "服务发现已回执..... " + status);
                BleCtrlAct.l0(BleCtrlAct.this);
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Object[] objArr = {gatt, characteristic, new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8724, new Class[]{BluetoothGatt.class, BluetoothGattCharacteristic.class, Integer.TYPE}, Void.TYPE).isSupported) {
                super.onCharacteristicRead(gatt, characteristic, status);
                BleCtrlAct.j0(BleCtrlAct.this, "读取回执 onCharacteristicRead");
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Object[] objArr = {gatt, characteristic, new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8725, new Class[]{BluetoothGatt.class, BluetoothGattCharacteristic.class, Integer.TYPE}, Void.TYPE).isSupported) {
                super.onCharacteristicWrite(gatt, characteristic, status);
                BleCtrlAct.j0(BleCtrlAct.this, "写入回执 onCharacteristicWrite");
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Class[] clsArr = {BluetoothGatt.class, BluetoothGattCharacteristic.class};
            if (!PatchProxy.proxy(new Object[]{gatt, characteristic}, this, changeQuickRedirect, false, 8726, clsArr, Void.TYPE).isSupported) {
                super.onCharacteristicChanged(gatt, characteristic);
                BleCtrlAct.j0(BleCtrlAct.this, "被写入 onCharacteristicChanged");
            }
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Object[] objArr = {gatt, descriptor, new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8727, new Class[]{BluetoothGatt.class, BluetoothGattDescriptor.class, Integer.TYPE}, Void.TYPE).isSupported) {
                super.onDescriptorRead(gatt, descriptor, status);
                BleCtrlAct.j0(BleCtrlAct.this, "读取 onDescriptorRead 回执");
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Object[] objArr = {gatt, descriptor, new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8728, new Class[]{BluetoothGatt.class, BluetoothGattDescriptor.class, Integer.TYPE}, Void.TYPE).isSupported) {
                super.onDescriptorWrite(gatt, descriptor, status);
                BleCtrlAct.j0(BleCtrlAct.this, "写入 onDescriptorWrite");
            }
        }

        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            if (!PatchProxy.proxy(new Object[]{gatt, new Integer(status)}, this, changeQuickRedirect, false, 8729, new Class[]{BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                super.onReliableWriteCompleted(gatt, status);
                BleCtrlAct.j0(BleCtrlAct.this, "写入 onReliableWriteCompleted");
            }
        }

        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            Object[] objArr = {gatt, new Integer(rssi), new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8730, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
                super.onReadRemoteRssi(gatt, rssi, status);
                BleCtrlAct.j0(BleCtrlAct.this, "写入 onReadRemoteRssi");
            }
        }

        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            Object[] objArr = {gatt, new Integer(mtu), new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8731, new Class[]{BluetoothGatt.class, cls, cls}, Void.TYPE).isSupported) {
                super.onMtuChanged(gatt, mtu, status);
                BleCtrlAct bleCtrlAct = BleCtrlAct.this;
                BleCtrlAct.j0(bleCtrlAct, "MTU 协商完毕" + mtu);
                gatt.discoverServices();
            }
        }
    }

    private void Z(BluetoothDevice device, String str) {
        if (!PatchProxy.proxy(new Object[]{device, str}, this, changeQuickRedirect, false, 8699, new Class[]{BluetoothDevice.class, String.class}, Void.TYPE).isSupported) {
            device.connectGatt(BaseApplication.b(), false, new d());
        }
    }

    private void a0() {
        boolean z = false;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8700, new Class[0], Void.TYPE).isSupported) {
            BluetoothGattService service = this.z4.getService(UUID.fromString("00001000-1115-1000-4c44-5341524e4f4f"));
            this.A4 = service;
            this.B4 = service.getCharacteristic(UUID.fromString("00001002-1115-1000-4c44-5341524e4f4f"));
            this.D4 = this.A4.getCharacteristic(UUID.fromString("00001001-1115-1000-4c44-5341524e4f4f"));
            StringBuilder sb = new StringBuilder();
            sb.append("  notifyCharacter==null?");
            sb.append(this.B4 == null);
            sb.append("    writeCharacter==null?");
            if (this.D4 == null) {
                z = true;
            }
            sb.append(z);
            X(sb.toString());
            boolean enableNotifyCationResult = this.z4.setCharacteristicNotification(this.B4, true);
            this.C4 = this.B4.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
            X("enableNotifyCationResult=" + enableNotifyCationResult + "  setNotifyDescriptorResult=" + this.B4.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) + " setNotifyDescriptor=" + this.z4.writeDescriptor(this.C4));
        }
    }

    private void c0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8701, new Class[0], Void.TYPE).isSupported) {
            this.D4.setValue(Y("0111000001106E67687838717567336C6B6C356F7964F1047A786E28872DB7B08BB2BCACDDE6"));
            this.z4.writeCharacteristic(this.D4);
        }
    }

    public byte[] Y(String src) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{src}, this, changeQuickRedirect, false, 8702, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] ret = new byte[(src.length() / 2)];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < src.length() / 2; i++) {
            ret[i] = w0(tmp[i * 2], tmp[(i * 2) + 1]);
        }
        return ret;
    }

    public byte w0(byte src0, byte src1) {
        Object[] objArr = {new Byte(src0), new Byte(src1)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Byte.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8703, new Class[]{cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Byte) proxy.result).byteValue();
        }
        byte _b0 = (byte) (Byte.decode("0x" + new String(new byte[]{src0})).byteValue() << 4);
        return (byte) (_b0 ^ Byte.decode("0x" + new String(new byte[]{src1})).byteValue());
    }

    public void R() {
    }

    public void v0(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 8704, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g(this.p0).a(msg, new Object[0]);
        }
    }
}
