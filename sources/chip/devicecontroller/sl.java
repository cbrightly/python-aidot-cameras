package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class sl implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ sl a = new sl();

    private /* synthetic */ sl() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readNullableInt56uAttribute((ChipClusters.TestClusterCluster.NullableInt56uAttributeCallback) obj);
    }
}
