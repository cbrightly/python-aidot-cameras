package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class dj0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ dj0 a = new dj0();

    private /* synthetic */ dj0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readPartitionIdChangeCountAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
