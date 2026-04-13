package chip.platform;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.util.Log;
import com.leedarson.serviceimpl.i;
import com.leedarson.serviceimpl.k;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AndroidBleManager implements BleManager {
    private static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static final char[] HEX_BASIC = "0123456789ABCDEF".toCharArray();
    public static final int INITIAL_CONNECTIONS = 4;
    private static final String TAG = "AndroidBleManager";
    private BleCallback mBleCallback;
    private final List<BluetoothGatt> mConnections = new ArrayList(4);
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == 0) {
                int connId = AndroidBleManager.this.getConnId(gatt);
                if (connId > 0) {
                    Log.d(AndroidBleManager.TAG, "onConnectionStateChange Disconnected");
                    AndroidBleManager.this.mPlatform.handleConnectionError(connId);
                    return;
                }
                Log.e(AndroidBleManager.TAG, "onConnectionStateChange disconnected: no active connection");
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            byte[] svcIdBytes = AndroidBleManager.convertUUIDToBytes(characteristic.getService().getUuid());
            byte[] charIdBytes = AndroidBleManager.convertUUIDToBytes(characteristic.getUuid());
            if (status != 0) {
                Log.e(AndroidBleManager.TAG, "onCharacteristicWrite for " + characteristic.getUuid().toString() + " failed with status: " + status);
                return;
            }
            int connId = AndroidBleManager.this.getConnId(gatt);
            if (connId > 0) {
                AndroidBleManager.this.mPlatform.handleWriteConfirmation(connId, svcIdBytes, charIdBytes, status == 0);
            } else {
                Log.e(AndroidBleManager.TAG, "onCharacteristicWrite no active connection");
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] svcIdBytes = AndroidBleManager.convertUUIDToBytes(characteristic.getService().getUuid());
            byte[] charIdBytes = AndroidBleManager.convertUUIDToBytes(characteristic.getUuid());
            int connId = AndroidBleManager.this.getConnId(gatt);
            if (connId > 0) {
                Log.d(AndroidBleManager.TAG, "recv:serviceUUID:" + AndroidBleManager.bytesToHexString(svcIdBytes, "") + "\ncharUUID:" + AndroidBleManager.bytesToHexString(charIdBytes) + "\ndata:" + AndroidBleManager.bytesToHexString(characteristic.getValue(), ""));
                AndroidBleManager.this.mPlatform.handleIndicationReceived(connId, svcIdBytes, charIdBytes, characteristic.getValue());
                return;
            }
            Log.e(AndroidBleManager.TAG, "onCharacteristicChanged no active connection");
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor desc, int status) {
            BluetoothGattCharacteristic characteristic = desc.getCharacteristic();
            byte[] svcIdBytes = AndroidBleManager.convertUUIDToBytes(characteristic.getService().getUuid());
            byte[] charIdBytes = AndroidBleManager.convertUUIDToBytes(characteristic.getUuid());
            if (status != 0) {
                Log.e(AndroidBleManager.TAG, "onDescriptorWrite for " + desc.getUuid().toString() + " failed with status: " + status);
            }
            int connId = AndroidBleManager.this.getConnId(gatt);
            if (connId == 0) {
                Log.e(AndroidBleManager.TAG, "onDescriptorWrite no active connection");
                return;
            }
            boolean z = true;
            if (desc.getValue() == BluetoothGattDescriptor.ENABLE_INDICATION_VALUE) {
                AndroidChipPlatform access$100 = AndroidBleManager.this.mPlatform;
                if (status != 0) {
                    z = false;
                }
                access$100.handleSubscribeComplete(connId, svcIdBytes, charIdBytes, z);
            } else if (desc.getValue() == BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE) {
                AndroidChipPlatform access$1002 = AndroidBleManager.this.mPlatform;
                if (status != 0) {
                    z = false;
                }
                access$1002.handleUnsubscribeComplete(connId, svcIdBytes, charIdBytes, z);
            } else {
                Log.d(AndroidBleManager.TAG, "Unexpected onDescriptorWrite().");
            }
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor desc, int status) {
        }
    };
    /* access modifiers changed from: private */
    public AndroidChipPlatform mPlatform;

    public static class BleMtuDenylist {
        static final boolean BLE_MTU_DENYLISTED;
        static final int BLE_MTU_FALLBACK = 23;

        private BleMtuDenylist() {
        }

        static {
            String str = Build.MANUFACTURER;
            if ("OnePlus".equals(str)) {
                BLE_MTU_DENYLISTED = "ONE A2005".equals(Build.MODEL);
                return;
            }
            boolean z = false;
            if ("motorola".equals(str)) {
                String str2 = Build.MODEL;
                if ("XT1575".equals(str2) || "XT1585".equals(str2)) {
                    z = true;
                }
                BLE_MTU_DENYLISTED = z;
                return;
            }
            BLE_MTU_DENYLISTED = false;
        }
    }

    public synchronized int addConnection(BluetoothGatt bleGatt) {
        int connIndex = 0;
        while (connIndex < this.mConnections.size()) {
            if (this.mConnections.get(connIndex) == null) {
                this.mConnections.set(connIndex, bleGatt);
                return connIndex + 1;
            }
            connIndex++;
        }
        this.mConnections.add(connIndex, bleGatt);
        return connIndex + 1;
    }

    public synchronized BluetoothGatt removeConnection(int connId) {
        int connIndex = connId - 1;
        if (connIndex >= 0) {
            if (connIndex < this.mConnections.size()) {
                return this.mConnections.set(connIndex, (Object) null);
            }
        }
        Log.e(TAG, "Trying to remove unknown connId " + connId);
        return null;
    }

    public synchronized BluetoothGatt getConnection(int connId) {
        int connIndex = connId - 1;
        if (connIndex >= 0) {
            if (connIndex < this.mConnections.size()) {
                return this.mConnections.get(connIndex);
            }
        }
        Log.e(TAG, "Unknown connId " + connId);
        return null;
    }

    public void setBleCallback(BleCallback bleCallback) {
        this.mBleCallback = bleCallback;
    }

    public BluetoothGattCallback getCallback() {
        return this.mGattCallback;
    }

    public void setAndroidChipPlatform(AndroidChipPlatform platform) {
        this.mPlatform = platform;
    }

    /* access modifiers changed from: private */
    public synchronized int getConnId(BluetoothGatt gatt) {
        int connIndex = 0;
        while (connIndex < this.mConnections.size()) {
            if (this.mConnections.get(connIndex) != gatt || gatt == null) {
                connIndex++;
            } else {
                return connIndex + 1;
            }
        }
        return 0;
    }

    public int init() {
        return 0;
    }

    public long setFlag(long flag, boolean isSet) {
        return 0;
    }

    public boolean hasFlag(long flag) {
        return false;
    }

    public boolean onSubscribeCharacteristic(int connId, byte[] svcId, byte[] charId) {
        BluetoothGatt bluetoothGatt = getConnection(connId);
        if (bluetoothGatt == null) {
            Log.i(TAG, "Tried to send characteristic, but BLE connection was not found.");
            return false;
        }
        BluetoothGattService subscribeSvc = bluetoothGatt.getService(convertBytesToUUID(svcId));
        if (subscribeSvc == null) {
            k.a.c("Bad service", TAG);
            return false;
        }
        BluetoothGattCharacteristic subscribeChar = subscribeSvc.getCharacteristic(convertBytesToUUID(charId));
        if (subscribeChar == null) {
            Log.e(TAG, "Bad characteristic");
            return false;
        } else if (!bluetoothGatt.setCharacteristicNotification(subscribeChar, true)) {
            Log.e(TAG, "Failed to subscribe to characteristic.");
            return false;
        } else {
            BluetoothGattDescriptor descriptor = subscribeChar.getDescriptor(UUID.fromString(CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            if (bluetoothGatt.writeDescriptor(descriptor)) {
                return true;
            }
            Log.e(TAG, "writeDescriptor failed");
            return false;
        }
    }

    public boolean onUnsubscribeCharacteristic(int connId, byte[] svcId, byte[] charId) {
        BluetoothGatt bluetoothGatt = getConnection(connId);
        if (bluetoothGatt == null) {
            Log.i(TAG, "Tried to unsubscribe characteristic, but BLE connection was not found.");
            return false;
        }
        BluetoothGattService subscribeSvc = bluetoothGatt.getService(convertBytesToUUID(svcId));
        if (subscribeSvc == null) {
            Log.e(TAG, "Bad service");
            return false;
        }
        BluetoothGattCharacteristic subscribeChar = subscribeSvc.getCharacteristic(convertBytesToUUID(charId));
        if (subscribeChar == null) {
            Log.e(TAG, "Bad characteristic");
            return false;
        } else if (!bluetoothGatt.setCharacteristicNotification(subscribeChar, false)) {
            Log.e(TAG, "Failed to unsubscribe to characteristic.");
            return false;
        } else {
            BluetoothGattDescriptor descriptor = subscribeChar.getDescriptor(UUID.fromString(CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            if (bluetoothGatt.writeDescriptor(descriptor)) {
                return true;
            }
            Log.e(TAG, "writeDescriptor failed");
            return false;
        }
    }

    public boolean onCloseConnection(int connId) {
        k.a.a("onCloseConnection C++通知JAVA 断开蓝牙", "");
        BluetoothGatt bluetoothGatt = getConnection(connId);
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
            bluetoothGatt.close();
            removeConnection(connId);
            BleCallback bleCallback = this.mBleCallback;
            if (bleCallback != null) {
                bleCallback.onCloseBleComplete(connId);
            }
            i.a.l().close();
            return true;
        }
        Log.i(TAG, "Tried to close BLE connection, but connection was not found.");
        return true;
    }

    public int onGetMTU(int connId) {
        Log.d(TAG, "Android Manufacturer: (" + Build.MANUFACTURER + ")");
        Log.d(TAG, "Android Model: (" + Build.MODEL + ")");
        if (!BleMtuDenylist.BLE_MTU_DENYLISTED) {
            return 0;
        }
        Log.e(TAG, "Detected Manufacturer/Model with MTU incompatibiility. Reporting MTU: " + 23);
        return 23;
    }

    public boolean onSendWriteRequest(int connId, byte[] svcId, byte[] charId, byte[] characteristicData) {
        BluetoothGatt bluetoothGatt = getConnection(connId);
        if (bluetoothGatt == null) {
            Log.i(TAG, "Tried to send characteristic, but BLE connection was not found.");
            return false;
        }
        BluetoothGattService sendSvc = bluetoothGatt.getService(convertBytesToUUID(svcId));
        if (sendSvc == null) {
            Log.e(TAG, "Bad service");
            return false;
        }
        BluetoothGattCharacteristic sendChar = sendSvc.getCharacteristic(convertBytesToUUID(charId));
        if (!sendChar.setValue(characteristicData)) {
            Log.e(TAG, "Failed to set characteristic");
            return false;
        }
        sendChar.setWriteType(2);
        if (bluetoothGatt.writeCharacteristic(sendChar)) {
            return true;
        }
        Log.e(TAG, "Failed writing char");
        return false;
    }

    public void onNotifyChipConnectionClosed(int connId) {
        if (getConnection(connId) != null) {
            removeConnection(connId);
            BleCallback bleCallback = this.mBleCallback;
            if (bleCallback != null) {
                bleCallback.onNotifyChipConnectionClosed(connId);
                return;
            }
            return;
        }
        Log.i(TAG, "Tried to notify connection closed, but BLE connection was not found.");
    }

    public void onNewConnection(int discriminator) {
    }

    /* access modifiers changed from: private */
    public static byte[] convertUUIDToBytes(UUID uuid) {
        byte[] idBytes = new byte[16];
        long idBits = uuid.getLeastSignificantBits();
        for (int i = 0; i < 8; i++) {
            idBytes[15 - i] = (byte) ((int) (255 & idBits));
            idBits >>= 8;
        }
        long idBits2 = uuid.getMostSignificantBits();
        for (int i2 = 0; i2 < 8; i2++) {
            idBytes[7 - i2] = (byte) ((int) (idBits2 & 255));
            idBits2 >>= 8;
        }
        return idBytes;
    }

    private static UUID convertBytesToUUID(byte[] id) {
        long mostSigBits = 0;
        long leastSigBits = 0;
        if (id.length == 16) {
            for (int i = 0; i < 8; i++) {
                mostSigBits = (mostSigBits << 8) | ((long) (id[i] & 255));
            }
            for (int i2 = 0; i2 < 8; i2++) {
                leastSigBits = (leastSigBits << 8) | ((long) (id[i2 + 8] & 255));
            }
        }
        return new UUID(mostSigBits, leastSigBits);
    }

    public static String bytesToHexString(byte[] array) {
        return bytesToHexString(array, "");
    }

    public static String bytesToHexString(byte[] array, String separator) {
        if (array == null || array.length == 0) {
            return "";
        }
        boolean sepNul = separator == null || separator.length() == 0;
        StringBuilder hexResult = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            int ai = array[i] & 255;
            if (i != 0 && !sepNul) {
                hexResult.append(separator);
            }
            char[] cArr = HEX_BASIC;
            hexResult.append(cArr[ai >>> 4]);
            hexResult.append(cArr[ai & 15]);
        }
        return hexResult.toString();
    }
}
