package chip.platform;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.os.Build;
import com.leedarson.serviceimpl.k;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BleConnectionCompat {
    private static final String TAG = "BleConnectionCompat";
    private final Context context;

    public BleConnectionCompat(Context context2) {
        this.context = context2;
    }

    public BluetoothGatt connectGatt(BluetoothDevice remoteDevice, boolean autoConnect, BluetoothGattCallback bluetoothGattCallback) {
        if (remoteDevice == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 24 || !autoConnect) {
            return connectGattCompat(bluetoothGattCallback, remoteDevice, autoConnect);
        }
        try {
            k kVar = k.a;
            kVar.a("Trying to connectGatt using reflection.", "");
            Object iBluetoothGatt = getIBluetoothGatt(getIBluetoothManager());
            if (iBluetoothGatt == null) {
                kVar.a("Couldn't get iBluetoothGatt object", "");
                return connectGattCompat(bluetoothGattCallback, remoteDevice, true);
            }
            BluetoothGatt bluetoothGatt = createBluetoothGatt(iBluetoothGatt, remoteDevice);
            if (bluetoothGatt == null) {
                kVar.a("Couldn't create BluetoothGatt object", "");
                return connectGattCompat(bluetoothGattCallback, remoteDevice, true);
            }
            if (!connectUsingReflection(bluetoothGatt, bluetoothGattCallback, true)) {
                kVar.a("Connection using reflection failed, closing gatt", "");
                bluetoothGatt.close();
            }
            return bluetoothGatt;
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            k.a.a("Error while trying to connect via reflection", "");
            return connectGattCompat(bluetoothGattCallback, remoteDevice, true);
        }
    }

    private BluetoothGatt connectGattCompat(BluetoothGattCallback bluetoothGattCallback, BluetoothDevice device, boolean autoConnect) {
        k.a.a("Connecting without reflection", "");
        if (Build.VERSION.SDK_INT >= 23) {
            return device.connectGatt(this.context, autoConnect, bluetoothGattCallback, 2);
        }
        return device.connectGatt(this.context, autoConnect, bluetoothGattCallback);
    }

    private static boolean connectUsingReflection(BluetoothGatt bluetoothGatt, BluetoothGattCallback bluetoothGattCallback, boolean autoConnect) {
        k.a.a("Connecting using reflection", "");
        setAutoConnectValue(bluetoothGatt, autoConnect);
        Method connectMethod = bluetoothGatt.getClass().getDeclaredMethod("connect", new Class[]{Boolean.class, BluetoothGattCallback.class});
        connectMethod.setAccessible(true);
        return ((Boolean) connectMethod.invoke(bluetoothGatt, new Object[]{true, bluetoothGattCallback})).booleanValue();
    }

    @TargetApi(23)
    private BluetoothGatt createBluetoothGatt(Object iBluetoothGatt, BluetoothDevice remoteDevice) {
        Constructor bluetoothGattConstructor = BluetoothGatt.class.getDeclaredConstructors()[0];
        bluetoothGattConstructor.setAccessible(true);
        k kVar = k.a;
        kVar.a("Found constructor with args count = " + bluetoothGattConstructor.getParameterTypes().length, "");
        if (bluetoothGattConstructor.getParameterTypes().length == 4) {
            return (BluetoothGatt) bluetoothGattConstructor.newInstance(new Object[]{this.context, iBluetoothGatt, remoteDevice, 2});
        }
        return (BluetoothGatt) bluetoothGattConstructor.newInstance(new Object[]{this.context, iBluetoothGatt, remoteDevice});
    }

    private static Object getIBluetoothGatt(Object iBluetoothManager) {
        if (iBluetoothManager == null) {
            return null;
        }
        return getMethodFromClass(iBluetoothManager.getClass(), "getBluetoothGatt").invoke(iBluetoothManager, new Object[0]);
    }

    private static Object getIBluetoothManager() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return null;
        }
        return getMethodFromClass(bluetoothAdapter.getClass(), "getBluetoothManager").invoke(bluetoothAdapter, new Object[0]);
    }

    private static Method getMethodFromClass(Class<?> cls, String methodName) {
        Method method = cls.getDeclaredMethod(methodName, new Class[0]);
        method.setAccessible(true);
        return method;
    }

    private static void setAutoConnectValue(BluetoothGatt bluetoothGatt, boolean autoConnect) {
        Field autoConnectField = bluetoothGatt.getClass().getDeclaredField("mAutoConnect");
        autoConnectField.setAccessible(true);
        autoConnectField.setBoolean(bluetoothGatt, autoConnect);
    }
}
