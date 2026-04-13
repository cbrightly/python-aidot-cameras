package com.clj.fastble.scan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.clj.fastble.data.BleDevice;
import java.util.ArrayList;
import java.util.List;

@TargetApi(18)
/* compiled from: BleScanPresenter */
public abstract class a implements BluetoothAdapter.LeScanCallback {
    private final List<BleDevice> a = new ArrayList();
    private final Handler b = new Handler(Looper.getMainLooper());
    private Handler c;
    private boolean d;

    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if (device != null && this.d) {
            Message message = this.c.obtainMessage();
            message.what = 0;
            message.obj = new BleDevice(device, rssi, scanRecord, System.currentTimeMillis());
            this.c.sendMessage(message);
        }
    }
}
