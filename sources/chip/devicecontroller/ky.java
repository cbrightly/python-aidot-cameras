package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ky implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ky a = new ky();

    private /* synthetic */ ky() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readTxTotalCountAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
