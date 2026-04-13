package meshsdk.callback;

import meshsdk.model.NetworkingDevice;

public interface MeshBindCallback {
    void onBindFail(int i, String str);

    void onBindSuccess(String str, NetworkingDevice networkingDevice, String str2);
}
