package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class so implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ so a = new so();

    private /* synthetic */ so() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.EthernetNetworkDiagnosticsCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.EthernetNetworkDiagnosticsCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
