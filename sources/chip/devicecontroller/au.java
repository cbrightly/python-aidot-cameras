package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class au implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ au a = new au();

    private /* synthetic */ au() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.EthernetNetworkDiagnosticsCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.EthernetNetworkDiagnosticsCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
