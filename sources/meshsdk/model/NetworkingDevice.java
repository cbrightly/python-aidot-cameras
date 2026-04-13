package meshsdk.model;

import android.bluetooth.BluetoothDevice;
import androidx.core.view.InputDeviceCompat;
import com.telink.ble.mesh.util.LogInfo;
import java.util.ArrayList;
import java.util.List;

public class NetworkingDevice {
    public static final String TAG_BIND = "bind";
    public static final String TAG_PROVISION = "provision";
    public static final String TAG_PUB_SET = "pub-set";
    public static final String TAG_SCAN = "scan";
    public BluetoothDevice bluetoothDevice;
    public boolean isProvisionedAndNotKeyBind = false;
    public boolean logExpand = false;
    public List<LogInfo> logs = new ArrayList();
    public NodeInfo nodeInfo;
    public int oobInfo;
    public int rssi;
    public NetworkingState state = NetworkingState.IDLE;
    public int veryLowBattery;

    public NetworkingDevice(NodeInfo nodeInfo2) {
        this.nodeInfo = nodeInfo2;
    }

    public int getStateColor() {
        return InputDeviceCompat.SOURCE_ANY;
    }

    public boolean isProcessing() {
        NetworkingState networkingState = this.state;
        return networkingState == NetworkingState.PROVISIONING || networkingState == NetworkingState.BINDING || networkingState == NetworkingState.TIME_PUB_SETTING;
    }

    public void addLog(String tag, String log) {
        this.logs.add(new LogInfo(tag, log, 1));
    }
}
