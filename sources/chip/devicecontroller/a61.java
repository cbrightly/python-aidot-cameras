package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class a61 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ a61 a = new a61();

    private /* synthetic */ a61() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readTxBeaconCountAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
