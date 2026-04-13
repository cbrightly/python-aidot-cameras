package chip.platform;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;

public interface BleManager {
    int addConnection(BluetoothGatt bluetoothGatt);

    BluetoothGattCallback getCallback();

    BluetoothGatt getConnection(int i);

    boolean hasFlag(long j);

    int init();

    boolean onCloseConnection(int i);

    int onGetMTU(int i);

    void onNewConnection(int i);

    void onNotifyChipConnectionClosed(int i);

    boolean onSendWriteRequest(int i, byte[] bArr, byte[] bArr2, byte[] bArr3);

    boolean onSubscribeCharacteristic(int i, byte[] bArr, byte[] bArr2);

    boolean onUnsubscribeCharacteristic(int i, byte[] bArr, byte[] bArr2);

    BluetoothGatt removeConnection(int i);

    void setAndroidChipPlatform(AndroidChipPlatform androidChipPlatform);

    void setBleCallback(BleCallback bleCallback);

    long setFlag(long j, boolean z);
}
