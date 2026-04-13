package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class k70 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ k70 a = new k70();

    private /* synthetic */ k70() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readTxDataCountAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
