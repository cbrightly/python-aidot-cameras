package com.google.chip.chiptool.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.os.ParcelUuid;
import android.util.Log;
import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.base.d;
import com.leedarson.serviceimpl.k;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import kotlinx.coroutines.channels.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BluetoothManager.kt */
public final class BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2$1$scannerObserver$1 implements d {
    final /* synthetic */ u<AdvertisingDevice> $$this$callbackFlow;
    final /* synthetic */ int $discriminator;
    final /* synthetic */ boolean $hasShortDis;
    final /* synthetic */ BluetoothManager this$0;

    BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2$1$scannerObserver$1(boolean $hasShortDis2, int $discriminator2, BluetoothManager $receiver, u<? super AdvertisingDevice> $$this$callbackFlow2) {
        this.$hasShortDis = $hasShortDis2;
        this.$discriminator = $discriminator2;
        this.this$0 = $receiver;
        this.$$this$callbackFlow = $$this$callbackFlow2;
    }

    public void onLeScan(@Nullable BluetoothDevice bluetoothDevice, int rssi, @Nullable byte[] scanRecord, @Nullable ScanRecord record) {
        byte[] disBytes;
        BluetoothDevice device = bluetoothDevice;
        if (record == null) {
            int i = rssi;
            byte[] bArr = scanRecord;
            return;
        }
        boolean z = this.$hasShortDis;
        int i2 = this.$discriminator;
        BluetoothManager bluetoothManager = this.this$0;
        u producerScope = this.$$this$callbackFlow;
        ScanRecord scanRecord2 = record;
        byte[] serviceData = record.getServiceData().get(ParcelUuid.fromString("0000FFF6-0000-1000-8000-00805F9B34FB"));
        if (serviceData == null) {
            int i3 = rssi;
            byte[] bArr2 = scanRecord;
            return;
        }
        byte[] bArr3 = serviceData;
        String str = null;
        k.m(k.a, kotlin.jvm.internal.k.l("serviceData:", e.a(serviceData)), (String) null, 2, (Object) null);
        byte[] bArr4 = null;
        if (z) {
            if (serviceData[2] == i2) {
                bArr4 = 1;
            }
            disBytes = bArr4;
        } else {
            byte[] disBytes2 = bluetoothManager.getServiceData(i2);
            if (serviceData[2] == disBytes2[2] && serviceData[1] == disBytes2[1]) {
                bArr4 = 1;
            }
            disBytes = bArr4;
        }
        if (disBytes != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("匹配到 Matter 设备 Addr: ");
            sb.append(device == null ? null : device.getAddress());
            sb.append(", Name ");
            if (device != null) {
                str = device.getName();
            }
            sb.append(str);
            sb.append(",scanRecord:");
            sb.append(e.a(scanRecord));
            bluetoothManager.logd(sb.toString());
            if (producerScope.getChannel().F()) {
                Log.w("chip.BluetoothManager", "Bluetooth device was scanned, but channel is already closed");
                int i4 = rssi;
                byte[] bArr5 = scanRecord;
                return;
            }
            producerScope.offer(new AdvertisingDevice(device, rssi, scanRecord));
            return;
        }
        int i5 = rssi;
        byte[] bArr6 = scanRecord;
    }

    public void onScanFail(int errorCode) {
        k.e(k.a, kotlin.jvm.internal.k.l("onScanFail:", Integer.valueOf(errorCode)), (String) null, 2, (Object) null);
    }

    @NotNull
    public String onFromBz() {
        return "matter ble scan";
    }
}
