package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class p7 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ p7 a = new p7();

    private /* synthetic */ p7() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).testSpecific((ChipClusters.TestClusterCluster.TestSpecificResponseCallback) obj);
    }
}
