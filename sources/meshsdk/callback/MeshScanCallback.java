package meshsdk.callback;

import meshsdk.model.NetworkingDevice;

public abstract class MeshScanCallback extends AbCommonCallback {
    public abstract void onDeviceFound(NetworkingDevice networkingDevice, String str, String str2, String str3);
}
