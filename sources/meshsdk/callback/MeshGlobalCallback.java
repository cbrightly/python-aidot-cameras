package meshsdk.callback;

import com.leedarson.serviceimpl.g;

public abstract class MeshGlobalCallback extends g {
    public abstract void onCanclePreReportDeviceStatusChanged();

    public abstract void onDeviceOnlineChange(String str, int i);

    public abstract void onDeviceStatusChange(String str, int i, Object obj);

    public abstract void onNetworkInfoUpdate(int i, int i2);

    public abstract void onNetworkStatusChange(int i);

    public abstract void onPreReportDeviceStatusChange(String str, int i, int i2, boolean z);

    public abstract void onRetryReportDeviceOnLine();
}
