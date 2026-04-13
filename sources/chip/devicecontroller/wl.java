package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class wl implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ wl a = new wl();

    private /* synthetic */ wl() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WiFiNetworkDiagnosticsCluster) baseChipCluster).readRssiAttribute((ChipClusters.WiFiNetworkDiagnosticsCluster.RssiAttributeCallback) obj);
    }
}
