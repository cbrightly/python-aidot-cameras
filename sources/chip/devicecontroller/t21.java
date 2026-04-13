package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class t21 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ t21 a = new t21();

    private /* synthetic */ t21() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WiFiNetworkDiagnosticsCluster) baseChipCluster).readChannelNumberAttribute((ChipClusters.WiFiNetworkDiagnosticsCluster.ChannelNumberAttributeCallback) obj);
    }
}
