package com.google.chip.chiptool.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import com.google.chip.chiptool.ChipClient;
import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.listener.a;
import java.util.Locale;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import kotlinx.coroutines.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BluetoothManager.kt */
public final class BluetoothManager$getBluetoothGattCallback$1 extends BluetoothGattCallback {
    final /* synthetic */ Context $context;
    final /* synthetic */ n<BluetoothGatt> $continuation;
    @NotNull
    private final n<BluetoothGatt> coroutineContinuation;
    final /* synthetic */ BluetoothManager this$0;
    private final BluetoothGattCallback wrappedCallback;

    BluetoothManager$getBluetoothGattCallback$1(Context $context2, n<? super BluetoothGatt> $continuation2, BluetoothManager $receiver) {
        this.$context = $context2;
        this.$continuation = $continuation2;
        this.this$0 = $receiver;
        this.wrappedCallback = ChipClient.INSTANCE.getAndroidChipPlatform($context2).getBLEManager().getCallback();
        this.coroutineContinuation = $continuation2;
    }

    public void onConnectionStateChange(@Nullable BluetoothGatt gatt, int status, int newState) {
        BluetoothDevice device;
        super.onConnectionStateChange(gatt, status, newState);
        BluetoothManager bluetoothManager = this.this$0;
        StringBuilder sb = new StringBuilder();
        String str = null;
        if (!(gatt == null || (device = gatt.getDevice()) == null)) {
            str = device.getName();
        }
        sb.append(str);
        sb.append(".onConnectionStateChange status = ");
        sb.append(status);
        sb.append(", newState=");
        sb.append(newState);
        bluetoothManager.logd(sb.toString());
        this.wrappedCallback.onConnectionStateChange(gatt, status, newState);
    }

    public void onServicesDiscovered(@Nullable BluetoothGatt gatt, int status) {
        BluetoothDevice device;
        BluetoothManager bluetoothManager = this.this$0;
        StringBuilder sb = new StringBuilder();
        String str = null;
        if (!(gatt == null || (device = gatt.getDevice()) == null)) {
            str = device.getName();
        }
        sb.append(str);
        sb.append(".onServicesDiscovered status = ");
        sb.append(status);
        bluetoothManager.logd(sb.toString());
        this.wrappedCallback.onServicesDiscovered(gatt, status);
        this.this$0.logd("chip.BluetoothManager|onServicesDiscovered Services Discovered");
        if (gatt != null) {
            gatt.requestMtu(247);
        }
    }

    public void onMtuChanged(@Nullable BluetoothGatt gatt, int mtu, int status) {
        BluetoothDevice device;
        BluetoothManager bluetoothManager = this.this$0;
        String str = null;
        if (!(gatt == null || (device = gatt.getDevice()) == null)) {
            str = device.getName();
        }
        bluetoothManager.logd(k.l(str, ".onMtuChanged: connecting to CHIP device"));
        super.onMtuChanged(gatt, mtu, status);
        this.wrappedCallback.onMtuChanged(gatt, mtu, status);
    }

    public void onCharacteristicChanged(@NotNull BluetoothGatt gatt, @NotNull BluetoothGattCharacteristic characteristic) {
        k.e(gatt, "gatt");
        k.e(characteristic, "characteristic");
        String uuid = characteristic.getUuid().toString();
        k.d(uuid, "characteristic.uuid.toString()");
        Locale locale = Locale.getDefault();
        k.d(locale, "getDefault()");
        String upperCase = uuid.toUpperCase(locale);
        k.d(upperCase, "(this as java.lang.String).toUpperCase(locale)");
        Locale locale2 = Locale.getDefault();
        k.d(locale2, "getDefault()");
        String upperCase2 = "a00b".toUpperCase(locale2);
        k.d(upperCase2, "(this as java.lang.String).toUpperCase(locale)");
        if (x.S(upperCase, upperCase2, false, 2, (Object) null)) {
            com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
            com.leedarson.serviceimpl.k.e(kVar, gatt.getDevice().getName() + ".on leedarson notify : " + characteristic.getUuid() + ",--> data:" + e.a(characteristic.getValue()), (String) null, 2, (Object) null);
            a ldsGattListener = this.this$0.getLdsGattListener();
            if (ldsGattListener != null) {
                ldsGattListener.a(characteristic);
                return;
            }
            return;
        }
        BluetoothManager bluetoothManager = this.this$0;
        bluetoothManager.logd(gatt.getDevice().getName() + ".on matter notify : " + characteristic.getUuid() + ",--> data:" + e.a(characteristic.getValue()));
        this.wrappedCallback.onCharacteristicChanged(gatt, characteristic);
    }

    public void onCharacteristicRead(@NotNull BluetoothGatt gatt, @NotNull BluetoothGattCharacteristic characteristic, int status) {
        k.e(gatt, "gatt");
        k.e(characteristic, "characteristic");
        BluetoothManager bluetoothManager = this.this$0;
        bluetoothManager.logd(gatt.getDevice().getName() + ".onCharacteristicRead: " + characteristic.getUuid() + " -> " + status);
        this.wrappedCallback.onCharacteristicRead(gatt, characteristic, status);
    }

    public void onCharacteristicWrite(@NotNull BluetoothGatt gatt, @NotNull BluetoothGattCharacteristic characteristic, int status) {
        k.e(gatt, "gatt");
        k.e(characteristic, "characteristic");
        BluetoothManager bluetoothManager = this.this$0;
        bluetoothManager.logd(gatt.getDevice().getName() + ".onCharacteristicWrite: " + characteristic.getUuid() + " -> " + status);
        String uuid = characteristic.getUuid().toString();
        k.d(uuid, "characteristic.uuid.toString()");
        Locale locale = Locale.getDefault();
        k.d(locale, "getDefault()");
        String upperCase = uuid.toUpperCase(locale);
        k.d(upperCase, "(this as java.lang.String).toUpperCase(locale)");
        Locale locale2 = Locale.getDefault();
        k.d(locale2, "getDefault()");
        String upperCase2 = "a00a".toUpperCase(locale2);
        k.d(upperCase2, "(this as java.lang.String).toUpperCase(locale)");
        if (!x.S(upperCase, upperCase2, false, 2, (Object) null)) {
            this.wrappedCallback.onCharacteristicWrite(gatt, characteristic, status);
        }
    }

    public void onDescriptorRead(@NotNull BluetoothGatt gatt, @NotNull BluetoothGattDescriptor descriptor, int status) {
        k.e(gatt, "gatt");
        k.e(descriptor, "descriptor");
        BluetoothManager bluetoothManager = this.this$0;
        bluetoothManager.logd(gatt.getDevice().getName() + ".onDescriptorRead: " + descriptor.getUuid() + " -> " + status);
        this.wrappedCallback.onDescriptorRead(gatt, descriptor, status);
    }

    public void onDescriptorWrite(@NotNull BluetoothGatt gatt, @NotNull BluetoothGattDescriptor descriptor, int status) {
        k.e(gatt, "gatt");
        k.e(descriptor, "descriptor");
        BluetoothManager bluetoothManager = this.this$0;
        bluetoothManager.logd(gatt.getDevice().getName() + ".onDescriptorWrite: " + descriptor.getUuid() + " -> " + status);
        this.wrappedCallback.onDescriptorWrite(gatt, descriptor, status);
    }

    public void onReadRemoteRssi(@NotNull BluetoothGatt gatt, int rssi, int status) {
        k.e(gatt, "gatt");
        BluetoothManager bluetoothManager = this.this$0;
        bluetoothManager.logd(gatt.getDevice().getName() + ".onReadRemoteRssi: " + rssi + " -> " + status);
        this.wrappedCallback.onReadRemoteRssi(gatt, rssi, status);
    }

    public void onReliableWriteCompleted(@NotNull BluetoothGatt gatt, int status) {
        k.e(gatt, "gatt");
        BluetoothManager bluetoothManager = this.this$0;
        bluetoothManager.logd(gatt.getDevice().getName() + ".onReliableWriteCompleted: " + status);
        this.wrappedCallback.onReliableWriteCompleted(gatt, status);
    }
}
