package chiptool.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import chiptool.bluetooth.BLEProxy;
import com.leedarson.serviceimpl.k;
import org.jetbrains.annotations.Nullable;

/* compiled from: BLEProxy.kt */
public final class BLEProxy$connect$1 extends BluetoothGattCallback {
    final /* synthetic */ BLEProxy this$0;

    BLEProxy$connect$1(BLEProxy $receiver) {
        this.this$0 = $receiver;
    }

    public void onConnectionStateChange(@Nullable BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        this.this$0.handler.removeMessages(this.this$0.getWHAT_CONNECT_TIMEOUT());
        this.this$0.bluetoothGatt = gatt;
        BluetoothGattCallback externalCallback = this.this$0.getExternalCallback();
        if (externalCallback != null) {
            externalCallback.onConnectionStateChange(gatt, status, newState);
        }
        if (newState == 2) {
            k.a.a(kotlin.jvm.internal.k.l("BLE 连接成功...", this.this$0.getBleMac()), this.this$0.getTAG());
            this.this$0.startServicesDiscovering();
            return;
        }
        BLEProxy bLEProxy = this.this$0;
        bLEProxy.onDisconnect("BLE 连接失败,status:" + status + ",newState:" + newState);
    }

    public void onServicesDiscovered(@Nullable BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        k.a.a("onServicesDiscovered, remove WHAT_SERVICE_DISCOVER_TIMEOUT", this.this$0.getTAG());
        this.this$0.handler.removeMessages(this.this$0.getWHAT_SERVICE_DISCOVER_TIMEOUT());
        if (gatt != null) {
            gatt.requestMtu(247);
        }
    }

    public void onMtuChanged(@Nullable BluetoothGatt gatt, int mtu, int status) {
        super.onMtuChanged(gatt, mtu, status);
        k kVar = k.a;
        kVar.a("onMtuChanged mtu:" + mtu + ", status:" + status, this.this$0.getTAG());
        if (status == 0) {
            kVar.a("mtu设置成功", this.this$0.getTAG());
        } else {
            kVar.a("mtu设置失败", this.this$0.getTAG());
        }
        BluetoothGattCallback externalCallback = this.this$0.getExternalCallback();
        if (externalCallback != null) {
            externalCallback.onMtuChanged(gatt, mtu, status);
        }
        BLEProxy.BleProxyListener access$getProxyListener$p = this.this$0.proxyListener;
        if (access$getProxyListener$p != null) {
            access$getProxyListener$p.onConnected(this.this$0.bluetoothGatt);
        }
        this.this$0.currentMode = BLEProxy.ActionMode.WORKING;
    }

    public void onCharacteristicChanged(@Nullable BluetoothGatt gatt, @Nullable BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        BluetoothGattCallback externalCallback = this.this$0.getExternalCallback();
        if (externalCallback != null) {
            externalCallback.onCharacteristicChanged(gatt, characteristic);
        }
    }

    public void onCharacteristicRead(@Nullable BluetoothGatt gatt, @Nullable BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        BluetoothGattCallback externalCallback = this.this$0.getExternalCallback();
        if (externalCallback != null) {
            externalCallback.onCharacteristicRead(gatt, characteristic, status);
        }
    }

    public void onCharacteristicWrite(@Nullable BluetoothGatt gatt, @Nullable BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        BluetoothGattCallback externalCallback = this.this$0.getExternalCallback();
        if (externalCallback != null) {
            externalCallback.onCharacteristicWrite(gatt, characteristic, status);
        }
    }

    public void onDescriptorRead(@Nullable BluetoothGatt gatt, @Nullable BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorRead(gatt, descriptor, status);
        BluetoothGattCallback externalCallback = this.this$0.getExternalCallback();
        if (externalCallback != null) {
            externalCallback.onDescriptorRead(gatt, descriptor, status);
        }
    }

    public void onDescriptorWrite(@Nullable BluetoothGatt gatt, @Nullable BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        BluetoothGattCallback externalCallback = this.this$0.getExternalCallback();
        if (externalCallback != null) {
            externalCallback.onDescriptorWrite(gatt, descriptor, status);
        }
    }

    public void onReadRemoteRssi(@Nullable BluetoothGatt gatt, int rssi, int status) {
        super.onReadRemoteRssi(gatt, rssi, status);
        BluetoothGattCallback externalCallback = this.this$0.getExternalCallback();
        if (externalCallback != null) {
            externalCallback.onReadRemoteRssi(gatt, rssi, status);
        }
    }

    public void onReliableWriteCompleted(@Nullable BluetoothGatt gatt, int status) {
        super.onReliableWriteCompleted(gatt, status);
    }
}
