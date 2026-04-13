package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class of implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ of a = new of();

    private /* synthetic */ of() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.EthernetNetworkDiagnosticsCluster) baseChipCluster).readTimeSinceResetAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
