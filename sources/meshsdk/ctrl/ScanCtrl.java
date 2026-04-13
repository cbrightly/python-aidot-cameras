package meshsdk.ctrl;

import android.util.Pair;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.serviceinterface.BleC075Service;
import com.telink.ble.mesh.core.ble.LeScanFilter;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import com.telink.ble.mesh.entity.NetworkingDeviceWrapper;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.foundation.event.ScanEvent;
import com.telink.ble.mesh.foundation.parameter.ScanParameters;
import java.util.HashMap;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.MeshScanLog;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshScanCallback;
import meshsdk.model.NetworkingDevice;
import meshsdk.util.LDSMeshUtil;

public class ScanCtrl extends CtrlLifecycle implements EventListener<String> {
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private HashMap<String, Long> actionTimeMap = new HashMap<>();
    /* access modifiers changed from: private */
    public HashMap<String, Pair<String, NetworkingDevice>> cacheDevMap = new HashMap<>();
    private boolean isScanning = false;
    /* access modifiers changed from: private */
    public MeshScanCallback scanCallback;

    public void setScanCallback(MeshScanCallback scanCallback2) {
        this.scanCallback = scanCallback2;
    }

    public ScanCtrl(SIGMesh sigMesh) {
        super(sigMesh);
        onCreate();
    }

    public void onCreate() {
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_SCAN_TIMEOUT", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DEVICE_FOUND", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_SCAN_FAIL", this);
    }

    public void scan() {
        HashMap<String, Pair<String, NetworkingDevice>> hashMap = this.cacheDevMap;
        if (hashMap != null && hashMap.size() > 0) {
            this.cacheDevMap.clear();
        }
        this.isScanning = true;
        ScanParameters parameters = ScanParameters.k(false, false);
        parameters.h((LeScanFilter) null);
        MeshScanLog.d("ScanCtrl scan scanMeshControll");
        this.actionTimeMap.remove("stopScan");
        MeshService.k().D(parameters);
    }

    private boolean validateAction(String action) {
        HashMap<String, Pair<String, NetworkingDevice>> hashMap;
        if (this.isScanning && NetworkingDevice.TAG_SCAN.equals(action) && (hashMap = this.cacheDevMap) != null && hashMap.size() > 0) {
            postCacheToJs();
            return false;
        } else if (!this.isScanning && "stopScan".equals(action)) {
            return false;
        } else {
            if (this.actionTimeMap.containsKey(action)) {
                if (System.currentTimeMillis() - this.actionTimeMap.get(action).longValue() <= 500) {
                    return false;
                }
            }
            this.actionTimeMap.put(action, Long.valueOf(System.currentTimeMillis()));
            return true;
        }
    }

    public void stopScan(String fromBz) {
        if (validateAction("stopScan")) {
            HashMap<String, Pair<String, NetworkingDevice>> hashMap = this.cacheDevMap;
            if (hashMap != null) {
                hashMap.clear();
            }
            BleC075Service bleC075Service = (BleC075Service) a.c().g(BleC075Service.class);
            if (bleC075Service != null) {
                bleC075Service.clearCache();
            }
            MeshLog.d("CZB SIGMesh stopScan");
            this.actionTimeMap.remove(NetworkingDevice.TAG_SCAN);
            MeshService k = MeshService.k();
            k.n(false, "ScanCtrl stopScan fromBz:" + fromBz);
            this.isScanning = false;
            MeshService.k().F(fromBz);
        }
    }

    public void performed(Event<String> event) {
        if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DEVICE_FOUND")) {
            try {
                onDeviceFound(((ScanEvent) event).a());
            } catch (Exception e) {
                timber.log.a.b(e);
                e.printStackTrace();
            }
        } else if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_SCAN_TIMEOUT")) {
            MeshLog.d("mesh scan timeout");
            stopScan("ScanCtrl scantimeout");
        } else if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_SCAN_FAIL")) {
            String errCode = "";
            if (event instanceof ScanEvent) {
                errCode = String.valueOf(((ScanEvent) event).b());
            }
            MeshLog.e("mesh scan fail:" + errCode);
        }
    }

    private void onDeviceFound(AdvertisingDevice advertisingDevice) {
        MeshScanCallback meshScanCallback;
        NetworkingDeviceWrapper wrapper = LDSMeshUtil.isMeshUnProvisionAdv(advertisingDevice.c, advertisingDevice.d, advertisingDevice.f);
        if (wrapper == null) {
            wrapper = LDSMeshUtil.isMeshProvisionButNotKeybindAdv(advertisingDevice.c, advertisingDevice.d, advertisingDevice.f);
        }
        if (wrapper != null && (meshScanCallback = this.scanCallback) != null) {
            meshScanCallback.onDeviceFound(wrapper.a, wrapper.b, wrapper.c, wrapper.d);
        }
    }

    public static String bytesToHex(byte[] bytes) {
        int a;
        char[] buf = new char[(bytes.length * 2)];
        int index = 0;
        for (byte b : bytes) {
            if (b < 0) {
                a = b + 256;
            } else {
                a = b;
            }
            int index2 = index + 1;
            char[] cArr = HEX_CHAR;
            buf[index] = cArr[a / 16];
            index = index2 + 1;
            buf[index2] = cArr[a % 16];
        }
        return new String(buf);
    }

    public void onDestroy() {
        MeshEventHandler.getInstance().removeEventListener(this);
    }

    public void postCacheToJs() {
        SIGMesh.getInstance().executorTask(new Runnable() {
            public void run() {
                MeshLog.e("Mesh扫描上报缓存列表:" + ScanCtrl.this.cacheDevMap.size());
                if (ScanCtrl.this.cacheDevMap != null && ScanCtrl.this.cacheDevMap.size() > 0) {
                    for (String mac : ScanCtrl.this.cacheDevMap.keySet()) {
                        Pair<String, NetworkingDevice> pair = (Pair) ScanCtrl.this.cacheDevMap.get(mac);
                        String advertisingData = (String) pair.first;
                        NetworkingDevice networkingDevice = (NetworkingDevice) pair.second;
                        if (ScanCtrl.this.scanCallback != null) {
                            ScanCtrl.this.scanCallback.onDeviceFound(networkingDevice, "cache", mac, advertisingData);
                        }
                    }
                }
            }
        });
    }
}
