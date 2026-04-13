package org.webrtc;

import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import org.webrtc.NetworkMonitorAutoDetect;

/* compiled from: lambda */
public final /* synthetic */ class u implements WifiP2pManager.GroupInfoListener {
    public final /* synthetic */ NetworkMonitorAutoDetect.WifiDirectManagerDelegate a;

    public /* synthetic */ u(NetworkMonitorAutoDetect.WifiDirectManagerDelegate wifiDirectManagerDelegate) {
        this.a = wifiDirectManagerDelegate;
    }

    public final void onGroupInfoAvailable(WifiP2pGroup wifiP2pGroup) {
        this.a.a(wifiP2pGroup);
    }
}
