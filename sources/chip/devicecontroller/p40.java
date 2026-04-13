package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class p40 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ p40 a = new p40();

    private /* synthetic */ p40() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WiFiNetworkDiagnosticsCluster) baseChipCluster).readBeaconLostCountAttribute((ChipClusters.WiFiNetworkDiagnosticsCluster.BeaconLostCountAttributeCallback) obj);
    }
}
