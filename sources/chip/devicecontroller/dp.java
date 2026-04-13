package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class dp implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ dp a = new dp();

    private /* synthetic */ dp() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readEpochUsAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
