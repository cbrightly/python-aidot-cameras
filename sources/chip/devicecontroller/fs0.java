package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class fs0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ fs0 a = new fs0();

    private /* synthetic */ fs0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readTxRetryCountAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
