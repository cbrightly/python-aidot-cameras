package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class jq implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ jq a = new jq();

    private /* synthetic */ jq() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readRxDataPollCountAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
