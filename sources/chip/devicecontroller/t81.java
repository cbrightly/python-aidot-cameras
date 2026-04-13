package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class t81 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ t81 a = new t81();

    private /* synthetic */ t81() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readTxBroadcastCountAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
